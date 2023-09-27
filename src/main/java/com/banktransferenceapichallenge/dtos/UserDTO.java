package com.banktransferenceapichallenge.dtos;

import java.math.BigDecimal;

import com.banktransferenceapichallenge.domain.user.UserType;


public record UserDTO(String firstName, String lastName, String email, String document, String password, BigDecimal balance, UserType userType) {
}
