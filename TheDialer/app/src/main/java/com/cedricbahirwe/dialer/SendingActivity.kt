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
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.cedricbahirwe.dialer.ui.theme.DialerTheme
import com.cedricbahirwe.dialer.ui.theme.MainRed

class SendingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DialerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    FieldsContainer("Android")
                }
            }
        }
    }
}

@Composable
fun FieldsContainer(name: String) {
    val state = remember { true }
    Box {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxWidth()
                .padding(10.dp)
        ) {

            TitleView("Transfer Money")

            val itemModifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .weight(1f)
                .align(Alignment.Start)
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(15.dp))
                .background(MaterialTheme.colors.background)
                .padding(10.dp)
            Row {
                DashBoardItem(R.drawable.wallet_pass, "Buy airtime", modifier = itemModifier)
                Spacer(modifier = Modifier.width(16.dp))
                DashBoardItem(R.drawable.paperplane_circle, "Transfer/Pay", modifier = itemModifier)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                DashBoardItem(R.drawable.clock_arrow_circlepath, "History", modifier = itemModifier)
                Spacer(modifier = Modifier.width(16.dp))
                DashBoardItem(R.drawable.wrench_and_screwdriver, "My Space", modifier = itemModifier)
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
                    Modifier.fillMaxWidth().height(48.dp),
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
                    Text("Pick a contact", Modifier.padding(start = 10.dp))
                }

                Spacer(Modifier.padding(10.dp))

                Button(
                    onClick = {},
                    Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Blue,
                    ),
                    elevation = btnElevation,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Dial USSD", Modifier.padding(start = 1.dp))
                }
            }

            Spacer(modifier = Modifier.weight(1.0f))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    DialerTheme {
        FieldsContainer("Android")
    }
}