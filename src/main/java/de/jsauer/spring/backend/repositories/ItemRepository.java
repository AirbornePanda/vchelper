package de.jsauer.spring.backend.repositories;

import de.jsauer.spring.backend.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByNameStartsWithIgnoreCase(String name);
}
