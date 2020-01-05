package tud.proj2.coffee_shop.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import tud.proj2.coffee_shop.domain.Coffee;

import java.util.List;

@Transactional(readOnly = true)
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {

    List<Coffee> findAllBelowPrice(double price);

    List<Coffee> findAllBySpecies(String species);

    @Query(value = "SELECT c FROM Coffee c WHERE price < ?1 ORDER BY price")
    List<Coffee> findAllBelowPriceSorted(double price);

    @Query(value = "SELECT c FROM Coffee c WHERE species = ?1 ORDER BY price")
    List<Coffee> findAllBySpeciesSorted(String species);


    // BETWEEN, ORDER BY, DESC
    List<Coffee> findByPriceBetweenOrderByPriceDesc(
            double minPrice, double maxPrice);
    // BETWEEN, AND, ORDER BY, ASC
    List<Coffee> findByPriceIsBetweenAndSpeciesOrderByPriceAsc(
            double minPrice, double maxPrice, String species);
    // LESS THAN, ORDER BY, ASC
    List<Coffee> findByPriceLessThanOrderByPriceAsc(
            double price);
    // LIKE, ORDER BY, ASC
    List<Coffee> findByNameLikeOrderByPriceAsc(
            String name);

    // TRANSACTION
    @Modifying
    @Transactional
    @Query("update Coffee set price = price * 1.23")
    void imposeTaxes ();

    //@Query("update Coffee set price = price * 1.23 where id = ?1")
    //void imposeTaxesById (long id);
}
