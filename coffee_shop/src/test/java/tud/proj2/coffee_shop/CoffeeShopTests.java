package tud.proj2.coffee_shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tud.proj2.coffee_shop.domain.Coffee;
import tud.proj2.coffee_shop.domain.Owner;
import tud.proj2.coffee_shop.domain.Plantation;
import tud.proj2.coffee_shop.service.CoffeeRepository;
import tud.proj2.coffee_shop.service.OwnerRepository;
import tud.proj2.coffee_shop.service.PlantationRepository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CoffeeShopTests {

    @Autowired
    PlantationRepository plantationRepository;
    @Autowired
    OwnerRepository ownerRepository;
	@Autowired
    CoffeeRepository coffeeRepository;

    @BeforeEach
    void setUp() {
        ownerRepository.deleteAll();
        plantationRepository.deleteAll();
        coffeeRepository.deleteAll();
    }

    @Test
    void insertTest() {
        Owner owner = new Owner("Jan", "Kowalski", 63);
        Plantation plantation = new Plantation("Paragwajska palarnia ziaren", "Paragwaj", Date.valueOf("1980-08-08"), owner);
        Coffee coffee = new Coffee("Kawa1", "Arabika", 15, plantation);
        ownerRepository.save(owner);
        plantationRepository.save(plantation);
        coffeeRepository.save(coffee);

        assertTrue(ownerRepository.findById(owner.getId()).isPresent());
        assertEquals(ownerRepository.findById(owner.getId()).get(), owner);

        assertTrue(plantationRepository.findById(plantation.getId()).isPresent());
        assertEquals(plantationRepository.findById(plantation.getId()).get(), plantation);

        assertTrue(coffeeRepository.findById(coffee.getId()).isPresent());
        assertEquals(coffeeRepository.findById(coffee.getId()).get(), coffee);
    }

    @Test
    void selectTest() {
        Owner owner = new Owner("Jan", "Kowalski", 63);
        Plantation plantation = new Plantation("Paragwajska palarnia ziaren", "Paragwaj", Date.valueOf("1980-08-08"), owner);
        Coffee coffee1 = new Coffee("Kawa1", "Arabika", 15, plantation);
        Coffee coffee2 = new Coffee("Kawa2", "Robusta", 15, plantation);
        ownerRepository.save(owner);
        plantationRepository.save(plantation);
        coffeeRepository.save(coffee1);
        coffeeRepository.save(coffee2);

        assertTrue(ownerRepository.findById(owner.getId()).isPresent());
        assertEquals(ownerRepository.findById(owner.getId()).get(), owner);

        assertTrue(plantationRepository.findById(plantation.getId()).isPresent());
        assertEquals(plantationRepository.findById(plantation.getId()).get(), plantation);

        assertTrue(coffeeRepository.findById(coffee1.getId()).isPresent());
        assertEquals(coffeeRepository.findById(coffee1.getId()).get(), coffee1);

        assertTrue(coffeeRepository.findById(coffee2.getId()).isPresent());
        assertEquals(coffeeRepository.findById(coffee2.getId()).get(), coffee2);
    }

    @Test
    void updateTest() {
        Owner owner = new Owner("Jan", "Kowalski", 63);
        Plantation plantation = new Plantation("Paragwajska palarnia ziaren", "Paragwaj", Date.valueOf("1980-08-08"), owner);
        Coffee coffee1 = new Coffee("Kawa1", "Arabika", 15, plantation);
        Coffee coffee2;
        ownerRepository.save(owner);
        plantationRepository.save(plantation);
        coffeeRepository.save(coffee1);

        assertTrue(coffeeRepository.findById(coffee1.getId()).isPresent());
        coffee2 = coffeeRepository.getOne(coffee1.getId());
        coffee2.setName("Kawa1 ZUpdatowana");
        coffeeRepository.save(coffee2);
        assertEquals(coffeeRepository.findAll().size(), 1);

        assertTrue(coffeeRepository.findById(coffee2.getId()).isPresent());
        assertEquals(coffeeRepository.findById(coffee2.getId()).get().getName(), coffee2.getName());
    }

    @Test
    void deleteTest() {
        Owner owner = new Owner("Jan", "Kowalski", 63);
        Plantation plantation = new Plantation("Paragwajska palarnia ziaren", "Paragwaj", Date.valueOf("1980-08-08"), owner);
        Coffee coffee = new Coffee("Kawa1", "Arabika", 15, plantation);
        ownerRepository.save(owner);
        plantationRepository.save(plantation);
        coffeeRepository.save(coffee);

        coffeeRepository.deleteAll();
        assertTrue(coffeeRepository.findAll().isEmpty());
        assertTrue(plantationRepository.findAll().isEmpty());
        assertTrue(ownerRepository.findAll().isEmpty());

        coffeeRepository.save(coffee);
        plantationRepository.deleteAll();
        assertTrue(coffeeRepository.findAll().isEmpty());
        assertTrue(plantationRepository.findAll().isEmpty());
        assertTrue(ownerRepository.findAll().isEmpty());

        ownerRepository.save(owner);
        plantationRepository.save(plantation);
        ownerRepository.deleteAll();
        assertTrue(plantationRepository.findAll().isEmpty());
        assertTrue(ownerRepository.findAll().isEmpty());
    }

// @NamedQueries
    @Test
    void findAllBelowPriceTest(){
        coffeeRepository.save(new Coffee("Kawa1", "Arabika", 1));
        coffeeRepository.save(new Coffee("Kawa2", "Arabika", 2));
        coffeeRepository.save(new Coffee("Kawa3", "Arabika", 3));
        coffeeRepository.save(new Coffee("Kawa4", "Arabika", 4));
        coffeeRepository.save(new Coffee("Kawa5", "Arabika", 5));

        assertTrue(coffeeRepository.findAllBelowPrice(0d).isEmpty());
        assertEquals(coffeeRepository.findAllBelowPrice(3d).size(), 2);
    }

    @Test
    void findAllBySpeciesTest(){
        coffeeRepository.save(new Coffee("Kawa1", "Arabika", 1));
        coffeeRepository.save(new Coffee("Kawa2", "Arabika", 2));
        coffeeRepository.save(new Coffee("Kawa3", "Robusta", 3));
        coffeeRepository.save(new Coffee("Kawa4", "Robusta", 4));
        coffeeRepository.save(new Coffee("Kawa5", "Robusta", 5));

        assertTrue(coffeeRepository.findAllBySpecies("Nonexistent").isEmpty());
        assertEquals(coffeeRepository.findAllBySpecies("Robusta").size(), 3);
    }

// @Queries
    @Test
    void findAllBelowPriceSortedTest() {

        Coffee coffee1 = new Coffee("Kawa4", "Robusta", 4);
        Coffee coffee2 = new Coffee("Kawa1", "Arabika", 1);
        Coffee coffee3 = new Coffee("Kawa3", "Robusta", 3);
        Coffee coffee4 = new Coffee("Kawa2", "Arabika", 2);
        Coffee coffee5 = new Coffee("Kawa5", "Robusta", 5);

        coffeeRepository.save(coffee4);
        coffeeRepository.save(coffee1);
        coffeeRepository.save(coffee3);
        coffeeRepository.save(coffee2);
        coffeeRepository.save(coffee5);

        assertTrue(coffeeRepository.findAllBelowPrice(0d).isEmpty());
        assertEquals(coffeeRepository.findAllBelowPrice(3d).size(), 2);

        List<Coffee> coffees = coffeeRepository.findAllBelowPrice(4d);

        assertEquals(coffees.get(0), coffee4);
        assertEquals(coffees.get(1), coffee3);
        assertEquals(coffees.get(2), coffee2);
    }


    @Test
    void findAllBySpeciesSortedTest(){
        Coffee coffee1 = new Coffee("Kawa4", "Robusta", 4);
        Coffee coffee2 = new Coffee("Kawa1", "Arabika", 1);
        Coffee coffee3 = new Coffee("Kawa3", "Robusta", 3);
        Coffee coffee4 = new Coffee("Kawa2", "Arabika", 2);
        Coffee coffee5 = new Coffee("Kawa5", "Robusta", 5);

        coffeeRepository.save(coffee4);
        coffeeRepository.save(coffee1);
        coffeeRepository.save(coffee3);
        coffeeRepository.save(coffee2);
        coffeeRepository.save(coffee5);

        assertTrue(coffeeRepository.findAllBySpeciesSorted("Nonexistent").isEmpty());
        assertEquals(coffeeRepository.findAllBySpeciesSorted("Robusta").size(), 3);

        List<Coffee> coffees = coffeeRepository.findAllBySpeciesSorted("Robusta");

        assertEquals(coffees.get(0), coffee3);
    }

// Queries
    @Test
    void findByPriceBetweenOrderByPriceDescTest(){
        Coffee coffee1 = new Coffee("Kawa4", "Robusta", 4);
        Coffee coffee2 = new Coffee("Kawa1", "Arabika", 1);
        Coffee coffee3 = new Coffee("Kawa3", "Robusta", 3);
        Coffee coffee4 = new Coffee("Kawa2", "Arabika", 2);
        Coffee coffee5 = new Coffee("Kawa5", "Robusta", 5);

        coffeeRepository.save(coffee4);
        coffeeRepository.save(coffee1);
        coffeeRepository.save(coffee3);
        coffeeRepository.save(coffee2);
        coffeeRepository.save(coffee5);

        assertTrue(coffeeRepository.findByPriceBetweenOrderByPriceDesc(10, 15).isEmpty());
        assertEquals(coffeeRepository.findByPriceBetweenOrderByPriceDesc(2, 4).size(), 3);
    }

    @Test
    void findByPriceIsBetweenAndSpeciesOrderByPriceAscTest(){
        Coffee coffee1 = new Coffee("Kawa4", "Robusta", 4);
        Coffee coffee2 = new Coffee("Kawa1", "Arabika", 1);
        Coffee coffee3 = new Coffee("Kawa3", "Robusta", 3);
        Coffee coffee4 = new Coffee("Kawa2", "Arabika", 2);
        Coffee coffee5 = new Coffee("Kawa5", "Robusta", 5);

        coffeeRepository.save(coffee4);
        coffeeRepository.save(coffee1);
        coffeeRepository.save(coffee3);
        coffeeRepository.save(coffee2);
        coffeeRepository.save(coffee5);

        assertTrue(coffeeRepository.findByPriceBetweenOrderByPriceDesc(10, 15).isEmpty());
        assertEquals(coffeeRepository.findByPriceBetweenOrderByPriceDesc(2, 4).size(), 3);
    }

    @Test
    void findByPriceLessThanOrderByPriceAscTest(){
        Coffee coffee1 = new Coffee("Kawa1", "Arabika", 1);
        Coffee coffee2 = new Coffee("Kawa2", "Arabika", 2);
        Coffee coffee3 = new Coffee("Kawa3", "Robusta", 3);
        Coffee coffee4 = new Coffee("Kawa4", "Robusta", 4);
        Coffee coffee5 = new Coffee("Kawa5", "Robusta", 5);

        coffeeRepository.save(coffee4);
        coffeeRepository.save(coffee1);
        coffeeRepository.save(coffee3);
        coffeeRepository.save(coffee2);
        coffeeRepository.save(coffee5);

        assertTrue(coffeeRepository.findByPriceLessThanOrderByPriceAsc(0).isEmpty());
        assertEquals(coffeeRepository.findByPriceLessThanOrderByPriceAsc(3.5).size(), 3);
    }

    @Test
    void findByNameLikeOrderByPriceAscTest(){
        Coffee coffee1 = new Coffee("Kawa1", "Arabika", 1);
        Coffee coffee2 = new Coffee("Kawa2", "Arabika", 2);
        Coffee coffee3 = new Coffee("Kawa3", "Robusta", 3);
        Coffee coffee4 = new Coffee("KawaPyszna", "Robusta", 4);
        Coffee coffee5 = new Coffee("KawaPyszna", "NieRobusta", 5);

        coffeeRepository.save(coffee4);
        coffeeRepository.save(coffee1);
        coffeeRepository.save(coffee3);
        coffeeRepository.save(coffee2);
        coffeeRepository.save(coffee5);

        assertTrue(coffeeRepository.findByNameLikeOrderByPriceAsc("Niedobra").isEmpty());
        assertEquals(coffeeRepository.findByNameLikeOrderByPriceAsc("KawaPyszna").size(), 2);

        List<Coffee> coffees = coffeeRepository.findByNameLikeOrderByPriceAsc("KawaPyszna");

        assertEquals(coffees.get(0), coffee4);
    }

// Transaction
    @Test
    void imposeTaxesByIdTest(){
        Coffee coffee1 = new Coffee("Kawa1", "Arabika", 1);
        Coffee coffee2 = new Coffee("Kawa2", "Arabika", 2);
        Coffee coffee3 = new Coffee("Kawa3", "Robusta", 3);
        Coffee coffee4 = new Coffee("Kawa4", "Robusta", 4);
        Coffee coffee5 = new Coffee("Kawa5", "Robusta", 5);

        coffeeRepository.save(coffee1);
        coffeeRepository.save(coffee2);
        coffeeRepository.save(coffee3);
        coffeeRepository.save(coffee4);
        coffeeRepository.save(coffee5);

        coffeeRepository.imposeTaxes();

        List<Coffee> coffees = coffeeRepository.findAll();

        assertEquals(coffees.get(0).getPrice(), coffee1.getPrice() * 1.23);
        assertEquals(coffees.get(1).getPrice(), coffee2.getPrice() * 1.23);
        assertEquals(coffees.get(2).getPrice(), coffee3.getPrice() * 1.23);
        assertEquals(coffees.get(3).getPrice(), coffee4.getPrice() * 1.23);
        assertEquals(coffees.get(4).getPrice(), coffee5.getPrice() * 1.23);
    }
}
