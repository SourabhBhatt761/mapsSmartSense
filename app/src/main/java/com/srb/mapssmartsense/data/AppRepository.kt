package com.srb.mapssmartsense.data

import androidx.lifecycle.LiveData
import com.srb.mapssmartsense.db.AppDao
import com.srb.mapssmartsense.db.AppEntity

class AppRepository(private val appDao: AppDao) {

    val getAllData : LiveData<List<AppEntity>> =appDao.getAllData()

    suspend fun insertData(appData : List<AppEntity>){
        appDao.insertData(appData)
    }

    suspend fun deleteData(appEntity: AppEntity){
        appDao.deleteItem(appEntity)
    }

    suspend fun deleteAllAppData() = appDao.deleteAll()


}