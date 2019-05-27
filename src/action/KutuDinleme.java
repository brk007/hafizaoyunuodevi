/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;
import controller.OyunYonetici;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author bendo
 */
public class KutuDinleme extends JToggleButton implements ActionListener , ChangeListener{
  
    private KutuDinleme es;
    private OyunYonetici om;
    public final int deger; 
    public int durum = 0;
    private boolean lock = false;
    public KutuDinleme(int deger , OyunYonetici om){
        this.deger = deger;
        this.om = om;
        addActionListener(om);
    }
    public void setEs(KutuDinleme es){
        this.es = es;
        addActionListener(this);
        addChangeListener(this);
    }  
    
    public void goster(boolean gosterilsinMi){
        if(gosterilsinMi){        
            this.setText(deger + "");
        }else{
            this.setText("");
        }
    }
      
    @Override 
    public void setEnabled(boolean e){
        if(e == false){
            lock = true;
            super.setEnabled(e);
            this.setText(deger + "");
        }else{
            lock = false;
            this.setText("");
            super.setEnabled(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {    
        if(lock) return;
        if(this.getText().equals("")){
            durum = 1;
        }else{
            durum = 0;
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(lock) return;
        if(this.durum == 0) this.setText("");
        else this.setText(deger + "");
    }
}
