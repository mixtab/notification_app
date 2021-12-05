package ua.com.tabarkevych.pecoade_app.data.s.repository

import androidx.lifecycle.LiveData
import ua.com.tabarkevych.pecoade_app.data.s.dao.PageDao
import ua.com.tabarkevych.pecoade_app.data.s.entity.Page

class PageRepository(private val pageDao: PageDao) {

    val readAllData:LiveData<List<Page>> =  pageDao.readAllData()

    suspend fun addPage(page:Page){
        pageDao.addPage(page)
    }

    suspend fun deletePage(page: Page){
        pageDao.deletePage(page)
    }
}