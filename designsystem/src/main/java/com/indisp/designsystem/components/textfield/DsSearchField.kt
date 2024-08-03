package com.indisp.designsystem.components.textfield

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import kotlinx.coroutines.delay

@Composable
fun DsSearchField(
    value: String,
    hint: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        delay(500)
        focusRequester.requestFocus()
    }

    DsTextField(
        modifier = modifier.onFocusChanged { Log.d("DsSearchField", "DsSearchField: $it") },
        value = value,
        onValueChange = onValueChange,
        inputType = DsTextInputType.Search,
        hint = hint,
        focusRequester = focusRequester,
        onDone = { focusManager.clearFocus() }
    )
}