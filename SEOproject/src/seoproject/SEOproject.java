/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seoproject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import seoproject.Process;

/**
 *
 * @author Vince
 */
public class SEOproject
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        if (args.length == 2)
        {
            try
            {
                String content1 = getTextOnly(args[0]);
                String content2 = getTextOnly(args[1]);
                Process proc1 = new Process(content1);
                Process proc2 = new Process(content2);
                ArrayList<String> res1 = proc1.lemmatisation();
                ArrayList<String> res2 = proc2.lemmatisation();
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
    
    public static String getTextOnly(String url)
    {
        Document doc;
        String result = "";
         try 
         {
             doc = Jsoup.connect(url).get();
             Elements body = doc.select("body");
             Elements list_balise = body.select("*");
              for (Element element : list_balise)
              {
                  if (element.hasText())
                  {
                     result += " "+(element.ownText());
                  }
              }
             return result;

         } catch (IOException ex)
         {
             Logger.getLogger(SEOproject.class.getName()).log(Level.SEVERE, null, ex);
         }

        return "";
    }
}
