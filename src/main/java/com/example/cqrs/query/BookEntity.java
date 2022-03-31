package com.example.cqrs.query;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class BookEntity {

    @Id
    private Integer bookId;

    @Column
    private UUID libraryId;

    @Column
    private String title;

    @Column
    private String description;

}
