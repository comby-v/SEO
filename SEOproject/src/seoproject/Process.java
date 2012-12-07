/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seoproject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import seoproject.Process;

/**
 *
 * @author Mika
 */
public class Process
{
    /* Lemmatisation */
    public static HashMap lemmatisation(String str)
    {
        HashMap map = new HashMap();
        StringReader str_reader = new StringReader(str);
        StreamTokenizer streamTokenizer = new StreamTokenizer(str_reader);
        try
        {
            while (streamTokenizer.nextToken() != StreamTokenizer.TT_EOF) // Tant que c'est pas la fin de fichier
            {
                if (streamTokenizer.ttype == StreamTokenizer.TT_WORD) // Si c'est un mot
                {
                    String word = uniform(streamTokenizer.sval);
                    Boolean isNumber = true;
                    try
                    {
                        Integer.parseInt(word);
                    }
                    catch (Exception e)
                    {
                        isNumber = false;
                    }
                    if (!word.isEmpty() && !isNumber)
                    {
                        System.out.println("word : "+word);
                        char first_letter = word.charAt(0); // Premiere lettre du mot
                        String dico = "lib/Dico/dico_"+first_letter+".txt"; // Chemin vers le dico
                        try (BufferedReader buf_reader = new BufferedReader(new FileReader(dico)))
                        {
                            String line;
                            while ((line = buf_reader.readLine()) != null) // On lit le dico ligne par ligne
                            {
                                if (line.contains(streamTokenizer.sval)) // Si la ligne contient le mot recherché
                                {
                                    String[] split = line.split("[ \t]");
                                    if (map.containsKey(split[1]))
                                    {
                                        int occ = (int)map.get(split[1]);
                                        map.put(split[1], ++occ);
                                    }
                                    else
                                    {
                                        map.put(split[1], 1);
                                    }
                                    break; // On s'arrete de lire le dico
                                }
                            }
                        }
                        catch (FileNotFoundException ex)
                        {
                            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }
    
    public static float tf_idf(HashMap map)
    {
        return 0.0f;
    }
    
    public static String uniform(String r)
    {
        r = r.toLowerCase();
        r = r.replaceAll("\\s", "");
        r = r.replaceAll("[àáâãäå]", "a");
        r = r.replaceAll("æ", "ae");
        r = r.replaceAll("ç", "c");
        r = r.replaceAll("[èéêë]", "e");
        r = r.replaceAll("[ìíîï]", "i");
        r = r.replaceAll("ñ", "n");                            
        r = r.replaceAll("[òóôõö]", "o");
        r = r.replaceAll("œ", "oe");
        r = r.replaceAll("[ùúûü]", "u");
        r = r.replaceAll("[ýÿ]", "y");
        r = r.replaceAll("\\W", "");
        return r;
    }
}
