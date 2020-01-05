package tud.proj2.coffee_shop.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name="Coffee.findAllBelowPrice",
                query="SELECT c FROM Coffee c WHERE c.price < ?1"),
        @NamedQuery(name="Coffee.findAllBySpecies",
                query="SELECT c FROM Coffee c WHERE c.species = ?1"),
})
public class Coffee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotEmpty
    private String name;
    @NotEmpty
    private String species;
    @NotEmpty
    private double price;

    @ManyToOne(cascade = CascadeType.ALL)
    private Plantation plantation;

    public Coffee(@NotEmpty String name, @NotEmpty String species, @NotEmpty double price) {
        this.name = name;
        this.species = species;
        this.price = price;
    }

    public Coffee(@NotEmpty String name, @NotEmpty String species, @NotEmpty double price, Plantation plantation) {
        this.name = name;
        this.species = species;
        this.price = price;
        this.plantation = plantation;
        plantation.addCoffee(this);
    }

    public Coffee() {
    }

    void setPlantation(Plantation newPlantation){
        if(plantation != null)
            plantation.coffees.remove(this);
        plantation = newPlantation;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coffee coffee = (Coffee) o;
        return Double.compare(coffee.price, price) == 0 &&
                Objects.equals(name, coffee.name) &&
                Objects.equals(species, coffee.species);
    }
}
