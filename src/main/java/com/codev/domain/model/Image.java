package com.codev.domain.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_image")
public class Image {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "file_name")
    private String fileName;

    @Column(length = 5000)
    private String file;

}
