package com.minivinci.editor.feature.timeline.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.minivinci.editor.feature.timeline.domain.engine.TimelineEngine
import com.minivinci.editor.feature.timeline.domain.model.TimelineClip
import kotlinx.coroutines.flow.StateFlow

class TimelineViewModel(
    private val timelineEngine: TimelineEngine = TimelineEngine()
) : ViewModel() {

    // حالة المشروع المنشورة للواجهة
    val projectState = timelineEngine.projectState

    // موقع مؤشر التشغيل المنشور للواجهة
    val playheadPosition = timelineEngine.playheadPosition

    fun onAddClip(trackId: String, clip: TimelineClip) {
        timelineEngine.addClipToTrack(trackId, clip)
    }

    fun onRemoveClip(clipId: String) {
        timelineEngine.removeClip(clipId)
    }

    fun onSeek(timeMs: Long) {
        timelineEngine.seekTo(timeMs)
    }
}
