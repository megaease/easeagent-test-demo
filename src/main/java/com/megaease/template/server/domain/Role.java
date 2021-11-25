package com.megaease.template.server.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "t_role")
public class Role {
    private Long id;
    private String code;
    private String name;
    private String description;
    private Boolean builtIn;
    private Date createDate;
    private Date updateDate;
}
