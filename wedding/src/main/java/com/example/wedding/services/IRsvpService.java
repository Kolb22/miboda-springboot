package com.example.wedding.services;

import com.example.wedding.models.Rsvp;

public interface IRsvpService {

    Rsvp create(Rsvp rsvp);

    Rsvp findByName(String name);
}
