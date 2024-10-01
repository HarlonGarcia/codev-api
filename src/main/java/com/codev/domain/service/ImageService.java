package com.codev.domain.service;

import com.codev.domain.dto.form.ImageDTOForm;
import com.codev.domain.model.Image;
import com.codev.domain.repository.ImageRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class ImageService {

    @Inject
    ImageRepository imageRepository;

    @Transactional
    public Image saveImage(ImageDTOForm imageDTOForm) {
        Image image = new Image(imageDTOForm);
        imageRepository.persist(image);
        return image;
    }

    public Optional<Image> getImageById(Long id) {
        return imageRepository.findByIdOptional(id);
    }

    @Transactional
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
}
