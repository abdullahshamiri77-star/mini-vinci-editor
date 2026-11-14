package com.minivinci.editor.feature.timeline.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minivinci.editor.feature.timeline.domain.engine.AdvancedTimelineEngine
import com.minivinci.editor.feature.timeline.domain.model.TimelineClip
import com.minivinci.editor.feature.timeline.domain.model.TimelineProject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TimelineViewModel : ViewModel() {

    // 1. إنشاء مشروع ابتدائي فارغ مع تهيئة المحرك بالـ initialProject المطلوبة
    private val initialProject = TimelineProject(
        projectId = "project_1",
        projectName = "VisionStudio Project",
        tracks = emptyList()
    )

    private val timelineEngine = AdvancedTimelineEngine(initialProject)

    // 2. تعريض حالة المشروع للواجهة عبر StateFlow
    val projectState: StateFlow<TimelineProject> = timelineEngine.projectState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = initialProject
        )

    // 3. إضافة مقطع بشكل صحيح باستخدام كائن TimelineClip متكامل
    fun addClip(trackId: String, clipId: String, mediaPath: String, startTimeUs: Long, durationUs: Long, outPointUs: Long) {
        val newClip = TimelineClip(
            clipId = clipId,
            mediaPath = mediaPath,
            startTimeUs = startTimeUs,
            durationUs = durationUs,
            inPointUs = 0L,
            outPointUs = outPointUs,
            speed = 1.0f
        )
        timelineEngine.addClipToTrack(trackId, newClip)
    }

    // 4. قص المقطع (Split) بدقة زمنية
    fun splitClip(trackId: String, clipId: String, splitTimeUs: Long) {
        timelineEngine.splitClip(trackId, clipId, splitTimeUs)
    }

    // 5. التراجع والتقدم (Undo / Redo)
    fun undo() {
        timelineEngine.undo()
    }

    fun redo() {
        timelineEngine.redo()
    }

    fun canUndo(): Boolean = timelineEngine.canUndo()
    fun canRedo(): Boolean = timelineEngine.canRedo()
}
