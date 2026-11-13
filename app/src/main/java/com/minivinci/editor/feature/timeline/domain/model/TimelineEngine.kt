package com.minivinci.editor.feature.timeline.domain.engine

import com.minivinci.editor.feature.timeline.domain.model.TimelineProject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TimelineEngine {

    private val _projectState = MutableStateFlow(
        TimelineProject(id = "proj_1", name = "My DaVinci Project")
    )
    val projectState: StateFlow<TimelineProject> = _projectState.asStateFlow()

    fun updateCurrentTime(timeMs: Long) {
        // إدارة وتحديث مؤشر التشغيل Playhead على التايملاين
    }
}
