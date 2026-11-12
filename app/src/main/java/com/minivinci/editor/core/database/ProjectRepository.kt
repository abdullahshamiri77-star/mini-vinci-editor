package com.minivinci.editor.core.database

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectRepository(private val projectDao: ProjectDao) {

    // جلب جميع المشاريع المحفوظة مرتبة من الأحدث للأقدم
    suspend fun getAllProjects(): List<ProjectEntity> {
        return withContext(Dispatchers.IO) {
            projectDao.getAllProjects()
        }
    }

    // حفظ أو تحديث مشروع محلياً
    suspend fun saveProject(project: ProjectEntity) {
        withContext(Dispatchers.IO) {
            projectDao.saveProject(project)
        }
    }
}
