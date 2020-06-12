package qucumbah;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
  @Override
  public void start(Stage stage) {
    var view = new View();
    var model = new Model();
    var viewModel = new ViewModel(view, model);

    viewModel.initialize();

    stage.setScene(view.getScene());
    stage.setTitle("Timelapsee");
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
