package com.cedricbahirwe.dialer.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cedricbahirwe.dialer.R
import com.cedricbahirwe.dialer.data.repository.AppSettingsRepository
import com.cedricbahirwe.dialer.ui.theme.AccentBlue
import com.cedricbahirwe.dialer.viewmodel.EditedField
import com.cedricbahirwe.dialer.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun PurchaseDetailView(
    viewModel: MainViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current
    val settings = AppSettingsRepository(context)

    val codePin = settings.getCodePin.collectAsState(initial = null)

    val coroutineScope = rememberCoroutineScope()

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
                if (viewModel.hasValidAmount) uiState.amount.toString()
                else stringResource(R.string.enter_amount),
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.primary.copy(
                    alpha = if (viewModel.hasValidAmount) 1f else 0.5f
                ),
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .border(
                        BorderStroke(
                            (if (uiState.editedField == EditedField.AMOUNT) 1.dp else Dp.Unspecified),
                            fieldBorderGradient
                        ),
                        RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colors.primary.copy(0.06f))
                    .background(Color.Green.copy(alpha = if (uiState.editedField == EditedField.AMOUNT) 0.04f else 0f))
                    .wrapContentHeight(Alignment.CenterVertically)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {
                            coroutineScope.launch {
                                viewModel.updateEditedField(EditedField.AMOUNT)
                            }
                        }
                    )
            )

            AnimatedVisibility(visible = codePin.value == null) {
                Column {
                    Spacer(Modifier.padding(vertical = 10.dp))
                    Box(
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .border(
                                BorderStroke(
                                    (if (uiState.editedField == EditedField.PIN) 1.dp else Dp.Unspecified),
                                    fieldBorderGradient
                                ),
                                RoundedCornerShape(8.dp)
                            )
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colors.primary.copy(0.06f))
                            .background(Color.Green.copy(alpha = if (uiState.editedField == EditedField.PIN) 0.04f else 0f))
                    ) {
                        Text(
                            uiState.pin.ifEmpty { stringResource(R.string.enter_pin) },
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentHeight(Alignment.CenterVertically)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = {
                                        coroutineScope.launch {
                                            viewModel.updateEditedField(EditedField.PIN)
                                        }
                                    }
                                )
                        )

                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    settings.saveCodePin(viewModel.getCodePin())
                                    viewModel.updateEditedField(EditedField.AMOUNT)
                                }
                            },
                            Modifier
                                .height(38.dp)
                                .align(Alignment.CenterEnd),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.primary,
                                contentColor = MaterialTheme.colors.onPrimary
                            ),
                            shape = RoundedCornerShape(8.dp),
                            enabled = viewModel.isPinCodeValid
                        ) {
                            Text(stringResource(R.string.common_save), Modifier.padding(start = 1.dp))
                        }
                    }

                    Text(
                        text = stringResource(R.string.pin_not_covered),
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.fillMaxWidth(),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }

            AnimatedVisibility(visible = codePin.value != null) {
                Text(
                    text = stringResource(R.string.pin_covered),
                    color = AccentBlue.copy(alpha = 0.75f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
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
            enabled = viewModel.hasValidAmount
        ) {
            Text(stringResource(R.string.common_confirm))
        }

        Spacer(Modifier.padding(4.dp))

        PinView(
            showDeleteBtn = viewModel.shouldShowDeleteBtn()
        ) {
            viewModel.handleNewKey(it)
        }
    }
}

