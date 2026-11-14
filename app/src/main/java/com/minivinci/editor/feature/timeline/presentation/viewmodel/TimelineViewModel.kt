package com.minivinci.editor.feature.timeline.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.minivinci.editor.feature.timeline.domain.model.TimelineClip
import com.minivinci.editor.feature.timeline.domain.model.TimelineProject
import com.minivinci.editor.feature.timeline.domain.model.TimelineTrack
import com.minivinci.editor.feature.timeline.domain.model.TrackType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TimelineViewModel : ViewModel() {

    // حالة المشروع تبدأ بمسارات حقيقية جاهزة (Video 1 و Audio 1)
    private val _projectState = MutableStateFlow(
        TimelineProject(
            projectName = "VisionStudio Project",
            fps = 30,
            tracks = listOf(
                TimelineTrack(
                    trackId = "v1",
                    trackType = TrackType.VIDEO,
                    clips = listOf(
                        // مقطع افتراضي تجريبي يظهر فوراً لبرهان التشغيل
                        TimelineClip(
                            clipId = "sample_clip_1",
                            mediaPath = "sample.mp4",
                            startTimeUs = 0L,
                            durationUs = 5_000_000L,
                            outPointUs = 5_000_000L
                        )
                    )
                ),
                TimelineTrack(
                    trackId = "a1",
                    trackType = TrackType.AUDIO,
                    clips = emptyList()
                )
            )
        )
    )
    val projectState: StateFlow<TimelineProject> = _projectState.asStateFlow()

    // إضافة مقطع حقيقي للمسار المحدد
    fun addClip(trackId: String, clipId: String, mediaPath: String, startTimeUs: Long, durationUs: Long, outPointUs: Long) {
        _projectState.update { currentProject ->
            val updatedTracks = currentProject.tracks.map { track ->
                if (track.trackId == trackId) {
                    val newClip = TimelineClip(
                        clipId = clipId,
                        mediaPath = mediaPath,
                        startTimeUs = startTimeUs,
                        durationUs = durationUs,
                        outPointUs = outPointUs
                    )
                    track.copy(clips = track.clips + newClip)
                } else {
                    track
                }
            }
            currentProject.copy(tracks = updatedTracks)
        }
    }

    // قص المقطع (Blade)
    fun splitClip(trackId: String, clipId: String, splitTimeUs: Long) {
        // منطق القص الفعلي سيتم تنفيذه وتحديث الواجهة فوراً
    }
}
