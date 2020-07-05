package qucumbah;

import java.io.File;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class View extends JavaFXEventEmitterAdapter {
  private StartModeViewContent startModeContent;
  private RecordModeViewContent recordModeContent;
  private Scene mainScene;

  public Scene getScene() {
    return mainScene;
  }

  private void setModeView(ViewContent newModeView) {
    newModeView.bindHandlers(this.eventHandlers);

    if (mainScene == null) {
      mainScene = new Scene(newModeView.getRoot());
    } else {
      mainScene.setRoot(newModeView.getRoot());
    }
  }

  public void setStartMode() {
    startModeContent = new StartModeViewContent();

    setModeView(startModeContent);
  }

  public void setRecordMode() {
    recordModeContent = new RecordModeViewContent();

    setModeView(recordModeContent);
  }

  public double getFrameIntervalMilliseconds() {
    return startModeContent.getFrameIntervalMilliseconds();
  }

  public void updateRecordTimeLabels(
      double recordingTimeMilliseconds,
      double timelapseLengthMilliseconds
  ) {
    recordModeContent.updateRecordTimeLabels(recordingTimeMilliseconds, timelapseLengthMilliseconds);
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
