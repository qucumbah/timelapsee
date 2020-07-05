package qucumbah.viewmodel;

import java.io.File;
import javafx.application.Platform;
import qucumbah.model.Model;
import qucumbah.view.View;

public class ViewModel {
  private View view;
  private Model model;

  public ViewModel(View view, Model model) {
    this.view = view;
    this.model = model;
  }

  public void initialize() {
    view.setEventHandler("startModeExit", Platform::exit);
    view.setEventHandler("recordStart", this::startRecording);

    view.setStartMode();
  }

  private void startRecording() {
    double frameIntervalMilliseconds = view.getFrameIntervalMilliseconds();
    final int MIN_FRAME_INTERVAL = 200;
    if (frameIntervalMilliseconds < MIN_FRAME_INTERVAL) {
      view.showInvalidInputWarning();
      return;
    }

    File outputFile = view.getOutputFile();
    if (outputFile == null) {
      return;
    }

    long recordingStartMilliseconds = System.currentTimeMillis();
    model.setEventHandler("frameRecorded", () -> {
      long recordingTimeMilliseconds = System.currentTimeMillis() - recordingStartMilliseconds;

      Platform.runLater(() -> view.updateRecordTimeLabels(
          recordingTimeMilliseconds,
          (recordingTimeMilliseconds / frameIntervalMilliseconds) * 1000 / 60
      ));
    });
    model.setEventHandler("recordingError", () -> view.showRecordingErrorMessage());
    model.setEventHandler("recordingSuccess", () -> {
      view.showRecordingSuccessMessage();
      Platform.exit();
    });

    model.startRecording(frameIntervalMilliseconds, outputFile);

    view.setEventHandler("recordStop", () -> model.stopRecording());

    view.setRecordMode();
  }
}
