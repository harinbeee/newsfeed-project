package com.example.newsfeed.users.controller;

import com.example.newsfeed.users.serivce.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
}
