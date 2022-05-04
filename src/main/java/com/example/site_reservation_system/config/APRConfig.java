package com.example.site_reservation_system.config;

import org.apache.catalina.core.AprLifecycleListener;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APRConfig {

    //@Bean
    public TomcatServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory container = new TomcatServletWebServerFactory();
        AprLifecycleListener aprLifecycleListener = new AprLifecycleListener();
        aprLifecycleListener.setSSLEngine("off");
        aprLifecycleListener.setUseOpenSSL(false);
        container.setProtocol("org.apache.coyote.http11.Http11AprProtocol");
        container.addContextLifecycleListeners(aprLifecycleListener);
        return container;
    }
}