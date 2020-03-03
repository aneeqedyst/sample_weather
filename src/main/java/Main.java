import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.*;
import java.util.Scanner;


public class Main{

    public  static int getweather(String location) throws IOException, InterruptedException{
        try {
            location = URLEncoder.encode(location, "UTF-8");
            String query = "http://api.weatherstack.com/current?access_key=e3d79cadddea91311e2271ec4a8ccabe&query=" + location;
            URI myURI = new URI(query);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(myURI)
                    .build();

            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            String jstring = response.body(); // json string response
            //System.out.println(jstring);


            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = objectMapper.readValue(jstring, JsonNode.class); // reading tree of the JSON
            JsonNode temp = node.get("current").get("temperature");
            int t = temp.asInt();
            return t;
        }
        catch (Exception e){
            System.out.println("An error occurred");
        }
        return 0;
    }

    public static void main(String[] args) throws IOException, InterruptedException{
        System.out.println("Welcome to Aneeq's Weather app!");
        Scanner scan = new Scanner(System.in);


        while(true){
            System.out.println("Enter location: ");
            String location = scan.nextLine();
            if(location.equals("exit"))
                break;
            System.out.println("Checking....");
            int temperature = getweather(location);
            System.out.println(temperature);
        }



    }
}
