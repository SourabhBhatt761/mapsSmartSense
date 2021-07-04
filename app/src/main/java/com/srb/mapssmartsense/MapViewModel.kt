package com.srb.mapssmartsense

import android.app.Application
import androidx.lifecycle.*
import com.srb.mapssmartsense.data.AppRepository
import com.srb.mapssmartsense.db.AppDatabase
import com.srb.mapssmartsense.db.AppEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapViewModel(application: Application) : AndroidViewModel(application){

     private val appDao = AppDatabase.getDatabase(application).appDao()
     private val repository: AppRepository = AppRepository(appDao)

     val getAllData: LiveData<List<AppEntity>> = repository.getAllData

     fun insertAppData(appData: List<AppEntity>) = viewModelScope.launch(Dispatchers.IO) {
          repository.insertData(appData)
     }

     fun deleteAllAppData() = viewModelScope.launch {
          repository.deleteAllAppData()
     }

     fun deleteData(appEntity: AppEntity) = viewModelScope.launch {
          repository.deleteData(appEntity)
     }

}