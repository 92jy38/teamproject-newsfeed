package com.sparta.newsfeed_project.domain.common.jwt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PasswordEncoderTest {
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        this.passwordEncoder = new PasswordEncoder();
    }

    @Test
    @DisplayName("encoder & matches 통합 테스트")
    void serialTest() {
        String input = "passwordExample123";
        String incorrectInput = "PasswordExample123";

        String output = passwordEncoder.encoder(input);
        String incorrectOutput = passwordEncoder.encoder(incorrectInput);

        boolean result1 = passwordEncoder.matches(input, output);
        boolean result2 = passwordEncoder.matches(incorrectInput, output);

        System.out.println("password: " + input);
        System.out.println();
        System.out.println("input1: " + input);
        System.out.println("output1: " + output);
        System.out.println("result1: " + result1);
        System.out.println();
        System.out.println("input2: " + incorrectInput);
        System.out.println("output2: " + incorrectOutput);
        System.out.println("result2: " + result2);

        Assertions.assertTrue(result1);
        Assertions.assertFalse(result2);
    }
}