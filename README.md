# Kafka for JUnit

[![Build Status](https://travis-ci.org/mguenther/kafka-junit.svg?branch=master)](https://travis-ci.org/mguenther/kafka-junit.svg) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/net.mguenther.kafka/kafka-junit/badge.svg)](https://maven-badges.herokuapp.com/maven-central/net.mguenther.kafka/kafka-junit)

**Kafka for JUnit** provides JUnit 4.x rule implementations that enables developers to start and stop a complete Kafka cluster comprised of Kafka brokers and distributed Kafka Connect workers from within a JUnit test. It also provides a rich set of convenient accessors to interact with such an embedded Kafka cluster in a lean and non-obtrusive way.

Kafka for JUnit can be used to both whitebox-test individual Kafka-based components of your application or to blackbox-test applications that offer an incoming and/or outgoing Kafka-based interface.

## Using Kafka for JUnit in your tests

Kafka for JUnit provides the necessary infrastructure to exercise your Kafka-based components against an embeddable Kafka cluster. However, Kafka for JUnit got you covered as well if you are simply interested in using the convenient accessors against Kafka clusters that are already present in your infrastructure. Checkout sections "Working with an embedded Kafka cluster" and "Working with an external Kafka cluster" in the [user's guide](https://mguenther.github.io/kafka-junit) for more information.

### Using JUnit 4 rules

```java
public class KafkaTest {

  @Rule
  public EmbeddedKafkaCluster kafka = provisionWith(defaultClusterConfig());

  @Test
  public void shouldWaitForRecordsToBePublished() throws Exception {
    kafka.send(to("test-topic", "a", "b", "c"));
    kafka.observe(on("test-topic", 4));
  }
}
```

The same applies for `@ClassRule`.

### What about JUnit 5?

You can use Kafka for JUnit with JUnit 5 of course. However, with its rule-based implementations, Kafka for JUnit is currently tailored for ease of use with JUnit 4. It implements no JUnit Jupiter extension for JUnit 5. There is an issue for that (cf. link:https://github.com/mguenther/kafka-junit/issues/4[ISSUE-004]), so the development wrt. a JUnit Jupiter extension is planned for a future release. PRs are welcome, though!
As Junit 5 does not support rules, one approach is to start your cluster in a `@BeforeEach` method and it stop in an `@AfterEach` method. 

```java
public class JUnit5KafkaTest {

    private EmbeddedKafkaCluster kafka;

    @BeforeEach
    public void setupKafka() {
        kafka = provisionWith(defaultClusterConfig());
        kafka.start();
    }

    @AfterEach
    public void tearDownKafka() {
        kafka.stop();
    }

    @Test
    public void shouldWaitForRecordsToBePublished() throws Exception {
        kafka.send(to("test-topic", "a", "b", "c"));
        kafka.observe(on("test-topic", 4));
    }

}
```

### Alternative ways

You do not have to use the JUnit 4 rules if you are not comfortable with them. `EmbeddedKafkaCluster` implements the `AutoCloseable` interface, so it is easy to manage it inside your tests yourself.

```java
public class KafkaTest {

  @Test
  public void shouldWaitForRecordsToBePublished() throws Exception {

    try (EmbeddedKafkaCluster kafka = provisionWith(defaultClusterConfig())) {
      kafka.start();
      kafka.send(to("test-topic", "a", "b", "c"));
      kafka.observe(on("test-topic", 3));
    }
  }
}
```

Of course, you can also test against existing clusters using `ExternalKafkaCluster` instead of `EmbeddableKafkaCluster`. See section <<section:external-kafka-cluster, Working with an external Kafka cluster>> for more information.

### Supported versions of Apache Kafka

| Version of Kafka for JUnit | Supports (up to)   |
| -------------------------- | ------------------ |
| 0.1.x                      | Apache Kafka 1.0.0 |
| 0.2.x                      | Apache Kafka 1.0.0 |
| 0.3.x                      | Apache Kafka 1.0.0 |
| 1.0.x                      | Apache Kafka 1.1.1 |
| 2.0.x                      | Apache Kafka 2.0.0 |
| 2.1.x                      | Apache Kafka 2.1.1 |
| 2.2.x                      | Apache Kafka 2.2.1 |
| 2.3.x                      | Apache Kafka 2.3.0 |
| 2.4.x                      | Apache Kafka 2.4.0 |
| 2.5.x                      | Apache Kafka 2.5.1 |
| 2.6.x                      | Apache Kafka 2.6.0 |

## Interacting with the Kafka cluster

See the [comprehensive user's guide](https://mguenther.github.io/kafka-junit) for examples on how to interact with the Kafka cluster from within your JUnit test.

## License

This work is released under the terms of the Apache 2.0 license.

<p>
    <div align="center">
        <div><img src="made-in-darmstadt.jpg"></div>
        <div><a href="https://mguenther.net">mguenther.net</a></div>
    </div>
</p>