package com.minivinci.editor.feature.export.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.minivinci.editor.feature.export.domain.model.ExportState
import com.minivinci.editor.feature.export.presentation.viewmodel.ExportViewModel
import com.minivinci.editor.feature.timeline.domain.model.TimelineProject

@Composable
fun ExportScreen(
    viewModel: ExportViewModel,
    project: TimelineProject,
    onExportFinished: (String) -> Unit
) {
    val exportState by viewModel.exportState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🚀 تصدير الفيديو - VisionStudio",
            color = Color.White,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        when (val state = exportState) {
            is ExportState.Idle -> {
                Button(
                    onClick = {
                        val path = "/storage/emulated/0/Movies/VisionStudio_${System.currentTimeMillis()}.mp4"
                        viewModel.startExportProcess(project, path)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1))
                ) {
                    Text(text = "بدء عملية الرندر والتصدير", color = Color.White)
                }
            }
            is ExportState.Progress -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = Color.Cyan)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "جاري التصدير... ${state.progressPercentage}%",
                        color = Color.White
                    )
                }
            }
            is ExportState.Success -> {
                Text(
                    text = "✅ تم التصدير بنجاح!\nالمسار: ${state.finalPath}",
                    color = Color.Green,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { 
                    onExportFinished(state.finalPath)
                    viewModel.resetExportState() 
                }) {
                    Text(text = "تم")
                }
            }
            is ExportState.Error -> {
                Text(
                    text = "❌ حدث خطأ: ${state.message}",
                    color = Color.Red
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { viewModel.resetExportState() }) {
                    Text(text = "إعادة المحاولة")
                }
            }
        }
    }
}
