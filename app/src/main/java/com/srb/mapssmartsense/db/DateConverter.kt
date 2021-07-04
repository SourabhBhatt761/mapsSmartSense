package com.srb.mapssmartsense.db

import androidx.room.TypeConverter
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

//class DateConverter {
//    @TypeConverter
//    fun toDate(dateLong: Long?): Date? {
//        return dateLong?.let { Date(it) }
//    }
//
//    @TypeConverter
//    fun fromDate(date: Date?): Long? {
//        return date?.time
//    }
//}

object DateConverter {
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String?): OffsetDateTime? {
        return value?.let {
            return formatter.parse(value, OffsetDateTime::from)
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(date: OffsetDateTime?): String? {
        return date?.format(formatter)
    }
}