<<<<<<< HEAD
package com.codev.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tb_user_solution")
public class UserSolution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "solution_id")
    private Solution solution;

    @Column(name = "solution_like")
    private boolean like;

}
=======
package com.codev.domain.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tb_user_solution")
public class UserSolution extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "solution_id")
    private Solution solution;

    @Column(name = "solution_like")
    private boolean like;

}
>>>>>>> 0621218cc0a887b73c4ea36637e1b94362cec9f6
