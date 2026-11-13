package com.minivinci.editor.feature.fusion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FusionScreen(viewModel: FusionViewModel) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("✨ Fusion VFX Workspace", color = Color.White, style = MaterialTheme.typography.titleLarge)
        
        Spacer(modifier = Modifier.height(32.dp))

        Text("Opacity: ${String.format("%.2f", state.opacity)}", color = Color.LightGray)
        Slider(
            value = state.opacity,
            onValueChange = { viewModel.updateOpacity(it) },
            valueRange = 0f..1f
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Scale: ${String.format("%.2f", state.scale)}", color = Color.LightGray)
        Slider(
            value = state.scale,
            onValueChange = { viewModel.updateScale(it) },
            valueRange = 0.5f..2f
        )
    }
}

