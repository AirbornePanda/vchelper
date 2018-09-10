package de.jsauer.spring.backend.repositories;

import de.jsauer.spring.backend.entities.LimitBurst;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LimitBurstRepository extends JpaRepository<LimitBurst, Long> {
    LimitBurst findOneByGarmId(final String garmId);

    LimitBurst findOneByNameIgnoreCaseContaining(final String name);
}
