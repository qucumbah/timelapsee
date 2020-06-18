package qucumbah;

import java.io.File;
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
    model.setEventListener("frameRecorded", (event) -> {
      long recordingTimeMilliseconds = System.currentTimeMillis() - recordingStartMilliseconds;

      Platform.runLater(() -> view.updateRecordModeLabels(
          recordingTimeMilliseconds,
          (recordingTimeMilliseconds / frameIntervalMilliseconds) * 1000 / 60
      ));
    });
    model.setEventListener(
        "recordingError",
        (event) -> view.showRecordingErrorMessage()
    );
    model.setEventListener("recordingSuccess", (event) -> {
      view.showRecordingSuccessMessage();
      Platform.exit();
    });
    model.startRecording(frameIntervalMilliseconds, outputFile);

    view.setEventListener("recordStop", (event) -> model.stopRecording());

    view.setRecordMode();
  }
}
