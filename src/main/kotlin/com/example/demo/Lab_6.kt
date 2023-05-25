package com.example.demo

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class Lab_6 : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(Lab_6::class.java.getResource("main-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 700.0, 300.0)
        stage.title = "Hello!"
        stage.scene = scene
        stage.show()
    }
}

fun main() {
    Application.launch(Lab_6::class.java)
}