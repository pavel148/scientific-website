package com.example.uni_dubna.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import javax.persistence.*;


@Entity
@Table(name = "scientific_users")
public class ScientificUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Size(min=2, max = 150, message = "Имя должно быть от 2 до 150 символов длиной")
    @Column(name="username")
    @NotEmpty
    private String username;

    @Column(name="password")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER) // Изменено на EAGER
    @JoinColumn(name = "role_id") // имя внешнего ключа
    private Role role;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    @Override
    public String toString() {
        return "ScientificUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }


}