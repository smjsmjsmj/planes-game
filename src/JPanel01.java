import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class JPanel01 extends JPanel implements Runnable,MouseMotionListener,MouseListener{
	int num=5;//定义敌机的数量，及种类
	int speed=3;//定义敌机速度的最大值
	Random rd=new Random();
	int xs[]=new int[num];//敌机出现的x坐标
	int ys[]=new int[num];//敌机出现的y坐标
	int ss[]=new int[num];//敌机出现的速度
	Image ar[]=new Image[num];//敌机的种类数量
	//背景图片
	Image imgBg=null;
	//英雄属性
	int hx=250;
	int hy=500;
	Image imgHero;
	//子弹
    List<Bullet> bulletList;
    Image fireImg;
	//分数属性
	Image imgScore;
	int score;
	boolean flag=false;
	boolean isShut=false;
	int count=0;
	//英雄生命总量
	int lifeCount=5;
	//英雄生命条数
	int lifeAllPer=3;
	//上次碰撞英雄机的敌机
	int leftCount=0;
	//通过构造函数进行初始化敌机出现的位置，敌机的种类，敌机的速度
	public JPanel01(){
		bulletList=new ArrayList<Bullet>();
		fireImg=new ImageIcon("img/fire.png").getImage();
		//绘制英雄图片
		imgHero=new ImageIcon("img/hero.png").getImage();
		//绘制背景图片
		imgBg=new ImageIcon("img/bg1.jpg").getImage();
		//绘制分数图片
		imgScore=new ImageIcon("img/score.png").getImage();
		//为敌机设置初始化位置坐标，图片
		for(int i=0;i<xs.length;i++){
			
			ys[i]=-rd.nextInt(580);
			ss[i]=rd.nextInt(speed)+1;
			int ars=rd.nextInt(15)+1;
			ar[i]=new ImageIcon("img/ep"+(ars<=9?"0"+ars:ars)+".png").getImage();
			xs[i]=rd.nextInt(410-ar[i].getWidth(null));
		}
	}
	public void paint(Graphics g) {
		super.paint(g);
	
		g.drawImage(imgBg, 0,0, 430, 640, null);
		//循环绘制敌机
		for(int i=0;i<ar.length;i++){
			g.drawImage(ar[i],xs[i],ys[i], ar[i].getWidth(null)*2/3, ar[i].getHeight(null)*2/3, null);
		}
		
		//绘制英雄
		g.drawImage(imgHero, hx,hy, imgHero.getWidth(null)*2/3, imgHero.getHeight(null)*2/3, null);
		//绘制分数
		g.drawImage(imgScore, 0,500, 35, 35, null);
		g.setColor(Color.green);
		//绘制得分
		g.drawString("得分:",80, 20);
	    g.drawString(score+"", 110, 20);
		//绘制子弹图片
		for(int i=0;i<bulletList.size();i++){
			bulletList.get(i).paint(g);
		}
		g.setColor(Color.green);
		//生命数量
		g.drawString("生命:",0, 20);
		g.drawString(lifeAllPer+"", 50, 20);
		//血量条
		g.fillRect(0, 30,lifeCount*20, 10);
		g.setColor(Color.red);
		g.drawRect(0, 30, 100, 10);
		g.setColor(Color.red);
		//判断英雄机是否死亡
		if(lifeAllPer==0){
			g.drawString("Game Over! \n您的本次得分："+score, 180, 300);
		}
	}

	@Override
	public void run() {
	
		while(true){
			try {
				
				Thread.sleep(8);
				if(flag && count%25==0){
					Bullet bullet =new Bullet();
				    bullet.setImg(fireImg);
				    bullet.setX(hx+5);
				    bullet.setY(hy-45);
				    bullet.setHeight(bullet.getImg().getHeight(null)*2/3);
				    bullet.setWidth(bullet.getImg().getWidth(null)*2/3);
				    bullet.setSpeed(3);
				    bulletList.add(bullet);
				}
				if(count>20000){
					count=0;
				}else{
					count++;
				}
				repaint();
				for(int i=0;i<xs.length;i++){
					
					if(ys[i]>=580){
						ys[i]=-35;
						ss[i]=rd.nextInt(speed)+1;
						int ars=rd.nextInt(15)+1;
						ar[i]=new ImageIcon("img/ep"+(ars<=9?"0"+ars:ars)+".png").getImage();
						xs[i]=rd.nextInt(410-ar[i].getWidth(null));
					}else{
						ys[i]=ys[i]+ss[i];
						for(int j=0;j<bulletList.size();j++){
							//判断子弹是否超出边界
							if(bulletList.get(j).getY()+bulletList.get(j).getHeight()<=0){
								continue;
							}
							//判断子弹是否击中敌机
							if(isHit(bulletList.get(j),xs[i],ys[i],ar[i].getWidth(null),ar[i].getHeight(null))){
								ys[i]=-95;
								bulletList.remove(j);
								ss[i]=rd.nextInt(speed)+1;
								int ars=rd.nextInt(15)+1;
								ar[i]=new ImageIcon("img/ep"+(ars<=9?"0"+ars:ars)+".png").getImage();
								xs[i]=rd.nextInt(410-ar[i].getWidth(null));
								j--;
								score=score+10;
								break;
							}
						}
						//判断是否击中英雄机
						if(isShut(hx,hy, imgHero.getWidth(null)*2/3, imgHero.getHeight(null)*2/3,xs[i],ys[i],ar[i].getWidth(null),ar[i].getHeight(null))){
							
							if(lifeCount==0){
								lifeAllPer--;
								if(lifeAllPer==0){
									return;
								}
								lifeCount=5;
							}else if(leftCount!=i){
								lifeCount--;
								leftCount=i;
							}
							
						}
						
					}
					
				}
				for(int j=0;j<bulletList.size();j++){
					if(bulletList.get(j).getY()>-fireImg.getHeight(null)*2/3){
						
						bulletList.get(j).move();
						
					}else{
						
						bulletList.remove(j);
						j--;
					}
				}
				
				
			
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	/**
	 * 判断子弹是否与敌机进行碰撞
	 * @param bt 子弹实例化对象
	 * @param ex 敌机x坐标
	 * @param ey 敌机y坐标
	 * @param ew 敌机的宽度
	 * @param eh 敌机的高度
	 * @return
	 */
	public boolean isHit(Bullet bt,int ex,int ey,int ew,int eh){
		int bx=bt.getX()+bt.getWidth()/2;//子弹中心的x坐标
		int by=bt.getY()+bt.getHeight()/2;//子弹的中心的y坐标
		int ex1=ex+ew/2;//敌机的中心的x坐标
		int ey1=ey+eh/2;//敌机的中心的y坐标
		int line1=(ex1-bx)*(ex1-bx);
		int line2=(ey1-by)*(ey1-by);
		int r1=ew<=eh?ew/3:eh/3;//敌机的内切圆半径
		int r2=bt.getHeight()<bt.getWidth()?bt.getHeight()/3:bt.getWidth()/3;//子弹的内切圆半径
		//判断是否相撞
		if((line1+line2)<=(r1+r2)*(r1+r2)){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断英雄机是否与敌机进行碰撞
	 * @param bt
	 * @param ex
	 * @param ey
	 * @param ew
	 * @param eh
	 * @return
	 */
	public boolean isShut(int hx,int hy,int hw,int hh,int ex,int ey,int ew,int eh){
		int bx=hx+hw/2;//英雄机的x坐标
		int by=hy+hh/2;//英雄机的y坐标
		int ex1=ex+ew/2;//敌机的x坐标
		int ey1=ey+eh/2;//敌机的y坐标
		int line1=(ex1-bx)*(ex1-bx);//平行于x轴的直角边
		int line2=(ey1-by)*(ey1-by);//平行于y轴的直角边
		int r1=ew<=eh?ew/3:eh/3;
		int r2=hh<hw?hh/3:hw/3;
		if((line1+line2)<=(r1+r2)*(r1+r2)){
			return true;
		}
		return false;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		hx=e.getX()<=70?0:e.getX();
		hx=e.getX()>=360?360:e.getX()-50;
		hy=e.getY()<=0?0:e.getY()-50;
		hy=e.getY()>=570?570:e.getY()-50;
	    flag=true;
		repaint();
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		flag=false;
		repaint();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
	
	}
	@Override
	public void mousePressed(MouseEvent e) {
		flag=true;
		repaint();
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		flag=false;
		repaint();
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	
}
