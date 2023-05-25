package com.example.demo

import com.example.demo.data.Film
import com.example.demo.data.Repository
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.CheckBox
import javafx.scene.control.TextField
import javafx.stage.Stage
import java.util.*

class AddFilmController {
    private var _repository: Repository? = null
    private var _mainController: MainController? = null
    fun set_mainController(_mainController: MainController?) {
        this._mainController = _mainController
    }

    fun set_repository(_repository: Repository?) {
        this._repository = _repository
    }

    @FXML
    var name: TextField? = null

    @FXML
    var date: TextField? = null

    @FXML
    var actors: TextField? = null

    @FXML
    var fileExtension: TextField? = null

    @FXML
    var size: TextField? = null

    @FXML
    var isFreeForAll: CheckBox? = null
    fun addFilmToFile(actionEvent: ActionEvent) {
        val name_ = name!!.text
        val date_ = date!!.text
        val actors_ = Arrays.asList(*actors!!.text.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
        val fileExtension_ = fileExtension!!.text
        val size_ = size!!.text.toDouble()
        val isFreeForAll_ = isFreeForAll!!.isSelected
        val newFilm = Film(name_, date_, actors_, fileExtension_, size_, isFreeForAll_)
        _repository!!.addFilm(newFilm)
        val source = actionEvent.source as Node
        val stage = source.scene.window as Stage
        _mainController!!.updateListView()
        stage.close()
    }
}
