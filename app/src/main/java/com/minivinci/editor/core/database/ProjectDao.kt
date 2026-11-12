package com.minivinci.editor.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {

    // جلب كافة المشاريع كتدفق بيانات حي (Flow) لتحديث واجهة المستخدم فوراً
    @Query("SELECT * FROM projects ORDER BY modifiedAt DESC")
    fun getAllProjects(): Flow<List<ProjectEntity>>

    // جلب مشروع معين بواسطة معرفه الفريد
    @Query("SELECT * FROM projects WHERE id = :projectId")
    suspend fun getProjectById(projectId: String): ProjectEntity?

    // إدخال مشروع جديد أو تحديثه في حال وجود تعارض
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: ProjectEntity)

    // حذف مشروع من قاعدة البيانات
    @Query("DELETE FROM projects WHERE id = :projectId")
    suspend fun deleteProject(projectId: String)
}
