package kz.zhanbolat.springboot.controller;

import kz.zhanbolat.springboot.entity.Ticket;
import kz.zhanbolat.springboot.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/{ticketId}")
    public Ticket getTicket(@PathVariable("ticketId") int ticketId) {
        return ticketRepository.findById(ticketId).orElseThrow(() -> new IllegalStateException("Ticket cannot be found"));
    }

    @PostMapping("/")
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @PutMapping("/")
    public Ticket updateTicket(@RequestBody Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @DeleteMapping("/{ticketId}")
    public Ticket deleteTicket(@PathVariable("ticketId") int ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new IllegalStateException("Ticket cannot be found"));
        ticketRepository.delete(ticket);
        return ticket;
    }
}
