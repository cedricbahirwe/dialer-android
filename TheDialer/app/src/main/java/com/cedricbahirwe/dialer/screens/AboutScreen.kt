package com.cedricbahirwe.dialer.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cedricbahirwe.dialer.R
import com.cedricbahirwe.dialer.ui.theme.AccentBlue
import com.cedricbahirwe.dialer.ui.theme.DialerTheme
import com.cedricbahirwe.dialer.utilities.AppLinks

@Composable
fun AboutScreen(
    navController: NavController,
    versionName: String,
    versionCode: Int
) {
    val context = LocalContext.current
    val text = "Cedric Bahirwe & Esther Carelle"
    val annotatedText = buildAnnotatedString {
        val textParts = text.split(" & ")

        withStyle(style = SpanStyle(color = AccentBlue, fontWeight = FontWeight.SemiBold)) {
            append(textParts[0])
            addStringAnnotation(
                tag = "URL",
                annotation = AppLinks.cedricGithub,
                start = 0,
                end = textParts[0].length
            )
        }

        append(" & ")

        withStyle(style = SpanStyle(color = AccentBlue, fontWeight = FontWeight.SemiBold)) {
            append(textParts[1])
            addStringAnnotation(
                tag = "URL",
                annotation = AppLinks.estherGithub,
                start = textParts[0].length + 5,
                end = text.length
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.about)) },
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp),
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.background)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = R.drawable.dialer_applogo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(20.dp)),
                )

                Text(
                    text = stringResource(R.string.app_name),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            val versionLabel = "Version " + versionName + " (${versionCode})"
            Text(
                text = versionLabel,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body1
            )
            Column(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Designed and developed by",
                    modifier = Modifier.alpha(0.75f)
                )

                ClickableText(
                    text = annotatedText,
                    onClick = { offset ->
                        annotatedText.getStringAnnotations(
                            tag = "URL",
                            start = offset,
                            end = offset
                        )
                            .firstOrNull()?.let { annotation ->
                                openWebLink(context, annotation.item)
                            }
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview
@Composable
fun AboutScreenPreview() {
    DialerTheme {
        AboutScreen(
            rememberNavController(),
            "1.0",
            1
        )
    }
}