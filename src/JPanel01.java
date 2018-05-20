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
	int num=5;//����л���������������
	int speed=3;//����л��ٶȵ����ֵ
	Random rd=new Random();
	int xs[]=new int[num];//�л����ֵ�x����
	int ys[]=new int[num];//�л����ֵ�y����
	int ss[]=new int[num];//�л����ֵ��ٶ�
	Image ar[]=new Image[num];//�л�����������
	//����ͼƬ
	Image imgBg=null;
	//Ӣ������
	int hx=250;
	int hy=500;
	Image imgHero;
	//�ӵ�
    List<Bullet> bulletList;
    Image fireImg;
	//��������
	Image imgScore;
	int score;
	boolean flag=false;
	boolean isShut=false;
	int count=0;
	//Ӣ����������
	int lifeCount=5;
	//Ӣ����������
	int lifeAllPer=3;
	//�ϴ���ײӢ�ۻ��ĵл�
	int leftCount=0;
	//ͨ�����캯�����г�ʼ���л����ֵ�λ�ã��л������࣬�л����ٶ�
	public JPanel01(){
		bulletList=new ArrayList<Bullet>();
		fireImg=new ImageIcon("img/fire.png").getImage();
		//����Ӣ��ͼƬ
		imgHero=new ImageIcon("img/hero.png").getImage();
		//���Ʊ���ͼƬ
		imgBg=new ImageIcon("img/bg1.jpg").getImage();
		//���Ʒ���ͼƬ
		imgScore=new ImageIcon("img/score.png").getImage();
		//Ϊ�л����ó�ʼ��λ�����꣬ͼƬ
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
		//ѭ�����Ƶл�
		for(int i=0;i<ar.length;i++){
			g.drawImage(ar[i],xs[i],ys[i], ar[i].getWidth(null)*2/3, ar[i].getHeight(null)*2/3, null);
		}
		
		//����Ӣ��
		g.drawImage(imgHero, hx,hy, imgHero.getWidth(null)*2/3, imgHero.getHeight(null)*2/3, null);
		//���Ʒ���
		g.drawImage(imgScore, 0,500, 35, 35, null);
		g.setColor(Color.green);
		//���Ƶ÷�
		g.drawString("�÷�:",80, 20);
	    g.drawString(score+"", 110, 20);
		//�����ӵ�ͼƬ
		for(int i=0;i<bulletList.size();i++){
			bulletList.get(i).paint(g);
		}
		g.setColor(Color.green);
		//��������
		g.drawString("����:",0, 20);
		g.drawString(lifeAllPer+"", 50, 20);
		//Ѫ����
		g.fillRect(0, 30,lifeCount*20, 10);
		g.setColor(Color.red);
		g.drawRect(0, 30, 100, 10);
		g.setColor(Color.red);
		//�ж�Ӣ�ۻ��Ƿ�����
		if(lifeAllPer==0){
			g.drawString("Game Over! \n���ı��ε÷֣�"+score, 180, 300);
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
							//�ж��ӵ��Ƿ񳬳��߽�
							if(bulletList.get(j).getY()+bulletList.get(j).getHeight()<=0){
								continue;
							}
							//�ж��ӵ��Ƿ���ел�
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
						//�ж��Ƿ����Ӣ�ۻ�
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
	 * �ж��ӵ��Ƿ���л�������ײ
	 * @param bt �ӵ�ʵ��������
	 * @param ex �л�x����
	 * @param ey �л�y����
	 * @param ew �л��Ŀ��
	 * @param eh �л��ĸ߶�
	 * @return
	 */
	public boolean isHit(Bullet bt,int ex,int ey,int ew,int eh){
		int bx=bt.getX()+bt.getWidth()/2;//�ӵ����ĵ�x����
		int by=bt.getY()+bt.getHeight()/2;//�ӵ������ĵ�y����
		int ex1=ex+ew/2;//�л������ĵ�x����
		int ey1=ey+eh/2;//�л������ĵ�y����
		int line1=(ex1-bx)*(ex1-bx);
		int line2=(ey1-by)*(ey1-by);
		int r1=ew<=eh?ew/3:eh/3;//�л�������Բ�뾶
		int r2=bt.getHeight()<bt.getWidth()?bt.getHeight()/3:bt.getWidth()/3;//�ӵ�������Բ�뾶
		//�ж��Ƿ���ײ
		if((line1+line2)<=(r1+r2)*(r1+r2)){
			return true;
		}
		return false;
	}
	
	/**
	 * �ж�Ӣ�ۻ��Ƿ���л�������ײ
	 * @param bt
	 * @param ex
	 * @param ey
	 * @param ew
	 * @param eh
	 * @return
	 */
	public boolean isShut(int hx,int hy,int hw,int hh,int ex,int ey,int ew,int eh){
		int bx=hx+hw/2;//Ӣ�ۻ���x����
		int by=hy+hh/2;//Ӣ�ۻ���y����
		int ex1=ex+ew/2;//�л���x����
		int ey1=ey+eh/2;//�л���y����
		int line1=(ex1-bx)*(ex1-bx);//ƽ����x���ֱ�Ǳ�
		int line2=(ey1-by)*(ey1-by);//ƽ����y���ֱ�Ǳ�
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
