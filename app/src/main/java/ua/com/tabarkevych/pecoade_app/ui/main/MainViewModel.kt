package ua.com.tabarkevych.pecoade_app.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.com.tabarkevych.pecoade_app.data.s.database.PageDatabase
import ua.com.tabarkevych.pecoade_app.data.s.entity.Page
import ua.com.tabarkevych.pecoade_app.data.s.repository.PageRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Page>>
    private val repository:PageRepository

    init {
        val pageDao = PageDatabase.getDatabase(application).pageDao()
        repository = PageRepository(pageDao)
        readAllData = repository.readAllData
    }

    fun addPage(page:Page){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPage(page)
        }
    }

    fun deletePage(page: Page){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePage(page)
        }

    }
}