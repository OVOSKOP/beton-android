package com.kodexgroup.betonapp.utils.views

import android.content.Context
import android.util.AttributeSet
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.navigation.findNavController
import com.kodexgroup.betonapp.R
import com.kodexgroup.betonapp.database.server.ServerController
import com.kodexgroup.betonapp.utils.hideKeyword
import com.kodexgroup.betonapp.utils.json.JSON
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpFormView(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {

    private val name: EditTextHintView
    private val city: EditTextHintView
    private val email: EditTextHintView
    private val phone: EditTextHintView
    private val login: EditTextHintView
    private val password: EditTextHintView
    private val secondPass: EditTextHintView

    private val progressBar: ProgressBar

    private val signUp: Button

    private lateinit var serverController: ServerController

    init {
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val root = inflater.inflate(R.layout.view_sign_up_form, this, true)

        if (!isInEditMode) {
            serverController = ServerController()
        }

        name = root.findViewById(R.id.name_user_txt)
        city = root.findViewById(R.id.city_txt)
        email = root.findViewById(R.id.email_txt)
        phone = root.findViewById(R.id.phone_numer_txt)
        login = root.findViewById(R.id.login_sign_up_txt)
        password = root.findViewById(R.id.password_sign_up_txt)
        secondPass = root.findViewById(R.id.second_pass_txt)

        signUp = root.findViewById(R.id.sign_up_ok_btn)

        progressBar = root.findViewById(R.id.sign_prgrs_bar)

        signUp.setOnClickListener {
            if (checkTxt()) {
                progressBar.visibility = View.VISIBLE
                send()
            }
        }

    }

    private fun checkTxt() : Boolean {
        var res = true

        if (name.text.isEmpty()) {
            name.isError("Заполните все поля.")
            res = false
        } else {
            name.isError = false
        }

        if (city.text.isEmpty()) {
            city.isError("Заполните все поля.")
            res = false
        } else {
            city.isError = false
        }

        if (email.text.isEmpty()) {
            email.isError("Заполните все поля.")
            res = false
        } else {
            email.isError = false
        }

        if (phone.text.isEmpty()) {
            phone.isError("Заполните все поля.")
            res = false
        } else {
            phone.isError = false
        }

        if (login.text.isEmpty()) {
            login.isError("Заполните все поля.")
            res = false
        } else {
            login.isError = false
        }

        if (password.text.isEmpty()) {
            password.isError("Заполните все поля.")
            res = false
        } else {
            password.isError = false
        }

        if (secondPass.text.isEmpty()) {
            secondPass.isError("Заполните все поля.")
            res = false
        } else {
            secondPass.isError = false
        }

        if (password.text.isNotEmpty() && secondPass.text.isNotEmpty()) {
            if (password.text != secondPass.text) {
                secondPass.isError("Пароли не совпадают.")
                res = false
            } else {
                secondPass.isError = false
            }
        }

        if (login.text.isNotEmpty()) {
            if (login.text.length < 8) {
                login.isError("Минимально 8 символов.")
                res = false
            } else {
                login.isError = false
            }
        }

        if (password.text.isNotEmpty()) {
            if (!password.text.contains(
                    Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+\$).{8,}")
                )
            ) {
                password.isError("Пароль должен быть длиной 8 символов, содержать латинские символы, числа и минимум одну заглавную букву.")
                res = false
            } else {
                password.isError = false
            }
        }

        if (email.text.isNotEmpty()) {
            if (!Patterns.EMAIL_ADDRESS.matcher(email.text).matches()) {
                email.isError("Введите корректный E-Mail.")
                res = false
            } else {
                email.isError = false
            }
        }

        if (phone.text.isNotEmpty()) {
            if (!Patterns.PHONE.matcher(phone.text).matches()) {
                phone.isError("Введите корректный номер телефона.")
                res = false
            } else {
                phone.isError = false
            }
        }

        return res

    }

    private fun send(second: Boolean = false) {
        CoroutineScope(Dispatchers.Default).launch {
            val res = serverController.userDAO.signUp(
                name.text,
                city.text,
                email.text,
                phone.text,
                login.text,
                password.text
            )

            val obj = JSON.Response(res)

            if (obj["code"] as Int == 400) {
                withContext(Dispatchers.Main) {
                    password.isError("Неверный логин или пароль.")
                }
            }

            if (obj["code"] as Int == 204 && !second) {
                send(true)
            }

            if (obj["code"] as Int == 200) {
                withContext(Dispatchers.Main) {
                    hideKeyword()
                    val navController = findNavController()

                    navController.navigate(R.id.is_signed)
                }
            }

            if ((obj["code"] as Int == 204 && second)) {
                withContext(Dispatchers.Main) {
                    login.isError = false
                    password.isError("Произошла ошибка.")
                }
            }
            withContext(Dispatchers.Main) {
                progressBar.visibility = View.INVISIBLE
            }
        }
    }

}