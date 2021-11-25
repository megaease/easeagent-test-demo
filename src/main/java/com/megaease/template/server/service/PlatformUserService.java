package com.megaease.template.server.service;

import com.github.pagehelper.PageInfo;
import com.megaease.template.server.domain.PlatformUser;
import com.megaease.template.server.dto.CreatePlatformUserCommand;
import com.megaease.template.server.dto.PlatformUserQueryModel;
import org.springframework.data.domain.Pageable;

public interface PlatformUserService {
    PageInfo<PlatformUser> page(Pageable pageable, PlatformUserQueryModel model);

    PlatformUser findByUsernameNotNull(String username);

    PlatformUser findByIdNotNull(final long userId);

    PlatformUser create(CreatePlatformUserCommand command);
}
