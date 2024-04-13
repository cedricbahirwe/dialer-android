package com.cedricbahirwe.dialer.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cedricbahirwe.dialer.R
import com.cedricbahirwe.dialer.data.repository.AppSettingsRepository
import com.cedricbahirwe.dialer.ui.theme.AccentBlue
import com.cedricbahirwe.dialer.ui.theme.DialerTheme
import com.cedricbahirwe.dialer.viewmodel.MainViewModel
import com.cedricbahirwe.dialer.viewmodel.MainViewModelFactory

@Composable
fun PurchaseDetailView(
    viewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(LocalContext.current, AppSettingsRepository.getInstance(LocalContext.current))
    )
) {
    val airtimeState by viewModel.airtimeState.collectAsState()

    val hasValidAmount by viewModel.hasValidAmount.collectAsState(initial = false)

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

        Column(Modifier.padding(vertical = 5.dp)) {
            val fieldBorderGradient = remember {
                Brush.linearGradient(
                    colors = listOf(Color.Green, Color.Blue)
                )
            }

            Text(
                if (hasValidAmount) airtimeState.toString()
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
                        BorderStroke(1.dp, fieldBorderGradient),
                        RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colors.primary.copy(0.06f))
                    .background(Color.Green.copy(alpha = 0.04f))
                    .wrapContentHeight(Alignment.CenterVertically)
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
                contentColor = Color.White,
                disabledContentColor = Color.White,
                backgroundColor = AccentBlue,
                disabledBackgroundColor = AccentBlue.copy(alpha = 0.5f)
            ),
            elevation = btnElevation,
            shape = RoundedCornerShape(8.dp),
            enabled = hasValidAmount
        ) {
            Text(stringResource(R.string.common_confirm))
        }

        Spacer(Modifier.padding(4.dp))

        PinView {
            viewModel.handleNewKey(it)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PurchaseDetailPreview() {
    DialerTheme {
        PurchaseDetailView()
    }
}
