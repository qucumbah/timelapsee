package qucumbah.view;

import java.util.Locale;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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

public class StartModeViewContent extends ViewContent {
  private TextField framesPerUnitOfTimeInput = new TextField();
  private ComboBox<String> unit = new ComboBox<>();
  private ComboBox<String> period = new ComboBox<>();

  {
    unit.getItems().addAll("Frames", "Seconds");
    unit.setValue("Frames");

    period.getItems().addAll("Second", "Minute", "Hour");
    period.setValue("Minute");

    framesPerUnitOfTimeInput.setTextFormatter(
        new TextFormatter<>(new NumberStringConverter(Locale.US)));
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
        new TextFormatter<>(new NumberStringConverter(Locale.US)));
  }

  private double getSecondsBetweenFrames() {
    double timeMultiplier = time.getValue().equals("Seconds") ? 1 : 60;

    String textFieldValue = timeBetweenFramesInput.textProperty().get();
    double inputValue = readValueAsDouble(textFieldValue);
    return timeMultiplier * inputValue;
  }

  private double readValueAsDouble(String value) {
    String valueWithoutSpaces = value.replaceAll(" ", "");
    try {
      return Double.parseDouble(valueWithoutSpaces);
    } catch (NumberFormatException exception) {
      return -1;
    }
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

  private Button startButton = new Button("Start");
  private Button exitButton = new Button("Start");

  private GridPane mainPane = new GridPane();

  {
    mainPane.setPadding(new Insets(20));
    mainPane.setHgap(10);
    mainPane.setVgap(10);

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
    startAndStopButtonsContainer.getChildren().addAll(startButton, exitButton);
    mainPane.add(startAndStopButtonsContainer, 0, 4);
  }

  @Override
  public void bindHandlers(Map<String, EventHandler<ActionEvent>> handlers) {
    EventHandler<ActionEvent> recordStartHandler = handlers.getOrDefault("recordStart", null);
    EventHandler<ActionEvent> startModeExitHandler = handlers.getOrDefault("startModeExit", null);

    startButton.onActionProperty().setValue(recordStartHandler);
    exitButton.onActionProperty().setValue(startModeExitHandler);
  }

  @Override
  public GridPane getRoot() {
    return mainPane;
  }
}
