package qucumbah;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import javafx.event.Event;

public class Model extends EventEmitter<Event> {
  private Recorder recorder;
  private Timer recordingTimer;

  public void startRecording(double frameIntervalMillisecondsDouble, File outputFile) {
    try {
      recorder = new JCodecRecorder(outputFile);
    } catch (IOException | AWTException exception) {
      signalRecordingError();
      return;
    }

    int frameIntervalMilliseconds = (int)frameIntervalMillisecondsDouble;
    int frameIntervalNanoseconds =
        (int)(frameIntervalMillisecondsDouble - frameIntervalMilliseconds);

    Runnable tryToRecordFrame = () -> {
      try {
        recorder.recordFrame();
      } catch (IOException exception) {
        signalRecordingError();
      }
    };

    recordingTimer = new Timer(
        tryToRecordFrame,
        frameIntervalMilliseconds,
        frameIntervalNanoseconds
    ).start();
  }

  public void stopRecording() {
    recordingTimer.stop();
    try {
      recorder.saveRecording();
      signalRecordingSuccess();
    } catch (IOException exception) {
      signalRecordingError();
    }
  }

  private void signalRecordingError() {
    emit("recordingError", null);
  }

  private void signalRecordingSuccess() {
    emit("recordingSuccess", null);
  }
}
