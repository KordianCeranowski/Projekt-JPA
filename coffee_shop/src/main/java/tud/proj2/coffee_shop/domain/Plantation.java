package tud.proj2.coffee_shop.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Plantation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String country;
    @NotEmpty
    private Date establishmentDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private Owner owner;
    @OneToMany(mappedBy = "plantation", cascade = CascadeType.ALL)
    List<Coffee> coffees;

    public Plantation() {
    }

    public Plantation(@NotEmpty String name, @NotEmpty String country, @NotEmpty Date establishmentDate, Owner owner) {
        this.name = name;
        this.country = country;
        this.establishmentDate = establishmentDate;
        this.owner = owner;
        this.coffees = new ArrayList<>();
        owner.addPlatation(this);
    }

    void addCoffee(Coffee coffee) {
        coffee.setPlantation(this);
        coffees.add(coffee);
    }

    void setOwner(Owner newOwner){
        if(owner != null)
            owner.plantations.remove(this);
        owner = newOwner;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plantation that = (Plantation) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(country, that.country);
    }
}
