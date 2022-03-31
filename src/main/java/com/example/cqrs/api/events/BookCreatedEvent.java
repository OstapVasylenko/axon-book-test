package com.example.cqrs.api.events;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
public class BookCreatedEvent {

	@TargetAggregateIdentifier
	private final UUID libraryId;
	private final Integer bookId;
	private final String title;
	private final String description;

}
