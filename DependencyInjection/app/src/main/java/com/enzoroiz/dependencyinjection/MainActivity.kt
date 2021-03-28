package com.enzoroiz.dependencyinjection

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var smartPhone: SmartPhone

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SmartPhoneInjector.init()
        SmartPhoneInjector.component.inject(this)

        smartPhone.getBatteryLevel()
        smartPhone.makeCall()
    }
}