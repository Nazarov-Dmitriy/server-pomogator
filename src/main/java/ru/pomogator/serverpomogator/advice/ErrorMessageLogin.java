package ru.pomogator.serverpomogator.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@AllArgsConstructor
@ToString
public class ErrorMessageLogin {
    private UUID id;
    private String[] login;
    private String[] password;
}
