package com.minivinci.editor.services.autosave

import com.minivinci.editor.core.database.ProjectEntity
import com.minivinci.editor.core.database.ProjectRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AutoSaveManager(
    private val repository: ProjectRepository,
    private val scope: CoroutineScope
) {
    private var saveJob: Job? = null

    /**
     * جدولة الحفظ التلقائي لمنع ضياع التعديلات أثناء المونتاج
     */
    fun scheduleAutoSave(project: ProjectEntity, delayMillis: Long = 3000L) {
        saveJob?.cancel()
        saveJob = scope.launch(Dispatchers.IO) {
            delay(delayMillis)
            repository.saveProject(project)
        }
    }
}
