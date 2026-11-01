package br.org.sabedoria.saotomasdeaquino

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

/**
 * SÃO TOMÁS DE AQUINO – O MESTRE DA SABEDORIA
 * Activity principal que hospeda a interface medieval responsiva e conecta:
 * 1. Motor nativo Android TextToSpeech configurado para Português do Brasil (pt-BR)
 *    com timbre grave, maduro, sereno e acolhedor (pitch = 0.80f).
 * 2. Compartilhamento nativo de frases e reflexões via Intent.ACTION_SEND.
 * 3. Armazenamento persistente (DOM Storage) para funcionamento 100% offline.
 */
class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var webView: WebView
    private var tts: TextToSpeech? = null
    private var isTtsReady: Boolean = false

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.parseColor("#160F09")
        window.navigationBarColor = Color.parseColor("#160F09")
        setContentView(R.layout.activity_main)

        // Inicializar motor de voz nativo Android
        tts = TextToSpeech(this, this)

        webView = findViewById(R.id.main_webview)
        webView.setBackgroundColor(Color.parseColor("#160F09"))

        with(webView.settings) {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            allowFileAccess = true
            mediaPlaybackRequiresUserGesture = false
            cacheMode = WebSettings.LOAD_DEFAULT
            mixedContentMode = WebSettings.MIXED_CONTENT_NEVER_ALLOW
        }

        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()
        webView.addJavascriptInterface(AndroidBridge(), "AndroidBridge")

        // Carrega a aplicação 100% empacotada offline
        webView.loadUrl("file:///android_asset/www/index.html")

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    finish()
                }
            }
        })
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val ptBr = Locale("pt", "BR")
            val result = tts?.setLanguage(ptBr)
            isTtsReady = (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED)

            // Selecionar voz masculina em Português Brasileiro quando disponível no motor Google TTS
            try {
                val availableVoices: Set<Voice>? = tts?.voices
                val preferredMale = availableVoices?.firstOrNull { v ->
                    v.locale.language == "pt" &&
                    (v.name.lowercase().contains("pt-br-x-ptd") ||
                     v.name.lowercase().contains("male") ||
                     v.name.lowercase().contains("daniel") ||
                     v.name.lowercase().contains("antonio"))
                }
                if (preferredMale != null) {
                    tts?.voice = preferredMale
                }
            } catch (_: Exception) {
                // Mantém a voz padrão pt-BR com ajuste de pitch grave
            }

            // Timbre masculino maduro, grave, tranquilo e acolhedor
            tts?.setPitch(0.80f)
            tts?.setSpeechRate(0.92f)
        }
    }

    inner class AndroidBridge {

        @JavascriptInterface
        fun speakPtBr(text: String, rate: Float, pitch: Float) {
            if (!isTtsReady) return
            tts?.setSpeechRate(rate.coerceIn(0.6f, 1.4f))
            tts?.setPitch(pitch.coerceIn(0.65f, 1.1f))
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "SAO_TOMAS_SPEECH")
        }

        @JavascriptInterface
        fun stopSpeaking() {
            tts?.stop()
        }

        @JavascriptInterface
        fun shareText(content: String) {
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "São Tomás de Aquino – O Mestre da Sabedoria")
                putExtra(Intent.EXTRA_TEXT, content)
            }
            val chooser = Intent.createChooser(sendIntent, "Compartilhar Sabedoria de São Tomás")
            startActivity(chooser)
        }
    }

    override fun onDestroy() {
        tts?.stop()
        tts?.shutdown()
        super.onDestroy()
    }
}
