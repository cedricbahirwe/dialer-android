package com.cedricbahirwe.dialer.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cedricbahirwe.dialer.R
import com.cedricbahirwe.dialer.model.RecentDialCode
import com.cedricbahirwe.dialer.viewmodel.HistoryViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Preview(showBackground = true)
@Composable
fun HistoryView(
    viewModel: HistoryViewModel = viewModel()
) {

    val recentCodes by rememberUpdatedState(newValue = viewModel.uiState)

    val estimatedTotalPrice = recentCodes.sumOf { it.totalPrice }

    Scaffold(bottomBar = {
        HistoryBottomBar(estimatedTotalPrice)
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row {
                Text(
                    stringResource(R.string.history_total_label),
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.weight(1.0f))
                Row(

                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                ) {
                    Text(
                        estimatedTotalPrice.formatDecimalSeparator(),
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary,
                        maxLines = 1)
                    Text(
                        stringResource(R.string.currency),
                        style = MaterialTheme.typography.caption,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary
                    )
                }
            }
            Text(
                stringResource(R.string.history_total_estimation_warning),
                fontSize = 10.sp,
                color = Color.Gray,
                textAlign = TextAlign.Left,
                maxLines = 1
            )
        }
    }) { innerPaddings ->
        Box(modifier = Modifier.padding(innerPaddings)) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    TitleView(stringResource(R.string.common_history))
                    LazyColumn(
                        Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colors.surface)
                            .padding(horizontal = 10.dp)
                    ) {
                        items(recentCodes) { code ->
                            HistoryRow(code = code) {
                                viewModel.performRecentDialing(it)
                            }
                            if (code != recentCodes.last()) {
                                Divider(Modifier.padding(start = 30.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HistoryRow(code: RecentDialCode, onClick: (RecentDialCode) -> Unit) {
    fun getColor(): Color {
        return if (code.detail.amount < 500) {
            Color.Green
        } else if (code.detail.amount < 1000) {
            Color.Blue
        } else {
            Color.Red
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .padding(5.dp)
            .clickable {
                onClick(code)
            }
    ) {

        Box(
            modifier = Modifier.size(20.dp)
        ) {
            if (code.count > 20) {
                Image(
                    painter = painterResource(id = R.drawable.fire),
                    contentDescription = stringResource(R.string.extensive_purchase),
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize(0.65f)
                        .clip(CircleShape)
                        .background(getColor())
                )
            }
        }

        Spacer(Modifier.padding(end = 10.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = "You bought ${code.detail.amount} RWF of airtime",
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Row {
                Text(
                    text = if ((0 until 10).contains(code.count))
                        pluralStringResource(
                            id = R.plurals.purchase_count,
                            code.count,
                            code.count
                        ) else
                        "More than 10+ times",
                    style = MaterialTheme.typography.caption,
                    fontStyle = FontStyle.Italic,
                )

                Spacer(Modifier.weight(1f))

                val sdf = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())

                val currentDateAndTime = sdf.format(Date())

                Text(
                    text = currentDateAndTime,
                    color = Color.Gray,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun HistoryBottomBar(estimatedTotalPrice: Int) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row {
            Text(
                stringResource(R.string.history_total_label),
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.weight(1.0f))
            Row(

                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(3.dp),
            ) {
                Text(
                    estimatedTotalPrice.formatDecimalSeparator(),
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary,
                    maxLines = 1)
                Text(
                    stringResource(R.string.currency),
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
            }
        }
        Text(
            stringResource(R.string.history_total_estimation_warning),
            fontSize = 10.sp,
            color = Color.Gray,
            textAlign = TextAlign.Left,
            maxLines = 1
        )
    }
}

private fun Int.formatDecimalSeparator(): String {
    return toString()
        .reversed()
        .chunked(3)
        .joinToString(",")
        .reversed()
}