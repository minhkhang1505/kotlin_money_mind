package com.nguyenminhkhang.moneymind.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryOf


@Composable
fun MonthlyLineChart(
    incomePerMonth: List<Float>,
    expensePerMonth: List<Float>,
    modifier: Modifier = Modifier
) {
    // Nhãn cho trục ngang (12 tháng)
    val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    // Tạo entries cho từng đường
    val incomeEntries = incomePerMonth.mapIndexed { index, value ->
        entryOf(x = index.toFloat(), y = value)
    }
    val expenseEntries = expensePerMonth.mapIndexed { index, value ->
        entryOf(x = index.toFloat(), y = value)
    }

    // Gộp entries vào một danh sách
    val allEntries = listOf(incomeEntries, expenseEntries)

    // Model producer (2 series)
    val chartModelProducer = remember { ChartEntryModelProducer(allEntries) }

    // Tùy chỉnh trục ngang với nhãn tháng
    val bottomAxis = rememberBottomAxis(
        valueFormatter = { value, _ ->
            val index = value.toInt().coerceIn(0, months.lastIndex)
            months[index]
        }
    )

    // Vẽ biểu đồ đường với 2 line
    Chart(
        chart = lineChart(), // Mặc định vẽ nhiều đường nếu nhiều entry list
        chartModelProducer = chartModelProducer,
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp),
        bottomAxis = bottomAxis
    )
}
