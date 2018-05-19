package io.openexchange.green.garden.samples.web;

import io.openexchange.green.garden.samples.pubsub.PubSubConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class SampleController {
    @Autowired
    private PubSubConfiguration.PubsubOutboundGateway messagingGateway;

    @RequestMapping("/")
    public String hello() throws UnknownHostException {
        return "Hello !!!!! " + InetAddress.getLocalHost().getHostName();
    }

    @RequestMapping("/pubsub")
    public String pubsub() throws UnknownHostException {
        String message = "Hello !!!!! " + InetAddress.getLocalHost().getHostName();
        messagingGateway.sendToPubsub(message);
        return message;
    }

//    @PostMapping("/publishMessage")
//    public RedirectView publishMessage(@RequestParam("message") String message) {
//        messagingGateway.sendToPubsub(message);
//        return new RedirectView("/");
//    }
}
