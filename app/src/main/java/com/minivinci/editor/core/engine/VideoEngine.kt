package com.minivinci.editor.core.engine

import android.content.Context
import android.net.Uri

class VideoEngine(private val context: Context) {
    
    private var currentSourceUri: Uri? = null

    /**
     * تجهيز وتحميل ملف الفيديو للمعاينة في التايم لاين
     */
    fun loadMediaSource(uri: Uri) {
        currentSourceUri = uri
        // سيتم ربط هذا لاحقاً بـ ExoPlayer أو محرك المعالجة الحقيقي
    }

    /**
     * الحصول على المسار الحالي المجهز للمعالجة
     */
    fun getCurrentSource(): Uri? = currentSourceUri

    /**
     * تنظيف الموارد وتحرير الذاكرة عند إغلاق المشروع
     */
    fun release() {
        currentSourceUri = null
    }
}
