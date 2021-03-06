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
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author PitzTech
 */
public class ProductManager {
    
    private Locale locale;
    private ResourceBundle resources;
    private DateTimeFormatter dateFormat;
    private NumberFormat moneyFormat;
    
    private Product product;
    private Review reviews[] = new Review[5];
    
    public ProductManager(Locale locale){
        this.locale = locale;
        resources = ResourceBundle.getBundle("labs.pm.data.resources", locale);
        dateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                                      .localizedBy(locale);
        moneyFormat = NumberFormat.getCurrencyInstance(locale);
    }
    
    public Product createProduct(String name, BigDecimal price, Rating rating, LocalDate bestBefore){
        product = new Food(name, price, rating, bestBefore); 
        return product;
    }
    public Product createProduct(String name, BigDecimal price, Rating rating){
        product = new Drink(name, price, rating);
        return product; 
    }
 
    public Product reviewProduct(Product product, Rating rating, String comments){
        if(reviews[reviews.length -1] != null)
            reviews = Arrays.copyOf(reviews, reviews.length + 5);
        
        int sum = 0, i;
        boolean reviewed = false;
        for(i = 0; i < reviews.length && !reviewed; i++){
            if(reviews[i] != null) continue;
            
            reviews[i] = new Review(rating, comments);
            reviewed = true;
            
            sum += reviews[i].getRating().ordinal();
        }
        
        this.product = product.applyRating(
                Rateable.convert(
                    Math.round((float)sum/i)
                )
        );
        
        return this.product;
    }
    
    public void printProductReport(){
        StringBuilder txt = new StringBuilder();
        txt.append(MessageFormat.format(resources.getString("product"),
                product.getName(),
                moneyFormat.format(product.getPrice()),
                product.getRating().getStars(),
                dateFormat.format(product.getBestBefore())
        ));
        
        txt.append("\n");
        
        if(reviews[0] == null){
            txt.append(resources.getString("no.reviews"));
            txt.append("\n");
        }else{
            for(var review : reviews){
                if(review == null) break;

                txt.append(MessageFormat.format(resources.getString("review"),
                        review.getRating().getStars(),
                        review.getComments()
                )); 

                txt.append("\n");
            }
        }
                 
        System.out.println(txt);
    }
}
