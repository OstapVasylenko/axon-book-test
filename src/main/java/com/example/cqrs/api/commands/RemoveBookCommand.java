package com.example.cqrs.api.commands;

import lombok.Data;

import java.util.UUID;

@Data
public class RemoveBookCommand {
    private UUID libraryID;
    private Integer bookId;
}
