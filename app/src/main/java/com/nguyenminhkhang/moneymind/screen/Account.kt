package com.nguyenminhkhang.moneymind.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.nguyenminhkhang.moneymind.components.MyBottomAppBar
import com.nguyenminhkhang.moneymind.components.MyTopAppBar

@Composable
fun AccountScreen (navController: NavController) {
    Scaffold(topBar = { MyTopAppBar(title = "Setting account", showBackButton = false, onBackButtonClick = {}) },
        bottomBar = { MyBottomAppBar(navController ) }) {
        Text(text = "Comming soon", modifier = Modifier.padding(it))
    }
}