/*
 * Copyright (C) 2021 victo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package labs.pm.data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import static java.math.RoundingMode.HALF_UP;
import java.time.LocalDate;
import static labs.pm.data.Rating.*;

/**
 * {@code Product} class represents properties and behaviours of
 * product objects in the Product Management System.
 * <br>
 * Each product can have a discount, calculated based on a
 * {@link DISCOUNT_RATE discount rate}
 * @version 4.0
 * @author PitzTech
 */
public abstract class Product {
    
    /**
     * A constant that defines a
     * {@link java.math.BigDecimal BigDecimal} value of the discount rate
     * <br>
     * Discount rate is 10%
     */
    public static final BigDecimal DISCOUNT_RATE;
    private static int maxId;
    private final int id;
    private String name;
    private BigDecimal price;
    private Rating rating;
       
    static{
        DISCOUNT_RATE = BigDecimal.valueOf(0.1);
        maxId = 100;
    }
    
//    {
//        id = ++maxId;
//    }
    public Product(int id, String name, BigDecimal price, Rating rating){
        this.id = id; 
        this.name = name;
        this.price = price;
        this.rating = rating;     
    }
    public Product(String name, BigDecimal price, Rating rating){
        this(++maxId, name, price, rating);
    }
    public Product(String name, BigDecimal price){
        this(name, price, NOT_RATED);
    }
    public Product(){
        this("NoName", BigDecimal.ZERO);
    }
    
    @Override
    public String toString(){
        return "Product{" + 
                "id: " + id + 
                ", name: " + name + 
                ", price: " + price + 
                ", rating: " + rating.getStars() + 
                " " + getBestBefore() +
                "}";
    }
    
    @Override
    public int hashCode(){
        int hash = 5;
        hash = 23 * hash + this.id;
        return hash;
    }
    
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj instanceof Product){ 
            final Product other = (Product) obj;
            return this.id == other.id 
                   && Objects.equals(this.name, other.name);
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
    
    /**
     * Calculates discount based on a product price and
     * {@link DISCOUNT_RATE discount rate}
     * @return a {@link java.math.BigDecimal BigDecimal}
     * value of the discount
     */
    public BigDecimal getDiscount(){
        return price.multiply(DISCOUNT_RATE).setScale(2, HALF_UP);
    }
    
    public Rating getRating(){
        return rating;
    }
    
    public abstract Product applyRating(Rating newRating);
    
    /**
     * Get the value of the best before date for the product
     * 
     * @return the value of bestBefore
     */
    public LocalDate getBestBefore(){
        return LocalDate.now();
    }
}
