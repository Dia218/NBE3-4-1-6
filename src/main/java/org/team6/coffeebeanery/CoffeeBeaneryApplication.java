package org.team6.coffeebeanery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;

@EnableJpaAuditing
@SpringBootApplication
@EnableScheduling // @Scheduled 사용하기 위한 애너테이션
public class CoffeeBeaneryApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(CoffeeBeaneryApplication.class, args);
    }
    
    @GetMapping("/") //메인 페이지
    public String root() {
        return "redirect:/product/list";
    }
}
