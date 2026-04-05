package com.gabrieldev.appcomplect.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class RecordatorioWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        mostrarNotificacion()
        return Result.success()
    }

    private fun mostrarNotificacion() {
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
            .setSmallIcon(android.R.drawable.btn_star)
            .setContentTitle("¡Hora de potenciar tu mente!")
            .setContentText("Avanza un poco más hoy para no perder tu racha.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1001, notification)
    }
}
