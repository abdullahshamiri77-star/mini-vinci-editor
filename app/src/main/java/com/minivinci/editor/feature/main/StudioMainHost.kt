package com.minivinci.editor.feature.main // تأكد أن الحزمة تطابق مكان الملف لديك

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.minivinci.editor.feature.color.ColorScreen
import com.minivinci.editor.feature.color.ColorViewModel
import com.minivinci.editor.feature.edit.EditScreen
import com.minivinci.editor.feature.edit.EditViewModel

enum class StudioScreen {
    EDIT,
    COLOR,
    FUSION
}

@Composable
fun StudioMainHost(
    editViewModel: EditViewModel,
    colorViewModel: ColorViewModel
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
                    label = { Text("Edit & Cut", color = Color.White) }
                )
                NavigationBarItem(
                    selected = currentScreen == StudioScreen.COLOR,
                    onClick = { currentScreen = StudioScreen.COLOR },
                    icon = { Text("🎨") },
                    label = { Text("Color Grade", color = Color.White) }
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
                    // المرحلة القادمة للمؤثرات البصرية
                }
            }
        }
    }
}
