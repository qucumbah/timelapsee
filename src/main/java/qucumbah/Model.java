package qucumbah;

import java.awt.Toolkit;

public class Model {
  private Thread recordingThread;
  private int frameIntervalMilliseconds;
  private int frameIntervalNanoseconds;

  private void recordingLoop() {
    try {
      // Record frame
      while (true) {
        Toolkit.getDefaultToolkit().beep();
        Thread.sleep(frameIntervalMilliseconds, frameIntervalNanoseconds);
      }
    } catch (InterruptedException exception) {
      // Stop and save recording
      Toolkit.getDefaultToolkit().beep();
    }
  }

  public void startRecording(double frameIntervalMillisecondsDouble) {
    frameIntervalMilliseconds = (int)frameIntervalMillisecondsDouble;
    frameIntervalNanoseconds = (int)(
        frameIntervalMillisecondsDouble - frameIntervalMilliseconds);

    recordingThread = new Thread(this::recordingLoop);
    recordingThread.start();
  }

  public void stopRecording() {
    recordingThread.interrupt();

    Toolkit.getDefaultToolkit().beep();
  }
}
