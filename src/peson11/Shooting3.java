package peson11;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Shooting3 {
	public static void main(String[] args) {
		Shooting3Frame f=new Shooting3Frame("Shooting3");
		f.setBackground(Color.black);
		f.setForeground(Color.white);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}
class Shooting3Frame extends JFrame implements KeyListener, Runnable{
	Image myship, enemy, bullet;
	int mx,my, ex,ey,bx,by;
	int scene, score;
	
	static int xsize=300,ysize=400,mssize=60,esize=60,bsize=8;
	Thread thread;
	Image offImage;
	boolean keyLeft, keyRight,keyUp,keyDown, keySpace; 
	boolean bflag;
	public Shooting3Frame(String title){
        super(title);
        Toolkit tk = Toolkit.getDefaultToolkit();
        myship = tk.getImage("myship.gif");
        enemy = tk.getImage("enemy.gif");
        bullet = tk.getImage("bullet.gif");
        keyLeft = keyRight =keyUp=keyDown= false;
        mx = (xsize-mssize) / 2; 
        my = ysize - mssize -10; 
        ex=(int)(Math.random()*(xsize-esize));
        ey=-esize;
        scene=1;
    	score=0;
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
			
			case KeyEvent.VK_UP: keyUp=true; break;
			
			case KeyEvent.VK_DOWN: keyDown=true; break;
			
			case KeyEvent.VK_SPACE: keySpace=true; break;
		}
	}
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			
			case KeyEvent.VK_UP: keyUp=false; break;
			
			case KeyEvent.VK_DOWN: keyDown=false; break;
			
			
			case KeyEvent.VK_SPACE: keySpace=false; break;
		}
	}
	
	public void keyTyped(KeyEvent e) {}

    public void run() {
        Thread thisThread = Thread.currentThread();
        while (thread == thisThread) {
        
            if (offImage!=null) {
            	switch(scene) {
            	case 1: gameMain(); break;
            	case 2: gameover(); break;
            	}
            };
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
		if(keyUp) {
			my-=8;
			if(my<0) my=0;
		}
		if(keyDown) {
			my+=8;
			if(my>ysize-mssize) my=ysize-mssize;
		}
		if	(keySpace) {
			if(!bflag) {
				bx=mx+(mssize-bsize)/2;
				by=my;
				bflag=true;
			}
		}
		if(bflag) {
			if(by<0) {				
				bflag=false;
			}
			else by-=8;
		}
		if(bflag) {
			if(bx<ex+esize&&bx+bsize>ex&&by<ey+esize&&by+bsize>ey) {
				score+=100;
				ex=(int)(Math.random()*(xsize-esize));
				ey=-esize;
				bflag=false;
			}
		}
		if(mx<ex+esize&&mx+mssize>ex&&my<ey+esize&&my+mssize>ey) {
			scene=2;
		}
		Graphics gv = offImage.getGraphics();
		gv.setFont(new Font("Serif",Font.PLAIN,16));
		gv.clearRect(0, 0, xsize, ysize);
		gv.drawImage(enemy,ex,ey,this);
		gv.drawImage(myship, mx, my, this);
		if(bflag) gv.drawImage(bullet,bx,by,this);
		gv.drawString("SCORE: "+String.valueOf(score),10,50);
		repaint();
	}
	void gameover() {
		Graphics gv=offImage.getGraphics();
		gv.clearRect(0,0,xsize,ysize);
		gv.setFont(new Font("Serif",Font.BOLD,28));
		gv.drawString("GAME OVER", 64, 180);
		gv.setFont(new Font("Serif",Font.PLAIN,20));
		gv.drawString("SCORE: "+String.valueOf(score),90,350);
		repaint();
	}

}

