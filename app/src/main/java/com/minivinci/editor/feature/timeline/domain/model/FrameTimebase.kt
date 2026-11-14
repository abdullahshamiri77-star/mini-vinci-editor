package com.minivinci.editor.feature.timeline.domain.model

/**
 * مسؤول عن التحويل الدقيق بين رقم الإطار (Frame Index) والزمن بالميكروثانية (Microseconds)
 * باستخدام حسابات صحيحة حصراً (Long) لتفادي أي أخطاء تراكمية في المونتاج.
 */
class FrameTimebase(val frameRate: FrameRate) {

    companion object {
        private const val US_PER_SECOND = 1_000_000L
    }

    /**
     * تحويل رقم الإطار (Frame Index) إلى ميكروثانية
     * الزمن = (frameIndex * denominator * 1,000,000) / numerator
     */
    fun frameToMicroseconds(frameIndex: Long): Long {
        return (frameIndex * frameRate.denominator * US_PER_SECOND) / frameRate.numerator
    }

    /**
     * تحويل الزمن بالميكروثانية إلى رقم الإطار الأقرب
     * الإطار = (microseconds * numerator) / (denominator * 1,000,000)
     */
    fun microsecondsToFrame(microseconds: Long): Long {
        val denominatorUs = frameRate.denominator.toLong() * US_PER_SECOND
        // إضافة نصف المقام لضمان التقريب السليم لأقرب إطار (Rounding to nearest)
        return (microseconds * frameRate.numerator + (denominatorUs / 2)) / denominatorUs
    }
}
