package com.example.app.dicodingeventsapp.core

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.app.dicodingeventsapp.R
import com.google.android.material.appbar.MaterialToolbar

abstract class CoreActivity<viewBinding : ViewBinding> : AppCompatActivity() {
    private var _binding: viewBinding? = null
    protected val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = setupBinding(layoutInflater)
        setContentView(binding.root)
//        Prefs.init(this)
    }

    protected fun setupToolbar(toolbar: MaterialToolbar, onNavigationClick: (() -> Unit)? = null) {
        setSupportActionBar(toolbar)
        toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                onNavigationClick?.invoke() ?: onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    abstract fun setupBinding(layoutInflater: LayoutInflater): viewBinding


}