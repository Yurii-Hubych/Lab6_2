package com.example.demo.data

import java.sql.SQLException
import java.util.*

class DataBaseRepository(private val dataBaseConnector: DataBaseConnector) : Repository {
    init {
        try {
            dataBaseConnector.connection.use { conn ->
                val tableCreateStr = """CREATE TABLE IF NOT EXISTS Films (
    id INT NOT NULL AUTO_INCREMENT,
    Name VARCHAR(50),
    Date VARCHAR(50),
    Actors TEXT,
    FileExtension VARCHAR(50),
    Size DOUBLE,
    IsFreeForAll BOOLEAN,
    PRIMARY KEY (id)
);"""
                val createTable = conn.createStatement()
                createTable.execute(tableCreateStr)
            }
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
    }

    override fun getAll(): List<Film> {
        val films: MutableList<Film> = ArrayList()
        try {
            dataBaseConnector.connection.use { connection ->
                val statement = connection.createStatement()
                val rs = statement.executeQuery("select * from Films")
                while (rs.next()) {
                    val actors = rs.getString(4)
                    val list = Arrays.asList(*actors.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
                    films.add(Film(rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            list,
                            rs.getString(5),
                            rs.getInt(6).toDouble(),
                            rs.getBoolean(7)))
                }
                rs.close()
            }
        } catch (exception: SQLException) {
            println("Не відбулося підключення до БД")
            exception.printStackTrace()
        }
        return films
    }

    override fun getById(id: Int): Film {
        var film: Film? = null
        try {
            dataBaseConnector.connection.use { connection ->
                val statement = connection.prepareStatement("select * from Films where id = ?")
                statement.setInt(1, id)
                val rs = statement.executeQuery()
                if (rs.next()) {
                    val actors = rs.getString(4)
                    val list = Arrays.asList(*actors.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
                    film = Film(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            list,
                            rs.getString(5),
                            rs.getInt(6).toDouble(),
                            rs.getBoolean(7))
                }
                rs.close()
            }
        } catch (exception: SQLException) {
            exception.printStackTrace()
        } finally {
            return film!!
        }
    }

    override fun addFilm(film: Film): Boolean {
        var updCount = 0
        val actors = java.lang.String.join(",", film.actors)
        try {
            dataBaseConnector.connection.use { conn ->
                val preparedStatement = conn.prepareStatement("INSERT INTO Films (Name, Date, Actors, FileExtension, Size, IsFreeForAll) VALUES (?,?,?,?,?,?)")
                preparedStatement.setString(1, film.name)
                preparedStatement.setString(2, film.date)
                preparedStatement.setString(3, actors)
                preparedStatement.setString(4, film.fileExtension)
                preparedStatement.setDouble(5, film.size)
                preparedStatement.setBoolean(6, film.isFreeForAll)
                updCount = preparedStatement.executeUpdate()
            }
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
        return updCount > 0
    }

    override fun deleteFilm(id: Int): Boolean {
        var updCount = 0
        try {
            dataBaseConnector.connection.use { conn ->
                val preparedStatement = conn.prepareStatement(
                        "DELETE FROM Films WHERE id = ?")
                preparedStatement.setInt(1, id)
                updCount = preparedStatement.executeUpdate()
            }
        } catch (e: SQLException) {
            throw RuntimeException(e)
        }
        return updCount > 0
    }

    override fun getAllByActor(actor: String): List<Film> {
        val films: MutableList<Film> = ArrayList()
        try {
            dataBaseConnector.connection.use { connection ->
                val statement = connection.prepareStatement(
                        "select * from Films where Actors Like(?)"
                )
                statement.setString(1, "%$actor%")
                val rs = statement.executeQuery()
                while (rs.next()) {
                    val actors = rs.getString(4)
                    val list = Arrays.asList(*actors.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
                    films.add(Film(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            list,
                            rs.getString(5),
                            rs.getInt(6).toDouble(),
                            rs.getBoolean(7)))
                }
                rs.close()
            }
        } catch (exception: SQLException) {
            println("Не відбулося підключення до БД")
            exception.printStackTrace()
        }
        return films
    }

    override fun getAllByFormat(FileExtension: String): List<Film> {
        val films: MutableList<Film> = ArrayList()
        try {
            dataBaseConnector.connection.use { connection ->
                val statement = connection.prepareStatement(
                        "select * from Films where FileExtension Like(?)"
                )
                statement.setString(1, "%$FileExtension%")
                val rs = statement.executeQuery()
                while (rs.next()) {
                    val actors = rs.getString(4)
                    val list = Arrays.asList(*actors.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
                    films.add(Film(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            list,
                            rs.getString(5),
                            rs.getInt(6).toDouble(),
                            rs.getBoolean(7)))
                }
                rs.close()
            }
        } catch (exception: SQLException) {
            println("Не відбулося підключення до БД")
            exception.printStackTrace()
        }
        return films
    }
}
