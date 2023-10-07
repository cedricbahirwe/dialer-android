package com.cedricbahirwe.dialer.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cedricbahirwe.dialer.R
import com.cedricbahirwe.dialer.model.PurchaseDetailModel
import com.cedricbahirwe.dialer.ui.theme.AccentBlue
import com.cedricbahirwe.dialer.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun PurchaseDetailView(
    viewModel: MainViewModel = viewModel()
) {
    val editedField = remember {
        mutableStateOf(EditedField.AMOUNT)
    }

    val purchaseDetail = remember {
        mutableStateOf(PurchaseDetailModel(0))
    }

    val hasValidAmount = remember(purchaseDetail.value) {
        purchaseDetail.value.amount >= 1
    }

    val codePin = remember {
        mutableStateOf("")
    }

    val isPinCodeValid = remember(codePin.value) {
        codePin.value.length == 5
    }

    val pinInput = remember {
        mutableStateOf("")
    }

    val amountInput = remember {
        mutableStateOf("")
    }

    val coroutineScope = rememberCoroutineScope()

    fun handleNextInput(value: String) {
        when (editedField.value) {
            EditedField.AMOUNT -> {
                purchaseDetail.value = purchaseDetail.value.copy(amount = value.toIntOrNull() ?: 0)
            }
            EditedField.PIN-> {
                codePin.value = value.take(5)
            }
        }
    }

    fun showDeleteBtn() : Boolean {
        return when (editedField.value) {
            EditedField.AMOUNT -> {
                purchaseDetail.value.amount > 0
            }

            EditedField.PIN-> {
                codePin.value.isNotEmpty()
            }
        }
    }

    fun handleNewKey(value: String) {
        val input = if (editedField.value == EditedField.PIN) pinInput else amountInput
        
        if (value == "X") {
            if (input.value.isNotEmpty())
                input.value = input.value.dropLast(1)
        } else {
            input.value += value
        }

        println("Sending ${input.value}")
        handleNextInput(input.value)
    }


    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(50.dp, 5.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.Gray)
        )

        Spacer(Modifier.padding(bottom = 5.dp))

        Column {
            val fieldBorderGradient = remember {
                Brush.linearGradient(
                    colors = listOf(Color.Green, Color.Blue)
                )
            }
            
            Text(
                if (hasValidAmount) purchaseDetail.value.amount.toString()
                else stringResource(R.string.enter_amount),
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.primary.copy(
                    alpha = if (hasValidAmount) 1f else 0.5f
                ),
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .border(
                        BorderStroke(
                            (if (editedField.value == EditedField.AMOUNT) 1.dp else Dp.Unspecified),
                            fieldBorderGradient
                        ),
                        RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colors.primary.copy(0.06f))
                    .background(Color.Green.copy(alpha = if (editedField.value == EditedField.AMOUNT) 0.04f else 0f))
                    .wrapContentHeight(Alignment.CenterVertically)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {
                            coroutineScope.launch {
                                editedField.value = EditedField.AMOUNT
                            }
                        }
                    )
                )

            Spacer(Modifier.padding(vertical = 10.dp))

            Box(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .border(
                        BorderStroke(
                            (if (editedField.value == EditedField.PIN) 1.dp else Dp.Unspecified),
                            fieldBorderGradient
                        ),
                        RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colors.primary.copy(0.06f))
                    .background(Color.Green.copy(alpha = if (editedField.value == EditedField.PIN) 0.04f else 0f))
            ) {
                Text(
                    codePin.value.ifEmpty { stringResource(R.string.enter_pin) },
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxSize()
//                        .height(40.dp)
//                        .border(
//                            BorderStroke(
//                                (if (editedField.value == EditedField.PIN) 1.dp else Dp.Unspecified),
//                                fieldBorderGradient
//                            ),
//                            RoundedCornerShape(8.dp)
//                        )
//                        .clip(RoundedCornerShape(8.dp))
//                        .background(MaterialTheme.colors.primary.copy(0.06f))
//                        .background(Color.Green.copy(alpha = if (editedField.value == EditedField.PIN) 0.04f else 0f))
                        .wrapContentHeight(Alignment.CenterVertically)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                coroutineScope.launch {
                                    editedField.value = EditedField.PIN
                                }
                            }
                        )
                )

                Button(
                    onClick = {

                    },
                    Modifier
                        .height(38.dp)
                        .align(Alignment.CenterEnd),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.onPrimary
                    ),
//                    elevation = btnElevation,
                    shape = RoundedCornerShape(8.dp),
                    enabled = isPinCodeValid
                ) {
                    Text(stringResource(R.string.common_save), Modifier.padding(start = 1.dp))
                }
            }

            Text(
                text = "Your pin will not be saved unless you save it.",
                color = Color.Red,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }

        val btnElevation = ButtonDefaults.elevation(
            defaultElevation = 20.dp,
            pressedElevation = 15.dp,
            disabledElevation = 0.dp,
            hoveredElevation = 15.dp,
            focusedElevation = 10.dp
        )

        Button(
            onClick = {
                viewModel.confirmPurchase()
            },
            Modifier
                .fillMaxWidth()
                .height(45.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = AccentBlue,
                contentColor = Color.White
            ),
            elevation = btnElevation,
            shape = RoundedCornerShape(8.dp),
            enabled = hasValidAmount
        ) {
            Text(stringResource(R.string.common_confirm))
        }

        Spacer(Modifier.padding(4.dp))

        PinView(
            showDeleteBtn = showDeleteBtn()
        ) {
            handleNewKey(it)
        }
    }
}

private enum class EditedField {
    AMOUNT, PIN
}