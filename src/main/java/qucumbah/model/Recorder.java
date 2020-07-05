package qucumbah.model;

import java.io.IOException;

public abstract class Recorder {
  public abstract void startRecording() throws IOException;

  public abstract void recordFrame() throws IOException;

  public abstract void saveRecording() throws IOException;

  public abstract void discardRecording() throws IOException;
}
