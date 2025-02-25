package com.github.heisdanielade.pamietampsa.config;

import com.github.heisdanielade.pamietampsa.entity.AppUser;
import com.github.heisdanielade.pamietampsa.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppUserConfig {
    @Bean
    CommandLineRunner commandLineRunner(AppUserRepository repository){
        return _ -> {
           AppUser daniel = new AppUser(

            );

            repository.saveAll(
                    List.of(
//                            daniel,
//                            samuel
                    )
            );
        };
    }
}
