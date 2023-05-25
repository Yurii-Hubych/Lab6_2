package com.example.demo.data

import java.io.*
import java.util.*
import java.util.function.ToIntFunction

class FileRepository(private val fileName: String) : Repository {
    private var films: MutableList<Film>

    init {
        films = ArrayList<Film>()
    }

    val allFilms: List<Any>
        get() {
            reloadData()
            return films
        }


    private fun reloadData() {
        if (File(fileName).exists()) {
            try {
                FileInputStream(fileName).use { fileInputStream ->
                    try {
                        ObjectInputStream(fileInputStream).use { objectInputStream -> films = objectInputStream.readObject() as MutableList<Film> }
                    } catch (e: ClassNotFoundException) {
                        throw RuntimeException(e)
                    }
                }
            } catch (e: FileNotFoundException) {
                throw RuntimeException(e)
            } catch (e: IOException) {
                //throw new RuntimeException(e);
            }
        }
    }

    override fun getById(id: Int): Film? {
        return null
    }

    override fun addFilm(film: Film): Boolean {
        var id = 0
        if (films.size > 0) {
            val maxId = films.stream().mapToInt(ToIntFunction<Film> { c: Film -> c.id }).max()
            if (maxId != null) {
                id = maxId.asInt
            }
        }
        film.id = id + 1
        films.add(film)
        try {
            save()
        } catch (e: IOException) {
            return false
        }
        return true
    }


    override fun getAll(): List<Film?> {
        reloadData()
        return films
    }

    @Throws(IOException::class)
    private fun save() {
        FileOutputStream(fileName).use { fileOutputStream -> ObjectOutputStream(fileOutputStream).use { objectOutputStream -> objectOutputStream.writeObject(films) } }
    }

    override fun getAllByActor(actor: String?): List<Film>? {
        return null
    }

    override fun deleteFilm(id: Int): Boolean {
        return false
    }

    override fun getAllByFormat(format: String?): List<Film>? {
        return null
    }
}
