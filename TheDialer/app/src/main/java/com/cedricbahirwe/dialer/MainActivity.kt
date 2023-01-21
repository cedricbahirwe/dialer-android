package com.cedricbahirwe.dialer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cedricbahirwe.dialer.ui.theme.DialerTheme
import com.cedricbahirwe.dialer.ui.theme.MainRed

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DialerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    DashBoardContainer()
                }
            }
        }
    }
}

@Composable
fun DashBoardItem(name: String, modifier: Modifier) {

    Column(modifier = modifier) {
        Text("IMG")

        Text(
            text = name,
            color = Color.Gray,
            style = MaterialTheme.typography.caption
        )
    }
}

@Composable
fun DashBoardContainer() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxWidth()
            .padding(10.dp)
    ) {

        Row {
            Text(
                "Dialer",
                modifier = Modifier.padding(vertical = 15.dp),
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        val itemModifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .weight(1f)
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(15.dp))
            .background(MaterialTheme.colors.background)
            .padding(10.dp, 16.dp)
        Row {
            DashBoardItem("Buy airtime", modifier = itemModifier)
            Spacer(modifier = Modifier.width(16.dp))
            DashBoardItem("Transfer/Pay", modifier = itemModifier)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            DashBoardItem("History", modifier = itemModifier)
            Spacer(modifier = Modifier.width(16.dp))
            DashBoardItem("My Space", modifier = itemModifier)
        }

        Spacer(modifier = Modifier.weight(1.0f))

        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MainRed,
                    contentColor = Color.White
                ),
            ) {
                Icon(
                    Icons.Rounded.AddCircle,
                    contentDescription = "Quick Dial icon"
                )
                Text("Add to cart", Modifier.padding(start = 10.dp))
            }
            Spacer(modifier = Modifier.weight(1.0f))

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(7.dp)
            ) {
                Text("|||")
                Spacer(modifier = Modifier.width(8.dp))
                Text("MTN")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DialerTheme {
        DashBoardContainer()
    }
}