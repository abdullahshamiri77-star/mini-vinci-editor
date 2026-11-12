package com.minivinci.editor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.minivinci.editor.core.engine.AudioEngine
import com.minivinci.editor.core.engine.VideoEngine
import com.minivinci.editor.ui.workspace.MainEditorScreen

class MainActivity : ComponentActivity() {

    private lateinit var videoEngine: VideoEngine
    private lateinit var audioEngine: AudioEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // تهيئة محركات VisionStudio الأساسية
        videoEngine = VideoEngine(applicationContext)
        audioEngine = AudioEngine(applicationContext)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // تشغيل واجهة المونتاج الاحترافية ومررنا إليها المحركات
                    MainEditorScreen(
                        videoEngine = videoEngine,
                        audioEngine = audioEngine
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // تنظيف موارد المحركات عند إغلاق التطبيق لضمان عدم تسريب الذاكرة
        videoEngine.release()
        audioEngine.release()
    }
}
