package com.example.cqrs.aggregate;

import com.example.cqrs.api.commands.CreateBookCommand;
import com.example.cqrs.api.commands.CreateLibraryCommand;
import com.example.cqrs.api.commands.RemoveBookCommand;
import com.example.cqrs.api.events.BookCreatedEvent;
import com.example.cqrs.api.events.LibraryCreatedEvent;
import com.example.cqrs.api.events.BookRemovedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Aggregate(repository = "repositoryForLibrary")
public class Library {

	@AggregateIdentifier
	private UUID libraryID;

	private String name;

	private List<Integer> bookIds;

	protected Library() {}

	@CommandHandler
	public Library(CreateLibraryCommand command) {
		UUID aggregateId = UUID.randomUUID();
		AggregateLifecycle.apply(new LibraryCreatedEvent(aggregateId, command.getName()));
	}

	@CommandHandler
	public void handle(CreateBookCommand command) {
		AggregateLifecycle.apply(new BookCreatedEvent(libraryID, command.getBookId(), command.getTitle(), command.getDescription()));
	}

	@CommandHandler
	public void handle(RemoveBookCommand command) {

		Integer bookId = command.getBookId();

		if(!bookIds.contains(bookId)) {
			throw new RuntimeException("Book doesn't exist in this library");
		}

		AggregateLifecycle.apply(new BookRemovedEvent(libraryID, command.getBookId()));

	}

	@EventSourcingHandler
	public void on(LibraryCreatedEvent event) {
		libraryID = event.getLibraryId();
		name = event.getName();
		bookIds = new ArrayList<>();
	}

	@EventSourcingHandler
	public void on(BookCreatedEvent event) {
		bookIds.add(event.getBookId());
	}

	public void on(BookRemovedEvent event) {
		bookIds.remove(event.getBookId());
	}

}
