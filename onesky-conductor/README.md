## Developer Instruction

### Testing the plugin

Write test cases inside `src/test` folder. To test the cases, go to `onesky-conductor` folder, then run
```
./gradlew test
```

### Packing the plugin

In `onesky-conductor` folder, run
```
./gradlew install
```
A new version of the associated archives will be built in local `./maven` folder
