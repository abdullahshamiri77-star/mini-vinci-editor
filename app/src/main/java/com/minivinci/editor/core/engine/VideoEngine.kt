package com.minivinci.editor.core.engine

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VideoEngine(private val context: Context) {

    // حالة التشغيل الحالية للمحرك (هل الفيديو قيد التشغيل أم متوقف)
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    // الإطار الحالي المعروض على شاشة المعاينة (Playhead Position)
    private val _currentFrame = MutableStateFlow(0L)
    val currentFrame: StateFlow<Long> = _currentFrame.asStateFlow()

    // إجمالي عدد إطارات المشروع أو التايم لاين
    private var totalFrames: Long = 300L // افتراضياً 10 ثوانٍ بمعدل 30 إطاراً في الثانية

    /**
     * بدء تشغيل الفيديو والمعاينة
     */
    fun play() {
        _isPlaying.value = true
        // سيتم ربط حلقة التشغيل (Playback Loop) هنا لاحقاً
    }

    /**
     * إيقاف مؤقت للتشغيل
     */
    fun pause() {
        _isPlaying.value = false
    }

    /**
     * الانتقال إلى إطار محدد بدقة (Seek)
     */
    fun seekTo(frame: Long) {
        _currentFrame.value = frame.coerceIn(0L, totalFrames)
    }

    /**
     * تحديث إجمالي إطارات التايم لاين بناءً على المشروع
     */
    fun setTimelineDuration(frames: Long) {
        totalFrames = frames
    }

    /**
     * تحرير الموارد عند إغلاق المحرك
     */
    fun release() {
        _isPlaying.value = false
        // تنظيف ذاكرة التخزين المؤقت أو عارض الرسومات (Surface/ExoPlayer)
    }
}
