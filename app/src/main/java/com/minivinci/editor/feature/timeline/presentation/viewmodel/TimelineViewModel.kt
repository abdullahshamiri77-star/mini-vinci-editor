package com.minivinci.editor.feature.timeline.presentation.viewmodel
package com.minivinci.editor.feature.timeline.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minivinci.editor.feature.timeline.domain.engine.AdvancedTimelineEngine
import com.minivinci.editor.feature.timeline.domain.model.AdvancedTimelineProject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TimelineViewModel : ViewModel() {

    // محرك التايملاين المتقدم
    private val timelineEngine = AdvancedTimelineEngine()

    // حالة المشروع مرئية ومباشرة للواجهة عبر الـ StateFlow
    val projectState: StateFlow<AdvancedTimelineProject> = timelineEngine.projectState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = timelineEngine.projectState.value
        )

    /**
     * إضافة مقطع فيديو أو صوت جديد إلى مسار محدد
     */
    fun addClip(trackId: String, mediaPath: String, durationUs: Long) {
        viewModelScope.launch {
            timelineEngine.addClipToTrack(trackId, mediaPath, durationUs)
        }
    }

    /**
     * قص/تقسيم المقطع عند نقطة زمنية معينة (Razor Tool / Split)
     */
    fun splitClip(trackId: String, clipId: String, splitTimeUs: Long) {
        viewModelScope.launch {
            timelineEngine.splitClip(trackId, clipId, splitTimeUs)
        }
    }
}


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
