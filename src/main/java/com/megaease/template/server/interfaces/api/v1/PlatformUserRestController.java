package com.megaease.template.server.interfaces.api.v1;

import com.github.pagehelper.PageInfo;
import com.megaease.template.server.constants.ApiPrefix;
import com.megaease.template.server.convert.PlatformUserConvert;
import com.megaease.template.server.domain.PlatformUser;
import com.megaease.template.server.dto.CreatePlatformUserCommand;
import com.megaease.template.server.dto.PlatformUserDto;
import com.megaease.template.server.dto.PlatformUserQueryModel;
import com.megaease.template.server.security.utils.JwtUtils;
import com.megaease.template.server.security.utils.SessionHelper;
import com.megaease.template.server.service.PlatformUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = ApiPrefix.PLATFORM_PREFIX)
public class PlatformUserRestController {

    @Autowired
    private PlatformUserService platformUserService;
    @Autowired
    private PlatformUserConvert platformUserConvert;
    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/platform-users/page")
    @PreAuthorize("hasPermission('platform','user.page')")
    public PageInfo<PlatformUserDto> page(Pageable pageable, PlatformUserQueryModel model) {
        return platformUserConvert.convertPageToDto(platformUserService.page(pageable, model));
    }

    @ApiOperation("create user")
    @PostMapping("/platform-users")
    public PlatformUserDto create(@RequestBody CreatePlatformUserCommand command) {
        PlatformUser platformUser = platformUserService.create(command);
        return PlatformUserDto.create(platformUser);
    }

    @ApiOperation("get my information")
    @GetMapping("/platform-users/my-user-info")
    public PlatformUserDto getSelfInfo() {
        PlatformUser user = platformUserService.findByIdNotNull(SessionHelper.getCurrentLoginUserId());
        return PlatformUserDto.create(user);
    }
}
