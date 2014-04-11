import java.util.Scanner;
class DoxMaster {
    public static void main(String[] args) {
        String readme0 = "Welcome to Dox Master.";
        String readme1 = "Stop trying to decompile my program.";
        String readme2 = "Coded by weebs.";
        HTTPRequest request = new HTTPRequest();
        Scanner name = new Scanner(System.in);
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
        request.setURL("http://www.facebook.com/search.php?q=" + formattedTargetName);
        source = request.get(); //Grab the results
        System.out.println(source);
    }
}