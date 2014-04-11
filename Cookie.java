import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author Isonyx
 * @date 2/21/2013
 *
 * Cookie class designed for HTTPRequest.java HTTPWrapper
 * 
 */

public class Cookie {
        private List<String> myCookies = new ArrayList<String>();
        String myCookie;
        
        public Cookie() {
                this.myCookie = "";
        }
        
        public Cookie(String cookies) {
                this.myCookie = cookies;
                setCookies(cookies);
        }
        
        public void setCookies(String cookies) {
                String storage[];
                this.myCookie = cookies;
                
                if (cookies.contains(";")) {
                        storage = cookies.split("; ");
                        for (String cookie : storage) this.myCookies.add(cookie);
                } else {
                        this.myCookies.add(cookies);
                }
        }
        
        public String getCookie(String cookie) {
                String theCookie = "";
                String storage[];
                if (this.myCookies.isEmpty()) return theCookie;
                for (String cookies : this.myCookies) {
                        storage = cookies.split("=");
                        if (storage[0].contains(cookie)) { theCookie = cookies; }
                }
                return theCookie;
        }
        
        public void updateCookies(URLConnection connection) {
                this.clearCookies();
                int iCounter = 1;
                String sKey, sField, aCookie;
                while ((sKey = connection.getHeaderFieldKey(iCounter)) != null) {
                        if (sKey.equals("Set-Cookie")) {
                                sField = connection.getHeaderField(iCounter);
                                StringTokenizer tokenizer = new StringTokenizer(sField, ",");
                                while (tokenizer.hasMoreTokens()) {
                                        String nextToken = tokenizer.nextToken();
                                        aCookie = (nextToken.indexOf(";") > -1) ? nextToken.substring(0, nextToken.indexOf(";")) : nextToken;
                                        int iIndex = aCookie.indexOf("=");
                                        if (iIndex > -1) {
                                                this.myCookies.add(aCookie.substring(0, iIndex) + "=" + aCookie.substring(iIndex + 1));
                                                this.myCookie = arrayListToString(this.myCookies, "; ");
                                        }
                                }
                        }
                        iCounter++;
                }
        }
        
        public void removeCookie(String cookie) {
                String returnCookies = "";
                String[] storage;
                
                this.myCookie = cookie;
                
                if (this.myCookies.isEmpty()) { return; }
                
                for (int i = 0; i < this.myCookies.size(); i++) {
                        storage = myCookies.get(i).split("=");
                        if (!storage[0].equals(cookie)) returnCookies += myCookies.get(i) + "; ";
                }
                returnCookies = returnCookies.substring(0, returnCookies.length() - 2);
                this.clearCookies();
                this.setCookies(returnCookies);
        }
        
        public void clearCookies() {
                this.myCookies.clear();
        }
        
        /**
         * arrayListToString(List<String> array, String delimiter)
         * 
         * Array to String function for internal use only.
         */
        private String arrayListToString(List<String> array, String delimiter) {
                String sReturn = "";
                
                for (int i = 0; i < array.size(); i++) {
                        sReturn += array.get(i) + delimiter;
                }

                return sReturn.substring(0, sReturn.length() - delimiter.length());
        }
        
        public String toString() {
                return this.myCookie;
        }
}