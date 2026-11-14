package com.minivinci.editor.feature.timeline.domain.command

import com.minivinci.editor.feature.timeline.domain.model.TimelineProject

class CommandHistory(
    private val undoStack: ArrayDeque<TimelineCommand> = ArrayDeque(),
    private val redoStack: ArrayDeque<TimelineCommand> = ArrayDeque(),
    private val maxHistorySize: Int = 50
) {

    /**
     * تنفيذ أمر جديد وتخزينه في سجل التراجع، مع تصفير سجل التقدم
     */
    fun executeCommand(project: TimelineProject, command: TimelineCommand): TimelineProject {
        val updatedProject = command.execute(project)
        undoStack.addLast(command)
        
        // الحفاظ على الحد الأقصى لحجم الذاكرة المخصصة للتاريخ
        if (undoStack.size > maxHistorySize) {
            undoStack.removeFirst()
        }
        
        redoStack.clear()
        return updatedProject
    }

    /**
     * التراجع عن آخر عملية (Undo)
     */
    fun undo(project: TimelineProject): TimelineProject {
        if (undoStack.isEmpty()) return project
        
        val command = undoStack.removeLast()
        val updatedProject = command.undo(project)
        redoStack.addLast(command)
        
        return updatedProject
    }

    /**
     * إعادة تنفيذ العملية التي تراجع عنها (Redo)
     */
    fun redo(project: TimelineProject): TimelineProject {
        if (redoStack.isEmpty()) return project
        
        val command = redoStack.removeLast()
        val updatedProject = command.execute(project)
        undoStack.addLast(command)
        
        return updatedProject
    }

    fun canUndo(): Boolean = undoStack.isNotEmpty()
    fun canRedo(): Boolean = redoStack.isNotEmpty()
}
