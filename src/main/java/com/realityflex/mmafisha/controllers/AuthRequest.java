package com.realityflex.mmafisha.controllers;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AuthRequest {
    String login;
    String password;

}
