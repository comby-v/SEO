/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seoproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
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
                        char first_letter = word.charAt(0); // Premiere lettre du mot
                        String dico = "lib/Dico/dico_"+first_letter+".txt"; // Chemin vers le dico
                        File f = new File(dico);
                        InputStream in = new FileInputStream(f);
                        try (BufferedReader buf_reader = new BufferedReader(new InputStreamReader(in, "ISO-8859-15")))
                        {
                            String line;
                            while ((line = buf_reader.readLine()) != null) // On lit le dico ligne par ligne
                            {
                                if (line.contains(streamTokenizer.sval)) // Si la ligne contient le mot recherché
                                {
                                    String[] split = line.split("[ \t]");
                                    if (map.containsKey(split[1]))
                                    {
                                        double occ = (double)map.get(split[1]);
                                        map.put(split[1], ++occ);
                                    }
                                    else
                                    {
                                        map.put(split[1], (double) 1);
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
    
    public static HashMap tf_idf(HashMap doc_comp, HashMap corpus, ArrayList<ArrayList<String>> docs)
    {
        Set keys = doc_comp.keySet();
        Iterator it1 = keys.iterator();
        Iterator it2 = keys.iterator();
        double max_occ = 0; // occ max ds doc_comp
        while (it1.hasNext())
        {
            String key = (String) it1.next();
            double occ = (double)doc_comp.get(key);
            max_occ = max_occ < occ ? occ : max_occ;
        }
        while (it2.hasNext())
        {
            String key = (String) it2.next();
            double occ = (double)doc_comp.get(key);
            double tf = occ / max_occ;
            double nx = 0;
            for (ArrayList<String> doc : docs)
            {
                for (String word : doc)
                {
                    if (word.equals(key))
                    {
                        nx++;
                        break;
                    }
                }
            }
            if (nx != 0)
            {
                double ratio = (double)docs.size()/nx;
                double idf = Math.log10(ratio) + 1;
                corpus.put(key, (double)tf*idf);
            }
        }
        return corpus;
    }
    
    public static double cos_salton(HashMap map1, HashMap map2)
    {
        Set keys = map1.keySet();
        Iterator it = keys.iterator();
        double d1d2 = 0;
        double d1 = 0;
        double d2 = 0;
        while (it.hasNext())
        {
            String key = (String) it.next();
            double val1 = (double)map1.get(key);
            double val2 = (double)map2.get(key);
            d1d2 += (val1 * val2)*(val1*val2);
            d1 += val1*val1;
            d2 += val2*val2;
        }
        return (Math.sqrt(d1d2)/(Math.sqrt(d1)*Math.sqrt(d2)));
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
