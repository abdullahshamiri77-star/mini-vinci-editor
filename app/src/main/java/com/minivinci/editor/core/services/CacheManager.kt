package com.minivinci.editor.core.services

import android.content.Context
import java.io.File

class CacheManager(private val context: Context) {

    // تحديد مجلد خاص بالتخزين المؤقت داخل ذاكرة التطبيق الداخلية
    private val cacheDir: File = File(context.cacheDir, "vision_studio_cache").apply {
        if (!exists()) mkdirs()
    }

    // الحصول على ملف مؤقت جديد بمعرف فريد
    fun createTempFile(prefix: String, extension: String): File {
        return File.createTempFile(prefix, extension, cacheDir)
    }

    // تنظيف الذاكرة المؤقتة للمشروع عند الحاجة لتوفير مساحة الجهاز
    fun clearCache() {
        if (cacheDir.exists()) {
            cacheDir.listFiles()?.forEach { it.delete() }
        }
    }
}
