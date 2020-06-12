package qucumbah;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import javafx.application.Platform;

public class ViewModel {
  private View view;
  private Model model;

  public ViewModel(View view, Model model) {
    this.view = view;
    this.model = model;
  }

  public void initialize() {
    view.setEventListener("startModeExit", (event) -> Platform.exit());
    view.setEventListener("recordStart", (event) -> startRecording());

    view.setStartMode();
  }

  private void startRecording() {
    double frameInterval = view.getFrameIntervalMilliseconds();
    if (frameInterval < 0) {
      view.showInvalidInputWarning();
      return;
    }

    File outputFile = view.getOutputFile();
    if (outputFile == null) {
      return;
    }

    try {
      model.startRecording(frameInterval, outputFile);

      view.setEventListener("recordStop", (event) -> model.stopRecording());

      view.setRecordMode();
    } catch (IOException | AWTException exception) {
      view.showUnknownError(exception.getMessage());
    }
  }
}
