package splitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import splitter.controller.Controller;

@SpringBootApplication
public class Main {

    @Autowired
    private Controller controller;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner runApplication() {
        return (args -> controller.run());
    }
}
