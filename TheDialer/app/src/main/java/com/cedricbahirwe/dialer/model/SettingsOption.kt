package com.cedricbahirwe.dialer.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.cedricbahirwe.dialer.R
import com.cedricbahirwe.dialer.model.protocol.Identifiable
import java.util.UUID

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

    fun getSettingsItem(): SettingsItem {
        return when (this) {
            CHANGE_LANGUAGE -> SettingsItem(
                Icons.Rounded.Create,
                Color.Black.copy(0.7f),
                R.string.change_language_description,
                R.string.change_language_description
            )
            BIOMETRICS -> SettingsItem(
                Icons.Rounded.ThumbUp,
                Color.Green,
                R.string.biometric_title,
                R.string.biometric_description
            )
            DELETE_PIN -> SettingsItem(
                Icons.Rounded.AddCircle,
                Color.Red,
                R.string.remove_pin_title,
                R.string.remove_pin_description
            )
            DELETE_ALL_USSD -> SettingsItem(
                Icons.Rounded.Delete,
                Color.Red.copy(0.9f),
                R.string.delete_ussd_title,
                R.string.delete_ussd_description
            )
            GET_STARTED -> SettingsItem(
                Icons.Rounded.Star,
                Color.Blue,
                R.string.getting_started_title,
                R.string.getting_started_description
            )
            CONTACT_US -> SettingsItem(
                Icons.Rounded.Phone,
                Color.Yellow,
                R.string.contact_us_title,
                R.string.contact_us_description
            )
            TWEET_US -> SettingsItem(
                Icons.Rounded.AddCircle,
                Color.Blue,
                R.string.tweet_us_title,
                R.string.tweet_us_description
            )
            TRANSLATION_SUGGESTION -> SettingsItem(
                Icons.Rounded.Send,
                Color.Blue,
                R.string.translate_suggestion_title,
                R.string.translate_suggestion_description
            )
            ABOUT -> SettingsItem(
                Icons.Rounded.AddCircle,
                Color.Cyan,
                R.string.common_about,
                R.string.version_information_title
            )
            REVIEW -> SettingsItem(
                Icons.Rounded.Person,
                Color.Red,
                R.string.review_dialer_title,
                R.string.review_dialer_subtitle
            )
        }
    }
}

data class SettingsItem(
    val icon: ImageVector,
    val color: Color,
    val titleResId: Int,
    val subtitleResId: Int
) : Identifiable<UUID> {
    override val id: UUID = UUID.randomUUID()

//        Icon(
//            painter = painterResource(id = R.drawable.clock_arrow_circlepath),
//            contentDescription = null,
//            modifier = Modifier
//                .size(28.dp)
//                .clip(MaterialTheme.shapes.small)
//                .background(color),
//            tint = Color.White
//        )

//        Icon(
//            painter = painterResource(id = icon.first),
//            contentDescription = "${icon.first} icon",
//            modifier = Modifier
//                .size(28.dp)
//                .clip(MaterialTheme.shapes.small)
//                .background(color),
//            tint = Color.White
//        )
//    }, color, title, subtitle)


}