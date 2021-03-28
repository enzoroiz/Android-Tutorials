package com.enzoroiz.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.Observer

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

//    private val viewModel: CalculatorViewModel by viewModels()
    private val viewModel: CalculatorBigDecimalViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.resultValue.observe(this, Observer { result.setText(it) })
        viewModel.newNumberValue.observe(this, Observer { newNumber.setText(it) })
        viewModel.operationValue.observe(this, Observer { operation.text = it })

        val numberListener = View.OnClickListener { view ->
            val digit = (view as Button).text.toString()
            viewModel.digitPressed(digit)
        }

        val operationsListener = View.OnClickListener { view ->
            val operand = (view as Button).text.toString()
            viewModel.operandPressed(operand)
        }

        button0.setOnClickListener(numberListener)
        button1.setOnClickListener(numberListener)
        button2.setOnClickListener(numberListener)
        button3.setOnClickListener(numberListener)
        button4.setOnClickListener(numberListener)
        button5.setOnClickListener(numberListener)
        button6.setOnClickListener(numberListener)
        button7.setOnClickListener(numberListener)
        button8.setOnClickListener(numberListener)
        button9.setOnClickListener(numberListener)
        buttonDot.setOnClickListener(numberListener)

        buttonEquals.setOnClickListener(operationsListener)
        buttonDivide.setOnClickListener(operationsListener)
        buttonMultiply.setOnClickListener(operationsListener)
        buttonMinus.setOnClickListener(operationsListener)
        buttonPlus.setOnClickListener(operationsListener)

        buttonNeg.setOnClickListener {
            viewModel.negPressed()
        }
    }
}
