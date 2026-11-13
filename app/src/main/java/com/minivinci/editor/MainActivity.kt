package com.minivinci.editor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.minivinci.editor.feature.export.presentation.viewmodel.ExportViewModel
import com.minivinci.editor.feature.timeline.presentation.viewmodel.TimelineViewModel
import com.minivinci.editor.navigation.VisionStudioNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    
                    // حقن الـ ViewModels الخاصة بالمحركات
                    val timelineViewModel: TimelineViewModel = viewModel()
                    val exportViewModel: ExportViewModel = viewModel()

                    // استدعاء مضيف التنقل المركزي لـ VisionStudio
                    VisionStudioNavHost(
                        navController = navController,
                        timelineViewModel = timelineViewModel,
                        exportViewModel = exportViewModel
                    )
                }
            }
        }
    }
}
