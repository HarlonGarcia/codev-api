package com.codev.domain.model;

import com.codev.domain.dto.form.ImageDTOForm;
import com.codev.domain.dto.form.UserDTOForm;
import com.codev.utils.GlobalConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table(name = "tb_user")
public class User extends PanacheEntityBase {

    @Id
    @GeneratedValue
    public UUID id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(unique = true, length = 65, nullable = false)
    private String email;

    @Column(nullable = false, length = 45)
    private String password;

    @Column(nullable = false)
    private boolean active;

    @Column(name = "github_url", length = 150)
    private String githubUrl;

    @Column(name = "additional_url", length = 150)
    private String additionalUrl;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_user_label",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id"))
    private Set<Label> labels;

    @ManyToMany
    @JoinTable(name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "tb_follow_user",
        joinColumns = @JoinColumn(name = "followed_id"),
        inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    @JsonIgnore
    private Set<User> usersFollowed;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "image_id")
    private Image image;

    public User(UserDTOForm userDTOForm) {
        this.id = UUID.randomUUID();
        this.name = userDTOForm.getName();
        this.email = userDTOForm.getEmail();
        this.password = userDTOForm.getPassword();
        this.active = GlobalConstants.ACTIVE;
        this.githubUrl = userDTOForm.getGithubUrl();
        this.additionalUrl = userDTOForm.getAdditionalUrl();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.roles = new ArrayList<>();

        Image imageDTO = null;

        if (null != userDTOForm.getImage()){
            imageDTO = new Image(userDTOForm.getImage());
        }

        this.image = imageDTO;
    }

    public void setImage(ImageDTOForm image) {
        this.image = new Image(image);
    }

    public User(){}

}