/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seoproject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
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
    public static void main(String[] args)
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
        
         
        // TODO code application logic here
        if (args.length == 2)
        {
           try
            {
               //MakeCorpus(tab_url);
                ArrayList<ArrayList<String>> list_doc = new ArrayList ();
                HashMap corpus_map = new HashMap ();

                for (int i = 0; i < tab_url.length; i++)
                {
                    ArrayList list_mot = new ArrayList ();
                    String corpus_file = "corpus_" + i + ".txt";
                    try
                    {
                       File f = new File(corpus_file);
                       InputStream in = new FileInputStream(f);
                       BufferedReader buf_reader = new BufferedReader(new InputStreamReader(in));
                       String line;
                        while ((line = buf_reader.readLine()) != null) 
                        {
                           String[] temp_list_word= line.split(" ");
                           for (String word : temp_list_word )
                           {
                                list_mot.add(word);
                                if (!corpus_map.containsKey(word))
                                 {
                                     corpus_map.put(word, (double)0);
                                 }
                           }
                        }
                        list_doc.add(list_mot);
                    } 
                    catch (FileNotFoundException ex) {
                        Logger.getLogger(SEOproject.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                String content1 = getTextOnly(args[0]);
                String content2 = getTextOnly(args[1]);

                HashMap res1 = Process.lemmatisation(content1);
                HashMap res2 = Process.lemmatisation(content2);
                
                HashMap vecteur1 = Process.tf_idf(res1, corpus_map, list_doc);
                HashMap vecteur2 = Process.tf_idf(res2, corpus_map, list_doc);
                double cosCoef = Process.cos_salton(vecteur1, vecteur2);
                System.out.println(cosCoef);
            }
            catch (Exception e)
            {
                Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, e);
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
    
    public void loadCorpus(String[] tab_url) throws IOException
    {

                
    }
    
    public static void  MakeCorpus(String[] tab_url) throws IOException
    {
        try
        {
         for (int i = 0; i < tab_url.length; i++)
         {
             String url = tab_url[i];
             String content = getTextOnly (url);
             HashMap map = Process.lemmatisation(content);
             Set cles = map.keySet();
             Iterator it = cles.iterator();
             FileWriter fstream = new FileWriter("corpus_"+i+".txt");
             BufferedWriter out = new BufferedWriter(fstream);
             while (it.hasNext())
             {
                 String cle = (String) it.next();
                 out.write(cle + " ");
                 //Close the output stream
             }
            out.close();
         }
        }
        catch (Exception e)
        {
            
        }
    }
}
