package com.minivinci.editor.feature.timeline.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minivinci.editor.feature.timeline.domain.engine.AdvancedTimelineEngine
import com.minivinci.editor.feature.timeline.domain.model.TimelineProject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TimelineViewModel : ViewModel() {

    private val timelineEngine = AdvancedTimelineEngine()

    val projectState: StateFlow<TimelineProject> = timelineEngine.projectState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = timelineEngine.projectState.value
        )

    fun addClip(trackId: String, mediaPath: String, durationUs: Long) {
        viewModelScope.launch {
            timelineEngine.addClipToTrack(trackId, mediaPath, durationUs)
        }
    }

    fun splitClip(trackId: String, clipId: String, splitTimeUs: Long) {
        viewModelScope.launch {
            timelineEngine.splitClip(trackId, clipId, splitTimeUs)
        }
    }
}
