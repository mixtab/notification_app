package ua.com.tabarkevych.notificationApp.data.s.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "page_table")
data class Page(
    @PrimaryKey (autoGenerate = true)
    val id: Int,
)
