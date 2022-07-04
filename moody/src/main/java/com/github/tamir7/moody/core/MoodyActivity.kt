package com.github.tamir7.moody.core

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.github.tamir7.moody.R
import com.github.tamir7.moody.navigator.Navigator
import javax.inject.Inject

class MoodyActivity : AppCompatActivity() {

    @Inject lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        MoodyApplication.getComponent(this).inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_moody)

        if (savedInstanceState == null) {
            navigator.setRoot(HomeScreen())
        }
    }

    override fun onResume() {
        super.onResume()
        navigator.start(this, R.id.moody_activity_root)
    }

    override fun onPause() {
        super.onPause()
        navigator.stop()
    }
}