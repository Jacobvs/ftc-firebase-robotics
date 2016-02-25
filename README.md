# ftc-firebase-robotics

[![Release](https://jitpack.io/v/Jacobvs/ftc-firebase-robotics.svg)](https://jitpack.io/#Jacobvs/ftc-firebase-robotics)

Gradle usage
==============

Add a repository to your root build.gradle (The one called ftc-app):
```
allprojects {
    repositories {
        jcenter()
        flatDir {
            dirs 'out'
            mavenCentral()
            maven { url "https://jitpack.io" }
        }
    }
}
```

Add a dependency and exclude duplicate files to the FtcRobotController build.gradle file:
```
android {
    ...
    packagingOptions {
        exclude 'META-INF/LICENSE'
        exclude 'META-INF/LICENSE-FIREBASE.txt'
        exclude 'META-INF/NOTICE'
    }
}
```
(At the bottom of the file)
```
dependencies {
    ...
    compile 'com.github.Jacobvs:ftc-firebase-robotics:1.0.1'
}
```

Code Example  
===============
For more detail check out the FirebaseExample.java file

```java
import com.firebase.client.Firebase;
import org.athenian.ftc.ListenerAction;
import org.athenian.ftc.RobotValues;
import org.athenian.ftc.ValueListener;
import org.athenian.ftc.ValueSource;
import org.athenian.ftc.ValueWriter;

public class FirebaseTest extends OpMode {
 
    RobotValues robotValues = null;
 
    public FirebaseTest() {
    }
 
    @Override
    public void init() {
 
        final Firebase fb = new Firebase("https://9523-2015.firebaseio.com/");

        this.robotValues = new RobotValues(fb, 0.5);
        this.robotValues
            .add(new ValueWriter("motor.a",
                new ValueSource() {
                  @Override
                  public Object getValue() {
                    return motor.getCurrentPosition();
                  }}))
            .add(new ValueWriter("servo.a",
                new ValueSource() {
                  @Override
                  public Object getValue() {
                    return servo.getPosition();
                  }
                }))
            .add(new ValueWriter("sensor.distance.a",
                new ValueSource() {
                  @Override
                  public Object getValue() {
                    return distance.getLightDetected();
                  }}))
            .add(new ValueWriter("sensor.gyro.a",
                new ValueSource() {
                  @Override
                  public Object getValue() {
                    return String.format("The values are- Heading: %s, Rotation: %s, Raw x: %s, Raw y: %s, Raw z: %s",
                        gyro.getHeading(), gyro.getRotation(), gyro.rawX(), gyro.rawY(), gyro.rawZ());
                  }}))
            .add(new ValueWriter("sensor.color.a",
                new ValueSource() {
                  @Override
                  public Object getValue() {
                    return String.format("The values are- ARGB: %s, Alpha: %s, Red: %s, Green: %s, Blue: %s",
                        color.argb(), color.alpha(), color.red(), color.green(), color.blue());
                  }}))
            .add(new ValueListener("motor.a",
                new ListenerAction() {
                  @Override
                  public void onValueChanged(Object val) {
                    if (val != null) {
                      if (val instanceof Long) {
                        motor.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
                        motor.setTargetPosition((Integer) val);
                        motor.setPower(0.5);
                      } else {
                        telemetry.addData("IntegerException", val + " is not an Integer");
                      }
                    } else {
                      telemetry.addData("NullException", val + " is Null");
                    }
                  }
                }))
            .add(new ValueListener("servo.a",
                new ListenerAction() {
                  @Override
                  public void onValueChanged(Object val) {
                    if (val != null) {
                      if(val instanceof Long){
                        long lVal =  (Long) val;
                        if (lVal == 0 || lVal == 1 ){
                          servo.setPosition(lVal);
                        }
                      }
                      if(val instanceof Double){
                        double dVal = (Double) val;
                        if(dVal <= 0 && dVal >= 1){
                          servo.setPosition(dVal);
                        }
    
                      } else {
                        telemetry.addData("ValueException", val + " is not a Double or a Long");
                      }
                    } else {
                      telemetry.addData("NullException", val + " is Null");
                    }
                  }
                }))
        ;
    
        // Start
        this.robotValues.start();
    }
 
    @Override
    public void stop() {
    	// Stop 
        this.robotValues.stop();
    }
 }
```
