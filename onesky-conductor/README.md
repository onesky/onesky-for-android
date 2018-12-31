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

### Testing the plugin

Write test cases inside `src/test` folder. To test the cases, go to `onesky-conductor` folder, then run
```
./gradlew test
```
