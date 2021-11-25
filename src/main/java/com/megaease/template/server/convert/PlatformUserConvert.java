package com.megaease.template.server.convert;

import com.github.pagehelper.PageInfo;
import com.megaease.template.common.utils.PaginationUtil;
import com.megaease.template.server.domain.PlatformUser;
import com.megaease.template.server.dto.PlatformUserDto;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class PlatformUserConvert {
    public PageInfo<PlatformUserDto> convertPageToDto(PageInfo<PlatformUser> pageInfo) {
        return PaginationUtil.buildPageInfo(
                pageInfo.getPageNum(),
                pageInfo.getPageSize(),
                pageInfo.getTotal(),
                pageInfo.getPages(),
                convertListToDto(pageInfo.getList())
        );
    }

    public List<PlatformUserDto> convertListToDto(List<PlatformUser> list) {
        List<PlatformUserDto> result = new LinkedList<>();
        for (PlatformUser user : list) {
            result.add(PlatformUserDto.create(user));
        }
        return result;
    }
}
