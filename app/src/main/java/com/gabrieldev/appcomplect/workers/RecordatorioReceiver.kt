package com.gabrieldev.appcomplect.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class RecordatorioReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        mostrarNotificacion(context)
    }

    private fun mostrarNotificacion(context: Context) {
        val channelId = "recordatorios_complet"
        val notificationManager = 
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Recordatorio Diario",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notificaciones para acceder y mantener tu racha de aprendizaje."
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info) 
            .setContentTitle("¡Hora de potenciar tu mente!")
            .setContentText("Avanza un poco más hoy para no perder tu racha.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1001, notification)
    }
}
