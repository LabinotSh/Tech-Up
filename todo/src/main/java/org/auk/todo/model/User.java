package org.auk.todo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String username;
    private String password;
    private int active;
    @Temporal(TemporalType.DATE)
    Date joinedAt;

    @OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL)
    private List<Todo> todoList;

    @ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
	    inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

    @PrePersist
    protected void onCreate() {
        joinedAt = new Date();
    }
}
