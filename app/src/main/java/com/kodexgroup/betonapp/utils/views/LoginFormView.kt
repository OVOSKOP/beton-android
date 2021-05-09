package com.kodexgroup.betonapp.utils.views

import android.content.Context
import android.content.SharedPreferences
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.navigation.findNavController
import com.kodexgroup.betonapp.R
import com.kodexgroup.betonapp.database.server.ServerController
import com.kodexgroup.betonapp.utils.app
import com.kodexgroup.betonapp.utils.hideKeyword
import com.kodexgroup.betonapp.utils.json.JSON
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginFormView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val login: EditTextHintView
    private val password: EditTextHintView
    private val loginBtn: Button
    private val progressBar: ProgressBar
    private val signUpBtn: Button

    private lateinit var serverController: ServerController
    private var userSharedPref: SharedPreferences? = null

    init {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val root = inflater.inflate(R.layout.view_login_form, this, true)

        if (!isInEditMode) {
            serverController = ServerController()

            userSharedPref = context.getSharedPreferences(
                    "user", Context.MODE_PRIVATE)
        }

        login = root.findViewById(R.id.name_user_txt)
        password = root.findViewById(R.id.password_root)
        loginBtn = root.findViewById(R.id.sign_up_ok_btn)
        signUpBtn = root.findViewById(R.id.signup_btn)
        progressBar = root.findViewById(R.id.login_prgrs_bar)

        loginBtn.setOnClickListener {
            if (login.text.isNotEmpty() && password.text.isNotEmpty()) {
                login.isError = false
                password.isError = false
                progressBar.visibility = View.VISIBLE

                send()
            } else {
                login.isError = login.text.isEmpty()
                password.isError = password.text.isEmpty()
            }
        }

        signUpBtn.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.to_sign_up)
        }

    }

    fun clear() {
        login.clear()
        password.clear()
    }

    private fun send(second: Boolean = false) {
        CoroutineScope(Dispatchers.Main).launch {
            val res = serverController.userDAO.login(login.text, password.text)

            val obj = JSON.Response(res)

            if (obj["code"] as Int == 400) {
                login.isError = true
                password.isError("Неверный логин или пароль.")
            }

            if (obj["code"] as Int == 204 && !second) {
                login.isError = false
                password.isError = false

                send(true)
            }

            if (obj["code"] as Int == 200) {
                login.isError = false
                password.isError = false

                hideKeyword()
                val navController = findNavController()

                with (userSharedPref?.edit()) {
                    this?.putString("user_login", login.text)

                    this?.apply()
                }

                app.currentUser = serverController.userDAO.getUserByLogin(login.text)
                println(app.currentUser)

                navController.navigate(R.id.is_signed)
            }

            if ((obj["code"] as Int == 204 && second)) {
                login.isError = false
                password.isError("Произошла ошибка.")
            }

            progressBar.visibility = View.INVISIBLE
        }
    }
}