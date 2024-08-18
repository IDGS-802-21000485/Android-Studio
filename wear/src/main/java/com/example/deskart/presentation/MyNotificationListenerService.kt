package com.example.deskart.presentation
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.app.Notification

class MyNotificationListenerService : NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        // Manejar la notificación recibida
        val notificationText = sbn.notification.extras.getCharSequence(Notification.EXTRA_TEXT)?.toString()
        // Realiza alguna acción con la notificación, como mostrar un mensaje en pantalla
    }
}
