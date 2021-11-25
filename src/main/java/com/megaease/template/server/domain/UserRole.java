package com.megaease.template.server.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "t_user_role")
public class UserRole {
    private Long id;
    private Long userId;
    private Long roleId;
    private String userRealm;
    private Date createDate;
    private Date updateDate;
}
