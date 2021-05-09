package com.kodexgroup.betonapp.database.server

import com.kodexgroup.betonapp.database.server.dao.FactoryDAO
import com.kodexgroup.betonapp.database.server.dao.ProductDAO
import com.kodexgroup.betonapp.database.server.dao.UserDAO

class ServerController {
    val userDAO: UserDAO = UserDAO()
    val productDAO: ProductDAO = ProductDAO()
    val factoryDAO: FactoryDAO = FactoryDAO()
}