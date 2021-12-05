package ua.com.tabarkevych.pecoade_app.data.s.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ua.com.tabarkevych.pecoade_app.data.s.entity.Page

@Dao
interface PageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPage(page:Page)

    @Delete
    suspend fun deletePage(page: Page)

    @Query("SELECT * FROM page_table ORDER BY id ASC")
    fun readAllData():LiveData<List<Page>>
}