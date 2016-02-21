# ftc-firebase-robotics

[![Release](https://jitpack.io/v/Jacobvs/ftc-firebase-robotics.svg)](https://jitpack.io/#Jacobvs/ftc-firebase-robotics)

Gradle usage
==============

Add a repository to your root build.gradle:
```
allprojects {
    repositories {
        ...
		maven { url "https://jitpack.io" }
	}
}
```

And add a dependency:
```
dependencies {
    ...
    compile 'com.github.Jacobvs:ftc-firebase-robotics:1.0'
}
```

Code Example
===============

```
public class FirebaseTest extends OpMode {
 
    RobotValues robotValues = null;
 
    public FirebaseTest() {
    }
 
    @Override
    public void init() {
 
        final Firebase fb = new Firebase("https://9523-2015.firebaseio.com/");
  
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
                        }))
                .add(new ValueListener("sensor.color.b",
                        new ListenerAction() {
                            @Override
                            public void onValueChanged(Object val) {
                                telemetry.addData("value2", "The value is: " + val);
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