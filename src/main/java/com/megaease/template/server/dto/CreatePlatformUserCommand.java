package com.megaease.template.server.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CreatePlatformUserCommand {
    @NotBlank
    @Size(max = 64)
    private String username;
    @NotBlank
    private String password;
}
