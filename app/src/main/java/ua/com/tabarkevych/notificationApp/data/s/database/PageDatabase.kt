package ua.com.tabarkevych.notificationApp.data.s.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ua.com.tabarkevych.notificationApp.data.s.dao.PageDao
import ua.com.tabarkevych.notificationApp.data.s.entity.Page

@Database(entities = [Page::class], version = 1, exportSchema = false)
abstract class PageDatabase : RoomDatabase() {

    abstract fun pageDao():PageDao

    companion object{
        @Volatile
        private var INSTANCE: PageDatabase? = null

        fun getDatabase(context: Context):PageDatabase{
            val tempInstance  = INSTANCE
            if (tempInstance != null ){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PageDatabase::class.java,
                    "page_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}