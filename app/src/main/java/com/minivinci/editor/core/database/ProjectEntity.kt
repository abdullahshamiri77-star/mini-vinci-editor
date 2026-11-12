package com.minivinci.editor.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey val id: String,
    val title: String,
    val lastModified: Long,
    val timelineDataJson: String // لحفظ حالة التايم لاين والمقاطع كملف نصي هيكلي
)
