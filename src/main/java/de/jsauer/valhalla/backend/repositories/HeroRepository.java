package de.jsauer.valhalla.backend.repositories;

import de.jsauer.valhalla.backend.entities.Hero;
import de.jsauer.valhalla.backend.enums.EElement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeroRepository extends JpaRepository<Hero, Long> {

    Hero findOneByGarmId(String garmId);

    List<Hero> findAllByNameIgnoreCaseContaining(String name, Pageable pageable);

    long countByNameIgnoreCaseContaining(String name);

    List<Hero> findAllBySkillElement(EElement element);
}
