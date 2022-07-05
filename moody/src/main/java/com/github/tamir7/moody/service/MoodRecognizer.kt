package com.github.tamir7.moody.service

import com.google.gson.Gson
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


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

    fun getEmotion(fileUri: String) {

        val file = File(fileUri)

        val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file)
        val body: MultipartBody.Part =
            MultipartBody.Part.createFormData("photo", file.name, requestFile)

        service.getEmotion(luxandKey, body).subscribeOn(Schedulers.io()).subscribe ({res ->
            Timber.e(res.status)
            if (res.status == "failure") {
                Timber.e(res.message)
            } else if (res.status == "success") {
                res.faces?.let {
                    it.forEach { face ->
                        Timber.e("Emotions: ${Gson().toJson(face.emotions)}")
                    }
                }
        }
        }, {err ->
            Timber.e(err.message)
        })
    }
}

data class MoodResponse(val status: String, val message: String?, val faces: ArrayList<Faces>?)

data class Faces(val emotions: Emotions)

data class Emotions(val contempt: Double?, val natural: Double?, val sadness: Double?, val happiness: Double)

interface LuxandService {
    @Multipart
    @POST("/photo/emotions")
    fun getEmotion(@Header("token") token: String, @Part part: MultipartBody.Part): Observable<MoodResponse>
}