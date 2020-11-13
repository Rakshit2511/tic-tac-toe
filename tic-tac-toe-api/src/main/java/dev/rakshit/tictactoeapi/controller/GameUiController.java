package dev.rakshit.tictactoeapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
@RequestMapping
public class GameUiController {

    @GetMapping("/")
    public String startGame() {
        return "redirect:play";
    }

    @GetMapping("/play")
    public String playGame() {
        return "index.html";
    }

}