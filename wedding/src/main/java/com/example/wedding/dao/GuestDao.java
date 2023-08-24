package com.example.wedding.dao;

import com.example.wedding.models.Guest;
import org.springframework.data.repository.CrudRepository;

public interface GuestDao extends CrudRepository<Guest, Long> {
    Guest findGuestByName(String name);
}