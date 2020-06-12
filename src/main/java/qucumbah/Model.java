package qucumbah;

import java.awt.AWTException;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import javafx.event.Event;

public class Model extends EventEmitter<Event> {
  private Thread recordingThread;
  private int frameIntervalMilliseconds;
  private int frameIntervalNanoseconds;

  private Recorder recorder;

  private void recordingLoop() {
    recorder.startRecording();
    try {
      while (true) {
        recorder.recordFrame();
        Thread.sleep(frameIntervalMilliseconds, frameIntervalNanoseconds);
      }
    } catch (InterruptedException exception) {
      recorder.saveRecording();
      emit("recordStop", null);
    }
  }

  public void startRecording(
      double frameIntervalMillisecondsDouble,
      File outputFile
  ) throws AWTException, IOException {
    recorder = new JCodecRecorder(outputFile);

    frameIntervalMilliseconds = (int)frameIntervalMillisecondsDouble;
    frameIntervalNanoseconds =
        (int)(frameIntervalMillisecondsDouble - frameIntervalMilliseconds);

    recordingThread = new Thread(this::recordingLoop);
    recordingThread.start();
  }

  public void stopRecording() {
    recordingThread.interrupt();

    Toolkit.getDefaultToolkit().beep();
  }
}
