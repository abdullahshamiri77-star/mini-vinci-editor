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

    private val _projectState = MutableStateFlow(
        TimelineProject(
            projectId = "vision_studio_proj_01",
            projectName = "VisionStudio Pro Project",
            fps = 30,
            tracks = listOf(
                TimelineTrack(trackId = "v4", trackType = TrackType.VIDEO, clips = emptyList()),
                TimelineTrack(trackId = "v3", trackType = TrackType.VIDEO, clips = emptyList()),
                TimelineTrack(trackId = "v2", trackType = TrackType.VIDEO, clips = emptyList()),
                TimelineTrack(trackId = "v1", trackType = TrackType.VIDEO, clips = emptyList()),
                TimelineTrack(trackId = "a1", trackType = TrackType.AUDIO, clips = emptyList())
            )
        )
    )
    val projectState: StateFlow<TimelineProject> = _projectState.asStateFlow()

    private val _currentPositionUs = MutableStateFlow(0L)
    val currentPositionUs: StateFlow<Long> = _currentPositionUs.asStateFlow()

    private val _selectedClipId = MutableStateFlow<String?>(null)
    val selectedClipId: StateFlow<String?> = _selectedClipId.asStateFlow()

    // الدالة موحدة باسم addRealClip لضمان توافقها التام مع الشاشة
    fun addRealClip(mediaUri: String, trackId: String = "v1") {
        _projectState.update { currentProject ->
            val updatedTracks = currentProject.tracks.map { track ->
                if (track.trackId == trackId) {
                    val newClip = TimelineClip(
                        clipId = "clip_${System.currentTimeMillis()}",
                        mediaPath = mediaUri,
                        startTimeUs = track.clips.sumOf { it.durationUs },
                        durationUs = 5_000_000L,
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

    fun seekTo(positionUs: Long) {
        _currentPositionUs.value = positionUs
    }

    fun selectClip(clipId: String) {
        _selectedClipId.value = clipId
    }

    fun splitClipAtPlayhead(trackId: String, clipId: String) {
        // منطق القص
    }
}
