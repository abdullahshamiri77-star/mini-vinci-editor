package com.minivinci.editor.feature.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.minivinci.editor.feature.color.ColorScreen
import com.minivinci.editor.feature.color.ColorViewModel
import com.minivinci.editor.feature.edit.EditScreen
import com.minivinci.editor.feature.edit.EditViewModel
import com.minivinci.editor.feature.fusion.FusionScreen
import com.minivinci.editor.feature.fusion.FusionViewModel

// تعريف الشاشات الأساسية (مُعرّف هنا مرة واحدة فقط لتجنب التكرار)
enum class StudioScreen {
    EDIT,
    COLOR,
    FUSION
}

@Composable
fun StudioMainHost(
    editViewModel: EditViewModel,
    colorViewModel: ColorViewModel,
    fusionViewModel: FusionViewModel
) {
    var currentScreen by remember { mutableStateOf(StudioScreen.EDIT) }

    Scaffold(
        bottomBar = {
            // شريط التنقل السفلي المستوحى من مساحات عمل DaVinci Resolve
            NavigationBar(
                containerColor = Color(0xFF1E1E1E)
            ) {
                NavigationBarItem(
                    selected = currentScreen == StudioScreen.EDIT,
                    onClick = { currentScreen = StudioScreen.EDIT },
                    icon = { Text("🎬") },
                    label = { Text("Edit", color = Color.White) }
                )
                NavigationBarItem(
                    selected = currentScreen == StudioScreen.COLOR,
                    onClick = { currentScreen = StudioScreen.COLOR },
                    icon = { Text("🎨") },
                    label = { Text("Color", color = Color.White) }
                )
                NavigationBarItem(
                    selected = currentScreen == StudioScreen.FUSION,
                    onClick = { currentScreen = StudioScreen.FUSION },
                    icon = { Text("✨") },
                    label = { Text("Fusion", color = Color.White) }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentScreen) {
                StudioScreen.EDIT -> {
                    EditScreen(viewModel = editViewModel)
                }
                StudioScreen.COLOR -> {
                    ColorScreen(viewModel = colorViewModel)
                }
                StudioScreen.FUSION -> {
                    FusionScreen(viewModel = fusionViewModel)
                }
            }
        }
    }
}
