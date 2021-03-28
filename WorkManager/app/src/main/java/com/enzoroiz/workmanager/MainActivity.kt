package com.enzoroiz.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStartOneTimeWorkRequest.setOnClickListener {
//            setUpOneTimeWorkRequest()
            setUpPeriodicWorkRequest()
        }
    }

    private fun setUpOneTimeWorkRequest() {
        val inputData = workDataOf(UPLOAD_COUNTER_KEY to UPLOAD_COUNTER)
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val compressWorkRequest = OneTimeWorkRequest.Builder(CompressWorker::class.java).build()
        val filterWorkRequest = OneTimeWorkRequest.Builder(FilterWorker::class.java).build()
        val uploadWorkRequest = OneTimeWorkRequest.Builder(UploadWorker::class.java)
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(this).apply {
            // Chained
//            beginWith(filterWorkRequest)
//                .then(compressWorkRequest)
//                .then(uploadWorkRequest)
//                .enqueue()

            // Parallel compress + filter
            beginWith(listOf(compressWorkRequest, filterWorkRequest))
                .then(uploadWorkRequest)
                .enqueue()

            getWorkInfoByIdLiveData(uploadWorkRequest.id).observe(this@MainActivity, Observer {
                txtWorkStatus.text = it.state.name
                if (it.state == WorkInfo.State.SUCCEEDED) {
                    txtWorkResult.text = it.outputData.getString(UploadWorker.RESULT_MESSAGE_KEY)
                }
            })
        }
    }

    private fun setUpPeriodicWorkRequest() {
        val periodicWorkRequest = PeriodicWorkRequest.Builder(FilterWorker::class.java, 20L, TimeUnit.MINUTES).build()
        WorkManager.getInstance(this).apply {
            enqueue(periodicWorkRequest)
            getWorkInfoByIdLiveData(periodicWorkRequest.id).observe(this@MainActivity, Observer {
                txtWorkStatus.text = it.state.name
            })
        }
    }

    companion object {
        const val UPLOAD_COUNTER_KEY = "upload_counter"
        const val UPLOAD_COUNTER = 600
    }
}