
package com.minivinci.editor.feature.timeline.domain.model

/**
 * يمثل مشروع التايملاين المتقدم بجميع مساراته ومقاطعه (على غرار DaVinci Resolve)
 */
data class AdvancedTimelineProject(
    val projectId: String,
    val projectName: String,
    val fps: Int = 30,
    val tracks: List<TimelineTrack> = emptyList()
)

/**
 * يمثل المسار الواحد (إما مسار فيديو أو مسار صوت)
 */
data class TimelineTrack(
    val trackId: String,
    val trackType: TrackType,
    val clips: List<TimelineClip> = emptyList(),
    val isMuted: Boolean = false,
    val isLocked: Boolean = false
)

/**
 * أنواع المسارات المدعومة في المحرك
 */
enum class TrackType {
    VIDEO,
    AUDIO
}

/**
 * يمثل المقطع الفردي على التايملاين (فيديو أو صوت)
 */
data class TimelineClip(
    val clipId: String,
    val mediaPath: String,
    val startTimeUs: Long,       // نقطة البداية الفعلية على التايملاين (بالميكروثانية)
    val durationUs: Long,        // مدة العرض على التايملاين
    val inPointUs: Long = 0,     // نقطة القطع الداخلية من الملف الأصلي (بداية المقطع)
    val outPointUs: Long,        // نقطة النهاية الداخلية من الملف الأصلي
    val speed: Float = 1.0f      // سرعة تشغيل المقطع
)
