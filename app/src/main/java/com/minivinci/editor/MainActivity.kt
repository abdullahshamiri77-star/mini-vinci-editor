package com.minivinci.editor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.minivinci.editor.feature.timeline.presentation.screen.TimelineScreen
import com.minivinci.editor.feature.timeline.presentation.viewmodel.TimelineViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // ربط مباشر ونظيف مع الـ TimelineViewModel و TimelineScreen الاحترافية
                    val timelineViewModel: TimelineViewModel = viewModel()
                    TimelineScreen(viewModel = timelineViewModel)
                }
            }
        }
    }
}
