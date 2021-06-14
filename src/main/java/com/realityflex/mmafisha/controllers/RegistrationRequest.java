package com.realityflex.mmafisha.controllers;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegistrationRequest {
    @NotEmpty
    String login;
    @NotEmpty
    String password;
}
