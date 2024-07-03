package com.m44rk0.fipe.exericiotabelafipe;

import com.m44rk0.fipe.exericiotabelafipe.main.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExericioTabelaFipeApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ExericioTabelaFipeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Main main = new Main();
        main.exibeMenu();
    }
}
