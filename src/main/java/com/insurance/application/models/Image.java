package com.insurance.application.models;

import javax.persistence.*;

@Entity
@Table(name = "image_info")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    @OneToOne
    @JoinColumn(name = "policy_id")
    private Policy policy;

    public Image() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }
}
