package com.cedricbahirwe.dialer.screens


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextFieldDefaults.TextFieldDecorationBox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cedricbahirwe.dialer.R
import com.cedricbahirwe.dialer.ui.theme.AccentBlue
import com.cedricbahirwe.dialer.ui.theme.DialerTheme

@Composable
fun SearchField(
    searchQuery: String,
    isEditing: MutableState<Boolean>,
    onSearch: (String) -> Unit,
    onClearField: () -> Unit,
    focusManager: FocusManager,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
    hint: String = "Search by name or phone"
) {
    val interactionSource = remember { MutableInteractionSource() }

    fun endEditing() {
        onClearField()
        focusManager.clearFocus(force = true)
        isEditing.value = false
    }
//
//    BackHandler {
//        focusManager.clearFocus()
//        isEditing.value = false
//    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        MainField(
            text = searchQuery,
            onSearch = onSearch,
            isEditing = isEditing,
            interactionSource = interactionSource,
            focusManager = focusManager,
            focusRequester = focusRequester,
            placeholder = {
                Text(
                    hint,
                    style = MaterialTheme.typography.caption,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = Color.LightGray,
                )
            },
            trailingIcon = {
                AnimatedVisibility(visible = isEditing.value) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Clear",
                        tint = MaterialTheme.colors.background,
                        modifier = Modifier
                            .background(
                                Color.Gray,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .size(18.dp)
                            .padding(3.dp)
                            .clickable {
                                if (searchQuery.isEmpty()) {
                                    endEditing()
                                } else {
                                    onClearField()
                                }
                            }
                    )
                }
            },
            modifier = Modifier.weight(1f)
        )

        AnimatedVisibility(
            visible = isEditing.value,
//            enter = fadeIn() + slideInHorizontally(),
//            exit = fadeOut() + slideOutHorizontally(),
        ) {
            Text(
                text = "Cancel",
                color = AccentBlue,
                modifier = Modifier
                    .clickable { endEditing() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchField() {
    var searchQuery by remember { mutableStateOf("") }
    val isEditing = remember { mutableStateOf(false) }

    DialerTheme(darkTheme = true) {
        Box(Modifier.background(Color.Black)) {
            SearchField(
                modifier = Modifier.padding(20.dp),
                searchQuery = searchQuery,
                isEditing = isEditing,
                onSearch = {
                    searchQuery = it
                },
                onClearField = {},
                focusManager = LocalFocusManager.current,
                focusRequester = FocusRequester()
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainField(
    text: String,
    placeholder: (@Composable () -> Unit),
    leadingIcon: (@Composable () -> Unit),
    trailingIcon: (@Composable () -> Unit),
    onSearch: (String) -> Unit,
    isEditing: MutableState<Boolean>,
    interactionSource: MutableInteractionSource,
    focusManager: FocusManager,
    focusRequester: FocusRequester,
    modifier: Modifier
) {
    BasicTextField(
        value = text,
        onValueChange = { newText ->
            onSearch(newText)
        },
        interactionSource = interactionSource,
        singleLine = true,
        textStyle = MaterialTheme.typography.body2.copy(
            color = MaterialTheme.colors.primary,
        ),
        keyboardOptions = KeyboardOptions(
            autoCorrectEnabled = false,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus(true)
                isEditing.value = false
            }
        ),
        cursorBrush = SolidColor(AccentBlue),
        modifier = modifier
            .background(
                colorResource(R.color.offBackground),
                RoundedCornerShape(6.dp)
            )
            .focusRequester(focusRequester)
            .onFocusEvent {
                isEditing.value = it.hasFocus

            }
            .onFocusChanged {
                isEditing.value = it.isFocused
            }
    ) { innerTextField ->
        TextFieldDecorationBox(
            enabled = true,
            singleLine = true,
            value = text,
            interactionSource = interactionSource,
            innerTextField = innerTextField,
            visualTransformation = VisualTransformation.None,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Green,
                disabledTextColor = Color.Transparent,
                backgroundColor = Color.Red,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                placeholderColor = Color.Gray
            ),
            contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(0.dp)
        )
    }
}