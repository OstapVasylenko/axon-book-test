package com.example.cqrs.api.commands;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
public class CreateBookCommand {

    @TargetAggregateIdentifier
    private final UUID libraryID;
    private final Integer bookId;
    private final String title;
    private final String description;

}
