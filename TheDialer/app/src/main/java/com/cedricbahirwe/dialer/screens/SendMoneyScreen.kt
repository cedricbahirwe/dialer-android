package com.cedricbahirwe.dialer.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cedricbahirwe.dialer.R

@Preview(showBackground = true)
@Composable
fun FieldsContainer() {
    val focusManager = LocalFocusManager.current
    var amount by remember { mutableStateOf(TextFieldValue("")) }
    var phoneNumber by remember { mutableStateOf(TextFieldValue("")) }
    val isValid = remember(amount, phoneNumber) {
        amount.text.isNotEmpty() && phoneNumber.text.isNotEmpty()
    }
    Box {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            TitleView("Transfer Money")
            if (isValid) {
                Text("Valid Input")
            }
            Column {
                OutlinedTextField(
                    value = amount,
                    onValueChange = { newValue ->
                        amount = newValue
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(stringResource(R.string.common_amount))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    placeholder = { Text(text = "Enter Amount") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = MaterialTheme.colors.primary,
                        focusedBorderColor = Color.Blue
                    ),
                    singleLine = true
                )

                Spacer(Modifier.padding(vertical = 8.dp))
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { newValue ->
                        phoneNumber = newValue
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Phone Number")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                    placeholder = { Text(text = "Enter Receiver's number") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = MaterialTheme.colors.primary,
                        focusedBorderColor = Color.Blue
                    ),
                    singleLine = true
                )
            }

            val btnElevation = ButtonDefaults.elevation(
                defaultElevation = 20.dp,
                pressedElevation = 15.dp,
                disabledElevation = 0.dp,
                hoveredElevation = 15.dp,
                focusedElevation = 10.dp
            )

            Column(Modifier.padding(vertical = 16.dp)) {
                Button(
                    onClick = {},
                    Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = Color.Blue
                    ),
                    elevation = btnElevation,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        Icons.Rounded.Person,
                        contentDescription = "Pick Contact icon"
                    )
                    Text(
                        stringResource(R.string.pick_contact_text),
                        Modifier.padding(start = 10.dp)
                    )
                }

                Spacer(Modifier.padding(10.dp))

                Button(
                    onClick = {},
                    Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Blue,
                        contentColor = Color.White
                    ),
                    elevation = btnElevation,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(stringResource(R.string.dial_ussd_text), Modifier.padding(start = 1.dp))
                }
            }

            Spacer(modifier = Modifier.weight(1.0f))
        }
    }
}