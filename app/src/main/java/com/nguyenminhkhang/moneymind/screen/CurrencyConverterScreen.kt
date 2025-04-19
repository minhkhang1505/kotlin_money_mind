package com.nguyenminhkhang.moneymind.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.nguyenminhkhang.moneymind.R
import com.nguyenminhkhang.moneymind.components.MyBottomAppBar
import com.nguyenminhkhang.moneymind.components.MyTopAppBar
import com.nguyenminhkhang.moneymind.viewmodel.CurrencyViewModel

data class ConverterHistory(
    val unit: String,
    val valueToConvert: String,
    val unitIsConverted: String,
    val valueAfterConverted: String
)

@Composable
fun CurrencyConverterScreen(navController: NavController) {
    Scaffold (
        topBar = {
            MyTopAppBar(title = "Currency Converter", showBackButton = false, onBackButtonClick = {})
        },
        bottomBar = { MyBottomAppBar(navController) }
    ) {

        val viewModel : CurrencyViewModel = viewModel()

        val monetaryUnitList = viewModel.currencies
        var isExpanded by remember { mutableStateOf(false) }
        var isExpandedToConvert by remember { mutableStateOf(false) }
        var selectedOption by remember { mutableStateOf("USD") }
        var selectedOptionToConvert by remember { mutableStateOf("VND") }
        var converterHistoryList = viewModel.converterHistoryList

        LaunchedEffect(selectedOption) {
            viewModel.fetchRates("4e27574ac7250ddfc1527a65", selectedOption)
        }

        val rate = viewModel.rates[selectedOptionToConvert] ?: 0.0
        var inputValue by remember { mutableStateOf("") }
        var result by remember { mutableStateOf("") }
        Column (
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .fillMaxWidth()

            ) {
                Column (
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = inputValue,
                            onValueChange = {inputValue = it},
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = { Text("$selectedOption") },
                            placeholder = { Text("Enter something...") },
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent
                            ),
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier.clickable {
                                isExpanded = true
                            }
                        ) {
                            Text(text = "$selectedOption", style = TextStyle(
                                fontSize = 24.sp
                            ))
                        }
                        DropdownMenu(
                            expanded = isExpanded,
                            onDismissRequest = { isExpanded = false },

                        ) {
                            monetaryUnitList.forEach { monetaryUnit ->
                                DropdownMenuItem(
                                    text = { Text(monetaryUnit) },
                                    onClick = {
                                        selectedOption = monetaryUnit
                                        isExpanded = false
                                    }
                                )
                            }
                        }
                    }
                    Row (
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(painter = painterResource(id =R.drawable.baseline_cached_24), contentDescription = "Dropdown icon")
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = result,
                            onValueChange = {result = it},
                            label = { Text("") },
                            placeholder = { Text("0") },
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent
                            ),
                            modifier = Modifier.fillMaxWidth(0.8f),
                            enabled = false
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier.clickable {
                                isExpandedToConvert = true
                            }
                        ) {
                            Text(text = "$selectedOptionToConvert", style = TextStyle(
                                fontSize = 24.sp
                            ))
                        }
                        DropdownMenu(
                            expanded = isExpandedToConvert,
                            onDismissRequest = { isExpandedToConvert = false }
                        ) {
                            monetaryUnitList.forEach { monetaryUnit ->
                                DropdownMenuItem(
                                    text = { Text(monetaryUnit) },
                                    onClick = {
                                        selectedOptionToConvert = monetaryUnit
                                        isExpandedToConvert = false
                                    }
                                )
                            }
                        }
                    }
                }

            }
            ElevatedButton(
                onClick = {
                    val value = inputValue.toDoubleOrNull() ?: 0.0
                    result = (value * rate).toString()
                    viewModel.addConverterHistory(ConverterHistory(unit = selectedOption, valueToConvert = inputValue, unitIsConverted = selectedOptionToConvert, valueAfterConverted = result))
                },
                modifier = Modifier.padding(8.dp).fillMaxWidth()
            ) {
                Text(text = "Convert")
            }
            Row (
                modifier = Modifier.padding(8.dp)
            ){
                Icon(painter = painterResource(id =R.drawable.baseline_history_24), contentDescription = "History")
                Text(text = " Converter history")
            }
            LazyColumn(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(converterHistoryList) { item ->  // Sử dụng items thay vì forEach
                    HistoryItem(converterItem = item)
                }
            }
        }
    }
}

@Composable
fun HistoryItem(converterItem: ConverterHistory) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .height(50.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,  // Căn giữa nội dung theo chiều dọc và ngang
            modifier = Modifier.fillMaxSize()  // Đảm bảo Box chiếm hết diện tích Card
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box {
                    Row {
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = converterItem.valueToConvert,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = converterItem.unit,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                        )
                    }
                }
                Icon(painter = painterResource(id = R.drawable.outline_navigate_next_24), contentDescription = "convert to")
                Box {
                    Row {
                        Text(
                            text = converterItem.valueAfterConverted,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = converterItem.unitIsConverted,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            }
        }
    }
}
