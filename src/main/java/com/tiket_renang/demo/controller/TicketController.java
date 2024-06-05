package com.tiket_renang.demo.controller;

import com.tiket_renang.demo.dto.TicketReq;
import com.tiket_renang.demo.dto.TicketResp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    private AtomicInteger ticketAvailable = new AtomicInteger(1000);

    TicketResp ticket = new TicketResp();
    TicketReq requestTicket = new TicketReq();

    @GetMapping
    public ResponseEntity<TicketResp> ticketCheck(){
        TicketResp ticket = new TicketResp();
        ticket.setTicket_remains(ticketAvailable.get());
        return ResponseEntity.ok(ticket);
    }

    @PostMapping
    private ResponseEntity<String> buyTicket(@RequestBody TicketReq req) {
        int previousTicketCount = ticketAvailable.get();
        int requestedTicketCount = req.getTicketAmount();

        if(requestedTicketCount > 0 && previousTicketCount >= requestedTicketCount) {
            int newTicketCount = previousTicketCount - requestedTicketCount;
            ticketAvailable.set(newTicketCount);
            return ResponseEntity.ok("Berhasil membeli ticket: " + requestedTicketCount + " ticket. Sisa tiket: " + newTicketCount);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Jumlah tiket yang diminta melebihi jumlah yang tersedia");
        }
    }
}