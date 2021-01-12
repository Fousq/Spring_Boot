package kz.zhanbolat.springboot.repository;

import kz.zhanbolat.springboot.entity.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Integer> {
}
