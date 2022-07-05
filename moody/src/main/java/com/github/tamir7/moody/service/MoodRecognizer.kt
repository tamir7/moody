package com.github.tamir7.moody.service

import androidx.exifinterface.media.ExifInterface
import com.github.tamir7.moody.model.Emotions
import com.github.tamir7.moody.util.FileUtils
import io.reactivex.rxjava3.core.Observable
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.RuntimeException


@Singleton
class MoodRecognizer @Inject constructor() {
    private val luxandKey = "e10e42924f8e4d278401095f1977b31a"
    private val luxandUrl = "https://api.luxand.cloud"
    private val client = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()
    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(luxandUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    private val service = retrofit.create(LuxandService::class.java)

    fun getEmotion(fileUri: String): Observable<Emotions> {
        return Observable.just(fileUri)
            .map { uri -> fixOrientation(uri) }
            .map { file ->
                val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file)
                return@map MultipartBody.Part.createFormData("photo", file.name, requestFile)
            }.flatMap { body ->
                service.getEmotion(luxandKey, body)
            }.map { moods ->
                if (moods.status == "failure") {
                    throw RuntimeException(moods.message)
                }
                if (moods.faces.isNullOrEmpty()) {
                    throw RuntimeException("Did not find any faces")
                }
                if (moods.faces.size > 1) {
                    throw RuntimeException("Too many faces")
                }
                moods.faces[0].emotions
            }
    }


    private fun fixOrientation(fileUri: String): File {
        var file = File(fileUri)
        val exif = ExifInterface(file)
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
        if (orientation != ExifInterface.ORIENTATION_NORMAL) {
            file = FileUtils.rotateFile(file, orientation)
            val newExif = ExifInterface(file)
            newExif.resetOrientation()
            newExif.saveAttributes()
        }
        return file
    }
}

data class MoodResponse(val status: String, val message: String?, val faces: ArrayList<Faces>?)

data class Faces(val emotions: Emotions)

interface LuxandService {
    @Multipart
    @POST("/photo/emotions")
    fun getEmotion(@Header("token") token: String, @Part part: MultipartBody.Part): Observable<MoodResponse>
}