package qucumbah;

import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class RecordModeViewContent extends ViewContent {
  private Label recordingTimeLabel = new Label("Recording for: 0 seconds");
  private Label timelapseLengthLabel = new Label("Timelapse length: 0 seconds");

  private Button stopButton = new Button("Stop and save");

  private GridPane mainPane = new GridPane();

  {
    mainPane.setPadding(new Insets(20));
    mainPane.setHgap(10);
    mainPane.setVgap(10);

    mainPane.add(new Label("Recording in process"), 0, 0);
    mainPane.add(recordingTimeLabel, 0, 1);
    mainPane.add(timelapseLengthLabel, 0, 2);
    mainPane.add(stopButton, 0, 3);
  }

  private String beautifyTime(double milliseconds) {
    double secondsRaw = Math.floor(milliseconds / 1000);
    double minutesRaw = Math.floor(secondsRaw / 60);
    double hoursRaw = Math.floor(minutesRaw / 60);

    int hours = (int)(hoursRaw);
    int minutes = (int)(minutesRaw % 60);
    int seconds = (int)(secondsRaw % 60);

    String hoursString = (hours == 0) ? "" : hours + " hours ";
    String minutesString = (minutes == 0) ? "" : minutes + " minutes ";
    String secondsString = seconds + " seconds ";

    return hoursString + minutesString + secondsString;
  }

  public void updateRecordTimeLabels(
      double recordingTimeMilliseconds,
      double timelapseLengthMilliseconds
  ) {
    String beautifiedRecordingTime = beautifyTime(recordingTimeMilliseconds);
    recordingTimeLabel.setText("Recording for: " + beautifiedRecordingTime);
    String beautifiedTimelapseLength = beautifyTime(timelapseLengthMilliseconds);
    timelapseLengthLabel.setText("Timelapse length: " + beautifiedTimelapseLength);
  }

  @Override
  public void bindHandlers(Map<String, EventHandler<ActionEvent>> handlers) {
    EventHandler<ActionEvent> recordStopHandler = handlers.getOrDefault("recordStop", null);
    stopButton.onActionProperty().setValue(recordStopHandler);
  }

  @Override
  public GridPane getRoot() {
    return mainPane;
  }
}
