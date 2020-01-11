/*
 * 
 * https://www.baeldung.com/java-completablefuture
 * 
 * https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html
 * 
 * 
 * 
 */


import java.util.concurrent.*;
import java.util.stream.*;

class test1 {
	
	static final int POOL_SIZE = Runtime.getRuntime().availableProcessors();
	static final ExecutorService POOL = Executors.newFixedThreadPool(POOL_SIZE);

	public static void main(String[] args) throws Exception {
		System.out.println("testing ExecutorService");
		
		
		System.out.println(POOL_SIZE);
		
		// ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
		
		test1 t = new test1();
		
		Future<String> f1 = t.calculateAsync();		
		
		t.joinFuture();
		
		System.out.println("Stopping thread and waiting future to complete ...");
		System.out.println(f1.get());
		
		System.out.println("Shutting down ExecutorService ...");
		POOL.shutdown();
	}
	
	public Future<String> calculateAsync() throws InterruptedException {
		CompletableFuture<String> completableFuture
				= new CompletableFuture<>();
	 
		POOL.submit(() -> {
			for (int i = 0; i < 3; i++) {				
				System.out.println(i);
				Thread.sleep(1000);
			}
			completableFuture.complete("Hello");
			return null;
		});
	 
		return completableFuture;
	}
	
	
	public void joinFuture() throws Exception {
		CompletableFuture<String> future1  
				= CompletableFuture.supplyAsync(() -> "Hello");
		CompletableFuture<String> future2  
				= CompletableFuture.supplyAsync(() -> "Beautiful");
		CompletableFuture<String> future3  
				= CompletableFuture.supplyAsync(() -> "World");
				
		String combined = Stream.of(future1, future2, future3)
		  .map(CompletableFuture::join)
		  .collect(Collectors.joining(" "));
		  
		System.out.println("Stream.of is nice: " + combined);
		 
		assert "Hello Beautiful World".equals(combined);
	}

}
