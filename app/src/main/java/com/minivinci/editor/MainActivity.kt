package com.minivinci.editor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.minivinci.editor.ui.screens.MainEditorScreen
import com.minivinci.editor.ui.components.MediaImportView
import com.minivinci.editor.utils.VideoProcessor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // حالة للتحكم في إظهار أو إخفاء نافذة استيراد الوسائط
            var showImportDialog by remember { mutableStateOf(false) }

            // عرض واجهة المونتاج الرئيسية
            MainEditorScreen(
                onImportClick = {
                    // عند الضغط على زر استيراد، تظهر نافذة اختيار الوسائط
                    showImportDialog = true
                },
                onSplitClick = {
                    // تجربة محرك القص (VideoProcessor)
                    // VideoProcessor.splitClipAtTime(...)
                },
                onExtractAudioClick = {
                    // تجربة محرك فصل الصوت
                    // VideoProcessor.extractAudioFromVideo(...)
                }
            )

            // إذا كانت الحالة تقتضي إظهار نافذة الاستيراد، يتم عرضها فوق الشاشة
            if (showImportDialog) {
                MediaImportView(
                    onDismiss = { showImportDialog = false },
                    onMediaSelected = { selectedItem ->
                        // تم اختيار مقطع، نقوم بإغلاق النافذة (يمكنك هنا إضافته للمسار لاحقاً)
                        showImportDialog = false
                    }
                )
            }
        }
    }
}
