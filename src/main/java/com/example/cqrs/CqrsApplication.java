package com.example.cqrs;

import com.example.cqrs.aggregate.Library;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.modelling.command.Repository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CqrsApplication {

    @Bean
    public Repository<Library> repositoryForLibrary(EventStore eventStore) {
        return EventSourcingRepository.builder(Library.class).eventStore(eventStore).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(CqrsApplication.class, args);
    }

}
