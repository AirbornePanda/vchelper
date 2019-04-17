package de.jsauer.valhalla.backend.repositories;

import de.jsauer.valhalla.backend.entities.Gear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GearRepository extends JpaRepository<Gear, Long> {
    Gear findOneByValkypediaId(String valkypediaId);
}
