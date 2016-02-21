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

Maven usage
==============

Add a repository to your root pom.xml:
```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

And add a dependency:
```
<dependency>
    <groupId>com.github.Jacobvs</groupId>
    <artifactId>ftc-firebase-robotics</artifactId>
    <version>1.0</version>
</dependency>
```