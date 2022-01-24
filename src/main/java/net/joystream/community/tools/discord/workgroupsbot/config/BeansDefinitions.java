package net.joystream.community.tools.discord.workgroupsbot.config;

import java.net.URI;
import net.joystream.community.tools.discord.workgroupsbot.beans.JoystreamWebsocketClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeansDefinitions {

    @Bean
    public JoystreamWebsocketClient wsProvider(ApplicationEventPublisher eventPublisher, @Value("${joystream.rpc.url}") String rpcUrl) {
        var wsClient = new JoystreamWebsocketClient(eventPublisher, URI.create("ws://" + rpcUrl));
        wsClient.connect();
        return wsClient;
    }

    @Bean
    public WebClient webClient(@Value("${joystream.rpc.url}") String rpcUrl) {
        return WebClient.builder()
            .defaultHeader("Content-Type", "application/json")
            .baseUrl("http://" + rpcUrl)
            .build();
    }
}
