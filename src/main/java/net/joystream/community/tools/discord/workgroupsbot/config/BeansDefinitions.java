package net.joystream.community.tools.discord.workgroupsbot.config;

import java.net.URI;
import net.joystream.community.tools.discord.workgroupsbot.beans.JoystreamWebsocketClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansDefinitions {


    @Bean
    public JoystreamWebsocketClient wsProvider(@Value("${joystream.rpc.url}") String rpcUrl) {
        var wsClient = new JoystreamWebsocketClient(URI.create(rpcUrl));
        wsClient.connect();
        return wsClient;
    }
}
