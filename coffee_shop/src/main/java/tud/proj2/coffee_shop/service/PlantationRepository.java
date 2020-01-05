package tud.proj2.coffee_shop.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import tud.proj2.coffee_shop.domain.Plantation;

@Transactional(readOnly = true)
public interface PlantationRepository extends JpaRepository<Plantation, Long> {
}
