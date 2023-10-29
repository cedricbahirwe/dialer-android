package com.cedricbahirwe.dialer.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cedricbahirwe.dialer.R
import com.cedricbahirwe.dialer.ui.theme.AccentBlue
import com.cedricbahirwe.dialer.ui.theme.MainRed

@Composable
fun WhatsNewScreen(
    onContinueClicked: () -> Unit
) {
    val changeLogs = ChangeLog.latestLogs

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Column(
                    modifier = Modifier.padding(top = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dialer_applogo),
                        contentDescription = null,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(20.dp)),
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = stringResource(R.string.app_name),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                        )

                        Text(
                            text = "Your USSD companion app.",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.alpha(0.75f)
                        )
                    }
                }
            }

            item {
                Text(
                    text = "What's in for you?",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 18.dp)
                        .fillMaxWidth(),
                    maxLines = 1,
                    textAlign = TextAlign.Center
                )
            }

            items(changeLogs) { changeLog ->
                ChangeLogView(log = changeLog)
            }
        }

        val fieldBorderGradient = Brush.linearGradient(
            colors = listOf(MainRed, AccentBlue)
        )

        Button(
            onClick = {
                onContinueClicked()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                contentColor = Color.White
            ),
            elevation = null,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
                .height(50.dp)
                .background(
                    brush = fieldBorderGradient,
                    shape = RoundedCornerShape(15.dp)
                )
        ) {
            Text(
                text = stringResource(R.string.common_continue),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White,
            )
        }
    }
}

@Composable
private fun ChangeLogView(log: ChangeLog) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            painter = painterResource(id = log.icon),
            contentDescription = null,
            tint = MainRed,
            modifier = Modifier
                .size(40.dp)
        )

        Column {
            Text(
                text = log.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = log.subtitle,
                fontSize = 14.sp,
                maxLines = 3,
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .alpha(0.8f),
            )
        }
    }
}

@Composable
@Preview
fun WhatsNewViewPreview() {
    WhatsNewScreen(onContinueClicked = {  })
}

private data class ChangeLog(
    val icon: Int,
    val title: String,
    val subtitle: String
) {
    companion object {
        val version500 = listOf(
            ChangeLog(R.drawable.caller, "Airtime", "Ability to quickly generate USSD for buying airtime."),
            ChangeLog(R.drawable.clock_arrow_circlepath, "History", "Get direct access to your frequently used USSD codes."),
            ChangeLog(R.drawable.currency, "Transfer/Pay", "Get the right USSD code for transferring to your friend or paying to the store."),
            ChangeLog(R.drawable.wrench_and_screwdriver, "My Space", "A unified space for buying electricity, Voice packs, Internet and more.")
        )

        val latestLogs: List<ChangeLog>
            get() = version500
    }
}