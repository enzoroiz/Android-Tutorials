package com.enzoroiz.notification

import android.app.Notification
import android.app.NotificationManager
import android.app.RemoteInput
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_remote_input.*

class RemoteInputActivity : AppCompatActivity() {
    private var notificationManager: NotificationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote_input)

        notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        getRemoteInput()
    }

    private fun getRemoteInput() {
        RemoteInput.getResultsFromIntent(this.intent)?.let { bundle ->
            txtRemoteInput.text = bundle.getCharSequence(INPUT_REMOTE_KEY).toString()
            updateNotification()
        }
    }

    private fun updateNotification() {
        val notification = Notification.Builder(this, CHANNEL_ID)
            .setContentText(getString(R.string.app_notification_remote_input_received_text))
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()

        notificationManager?.notify(NOTIFICATION_ID, notification)
    }
}