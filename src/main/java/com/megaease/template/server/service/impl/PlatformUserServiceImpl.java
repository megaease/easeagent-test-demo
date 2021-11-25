package com.megaease.template.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.megaease.template.common.enums.BusinessError;
import com.megaease.template.common.exception.BusinessException;
import com.megaease.template.common.exception.ClientParamException;
import com.megaease.template.common.exception.DataDuplicationException;
import com.megaease.template.common.validate.Validation;
import com.megaease.template.server.domain.PlatformUser;
import com.megaease.template.server.dto.CreatePlatformUserCommand;
import com.megaease.template.server.dto.PlatformUserQueryModel;
import com.megaease.template.server.mapper.PlatformUserMapper;
import com.megaease.template.server.service.PlatformUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Optional;

@Service
@Validation
public class PlatformUserServiceImpl implements PlatformUserService {

    @Autowired
    private PlatformUserMapper platformUserMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public PageInfo<PlatformUser> page(Pageable pageable, PlatformUserQueryModel model) {
        return PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize())
                .doSelectPageInfo(() -> platformUserMapper.selectByDynamic(model.convertToUser()));
    }

    @Override
    public PlatformUser findByUsernameNotNull(String username) {
        return Optional.ofNullable(platformUserMapper.selectOneByUsername(username))
                .orElseThrow(() -> new ClientParamException("error.platform_user.no_exist"));
    }

    @Override
    public PlatformUser findByIdNotNull(final long userId) {
        return Optional.ofNullable(platformUserMapper.selectByPrimaryKey(userId))
                .orElseThrow(() -> new ClientParamException("error.platform_user.no_exist"));
    }

    @Override
    public PlatformUser create(@Valid CreatePlatformUserCommand command) {
        PlatformUser platformUser = new PlatformUser();
        if (platformUserMapper.selectOneByUsername(platformUser.getUsername()) != null) {
            throw new DataDuplicationException("error.platform.username.exist");
        }
        platformUser.setUsername(command.getUsername());
        platformUser.setPassword(passwordEncoder.encode(command.getPassword()));
        if (platformUserMapper.insertSelective(platformUser) == 0) {
            throw new BusinessException("error.platform.create", BusinessError.GENERAL_CREATE_ERR);
        }
        return platformUser;
    }
}
