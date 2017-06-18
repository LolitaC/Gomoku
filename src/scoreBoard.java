import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class scoreBoard extends JPanel{
	
	Image image;//»ÀŒÔÕº±Í
	
	public scoreBoard(){
		
		
		try {

            image=ImageIO.read(new File("resource/user.png"));

        } catch (Exception e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        }
	}
	
	public void paint(Graphics g){
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
	}
}
