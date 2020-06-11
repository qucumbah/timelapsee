package qucumbah;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class App extends Application {
  @Override
  public void start(Stage stage) {
    var view = new View();
    var model = new Model();
    var viewModel = new ViewModel(view, model);

    viewModel.start();

    stage.setScene(view.getScene());
    stage.setTitle("Timelapsee");
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
