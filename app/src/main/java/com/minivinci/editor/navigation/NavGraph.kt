package com.minivinci.editor.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.minivinci.editor.feature.timeline.presentation.screen.TimelineScreen
import com.minivinci.editor.feature.timeline.presentation.viewmodel.TimelineViewModel
import com.minivinci.editor.feature.export.presentation.screen.ExportScreen
import com.minivinci.editor.feature.export.presentation.viewmodel.ExportViewModel

sealed class Screen(val route: String) {
    object Edit : Screen("edit")
    object Color : Screen("color")
    object Fusion : Screen("fusion")
    object Timeline : Screen("timeline")
    object Export : Screen("export")
}

@Composable
fun VisionStudioNavHost(
    navController: NavHostController,
    timelineViewModel: TimelineViewModel,
    exportViewModel: ExportViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Timeline.route
    ) {
        // شاشة التايملاين الرئيسية
        composable(Screen.Timeline.route) {
            TimelineScreen(viewModel = timelineViewModel)
        }

        // مساحة التعديل (Edit Workspace)
        composable(Screen.Edit.route) {
            WorkspacePlaceholder(title = "🎬 Edit Workspace (تحرير الفيديو)")
        }

        // مساحة التلوين السينمائي (Color Grading - DaVinci Style)
        composable(Screen.Color.route) {
            WorkspacePlaceholder(title = "🎨 Color Workspace (تصحيح الألوان)")
        }

        // مساحة المؤثرات البصرية (Fusion Workspace)
        composable(Screen.Fusion.route) {
            WorkspacePlaceholder(title = "✨ Fusion Workspace (المؤثرات البصرية)")
        }

        // شاشة التصدير والرندر
        composable(Screen.Export.route) {
            val project = timelineViewModel.projectState.value
            ExportScreen(
                viewModel = exportViewModel,
                project = project,
                onExportFinished = { finalPath ->
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun WorkspacePlaceholder(title: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.titleLarge
        )
    }
}
