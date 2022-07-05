package com.github.tamir7.moody.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import butterknife.BindView
import com.github.tamir7.moody.R
import com.github.tamir7.moody.core.EvaluateArguments
import com.github.tamir7.moody.core.MoodyActivity
import com.github.tamir7.moody.core.MoodyFragment
import com.github.tamir7.moody.inject.FragmentComponent
import com.github.tamir7.moody.service.MoodRecognizer
import com.github.tamir7.moody.util.CameraManager
import com.github.tamir7.moody.util.GlideApp
import javax.inject.Inject


class EvaluateFragment: MoodyFragment() {
    private var arguments: EvaluateArguments? = null
    @BindView(R.id.evaluated_image_view) @JvmField  var evaluatedImageView: ImageView? = null
    @Inject lateinit var moodRecognizer: MoodRecognizer
    @Inject lateinit var cameraManager: CameraManager

    override fun inject(component: FragmentComponent) = component.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments = getArguments(EvaluateArguments::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_evaluate, null)
        (activity as MoodyActivity).setTitle(getString(R.string.result_title_text))
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

    override fun onStart() {
        super.onStart()
        arguments?.let {
            moodRecognizer.getEmotion(it.file)
        }
    }
}