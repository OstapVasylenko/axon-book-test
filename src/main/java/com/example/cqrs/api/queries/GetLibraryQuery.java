package com.example.cqrs.api.queries;

import lombok.Data;

import java.util.UUID;

@Data
public class GetLibraryQuery {
	private final UUID libraryId;
}
