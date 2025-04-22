package com.nguyenminhkhang.moneymind.components

import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.core.entry.entryOf
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer

@Composable
fun WeeklyBarChart(weeklySpending: List<Float>, modifier: Modifier = Modifier) {
    // Danh sách các ngày trong tuần
    val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thi", "Fri", "Sat", "Sun")

    // Tạo entries từ weeklySpending
    val entries = weeklySpending.mapIndexed { index, value ->
        entryOf(index.toFloat(), value)
    }

    // Tạo model cho biểu đồ
    val chartModelProducer = remember { ChartEntryModelProducer(entries) }

    // Tùy chỉnh trục ngang với nhãn ngày trong tuần
    val bottomAxis = rememberBottomAxis(
        valueFormatter = { value, _ ->
            val index = value.toInt().coerceIn(0, daysOfWeek.size - 1)
            daysOfWeek[index]
        }
    )

    // Cấu hình biểu đồ cột
    val columnChart = columnChart()

    // Vẽ biểu đồ
    Chart(
        chart = columnChart,
        chartModelProducer = chartModelProducer,
        modifier = modifier,
        bottomAxis = bottomAxis // Áp dụng trục ngang tùy chỉnh
    )
}