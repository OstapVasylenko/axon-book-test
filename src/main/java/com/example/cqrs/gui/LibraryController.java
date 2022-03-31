package com.example.cqrs.gui;

import com.example.cqrs.aggregate.Library;
import com.example.cqrs.api.commands.CreateBookCommand;
import com.example.cqrs.api.commands.CreateLibraryCommand;
import com.example.cqrs.api.queries.GetBooksQuery;
import com.example.cqrs.api.queries.GetLibraryQuery;
import com.example.cqrs.dto.BookDto;
import com.example.cqrs.dto.LibraryDto;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class LibraryController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @Autowired
    public LibraryController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping("/library")
    public String addLibrary(@RequestBody LibraryDto library) {
        commandGateway.send(new CreateLibraryCommand(library.getLibrayName()));
        return "Registered";
    }

    @GetMapping("/library/{library}")
    public Library getLibrary(@PathVariable UUID library) throws InterruptedException, ExecutionException {
        CompletableFuture<Library> future = queryGateway.query(new GetLibraryQuery(library), Library.class);
        return future.get();
    }

    @PostMapping("/library/{library}/book")
    public String addBook(@PathVariable String library, @RequestBody BookDto book) {
        commandGateway.send(new CreateBookCommand(UUID.fromString(library), book.getId(), book.getTitle(), book.getDescription()));
        return "Registered";
    }

    @GetMapping("/library/{library}/book")
    public List<BookDto> addBook(@PathVariable String library) throws InterruptedException, ExecutionException {
        return queryGateway.query(new GetBooksQuery(UUID.fromString(library)), ResponseTypes.multipleInstancesOf(BookDto.class)).get();
    }

}
