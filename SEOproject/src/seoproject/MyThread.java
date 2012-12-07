/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seoproject;

/**
 *
 * @author Mika
 */
public class MyThread extends Thread
{
    private String str;
  
    public MyThread(String str)
    {
        this.str = str;
    }
    
    @Override
    public void run()
    {
      // faire quelque chose avec str
    }
}
