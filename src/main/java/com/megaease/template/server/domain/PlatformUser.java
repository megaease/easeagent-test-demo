package com.megaease.template.server.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_platform_user")
public class PlatformUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

}
