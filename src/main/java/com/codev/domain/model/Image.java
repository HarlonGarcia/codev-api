package com.codev.domain.model;

import com.codev.domain.dto.form.ImageDTOForm;
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

    @Column(length = 500000)
    private String file;

    public Image(ImageDTOForm imageDTOForm) {
        this.fileName = imageDTOForm.getFileName();
        this.file = imageDTOForm.getFile();
    }

    public Image() {}

}
