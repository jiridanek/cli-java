# cli-java

[![Build Status](https://travis-ci.org/rh-messaging/cli-java.svg?branch=master)](https://travis-ci.org/rh-messaging/cli-java)
[![Code Coverage](https://codecov.io/gh/rh-messaging/cli-java/branch/master/graph/badge.svg)](https://codecov.io/gh/rh-messaging/cli-java)
[![Coverity Scan Status](https://scan.coverity.com/projects/14128/badge.svg)](https://scan.coverity.com/projects/cli-java)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/6af323f5f8804b659418013a719f3708)](https://www.codacy.com/app/jdanekrh/cli-java?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=rh-messaging/cli-java&amp;utm_campaign=Badge_Grade)
[![Code Climate](https://codeclimate.com/github/rh-messaging/cli-java/badges/gpa.svg)](https://codeclimate.com/github/rh-messaging/cli-java)

cli-java is a collection of commandline messaging clients suitable for interacting with Message Oriented Middleware.

## Getting started

When using IntelliJ IDEA Ultimate Edition, select "Open" (not "Import Project") option to open project and delete OSGi facets in File >> Project Structure >> Project Settings >> Facets.

    mvn clean package  # compile without executing external tests (tests that require broker)
    java -jar cli-qpid-jms/target/cli-qpid-jms-*.jar sender -b amqp://127.0.0.1:5672 -a myQ --log-msgs dict

### Run tests

    mvn test -Ptests
    
    mvn test -Pcoverage,tests  # collect coverage using JaCoCo
    
    mvn clean test -Dmaven.test.failure.ignore
    find -wholename "*/surefire-reports/TEST-*.xml" | zip -j@ test_results.zip
    
### Update dependencies

     mvn versions:display-dependency-updates
     mvn versions:display-plugin-updates

### Update versions

    mvn versions:set -DgenerateBackupPoms=false -DnewVersion=2017.07

## Build docker

Uses `podman`. Needs `sudo` to hook qemu.

```shell
mvn clean
bash build_java.sh
bash build_docker.sh
```

Date-based versioning of image tags, use

```shell
bash build_docker.sh $(date '+%Y-%m-%d')
```

## List of Java clis

| maven module     | messaging library                                                                                      | protocol (JMS version) | notes            |
|------------------|--------------------------------------------------------------------------------------------------------|------------------------|------------------|
| cli-activemq     | [activemq-client](https://deps.dev/maven/org.apache.activemq%3Aactivemq-client)                        | OpenWire (JMS v1.1)    | javax.jms API    |
| cli-activemq-jmx | [artemis-core-client](https://deps.dev/maven/org.apache.activemq%3Aartemis-core-client)                | JMX management         |                  |
| cli-artemis-jms  | [artemis-jms-client](https://deps.dev/maven/org.apache.activemq%3Aartemis-jms-client)                  | Artemis Core           | javax.jms API    |
| cli-paho-java    | [eclipse.paho.client.mqttv3](https://deps.dev/maven/org.eclipse.paho%3Aorg.eclipse.paho.client.mqttv3) | MQTT v3                |                  |
| cli-protonj2     | [protonj2-client](https://deps.dev/maven/org.apache.qpid%3Aprotonj2-client)                            | AMQP 1.0               | "imperative API" |
| cli-qpid-jms     | [qpid-jms-client](https://deps.dev/maven/org.apache.qpid%3Aqpid-jms-client)                            | AMQP 1.0 (JMS v2.0)    | jakarta.jms API  |

## Additional maven modules

| maven module |                                                                             |
|--------------|-----------------------------------------------------------------------------|
| parent       | common maven configuration for child modules, parent of all other modules   |
| bom          | contains dependencyManagement pom section with dependency versions          |
| broker       | embedded artemis-server broker for use in selftests                         |
| tests        | test dependency of cli-* projects, contains shared test code                |
| lib          | shared code that does not depend on JMS                                     |
| jmslib       | shared code that depends on javax.jms API                                   |
| jakartalib   | shared code that depends on jakarta.jmx API                                 |
| cli          | the ClientListener interface for use in client selftests (messages as Maps) |

## Directories

| directory |                                                            |
|-----------|------------------------------------------------------------|
| .github   | GitHub Actions CI configurations, dependabot.yml file      |
| image     | helper scripts for Dockerfile/Containerfile to build image |
| scripts   | helper scripts for CI jobs                                 |


## Related projects

* https://github.com/rh-messaging/cli-netlite
* https://github.com/rh-messaging/cli-rhea
* https://github.com/rh-messaging/cli-proton-python
* https://github.com/rh-messaging/cli-proton-ruby
