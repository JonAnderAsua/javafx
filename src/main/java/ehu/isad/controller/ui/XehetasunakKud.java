package ehu.isad.controller.ui;

import com.google.gson.Gson;
import ehu.isad.Book;
import ehu.isad.Details;
import ehu.isad.Main;
import ehu.isad.controller.db.ZerbitzuKud;
import ehu.isad.utils.Sarea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;

public class XehetasunakKud {

    private Main mainApp;
    private Gson gson;
    private Book book;
    private ZerbitzuKud zk = ZerbitzuKud.getInstance();

    @FXML
    private Text izenburuText;

    @FXML
    private Text argitalText;

    @FXML
    private Text orriKopText;

    @FXML
    private Button butAtzEg;

    @FXML
    private ImageView irudiaField;

    @FXML
    void atzeraEgin(ActionEvent event) {
        mainApp.liburuErakutsi();
    }

    public void setMainApp(Main liburuak) throws Exception {
        mainApp = liburuak;
    }

    public Book getLib(String s) throws Exception {

        Book emaitza = Sarea.URLlortu(s);

        return emaitza;
    }

    public void egin(Book b) throws Exception {
        String isbn = b.getISBN();
        String izena = b.toString();
        boolean dago = liburuaHartu(isbn); //Liburua datu basean bilatu

        if(!dago){ //Liburua datu basean ez badago
            book = this.getLib(isbn);
            sartudb(book);
        }
        else{
            book = getLiburua(isbn);
        }
        b.setIsbn(isbn);
        b.setTitle(izena);
        Details details = book.getDetails();
        izenburuText.setText(details.getTitle());
        argitalText.setText(details.getArgitaretxea());
        orriKopText.setText(String.valueOf(details.getPages()));
        String url = book.getThumbnail_url().replace("S", "L");
        Image i = createImage(url);
        irudiaField.setImage(i);
        mainApp.liburuErakutsi();
    }

    private Boolean liburuaHartu(String isbn) throws SQLException {
        Book emaitza = zk.liburuaEskatu(isbn);
        if(emaitza == null){
            return false;
        }
        else {
            return true;
        }
    }

    private Book getLiburua(String s) throws SQLException {
        Book emaitza = zk.liburuaEskatu(s);
        return emaitza;
    }

    private Image createImage(String url) throws IOException {
        URLConnection conn = new URL(url).openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36");
        try (InputStream stream = conn.getInputStream()) {
            return new Image(stream);
        }
    }

    private void sartudb(Book b){
        zk.sartuDb(b);
    }
}

