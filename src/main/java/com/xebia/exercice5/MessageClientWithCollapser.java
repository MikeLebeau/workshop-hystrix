package com.xebia.exercice5;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey.Factory;
import com.xebia.MessageApi;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import static java.util.stream.Collectors.toList;
/**
 * The goal here is to use HystrixCollapser feature.
 * Collapser allows to gather multiple calls to MessageApi into a unique one.
 */
public class MessageClientWithCollapser {

    private final MessageApi messageApi;

    private final class Collapser extends HystrixCollapser<Map<String, String>, String, String> {

        private final String userName;

        public Collapser(String userName) {
            this.userName = userName;
        }

        @Override
        public String getRequestArgument() {
            return userName;
        }

        @Override
        public HystrixCommand<Map<String, String>> createCommand(Collection<CollapsedRequest<String, String>> requests) {

            return new HystrixCommand<Map<String, String>>(Factory.asKey("MessageWithCollapser")) {

                @Override
                public Map<String, String> run() throws Exception {

                    List<String> userNames = requests.stream().map(CollapsedRequest::getArgument).collect(toList());

                    return messageApi.getMessage(userNames);
                }

            };

        }

        @Override
        public void mapResponseToRequests(Map<String, String> messages, Collection<CollapsedRequest<String, String>> requests) {

            requests.forEach(request -> {
                String userName = request.getArgument();
                String message = messages.get(userName);
                request.setResponse(message);
            });

        }

    }

    MessageClientWithCollapser(MessageApi messageApi) {
        this.messageApi = messageApi;
    }

    public List<Future<String>> getMessage(List<String> userNames) throws Exception {

        return userNames.stream()
            .map(Collapser::new)
            .map(Collapser::queue)
            .collect(toList());
    }

}
