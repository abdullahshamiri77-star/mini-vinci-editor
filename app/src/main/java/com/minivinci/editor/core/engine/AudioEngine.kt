package com.minivinci.editor.core.engine

import android.content.Context
import android.net.Uri

class AudioEngine(private val context: Context) {

    private var currentAudioUri: Uri? = null
    private var volumeLevel: Float = 1.0f // مستوى الصوت الافتراضي (100%)

    /**
     * تحميل وتجهيز الملف الصوتي للمسار A1
     */
    fun loadAudioSource(uri: Uri) {
        currentAudioUri = uri
        // سيتم ربط هذا لاحقاً بمعالج الصوتيات ومزج المسارات
    }

    /**
     * ضبط مستوى الصوت للمقطع الصوتي
     */
    fun setVolume(level: Float) {
        volumeLevel = level.coerceIn(0.0f, 2.0f)
    }

    fun getVolume(): Float = volumeLevel

    /**
     * تنظيف الموارد وتحرير الذاكرة
     */
    fun release() {
        currentAudioUri = null
    }
}
