package com.github.tamir7.moody.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.github.tamir7.moody.R
import com.github.tamir7.moody.core.MoodyActivity
import com.github.tamir7.moody.core.MoodyFragment
import com.github.tamir7.moody.core.ResultArguments
import com.github.tamir7.moody.inject.FragmentComponent
import com.github.tamir7.moody.model.Emotion
import com.github.tamir7.moody.navigator.Navigator
import javax.inject.Inject

class ResultFragment: MoodyFragment() {
    private var arguments: ResultArguments? = null
    @Inject lateinit var navigator: Navigator
    @BindView(R.id.result_text) @JvmField  var resultText: TextView? = null
    @BindView(R.id.result_title_text) @JvmField  var resulTitleText: TextView? = null

    override fun inject(component: FragmentComponent) = component.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments = getArguments(ResultArguments::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_result, null)
        (activity as MoodyActivity).setTitle(getString(R.string.result_toolbar_title_text))
        return  view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            if (it.error != null) {
                resulTitleText?.setText(R.string.result_error_title_text)
                resultText?.text = it.error
            } else {
                it.emotions?.let { emotions ->
                    resulTitleText?.setText(R.string.result_success_title_text)
                    val textId = when(emotions.getStrongestEmotion()) {
                        Emotion.Unknown -> R.string.result_unknown_text
                        Emotion.Contempt -> R.string.result_contempt_text
                        Emotion.Neutral -> R.string.result_neutral_text
                        Emotion.Sadness -> R.string.result_sadness_text
                        Emotion.Happiness -> R.string.result_happiness_text
                        Emotion.Anger -> R.string.result_anger_text
                        Emotion.Disgust -> R.string.result_disgust_text
                        Emotion.Fear -> R.string.result_fear_text
                    }
                    resultText?.setText(textId)
                }
            }
        }
    }

    @OnClick(R.id.result_done_button)
    fun onClickDone() {
        navigator?.goBack()
    }
}