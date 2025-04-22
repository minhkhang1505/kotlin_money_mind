package com.nguyenminhkhang.moneymind.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.nguyenminhkhang.moneymind.R
import com.nguyenminhkhang.moneymind.Screen

@Composable
fun MyBottomAppBar(navController: NavController) {
    BottomAppBar(
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly, // Cách đều các nút
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(onClick = { navController.navigate(Screen.DashboardScreen.route) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_home_24),
                        contentDescription = "Main screen"
                    )
                }
                IconButton(onClick = { navController.navigate(Screen.TaskListScreen.route) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_call_to_action_24),
                        contentDescription = "Wallet",
                    )
                }
                FloatingActionButton(
                    onClick = { navController.navigate(Screen.AddTransactionScreen.route) },
                    containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                    elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                ) {
                    Icon(Icons.Filled.Add, "Add new transaction")
                }
                IconButton(onClick = { navController.navigate(Screen.CurrencyConverterScreen.route) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_cached_24),
                        contentDescription = "Cerrency convertor",
                    )
                }
                IconButton(onClick = { navController.navigate(Screen.AccountScreen.route) }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_manage_accounts_24),
                        contentDescription = "Account",
                    )
                }
            }

        },
    )
}