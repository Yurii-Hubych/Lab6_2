package com.example.demo

import com.example.demo.data.DataBaseConnector
import com.example.demo.data.DataBaseRepository
import com.example.demo.data.Film
import com.example.demo.data.Repository
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.ComboBox
import javafx.scene.control.ListView
import javafx.stage.Stage
import java.io.IOException
import java.net.URL
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors

class MainController : Initializable {
    @FXML
    lateinit var listFilms: ListView<*>

    @FXML
    lateinit var actorsCombo: ComboBox<String>

    @FXML
    lateinit var formatsCombo: ComboBox<String>
    private lateinit var repository: Repository
    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {
        repository = DataBaseRepository(
                DataBaseConnector("carsShopDB"))
        updateListView()
        actorsCombo.onAction = EventHandler { event: ActionEvent? ->
            val selectedActor = actorsCombo.selectionModel.selectedItem
            filterByActor(selectedActor)
        }
        formatsCombo.onAction = EventHandler { event: ActionEvent? ->
            val selectedFormat = formatsCombo.selectionModel.selectedItem
            filterByFormat(selectedFormat)
        }
    }

    fun updateListView() {
        val films = repository.all
        val filmsList = FXCollections.observableList(films)
        listFilms.items = filmsList
        var actors: MutableList<String> = ArrayList()
        actors.addAll(
                films
                        .stream()
                        .map { film: Film ->
                            film.actors.stream().map<String> { obj: String -> obj.trim { it <= ' ' } }
                                    .collect(Collectors.toList())
                        }
                        .flatMap { obj: List<String> -> obj.stream() }
                        .distinct()
                        .collect(Collectors.toList())
        )
        actors = ArrayList(LinkedHashSet(actors))
        actors.add("all")
        val actorsList = FXCollections.observableList(actors)
        actorsCombo.items = actorsList
        actorsCombo.selectionModel.select(actors.size - 1)
        val formats = films
                .stream()
                .map<String> { film: Film -> film.fileExtension }
                .distinct()
                .collect(Collectors.toList<String>())
        formats.add("all")
        val formatsList = FXCollections.observableList(formats)
        formatsCombo.items = formatsList
        formatsCombo.selectionModel.select(formats.size - 1)
    }


    @FXML
    fun deleteFilm(actionEvent: ActionEvent?) {
        val toDelete = listFilms.selectionModel.selectedItem as Film
        repository.deleteFilm(toDelete.id)
        updateListView()
    }


    @FXML
    fun addNewFilm(actionEvent: ActionEvent?) {
        val newWindow = Stage()
        val loader = FXMLLoader(Lab_6::class.java.getResource(
                "add-film-form.fxml"
        )
        )
        var root: Parent? = null
        root = try {
            loader.load()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        newWindow.title = "Додати фільм"
        newWindow.scene = Scene(root, 450.0, 300.0)
        //newWindow.initModality(Modality.WINDOW_MODAL);
        val secondController = loader.getController<AddFilmController>()
        secondController.set_repository(repository)
        secondController.set_mainController(this)
        newWindow.show()
    }

    fun filterByActor(actor: String) {
        val films = if (actor == "all") {
            repository.all
        } else {
            repository.getAllByActor(actor)
        }
        val filmsList = FXCollections.observableList(films)
        listFilms.items = filmsList
        formatsCombo.selectionModel.select("all")
    }


    fun filterByFormat(format: String?) {
        val films = if (format == "all") {
            repository.all
        } else {
            repository.getAllByFormat(format)
        }
        val filmsList = FXCollections.observableList(films)
        listFilms.items = filmsList
        actorsCombo.selectionModel.select("all")
    }

}
