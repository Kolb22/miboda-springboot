package com.example.wedding.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "rsvp")
@Data
public class Rsvp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique = true)
    private String code;

    private String name;

    @Column(unique = true)
    private String email;

    private Date expiredDate;

    @ToString.Exclude
    @JsonIgnoreProperties({"rsvp","hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "rsvp", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Guest> guests;
}