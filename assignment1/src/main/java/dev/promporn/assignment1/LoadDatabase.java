package dev.promporn.assignment1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    public static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(StoragRepository repository) {
        return args -> {
            log.info("loading "+repository.save(new Storge("Grinders", "Gorya", "outside storage", false, null)));
            log.info("loading "+repository.save(new Storge("Nailers", "Promporn", "toolbox 2nd floor", false, null)));
            log.info("loading"+repository.save(new Storge("paint brush","Gorya","paint cabinet",false,null)));
        };
    }
}