package com.example.tipster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    private var mBillAmount: Double = 0.00
    private var mTipAmount : Int = 15
    private var mNumberOfPeople : Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get a handle on the incrementTipButton
        val incrementTipButton = findViewById<Button>(R.id.increment_tip_button)
        // Get a handle on the decrementTipButton
        val decrementTipButton = findViewById<Button>(R.id.decrement_tip_button)
        // Get a handle on the incrementPeopleButton
        val incrementPeopleButton = findViewById<Button>(R.id.increment_people_button)
        // Get a handle on the decrementPeopleButton
        val decrementPeopleButton = findViewById<Button>(R.id.decrement_people_button)
        // Get a handle on the bill TextInput Field
        val billAmount = findViewById<TextInputEditText>(R.id.bill_edit_text)
        // Get a handle on the tipAmount TextInput Field
        val tipAmount = findViewById<TextInputEditText>(R.id.tip_amount)
        // Get a handle on the numberOfPeople TextInput Field
        val numberOfPeople = findViewById<TextInputEditText>(R.id.number_of_people)
        // Get a handle on the tipValue TextView Field
        val tipValue = findViewById<TextView>(R.id.tip_value)
        // Get a handle on the totalValue TextView Field
        val totalValue = findViewById<TextView>(R.id.total_value)

        // Whenever the button is tapped we will get the [mTipAmount] value,
        // increment it by one, and set it back as the tip amount String
        incrementTipButton.setOnClickListener {
            mTipAmount ++
            tipAmount.setText(mTipAmount.toString())
        }

        // Whenever the button is tapped we will get the [mTipAmount] value,
        // decrement it by one, and set it back as the tip amount String
        decrementTipButton.setOnClickListener {
            mTipAmount --
            tipAmount.setText(mTipAmount.toString())
        }

        // Whenever the button is tapped we will get the [mNumberOfPeople] value,
        // increment it by one, and set it back as the number of people String
        incrementPeopleButton.setOnClickListener {
            mNumberOfPeople ++
            numberOfPeople.setText(mNumberOfPeople.toString())
        }

        // Whenever the button is tapped we will get the [mNumberOfPeople] value,
        // increment it by one, and set it back as the number of people String
        decrementPeopleButton.setOnClickListener {
            mNumberOfPeople --
            //We don't want to divide by zero,
            //so catching any people less than 1, and resetting to 1
            if (mNumberOfPeople < 1){
                mNumberOfPeople = 1
            }
            numberOfPeople.setText(mNumberOfPeople.toString())
        }

        //Whenever the bill amount is changed, we will store it, and recalculate the tip and total
        billAmount.addTextChangedListener { newBill ->
            // Get the new value
            val newBillDouble = newBill.toString().trim().toDoubleOrNull()
            // Check to make sure it is not null
            if(newBillDouble != null){
                // It was not null, store it
                mBillAmount = newBillDouble
            }
            else{
                // It was null, display previous value
                billAmount.setText(mBillAmount.toString())
            }
            //Set the calculated values to display
            tipValue.text = "${calculateTipPerPerson()}"
            totalValue.text = "${calculateTotalPerPerson()}"
        }

        //Whenever the tip amount is changed, we will store it, and recalculate the tip and total
        tipAmount.addTextChangedListener { newTip ->
            // Get the new value
            val newTipInteger = newTip.toString().trim().toIntOrNull()
            // Check to make sure it is not null
            if(newTipInteger != null){
                // It was not null, store it
                mTipAmount = newTipInteger
            }
            else{
                // It was null, display previous value
                tipAmount.setText(mTipAmount.toString())
            }
            //Set the calculated values to display
            tipValue.text = "${calculateTipPerPerson()}"
            totalValue.text = "${calculateTotalPerPerson()}"
        }

        //Whenever the number of people is changed, we will store it, and recalculate the tip and total
        numberOfPeople.addTextChangedListener { newPeopleValue ->
            // Get the new value
            val newPeopleInteger = newPeopleValue.toString().trim().toIntOrNull()
            // Check to make sure it is not null
            if(newPeopleInteger != null){
                // It was not null, store it
                mNumberOfPeople = newPeopleInteger
            }
            else{
                // It was null, display previous value
                numberOfPeople.setText(mNumberOfPeople.toString())
            }
            //Set the calculated values to display
            tipValue.text = "${calculateTipPerPerson()}"
            totalValue.text = "${calculateTotalPerPerson()}"
        }

    }

    /**
     * This method will be used to calculate the Tip per person
     */
    private fun calculateTipPerPerson(): BigDecimal {
        return ((mBillAmount.toBigDecimal() * (mTipAmount.toBigDecimal().divide(BigDecimal(100)))) / mNumberOfPeople.toBigDecimal()).setScale(2, RoundingMode.HALF_EVEN)
    }

    /**
     * This method will be used to calculate the Tip per person
     */
    private fun calculateTotalPerPerson() : BigDecimal{
        return ((mBillAmount.toBigDecimal() * (mTipAmount.toBigDecimal().divide(BigDecimal(100)))) + mBillAmount.toBigDecimal() / mNumberOfPeople.toBigDecimal()).setScale(2, RoundingMode.HALF_EVEN)
    }

}