package com.insurance.application.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Table(name = "authorities")
@Entity
public class UserRole {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "authority")
    String authority;

    @JsonIgnore
    @OneToMany(mappedBy = "userRole")
    Set<UserInfo> usersSet;

    public UserRole() {
    }


    public int getId() {
        return id;
    }

    public String getAuthority() {
        return authority;
    }

    public Set<UserInfo> getUsersSet() {
        return usersSet;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public void setUsersSet(Set<UserInfo> usersSet) {
        this.usersSet = usersSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return id == userRole.id &&
                authority.equals(userRole.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authority);
    }
}
