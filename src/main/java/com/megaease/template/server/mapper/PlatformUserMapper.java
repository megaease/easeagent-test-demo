package com.megaease.template.server.mapper;

import com.megaease.template.common.dao.MySqlCommonMapper;
import com.megaease.template.server.domain.PlatformUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Mapper
public interface PlatformUserMapper extends MySqlCommonMapper<PlatformUser> {
    /**
     * use dynamic sql to find users
     *
     * @param platformUser user info
     * @return
     */
    List<PlatformUser> selectByDynamic(@Param("platformUser") PlatformUser platformUser);

    /**
     * find a user by its username
     *
     * @param username
     * @return
     */
    default PlatformUser selectOneByUsername(String username) {
        Example example = new Example(PlatformUser.class);
        example.createCriteria().andEqualTo("username", username);
        return this.selectOneByExample(example);
    }
}
