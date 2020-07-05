package qucumbah;

import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;

public abstract class ViewContent {
  public abstract void bindHandlers(Map<String, EventHandler<ActionEvent>> handlers);

  public abstract Parent getRoot();
}
