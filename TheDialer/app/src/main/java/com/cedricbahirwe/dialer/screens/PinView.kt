package com.cedricbahirwe.dialer.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cedricbahirwe.dialer.common.CircleButton
import com.cedricbahirwe.dialer.ui.theme.DialerTheme

@Composable
fun PinView(isFullMode: Boolean = false,
            showDeleteBtn: Boolean = true,
            btnSize: Float = 50f,
            btnColors: ButtonColors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = Color.Gray.copy(0.2f)
            ), onEditChanged: (String) -> Unit
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
        onEditChanged(key)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(buttons.size) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (!isFullMode && buttons[it] != "*" ) {
                    if (buttons[it] != "X" || showDeleteBtn) {
                        CircleButton(
                            title = buttons[it],
                            size = btnSize,
                            btnColors = ButtonDefaults.outlinedButtonColors(
                                backgroundColor = btnColors.backgroundColor(true).value,
                                contentColor = if (buttons[it] == "X") Color.Red else MaterialTheme.colors.primary
                            )
                        ) {
                            addKey(buttons[it])
                        }
                    }
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PinViewPreview() {
    DialerTheme {
        PinView {}
//        CircleButton(title = "0", size = 60f, btnColors = ButtonDefaults.buttonColors()) {}
    }
}