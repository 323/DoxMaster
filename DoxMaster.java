import java.util.Scanner;
import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.*;
import org.apache.commons.lang3.StringUtils;

class DoxMaster {
    public static void main(String[] args) throws Exception {
        String readme0 = "Welcome to Dox Master.";
        String readme1 = "Stop trying to decompile my program.";
        String readme2 = "Coded by 323.";
        HTTPRequest request = new HTTPRequest();
        Scanner name = new Scanner(System.in);
        String profileMarker; //The string that marks the beginning of a new twitter pr
        String url; //String for storing URLs
        String source; //String for storing page sources
        String targetName; //Store target's name
        String formattedTargetName; //Store the URL-formatted target name
        String formattedTargetNameWP; //Stores the WhitePages URL-formatted target name
        String username; //Store results of possible target usernames
        String facebookResultsData[]; //Store results of facebooks with the target's name
        String facebookAbouts[]; //Store "About" sections from facebookResults
        String twitterResultsData[] = new String[200]; //Store results of twitters with the target's name
        int twitterResults; //Store the number of twitter results
        String twitterBios[]; //Store "Bios" from twitterResults
        String targetInterests[]; //Store high-frequency words from facebooks and twitters
        System.out.println("Enter the name of the person you'd like to dox.");
        targetName = name.nextLine();
        formattedTargetName = targetName.replace( ' ', '+' ); //Formatted for Twitter/Facebook/etc
        formattedTargetNameWP = targetName.replace( ' ', '-' ); //Formatted for WhitePages
        System.out.println("[~] Doxing your target...");
        System.out.println("[~] Checking Twitter for the name " + targetName);
        url = "https://twitter.com/search?q=" + formattedTargetName + "&mode=users";
        source = SSLSocketClient(url);
        //Parse twitter results here
        profileMarker = "<li class=\"js-stream-item stream-item stream-item";
        twitterResults = StringUtils.countMatches(source, profileMarker);
        if (twitterResults > 0) {
            if (twitterResults > 200) {
                System.out.println("[~] There were over 200 Twitter profiles matching " + targetName + "! As a result, the number of profiles that data will be collected from has been limited to 200.");
            } else {
                System.out.println("[+] " + twitterResults + " results were found when searching for " + targetName + " on Twitter.");
            }
            twitterResultsData[0] = StringUtils.substringBetween(source, "<ol class=\"stream-items", "<div class=\"stream-footer \">"); //Get section of source with profiles
            twitterResultsData[1] = StringUtils.substringBetween(twitterResultsData[0], profileMarker, "</li>"); //Get first profile
            System.out.println("/////////////////////////////////////////firstprofile////////////////////////////////////////////////////////////");
            System.out.println(twitterResultsData[1]);
            if (twitterResults > 2) {
                for (int i=2; i <= twitterResults; i++) {
                    int j = i - 1;
                    if (j == 1) {
                        if (i == twitterResults) {
                            twitterResultsData[i] = StringUtils.substringBetween(twitterResultsData[0], "</div></li>", "</ol>");
                        } else {
                            twitterResultsData[i] = StringUtils.substringBetween(twitterResultsData[0], "</div></li>", "</div></li>");
                        }
                    } else {
                        if (i == twitterResults) {
                            twitterResultsData[i] = StringUtils.substringBetween(twitterResultsData[0], twitterResultsData[j], "</ol>");
                        } else {
                            twitterResultsData[i] = StringUtils.substringBetween(twitterResultsData[0], twitterResultsData[j], "</div></li>");
                        }
                    }
                        
                    System.out.println(twitterResultsData[i]);
                }
            } else {
                twitterResultsData[2] = StringUtils.substringBetween(twitterResultsData[0], twitterResultsData[1], "</ol>");
            }
        } else {
            System.out.println("[-] Sorry, no matches were found when searching for " + targetName + " on Twitter.");
        }
        //Finished parsing twitter results
        System.out.println("[~] Checking Facebook for the name " + targetName);
        url = "https://www.facebook.com/search.php?q=" + formattedTargetName;
        source = SSLSocketClient(url);
        //Parse facebook results here
        System.out.println("[~] Checking WhitePages for the name " + targetName);
        request.setURL("http://www.whitepages.com/name/" + formattedTargetNameWP + "/");
        source = request.get(); //Using normal HTTP wrappers since it isn't HTTPS
        //Parse whitepages results here
    }
    public static String SSLSocketClient(String parameters) throws Exception {
        String endhtml = "</html>";
        Boolean found = false;
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
        
        while (((line = in.readLine()) != null) && (found == false)){
                  source += line;
                  found = source.contains(endhtml);
        }
        out.close();
        in.close();
        return source;
    }
}