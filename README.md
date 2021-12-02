# easeagent-test-demo

Test demos for easeagent, each module can be compiled and run independently to test a specific plugin or feature

## Develop

Add the following JVM options to debug options, enable dump class files which was modified by easeagent to the specified directory.
Replace all **/path/to/.../** to actual local path.

```
 -javaagent:/path/to/easeagent/build/target/easeagent-dep.jar=/path/to/easeagent/build/src/main/resources/agent.properties 
    -Deaseagent.server.port=9901
    -Deaseagent.middleware.update=true
    -Dnet.bytebuddy.dump=/path/to/ease-test-demo/tmp

```

### ENV
| software | version |
| -------- | ------- |
| jdk      | 8       |

