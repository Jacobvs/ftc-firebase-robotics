package org.athenian.ftc;

import com.google.common.base.Splitter;

import java.util.List;

/**
 * Created by Team9523 on 2/20/2016.
 */
public class ValueWriter {
  private final List<String> path;
  private final ValueSource  valueSource;

  public ValueWriter(final String path, final ValueSource valueSource) {
    this.path = Splitter.on(".").splitToList(path);
    this.valueSource = valueSource;
  }

  public ValueSource getValueSource() { return this.valueSource; }

  public List<String> getPath() { return this.path; }
}