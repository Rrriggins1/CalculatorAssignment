package com.example.calculatorassignmetn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.calculatorassignmetn.ui.theme.CalculatorAssignmetnTheme
import java.text.NumberFormat
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorAssignmetnTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Calculator()
                }
            }
        }
    }
}

@Composable
fun Calculator(modifier: Modifier = Modifier) {
    val loanInput by remember {mutableStateOf("")}
    val irInput by remember {mutableStateOf("")}
    val noyInput by remember {mutableStateOf("")}

    val loan = loanInput.toInt()
    val ir = irInput.toDoubleOrNull() ?: 0.0
    val noy = noyInput.toInt()

    val payment = calculateMortgage(loan, ir, noy)

    Column(modifier = Modifier.padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(R.string.app_name),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(alignment = Alignment.Start)
        )
        LoanNumberField(modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth())
        IRNumberField(modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth())
        NOYNumberField(modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth())
        Text(
            text = stringResource(R.string.monthly_payment, payment),
            modifier = Modifier

        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanNumberField(modifier: Modifier = Modifier) {
    var loanInput by remember {mutableStateOf("")}

    TextField(
        value = loanInput,
        onValueChange = {loanInput = it},
        label = { Text(stringResource(R.string.loan_amount))},
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IRNumberField(modifier: Modifier = Modifier) {
    var irInput by remember {mutableStateOf("")}
    TextField(
        value = irInput,
        onValueChange = {irInput = it},
        label = { Text(stringResource(R.string.interest_rate))},
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NOYNumberField(modifier: Modifier = Modifier) {
    var noyInput by remember {mutableStateOf("")}
    TextField(
        value = noyInput,
        onValueChange = {noyInput = it},
        label = { Text(stringResource(R.string.noy))},
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
}
private fun calculateMortgage(p: Int, r: Double, n: Int): String {
    var mortgage = p * ( (r*(1.0 + r).pow(n)) / ((1.0 + r).pow(n) - 1) )
    if (mortgage.isNaN() || mortgage.isInfinite()) mortgage = 0.0
    return NumberFormat.getCurrencyInstance().format(mortgage)
}


@Preview(showBackground = true)
@Composable
fun CalcPreview() {
    CalculatorAssignmetnTheme {
        Calculator()
    }
}