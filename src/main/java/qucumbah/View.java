package qucumbah;

import java.io.File;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class View extends JavaFXEventEmitterAdapter {
  private StartModeView startModeView;
  private RecordModeView recordModeView;
  private Scene mainScene;

  public Scene getScene() {
    return mainScene;
  }

  public void setStartMode() {
    startModeView = new StartModeView();
    startModeView.bindHandlers(this.eventHandlers);

    if (mainScene == null) {
      mainScene = new Scene(startModeView.getMainPane());
    } else {
      mainScene.setRoot(startModeView.getMainPane());
    }
  }

  public void setRecordMode() {
    recordModeView = new RecordModeView();
    recordModeView.bindHandlers(this.eventHandlers);

    if (mainScene == null) {
      mainScene = new Scene(recordModeView.getMainPane());
    } else {
      mainScene.setRoot(recordModeView.getMainPane());
    }
  }

  public double getFrameIntervalMilliseconds() {
    return startModeView.getFrameIntervalMilliseconds();
  }

  public void updateRecordTimeLabels(
      double recordingTimeMilliseconds,
      double timelapseLengthMilliseconds
  ) {
    recordModeView.updateRecordTimeLabels(recordingTimeMilliseconds, timelapseLengthMilliseconds);
  }

  public void showInvalidInputWarning() {
    var alert = new Alert(AlertType.ERROR, "Invalid input value");
    alert.showAndWait();
  }

  public File getOutputFile() {
    var fileChooser = new FileChooser();
    fileChooser.setTitle("Save recording to");
    fileChooser.getExtensionFilters().addAll(
        new ExtensionFilter("MP4 video file", "*.mp4")
    );

    return fileChooser.showSaveDialog(null);
  }

  public void showRecordingErrorMessage() {
    var alert = new Alert(
        AlertType.ERROR,
        "Unknown recording error"
    );
    alert.showAndWait();
  }

  public void showRecordingSuccessMessage() {
    var alert = new Alert(
        AlertType.INFORMATION,
        "Success"
    );
    alert.showAndWait();
  }
}
