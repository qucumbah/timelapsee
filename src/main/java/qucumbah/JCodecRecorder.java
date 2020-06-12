package qucumbah;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.jcodec.api.awt.AWTSequenceEncoder;

public class JCodecRecorder extends Recorder {
  private AWTSequenceEncoder encoder;
  private Robot screenCapturingRobot;
  private Rectangle screenRectangle;

  public static final int FRAMES_PER_SECOND = 60;

  public JCodecRecorder(File outputFile) throws AWTException, IOException {
    encoder = AWTSequenceEncoder.createSequenceEncoder(outputFile, FRAMES_PER_SECOND);
    screenCapturingRobot = new Robot();
    screenRectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
  }

  @Override
  public void startRecording() throws IOException {

  }

  @Override
  public void recordFrame() throws IOException {
    BufferedImage screenImage = screenCapturingRobot.createScreenCapture(screenRectangle);
    encoder.encodeImage(screenImage);
  }

  @Override
  public void saveRecording() throws IOException {
    encoder.finish();
  }
}
