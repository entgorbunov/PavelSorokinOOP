package com.spring.command;

public interface Command {
    void execute();
    CommandType getType();
}
