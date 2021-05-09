package com.kodexgroup.betonapp.database.server.dao

import com.kodexgroup.betonapp.database.server.entities.Factory
import com.kodexgroup.betonapp.database.server.entities.User
import com.kodexgroup.betonapp.network.NetworkController
import com.kodexgroup.betonapp.utils.xml.XMLParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.Request
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UserDAO {

    private val networkController = NetworkController()

    suspend fun login(login: String, password: String) : String {
        val body = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("login", login)
            .addFormDataPart("password", password)
            .build()

        val request = Request.Builder()
            .url("https://api.seostor.ru/beton/test/users/auth.php")
            .post(body)
            .build()

        return networkController.execute(request)
    }

    suspend fun signUp(name: String, city: String, email: String, phone: String, login: String, password: String) : String {

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("name", name)
            .addFormDataPart("role", 0.toString())
            .addFormDataPart("city", city)
            .addFormDataPart("email", email)
            .addFormDataPart("favorites", "{\"products\": \"\", \"factories\": \"\"}")
            .addFormDataPart("phone", phone)
            .addFormDataPart("login", login)
            .addFormDataPart("password", password)
            .build()


        val request = Request.Builder()
            .url("https://api.seostor.ru/beton/test/users/signup.php")
            .post(requestBody)
            .build()

        return networkController.execute(request)
    }

    suspend fun modifyFavorite(id: String, favorite: JSONObject) : String {

        val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id", id)
                .addFormDataPart("favorites", "{\"products\": \"${favorite["products"]}\", \"factories\": \"${favorite["factories"]}\"}")
                .build()


        val request = Request.Builder()
                .url("https://api.seostor.ru/beton/test/users/modify.php")
                .post(requestBody)
                .build()

        return networkController.execute(request)

    }

    suspend fun getUserByLogin(login: String) : User? {

        val body = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("login", login)
                .build()
        val request = Request.Builder()
                .url("https://api.seostor.ru/beton/test/users/get.php")
                .post(body)
                .build()

        val xml = networkController.execute(request)

        val xmlParser = XMLParser()
        val parser = xmlParser.getParser(xml)

        return xmlParser.getObjectFromParser<User>(parser, User.START_TAG).firstOrNull()

    }

}