package com.github.tamir7.moody.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import butterknife.BindView
import com.github.tamir7.moody.R
import com.github.tamir7.moody.core.*
import com.github.tamir7.moody.inject.FragmentComponent
import com.github.tamir7.moody.navigator.Navigator
import com.github.tamir7.moody.service.MoodRecognizer
import com.github.tamir7.moody.util.CameraManager
import com.github.tamir7.moody.util.GlideApp
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class EvaluateFragment: MoodyFragment() {
    private var arguments: EvaluateArguments? = null
    private var disposable: Disposable? = null
    @BindView(R.id.evaluated_image_view) @JvmField  var evaluatedImageView: ImageView? = null
    @BindView(R.id.progressBar) @JvmField var progressBar: ProgressBar? = null
    @Inject lateinit var moodRecognizer: MoodRecognizer
    @Inject lateinit var cameraManager: CameraManager
    @Inject lateinit var navigator: Navigator

    override fun inject(component: FragmentComponent) = component.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments = getArguments(EvaluateArguments::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_evaluate, null)
        (activity as MoodyActivity).setTitle(getString(R.string.evaluate_title_text))
        return  view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.file?.let { file ->
            evaluatedImageView?.let {
                GlideApp.with(this).load(file).into(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        arguments?.let {
            disposable = moodRecognizer.getEmotion(it.file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ emotions ->
                    navigator.replace(ResultScreen(ResultArguments(emotions, null)))
                }, { error ->
                    navigator.replace(ResultScreen(ResultArguments(null, error.message)))
                })
        }
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}