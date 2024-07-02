package com.spring.command;

import com.spring.service.user.impl.UserService;

public class CreateUserCommand extends AbstractCommand {
    private final UserService userService;
    private final String login;

    public CreateUserCommand(UserService userService, String login) {
        super(CommandType.USER_CREATE);
        this.userService = userService;
        this.login = login;
    }

    @Override
    public void execute() {
        userService.createUser(login);
    }

}
