# Timelapsee
This is a small JavaFX app that can record sped-up footage (timelapse) of the screen.

## How to use it
First, you specify how far apart frame captures will be.

![start](https://user-images.githubusercontent.com/39967396/87251367-de6a1a00-c473-11ea-8aac-ff9e6d703a70.png)

Then, you choose the resulting file location.
Lastly, when you think the recording is done, you press the "Stop and save" button to finish recording.

![end](https://user-images.githubusercontent.com/39967396/87251368-e033dd80-c473-11ea-89be-2127bd7f8635.png)

## What does it use under the hood?
Timelapsee uses a combination of [AWT robot](https://docs.oracle.com/en/java/javase/14/docs/api/java.desktop/java/awt/Robot.html)
to create screen captures and [JCodec](http://jcodec.org/) to stitch them together, although a different kind of recorder is easily implementable.
