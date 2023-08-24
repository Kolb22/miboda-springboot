package com.example.wedding.services.impl;

import com.example.wedding.dao.GuestDao;
import com.example.wedding.dao.RsvpDao;
import com.example.wedding.models.Guest;
import com.example.wedding.models.Rsvp;
import com.example.wedding.services.IRsvpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RsvpServiceImpl implements IRsvpService {

    @Autowired
    RsvpDao rsvpDao;

    @Autowired
    GuestDao guestDao;

    @Override
    public Rsvp create(Rsvp rsvp) {

        String uniqueID = UUID.randomUUID().toString();

        if(rsvp.getCode() == null)
            rsvp.setCode(uniqueID.substring(0, 16));

        rsvp.setGuests(setRsvpInGuests(rsvp));

        return rsvpDao.save(rsvp);
    }

    @Override
    public Rsvp findByName(String name) {

        Guest guest = guestDao.findGuestByName(name.toUpperCase().trim());
        Rsvp rsvp = rsvpDao.findById(guest.getRsvp().getId()).orElse(new Rsvp());

        return rsvp;
    }

    private List<Guest> setRsvpInGuests(Rsvp rsvp){
        return rsvp.getGuests().stream().map(e -> {
            e.setName(e.getName().toUpperCase().trim());
            e.setRsvp(rsvp);
            return e;
        }).collect(Collectors.toList());
    }
}
