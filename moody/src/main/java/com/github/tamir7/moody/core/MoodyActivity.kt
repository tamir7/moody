package com.github.tamir7.moody.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.github.tamir7.moody.R
import com.github.tamir7.moody.navigator.Navigator
import javax.inject.Inject

class MoodyActivity : AppCompatActivity() {

    @Inject lateinit var navigator: Navigator
    @BindView(R.id.toolbar) @JvmField  var toolbar: Toolbar? = null
    private var unbinder: Unbinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        MoodyApplication.getComponent(this).inject(this)
        super.onCreate(savedInstanceState)

        val view = layoutInflater.inflate(R.layout.activity_moody, null)
        setContentView(view)
        unbinder = ButterKnife.bind(this, view)
        setupToolbar()

        if (savedInstanceState == null) {
            navigator.setRoot(HomeScreen())
        }
    }

    private fun setupToolbar() {
        toolbar?.setNavigationIcon(R.drawable.ic_action_back)
        toolbar?.setNavigationOnClickListener { onBackPressed() }
        toolbar?.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.white))
        toolbar?.setTitleTextColor(ContextCompat.getColor(applicationContext, R.color.black))
    }

    fun setTitle(title: String) {
        toolbar?.title = title
    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder?.unbind()
    }

    override fun onResume() {
        super.onResume()
        navigator.start(this, R.id.content_root)
    }

    override fun onPause() {
        super.onPause()
        navigator.stop()
    }
}