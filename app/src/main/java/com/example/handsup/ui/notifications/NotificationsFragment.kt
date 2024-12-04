package com.example.handsup.ui.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkBuilder
import com.example.handsup.R
import com.example.handsup.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private val channelId = "exampleChannel"
    private lateinit var notificationManager: NotificationManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Configurar el gestor de notificaciones
        notificationManager = requireContext().getSystemService(NotificationManager::class.java)
        createNotificationChannel()

        // Configurar el botón para generar una notificación
        binding.btnNotify.setOnClickListener {
            showNotification()
        }

        // Recibir el mensaje de notificación y mostrarlo en el TextView
        val message = arguments?.getString("notification_message")
        binding.TextNot.text = message ?: "No hay mensaje" // Texto predeterminado si el mensaje es nulo

        return root
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "HandsUp",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "LSM al alcance de tus manos: Aprende,Comunica, Conecta."
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification() {
        // Obtén el contexto de la aplicación
        val context = requireContext()

        // Configura el Intent para navegar al HomeFragment
        val intent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.mobile_navigation)
            .setDestination(R.id.navigation_home)
            .createPendingIntent()

        // Crea la notificación
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setContentTitle("HandsUp")
            .setContentText("LSM al alcance de tus manos: Aprende,Comunica, Conecta.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(intent) // Asocia el PendingIntent
            .setAutoCancel(true) // Cierra la notificación al hacer clic
            .build()

        // Publica la notificación
        notificationManager.notify(1, notification)
        Toast.makeText(context, "HandsUp", Toast.LENGTH_SHORT).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
