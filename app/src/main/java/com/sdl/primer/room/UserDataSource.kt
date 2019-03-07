package com.sdl.primer.room

/**
 * create by songdongliang on 2019/2/28
 */
interface UserDataSource {

    fun getUser(): User

    fun insertOrUpdateUser(user: User)

    fun deleteAllUsers()
}