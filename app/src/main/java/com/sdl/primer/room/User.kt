package com.sdl.primer.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * create by songdongliang on 2019/2/28
 */
@Entity(tableName = "users")
data class User(
        @PrimaryKey @ColumnInfo(name = "userid") val mId: String,
        @ColumnInfo(name = "username") val mUserName: String,
        @ColumnInfo(name = "last_update") val mDate: Date)