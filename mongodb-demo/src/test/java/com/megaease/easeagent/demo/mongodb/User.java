package com.megaease.easeagent.demo.mongodb;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class User {
    private Long userId;
    private String name;
    private Date createTime;
}
