package com.cedricbahirwe.dialer.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cedricbahirwe.dialer.R
import com.cedricbahirwe.dialer.model.PreviewContent
import com.cedricbahirwe.dialer.model.RecentDialCode
import java.util.UUID

@Preview(showBackground = true)
@Composable
fun RecentCodesList() {
    Scaffold(bottomBar = {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth().padding(10.dp)
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
                    "120,000 RWF",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )
            }
            Text(
                "The estimations are based on the recent USSD codes used.",
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
//                        .background(MaterialTheme.colors.background)
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    TitleView("History")

                    Column(
                        Modifier
                            .verticalScroll(state = ScrollState(0))
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colors.surface)
                            .padding(horizontal = 10.dp)

                    ) {
                        val recentCodes = Array(size = 20) {
                            RecentDialCode(
                                UUID.randomUUID(),
                                PreviewContent.exampleRecentCode.detail
                            )
                        }

                        recentCodes.forEach { code ->
                            HistoryRow(code) {

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
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(5.dp)
            .clickable {
                onClick(code)
            }
    ) {

        if (code.count > 20) {
            Image(
                painter = painterResource(id = R.drawable.fire),
                contentDescription = "Fire icon",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .size(25.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(getColor())
            )
        }
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
                .background(MaterialTheme.colors.onSurface)
                .wrapContentSize(Alignment.Center),
            color = MaterialTheme.colors.surface
        )
    }
}
