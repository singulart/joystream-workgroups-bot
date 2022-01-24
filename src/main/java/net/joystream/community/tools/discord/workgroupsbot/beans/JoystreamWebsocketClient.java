package net.joystream.community.tools.discord.workgroupsbot.beans;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import java.net.URI;
import net.joystream.community.tools.discord.workgroupsbot.events.NewBlockEvent;
import net.joystream.community.tools.discord.workgroupsbot.rpcpayloads.SubscribeNewHeads;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.context.ApplicationEventPublisher;

public class JoystreamWebsocketClient extends WebSocketClient {

    private final Logger logger = LogManager.getLogger(JoystreamWebsocketClient.class);

    private final ObjectMapper mapper = new ObjectMapper();

    private final ApplicationEventPublisher eventPublisher;

    public JoystreamWebsocketClient(ApplicationEventPublisher eventPublisher, URI serverUri) {
        super(serverUri);
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {

        logger.info("Connected to {}", this.getURI());
        try {
            String payload = mapper.writeValueAsString(new SubscribeNewHeads());
            logger.info("Subscribing to new blocks... {}", payload);
            send(payload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(String payload) {
        var blockNumberHex = "";
        try {
            blockNumberHex = JsonPath.read(payload, "$.params.result.number");
            long blockNumber = Long.parseLong(blockNumberHex.substring(2), 16);
            logger.info("Block #{}", blockNumber);
            this.eventPublisher.publishEvent(new NewBlockEvent(this, blockNumber));
        } catch (PathNotFoundException e) {
            logger.info("Not a new block payload: {}", payload);
        } catch (NumberFormatException e) {
            logger.info("Cannot interpret {} as number", blockNumberHex);
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        logger.info("onClose {}", s);
    }

    @Override
    public void onError(Exception e) {
        logger.info("onError", e);
    }
}
