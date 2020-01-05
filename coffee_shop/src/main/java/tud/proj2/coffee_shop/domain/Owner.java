package tud.proj2.coffee_shop.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @NotEmpty
    private int age;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    List<Plantation> plantations;

    public Owner() {
    }

    public Owner(@NotEmpty String name, @NotEmpty String surname, @NotEmpty int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.plantations = new ArrayList<>();
    }

    void addPlatation(Plantation plantation) {
        plantation.setOwner(this);
        plantations.add(plantation);
    }

    public long getId() {
        return id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return age == owner.age &&
                Objects.equals(name, owner.name) &&
                Objects.equals(surname, owner.surname);
    }
}
