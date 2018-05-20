import java.awt.Graphics;

import javax.swing.JPanel;

public class DownBallPanel extends JPanel implements Runnable {

	// int y=0;
	// int flag=0;
	// public void paint(Graphics g) {
	// // TODO Auto-generated method stub
	// super.paint(g);
	// g.fillOval(30, y, 40, 40);
	// if(flag==0){
	// if(y>=320){
	// flag=1;
	// y=320;
	// }else{
	// y=y+40;
	// }
	// }else{
	// if(y<=0){
	// flag=0;
	// y=0;
	// }else{
	// y=y-40;
	// }
	// }
	// try {
	// Thread.sleep(500);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// super.repaint();
	//
	// }
	int y=0;
	int flag=0;
	int x=0;
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		g.fillOval(x, y, 40, 40);
		//super.repaint();
	}

	public void run() {
       while(true){
    	   if(flag==0){
    		 
        	   if(y>=320){
        		   y=320;
        		   flag=1;
        	   }else{
        		   y++;
        		   x++;
        	   }
    	   }else{
    		   if(y<=0){
    			   flag=0;
    			   y=0;
    		   }else{
    			   y--;
    			   x--;
    		   }
    	   }
    	  
    	   try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	   repaint();
       }
	}

}
