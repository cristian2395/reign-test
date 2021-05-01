package com.example.reigntest.Activities

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.example.reigntest.databinding.ActivityWebViewBinding


class WebViewActivity : AppCompatActivity() {

    lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.myWebView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if (newProgress == 100) {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
        binding.btBack.setOnClickListener {
            finish()
        }
        intent.getStringExtra("storyUrl")?.let {
            binding.myWebView.loadUrl(it)
        }
    }
}