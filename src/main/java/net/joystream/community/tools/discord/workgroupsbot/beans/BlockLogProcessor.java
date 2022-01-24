package net.joystream.community.tools.discord.workgroupsbot.beans;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.joystream.community.tools.discord.workgroupsbot.events.NewBlockEvent;
import net.joystream.community.tools.discord.workgroupsbot.rpcpayloads.GetBlockEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class BlockLogProcessor {

    private final ObjectMapper mapper = new ObjectMapper();
    private WebClient webClient;

    @Autowired
    public BlockLogProcessor(WebClient client) {
        this.webClient = client;
    }

    @EventListener
    public void handleNewBlockEvent(NewBlockEvent newBlockEvent) throws JsonProcessingException {
        webClient.post()
            .bodyValue(mapper.writeValueAsString(new GetBlockEvents(newBlockEvent.getBlock())))
            .retrieve();
    }
}
