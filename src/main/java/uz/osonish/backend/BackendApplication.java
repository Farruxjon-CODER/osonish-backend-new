package uz.osonish.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching; // 1. Import qo'shildi

@SpringBootApplication
@EnableCaching // 2. Keshlashni yoqish uchun ushbu annotatsiya qo'shildi
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}