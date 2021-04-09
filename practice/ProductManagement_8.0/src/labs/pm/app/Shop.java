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
package labs.pm.app;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;

import labs.pm.data.*;
import static labs.pm.data.Rating.*;
        
/**
 * {@code Shop} class represents an application that manages Products
 * @version 8.0
 * @author PitzTech
 */
public class Shop{
    public static void main(String args[]){
        //var p1 = new Product(
        //                         "Tea",
        //                         BigDecimal.valueOf(1.99)
        //                        );
        var creator = new ProductManager("en-US");
        
//        creator.createProduct(
//                            "Tea",
//                            BigDecimal.valueOf(1.99),
//                            NOT_RATED
//                           ); 
        creator.parseProduct("D,101,Tea,1.99,0,09-09-2002");
        //creator.printProductReport(42);
        
        creator.reviewProduct(101, Rating.FOUR_STAR, "Nice hot cup of tea.");
        //creator.reviewProduct(42, Rating.FOUR_STAR, "Nice hot cup of tea.");
        //creator.reviewProduct(p1.getId(), Rating.FOUR_STAR, "Nice hot cup of tea.");
        //creator.printProductReport(p1);
        
        //creator.reviewProduct(101, Rating.TWO_STAR, "Rather weak tea.");
        creator.parseReview("101,3,Rather weak tea.");
        creator.reviewProduct(101, Rating.FIVE_STAR, "Perfect tea.");
        
        creator.printProductReport(101);
        //creator.printProductReport(p1.getId());
        
        //creator.changeLocale("en-US");
        
//        creator.createProduct(
//                            "Coffe",
//                            BigDecimal.valueOf(1.99),
//                            FOUR_STAR
//                           );
        creator.parseProduct("D,102,Coffe,1.99,4,2020-49-06");
        creator.parseReview("102,3,Coffe was ok");
        creator.parseReview("102,1,Where is the milk?!");
        creator.parseReview("102,5,It's perfect with ten spoon of sugar!");
        
        creator.printProductReport(102);
        
        creator.createProduct(
                              "Cake",
                              BigDecimal.valueOf(3.99),
                              FIVE_STAR,
                              LocalDate.now().plusDays(2)
                            );
         
        creator.createProduct(
                              "Cake",
                              BigDecimal.valueOf(3.99),
                              FIVE_STAR,
                              LocalDate.now().plusDays(2)
                            );
       
        creator.printProducts(
                p -> p.getPrice().floatValue() < 2,
                (p1, p2) -> p2.getRating().ordinal() - p1.getRating().ordinal()
        );
        
        creator.getDiscounts().forEach((rating, discount) -> System.out.println(rating +'\t'+ discount));
        
//        creator.printProducts(
//                (p1, p2) -> p1.getPrice().compareTo(p2.getPrice())
//        );
        
        Comparator<Product> ratingSorter = (p1, p2) -> p1.getRating().ordinal() - p2.getRating().ordinal();
        Comparator<Product> priceSorter = (p1, p2) -> p1.getPrice().compareTo(p2.getPrice());
        //creator.printProducts(ratingSorter.thenComparing(priceSorter).reversed());

    }
}