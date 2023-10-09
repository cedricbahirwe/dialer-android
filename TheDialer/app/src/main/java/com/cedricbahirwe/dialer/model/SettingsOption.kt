package com.cedricbahirwe.dialer.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.cedricbahirwe.dialer.R
import com.cedricbahirwe.dialer.model.protocol.Identifiable
import com.cedricbahirwe.dialer.ui.theme.AccentBlue
import com.cedricbahirwe.dialer.ui.theme.MainRed
import java.util.UUID

enum class SettingsOption {
    CHANGE_LANGUAGE,
    BIOMETRICS,
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
                Icons.Rounded.Lock,
                Color.Green,
                R.string.biometric_title,
                R.string.biometric_description
            )
            DELETE_PIN -> SettingsItem(
                Icons.Rounded.Delete,
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
            CONTACT_US -> SettingsItem(
                Icons.Rounded.Phone,
                MainRed,
                R.string.contact_us_title,
                R.string.contact_us_description
            )
            TWEET_US -> SettingsItem(
                Icons.Rounded.ThumbUp,
                AccentBlue,
                R.string.tweet_us_title,
                R.string.tweet_us_description
            )
            TRANSLATION_SUGGESTION -> SettingsItem(
                Icons.Rounded.Send,
                Color.Cyan,
                R.string.translate_suggestion_title,
                R.string.translate_suggestion_description
            )
            ABOUT -> SettingsItem(
                Icons.Rounded.Info,
                Color.Magenta,
                R.string.common_about,
                R.string.version_information_title
            )
            REVIEW -> SettingsItem(
                Icons.Rounded.Star,
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

}