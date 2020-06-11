package qucumbah;

import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.converter.NumberStringConverter;

public class View {
  private GridPane mainPane = new GridPane();

  public View() {
    mainPane.setPadding(new Insets(20));
    mainPane.setHgap(10);
    mainPane.setVgap(10);
  }

  public Scene getScene() {
    return new Scene(mainPane);
  }

  private Map<String, EventHandler<ActionEvent>> listeners = new HashMap<>();

  public void setEventListener(String eventName, EventHandler<ActionEvent> listener) {
    listeners.put(eventName, listener);
  }

  private EventHandler<ActionEvent> getEventListener(String eventName) {
    return listeners.getOrDefault(eventName, null);
  }

  public double readValueAsDouble(String value) {
    String valueWithoutSpaces = value.replaceAll(" ", "");
    try {
      return Double.parseDouble(valueWithoutSpaces);
    } catch (NumberFormatException exception) {
      return -1;
    }
  }

  private TextField framesPerUnitOfTimeInput = new TextField();
  private ComboBox<String> unit = new ComboBox<>();
  private ComboBox<String> period = new ComboBox<>();

  {
    unit.getItems().addAll("Frames", "Seconds");
    unit.setValue("Frames");

    period.getItems().addAll("Second", "Minute", "Hour");
    period.setValue("Minute");

    framesPerUnitOfTimeInput.setTextFormatter(
        new TextFormatter<>(new NumberStringConverter())
    );
  }

  private double getFramesPerSecond() {
    double unitMultiplier = unit.getValue().equals("Frames") ? 1 : 1000;
    double periodDivisor;
    switch (period.getValue()) {
      case "Second": periodDivisor = 1; break;
      case "Minute": periodDivisor = 60; break;
      case "Hour": periodDivisor = 3600; break;
      default: periodDivisor = 1;
    }

    String textFieldValue = framesPerUnitOfTimeInput.textProperty().get();
    double inputValue = readValueAsDouble(textFieldValue);
    return unitMultiplier * inputValue / periodDivisor;
  }

  private TextField timeBetweenFramesInput = new TextField();
  private ComboBox<String> time = new ComboBox<>();

  {
    time.getItems().addAll("Seconds", "Minutes");
    time.setValue("Seconds");

    timeBetweenFramesInput.setTextFormatter(
        new TextFormatter<>(new NumberStringConverter())
    );
  }

  private double getSecondsBetweenFrames() {
    double timeMultiplier = time.getValue().equals("Seconds") ? 1 : 60;

    String textFieldValue = timeBetweenFramesInput.textProperty().get();
    double inputValue = readValueAsDouble(textFieldValue);
    return timeMultiplier * inputValue;
  }

  private ToggleGroup recordTypeButtons = new ToggleGroup();
  private RadioButton setRecordTypeFramesPerUnitOfTime = new RadioButton(
      "Use frames per unit of time");
  private RadioButton setRecordTypeTimeBetweenFrames = new RadioButton(
      "Use amount of time between frames");

  {
    setRecordTypeFramesPerUnitOfTime.setToggleGroup(recordTypeButtons);
    setRecordTypeTimeBetweenFrames.setToggleGroup(recordTypeButtons);
    setRecordTypeFramesPerUnitOfTime.setSelected(true);
  }

  public double getFrameIntervalMilliseconds() {
    if (setRecordTypeTimeBetweenFrames.isSelected()) {
      return getSecondsBetweenFrames() * 1000;
    } else {
      return 1000 / getFramesPerSecond();
    }
  }

  public void setStartMode() {
    mainPane.getChildren().clear();

    mainPane.add(setRecordTypeFramesPerUnitOfTime, 0, 0);

    var framesPerUnitOfTimeControlsContainer = new HBox();
    framesPerUnitOfTimeControlsContainer.setSpacing(10);
    framesPerUnitOfTimeControlsContainer.getChildren().addAll(
        unit,
        new Label("per"),
        period,
        framesPerUnitOfTimeInput
    );
    mainPane.add(framesPerUnitOfTimeControlsContainer, 0, 1);

    mainPane.add(setRecordTypeTimeBetweenFrames, 0, 2);

    var timeBetweenFramesControlsContainer = new HBox();
    timeBetweenFramesControlsContainer.setSpacing(10);
    timeBetweenFramesControlsContainer.getChildren().addAll(
        time,
        new Label("between frames"),
        timeBetweenFramesInput
    );
    mainPane.add(timeBetweenFramesControlsContainer, 0, 3);

    var startAndStopButtonsContainer = new HBox();
    startAndStopButtonsContainer.setSpacing(10);
    var startButton = new Button("Start");
    startButton.onActionProperty().setValue(getEventListener("recordStart"));
    var exitButton = new Button("Exit");
    exitButton.onActionProperty().setValue(getEventListener("startModeExit"));
    startAndStopButtonsContainer.getChildren().addAll(startButton, exitButton);
    mainPane.add(startAndStopButtonsContainer, 0, 4);
  }

  private Label recordingTimeLabel = new Label("Recording for: 0 seconds");
  private Label timelapseLengthLabel = new Label("Timelapse length: 0 seconds");

  private String beautifyTime(double milliseconds) {
    double seconds = Math.floor(milliseconds / 1000);
    double minutes = Math.floor(seconds / 60);
    double hours = Math.floor(minutes / 60);

    String hoursString = (hours == 0) ? "" : hours + " hours";
    String minutesString = (minutes == 0) ? "" : hours + " minutes";
    String secondsString = seconds + " seconds";

    return hoursString + minutesString + secondsString;
  }

  public void updateRecordModeLabels(
      double recordingTimeMilliseconds,
      double timelapseLengthMilliseconds
  ) {
    String beautifiedRecordingTime = beautifyTime(recordingTimeMilliseconds);
    recordingTimeLabel.setText("Recording for:" + beautifiedRecordingTime);
    String beautifiedTimelapseLength = beautifyTime(timelapseLengthMilliseconds);
    timelapseLengthLabel.setText("Timelapse length:" + beautifiedTimelapseLength);
  }

  public void setRecordMode() {
    mainPane.getChildren().clear();

    mainPane.add(new Label("Recording in process"), 0, 0);
    mainPane.add(recordingTimeLabel, 0, 1);
    mainPane.add(timelapseLengthLabel, 0, 2);
    var stopButton = new Button("Stop and save/discard");
    stopButton.onActionProperty().setValue(getEventListener("recordStop"));
    mainPane.add(stopButton, 0, 3);
  }

  public void showInvalidInputWarning() {
    var alert = new Alert(Alert.AlertType.ERROR, "Invalid input value");
    alert.showAndWait();
  }
}
