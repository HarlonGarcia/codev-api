package com.codev.domain.model;

import com.codev.domain.dto.form.ImageDTOForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "tb_image")
@AllArgsConstructor
@Data
public class Image {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "file_name")
    private String fileName;

    @Column(length = 7000000)
    private String file;

    public Image(ImageDTOForm imageDTOForm) {
        this.fileName = imageDTOForm.getFileName();
        this.file = imageDTOForm.getFile();
    }

    public Image() {}

    public Image(String file, String fileName) {
        this.file = file;
        this.fileName = fileName;
    }
}
