package com.example.e_medib.utils

import androidx.room.TypeConverter
import java.util.*

class UUIDConverter {
    @TypeConverter
    fun uuidFromString(uuid: UUID): String {
        return uuid.toString()
    }

    @TypeConverter
    fun stringFromUUID(id: String): UUID? {
        return UUID.fromString(id)
    }
}