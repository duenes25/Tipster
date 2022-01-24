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
    val mBillAmount = 0
    val mTipAmount = 15
    val mNumberOfPeople = 2

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
        // Whenever the button is tapped we will get the tipAmount value,
        // increment it by one, and set it back as the tip amount
        incrementTipButton.setOnClickListener {
             val incrementedTip = Integer.parseInt(tipAmount.text.toString().trim()) + 1
            tipAmount.setText(incrementedTip.toString())
        }

        // Whenever the button is tapped we will get the tipAmount value,
        // decrement it by one, and set it back as the tip amount
        decrementTipButton.setOnClickListener {
            val decrementedTip = Integer.parseInt(tipAmount.text.toString().trim()) - 1
            tipAmount.setText(decrementedTip.toString())
        }

        // Whenever the button is tapped we will get the numberOfPeople value,
        // increment it by one, and set it back as the number of people.
        incrementPeopleButton.setOnClickListener {
            val incrementedPeople = Integer.parseInt(numberOfPeople.text.toString().trim()) + 1
            numberOfPeople.setText(incrementedPeople.toString())
        }

        // Whenever the button is tapped we will get the numberOfPeople value,
        // increment it by one, and set it back as the number of people.
        decrementPeopleButton.setOnClickListener {
            val decrementPeople = Integer.parseInt(numberOfPeople.text.toString().trim()) - 1
            if (decrementPeople > 0){
                numberOfPeople.setText(decrementPeople.toString())
            }
        }

        //Whenever the bill amount is changed, we will recalculate the tip and total
        billAmount.addTextChangedListener { newBill ->
            val bill = getBigDecimalAmount(newBill.toString().trim(), 0)
            val tip = getBigDecimalAmount(tipAmount.text.toString().trim(), 0)
            val people = getBigDecimalAmount(numberOfPeople.text.toString().trim(), 1)
            val tipPerPerson = calculateTipPerPerson(bill, tip, people)
            val totalPerPerson = calculateTotalPerPerson(bill, tip, people)
            tipValue.text = "$tipPerPerson"
            totalValue.text = "$totalPerPerson"
        }

        //Whenever the tip amount is changed, we will recalculate the tip and total
        tipAmount.addTextChangedListener { newTip ->
            val bill = getBigDecimalAmount(billAmount.text.toString().trim(), 0)
            val tip = getBigDecimalAmount(newTip.toString().trim(), 0)
            val people = getBigDecimalAmount(numberOfPeople.text.toString().trim(), 1)
            val tipPerPerson = calculateTipPerPerson(bill, tip, people)
            val totalPerPerson = calculateTotalPerPerson(bill, tip, people)
            tipValue.text = "$tipPerPerson"
            totalValue.text = "$totalPerPerson"
        }

        //Whenever the number of people is changed, we will recalculate the tip and total
        numberOfPeople.addTextChangedListener { newPeopleValue ->
            val bill = getBigDecimalAmount(billAmount.text.toString().trim(), 0)
            val tip = getBigDecimalAmount(tipAmount.text.toString().trim(), 0)
            val people = getBigDecimalAmount(newPeopleValue.toString().trim(), 1)
            val tipPerPerson = calculateTipPerPerson(bill, tip, people)
            val totalPerPerson = calculateTotalPerPerson(bill, tip, people)
            tipValue.text = "$tipPerPerson"
            totalValue.text = "$totalPerPerson"
        }

    }

    /**
     * This method will be used to calculate the Tip per person based on the following input parameters
     * @param [bill] - The Total bill of the party
     * @param [tip] - The amount of tip that the user would like to give
     * @param [people] - The number of people that will split the bill
     */
    private fun calculateTipPerPerson(bill: BigDecimal, tip: BigDecimal, people: BigDecimal): BigDecimal {
        return ((bill * (tip.divide(BigDecimal(100)))) / people).setScale(2, RoundingMode.HALF_EVEN)
    }

    private fun calculateTotalPerPerson(bill: BigDecimal, tip: BigDecimal, people: BigDecimal) : BigDecimal{
        return ((bill * (tip.divide(BigDecimal(100)))) + bill / people).setScale(2, RoundingMode.HALF_EVEN)
    }
    private fun getBigDecimalAmount(stringValue: String?, defaultValue: Int): BigDecimal{
        return if (stringValue?.isNotEmpty() == true){
            stringValue.toBigDecimal()
        } else{
            BigDecimal(defaultValue)
        }
    }

}