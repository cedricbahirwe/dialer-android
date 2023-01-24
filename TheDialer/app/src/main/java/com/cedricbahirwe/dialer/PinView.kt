package com.cedricbahirwe.dialer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cedricbahirwe.dialer.ui.theme.DialerTheme

class PinView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DialerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    PinView("Android")
                }
            }
        }
    }
}

@Composable
fun PinView(
    input: String, isFullMode: Boolean = false,
    btnSize: Float = 60f,
    btnColors: ButtonColors = ButtonDefaults.outlinedButtonColors(
        backgroundColor = Color.Gray.copy(0.2f)
    )
) {

    val buttons = remember {
        listOf(
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "*",
            "0",
            "#".takeIf { isFullMode } ?: "X")
    }

    fun addKey(key: String) {
        println("The new input is ${input + key}")
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(buttons.size) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircleButton(
                    title = buttons[it],
                    size = btnSize,
                    btnColors = btnColors
                ) {
                    addKey(buttons[it])
                }
            }
        }
    }
}

@Composable
private fun CircleButton(title: String, size: Float, btnColors: ButtonColors, action: () -> Unit) {
    OutlinedButton(
        onClick = action,
        modifier = Modifier.size(size.dp),
        shape = CircleShape,
        border = null,
        contentPadding = PaddingValues(0.dp),
        colors = btnColors
    ) {
        Text(
            text = title,
            Modifier.padding(start = 1.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PinViewPreview() {
    DialerTheme {
        PinView("Android")
//        CircleButton(title = "0", size = 60f, btnColors = ButtonDefaults.buttonColors()) {}
    }
}