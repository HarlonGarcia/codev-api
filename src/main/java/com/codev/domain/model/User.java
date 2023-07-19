package com.codev.domain.model;

import com.codev.domain.dto.form.UserDTOForm;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "tb_user")
public class User extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column(name = "github_url")
    private String githubUrl;

    @Column(name = "additional_url")
    private String additionalUrl;

    @ManyToMany
    @JoinTable(name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User(UserDTOForm userDTOForm) {
        this.id = userDTOForm.getId();
        this.name = userDTOForm.getName();
        this.email = userDTOForm.getEmail();
        this.password = userDTOForm.getPassword();
        this.githubUrl = userDTOForm.getGithubUrl();
        this.additionalUrl = userDTOForm.getAdditionalUrl();
    }

    public User(){}

}