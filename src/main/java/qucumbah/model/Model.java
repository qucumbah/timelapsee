package qucumbah.model;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import qucumbah.util.EventEmitter;
import qucumbah.util.Timer;

public class Model extends EventEmitter {
  private Recorder recorder;
  private Timer recordingTimer;

  public void startRecording(double frameIntervalMillisecondsDouble, File outputFile) {
    try {
      recorder = new JCodecRecorder(outputFile);
      recorder.startRecording();
    } catch (IOException | AWTException exception) {
      signalRecordingError();
      return;
    }

    Runnable tryToRecordFrame = () -> {
      try {
        recorder.recordFrame();
        signal("frameRecorded");
      } catch (IOException exception) {
        signalRecordingError();
      }
    };

    int frameIntervalMilliseconds = (int)frameIntervalMillisecondsDouble;
    int frameIntervalNanoseconds =
        (int)((frameIntervalMillisecondsDouble - frameIntervalMilliseconds) * 1000);

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
    signal("recordingError");
  }

  private void signalRecordingSuccess() {
    signal("recordingSuccess");
  }
}
