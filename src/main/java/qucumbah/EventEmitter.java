package qucumbah;

import java.util.HashMap;
import java.util.Map;
import javafx.event.Event;
import javafx.event.EventHandler;

public abstract class EventEmitter<T extends Event> {
  private final Map<String, EventHandler<T>> listeners = new HashMap<>();

  protected final EventHandler<T> getEventListener(String eventName) {
    return listeners.getOrDefault(eventName, null);
  }

  protected final void emit(String eventName, T event) {
    EventHandler<T> handler = getEventListener(eventName);
    if (handler == null) {
      return;
    }

    handler.handle(event);
  }

  public final void setEventListener(String eventName, EventHandler<T> listener) {
    listeners.put(eventName, listener);
  }
}
