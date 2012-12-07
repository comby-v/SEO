/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seoproject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
            
            String tab_url[] =
            {"http://fr.wikipedia.org/wiki/Philosophie",
             "http://fr.wikipedia.org/wiki/Booba",
             "http://fr.wikipedia.org/wiki/Paris",
             "http://fr.wikipedia.org/wiki/Pain",
             "http://fr.wikipedia.org/wiki/Viking",
             "http://fr.wikipedia.org/wiki/France",
             "http://fr.wikipedia.org/wiki/Humanit%C3%A9",
             "http://fr.wikipedia.org/wiki/Politique",
             "http://fr.wikipedia.org/wiki/Caribou",
             "http://fr.wikipedia.org/wiki/%C3%89tat",
             "http://fr.wikipedia.org/wiki/Apple",
             "http://fr.wikipedia.org/wiki/Sport",
             "http://fr.wikipedia.org/wiki/Espace_%C3%A9conomique_europ%C3%A9en",
             "http://fr.wikipedia.org/wiki/Football",
             "http://fr.wikipedia.org/wiki/Telephone",
             "http://fr.wikipedia.org/wiki/Tabac"
            };
             
           try
            {
               FileWriter fstream = new FileWriter("corpus.txt");
               BufferedWriter out = new BufferedWriter(fstream);
                for (int i = 0; i < tab_url.length; i++)
                {
                    String url = tab_url[i];
                    String content = getTextOnly (url);
                    HashMap list_lem = Process.lemmatisation(content);
                    for (String mot : (String[])list_lem.keySet().toArray())
                    {
                         // Create file 

                        out.write(mot + " ");
                        //Close the output stream
                        
                    }
                }
                out.close();
                String content1 = getTextOnly(args[0]);
                String content2 = getTextOnly(args[1]);
                String[] split = content1.split(" ");
                System.out.println (split.length);
                int count = 0;
                for (int i = 0; i < split.length; i++)
                {
                    if (!split[i].equals(""))
                    {
                        count++;
                    }
                }

                HashMap res1 = Process.lemmatisation(content1);
                HashMap res2 = Process.lemmatisation(content2);
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
