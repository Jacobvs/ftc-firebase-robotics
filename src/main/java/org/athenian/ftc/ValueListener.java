package org.athenian.ftc;

/**
 * Created by Team9523 on 2/20/2016.
 */
public class ValueListener {
  private final String         path;
  private final ListenerAction listenerAction;

  public ValueListener(final String path, final ListenerAction listenerAction) {
    this.path = path;
    this.listenerAction = listenerAction;
  }

  public String getPath() { return this.path; }

  public ListenerAction getListenerAction() { return this.listenerAction; }

}