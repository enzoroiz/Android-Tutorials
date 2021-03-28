package com.enzoroiz.android_tutorials

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"
private const val TEXT_VIEW_CONTENT = "TextViewContent"

class MainActivity : AppCompatActivity() {
    private var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userInput = findViewById<EditText>(R.id.editText)
        val button = findViewById<Button>(R.id.button)
        textView = findViewById(R.id.textView)
        textView?.text = ""
        textView?.movementMethod = ScrollingMovementMethod()

        button?.setOnClickListener {
            textView?.append("${userInput.text}\n")
            userInput.setText("")
        }
    }

    override fun onStart() {
        Log.d(TAG,"onStart: called")
        super.onStart()
    }

    // Not always called
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.d(TAG,"onRestoreInstanceState: called")
        super.onRestoreInstanceState(savedInstanceState)
        textView?.text = savedInstanceState.getString(TEXT_VIEW_CONTENT, "")
    }

    override fun onResume() {
        Log.d(TAG,"onResume: called")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG,"onPause: called")
        super.onPause()
    }

    // Not always called
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        Log.d(TAG,"onSaveInstanceState: called")
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putString(TEXT_VIEW_CONTENT, textView?.text.toString())
    }

    // Recommended place to put code to save your data
    override fun onStop() {
        Log.d(TAG,"onStop: called")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d(TAG,"onDestroy: called")
        super.onDestroy()
    }
}
