package com.cedricbahirwe.dialer.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.cedricbahirwe.dialer.common.TitleView
import com.cedricbahirwe.dialer.navigation.NavRoute
import com.cedricbahirwe.dialer.ui.theme.AccentBlue
import com.cedricbahirwe.dialer.ui.theme.MainRed
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DashBoardContainer(navController: NavHostController) {

    val isMySpaceFlowActive = remember { mutableStateOf(false) }

    val purchaseSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )

    val mySpaceSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { false }
    )

    val coroutineScope = rememberCoroutineScope()

    BackHandler(purchaseSheetState.isVisible) {
        coroutineScope.launch { purchaseSheetState.hide() }
    }

    BackHandler(mySpaceSheetState.isVisible) {
        coroutineScope.launch { mySpaceSheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = if (isMySpaceFlowActive.value) mySpaceSheetState else purchaseSheetState,
        sheetContent = {
            if (isMySpaceFlowActive.value)
                MySpaceScreen {
                    coroutineScope.launch {
                        mySpaceSheetState.hide()
                    }
                }
            else PurchaseDetailView()
        },
        modifier = Modifier.fillMaxSize(),
        sheetShape = RoundedCornerShape(15.dp)
    ) {

        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TitleView("Dialer")

                IconButton(onClick = {
                    navController.navigate(NavRoute.Settings.path)
                }) {
                    Icon(
                        Icons.Filled.Settings,
                        "backIcon",
                        tint = AccentBlue
                    )
                }
            }

            val itemModifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .weight(1f)
                .align(Alignment.Start)
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(15.dp))
                .background(MaterialTheme.colors.background)
                .padding(10.dp)
            Row {
                DashBoardItem(R.drawable.wallet_pass, stringResource(R.string.buy_airtime), modifier = itemModifier) {
                    coroutineScope.launch {
                        isMySpaceFlowActive.value = false
                        purchaseSheetState.show()
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                DashBoardItem(
                    R.drawable.paperplane_circle,
                    stringResource(R.string.transfer_pay),
                    modifier = itemModifier
                ) {
                    navController.navigate(NavRoute.Send.path)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                DashBoardItem(
                    R.drawable.clock_arrow_circlepath,
                    stringResource(R.string.common_history),
                    modifier = itemModifier
                ) {
                    navController.navigate(NavRoute.History.path)
                }
                Spacer(modifier = Modifier.width(16.dp))
                DashBoardItem(
                    R.drawable.wrench_and_screwdriver,
                    stringResource(R.string.my_space),
                    modifier = itemModifier
                ) {
                    coroutineScope.launch {
                        isMySpaceFlowActive.value = true
                        mySpaceSheetState.show()
                    }

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
                        contentDescription = stringResource(R.string.quick_dial_icon)
                    )
                    Text(stringResource(R.string.quick_dial), Modifier.padding(start = 10.dp))
                }
                Spacer(modifier = Modifier.weight(1.0f))

            }
        }
    }
}

@Composable
private fun DashBoardItem(icon: Int, name: String, modifier: Modifier, onClick: () -> Unit) {

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
