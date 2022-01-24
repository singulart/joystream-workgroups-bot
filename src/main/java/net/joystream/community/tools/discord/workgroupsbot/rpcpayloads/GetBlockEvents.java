package net.joystream.community.tools.discord.workgroupsbot.rpcpayloads;

public class GetBlockEvents extends JsonRpcPayload {

    public GetBlockEvents(Long block) {
        super("system_events", new String[]{block + ""}); //todo convert to hex?
    }
}
