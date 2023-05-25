package com.example.demo.data

import java.io.Serializable

class Film : Serializable {
    var id: Int
    val name: String
    val date: String
    val actors: List<String>
    var fileExtension: String
    var size: Double
    var isFreeForAll: Boolean

    constructor(id: Int, name: String, date: String, actors: List<String>, fileExtension: String, size: Double, isFreeForAll: Boolean) {
        this.id = id
        this.name = name
        this.date = date
        this.actors = actors
        this.fileExtension = fileExtension
        this.size = size
        this.isFreeForAll = isFreeForAll
    }

    constructor(name: String, date: String, actors: List<String>, fileExtension: String, size: Double, isFreeForAll: Boolean) {
        id = 0
        this.name = name
        this.date = date
        this.actors = actors
        this.fileExtension = fileExtension
        this.size = size
        this.isFreeForAll = isFreeForAll
    }

    override fun toString(): String {
        return "Film{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", actors=" + actors +
                ", file_extension='" + fileExtension + '\'' +
                ", size=" + size +
                ", isFreeForAll=" + isFreeForAll +
                '}'
    }
}
