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
import java.util.Locale;

import labs.pm.data.*;
import static labs.pm.data.Rating.*;
        
/**
 * {@code Shop} class represents an application that manages Products
 * @version 2.0
 * @author PitzTech
 */
public class Shop{
    public static void main(String args[]){
        //var p1 = new Product(
        //                         "Tea",
        //                         BigDecimal.valueOf(1.99)
        //                        );
        var creator = new ProductManager(Locale.FRANCE);
        
        Product p1 = creator.createProduct(
                                 "Tea",
                                 BigDecimal.valueOf(1.99),
                                 NOT_RATED
                                ); 
        creator.printProductReport();
        
        p1 = creator.reviewProduct(p1, Rating.FOUR_STAR, "Nice hot cup of tea.");
        creator.printProductReport();
        
        p1 = creator.reviewProduct(p1, Rating.TWO_STAR, "Rather weak tea.");
        p1 = creator.reviewProduct(p1, Rating.FIVE_STAR, "Perfect tea.");
        creator.printProductReport();
        
        Product p2 = creator.createProduct(
                                 "Coffe",
                                 BigDecimal.valueOf(1.99),
                                 FOUR_STAR
                                );
        Product p3 = creator.createProduct(
                              "Cake",
                              BigDecimal.valueOf(3.99),
                              FIVE_STAR,
                              LocalDate.now().plusDays(2)
                            );
        
        //Object p4 = new Product(
        //                         "Tea",
        //                         BigDecimal.valueOf(1.99)
        //                        );
       
        Product p5 = creator.createProduct(
                              "Cake",
                              BigDecimal.valueOf(3.99),
                              FIVE_STAR,
                              LocalDate.now().plusDays(2)
                            );
        
        //Product p6 = new Product();
        // a immutable / thread safe change
        
        p5 = p5.applyRating(THREE_STAR);
        
        Product p7 = p5.applyRating(TWO_STAR);
        
        System.out.println(p5.equals(p7));
        
        //if(p3 instanceof Food){
            LocalDate bestBefore = ((Food)p3).getBestBefore();
        //}
        
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        //System.out.println(p4);
        System.out.println(p5);
        //System.out.println(p6);
    }
}