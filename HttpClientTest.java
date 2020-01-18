import java.net.http.HttpClient;
import java.net.http.HttpClient.*;
import java.net.http.*;
import java.net.http.HttpResponse.*;
import java.net.http.HttpRequest.*;
import java.nio.file.*;
import java.time.*;
import java.net.*;
import java.util.concurrent.*;

class HttpClientTest {

	public static void main(String[] args) throws Exception {
		System.out.println("Testing HttpClient ...");
		
		test1();
	}


	public static void test1() throws Exception {
		HttpClient client = HttpClient.newBuilder()
			.version(Version.HTTP_1_1)
			.followRedirects(Redirect.NORMAL)
			.connectTimeout(Duration.ofSeconds(20))
			//.proxy(ProxySelector.of(new InetSocketAddress("proxy.example.com", 80)))
			//.authenticator(Authenticator.getDefault())
			.build();
			
	   HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create("https://google.com/"))
			.timeout(Duration.ofMinutes(2))
			.header("Content-Type", "application/json")
			.POST(BodyPublishers.ofFile(Paths.get("file.json")))
			.build();
			
		System.out.println("Testing Async...");
	   CompletableFuture<Void> cf = client.sendAsync(request, BodyHandlers.ofString())
        .thenApply(HttpResponse::body)
        .thenAccept(System.out::println);
        
        
        CompletableFuture<String> cf2 = client.sendAsync(request, BodyHandlers.ofString())
        .thenApply(HttpResponse::body);
        
        //Thread.sleep(1000);  	
        
        /*client.sendAsync(request, BodyHandlers.ofString())
        .thenApply(r -> System.out.println(r.class.getName()))
        .thenAccept(System.out::println); */
			
		//System.out.println("Testing Sync...");
	   //HttpResponse<String> responseSync = client.send(request, BodyHandlers.ofString());
	   //System.out.println(responseSync.statusCode());
	   //System.out.println(responseSync.body());  
	   
	   Object o = cf.get();
	   
	   System.out.println(o == null ? "o is null" : o.getClass().getName());
	   
	   o = cf2.get();
	   
	   System.out.println(o);
	   System.out.println(o.getClass().getName());
	}
}
