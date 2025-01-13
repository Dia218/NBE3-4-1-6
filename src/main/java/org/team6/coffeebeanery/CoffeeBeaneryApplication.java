package org.team6.coffeebeanery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class CoffeeBeaneryApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(CoffeeBeaneryApplication.class, args);
    }
    
    @GetMapping("/") //메인 페이지
    public String root() {
        return "redirect:/product/list";
    }
}
