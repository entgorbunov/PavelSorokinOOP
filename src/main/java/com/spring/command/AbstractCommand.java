package com.spring.command;


public abstract class AbstractCommand implements Command {
    protected final CommandType type;

    protected AbstractCommand(CommandType type) {
        this.type = type;
    }

    @Override
    public CommandType getType() {
        return type;
    }
}
