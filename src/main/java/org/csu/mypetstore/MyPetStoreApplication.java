package org.csu.mypetstore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("org.csu.mypetstore.persistence")
public class MyPetStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyPetStoreApplication.class, args);
    }

}
