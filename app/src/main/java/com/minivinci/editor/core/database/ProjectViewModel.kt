package com.minivinci.editor.core.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProjectViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ProjectRepository

    // حالة المشاريع لعرضها في الواجهة بشكل تفاعلي
    private val _projects = MutableStateFlow<List<ProjectEntity>>(emptyList())
    val projects: StateFlow<List<ProjectEntity>> = _projects.asStateFlow()

    init {
        val database = AppDatabase.getDatabase(application)
        val dao = database.projectDao()
        repository = ProjectRepository(dao)
        loadProjects()
    }

    // جلب المشاريع من قاعدة البيانات في الخلفية
    fun loadProjects() {
        viewModelScope.launch {
            _projects.value = repository.getAllProjects()
        }
    }

    // حفظ مشروع جديد وتحديث القائمة فوراً
    fun createNewProject(name: String) {
        viewModelScope.launch {
            val newProject = ProjectEntity(
                projectName = name,
                createdAt = System.currentTimeMillis()
            )
            repository.saveProject(newProject)
            loadProjects() // إعادة تحميل القائمة لتحديث الواجهة
        }
    }
}
