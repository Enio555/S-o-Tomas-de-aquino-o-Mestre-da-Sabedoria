# Preservar métodos expostos à WebView via @JavascriptInterface
-keepattributes *Annotation*
-keepattributes JavascriptInterface
-keepclassmembers class br.org.sabedoria.saotomasdeaquino.MainActivity$AndroidBridge {
    @android.webkit.JavascriptInterface <methods>;
}
