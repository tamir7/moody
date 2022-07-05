package com.github.tamir7.moody.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import butterknife.OnClick
import com.github.tamir7.moody.R
import com.github.tamir7.moody.core.MoodyActivity
import com.github.tamir7.moody.core.MoodyFragment
import com.github.tamir7.moody.core.EvaluateArguments
import com.github.tamir7.moody.core.EvaluateScreen
import com.github.tamir7.moody.inject.FragmentComponent
import com.github.tamir7.moody.navigator.Navigator
import com.github.tamir7.moody.util.CameraManager
import java.io.File
import javax.inject.Inject

class HomeFragment : MoodyFragment() {
    @Inject lateinit var navigator: Navigator
    @Inject lateinit var cameraManager: CameraManager
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var currentImageFile: File? = null

    override fun inject(component: FragmentComponent) = component.inject(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    handleCameraImage()
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, null)
        (activity as MoodyActivity).setTitle(getString(R.string.home_title_text))
        return view
    }

    @OnClick(R.id.home_action_button)
    fun onClickButton() {
        startCamera()
    }

    private fun startCamera() {
        activity?.let {
            cameraManager.createImageFile(it).let { file ->
                currentImageFile = file
                val intent = cameraManager.createImageCaptureIntent(it, file)
                resultLauncher.launch(intent)
            }
        }
    }

    private fun handleCameraImage() {
        currentImageFile?.let { file ->
            navigator.add(EvaluateScreen(EvaluateArguments(file.toString())))
        }
    }
}