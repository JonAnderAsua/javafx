package ehu.isad.controller.ui;

import ehu.isad.Book;
import ehu.isad.Main;
import ehu.isad.controller.db.ZerbitzuKud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.sql.SQLException;
import java.util.List;

public class LiburuKud {

    private ObservableList<Book> liburuak;
    private List<Book> liburuList ;

    @FXML
    private ComboBox comboBox;

    @FXML
    private Button botoia;

    private Main mainApp;

    public void setMainApp(Main main) {
        this.mainApp = main;
    }


    @FXML
    void sacatu(ActionEvent event) throws Exception {
        Book b = (Book) comboBox.getValue();
        mainApp.xeheErakutsi(b);
    }

    @FXML
    public void initialize() throws SQLException {
        liburuList = ZerbitzuKud.getInstance().lortuZerbitzuak(); //Datu basean dauden liburuak eskuratu

        liburuak = FXCollections.observableArrayList(liburuList);

        comboBox.setItems(liburuak);

    }
}