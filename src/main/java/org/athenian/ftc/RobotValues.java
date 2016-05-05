package org.athenian.ftc;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.common.base.Splitter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Team 9523 on 2/20/2016.
 */
public class RobotValues {
  private final List<ValueWriter> valueList = new ArrayList<ValueWriter>();

  private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

  private final Firebase firebase;
  private final int      updateFreqSecs;

  public RobotValues(final Firebase firebase, final int updateFreqSecs) {
    this.firebase = firebase;
    this.updateFreqSecs = updateFreqSecs;
  }

  public double getUpdateFreqSecs() { return this.updateFreqSecs; }

  public void start() {
    this.executor
        .scheduleWithFixedDelay(
            new Runnable() {
              @Override
              public void run() {
                for (final ValueWriter value : valueList) {
                  Firebase fb = firebase;
                  for (String val : value.getPath())
                    fb = fb.child(val);
                  try {
                    final Object obj = value.getValueSource().getValue();
                    fb.setValue(obj);
                  }
                  catch (Throwable e) {
                    fb.setValue(String.format("Exception thrown in getValue() %s [%s]",
                                              e.getClass().getSimpleName(), e.getMessage()));
                  }
                }
              }
            }, 1, this.updateFreqSecs * 1000, TimeUnit.MILLISECONDS);
  }

  public void stop() { this.executor.shutdownNow(); }

  public RobotValues add(final ValueWriter valueWriter) {
    this.valueList.add(valueWriter);
    return this;
  }

  public RobotValues add(final ValueListener listener) {
    final List<String> pathVals = Splitter.on(".")
                                          .splitToList(listener.getPath());
    Firebase fb = this.firebase;
    for (final String val : pathVals)
      fb = fb.child(val);

    // Write an empty node
    fb.setValue("");

    fb.addValueEventListener(
        new ValueEventListener() {
          @Override
          public void onDataChange(final DataSnapshot snapshot) {
            // Catch exceptions here to avoid blowing up the client app
            try {
              listener.getListenerAction()
                      .onValueChanged(snapshot.getValue());
            }
            catch (Throwable e) {
              // Ignore the exception
            }
          }

          @Override
          public void onCancelled(final FirebaseError error) {
          }
        });

    return this;
  }
}