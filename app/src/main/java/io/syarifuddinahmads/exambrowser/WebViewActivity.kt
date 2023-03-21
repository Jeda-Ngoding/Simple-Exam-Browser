package io.syarifuddinahmads.exambrowser

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.util.Log
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import java.net.URISyntaxException

class WebViewActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        // hide navigation bar
        supportActionBar?.hide()

        // get data from main activity
        val url: String? = intent.getStringExtra("url")

        // initial component
        webView = findViewById(R.id.web_view)

        // check saved instance
        if (savedInstanceState == null) {
            webView.loadUrl(url.toString())
        }

        // setting webview
        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                Toast.makeText(applicationContext, url.toString(), Toast.LENGTH_SHORT).show()
                if (url!!.startsWith("https") || url.startsWith("http")) {
                    return false
                } else {
                    try {
                        val i = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                        val fallbackUrl: String? = i.getStringExtra("browser_fallback_url")
                        Log.v("Fallback URL", fallbackUrl.toString())
                        if (fallbackUrl != null) {
                            view?.loadUrl(fallbackUrl)
                            return true
                        }
                    } catch (e: URISyntaxException) {
                        Log.e("URI Syntax", e.reason)
                    }
                }
                return true
            }
        }

        webView.settings.builtInZoomControls = true
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.databaseEnabled = true
        webView.settings.minimumFontSize = 1
        webView.settings.minimumLogicalFontSize = 1
    }

    override fun onPause() {
        super.onPause()

        val activityManager =
            applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.moveTaskToFront(taskId, 0)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        webView.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        webView.restoreState(savedInstanceState)
    }

    var doubleBackToExitPressed = false
    override fun onBackPressed() {

        // check webview control navigation
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }

        if (doubleBackToExitPressed) {
            super.onBackPressed()
            return
        }

        doubleBackToExitPressed = true
        Toast.makeText(this, "Tab sekali lagi untuk keluar...", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressed = false }, 2000)
    }
}