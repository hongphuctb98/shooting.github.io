package peson11;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Shooting {
	public static void main(String[] args) {
		ShootingexFrame f=new ShootingexFrame("Shooting");
		f.setBackground(Color.black);
		f.setForeground(Color.white);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}
class ShootingexFrame extends JFrame implements KeyListener, Runnable{
	Image myship,  bullet;
	Image[] enemy;
	int mx,my, bx,by;
	int ex[]= {0,0,0};
	int ey[]= {0,0,0};
	int scene, score;
	
	static int xsize=300,ysize=400,mssize=60,esize=60,bsize=8;
	Thread thread;
	Image offImage;
	boolean keyLeft, keyRight,keyUp,keyDown, keySpace; 
	boolean bflag;
	public ShootingexFrame(String title){
        super(title);
        Toolkit tk = Toolkit.getDefaultToolkit();
        myship = tk.getImage("myship.gif");
        for(int i=0;i<ex.length;i++) {
        	enemy[i] = tk.getImage("enemy.gif");
        }
        
        bullet = tk.getImage("bullet.gif");
        keyLeft = keyRight =keyUp=keyDown= false;
        mx = (xsize-mssize) / 2; 
        my = ysize - mssize -10; 
        for(int i=0;i<3;i++) {
        	 ex[i]=(int)(Math.random()*(xsize-esize));
             ey[i]=-esize;
        }
       
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
		for(int i=0;i<3;i++)
		{
			ey[i]+=8;
			if(ey[i]>ysize) {
				ey[i]=-esize;
				ex[i]=(int)(Math.random()*(xsize-esize));	
			}
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
			for(int i=0;i<3;i++) {
				if(bx<ex[i]+esize&&bx+bsize>ex[i]&&by<ey[i]+esize&&by+bsize>ey[i]) {
				score+=100;
				ex[i]=(int)(Math.random()*(xsize-esize));
				ey[i]=-esize;
				bflag=false;
				}
			}
		}
		for(int i=0;i<3;i++) {
			if(mx<ex[i]+esize&&mx+mssize>ex[i]&&my<ey[i]+esize&&my+mssize>ey[i]) {
				scene=2;
			}
		}
		for(int i=0;i<3;i++) {
		
			Graphics gv = offImage.getGraphics();
			gv.setFont(new Font("Serif",Font.PLAIN,16));
			gv.clearRect(0, 0, xsize, ysize);
			gv.drawImage(enemy[i],ex[i],ey[i],this);
			gv.drawImage(myship, mx, my, this);
			if(bflag) gv.drawImage(bullet,bx,by,this);
			gv.drawString("SCORE: "+String.valueOf(score),10,50);
			repaint();
		}
		
		
		
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

