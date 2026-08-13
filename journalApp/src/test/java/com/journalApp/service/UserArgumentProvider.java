package com.journalApp.service;

import com.journalApp.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UserArgumentProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(
            ExtensionContext extensionContext) {

        return Stream.of(
                Arguments.of(
                        User.builder()
                                .userName("shyam")
                                .password("shyam")
                                .build()
                ),
                Arguments.of(
                        User.builder()
                                .userName("suraj")
                                .password("suraj")
                                .build()
                )
        );
    }
}