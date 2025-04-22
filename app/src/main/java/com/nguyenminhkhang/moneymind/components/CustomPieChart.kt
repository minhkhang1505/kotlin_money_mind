package com.nguyenminhkhang.moneymind.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.xr.runtime.math.toDegrees
import kotlinx.coroutines.launch
import java.lang.StrictMath.atan2
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.min

@Composable
fun CustomPieChart(
    data: List<Float>,
    labels: List<String>,
    modifier: Modifier = Modifier
) {
    val total = data.sum()
    val colors = List(data.size) { index ->
        Color.hsl((index * 60f) % 360f, 0.7f, 0.5f)
    }

    val animatedScale = remember { Animatable(0f) }
    val animatedAlpha = remember { Animatable(0f) }
    val selectedSlice = remember { mutableStateOf(-1) } // Index của phần được chọn

    LaunchedEffect(Unit) {
        launch {
            animatedScale.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 800,
                    easing = FastOutSlowInEasing
                )
            )
        }
        launch {
            animatedAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 800,
                    easing = FastOutSlowInEasing
                )
            )
        }
    }

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        // Pie Chart with user interaction
        BoxWithConstraints(modifier = Modifier.size(200.dp)) {
            val boxWidth = constraints.maxWidth.toFloat()
            val boxHeight = constraints.maxHeight.toFloat()
            val center = Offset(boxWidth / 2, boxHeight / 2)
            val radius = min(boxWidth, boxHeight) / 2 * 0.8f

            var startAngle = 0f
            Canvas(modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val angle = atan2(offset.y - center.y, offset.x - center.x)
                        val angleNormalized = if (angle < 0) angle + 360 else angle

                        var accumulatedAngle = 0f
                        data.forEachIndexed { index, value ->
                            val sweepAngle = (value / total) * 360f * animatedScale.value
                            if (angleNormalized in accumulatedAngle..(accumulatedAngle + sweepAngle)) {
                                selectedSlice.value = index
                            }
                            accumulatedAngle += sweepAngle
                        }
                    }
                }) {
                data.forEachIndexed { index, value ->
                    val sweepAngle = (value / total) * 360f * animatedScale.value
                    drawArc(
                        color = colors[index],
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = true,
                        topLeft = Offset(center.x - radius, center.y - radius),
                        size = Size(radius * 2, radius * 2)
                    )
                    startAngle += sweepAngle
                }
            }

            // Hiển thị phần được chọn (nếu có)
//            if (selectedSlice.value != -1) {
//                val selectedLabel = labels[selectedSlice.value]
//                val selectedValue = data[selectedSlice.value]
//                Text(
//                    text = "Đã chọn: $selectedLabel\nTỷ lệ: ${(selectedValue / total) * 100}%",
//                    modifier = Modifier.align(Alignment.BottomCenter),
//                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp),
//                    color = MaterialTheme.colorScheme.primary
//                )
//            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Legend (Chú thích không có thay đổi)
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            data.forEachIndexed { index, value ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(colors[index], shape = CircleShape)
                    )
                    Text(
                        text = "${labels[index]} (${((value / total) * 100).toInt()}%)",
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}


// Hàm tiện ích để đổi độ sang radian
private fun Double.toRadians() = this * PI / 180