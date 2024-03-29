package de.jsauer.valhalla.backend.repositories;

import de.jsauer.valhalla.backend.entities.Gear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Gear, Long> {
    List<Gear> findByNameStartsWithIgnoreCase(String name);
}
