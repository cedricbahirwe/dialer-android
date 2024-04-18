package com.cedricbahirwe.dialer.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cedricbahirwe.dialer.R
import com.cedricbahirwe.dialer.ui.theme.DialerTheme

@Composable
fun PinButton(title: String, size: Float, btnColors: ButtonColors, action: () -> Unit) {

    val isDeleteBtn = remember(title) {
        title == "X"
    }

    OutlinedButton(
        onClick = action,
        modifier = Modifier
            .fillMaxWidth()
            .height(size.dp),
        border = null,
        elevation = if (isDeleteBtn) null else ButtonDefaults.elevation(defaultElevation = 1.dp),
        colors = btnColors
    ) {

        if (isDeleteBtn) {
            Image(
                painter = painterResource(id = R.drawable.delete_left),
                contentDescription = stringResource(R.string.delete_digit),
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.size((size * 0.5).dp),
                colorFilter = ColorFilter.tint(LocalContentColor.current)
            )
        } else {
            Text(
                text = title,
                modifier = Modifier.padding(start = 1.dp),
                fontSize = (size / 2.5).sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PinButtonPreview() {
    DialerTheme(darkTheme = false) {
        PinButton(title = "0", size = 50f, btnColors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.Gray.copy(0.2f),
            contentColor = colors.primary
        )) {}
    }
}