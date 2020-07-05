package qucumbah.util;

import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public abstract class JavaFXEventEmitterAdapter {
  protected Map<String, EventHandler<ActionEvent>> eventHandlers = new HashMap<>();

  public void setEventHandler(String eventName, Runnable actualHandler) {
    EventHandler<ActionEvent> javaFXHandler = (event) -> actualHandler.run();
    eventHandlers.put(eventName, javaFXHandler);
  }
}
