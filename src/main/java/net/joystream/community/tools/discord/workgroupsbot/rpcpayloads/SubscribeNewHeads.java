package net.joystream.community.tools.discord.workgroupsbot.rpcpayloads;

public class SubscribeNewHeads extends JsonRpcPayload {

    public SubscribeNewHeads() {
        super("chain_subscribeNewHeads", new String[0]);
    }
}
