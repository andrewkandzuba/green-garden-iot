package io.openexchange.green.garden.samples;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class SampleController {

    @RequestMapping("/")
    public String hello() throws UnknownHostException {
        return "Hello !!! " + InetAddress.getLocalHost().getHostName() ;
    }
}
