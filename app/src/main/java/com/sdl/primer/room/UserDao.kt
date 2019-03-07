package com.sdl.primer.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

/**
 * create by songdongliang on 2019/2/28
 */
@Dao
interface UserDao {

    /**
     * Get the user from the table. Since, for simplicity we only have one user in the database,
     * this query gets all users from the table, but limits the result to just the 1st user.
     *
     * @return the user from the table
     */
    @Query("SELECT * FROM users LIMIT 1")
    fun getUser(): User

    /**
     * Insert a user in the database. If the user already exists, replace it
     *
     * @param user the user to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    /**
     * Delete all users
     */
    @Query("DELETE FROM users")
    fun deleteAllUsers()
}