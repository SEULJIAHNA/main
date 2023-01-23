package shareYourFashion.main.controller;

import shareYourFashion.main.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    @Autowired
    private final UserService userService;

    // main page
    @GetMapping(value = {"" , "/"})
    public String index() {

        return "index";

    }



}
