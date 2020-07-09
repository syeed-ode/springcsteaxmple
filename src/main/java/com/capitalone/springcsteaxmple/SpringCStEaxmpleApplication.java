package com.capitalone.springcsteaxmple;

import com.capitalone.springcsteaxmple.person.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;
import java.util.function.Function;

@SpringBootApplication
public class SpringCStEaxmpleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCStEaxmpleApplication.class, args);
    }

    @Bean
    public Consumer<Person> log() {
        return person -> { System.out.println("Received: " + person);};
    }

    @Bean
    public Function<String, String> upperCase() {
        return value -> {
            System.out.println("Received: " + value);
            return value.toUpperCase();
        };
    }

    @Bean
    public Function<String, String> reverse() {
        return value -> {
            System.out.println("Received: " + value);
            return new StringBuilder(value).reverse().toString();
        };
    }

    @Bean
    public Function<String, String> echo() {
        return value -> value;
    }
}
