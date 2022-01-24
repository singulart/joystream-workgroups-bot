package net.joystream.community.tools.discord.workgroupsbot.beans;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import java.net.URI;
import java.util.HashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class SimpleWebsocketClient extends WebSocketClient {

    private final Logger logger = LogManager.getLogger(SimpleWebsocketClient.class);

    private final ObjectMapper mapper = new ObjectMapper();

    public SimpleWebsocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {

        logger.info("onOpen");

        var subscribeNewHeadCommand = new HashMap<String, Object>();
        subscribeNewHeadCommand.put("id", 1);
        subscribeNewHeadCommand.put("jsonrpc", "2.0");
        subscribeNewHeadCommand.put("method", "chain_subscribeNewHeads");
        subscribeNewHeadCommand.put("params", new String[0]);

        try {
            this.send(mapper.writeValueAsString(subscribeNewHeadCommand));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(String payload) {
        String blockNumberHex = JsonPath.read(payload, "$.params.result.number");
        logger.info("Block #{}", Long.parseLong(blockNumberHex.substring(2), 16));
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
