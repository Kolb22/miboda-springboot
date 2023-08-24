package com.example.wedding.Controllers;

import com.example.wedding.models.Rsvp;
import com.example.wedding.services.IRsvpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class RsvpController {

    @Autowired
    IRsvpService rsvpService;

    @GetMapping("/ok")
    public ResponseEntity<?> ok() {


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/rsvp")
    public ResponseEntity<?> create(@RequestBody Rsvp rsvp) {

        Map<String, Object> errorResponse = new HashMap<>();

        try{
            rsvp = rsvpService.create(rsvp);
        } catch (Exception e){
            errorResponse.put("error", "Something has happened trying to create the rsvp. Please try again later.");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(rsvp, HttpStatus.OK);
    }

    @GetMapping("/rsvp/{name}")
    public ResponseEntity<?> findByName(@PathVariable String name) {

        Map<String, Object> errorResponse = new HashMap<>();
        Rsvp rsvp;

        try{
            rsvp = rsvpService.findByName(name);

            if(rsvp.getId() == null)
                throw new Exception();

        } catch (Exception e){
            errorResponse.put("error", "Something has happened trying to finding the rsvp. Please try again later.");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(rsvp, HttpStatus.OK);
    }
}
