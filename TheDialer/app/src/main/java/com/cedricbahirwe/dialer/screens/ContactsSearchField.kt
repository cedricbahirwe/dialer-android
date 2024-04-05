package com.cedricbahirwe.dialer.screens


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.cedricbahirwe.dialer.R
import com.cedricbahirwe.dialer.ui.theme.AccentBlue

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    searchQuery: String,
    hint: String = "Search by name or phone",
    onSearch: (String) -> Unit,
    onEndEditing: () -> Unit
) {
    var isSearching by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .padding(horizontal = 16.dp),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(text = "$isSearching")
        CustomTextField(
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = Color.LightGray,
                )
            },
            trailingIcon = {
                AnimatedVisibility(visible = isSearching) {
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
                                isSearching = false
                                onEndEditing()
                            }
                    )
                }
            },
            modifier = Modifier
                .padding(4.dp)
                .height(32.dp)
                .background(
                    colorResource(R.color.offBackground),
                    RoundedCornerShape(6.dp)
                )
                .focusRequester(if (isSearching) FocusRequester.Default else FocusRequester())
                .onFocusChanged { isSearching = it.isFocused },
//                    .onTex { isSearching = true },,
            fontSize = MaterialTheme.typography.caption.fontSize,
            placeholderText = hint,
            searchQuery = searchQuery,
            onSearch = onSearch,
            onEndEditing = onEndEditing
        )
//        Column(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(2.dp),
//            modifier = Modifier
//                .background(
//                    colorResource(R.color.offBackground),
//                    RoundedCornerShape(6.dp)
//
//                )
//        ) {


//            SimpleTextField(
//                value = searchQuery,
//                onValueChange = {
//                    onSearch(it)
//                },
//                placeholder = { Text(hint, color = Color.Gray) },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .focusRequester(if (isSearching) FocusRequester.Default else FocusRequester())
//                    .onFocusChanged { isSearching = it.isFocused },
////                    .onTex { isSearching = true },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
//                keyboardActions = KeyboardActions(onDone = { isSearching = false }),
//                shape = RoundedCornerShape(0.dp),
//                colors = TextFieldDefaults.textFieldColors(
//                    disabledTextColor = Color.Transparent,
//                    backgroundColor = Color.Red,
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent,
//                    disabledIndicatorColor = Color.Transparent
//                ),
//                leadingIcon = {
//                    Icon(
//                        imageVector = Icons.Filled.Search,
//                        contentDescription = "Search",
//                        tint = Color.Gray
//                    )
//                },
//                trailingIcon = {
//                    AnimatedVisibility(visible = isSearching) {
//                        IconButton(
//                            onClick = {
//                                isSearching = false
//                                onEndEditing()
//                            },
//                            modifier = Modifier
//                                .padding(end = 9.dp)
//                                .size(25.dp),
//                        ) {
//                            Icon(
//                                imageVector = Icons.Filled.Clear,
//                                contentDescription = "Clear",
//                                tint = Color.Gray,
//                                modifier = Modifier
//                                    .background(Color.DarkGray, shape = RoundedCornerShape(15.dp))
//                                    .size(25.dp)
//                                    .padding(5.dp)
//                            )
//                        }
//                    }
//                }
//            )
//            AnimatedVisibility(visible = isSearching) {
//                IconButton(
//                    onClick = { searchQuery = "" },
//                    modifier = Modifier
//                        .padding(end = 9.dp)
//                        .size(25.dp),
//                ) {
//                    Icon(
//                        imageVector = Icons.Filled.Clear,
//                        contentDescription = "Clear",
//                        tint = Color.Gray,
//                        modifier = Modifier
//                            .background(Color.DarkGray, shape = RoundedCornerShape(15.dp))
//                            .size(25.dp)
//                            .padding(5.dp)
//                    )
//                }
//            }
//        }

        AnimatedVisibility(visible = isSearching) {
            Text(
                text = "Cancel",
                color = AccentBlue,
                modifier = Modifier.clickable { isSearching = false }
            )
        }
    }
}

//    Surface(
//        modifier = modifier,
//        color = Color.LightGray,
//        elevation = 4.dp
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
////            modifier = Modifier.fillMaxWidth()
//        ) {
//            TextField(
//                value = text,
//                onValueChange = { newText ->
//                    text = newText
//                    onSearch(newText)
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp),
//                placeholder = { Text(hint) },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
//                keyboardActions = KeyboardActions(onDone = { /* Handle search action */ })
//            )
//
//            IconButton(  onClick = { text = "" },
//                modifier = Modifier.padding(end = 16.dp)
//            ) {
//                Icon(
//                    Icons.Filled.Settings,
//                    "backIcon",
//                    tint = AccentBlue
//                )
//            }
//        }
//    }
//}

@Preview(showBackground = true)
@Composable
fun PreviewSearchField() {
//    DialerTheme(darkTheme = true) {
        SearchField(
            modifier = Modifier
                .background(Color.Green)
                .padding(20.dp),
            searchQuery = "",
            onSearch = {}
        ) {}
//    }
}


@Composable
private fun CustomTextField(
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String = "Placeholder",
    fontSize: TextUnit = MaterialTheme.typography.body2.fontSize,
    searchQuery: String,
    onSearch: (String) -> Unit,
    onEndEditing: () -> Unit
) {
    BasicTextField(modifier = modifier
        .fillMaxWidth(),
        value = searchQuery,
        onValueChange = {
            onSearch(it)
        },
        singleLine = true,
        cursorBrush = SolidColor(AccentBlue),
        textStyle = LocalTextStyle.current.copy(
            color = MaterialTheme.colors.onSurface,
            fontSize = fontSize
        ),
        decorationBox = { innerTextField ->
            Row(
                modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (leadingIcon != null) leadingIcon()
                Box(Modifier.weight(1f)) {
                    if (searchQuery.isEmpty()) Text(
                        placeholderText,
                        style = LocalTextStyle.current.copy(
                            color = Color.Gray,
                            fontSize = fontSize
                        )
                    )
                    innerTextField()
                }
                if (trailingIcon != null) trailingIcon()
            }
        }
    )
}


//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun SimpleTextField(
//    value: String,
//    onValueChange: (String) -> Unit,
//    modifier: Modifier = Modifier,
//    enabled: Boolean = true,
//    readOnly: Boolean = false,
//    textStyle: TextStyle = LocalTextStyle.current,
//    leadingIcon: @Composable (() -> Unit)? = null,
//    trailingIcon: @Composable (() -> Unit)? = null,
//    visualTransformation: VisualTransformation = VisualTransformation.None,
//    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
//    keyboardActions: KeyboardActions = KeyboardActions(),
//    singleLine: Boolean = false,
//    maxLines: Int = Int.MAX_VALUE,
//    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
//    placeholder: @Composable (() -> Unit)? = null,
//    onTextLayout: (TextLayoutResult) -> Unit = {},
//    cursorBrush: Brush = SolidColor(Color.Black),
//    colors: TextFieldColors = TextFieldDefaults.textFieldColors(),
//    backgroundColor: Color = Color.Transparent,
//    shape: Shape = RoundedCornerShape(0.dp),
//    fieldHeight: Dp = 30.dp,
//) {
//    BasicTextField(modifier = modifier
//        .background(backgroundColor, shape = shape),
//        value = value,
//        onValueChange = onValueChange,
//        singleLine = singleLine,
//        maxLines = maxLines,
//        enabled = enabled,
//        readOnly = readOnly,
//        interactionSource = interactionSource,
//        textStyle = textStyle,
//        visualTransformation = visualTransformation,
//        keyboardOptions = keyboardOptions,
//        keyboardActions = keyboardActions,
//        onTextLayout = onTextLayout,
//        cursorBrush = cursorBrush,
//        decorationBox = { innerTextField ->
//            TextFieldDefaults.TextFieldDecorationBox(
//                value = value,
//                innerTextField = {
//                    Box(
//                        modifier = Modifier.height(fieldHeight),
//                        contentAlignment = Alignment.CenterStart
//                    ) {
//                        innerTextField()
//                    }
//                },
//                enabled = enabled,
//                colors = colors,
//                singleLine = singleLine,
//                visualTransformation = VisualTransformation.None,
//                interactionSource = interactionSource,
//                contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
//                    top = 0.dp,
//                    bottom = 0.dp
//                ),
//                placeholder = {
//                    if (value.isEmpty() && placeholder != null) {
//                        Box(
//                            modifier = Modifier.height(fieldHeight),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            placeholder()
//                        }
//                    }
//                },
//                leadingIcon = leadingIcon,
//                trailingIcon = trailingIcon
//            )
//        }
//    )
//}
