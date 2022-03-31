package com.example.cqrs.query;

import com.example.cqrs.api.events.BookCreatedEvent;
import com.example.cqrs.api.events.BookRemovedEvent;
import com.example.cqrs.api.queries.GetBooksQuery;
import com.example.cqrs.dto.BookDto;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class BookRepositoryProjector {

	private final BookRepository bookRepository;

	public BookRepositoryProjector(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@EventHandler
	public void addBook(BookCreatedEvent event) {
		BookEntity book = new BookEntity();
		book.setBookId(event.getBookId());
		book.setLibraryId(event.getLibraryId());
		book.setTitle(event.getTitle());

		bookRepository.save(book);
	}

	@EventHandler
	public void removeBook(BookRemovedEvent event) {
		BookEntity book = new BookEntity();
		book.setBookId(event.getBookId());

		bookRepository.delete(book);
	}

	@QueryHandler
	public List<BookDto> getBooks(GetBooksQuery query) {
		return bookRepository.findById(query.getLibraryId().toString()).stream().map(toBook()).collect(Collectors.toList());
	}

	private Function<BookEntity, BookDto> toBook() {
		return bookEntity -> BookDto.builder().id(bookEntity.getBookId()).title(bookEntity.getTitle()).description(bookEntity.getDescription()).build();
	}
}
