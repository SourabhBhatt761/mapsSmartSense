package com.srb.mapssmartsense.db

import android.os.SystemClock
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.util.*

@Entity(tableName = "map_table")
data class AppEntity(

    @ColumnInfo(name = "unique_id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    val latitude: Double,

    val longitude: Double,

    val timeClicked : String,

    val place: String,


)