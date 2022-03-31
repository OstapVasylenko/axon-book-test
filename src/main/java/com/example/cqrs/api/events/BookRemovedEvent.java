package com.example.cqrs.api.events;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
public class BookRemovedEvent {

    @TargetAggregateIdentifier
    private final UUID libraryID;
    private final Integer bookId;
}
