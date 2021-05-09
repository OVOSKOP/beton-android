package com.kodexgroup.betonapp.database.server.dao

import com.kodexgroup.betonapp.database.server.entities.Product
import com.kodexgroup.betonapp.network.NetworkController
import com.kodexgroup.betonapp.utils.xml.XMLParser
import okhttp3.MultipartBody
import okhttp3.Request
import org.json.JSONObject
import java.sql.Date

class ProductDAO() {

    private val networkController = NetworkController()

    suspend fun getProducts(id: List<String>? = null, factory: List<String>? = null, type: List<Int>? = null) : List<Product> {
        val bodyBuilder = MultipartBody.Builder()
                .setType(MultipartBody.FORM)

        val requestBuilder = Request.Builder()
                .url("https://api.seostor.ru/beton/test/products/get.php")

        if (id != null && id.isNotEmpty() || factory != null && factory.isNotEmpty() || type != null && type.isNotEmpty()) {
            bodyBuilder.apply {
                if (id != null && id.isNotEmpty()) {
                    addFormDataPart("product", id.joinToString(" "))
                }
                if (factory != null && factory.isNotEmpty()) {
                    addFormDataPart("factory", factory.joinToString(" "))
                }
                if (type != null && type.isNotEmpty()) {
                    addFormDataPart("type", type.joinToString(" "))
                }
            }
            val body = bodyBuilder.build()

            requestBuilder.post(body)
        }

        val xml = networkController.execute(requestBuilder.build())

        val xmlParser = XMLParser()
        val parser = xmlParser.getParser(xml)

        return xmlParser.getObjectFromParser(parser, Product.START_TAG)
    }

    suspend fun modify(id: String, favorite: Int) : String {

        val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id", id)
                .addFormDataPart("favorite", favorite.toString())
                .build()


        val request = Request.Builder()
                .url("https://api.seostor.ru/beton/test/products/modify.php")
                .post(requestBody)
                .build()

        return networkController.execute(request)

    }

}