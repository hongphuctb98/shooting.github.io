package peson11;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Shooting2 {
	public static void main(String[] args) {
    	Shooting2Frame f = new Shooting2Frame("Shooting2");
        f.setBackground(Color.black);
        f.setForeground(Color.white);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
	}
}
class Shooting2Frame extends JFrame implements KeyListener,Runnable{
	Image myship, enemy, bullet;
	int mx,my, ex,ey,bx,by;
	static int xsize=300, ysize=400,mssize=60,esize=60, bsize=8;
	Thread thread;
	Image offImage;
	boolean keyLeft, keyRight,keySpace;
	boolean bflag;
	public Shooting2Frame(String title) {
		super(title);
		Toolkit tk=Toolkit.getDefaultToolkit();
		myship=tk.getImage("myship.gif");
		enemy=tk.getImage("enemy.gif");
		bullet=tk.getImage("bullet.gif");
		keyLeft=keyRight=keySpace= false;
		mx=(xsize-mssize)/2;
		my=ysize-mssize-10;
		ex=(int)(Math.random()*(xsize-esize));
		ey=-esize;
		setSize(xsize,ysize);
		addKeyListener(this);
		requestFocusInWindow();
		thread = new Thread(this);
		thread.start();
	}
	public void paint(Graphics g)
	{
		if(offImage==null) offImage=createImage(xsize,ysize);
		g.drawImage(offImage,0,0,this);
	}
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT: keyLeft= true; break;
		case KeyEvent.VK_RIGHT: keyRight=true; break;
		case KeyEvent.VK_SPACE: keySpace=true; break;
		}
	}
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT: keyLeft= false; break;
		case KeyEvent.VK_RIGHT: keyRight=false; break;
		case KeyEvent.VK_SPACE: keySpace=false; break;
		}
	}
	public void keyTyped(KeyEvent e) {}
	public void run() {
		Thread thisThread= thread.currentThread();
		while (thread==thisThread) {
			if(offImage!=null) gameMain();
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
		if(ey>ysize) {
			ey=-esize;
			ex=(int)(Math.random()*(xsize-esize));
		}
		if (keyLeft) {
			mx -= 8;
			if (mx < 0) mx = 0;  
		}

		if (keyRight) {
			mx += 8;
			if (mx > xsize - mssize) mx = xsize - mssize; 
		}
		if(keySpace) {
			if(!bflag) {
				bx=mx+(mssize-bsize)/2;
				by=my;
				bflag=true;
			}
		}
		if(bflag) {
			if(by<0) bflag=false;
			else by-=8;
		}
		Graphics gv = offImage.getGraphics();
		
		gv.clearRect(0, 0, xsize, ysize);
		gv.drawImage(enemy,ex,ey,this);
		gv.drawImage(myship, mx, my, this);
		if(bflag) gv.drawImage(bullet,bx,by,this);
		repaint();
	}
}