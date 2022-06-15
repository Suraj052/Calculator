package com.example.calciolator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat

class MainActivity : AppCompatActivity() {
    private lateinit var result: EditText
    private lateinit var newNumber: EditText
    private val displayOperation by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.operation) }

    // Variables to hold the operands and type of calculation
    private var operand1: Double? = null
    private var operand2: Double = 0.0
    private var pendingOperation = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView)

        result = findViewById(R.id.e1)
        newNumber = findViewById(R.id.e2)

        // Data input buttons
        val button0: TextView = findViewById(R.id.zero)
        val button1: TextView = findViewById(R.id.one)
        val button2: TextView = findViewById(R.id.two)
        val button3: TextView = findViewById(R.id.three)
        val button4: TextView = findViewById(R.id.four)
        val button5: TextView = findViewById(R.id.five)
        val button6: TextView = findViewById(R.id.six)
        val button7: TextView = findViewById(R.id.seven)
        val button8: TextView = findViewById(R.id.eight)
        val button9: TextView = findViewById(R.id.nine)
        val buttonDot: TextView = findViewById(R.id.dot)

        // Operation buttons
        val buttonEquals = findViewById<TextView>(R.id.equal)
        val buttonDivide = findViewById<TextView>(R.id.divide)
        val buttonMultiply = findViewById<TextView>(R.id.multiply)
        val buttonMinus = findViewById<TextView>(R.id.minus)
        val buttonPlus = findViewById<TextView>(R.id.add)

        val listener = View.OnClickListener { v ->
            val b = v as TextView
            newNumber.append(b.text)
        }

        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        buttonDot.setOnClickListener(listener)

        val opListener = View.OnClickListener { v ->
            val op = (v as TextView).text.toString()
            val value = newNumber.text.toString()
            if (value.isNotEmpty()) {
                performOperation(value, op)
            }
            pendingOperation = op
            displayOperation.text = pendingOperation
        }
        buttonEquals.setOnClickListener(opListener)
        buttonDivide.setOnClickListener(opListener)
        buttonMultiply.setOnClickListener(opListener)
        buttonMinus.setOnClickListener(opListener)
        buttonPlus.setOnClickListener(opListener)

    }

    private fun performOperation(value: String, operation: String) {
        if (operand1 == null) {
            operand1 = value.toDouble()
        } else {
            operand2 = value.toDouble()

            if (pendingOperation == "=") {
                pendingOperation = operation
            }

            when (pendingOperation) {
                "=" -> operand1 = operand2
                "/" -> if (operand2 == 0.0) {
                    operand1 = Double.NaN   // handle attempt to divide by zero
                } else {
                    operand1 = operand1!! / operand2
                }
                "*" -> operand1 = operand1!! * operand2
                "-" -> operand1 = operand1!! - operand2
                "+" -> operand1 = operand1!! + operand2
            }
        }
        result.setText(operand1.toString())
        newNumber.setText("")
    }
}