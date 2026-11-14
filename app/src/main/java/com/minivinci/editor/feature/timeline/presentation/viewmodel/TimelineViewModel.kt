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

    // محرك التايملاين المتقدم والموحد
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
