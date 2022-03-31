package com.example.cqrs.api.commands;

import lombok.Data;

@Data
public class CreateLibraryCommand {
	private final String name;
}
