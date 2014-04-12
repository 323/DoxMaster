import java.util.Scanner;
import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.*;

class DoxMaster {
    public static void main(String[] args) throws Exception {
        String readme0 = "Welcome to Dox Master.";
        String readme1 = "Stop trying to decompile my program.";
        String readme2 = "Coded by 323.";
        HTTPRequest request = new HTTPRequest();
        Scanner name = new Scanner(System.in);
        String url; //String for storing URLs
        String source; //String for storing page sources
        String targetName; //Store target's name
        String formattedTargetName; //Store the URL-formatted target name
        String username; //Store results of possible target usernames
        String facebookResults[]; //Store results of facebooks with the target's name
        String facebookAbouts[]; //Store "About" sections from facebookResults
        String twitterResults[]; //Store results of twitters with the target's name
        String twitterBios[]; //Store "Bios" from twitterResults
        String targetInterests[]; //Store high-frequency words from facebooks and twitters
        System.out.println("Enter the name of the person you'd like to dox.");
        targetName = name.nextLine();
        System.out.println("Doxing your target...");
        System.out.println("Checking Facebooks with the name " + targetName);
        formattedTargetName = targetName.replace( ' ', '+' );
        url = "https://www.facebook.com/search.php?q=" + formattedTargetName;
        source = SSLSocketClient(url);
        System.out.println(source);
    }
<<<<<<< HEAD
    public static String SSLSocketClient(String parameters) throws Exception {
        String endhtml = "</html>";
        Boolean found;
        String urlString = parameters;
        URL url = new URL(urlString);
    
        Security.addProvider(
          new com.sun.net.ssl.internal.ssl.Provider());
    
        SSLSocketFactory factory = 
          (SSLSocketFactory)SSLSocketFactory.getDefault();
        SSLSocket socket = 
          (SSLSocket)factory.createSocket(url.getHost(), 443);
    
        PrintWriter out = new PrintWriter(
            new OutputStreamWriter(
              socket.getOutputStream()));
        out.println("GET " + urlString + " HTTP/1.1");
        out.println();
        out.flush();
    
        BufferedReader in = new BufferedReader(
          new InputStreamReader(
          socket.getInputStream()));
    
        String line;
        String source = "";
        
        while (((line = in.readLine()) != null) && (found = false)) {
              source += line;
              found = source.contains(endhtml);
        }
        //System.out.println(source); //For testing
        out.close();
        in.close();
        return source;
    }
}
=======
}
>>>>>>> 0be5af53735410182eb1363360a3318c5cf7a302
