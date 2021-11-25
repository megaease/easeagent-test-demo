package com.megaease.template.server.dto;

import com.megaease.template.server.enums.ClientId;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PlatformLoginCommand {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    private ClientId clientId;
}
