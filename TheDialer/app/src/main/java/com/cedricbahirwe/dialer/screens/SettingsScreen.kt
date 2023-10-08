package com.cedricbahirwe.dialer.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cedricbahirwe.dialer.R
import com.cedricbahirwe.dialer.model.SettingsOption
import com.cedricbahirwe.dialer.ui.theme.DialerTheme
import com.cedricbahirwe.dialer.viewmodel.MainViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Settings") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White,
                elevation = 10.dp
            )
        }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {

            Section(R.string.general_settings) {
                SettingsItemRow(SettingsOption.BIOMETRICS)
                Divider(startIndent = 60.dp)
                SettingsItemRow(SettingsOption.DELETE_PIN)
                Divider(startIndent = 60.dp)
                SettingsItemRow(SettingsOption.DELETE_ALL_USSD)

            }

            Section(R.string.reach_out) {
                SettingsItemRow(SettingsOption.CONTACT_US) {}
                Divider(startIndent = 60.dp)
                SettingsItemRow(SettingsOption.TWEET_US) {}
                Divider(startIndent = 60.dp)
                SettingsItemRow(SettingsOption.TRANSLATION_SUGGESTION) {}
            }

            Section(R.string.common_colophon) {
                SettingsItemRow(SettingsOption.ABOUT) {}
                Divider(startIndent = 60.dp)
                SettingsItemRow(SettingsOption.REVIEW) {}
            }

//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(Color.Red.copy(alpha = 0.2f))
//                        .padding(6.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween
//
//                ) {
//                    Column() {
//                        Text(
//                            text = "Biometric Authentication",
//                            style = MaterialTheme.typography.body1
//                        )
//
//                        Text(
//                            text = "For securing your activities on the app.",
//                            style = MaterialTheme.typography.body2,
//                            modifier = Modifier.alpha(0.8f)
//                        )
//                    }
//
//                }

                Spacer(Modifier.weight(1.0f))
        }
    }
}
//Checkbox(checked = false, onCheckedChange = {})
//Switch(
//checked = true, // You can bind this to a ViewModel or a preference
//onCheckedChange = { /* Handle switch state change */ }
//)

@Composable
private fun SectionHeader(titleResId: Int) {
    Text(
        text = stringResource(titleResId),
        style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
    )
}

@Composable
private fun Section(titleResId: Int, sectionContent: @Composable () -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SectionHeader(titleResId)

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(MaterialTheme.colors.surface)
        ) {
            sectionContent()
        }
    }
}

@Composable
private fun SettingsItemRow(
    option: SettingsOption,
    action: (() -> Unit)? = null
) {
    val item = option.getSettingsItem()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colors.surface,
                MaterialTheme.shapes.small
            )
            .padding(start = 15.dp)
            .padding(vertical = 10.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.icon.name,
            modifier = Modifier
                .size(28.dp)
                .clip(MaterialTheme.shapes.small)
                .background(item.color)
                .padding(4.dp),
            tint = Color.White
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(item.titleResId),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primary
            )
            Text(
                text = stringResource(item.subtitleResId),
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    DialerTheme {
        val navController = rememberNavController()
        SettingsScreen(navController = navController)
    }
}