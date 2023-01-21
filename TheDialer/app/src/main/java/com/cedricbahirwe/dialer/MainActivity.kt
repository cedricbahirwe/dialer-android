package com.cedricbahirwe.dialer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cedricbahirwe.dialer.ui.theme.DialerTheme

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
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DialerTheme {
        DashBoardContainer()
    }
}