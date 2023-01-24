package com.cedricbahirwe.dialer.model

import androidx.compose.foundation.Image
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.cedricbahirwe.dialer.model.protocol.Identifiable
import java.util.*

data class SettingsItem(
    val icon: @Composable () -> Unit,
    val color: Color,
    val title: String,
    val subtitle: String
) : Identifiable<UUID> {
    override val id: UUID = UUID.randomUUID()

    constructor(
        icon: Pair<Int, Modifier>,
        color: Color,
        title: String,
        subtitle: String
    ) : this({
        Image(
            painter = painterResource(id = icon.first),
            contentDescription = "${icon.first} icon",
            modifier = icon.second,
        )
    }, color, title, subtitle)


    constructor(
        sysIcon: (ImageVector),
        color: Color,
        title: String,
        subtitle: String
    ) : this({
        Icon(
            sysIcon,
            contentDescription = sysIcon.name
        )
    }, color, title, subtitle)
}