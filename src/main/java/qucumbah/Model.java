package qucumbah;

import java.awt.Toolkit;
import javafx.event.Event;

public class Model extends EventEmitter<Event> {
  private Thread recordingThread;
  private int frameIntervalMilliseconds;
  private int frameIntervalNanoseconds;

  private Recorder recorder;

  public Model() {
    // recorder = new JCodecRecorder();
  }

  private void recordingLoop() {
    // recorder.startRecording();
    try {
      while (true) {
        // recorder.recordFrame();
        Toolkit.getDefaultToolkit().beep();
        Thread.sleep(frameIntervalMilliseconds, frameIntervalNanoseconds);
      }
    } catch (InterruptedException exception) {
      // Stop and save recording
      emit("recordStop", null);
      Toolkit.getDefaultToolkit().beep();
    }
  }

  public void startRecording(double frameIntervalMillisecondsDouble) {
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
