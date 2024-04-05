package com.cedricbahirwe.dialer.screens

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cedricbahirwe.dialer.R
import com.cedricbahirwe.dialer.common.TitleView
import com.cedricbahirwe.dialer.data.isMerchantTransfer
import com.cedricbahirwe.dialer.ui.theme.AccentBlue
import com.cedricbahirwe.dialer.ui.theme.DialerTheme
import com.cedricbahirwe.dialer.viewmodel.ContactsViewModel
import com.cedricbahirwe.dialer.viewmodel.TransferViewModel
import com.cedricbahirwe.dialer.viewmodel.TransferViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun TransferView(
    viewModel: TransferViewModel = viewModel(
        factory = TransferViewModelFactory(
            LocalContext.current
        )
    )
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    val contactsSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    LaunchedEffect(contactsSheetState.progress.to) {
        if (contactsSheetState.progress.from == ModalBottomSheetValue.Expanded &&
            contactsSheetState.progress.to == ModalBottomSheetValue.HalfExpanded) {
            contactsSheetState.hide()
        }
    }

    val coroutineScope = rememberCoroutineScope()

    BackHandler(contactsSheetState.isVisible) {
        coroutineScope.launch { contactsSheetState.hide() }
    }

    val uiState by viewModel.uiState.collectAsState()

    val isMerchantTransfer = remember(uiState.isMerchantTransfer) {
        uiState.isMerchantTransfer
    }
    
    val selectedContact by viewModel.selectedContact.collectAsState()

    val pageTitle = if (isMerchantTransfer) {
        stringResource(id = R.string.pay_merchant)
    } else {
        stringResource(R.string.transfer_momo)
    }

    val feeHintText = if (uiState.estimatedFee == -1) {
        stringResource(R.string.beyond_range_fee_msg)
    } else {
        stringResource(R.string.estimated_fee_message, uiState.estimatedFee)
    }

//    DisposableEffect(Unit) {
//        focusRequester.requestFocus()
//        onDispose {
//            keyboardController?.hide()
//        }
//    }


    ModalBottomSheetLayout(
        sheetState = contactsSheetState,
        sheetContent = {
            ContactsList(
                viewModel = ContactsViewModel(LocalContext.current),
                onSelectContact = {
                    viewModel.cleanPhoneNumber(it)
                    coroutineScope.launch {
                        contactsSheetState.hide()
                    }
                }
            )
        },
        sheetShape = RoundedCornerShape(15.dp, 15.dp)
    ) {
        Box {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    TitleView(
                        title = pageTitle,
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                    )

                    TextButton(
                        modifier = Modifier
                            .align(Alignment.TopEnd),
                        onClick = {
                            viewModel.switchTransactionType()
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Transparent,
                            contentColor = AccentBlue
                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            if (isMerchantTransfer)
                                stringResource(R.string.pay_user) else
                                stringResource(R.string.pay_merchant)
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AnimatedVisibility(
                        visible = !isMerchantTransfer && uiState.amount.isNotEmpty()
                    ) {
                        Spacer(Modifier.padding(vertical = 8.dp))
                        Text(
                            text = feeHintText,
                            color = AccentBlue,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    OutlinedTextField(
                        value = uiState.amount,
                        onValueChange = {
                            viewModel.handleTransactionAmountChange(it)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        label = {
                            Text(stringResource(R.string.common_amount))
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        ),
                        placeholder = { Text(text = "Enter Amount") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = MaterialTheme.colors.primary,
                            focusedBorderColor = AccentBlue
                        ),
                        singleLine = true
                    )

                    Spacer(Modifier.padding(vertical = 8.dp))
                    AnimatedVisibility(
                        visible = !isMerchantTransfer
                    ) {
                        Text(
                            text = selectedContact.names,
                            color = AccentBlue,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    OutlinedTextField(
                        value = uiState.number,
                        onValueChange = {
                            viewModel.handleTransactionNumberChange(it)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(if (isMerchantTransfer) "Merchant Code" else "Phone Number")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                            }
                        ),
                        placeholder = { Text(text = "Enter ${if (isMerchantTransfer) "Merchant Code" else "Receiver's number"}") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = MaterialTheme.colors.primary,
                            focusedBorderColor = AccentBlue
                        ),
                        singleLine = true
                    )

                    AnimatedVisibility(
                        visible = isMerchantTransfer
                    ) {
                        Spacer(Modifier.padding(vertical = 8.dp))
                        Text(
                            text = stringResource(R.string.show_merchant_code_warning),
                            color = AccentBlue,
                            style = MaterialTheme.typography.caption
                        )
                    }
                }

                val btnElevation = ButtonDefaults.elevation(
                    defaultElevation = 20.dp,
                    pressedElevation = 15.dp,
                    disabledElevation = 0.dp,
                    hoveredElevation = 15.dp,
                    focusedElevation = 10.dp
                )

                Column(Modifier.padding(vertical = 16.dp)) {
                    AnimatedVisibility(
                        visible = !isMerchantTransfer
                    ) {
                        Button(
                            onClick = {
                                val activity = context as Activity
                                // on below line checking if permission is granted.
                                if (hasContactPermission(context)) {
                                    focusManager.clearFocus()
                                    // if permission granted open contacts List
                                    coroutineScope.launch {
                                        contactsSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                    }
                                } else {
                                    // if permission not granted requesting permission .
                                    requestContactPermission(context, activity)
                                }
                            },
                            Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.White,
                                contentColor = AccentBlue
                            ),
                            elevation = btnElevation,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(
                                Icons.Rounded.Person,
                                contentDescription = "Pick Contact icon"
                            )
                            Text(
                                stringResource(R.string.pick_contact_text),
                                Modifier.padding(start = 10.dp)
                            )
                        }
                    }


                    Spacer(Modifier.padding(10.dp))

                    Button(
                        onClick = {
                            focusManager.clearFocus()
                            viewModel.transferMoney()
                        },
                        Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = AccentBlue,
                            contentColor = Color.White
                        ),
                        elevation = btnElevation,
                        shape = RoundedCornerShape(8.dp),
                        enabled = uiState.isValid
                    ) {
                        Text(
                            stringResource(R.string.dial_ussd_text),
                            Modifier.padding(start = 1.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1.0f))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransferPreview() {
    DialerTheme {
        TransferView()
    }
}
// Function to open the contact picker and handle the result
fun hasContactPermission(context: Context): Boolean {
    // on below line checking if permission is present or not.
    return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) ==
            PackageManager.PERMISSION_GRANTED
}

fun requestContactPermission(context: Context, activity: Activity) {
    // on below line if permission is not granted requesting permissions.
    if (!hasContactPermission(context)) {
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_CONTACTS), 1)
    }
}



