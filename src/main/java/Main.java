import kafka.server.KafkaConfig$;
import net.mguenther.kafka.junit.EmbeddedKafkaCluster;
import net.mguenther.kafka.junit.EmbeddedKafkaClusterConfig;
import net.mguenther.kafka.junit.EmbeddedKafkaConfig;

import static net.mguenther.kafka.junit.EmbeddedKafkaCluster.provisionWith;
public class Main {
    public static void main(String[] args) {
        EmbeddedKafkaCluster kafka = provisionWith(EmbeddedKafkaClusterConfig.create()
                .provisionWith(EmbeddedKafkaConfig.create()
                        .withNumberOfBrokers(1)
                        .with(KafkaConfig$.MODULE$.PortProp(), "9092")
                        .build())
                .build());
        kafka.start();
    }
}
