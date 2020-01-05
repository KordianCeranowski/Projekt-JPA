package tud.proj2.coffee_shop.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import tud.proj2.coffee_shop.domain.Owner;

@Transactional(readOnly = true)
public interface OwnerRepository extends JpaRepository<Owner, Long> {
}