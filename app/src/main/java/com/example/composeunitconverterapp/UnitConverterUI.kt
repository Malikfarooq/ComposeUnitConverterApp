package com.example.composeunitconverterapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UnitConverterUI() {
    var inputValue by remember { mutableStateOf("") }
    var outPutValue by remember { mutableStateOf("") }

    var inputUnit by remember { mutableStateOf("Meter") }
    var outPutUnit by remember { mutableStateOf("Meter") }

    var isInputExpanded by remember { mutableStateOf(false) }
    var isOutPutExpanded by remember { mutableStateOf(false) }

    var inputFactorConversion by remember { mutableStateOf(1.0) }
    var outPutFactorConversion by remember { mutableStateOf(1.0) }


    fun convertUnits() {
        val input = inputValue.toDoubleOrNull() ?: 0.0
        val result = ((input * inputFactorConversion / outPutFactorConversion) * 100).roundToInt() / 100.0
        outPutValue = result.toString()
    }




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = "Unit Converter",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.padding(16.dp))

        OutlinedTextField(
            value = inputValue,
            onValueChange = { inputValue = it },
            label = { Text("Enter Value") }, modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            dropDownButton(
                label = inputUnit,
                expended =isInputExpanded,
                onExpandedChange = { isInputExpanded = it },
                onSelectedOptionChange = { unit, factor ->
                    inputUnit = unit
                    inputFactorConversion = factor
                    convertUnits()
                }

            )

            Spacer(modifier = Modifier.padding(16.dp))

            dropDownButton(
                label = outPutUnit,
                expended =isOutPutExpanded,
                onExpandedChange = { isOutPutExpanded = it },
                onSelectedOptionChange = { unit, factor ->
                    outPutUnit = unit
                    outPutFactorConversion = factor
                    convertUnits()
                }

            )


        }
        Spacer(modifier = Modifier.padding(30.dp))

        Text(
            text = "Result $outPutValue $outPutUnit"
            , style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

    }

}


@Composable
fun dropDownButton(
    label: String,
    expended: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onSelectedOptionChange: (String, Double) -> Unit
) {
    Box(modifier = Modifier.wrapContentSize()) {
        Button(
            onClick = {
                onExpandedChange(!expended) // Toggle expansion
            }
        ) {
            Text(text = label)
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier.rotate(if (expended) 180f else 0f)
            )
        }

        DropdownMenu(
            expanded = expended,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            listOf(
                "Centimeters" to 0.01,
                "Meters" to 0.1,
                "Feet" to 0.3048,
                "Millimeter" to 0.001
            ).forEach { (unit, factor) ->
                DropdownMenuItem(
                    text = { Text(unit) },
                    onClick = {
                        onSelectedOptionChange(unit, factor)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
}
