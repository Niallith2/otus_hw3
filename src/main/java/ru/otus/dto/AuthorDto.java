package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorDto {
    public long id;
    public long idBook;
    public String firstName;
    public String lastName;
}
