package com.nutmeg.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address extends BaseEntity {

    @Column
    String firstLine;
    @Column
    String secondLine;
    @Column
    String postcode;
    @ManyToMany(mappedBy = "addresses", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<User> users = new ArrayList<>();
}
