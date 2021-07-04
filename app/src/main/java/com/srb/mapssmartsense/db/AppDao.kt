package com.srb.mapssmartsense.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AppDao {

    @Query("SELECT * FROM map_table ORDER BY unique_id ASC")
    fun getAllData(): LiveData<List<AppEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(app : List<AppEntity>)

    @Delete
    suspend fun deleteItem(app : AppEntity)

    @Query("DELETE FROM map_table")
    suspend fun deleteAll()

//    @Query("SELECT * FROM todo_table WHERE title LIKE :searchQuery")
//    fun searchDatabase(searchQuery: String): LiveData<List<ToDoData>>
//
//    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
//    fun sortByHighPriority(): LiveData<List<ToDoData>>
//
//    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
//    fun sortByLowPriority(): LiveData<List<ToDoData>>

}