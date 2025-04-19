package com.nguyenminhkhang.moneymind.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(title: String,
                showBackButton: Boolean = true,
                onBackButtonClick: () -> Unit = {}
) {
    TopAppBar(
        title = {Text(text = title)},
        navigationIcon = {
            if (showBackButton) {
                BackButton(onBackButtonClick)
            }
        }
    )

}

@Composable
fun BackButton(onBackButtonClick: () -> Unit) {
    IconButton(onClick = onBackButtonClick) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
    }
}
