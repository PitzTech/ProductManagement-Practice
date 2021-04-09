/*
 * Copyright (C) 2021 PitzTech
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

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import labs.pm.data.*;
        
/**
 * {@code Shop} class represents an application that manages Products
 * @version 10.0
 * @author PitzTech
 */
public class Shop{
    public static void main(String args[]){
        var creator = ProductManager.getInstance();
        AtomicInteger clientCount = new AtomicInteger(0);
        
        Callable<String> client = () -> {
            String clientId = "Client "+ clientCount.incrementAndGet();
            String threadName = Thread.currentThread().getName();
            int productId = ThreadLocalRandom.current().nextInt(63)+101;
            String languageTag = ProductManager.getSupportedLocales()
                                               .stream()
                                               .skip(ThreadLocalRandom.current().nextInt(1))
                                               .findFirst().get();
            StringBuilder log = new StringBuilder();
            log.append(clientId+" "+threadName+"\n-\tstart of log\t-\n");
            log.append(creator.getDiscounts(languageTag)
                                .entrySet()
                                .stream()
                                .map(entry -> entry.getKey() + "\t"+entry.getValue())
                                .collect(Collectors.joining("\n")));
            
            Product product = creator.reviewProduct(productId, Rating.FOUR_STAR, "Yet another review");
            log.append(product != null ? "\nProduct "+productId+" reviewed\n" 
                                       : "\nProduct "+productId+" not reviewed\n");
            creator.printProductReport(productId, languageTag, clientId);
            log.append(clientId+" generated report for "+productId+" product");
            
            log.append("\n-\tend of log\t-\n");
            return log.toString();
        };
        
        List<Callable<String>> clients = Stream.generate(() -> client)
                                               .limit(5)
                                               .collect(Collectors.toList());
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        try {
            List<Future<String>> results = executorService.invokeAll(clients);
            executorService.shutdown();
            
            results.stream().forEach(result -> {
                try {
                    System.out.println(result.get());
                } catch (InterruptedException | ExecutionException ex) {
                    Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, "Error invoking clients", ex);
                }
            });
        } catch (InterruptedException ex) {
            Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, "Error invoking clients", ex);
        }
    }
}