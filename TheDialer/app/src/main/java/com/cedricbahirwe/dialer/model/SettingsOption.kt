package com.cedricbahirwe.dialer.model

import android.content.res.Resources
import androidx.compose.foundation.Image
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.cedricbahirwe.dialer.R
import com.cedricbahirwe.dialer.model.protocol.Identifiable
import java.util.*

enum class SettingsOption {
    CHANGE_LANGUAGE,
    BIOMETRICS,
    GET_STARTED,
    CONTACT_US,
    TWEET_US,
    TRANSLATION_SUGGESTION,
    ABOUT,
    REVIEW,
    DELETE_PIN,
    DELETE_ALL_USSD;

    private companion object {
        val context: Resources = Resources.getSystem()
    }

    fun getSettingsItem(): SettingsItem {
        return when (this) {
            CHANGE_LANGUAGE -> SettingsItem(
                Icons.Rounded.Create,
                Color.Black.copy(0.7f),
                context.getString(R.string.change_language_title),
                context.getString(R.string.change_language_description)
            )
            BIOMETRICS -> SettingsItem(
                Icons.Rounded.ThumbUp,
                Color.Green,
                context.getString(R.string.biometric_title),
                context.getString(R.string.biometric_description)
            )
            DELETE_PIN -> SettingsItem(
                Icons.Rounded.AddCircle,
                Color.Red,
                context.getString(R.string.remove_pin_title),
                context.getString(R.string.remove_pin_description)
            )
            DELETE_ALL_USSD -> SettingsItem(
                Icons.Rounded.Delete,
                Color.Red.copy(0.9f),
                context.getString(R.string.delete_ussd_title),
                context.getString(R.string.delete_ussd_description)
            )
            GET_STARTED -> SettingsItem(
                Icons.Rounded.Star,
                Color.Blue,
                context.getString(R.string.getting_started_title),
                context.getString(R.string.getting_started_description)
            )
            CONTACT_US -> SettingsItem(
                Icons.Rounded.Phone,
                Color.Yellow,
                context.getString(R.string.contact_us_title),
                context.getString(R.string.contact_us_description)
            )
            TWEET_US -> SettingsItem(
                Icons.Rounded.AddCircle,
                Color.Blue,
                context.getString(R.string.tweet_us_title),
                context.getString(R.string.tweet_us_description)
            )
            TRANSLATION_SUGGESTION -> SettingsItem(
                Icons.Rounded.Send,
                Color.Blue,
                context.getString(R.string.translate_suggestion_title),
                context.getString(R.string.translate_suggestion_description)
            )
            ABOUT -> SettingsItem(
                Icons.Rounded.AddCircle,
                Color.Cyan,
                context.getString(R.string.common_about),
                context.getString(R.string.version_information_title)
            )
            REVIEW -> SettingsItem(
                Icons.Rounded.Person,
                Color.Red,
                context.getString(R.string.review_dialer_title),
                context.getString(R.string.review_dialer_subtitle)
            )
        }
    }
}

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
        sysIcon: ImageVector,
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