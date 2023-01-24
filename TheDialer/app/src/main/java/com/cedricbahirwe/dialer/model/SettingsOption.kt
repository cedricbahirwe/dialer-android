package com.cedricbahirwe.dialer.model

import androidx.compose.foundation.Image
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
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

    fun getSettingsItem(): SettingsItem {
        return when (this) {
            CHANGE_LANGUAGE -> SettingsItem(
                Icons.Rounded.Create,
                Color.Black.copy(0.7f),
                "Change Language",
                "Select your desired language."
            )
            BIOMETRICS -> SettingsItem(
                Icons.Rounded.ThumbUp,
                Color.Green,
                "Biometric Authentication",
                "For securing your activities on the app."
            )
            DELETE_PIN -> SettingsItem(
                Icons.Rounded.AddCircle,
                Color.Red,
                "Remove Pin",
                "You'll need to re-enter it later."
            )
            DELETE_ALL_USSD -> SettingsItem(
                Icons.Rounded.Delete,
                Color.Red.copy(0.9f),
                "Delete All USSD codes",
                "This action can not be undone."
            )
            GET_STARTED -> SettingsItem(
                Icons.Rounded.Star,
                Color.Blue,
                "Just getting started?",
                "Read our quick start blog post."
            )
            CONTACT_US -> SettingsItem(
                Icons.Rounded.Phone,
                Color.Yellow,
                "Contact Us",
                "Get help or ask a question."
            )
            TWEET_US -> SettingsItem(
                Icons.Rounded.AddCircle,
                Color.Blue,
                "Tweet Us",
                "Stay up to date."
            )
            TRANSLATION_SUGGESTION -> SettingsItem(
                Icons.Rounded.Send,
                Color.Blue,
                "Translation Suggestion",
                "Improve our localization."
            )
            ABOUT -> SettingsItem(
                Icons.Rounded.AddCircle,
                Color.Cyan,
                "About",
                "Version information."
            )
            REVIEW -> SettingsItem(
                Icons.Rounded.Person,
                Color.Red,
                "Review Dialer",
                "Let us know how we are doing."
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