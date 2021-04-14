package com.pixiedia.pixicommerce.ui.WebActivity

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebViewClient
import com.pixiedia.pixicommerce.databinding.ActivityWebBinding
import com.pixiedia.pixicommerce.ui.base.BaseActivity
import im.delight.android.webview.AdvancedWebView

class WebActivity : BaseActivity(), AdvancedWebView.Listener {

    private lateinit var binder: ActivityWebBinding
    private var currentUrl = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binder.root)
        binder.webView.webViewClient = WebViewClient()
        binder.webView.setListener(this, this)

        intent.extras?.let {
            // TODO: 9/9/20
            currentUrl = it.getString(URL, "https://www.google.com")
            binder.webView.loadUrl(currentUrl)
        }

        showLoading()
    }

    override fun onResume() {
        super.onResume()
        binder.webView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binder.webView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binder.webView.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        binder.webView.onActivityResult(requestCode, resultCode, intent)
    }

    override fun onBackPressed() {
        if (!binder.webView.onBackPressed()) return
        super.onBackPressed()
    }

    companion object {
        const val URL = "url"
    }

    override fun onPageStarted(url: String?, favicon: Bitmap?) {
        showLoading()
    }

    override fun onPageFinished(url: String?) {
        hideLoading()
    }

    override fun onPageError(errorCode: Int, description: String?, failingUrl: String?) {
        showError(description)
    }

    override fun onDownloadRequested(
        url: String?,
        suggestedFilename: String?,
        mimeType: String?,
        contentLength: Long,
        contentDisposition: String?,
        userAgent: String?
    ) {
    }

    override fun onExternalPageRequest(url: String?) {
    }
}
