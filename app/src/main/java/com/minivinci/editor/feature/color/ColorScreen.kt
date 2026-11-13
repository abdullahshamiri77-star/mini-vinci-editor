package com.minivinci.editor.feature.color

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorScreen(
    viewModel: ColorViewModel,
    onNavigateBack: () -> Unit = {}
) {
    val colorParams by viewModel.colorParameters.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("VisionStudio - Color Grading Page", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1E1E1E))
            )
        },
        containerColor = Color(0xFF121212)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // شاشة معاينة الفيديو الملونة (Color Grade Preview Window)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Black)
                    .border(1.dp, Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Cinematic Color Viewer\nTemp: ${colorParams.temperature.toInt()}K | Sat: %.2f".format(colorParams.saturation),
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
            }

            // قسم التحكم الأساسي (Primary Wheels & Sliders)
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Primary Adjustments", color = Color.White, fontSize = 16.sp)

                    // شريط تحكم التعريض (Exposure)
                    ColorSliderControl(
                        label = "Exposure",
                        value = colorParams.exposure,
                        range = -2f..2f,
                        onValueChanged = { viewModel.updateExposure(it) }
                    )

                    // شريط تحكم التباين (Contrast)
                    ColorSliderControl(
                        label = "Contrast",
                        value = colorParams.contrast,
                        range = 0.5f..2f,
                        onValueChanged = { viewModel.updateContrast(it) }
                    )

                    // شريط حرارة اللون (Temperature)
                    ColorSliderControl(
                        label = "Temperature (K)",
                        value = colorParams.temperature,
                        range = 2000f..10000f,
                        onValueChanged = { viewModel.updateTemperature(it) }
                    )

                    // شريط تشبع الألوان (Saturation)
                    ColorSliderControl(
                        label = "Saturation",
                        value = colorParams.saturation,
                        range = 0f..2f,
                        onValueChanged = { viewModel.updateSaturation(it) }
                    )
                }
            }

            // أزرار الحفظ أو إعادة التعيين
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9534F)),
                    onClick = { viewModel.resetColors() }
                ) {
                    Text("Reset Grade")
                }

                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CA64C)),
                    onClick = { /* تطبيق وحفظ التلوين في المشروع */ }
                ) {
                    Text("Save / Apply")
                }
            }
        }
    }
}

@Composable
fun ColorSliderControl(
    label: String,
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    onValueChanged: (Float) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, color = Color.LightGray, fontSize = 12.sp)
            Text("%.2f".format(value), color = Color.White, fontSize = 12.sp)
        }
        Slider(
            value = value,
            onValueChange = onValueChanged,
            valueRange = range,
            colors = SliderDefaults.colors(thumbColor = Color(0xFF2E6DA4), activeTrackColor = Color(0xFF2E6DA4))
        )
    }
}
