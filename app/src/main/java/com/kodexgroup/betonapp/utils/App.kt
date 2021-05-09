package com.kodexgroup.betonapp.utils

import android.app.Application
import android.content.Context
import com.kodexgroup.betonapp.database.server.ServerController
import com.kodexgroup.betonapp.database.server.entities.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class App : Application() {

    var currentUser: User? = null

    override fun onCreate() {
        super.onCreate()

        val userSharedPref = getSharedPreferences(
                "user", Context.MODE_PRIVATE)
        val login = userSharedPref?.getString("user_login", "") ?: ""

        CoroutineScope(Dispatchers.IO).launch {
            if (login.isNotEmpty()) {
                try {
                    currentUser = ServerController().userDAO.getUserByLogin(login)
                } catch (e: Exception) {
                    try {
                        currentUser = ServerController().userDAO.getUserByLogin(login)
                    } catch (e: Exception) { }
                }

            }
        }
    }

}