package com.megaease.template.server.dto;

import com.megaease.template.server.domain.PlatformUser;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Size;

@Data
public class PlatformUserQueryModel {
    @Size(max = 64)
    private String username;

    public PlatformUser convertToUser() {
        PlatformUser platformUser = new PlatformUser();
        platformUser.setUsername(StringUtils.isEmpty(username) ? null : "%" + username + "%");
        return platformUser;
    }
}
