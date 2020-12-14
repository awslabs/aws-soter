# Running Soter

Running a single test class can be done like so:

```
brazil-build single-unit-test -DtestClass=com.amazon.soter.examples.random.RandomNumberTest
```

You can also use `-DtestMethods` to run a single test method in the test class.

## Debugging Soter

Debugging can be done by starting brazil with the debugger set to immediately suspend; then, from IntelliJ you 
can connect and remotely attach the debugger.

```
brazil-build single-unit-test -DtestClass=com.amazon.soter.examples.random.RandomNumberTest -Dtests.additional.jvmargs="-Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=10400"
```