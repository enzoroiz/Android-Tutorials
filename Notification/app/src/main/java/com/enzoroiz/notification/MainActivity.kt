package com.enzoroiz.notification

import android.app.*
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

private const val NOTIFICATION_ACTIVITY_REQUEST_CODE = 100
private const val NO_ICON = 0
const val CHANNEL_ID = "com.enzoroiz.notification.channel"
const val CHANNEL_NAME = "Default Notification Channel"
const val CHANNEL_DESCRIPTION = "Channel used to display notifications for this app"
const val NOTIFICATION_ID = 1
const val INPUT_REMOTE_KEY = "com.enzoroiz.notification.channel.input_remote_key"

class MainActivity : AppCompatActivity() {
    private var notificationManager: NotificationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpNotification()
        btnShowNotification.setOnClickListener { showNotification() }
    }

    private fun setUpNotification() {
        val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, IMPORTANCE_HIGH).apply {
            description = CHANNEL_DESCRIPTION
        }

        notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager?.createNotificationChannel(notificationChannel)
    }

    private fun showNotification() {
        val detailsActivityAction = getAction(R.string.app_notification_details_button_text, DetailsActivity::class.java)
        val settingsActivityAction = getAction(R.string.app_notification_settings_button_text, SettingsActivity::class.java)
        val remoteInputActivityAction = getAction(R.string.app_notification_remote_input_button_text, RemoteInputActivity::class.java, true)

        val notification = Notification.Builder(this, CHANNEL_ID)
//                .setContentIntent(getPendingIntent(MainActivity::class.java))
                .setContentTitle(getString(R.string.app_notification_title))
                .setContentText(getString(R.string.app_notification_text))
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .addAction(detailsActivityAction)
                .addAction(settingsActivityAction)
                .addAction(remoteInputActivityAction)
                .setAutoCancel(true)
                .build()

        notificationManager?.notify(NOTIFICATION_ID, notification)
    }

    private fun <T> getPendingIntent(intentClass: Class<T>): PendingIntent {
        return PendingIntent.getActivity(
                this,
                NOTIFICATION_ACTIVITY_REQUEST_CODE,
                Intent(this, intentClass),
                PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun <T> getAction(@StringRes title: Int, intentClass: Class<T>, isRemoteInput: Boolean = false): Notification.Action {
        val remoteInput = RemoteInput.Builder(INPUT_REMOTE_KEY).setLabel(getString(title)).build()
        return Notification.Action.Builder(
                Icon.createWithResource(this, NO_ICON),
                getString(title),
                getPendingIntent(intentClass)
            ).apply {
                if (isRemoteInput) addRemoteInput(remoteInput)
            }.build()
    }
}