package com.minivinci.editor.core.database

import kotlinx.coroutines.flow.Flow

class ProjectRepository(private val projectDao: ProjectDao) {

    // جلب كافة المشاريع كتدفق بيانات حي (Flow)
    val allProjects: Flow<List<ProjectEntity>> = projectDao.getAllProjects()

    // حفظ أو تحديث مشروع في قاعدة البيانات
    suspend fun saveProject(project: ProjectEntity) {
        projectDao.insertProject(project)
    }

    // جلب مشروع بواسطة معرّفه
    suspend fun getProjectById(projectId: String): ProjectEntity? {
        return projectDao.getProjectById(projectId)
    }

    // حذف مشروع
    suspend fun deleteProject(projectId: String) {
        projectDao.deleteProject(projectId)
    }
}
