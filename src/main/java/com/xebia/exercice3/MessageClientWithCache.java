package com.xebia.exercice3;

import com.xebia.MessageApi;

/**
 * The goal here is to use HystrixCommand cache capabilities.
 */
public class MessageClientWithCache {

    private final MessageApi messageApi;

    public MessageClientWithCache(MessageApi messageApi) {
        this.messageApi = messageApi;
    }

    public String getMessage(String userName) {
        // TODO create and execute an Hystrix command and override run method which calls getMessage with the userName

        // TODO In the Hystrix command override the getCacheKey method with userName as the cache key
        // TODO execute the test
        return null;
    }

}
