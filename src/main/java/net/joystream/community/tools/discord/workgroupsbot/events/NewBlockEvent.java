package net.joystream.community.tools.discord.workgroupsbot.events;

import org.springframework.context.ApplicationEvent;

public class NewBlockEvent extends ApplicationEvent {

    private final Long block;

    public NewBlockEvent(Object source, Long block) {
        super(source);
        this.block = block;
    }

    public Long getBlock() {
        return block;
    }
}
