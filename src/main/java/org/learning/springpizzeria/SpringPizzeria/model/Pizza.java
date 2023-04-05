package org.learning.springpizzeria.SpringPizzeria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "pizzas")
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Non puoi lascare vuoto questo campo")
    @Size(min = 1, max = 20, message = "La lunghezza del campo deve essere tra 1 e 20")
    private String name;
    @Lob
    @NotEmpty(message = "Non puoi lascare vuoto questo campo")
    @Size(min = 1,max = 100, message = "La lunghezza del campo deve essere tra 1 e 100")
    private String description;

    @NotNull(message = "Non puoi lascare vuoto questo campo")
    @Positive(message = "Puoi inserire solo valori maggiori di 0")
    private BigDecimal price;

    private String image;

    @OneToMany(mappedBy = "pizza")
    private List<Offer> offers;

//    GETTER SETTER


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}
