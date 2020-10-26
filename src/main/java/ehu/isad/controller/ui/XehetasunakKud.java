package ehu.isad.controller.ui;

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

    private final ZerbitzuKud zk = ZerbitzuKud.getInstance();

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
    void atzeraEgin(ActionEvent event) {mainApp.liburuErakutsi();}

    public void setMainApp(Main liburuak) throws Exception {mainApp = liburuak;}

    public Book getLib(String s) throws Exception {return Sarea.URLlortu(s);}

    public void egin(Book b) throws Exception {
        Book book = new Book("","");
        String isbn = b.getISBN();
        String izena = b.toString();
        boolean dago = liburuaHartu(isbn); //Liburua datu basean bilatu

        if(!dago){ //Liburua datu basean ez badago
            book = this.getLib(isbn);
            sartudb(book); //Liburua datu basean sartu
        }
        else{
            book = getLiburua(isbn); //Liburua datu basetik hartu
        }

        //Hurrengo set-ak "parche" bezala funtzionatzen dute
        book.setIsbn(isbn);
        book.setTitle(izena);

        //Liburuaren xehetasunak eskuratu
        int orriKop = orriakEskuratu(book);
        String publisher = getPublisherDBtik(book);
        Details details = new Details(orriKop,publisher);

        comboBoxeanSartu(book,details);
        mainApp.liburuErakutsi();

    }

    private String getPublisherDBtik(Book b) {
        return zk.getPublisher(b);
    }

    private int orriakEskuratu(Book b) throws SQLException {return zk.orriakEskuratu(b);}

    private Boolean liburuaHartu(String isbn) throws SQLException {
        Book emaitza = zk.liburuaEskatu(isbn);

        if(emaitza.getThumbnail_url() == null){ //Liburua datu basean ez badago
            return false;
        }
        else {
            return true;
        }
    }

    private Book getLiburua(String s) throws SQLException {return zk.liburuaEskatu(s);}

    private Image createImage(String url) throws IOException {
        URLConnection conn = new URL(url).openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36");
        try (InputStream stream = conn.getInputStream()) {
            return new Image(stream);
        }
    }

    private void sartudb(Book b){zk.sartuDb(b);}

    private void comboBoxeanSartu(Book book, Details details) throws IOException {
        izenburuText.setText(book.toString());
        argitalText.setText(details.getArgitaretxea());
        orriKopText.setText(String.valueOf(details.getPages()));
        String url = book.getThumbnail_url().replace("S", "L");
        Image i = createImage(url); //Aldatu behar da irudiak karpetatik gordetzeko.
        irudiaField.setImage(i);
    }
}

