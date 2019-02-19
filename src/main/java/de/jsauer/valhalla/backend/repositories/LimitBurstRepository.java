package de.jsauer.valhalla.backend.repositories;

import de.jsauer.valhalla.backend.entities.LimitBurst;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LimitBurstRepository extends JpaRepository<LimitBurst, Long> {
    LimitBurst findOneByGarmId(final String garmId);

    LimitBurst findOneByNameIgnoreCaseContaining(final String name);
}
