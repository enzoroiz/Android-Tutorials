package com.enzoroiz.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.lang.NumberFormatException

class CalculatorViewModel : ViewModel() {
    private var operand: Double? = null
    private var pendingOperation = ""

    private val result = MutableLiveData<Double>()
    val resultValue: LiveData<String>
        get() = Transformations.map(result) { it.toString() }

    private val newNumber = MutableLiveData<String>()
    val newNumberValue: LiveData<String>
        get () = newNumber

    private val operation = MutableLiveData<String>()
    val operationValue: LiveData<String>
        get() = operation

    fun digitPressed(caption: String) {
        newNumber.value?.let {
            newNumber.value = newNumber.value + caption
        } ?: run { newNumber.value = caption }
    }

    fun operandPressed(operand: String) {
        try {
            newNumber.value?.toDouble()?.let {
                performOperation(it, operand)
            }
        } catch (e: NumberFormatException) {
            newNumber.value = null
        }

        pendingOperation = operand
        operation.value = pendingOperation
    }

    fun negPressed() {
        val value = newNumber.value
        if (value.isNullOrEmpty()) {
            newNumber.value = "-"
        } else {
            try {
                val negativeNumber = value.toDouble() * -1
                newNumber.value = negativeNumber.toString()
            } catch (e: NumberFormatException) {
                newNumber.value = null
            }
        }
    }

    private fun performOperation(value: Double, operation: String) {
        if (operand == null) {
            operand = value
        } else {
            if (pendingOperation == "=") {
                pendingOperation = operation
            }

            when (pendingOperation) {
                "=" -> operand = value
                "*" -> operand = operand!! * value
                "-" -> operand = operand!! - value
                "+" -> operand = operand!! + value
                "/" -> operand = if (value == 0.0) Double.NaN else operand!! / value
            }
        }

        result.value = operand
        newNumber.value = ""
    }
}