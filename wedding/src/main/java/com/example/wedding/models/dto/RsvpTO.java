package com.example.wedding.models.dto;

import com.example.wedding.models.Guest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
public class RsvpTO {

    private Long id;
    private String code;
    private String name;
    private String email;
    private Date expiredDate;
    private boolean hasExpiredRsvp;

    @ToString.Exclude
    @JsonIgnoreProperties({"rsvp","hibernateLazyInitializer", "handler"})
    private List<Guest> guests;
}
