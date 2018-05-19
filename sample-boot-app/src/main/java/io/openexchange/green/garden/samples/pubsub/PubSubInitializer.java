package io.openexchange.green.garden.samples.pubsub;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.PubSubAdmin;
import org.springframework.stereotype.Component;

/**
 * Attempt to create the topic and subscription on startup - needed for emulator only-  but will work with the real service too
 */
@Component
public class PubSubInitializer implements InitializingBean {

    private static final Log log = LogFactory.getLog(PubSubInitializer.class);

    @Value("${pubsub.subscription}")
    private String subscriptionName;
    @Value("${pubsub.topic}")
    private String topicName;

    @Autowired
    PubSubAdmin pubSubAdmin;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            pubSubAdmin.createTopic("testTopic");
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        try {
            pubSubAdmin.createSubscription(subscriptionName, topicName);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }
}
