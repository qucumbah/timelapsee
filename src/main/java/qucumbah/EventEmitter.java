package qucumbah;

import java.util.HashMap;
import java.util.Map;

public abstract class EventEmitter {
  private final Map<String, Runnable> handlers = new HashMap<>();

  protected Runnable getEventHandler(String eventName) {
    return handlers.getOrDefault(eventName, null);
  }

  protected void signal(String eventName) {
    Runnable handler = getEventHandler(eventName);
    if (handler == null) {
      return;
    }

    handler.run();
  }

  public void setEventHandler(String eventName, Runnable handler) {
    handlers.put(eventName, handler);
  }
}
