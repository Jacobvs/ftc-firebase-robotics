# ftc-firebase-robotics

[![Release](https://jitpack.io/v/Jacobvs/ftc-firebase-robotics.svg)](https://jitpack.io/#Jacobvs/ftc-firebase-robotics)

Gradle usage
==============

Add a repository to your root build.gradle (The one called ftc-app):
```
allprojects {
    repositories {
        ...
		maven { url "https://jitpack.io" }
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
    compile 'com.github.Jacobvs:ftc-firebase-robotics:1.0'
}
```

Code Example
===============

```java
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
 
        final Firebase fb = new Firebase("https://your-firebase-url.firebaseio.com/");
  
        this.robotValues = new RobotValues(fb, 1.5);
        this.robotValues
                .add(new ValueWriter("sensor.color.a",
                        new ValueSource() {
                            @Override
                            public Object getValue() {
                                return System.currentTimeMillis();
                            }}))
                .add(new ValueListener("sensor.color.a",
                        new ListenerAction() {
                            @Override
                            public void onValueChanged(Object val) {
                                telemetry.addData("value1", "The value is: " + val);
                            }
                        }));
 
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
