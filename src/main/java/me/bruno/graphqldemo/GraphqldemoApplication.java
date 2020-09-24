package me.bruno.graphqldemo;

import me.bruno.graphqldemo.entity.Food;
import me.bruno.graphqldemo.service.FoodService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class GraphqldemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphqldemoApplication.class, args);
	}

	@Bean
    ApplicationRunner runner(FoodService service) {
	    return args -> {
            Stream.of("Pizza", "Spam", "Eggs", "Avocado")
                    .forEach(name -> service.saveFood(Food.builder()
                            .name(name)
                            .build()));

            service.getFoods().forEach(System.out::print);
        };
    }
}
