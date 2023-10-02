package com.cedricbahirwe.dialer.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cedricbahirwe.dialer.R
import com.cedricbahirwe.dialer.navigation.NavRoute
import com.cedricbahirwe.dialer.ui.theme.MainRed

@Composable
fun TitleView(title: String, modifier: Modifier = Modifier) {
    Row {
        Text(
            title,
            modifier = modifier.padding(vertical = 15.dp),
            color = MaterialTheme.colors.primary,
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun DashBoardItem(icon: Int, name: String, modifier: Modifier, onClick: () -> Unit) {

    Column(
        modifier = modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
    ) {
        val borderWidth = 1.dp
        Image(
            painter = painterResource(id = icon),
            contentDescription = "$name icon",

            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .size(25.dp)
                .border(
                    BorderStroke(borderWidth, MainRed),
                    CircleShape
                )
                .padding(borderWidth)
                .clip(CircleShape),
            alignment = Alignment.TopStart
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = name,
            color = Color.Gray,
            style = MaterialTheme.typography.caption
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DashBoardContainerPreview() {
    val navController = rememberNavController()
    DashBoardContainer(navController = navController)
}

@Composable
fun DashBoardContainer(navController: NavHostController) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxWidth()
            .padding(10.dp)
    ) {

        TitleView("Dialer")

        val itemModifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .weight(1f)
            .align(Alignment.Start)
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(15.dp))
            .background(MaterialTheme.colors.background)
            .padding(10.dp)
        Row {
            DashBoardItem(R.drawable.wallet_pass, "Buy airtime", modifier = itemModifier) {
                navController.navigate(NavRoute.AirtimePurchase.path)
            }
            Spacer(modifier = Modifier.width(16.dp))
            DashBoardItem(R.drawable.paperplane_circle, "Transfer/Pay", modifier = itemModifier) {
                navController.navigate(NavRoute.Send.path)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            DashBoardItem(R.drawable.clock_arrow_circlepath, "History", modifier = itemModifier) {
                navController.navigate(NavRoute.History.path)
            }
            Spacer(modifier = Modifier.width(16.dp))
            DashBoardItem(R.drawable.wrench_and_screwdriver, "My Space", modifier = itemModifier) {
                navController.navigate(NavRoute.MySpace.path)
            }
        }

        Spacer(modifier = Modifier.weight(1.0f))

        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            Button(
                onClick = {
                    navController.navigate(NavRoute.QuickDialing.path)
                },
                enabled = true,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MainRed,
                    contentColor = Color.White
                ),
            ) {
                Icon(
                    Icons.Rounded.AddCircle,
                    contentDescription = "Quick Dial icon"
                )
                Text(stringResource(R.string.quick_dial), Modifier.padding(start = 10.dp))
            }
            Spacer(modifier = Modifier.weight(1.0f))

        }
    }
}