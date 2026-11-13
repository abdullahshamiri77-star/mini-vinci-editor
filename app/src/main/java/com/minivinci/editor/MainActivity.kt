package com.minivinci.editor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.minivinci.editor.core.engine.AudioEngine
import com.minivinci.editor.core.engine.VideoEngine
import com.minivinci.editor.feature.color.ColorViewModel
import com.minivinci.editor.feature.edit.EditViewModel
import com.minivinci.editor.feature.fusion.FusionViewModel
import com.minivinci.editor.feature.main.StudioMainHost

class MainActivity : ComponentActivity() {

    private lateinit var videoEngine: VideoEngine
    private lateinit var audioEngine: AudioEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        videoEngine = VideoEngine(applicationContext)
        audioEngine = AudioEngine(applicationContext)

        setContent {
            val editViewModel: EditViewModel = viewModel()
            val colorViewModel: ColorViewModel = viewModel()
            val fusionViewModel: FusionViewModel = viewModel()

            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF121212)
                ) {
                    StudioMainHost(
                        editViewModel = editViewModel,
                        colorViewModel = colorViewModel,
                        fusionViewModel = fusionViewModel
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        videoEngine.release()
        audioEngine.release()
    }
}
