package com.cedricbahirwe.dialer.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cedricbahirwe.dialer.R
import com.cedricbahirwe.dialer.data.SettingsOption
import com.cedricbahirwe.dialer.data.repository.AppSettingsRepository
import com.cedricbahirwe.dialer.ui.theme.DialerTheme
import com.cedricbahirwe.dialer.viewmodel.MainViewModel
import com.cedricbahirwe.dialer.viewmodel.MainViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(AppSettingsRepository.getInstance(LocalContext.current))
    )
) {

    val biometricsState = viewModel.biometricsState.collectAsState(initial = false)
    val codePin = viewModel.getCodePin.collectAsState(initial = null)
    val allUSSDCodes = viewModel.allUSSDCodes.collectAsState(initial = emptySet())

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.help_more)) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 10.dp
            )
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.weight(1f)) {
                        SettingsItemRow(SettingsOption.BIOMETRICS)
                    }
                    Switch(
                        checked = biometricsState.value,
                        onCheckedChange = { newValue ->
                            coroutineScope.launch {
                                viewModel.saveBiometricsStatus(newValue)
                            }
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.Green,
                            uncheckedThumbColor = Color.White,
                            checkedTrackColor = Color.Gray,
                            uncheckedTrackColor = Color.LightGray
                        )
                    )
                }

                AnimatedVisibility(visible = codePin.value != null) {
                    Divider(startIndent = 60.dp)
                    SettingsItemRow(SettingsOption.DELETE_PIN) {
                        coroutineScope.launch {
                            println("Pin is ${codePin.value!!.asString}")
                            viewModel.removePinCode()
                        }
                    }
                }

                AnimatedVisibility(visible = allUSSDCodes.value.isNotEmpty()) {
                    Divider(startIndent = 60.dp)
                    SettingsItemRow(SettingsOption.DELETE_ALL_USSD) {
                        coroutineScope.launch {
                            viewModel.removeAllUSSDCodes()
                        }
                    }
                }
            }

            Section(R.string.reach_out) {
                SettingsItemRow(SettingsOption.CONTACT_US) {}
                Divider(startIndent = 60.dp)
                SettingsItemRow(SettingsOption.TWEET_US) {}
            }

            Section(R.string.common_colophon) {
                SettingsItemRow(SettingsOption.ABOUT) {}
                Divider(startIndent = 60.dp)
                SettingsItemRow(SettingsOption.REVIEW)
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
//            modifier = Modifier.weight(1f),
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
@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    DialerTheme {
        val navController = rememberNavController()
        SettingsScreen(navController = navController)
    }
}