package com.mycompany.mavenproject1.resources;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author 
 */
@Path("javaee8")
public class JavaEE8Resource {
    
    @Inject
    ThreadStarter ts;
    
    @GET
    public Response ping() {
        
        System.out.println(Thread.currentThread().getName() + "Testing async ...");
        
        ts.executeAsync();
        
        System.out.println(Thread.currentThread().getName() + "After async call.");
        
        System.out.println(Thread.currentThread().getName() + "CompletableFuture test ...");
        try {
            Future<String> fs = ts.calculateAsync();
            System.out.println(Thread.currentThread().getName() + " " + fs.get());
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(JavaEE8Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Response
                .ok("ping")
                .build();
    }
}
