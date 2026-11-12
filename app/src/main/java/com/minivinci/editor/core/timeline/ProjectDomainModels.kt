package com.minivinci.editor.core.timeline

// 1. نموذج المشروع الشامل
data class Project(
    val id: String,
    val name: String,
    val settings: ProjectSettings,
    val timeline: Timeline,
    val mediaAssets: List<MediaAsset>
)

// 2. إعدادات المشروع (الدقة، معدل الإطارات، عينة الصوت)
data class ProjectSettings(
    val width: Int = 1920,
    val height: Int = 1080,
    val frameRateNumerator: Int = 30,
    val frameRateDenominator: Int = 1,
    val audioSampleRate: Int = 48000
)

// 3. نموذج التايم لاين الكامل
data class Timeline(
    val id: String,
    val tracks: List<Track>,
    val durationFrames: Long
)

// 4. نموذج المسار (فيديو، صوت، نص، مؤثرات)
data class Track(
    val id: String,
    val type: TrackType,
    val clips: List<Clip>
)

enum class TrackType {
    VIDEO,
    AUDIO,
    TEXT,
    EFFECT
}

// 5. نموذج المقطع داخل المسار
data class Clip(
    val id: String,
    val mediaId: String?,
    val startFrame: Long,
    val sourceInFrame: Long,
    val durationFrames: Long,
    val effects: List<Effect> = emptyList()
)

// 6. أصل الوسائط (فيديو، صوت، صورة)
data class MediaAsset(
    val id: String,
    val uri: String,
    val type: MediaType,
    val durationFrames: Long?,
    val width: Int?,
    val height: Int?
)

enum class MediaType {
    VIDEO,
    AUDIO,
    IMAGE,
    UNKNOWN
}

// 7. نموذج المؤثرات المرئية أو الصوتية
data class Effect(
    val id: String,
    val type: String,
    val enabled: Boolean,
    val parameters: Map<String, Float>
)
