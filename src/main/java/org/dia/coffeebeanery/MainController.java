package org.dia.coffeebeanery;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/") //메인 페이지
    public String root() {
        return "redirect:/product/list";
    }
}