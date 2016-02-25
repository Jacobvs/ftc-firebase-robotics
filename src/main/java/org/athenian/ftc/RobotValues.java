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
  private final long updateFreqMillis;
  private final ScheduledThreadPoolExecutor executor;
  private final Firebase firebaseRef;

  public RobotValues(final Firebase firebaseRef, double updateFreq) {
    this.updateFreqMillis = (long) (updateFreq * 1000);
    this.executor = new ScheduledThreadPoolExecutor(1);
    this.firebaseRef = firebaseRef;
  }

  public double getUpdateFreq() {
    return this.updateFreqMillis;
  }

  public void start() {
    this.executor
        .scheduleWithFixedDelay(
            new Runnable() {
              @Override
              public void run() {
                for (ValueWriter value : valueList) {
                  Firebase fb = firebaseRef;
                  for (String val : value.getPath())
                    fb = fb.child(val);
                  try {
                    final Object obj = value.getValueSource().getValue();
                    fb.setValue(obj);
                  } catch (Throwable e) {
                    fb.setValue(String.format("Exception thrown in getValue() %s [%s]",
                        e.getClass().getSimpleName(), e.getMessage()));
                  }
                }
              }
            }, 1, this.updateFreqMillis, TimeUnit.MILLISECONDS);
  }

  public void stop() {
    this.executor.shutdownNow();
  }

  public RobotValues add(final ValueWriter valueWriter) {
    this.valueList.add(valueWriter);
    return this;
  }

  public RobotValues add(final ValueListener valueListener) {
    final List<String> pathVals = Splitter.on(".").splitToList(valueListener.getPath());
    Firebase fb = this.firebaseRef;
    for (String val : pathVals)
      fb = fb.child(val);
    fb.setValue("");

    fb.addValueEventListener(
        new ValueEventListener() {
          @Override
          public void onDataChange(final DataSnapshot dataSnapshot) {
            valueListener.getListenerAction().onValueChanged(dataSnapshot.getValue());
          }

          @Override
          public void onCancelled(final FirebaseError error) {
          }
        });
    return this;
  }

}