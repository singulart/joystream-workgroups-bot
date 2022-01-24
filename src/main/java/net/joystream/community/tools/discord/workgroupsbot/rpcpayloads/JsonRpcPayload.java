package net.joystream.community.tools.discord.workgroupsbot.rpcpayloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

//@JsonTypeInfo(
//    use = JsonTypeInfo.Id.NAME,
//    property = "t")
@JsonSubTypes({
    @Type(value = GetBlockEvents.class, name = "GetBlockEvents"),
    @Type(value = SubscribeNewHeads.class, name = "SubscribeNewHeads")
})
@JsonPropertyOrder({"id", "jsonrpc", "method", "params" })
public abstract class JsonRpcPayload {

    @JsonProperty
    private final String jsonrpc = "2.0";
    private static Long id = 0L;
    @JsonProperty
    private final String method;
    @JsonProperty
    private final String[] params;
//2022-01-25 00:44:59.803  INFO 69546 --- [ctReadThread-22] n.j.c.t.d.w.b.JoystreamWebsocketClient   : Subscribing to new blocks... {"id":1,"jsonrpc":"2.0","method":"chain_subscribeNewHeads","params":[]}
    protected JsonRpcPayload(String method, String[] params) {
        this.params = params;
        JsonRpcPayload.id++;
        this.method = method;
    }

    public Long getId() {
        return id;
    }
}
