package com.megaease.template.server.dto;

import com.megaease.template.server.domain.PlatformUser;
import lombok.Data;

@Data
public class PlatformUserDto {
    private Long id;
    private String username;

    public static PlatformUserDto create(PlatformUser platformUser) {
        PlatformUserDto result = new PlatformUserDto();
        result.setId(platformUser.getId());
        result.setUsername(platformUser.getUsername());
        return result;
    }
}
