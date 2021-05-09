package com.kodexgroup.betonapp.database.server.entities

import com.kodexgroup.betonapp.utils.json.JSON
import com.kodexgroup.betonapp.utils.xml.interfaces.IXMLClass
import org.json.JSONObject

class User : IXMLClass {

    private lateinit var _id: String
    val id: String
        get() = _id

    private lateinit var _name: String
    val name: String
        get() = _name

    private lateinit var _login: String
    val login: String
        get() = _login

    private var _favorites: JSONObject? = null
    var favorites: JSONObject? = null
        get() = _favorites

    private lateinit var _email: String
    val email: String
        get() = _email

    private lateinit var _phone: String
    val phone: String
        get() = _phone

    private var _role: Int = 0
    val role: Int
        get() = _role

    private lateinit var _city: String
    val city: String
        get() = _city

    companion object {
        const val START_TAG = "User"
        const val ID = "Id"
        const val NAME = "Title"
        const val LOGIN = "Login"
        const val FAVORITES = "Favorites"
        const val EMAIL = "EMail"
        const val PHONE = "Phone"
        const val ROLE = "Role"
        const val SUBSCRIBE = "Suscribe"
        const val FACTORY = "Factory"
        const val PHOTO = "Photo"
        const val CITY = "City"
        const val SUPPORT = "Support"
        const val DATE = "Date"
    }

    override fun setData(key: String, value: String?) {
        val newValue = value ?: ""
        when (key) {
            ID -> {
                _id = newValue
            }
            NAME -> {
                _name = newValue
            }
            LOGIN -> {
                _login = newValue
            }
            FAVORITES -> {
                if (newValue != "")
                    _favorites = JSON.Response(newValue)
            }
            EMAIL -> {
                _email = newValue
            }
            PHONE -> {
                _phone = newValue
            }
            ROLE -> {
                _role = newValue.toInt()
            }
            CITY -> {
                _city = newValue
            }
        }
    }

}