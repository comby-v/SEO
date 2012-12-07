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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import seoproject.Process;

/**
 *
 * @author Mika
 */
public class Process
{
    private String content;
  
    public Process(String str)
    {
        this.content = str;
    }
    
    /* Lemmatisation */
    public ArrayList<String> lemmatisation()
    {
        ArrayList<String> list = new ArrayList<String>();
        StringReader str_reader = new StringReader(this.content);
        StreamTokenizer streamTokenizer = new StreamTokenizer(str_reader);
        try
        {            
            while (streamTokenizer.nextToken() != StreamTokenizer.TT_EOF) // Tant que c'est pas la fin de fichier
            {
                if (streamTokenizer.ttype == StreamTokenizer.TT_WORD) // Si c'est un mot
                {
                    char first_letter = streamTokenizer.sval.charAt(0); // Premiere lettre du mot
                    String dico = "/lib/Dico/dico_"+first_letter+".txt"; // Chemin vers le dico
                    try (BufferedReader buf_reader = new BufferedReader(new FileReader(dico)))
                    {
                        String line;
                        while ((line = buf_reader.readLine()) != null) // On lit le dico ligne par ligne
                        {
                            if (line.contains(streamTokenizer.sval)) // Si la ligne contient le mot recherché
                            {
                                String[] split = line.split("[ \t]");
                                list.add(split[2]);
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
        catch (IOException ex)
        {
            Logger.getLogger(Process.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
