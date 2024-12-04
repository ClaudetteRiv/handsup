package com.example.handsup

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

class VideoActivity : AppCompatActivity() {

    // Cancelamos alguna advertencia de JavaScript
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        // Inicializamos el botón
        val btn = findViewById<Button>(R.id.btnLessonCompleted)
        btn.setOnClickListener {
            btn.isEnabled = false  // Desactiva el botón temporalmente

            val navController = findNavController(R.id.nav_host_fragment_activity_main)
            navController.navigate(R.id.navigation_home)

            // Reactiva el botón después de la navegación
            btn.postDelayed({ btn.isEnabled = true }, 1000) // Espera un tiempo para reactivar el botón
        }


        // Obtén el valor recibido del Intent
        val receivedValue = intent.getStringExtra("lesson_title")
        Toast.makeText(this, "Recibido: $receivedValue", Toast.LENGTH_SHORT).show()

        // Define el mapa de lecciones y URLs
        val videoMap = mapOf(
            "Abecedario" to "https://www.youtube.com/embed/vXSSgzXJy1Q",
            "Números" to "https://www.youtube.com/embed/RsBYf7MN1N0",
            "Colores" to "https://www.youtube.com/embed/U7m4daxkSBQ",
            "Frutas" to "https://www.youtube.com/embed/OTu9JgsrZ80",
            "Animales" to "https://www.youtube.com/embed/0XPEfoqRnXo"
        )

        // Encuentra la URL basada en el título recibido
        val videoUrl = videoMap[receivedValue]

        if (videoUrl == null) {
            Toast.makeText(this, "Video no encontrado para: $receivedValue", Toast.LENGTH_SHORT).show()
            return
        }

        // Configura el WebView
        val webView: WebView = findViewById(R.id.video)
        val settings: WebSettings = webView.settings
        settings.javaScriptEnabled = true  // Habilitar JavaScript
        webView.webViewClient = WebViewClient() // Prevenir abrir en el navegador externo

        // HTML dinámico para el video
        val videoHtml = """
            <html>
                <body style="margin:0;padding:0;">
                    <iframe width="100%" height="100%" 
                        src="$videoUrl" 
                        frameborder="0" 
                        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" 
                        allowfullscreen>
                    </iframe>
                </body>
            </html>
        """

        // Carga el HTML en el WebView
        webView.loadData(videoHtml, "text/html", "utf-8")
    }
}
