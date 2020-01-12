package com.mycompany.mavenproject1.resources;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;

@Stateless
public class ThreadStarter {

    private static final Logger LOG
            = Logger.getLogger(ThreadStarter.class.getName());

    @Resource
    ManagedExecutorService mes;

    public void executeAsync() {
        this.mes.submit(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "Sleping for 1 second ...");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + "Enough sleping");
            } catch (Exception e) {
                LOG.log(Level.SEVERE, null, e);
                e.printStackTrace();
            }
        });
    }

    public Future<String> calculateAsync() throws InterruptedException {
        CompletableFuture<String> completableFuture
                = new CompletableFuture<>();

        mes.submit(() -> {
            for (int i = 0; i < 3; i++) {
                System.out.println(Thread.currentThread().getName() + " " + i);
                Thread.sleep(1000);
            }
            completableFuture.complete("Hello");
            return null;
        });

        return completableFuture;
    }

}
