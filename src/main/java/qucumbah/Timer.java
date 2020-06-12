package qucumbah;

public class Timer {
  private Runnable action;
  private int timeoutMilliseconds;
  private int timeoutNanoseconds;

  public Timer(Runnable action, int timeoutMilliseconds, int timeoutNanoseconds) {
    this.action = action;
    this.timeoutMilliseconds = timeoutMilliseconds;
    this.timeoutNanoseconds = timeoutNanoseconds;
  }

  public Timer(Runnable action, int timeoutMilliseconds) {
    this(action, timeoutMilliseconds, 0);
  }

  private Thread thread;

  public Timer start() {
    if (thread != null) {
      return this;
    }

    thread = new Thread(this::threadLoop);
    thread.start();

    return this;
  }

  public void stop() {
    if (thread == null) {
      return;
    }

    thread.interrupt();
  }

  private void threadLoop() {
    try {
      while (true) {
        action.run();
        Thread.sleep(timeoutMilliseconds, timeoutNanoseconds);
      }
    } catch (InterruptedException exception) {
      thread = null;
    }
  }
}
