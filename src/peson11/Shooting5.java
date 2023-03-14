package peson11;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Shooting5 {
	public static void main(String[] args) {
		Shooting5Frame f=new Shooting5Frame("Shooting5");
		f.setBackground(Color.black);
		f.setForeground(Color.white);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}
class Shooting5Frame extends JFrame implements KeyListener, Runnable{
	private static final Graphics Graphics = null;
	static int xsize=300,ysize=400,mssize=60,esize=60,bsize=32,psize=32 ,en=3, gysize=600,bl=4;
	Image myship;
	Image enemy[]=new Image[en];
	Image bg;
	Image power,power1;
	Image bullet[]= new Image[bl];
	String base="bullet";
	
	int gy;
	int mx,my,by;
	int bx[]= new int[bl];
	int px,px1;
	int py,py1;
	
	int ex[]=new int[en], ey[]=new int[en];
	int dex[] =new int[en];
	int scene, score,hiscore,level,item;

	Thread thread;
	Image offImage;
	
	boolean keyLeft, keyRight,keyUp,keyDown, keySpace,keyEnter; 
	boolean bflag, pflag,pflag1;
	public Shooting5Frame(String title){
        super(title);
        gy=-gysize ;
        Toolkit tk = Toolkit.getDefaultToolkit();
        myship = tk.getImage("myship.gif");    
        bg=tk.getImage("galaxy1.gif");
        for(int i=0;i<bl;i++) {
        	bullet[i] = tk.getImage(base+i+".gif");
        	
        }
        power=tk.getImage("power0.gif");
        power1=tk.getImage("power1.gif");
        
        mx = (xsize-mssize) / 2; 
        my = ysize - mssize -10; 
        px=(int)(Math.random()*(xsize-psize));
        py=-psize;
        px1=(int)(Math.random()*(xsize-psize));
        py1=-psize;
       
        for(int i=0;i<en;i++) {
        	 enemy[i] = tk.getImage("enemy.gif");
        	 ex[i] = (int)(Math.random() * (xsize - esize));
             ey[i] = -esize - (int)(Math.random() * ysize);
             dex[i]=(int)(Math.random()*5);
             
        }
        keyLeft = keyRight =keyUp=keyDown= false;
        item=0;
        level=1;
        scene=0;
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
			
			case KeyEvent.VK_ENTER: keyEnter=true; break;
		}
	}
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			
			case KeyEvent.VK_UP: keyUp=false; break;
			
			case KeyEvent.VK_DOWN: keyDown=false; break;
				
			case KeyEvent.VK_SPACE: keySpace=false; break;
			
			case KeyEvent.VK_ENTER: keyEnter=false; break;
		}
	}
	
	public void keyTyped(KeyEvent e) {}

    public void run() {
        Thread thisThread = Thread.currentThread();
        while (thread == thisThread) {
        
            if (offImage!=null) {
            	switch(scene) {
            	case 0: gameStart(); break;
            	case 1: gameMain(); break;
            	case 2: gameover(); break;
            	case 3: gameStop(); break;
            	}
            };
            try {
                Thread.sleep(30);
            }
            catch(InterruptedException e) {
                break;
            }
            gy++;
            if(gy>0) {
            	gy=-gysize;
            }
        }
    }
    

	void gameMain() {
		
		
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
				for(int i=0;i<bl;i++) {
					bx[i]=mx+(mssize-bsize)/2;
				}
				
				by=my;
				bflag=true;
			}
		}
		if(keyEnter) {
			scene=3;
		}
		if(bflag) {
			if(by<0) {				
				bflag=false;
			}
			else {
				by-=8;
				bx[1]-=4;
				bx[3]+=4;
			}
		} 
		if(pflag) {
			
				py+=5;
				
				if(mx<px+psize&&mx+mssize>px&&my<py+psize&&my+mssize>py) {
					item=2;				
					py=-psize;	
					pflag=false;
				}
				if(py>=ysize) {
					 
					 px=(int)(Math.random()*(xsize-psize));
				     py=-psize;	
				     pflag=false; 
				}		
		}
		if(pflag1) {
			
			py1+=5;
			if(mx<px1+psize&&mx+mssize>px1&&my<py1+psize&&my+mssize>py1) {
				item=3;				
				py1=-psize;	
				pflag1=false;
			}
			if(py1>=ysize) {
				 
				 px1=(int)(Math.random()*(xsize-psize));
			     py1=-psize;	
			     pflag1=false; 
			}		
	   }
		
		for(int i=0; i<level;i++) 
		{
			ex[i] += dex[i];
			if (ex[i]<0 || ex[i]>xsize-esize) {
				dex[i] = -dex[i];
				ex[i] += 2*dex[i];
			}
			ey[i]+=8;
			if(ey[i]>=ysize) {
				ey[i]=-esize;
				ex[i]=(int)(Math.random()*(xsize-esize));
				
			}
			
			if(bflag) {
					if(item==0) {
						if(bx[item]<ex[i]+esize-20&&bx[item]+bsize+20>ex[i]+20&&by<ey[i]+esize-20&&by+bsize>ey[i]+20) {
							score+=100;
							if(score>=1000) {
								level=3;
							}else if(score>=500) {
								level=2;
							}
							ex[i]=(int)(Math.random()*(xsize-esize));
							ey[i]=-esize;
							bflag=false;
							if(score==300) {
								pflag=true;
							}
							if(score==600) {
								pflag1=true;
							}
						}
					}
					if(item==2) {
						if(bx[item]<ex[i]+esize-20&&bx[item]+bsize+20>ex[i]+20&&by<ey[i]+esize-20&&by+bsize>ey[i]+20) {
							score+=100;
							if(score>=1000) {
								level=3;
							}else if(score>=500) {
								level=2;
							}
							ex[i]=(int)(Math.random()*(xsize-esize));
							ey[i]=-esize;
							bflag=false;
							
							if(score==600) {
								pflag1=true;
							}
						}
					}
					if(item==3) {
						for(int j=1;j<=3;j++) {
							if(bx[j]<ex[i]+esize-20&&bx[j]+bsize+20>ex[i]+20&&by<ey[i]+esize-20&&by+bsize>ey[i]+20) {
								score+=100;
								if(score>=1000) {
									level=3;
								}else if(score>=500) {
									level=2;
								}
								ex[i]=(int)(Math.random()*(xsize-esize));
								ey[i]=-esize;
								bflag=false;			
							}
						}
						
					}
		
				
			}
			if(mx<ex[i]+esize&&mx+mssize>ex[i]&&my<ey[i]+esize&&my+mssize>ey[i]) {
				scene=2;
			}
			
		}
		
			Graphics gv = offImage.getGraphics();
			gv.setFont(new Font("Serif",Font.PLAIN,16));
			gv.clearRect(0, 0, xsize, ysize);
			gv.drawImage(bg,0,gy,this);
			for(int i=0;i<level;i++) {
				gv.drawImage(enemy[i],ex[i],ey[i],this);
			}
			
			if(pflag) {
				
					gv.drawImage(power,px,py,this);	
			}
			if(pflag1) {
				gv.drawImage(power1,px1,py1,this);	
			}
				
			gv.drawImage(myship, mx, my, this);
			
			
			if(bflag) {
				if(item==0) {
					gv.drawImage(bullet[item],bx[0],by,this);
				}
				if(item==2) {
					gv.drawImage(bullet[item],bx[0],by,this);
				}
				if(item==3) {
					for(int j=1;j<=3;j++) {
						gv.drawImage(bullet[j],bx[j],by,this);
					}
				}
	
			}
			gv.drawString("SCORE: "+String.valueOf(score),10,50);
			repaint();
		
		
		
	}
	void gameStart() {
		Graphics gv = offImage.getGraphics();
		gv.clearRect(0, 0, xsize, ysize);
		gv.setFont(new Font("Serif", Font.BOLD, 28));
		gv.drawString("Shooting Game", 64, 180);
		gv.setFont(new Font("Serif", Font.PLAIN, 20));
		gv.drawString("Hit Space key", 90, 350);
		repaint();		
		if (keySpace) {
			keyLeft = keyRight = keyUp = keyDown = keySpace = false;
			scene = 1;
		}
	}
	void gameStop() {
		Graphics gv = offImage.getGraphics();
		gv.setFont(new Font("Serif", Font.BOLD,35));
		gv.drawString("Pause", 100, 200);
		if(keyEnter) {
			if(thread!=null) {
				scene=1;
			}
		}
	}
		
	void gameover() {
		Graphics gv=offImage.getGraphics();
		if(keySpace) {
			keyLeft=keyRight=keyUp=keyDown=keySpace=false;
			bflag=pflag=pflag1=false;
			mx=(xsize-mssize)/2;
			my=ysize-mssize-10;
			for(int i=0;i<en;i++) {
				ex[i] = (int)(Math.random() * (xsize - esize));
	             ey[i] = -esize - (int)(Math.random() * ysize);
			}
			item=0;
			scene=0;
			score=0;
			level=1;
			gy=-gysize;
			py=-psize;
			py1=psize;
		}
		if(score>hiscore) {
			hiscore=score;
		}
		gv.clearRect(0,0,xsize,ysize);
		gv.setFont(new Font("Serif",Font.BOLD,28));
		gv.drawString("GAME OVER", 64, 180);
		gv.setFont(new Font("Serif",Font.PLAIN,20));
		gv.drawString("SCORE: "+String.valueOf(score),90,350);
		gv.drawString("HISCORE: "+String.valueOf(hiscore),70,370);
		repaint();
	}

}

