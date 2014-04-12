import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.*;

/**
 * 
 * @author Isonyx
 * @date 2/21/2013
 * 
 * HTTPWrapper designed to handle specific header values as well as cookies.
 * 
 */

public class HTTPRequest {
        public Cookie myCookie;
        private HttpURLConnection URLConnection;
        private URL myURL;
        private String myUserAgent;
        private String myAcceptType;
        private String myAcceptLanguage;
        private String myAcceptCharset;
        private String myKeepAlive;
        private String myConnection;
        private String myReferer;
        
        public HTTPRequest(String URL) {
                this.Initialize();
                try { this.myURL = new URL(URL); }
                catch (Exception e) { e.printStackTrace(); }
        }
        
        public HTTPRequest() {
                this.Initialize();
        }
        
        private void Initialize() {
                this.myCookie = new Cookie();
                this.URLConnection = null;
                this.myUserAgent = "";
                this.myAcceptType = "";
                this.myAcceptLanguage = "";
                this.myAcceptCharset = "";
                this.myKeepAlive = "";
                this.myConnection = "";
                this.myReferer = "";
                
        }
        public void setURL(String URL) {
                try { this.myURL = new URL(URL); } 
                catch (MalformedURLException e) { e.printStackTrace(); }
        }
        
        public URL getURL() {
                return myURL;
        }
        
        public void setUserAgent(String agent) {
                this.myUserAgent = agent;
        }
        
        public String getUserAgent() {
                return myUserAgent;
        }
        
        public void setAccept(String type, String language, String charset) {
                this.myAcceptType = type;
                this.myAcceptLanguage = language;
                this.myAcceptCharset = charset;
        }
        
        public String getAccept() {
                return myAcceptType;
        }
        
        public String getAcceptLanguage() {
                return myAcceptLanguage;
        }
        
        public String getAcceptCharset() {
                return myAcceptCharset;
        }
        
        public void setKeepAlive(String keepAlive) {
                this.myKeepAlive = keepAlive;
        }
        
        public String getKeepAlive() {
                return myKeepAlive;
        }
        
        public void setConnection(String connection) {
                this.myConnection = connection;
        }
        
        public String getConnection() {
                return myConnection;
        }
        
        public void setReferer(String referer) {
                this.myReferer = referer;
        }
        
        public String getReferer() {
                return myReferer;
        }
        
        public HttpURLConnection getURLConnection() {
                return this.URLConnection;
        }
        
        public String get(String link) {
                this.setURL(link);
                return this.get();
        }
        
        public String get() {
                try {
                        //Set Request Information
                        this.URLConnection = (HttpURLConnection)myURL.openConnection();
                        this.URLConnection.setRequestMethod("GET");
                        this.URLConnection.setAllowUserInteraction(false);
                        this.URLConnection.setDoOutput(false);
                        this.URLConnection.setInstanceFollowRedirects(false);
                        
                        //Set Request Properties
                        this.URLConnection.setRequestProperty("User-Agent", this.getUserAgent());
                        this.URLConnection.setRequestProperty("Accept", this.getAccept());
                        this.URLConnection.setRequestProperty("Accept-Language", this.getAcceptLanguage());
                        this.URLConnection.setRequestProperty("Accept-Charset", this.getAcceptCharset());
                        this.URLConnection.setRequestProperty("Keep-Alive", this.getKeepAlive());
                        this.URLConnection.setRequestProperty("Connection", this.getConnection());
                        this.URLConnection.setRequestProperty("Referer", this.getReferer());
                        
                        //Set Cookies
                        if (!myCookie.toString().isEmpty()) { this.URLConnection.setRequestProperty("Cookie", myCookie.toString()); }
                        
                        //Connection
                        this.URLConnection.connect();
                        
                        //Read Response
                        StringBuffer sReturn = new StringBuffer();
                        String sTemporary = "";
                        BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream)this.URLConnection.getContent()));
                        while ((sTemporary = reader.readLine()) != null) sReturn.append(sTemporary + "\r");
                        
                        //Clean Up
                        reader.close();
                        myCookie.updateCookies(this.URLConnection);
                        this.setReferer(myURL.toString());
                        
                        return sReturn.toString();
                } catch (Exception e) { 
                        e.printStackTrace(); 
                        return null;
                } finally {
                        if (this.URLConnection != null) { this.URLConnection.disconnect(); }
                }
        }
        
        public String post(String link, String parameters) {
                this.setURL(link);
                return this.post(parameters);
        }
        
        public String post(String parameters) {
                try {
                        //Set Request Information
                        this.URLConnection = (HttpURLConnection)myURL.openConnection();
                        this.URLConnection.setRequestMethod("POST");
                        this.URLConnection.setAllowUserInteraction(false);
                        this.URLConnection.setDoOutput(true);
                        this.URLConnection.setInstanceFollowRedirects(false);
                        
                        //Set Request Properties
                        this.URLConnection.setRequestProperty("User-Agent", this.getUserAgent());
                        this.URLConnection.setRequestProperty("Accept", this.getAccept());
                        this.URLConnection.setRequestProperty("Accept-Language", this.getAcceptLanguage());
                        this.URLConnection.setRequestProperty("Accept-Charset", this.getAcceptCharset());
                        this.URLConnection.setRequestProperty("Keep-Alive", this.getKeepAlive());
                        this.URLConnection.setRequestProperty("Connection", this.getConnection());
                        this.URLConnection.setRequestProperty("Referer", this.getReferer());
                        this.URLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        this.URLConnection.setRequestProperty("Content-Length", Integer.toString(parameters.length()));
                        
                        //Set Cookies
                        if (!myCookie.toString().isEmpty()) { this.URLConnection.setRequestProperty("Cookie", myCookie.toString()); }
                        
                        //Write Data
                        OutputStreamWriter writer = new OutputStreamWriter(this.URLConnection.getOutputStream());
                        writer.write(parameters);
                        writer.flush();
                        writer.close();
                        
                        //Read Result
                        String sResult = "";
                        String sLine = null;
                        BufferedReader reader = new BufferedReader(new InputStreamReader(new DataInputStream(this.URLConnection.getInputStream())));
                        while ((sLine = reader.readLine()) != null) sResult += sLine + "\n";
                        
                        //Clean Up
                        reader.close();
                        myCookie.updateCookies(this.URLConnection);
                        this.setReferer(myURL.toString());
                        
                        return sResult;
                } catch (Exception e) {
                        e.printStackTrace(); 
                        return null;
                } finally {
                        if (this.URLConnection != null) { this.URLConnection.disconnect(); }
                }
        }
        
}