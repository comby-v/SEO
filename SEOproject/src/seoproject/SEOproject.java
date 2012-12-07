/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seoproject;

import com.sun.servicetag.SystemEnvironment;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 *
 * @author Vince
 */
public class SEOproject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        if (args.length == 2)
        {
            try
            {
               URL url1 = new URL (args[0]);
               URL url2= new URL(args[1]);
               String content1 = loadUrl(url1);
               String content2 = loadUrl(url2);
               Process proc1 = new Process(content1);
               Process proc2 = new Process(content2);
               ArrayList<String> res1 = proc1.lemmatisation();
               ArrayList<String> res2 = proc2.lemmatisation();
               
               System.out.println (res1);
                
                
               
            }
            catch (Exception e)
            {
                System.out.println ("Exception :");
                System.out.println(e);
            }

            
            
        }
        else
        {
           System.out.println("Vous devez avoir 2 arguments");
        }
        
        
    }
    
    
  public static String loadUrl(URL url) throws IOException {
    InputStream stream = null;
    try
    {
      stream = url.openStream();
      return loadStream(stream);
    }
    finally
    {
      if (stream != null)
      {
        try
        {
          stream.close();
        } 
        catch (IOException e)
        {
        }
      }
    }
  }
    
    public static String loadStream(InputStream stream) throws IOException
    {
      Reader reader = new InputStreamReader(stream, Charset.forName("UTF-8"));
      char[] buffer = new char[1024];
      int count;
      StringBuilder str = new StringBuilder();
      while ((count = reader.read(buffer)) != -1)
      {
        str.append(buffer, 0, count);
      }
      return str.toString();
    }
}
