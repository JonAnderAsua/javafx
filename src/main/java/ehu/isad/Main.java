package ehu.isad;

import ehu.isad.controller.ui.KautotuKud;
import ehu.isad.controller.ui.LiburuKud;
import ehu.isad.controller.ui.MainKud;
import ehu.isad.controller.ui.XehetasunakKud;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

  private Parent kautotuUI;
  private Parent mainUI;

  private Stage stage;

  private LiburuKud kautotuKud;
  private XehetasunakKud mainKud;

  private Scene libKudScene ; // Liburua aukeratzeko Scene
  private Scene xeheScene ; // Xehetasunak ikusteko Scene


  @Override
  public void start(Stage primaryStage) throws Exception {

    stage = primaryStage;
    pantailakKargatu();

    stage.setTitle("Liburuak Aukeratu");
    stage.setScene(new Scene(kautotuUI, 450, 275));
    stage.show();
  }

  private void pantailakKargatu() throws Exception {

    FXMLLoader loaderKautotu = new FXMLLoader(getClass().getResource("/Liburuak.fxml"));
    kautotuUI = (Parent) loaderKautotu.load();
    kautotuKud = loaderKautotu.getController();
    kautotuKud.setMainApp(this);

    FXMLLoader loaderMain = new FXMLLoader(getClass().getResource("/Xehetasunak.fxml"));
    mainUI = (Parent) loaderMain.load();
    mainKud = loaderMain.getController();
    mainKud.setMainApp(this);
  }


  public static void main(String[] args) {
    launch(args);
  }

  public void mainErakutsi() {
    stage.setScene(new Scene(mainUI));
    stage.show();
  }

  public void xeheErakutsi(Book b) throws Exception {
    mainKud.egin(b);
    stage.setScene(xeheScene);
    stage.show();
  }

  public void liburuErakutsi() {
    stage.setScene(libKudScene);
    stage.show();
  }
}
