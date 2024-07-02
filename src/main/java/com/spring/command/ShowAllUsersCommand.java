package com.spring.command;

import com.spring.service.user.impl.UserService;

public class ShowAllUsersCommand extends AbstractCommand {
    private final UserService userService;


    public ShowAllUsersCommand(UserService userService) {
        super(CommandType.SHOW_ALL_USERS);
        this.userService = userService;
    }


    @Override
    public void execute() {
        userService.getAllUsers();
    }

}
