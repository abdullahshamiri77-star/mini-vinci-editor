package com.minivinci.editor.feature.timeline.domain.engine

import com.minivinci.editor.feature.timeline.domain.command.AddClipCommand
import com.minivinci.editor.feature.timeline.domain.command.CommandHistory
import com.minivinci.editor.feature.timeline.domain.command.SplitClipCommand
import com.minivinci.editor.feature.timeline.domain.model.TimelineClip
import com.minivinci.editor.feature.timeline.domain.model.TimelineProject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AdvancedTimelineEngine(initialProject: TimelineProject) {

    private val _projectState = MutableStateFlow(initialProject)
    val projectState: StateFlow<TimelineProject> = _projectState.asStateFlow()

    private val commandHistory = CommandHistory()

    /**
     * إضافة مقطع جديد إلى مسار محدد عبر نظام الأوامر
     */
    fun addClipToTrack(trackId: String, clip: TimelineClip) {
        val command = AddClipCommand(trackId, clip)
        val updatedProject = commandHistory.executeCommand(_projectState.value, command)
        _projectState.value = updatedProject
    }

    /**
     * قص مقطع عند نقطة زمنية محددة (Split / Razor)
     */
    fun splitClip(trackId: String, clipId: String, splitTimeUs: Long) {
        val command = SplitClipCommand(trackId, clipId, splitTimeUs)
        val updatedProject = commandHistory.executeCommand(_projectState.value, command)
        _projectState.value = updatedProject
    }

    /**
     * التراجع عن آخر عملية (Undo)
     */
    fun undo() {
        if (commandHistory.canUndo()) {
            val updatedProject = commandHistory.undo(_projectState.value)
            _projectState.value = updatedProject
        }
    }

    /**
     * إعادة تنفيذ عملية تم التراجع عنها (Redo)
     */
    fun redo() {
        if (commandHistory.canRedo()) {
            val updatedProject = commandHistory.redo(_projectState.value)
            _projectState.value = updatedProject
        }
    }

    fun canUndo(): Boolean = commandHistory.canUndo()
    fun canRedo(): Boolean = commandHistory.canRedo()
}
