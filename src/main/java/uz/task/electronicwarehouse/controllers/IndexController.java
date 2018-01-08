package uz.task.electronicwarehouse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Boburmirzo on 06/01/18.
 */
@Controller
public class IndexController {

    @GetMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(name = "error", required = false) String error, Model model) {
        model.addAttribute("error", error != null);

        return "login";
    }

    @GetMapping("/access_denied")
    public String denied() {
        return "access_denied";
    }

    @GetMapping("/logout_success")
    public String logout() {
        return "logout_success";
    }

}
