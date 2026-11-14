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

    // حالة المشروع تبدأ بمسارات حقيقية وجاهزة للملفات المستوردة
    private val _projectState = MutableStateFlow(
        TimelineProject(
            projectId = "project_1",
            projectName = "VisionStudio Project",
            fps = 30,
            tracks = listOf(
                TimelineTrack(
                    trackId = "v1",
                    trackType = TrackType.VIDEO,
                    clips = emptyList() // تبدأ فارغة لتستقبل فيديوهاتك الحقيقية عند الاستيراد
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

    // دالة حقيقية لاستيراد وإضافة فيديو من ذاكرة الهاتف إلى التايملاين
    fun addRealClip(mediaUri: String, trackId: String = "v1") {
        _projectState.update { currentProject ->
            val updatedTracks = currentProject.tracks.map { track ->
                if (track.trackId == trackId) {
                    val newClip = TimelineClip(
                        clipId = "clip_${System.currentTimeMillis()}",
                        mediaPath = mediaUri,
                        startTimeUs = track.clips.sumOf { it.durationUs }, // يوضع تلقائياً في نهاية المقطع السابق
                        durationUs = 5_000_000L, // مدة افتراضية 5 ثوانٍ
                        outPointUs = 5_000_000L
                    )
                    track.copy(clips = track.clips + newClip)
                } else {
                    track
                }
            }
            currentProject.copy(tracks = updatedTracks)
        }
    }

    // دالة القص (Blade)
    fun splitClip(trackId: String, clipId: String, splitTimeUs: Long) {
        // منطق القص التفاعلي
    }
}
