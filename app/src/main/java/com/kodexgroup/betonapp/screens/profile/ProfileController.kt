package com.kodexgroup.betonapp.screens.profile

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.kodexgroup.betonapp.R
import com.kodexgroup.betonapp.utils.UserRole
import com.kodexgroup.betonapp.utils.app

class ProfileController(private val fragment: Fragment, view: View) {

    private val user = fragment.app.currentUser

    private val userName: TextView = view.findViewById(R.id.user_name)
    private val userAddress: TextView = view.findViewById(R.id.user_address)
    private val userPhone: TextView = view.findViewById(R.id.user_phone)
    private val userEmail: TextView = view.findViewById(R.id.user_email)
    private val userRole: TextView = view.findViewById(R.id.user_role)
    private val quit: TextView = view.findViewById(R.id.quit)

    private var userSharedPref: SharedPreferences? = fragment.context?.getSharedPreferences(
            "user", Context.MODE_PRIVATE)

    init {
        userName.text = user?.name
        userAddress.text = user?.city
        userPhone.text = user?.phone
        userEmail.text = user?.email
        userRole.text = UserRole[user?.role ?: 0]

        quit.setOnClickListener {
            val navController = fragment.findNavController()

            with (userSharedPref?.edit()) {
                this?.remove("user_login")

                this?.apply()
            }

            fragment.app.currentUser = null

            navController.navigate(R.id.to_quit)
        }
    }

}