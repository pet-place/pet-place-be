package com.petplace.be.connection

import org.junit.jupiter.api.Test
import java.sql.Connection
import java.sql.DriverManager

class DBConnectionTest {
    val DRIVER: String = "jdbc:mysql://35.77.202.110:3306/petplace"
    val USER: String = "devuser"
    val PASSWORD: String = "qwer!@34"

    @Test
    fun testConnection() {

        try {
            DriverManager.getConnection(DRIVER,USER,PASSWORD)
            println("success!")
        }catch (e: Exception){
            e.printStackTrace()
        }

    }
}