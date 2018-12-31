## Developer Instruction

### Packing the plugin

In `onesky-conductor` folder, run
```
./gradlew install
```
A new version of the associated archives will be built in local `./maven` folder

### Consume dependencies from local maven
Adding the local Maven cache as a repository, e.g.
```
repositories {
    mavenLocal()
}
```

Gradle uses the same logic as Maven to identify the location of your local Maven cache. If a local repository location is defined in a settings.xml, this location will be used. The settings.xml in USER_HOME/.m2 takes precedence over the settings.xml in M2_HOME/conf. If no settings.xml is available, Gradle uses the default location USER_HOME/.m2/repository.

For more details please refer to [Gradle Docs](https://docs.gradle.org/current/userguide/repository_types.html#sub:maven_local).

#### 1. Modify the plugin build file
To execute the updated plugin code locally, you should modify the plugin version to avoid confusion with the remote one, such that the content of `onesky-conductor/build.gradle` will look like:
```
group 'app.onesky.android'
version '0.0.2-dev'
...
```

#### 2. Pack the local plugin
In `onesky-conductor` folder, run
```
./gradlew install
```
A new version of the associated archives will be built in local `./maven` folder

#### 3. Copy the local plugin to local cache
```
mkdir ~/.m2/repository/app/onesky/android/onesky-conductor/
cp -r maven/app/onesky/android/onesky-conductor/0.0.2-dev ~/.m2/repository/app/onesky/android/onesky-conductor/
```

#### 4. Update the app dependency
To import local modified plugin, you have to replace the remote onesky-conductor dependency with the local one in your app's top-level build file `build.gradle`, and then modify the dependency version to match the local one. Such that the content of `onesky-helloworld/build.gradle` will look like:
```
buildscript {
    
    repositories {
        google()
        jcenter()
        // maven { url 'https://sdk.onesky.app/maven' }
        mavenLocal()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.1.0'
        classpath 'app.onesky.android:onesky-conductor:0.0.2-dev'

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
...
```

#### 5. Execute the modified plugin
In `onesky-helloworld` folder, run
```
./gradlew localize
```

### Testing the plugin

Write test cases inside `src/test` folder. To test the cases, go to `onesky-conductor` folder, then run
```
./gradlew test
```
