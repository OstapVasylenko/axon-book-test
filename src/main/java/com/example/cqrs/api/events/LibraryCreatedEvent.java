package com.example.cqrs.api.events;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
public class LibraryCreatedEvent {

	@TargetAggregateIdentifier
	private final UUID libraryId;
	private final String name;

}
