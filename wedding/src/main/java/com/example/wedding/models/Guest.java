package com.example.wedding.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "guest")
@Data
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private boolean hasAccepted;
    
    private Date acceptedDate;

    @ManyToOne
    @JoinColumn(name = "fk_rsvp_id")
    private Rsvp rsvp;

}