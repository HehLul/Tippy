package com.amdsalman.tippy

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.amdsalman.tippy.ui.theme.TippyTheme


private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15
class MainActivity : ComponentActivity() {
    private lateinit var editTxtBaseAmount:EditText
    private lateinit var seekBarTip:SeekBar
    private lateinit var tvTipPercentLabel:TextView
    private lateinit var tvTipAmount:TextView
    private lateinit var tvTotalAmount:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)
        editTxtBaseAmount = findViewById(R.id.editTxt_BaseAmount)
        seekBarTip = findViewById(R.id.seekBarTip)
        tvTipPercentLabel = findViewById(R.id.tvTipPercentLabel)
        tvTipAmount = findViewById(R.id. tvTipAmount)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)

        seekBarTip.progress = INITIAL_TIP_PERCENT
        tvTipPercentLabel.text = "$INITIAL_TIP_PERCENT%"

        seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "onProgressChanged $progress")
                tvTipPercentLabel.text = "$progress%"
                computeTipAndTotal()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        editTxtBaseAmount.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "afterTextChanged $s")
                computeTipAndTotal()
            }
        })



    }

    private fun computeTipAndTotal() {
        if(editTxtBaseAmount.text.isEmpty()){
            tvTotalAmount.text = ""
            tvTipAmount.text = ""
            return
        }
        //Get value of base and tip
        val baseAmount = editTxtBaseAmount.text.toString().toDouble()
        val tipPercent = seekBarTip.progress
        //Compute tip and total
        val tipAmount = baseAmount*tipPercent/100
        val totalAmount = baseAmount + tipAmount
        //update UI
        tvTipAmount.text = "%.2f".format(tipAmount)
        tvTotalAmount.text = "%.2f".format(totalAmount)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Lmao the $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TippyTheme {
        Greeting("Android")
    }
}