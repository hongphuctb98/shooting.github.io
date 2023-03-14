package peson11;



import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Shooting1 {
	public static void main(String[] args) {
    	ShootingFrame f = new ShootingFrame("Shooting1");
        f.setBackground(Color.black);
        f.setForeground(Color.white);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
	}
}
class ShootingFrame extends JFrame implements KeyListener,Runnable{
    Image myship, enemy;
    int mx, my, ex,ey; 
    static int xsize = 300, ysize = 400, mssize=60, esize=60;
    Thread thread;
    Image offImage;
    boolean keyLeft, keyRight; 
	public ShootingFrame(String title){
        super(title);
        Toolkit tk = Toolkit.getDefaultToolkit();
        
        myship = tk.getImage("myship.gif");        
        keyLeft = keyRight = false;
        mx = (xsize-mssize) / 2;
        my = ysize - mssize -10;
        
        enemy= tk.getImage("enemy.gif");
        ex=(int)(Math.random()*(xsize-esize));
        ey=-esize;
        
        setSize(xsize, ysize);
        addKeyListener(this);
        requestFocusInWindow();
        thread = new Thread(this);
        thread.start();
    }
    public void paint(Graphics g){
    	
        if (offImage==null) offImage = createImage(xsize, ysize);
    	g.drawImage(offImage, 0, 0, this);
    }
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			
			case KeyEvent.VK_LEFT: keyLeft = true; break;
			
			case KeyEvent.VK_RIGHT: keyRight = true; break;
		}
	}
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			
			case KeyEvent.VK_RIGHT: keyRight = false; break;
		}
	}
	
	public void keyTyped(KeyEvent e) {}

    public void run() {
        Thread thisThread = Thread.currentThread();
        while (thread == thisThread) {
        	
            if (offImage!=null) gameMain();
            try {
                Thread.sleep(20);
            }
            catch(InterruptedException e) {
                break;
            }
        }
    } 

	void gameMain() {
		
		ey+=8;
		if(ey>=ysize) {
			ex=(int)(Math.random()*(xsize-esize));
			ey=-esize;
		}
		
		if (keyLeft) {
			mx -= 8;
			if (mx < 0) mx = 0;  
		}
		
		if (keyRight) {
			mx += 8;
			if (mx > xsize - mssize) mx = xsize - mssize;
		}
		Graphics gv = offImage.getGraphics();
		
		gv.clearRect(0, 0, xsize, ysize);
		gv.drawImage(enemy,ex,ey,this);
		gv.drawImage(myship, mx, my, this);
		repaint();
	}

}