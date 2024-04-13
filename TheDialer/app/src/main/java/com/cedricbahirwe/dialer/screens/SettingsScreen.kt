package com.cedricbahirwe.dialer.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cedricbahirwe.dialer.BuildConfig
import com.cedricbahirwe.dialer.R
import com.cedricbahirwe.dialer.data.SettingsOption
import com.cedricbahirwe.dialer.data.repository.AppSettingsRepository
import com.cedricbahirwe.dialer.navigation.NavRoute
import com.cedricbahirwe.dialer.ui.theme.AccentBlue
import com.cedricbahirwe.dialer.ui.theme.DialerTheme
import com.cedricbahirwe.dialer.utilities.AppLinks
import com.cedricbahirwe.dialer.viewmodel.MainViewModel
import com.cedricbahirwe.dialer.viewmodel.MainViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(LocalContext.current, AppSettingsRepository.getInstance(LocalContext.current))
    )
) {

//    val biometricsState = viewModel.biometricsState.collectAsState(initial = false)
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

//    val reviewManager = remember {
//        ReviewManagerFactory.create(context)
//    }

//    val reviewInfo = rememberReviewTask(reviewManager)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.help_more)) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "backIcon")
                    }
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 10.dp
            )
        },
        bottomBar = {
            TermsAndConditions()
        }
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .verticalScroll(rememberScrollState())

        ) {

            Spacer(Modifier.height(8.dp))

            Section(R.string.common_general) {
                // TODO: Waiting for future version?

//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Box(
//                        modifier = Modifier.weight(1f)) {
//                        SettingsItemRow(SettingsOption.BIOMETRICS)
//                    }
//                    Switch(
//                        checked = biometricsState.value,
//                        onCheckedChange = { newValue ->
//                            coroutineScope.launch {
//                                viewModel.saveBiometricsStatus(newValue)
//                            }
//                        },
//                        colors = SwitchDefaults.colors(
//                            checkedThumbColor = Color.Green,
//                            uncheckedThumbColor = Color.White,
//                            checkedTrackColor = Color.Gray,
//                            uncheckedTrackColor = Color.LightGray
//                        )
//                    )
//                }

                Divider(startIndent = 60.dp)
                SettingsItemRow(SettingsOption.DELETE_ALL_USSD) {
                    coroutineScope.launch {
                        viewModel.removeAllUSSDCodes()
                        Toast.makeText(context, "Recent USSD Codes have been deleted.", Toast.LENGTH_LONG).show()
                    }
                }
            }

            Section(R.string.reach_out) {
                SettingsItemRow(SettingsOption.CONTACT_US) {
                    openWebLink(context, AppLinks.CONTACT_EMAIL_LINK)
                }
                Divider(startIndent = 60.dp)
                SettingsItemRow(SettingsOption.TWEET_US) {
                    openWebLink(context, AppLinks.DIALER_TWITTER)
                }
            }

            Section(R.string.common_colophon) {
                SettingsItemRow(SettingsOption.ABOUT) {
                    navController.navigate(NavRoute.AboutApp.path)
                }
                Divider(startIndent = 60.dp)
                SettingsItemRow(SettingsOption.REVIEW) {
                    val appPackageName = BuildConfig.APPLICATION_ID

                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName"))
                        startActivity(context, intent , null)
                    } catch (e: android.content.ActivityNotFoundException) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName"))
                        startActivity(context, intent, null)
                    }
//                    reviewInfo?.let {
//                        reviewManager.launchReviewFlow(context as Activity, reviewInfo)
//                    } ?: println("Value is null")
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(titleResId: Int) {
    Text(
        text = stringResource(titleResId),
        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple().takeIf { action != null },
                onClick = {
                    action?.invoke()
                }
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
                .clip(RoundedCornerShape(6.dp))
                .background(item.color)
                .padding(4.dp),
            tint = Color.White
        )

        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(item.titleResId),
                style = MaterialTheme.typography.body1,
            )
            Text(
                text = stringResource(item.subtitleResId),
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun TermsAndConditions() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "By using Dialer, you accept our",
            style = TextStyle.Default.copy(fontWeight = FontWeight.Bold)
        )

        Row {
            Text(
                text = "Terms & Conditions",
                style = TextStyle.Default.copy(color = AccentBlue, fontWeight = FontWeight.Bold),
                modifier = Modifier.clickable {
                    openWebLink(context, AppLinks.APP_PRIVACY_POLICY)
                }
            )
            Text(
                text = " and ",
                style = TextStyle.Default.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "Privacy Policy.",
                style = TextStyle.Default.copy(color = AccentBlue, fontWeight = FontWeight.Bold),
                modifier = Modifier.clickable {
                    openWebLink(context, AppLinks.APP_PRIVACY_POLICY)
                }
            )
        }
    }
}

fun openWebLink(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}

@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    DialerTheme {
        val navController = rememberNavController()
        SettingsScreen(navController = navController)
    }
}

//@Composable
//fun rememberReviewTask(reviewManager: ReviewManager): ReviewInfo? {
//    val reviewInfo: MutableState<ReviewInfo?> = remember {
//        mutableStateOf(null)
//    }
//
//    reviewManager.requestReviewFlow().addOnCompleteListener {
//        if (it.isSuccessful) {
//            reviewInfo.value = it.result
//        }
//    }
//
//    return reviewInfo.value
//}