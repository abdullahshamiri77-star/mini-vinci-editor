package com.minivinci.editor.feature.timeline.domain.engine

import com.minivinci.editor.feature.timeline.domain.model.TimelineClip
import com.minivinci.editor.feature.timeline.domain.model.TimelineProject
import com.minivinci.editor.feature.timeline.domain.usecase.AddClip
import com.minivinci.editor.feature.timeline.domain.usecase.RemoveClip
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TimelineEngine(
    private val addClipUseCase: AddClip = AddClip(),
    private val removeClipUseCase: RemoveClip = RemoveClip()
) {

    // حالة المشروع الحالية في التايملاين
    private val _projectState = MutableStateFlow(
        TimelineProject(
            id = "proj_davinci_1",
            name = "VisionStudio Master Project",
            fps = 30
        )
    )
    val projectState: StateFlow<TimelineProject> = _projectState.asStateFlow()

    // مؤشر التشغيل الزمني (Playhead Position in Milliseconds)
    private val _playheadPosition = MutableStateFlow(0L)
    val playheadPosition: StateFlow<Long> = _playheadPosition.asStateFlow()

    /**
     * إضافة مقطع جديد إلى مسار محدد
     */
    fun addClipToTrack(trackId: String, clip: TimelineClip) {
        _projectState.update { currentProject ->
            addClipUseCase(currentProject, trackId, clip)
        }
    }

    /**
     * إزالة مقطع من التايملاين بناءً على معرفه
     */
    fun removeClip(clipId: String) {
        _projectState.update { currentProject ->
            removeClipUseCase(currentProject, clipId)
        }
    }

    /**
     * تحديث موقع مؤشر التشغيل (Playhead) أثناء العرض أو التحريك
     */
    fun seekTo(timeMs: Long) {
        _playheadPosition.value = timeMs.coerceAtLeast(0L)
    }
}
