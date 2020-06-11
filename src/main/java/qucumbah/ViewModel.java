package qucumbah;

import javafx.application.Platform;

public class ViewModel {
  private View view;
  private Model model;

  public ViewModel(View view, Model model) {
    this.view = view;
    this.model = model;
  }

  public void start() {
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
    model.startRecording(frameInterval);
    view.setEventListener("recordStop", (event) -> model.stopRecording());

    view.setRecordMode();
  }
}
