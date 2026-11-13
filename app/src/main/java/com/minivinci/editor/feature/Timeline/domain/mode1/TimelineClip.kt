package com.minivinci.editor.feature.timeline.domain.model

data class TimelineClip(
    val id: String,
    val mediaUri: String,
    val clipType: TimelineClipType,
    val startTimeMs: Long,      // نقطة البداية على التايملاين العام
    val durationMs: Long,       // مدة العرض
    val inPointMs: Long = 0L,   // نقطة الاقتصاص من الملف الأصلي (Trim In)
    val outPointMs: Long,       // نقطة النهاية من الملف الأصلي (Trim Out)
    val volume: Float = 1.0f    // مستوى الصوت الخاص بالمقطع
)

enum class TimelineClipType {
    VIDEO,
    AUDIO,
    IMAGE
}
