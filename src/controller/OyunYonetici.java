/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import action.KutuDinleme;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;

/**
 *
 * @author bendo
 */
public class OyunYonetici implements ActionListener{ 
    
    private JPanel panel;
    private KutuDinleme[] kutular;
    public int puan;
    public int durum = 0; // 0:Göster 1:oyun oyuna
   
    private String layout;
    private JLabel puanText;
    private JLabel timerText;
    private Timer timer;
    private long start = 0;
    public boolean oyunBitti = false;
    
    public OyunYonetici(JPanel panel,String layout, JLabel puanText, JLabel timerText){
        this.panel = panel;
        this.layout = layout;
        System.out.println(layout);
        this.puanText = puanText;
        this.timerText = timerText;
        timer = new Timer();
    }
     
    
    public void sayacibaslat(){
        start = System.currentTimeMillis();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                timerText.setText(((System.currentTimeMillis() - start)/ 1000)/3600+":"+""+(((System.currentTimeMillis() - start)/ 1000)/60)%60+":"+((System.currentTimeMillis() - start)/ 1000)%60+"");
                if(oyunBitti)
                timer.cancel();
            }
        };
        timer.schedule(task , 1000, 500);
    }
    
    public void oyunMantiginiUygula(){
        KutuDinleme[] secilenOyunKutulari = new KutuDinleme[kutular.length];
        int secilenIndex = 0;
        for(int i = 0 ; i < kutular.length ; i++){
            if(kutular[i].durum == 1)
                secilenOyunKutulari[secilenIndex++] = kutular[i];
        }
        
        if(secilenIndex >= 2){
            if(secilenOyunKutulari[0].durum == 1 && secilenOyunKutulari[1].durum == 1 && 
                 secilenOyunKutulari[0].deger == secilenOyunKutulari[1].deger){

                 this.puan += 50;
                 secilenOyunKutulari[0].setEnabled(false);               
                 secilenOyunKutulari[1].setEnabled(false);
            }else{
                  this.puan -= 20;
            }
        
             for(int i = 0 ; i < secilenIndex ; i++){
                 secilenOyunKutulari[i].durum = 0;
                 secilenOyunKutulari[i].setSelected(false);   
             }
        }
 
        oyunBitti = true;
        for(int i = 0 ; i < kutular.length ; i++){
            if(kutular[i].isEnabled()){
                oyunBitti = false;
                break;
            }
        }
       puanText.setText(puan + "");
        if(oyunBitti){
            JOptionPane.showMessageDialog(panel, "Oyun bitti");
            timer.cancel();
        }
    }
    
    public void kutulariolusturveyerlestir(int kacKutu, String layout_turu){
        if(kacKutu % 2 != 0) kacKutu++;
         
        panel.removeAll();
        kutular = null;
        kutular = new KutuDinleme[kacKutu];
        int id = 0;
        for(int i = 0 ; i < kacKutu  ; i += 2){
  
            KutuDinleme kutu1 = new KutuDinleme(id ,this );
            KutuDinleme kutu2 = new KutuDinleme(id ,this );
            id++;
            kutu1.setEs(kutu2);
            kutu2.setEs(kutu1);
            kutular[i] = kutu1;
            kutular[i].setPreferredSize(new Dimension(50, 50));
            kutular[i + 1] = kutu2;
            kutular[i+1].setPreferredSize(new Dimension(50, 50));
        }
        
        diziyikaristir(kutular);
        
     if(layout.equalsIgnoreCase("GridLayout")){           
           System.out.println(layout);
           panel.setLayout(new GridLayout(2,4));
        }
     else if (layout.equalsIgnoreCase("FlowLayout")) {           
           panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
       }    
    
        
        for(int i = 0 ; i < kacKutu ; i++){
            panel.add(kutular[i]);
            
        }  
        panel.revalidate();
        panel.repaint();
    }
    
    private void diziyikaristir(KutuDinleme[] array)
    {
        int index;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            if (index != i)
            {
                KutuDinleme o = array[i];
                array[i] = array[index];
                array[index] = o;
            }
        }
    }   
    
    public void kutularigoster(boolean gosterilsinMi){    
        for(int i = 0 ; i < kutular.length;i++){
            kutular[i].goster(gosterilsinMi);
        }
    }

    public void kutulariaktifet(boolean aktifedilsinMi){    
        for(int i = 0 ; i < kutular.length;i++){
            kutular[i].setEnabled(aktifedilsinMi);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.durum == 1){
           oyunMantiginiUygula();
           
        }
    }      
    
    
}

   

