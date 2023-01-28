package com.cedricbahirwe.dialer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cedricbahirwe.dialer.model.PreviewContent
import com.cedricbahirwe.dialer.model.RecentDialCode
import com.cedricbahirwe.dialer.ui.theme.DialerTheme
import java.text.NumberFormat
import java.util.*

class HistoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DialerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    RencentCodesList()
                }
            }
        }
    }
}

@Composable
private fun RencentCodesList() {
    fun getTotal(): String {
        val formatter = NumberFormat.getCurrencyInstance()
        formatter.currency = Currency.getInstance("RWF")
        return formatter.format(120000)
    }
    Box {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            TitleView("History")

            Column(
                Modifier
                    .verticalScroll(state = ScrollState(0))
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .padding(horizontal = 10.dp)

            ) {
                val recentCodes = Array(size = 10) {
                    RecentDialCode(UUID.randomUUID(), PreviewContent.exampleRecentCode.detail)
                }

                recentCodes.forEach { code ->

                    HistoryRow(code)
                    if (code != recentCodes.last()) {
                        Divider(Modifier.padding(start = 30.dp))
                    }

                }
            }

            Spacer(modifier = Modifier.weight(1.0f))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row {
                    Text(
                        "Total :", 
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary
                    )
                    Spacer(modifier = Modifier.weight(1.0f))
                    Text(
                        getTotal(),
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary
                    )
                }
                Text(
                    "The estimations are based on the recent USSD codes used.",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Left,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun HistoryRow(code: RecentDialCode) {
    fun getColor(): Color {
        return if (code.detail.amount < 1000) {
            Color.Green
        } else if (code.detail.amount < 5000) {
            Color.Blue
        } else {
            Color.Red
        }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(5.dp)
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(getColor())
        )
        Spacer(Modifier.padding(end = 10.dp))

        Text(
            code.detail.fullCode,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1
        )
        Spacer(Modifier.weight(1f))
        Text(
            "${code.count}",
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.primary)
                .wrapContentSize(Alignment.Center),
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryActivityPreview() {
    DialerTheme {
        RencentCodesList()
    }
}