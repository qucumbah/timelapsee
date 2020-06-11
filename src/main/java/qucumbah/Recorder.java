package qucumbah;

import java.io.File;

public abstract class Recorder {
  public abstract void startRecording();

  public abstract void recordFrame();

  public abstract void saveRecording(File saveFile);
}
