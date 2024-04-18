package com.cedricbahirwe.dialer.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cedricbahirwe.dialer.R
import com.cedricbahirwe.dialer.common.PinButton
import com.cedricbahirwe.dialer.ui.theme.DialerTheme
import com.cedricbahirwe.dialer.utilities.AppConstants

@Composable
fun PinView(
    btnSize: Float = 50f,
    onEditChanged: (String) -> Unit
) {

    val getColorForButton: @Composable (String) -> ButtonColors = { button ->
        ButtonDefaults.outlinedButtonColors(
            backgroundColor = if (button == "X") Color.Transparent else colorResource(id = R.color.offBackground),
            contentColor = MaterialTheme.colors.primary
        )
    }

    val buttons = remember {
        AppConstants.PIN_UI_DIGITS
    }

    fun addKey(key: String) {
        onEditChanged(key)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(bottom = 6.dp)
    ) {
        items(buttons) {
            if (it != "-") {
                PinButton(
                    title = it,
                    size = btnSize,
                    btnColors = getColorForButton(it),
                ) {
                    addKey(it)
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun PinViewPreview() {
    DialerTheme {
        Surface {
            PinView {}
        }
    }
}