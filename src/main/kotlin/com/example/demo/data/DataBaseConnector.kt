package com.example.demo.data

import java.lang.reflect.InvocationTargetException
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DataBaseConnector(dataBaseName: String?) {
    private val dataBaseUrl = "jdbc:h2:/demo1/demo/db/films"
    private val dataBaseUser = "123"
    private val dataBasePassword = "123"
    private val driverClass = "org.h2.Driver"
    fun testDriver(): Boolean {
        return try {
            Class.forName(driverClass).getDeclaredConstructor().newInstance()
            true
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            false
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            false
        } catch (e: InstantiationException) {
            e.printStackTrace()
            false
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
            false
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
            false
        }
    }

    @get:Throws(SQLException::class)
    val connection: Connection
        get() = DriverManager.getConnection(dataBaseUrl, dataBaseUser, dataBasePassword)
}
