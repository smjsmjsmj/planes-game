import java.awt.Graphics;
import java.awt.Image;
/**
 * 子弹类
 * @author smj 2016 1207
 *
 */
public class Bullet {

	//大小
	private int width;
	private int height;
	//背景图片
	private Image img;
	//坐标
	private int x;
	private int y;
	//速度
	private int speed;
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public Image getImg() {
		return img;
	}
	public void setImg(Image img) {
		this.img = img;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	//子弹移动
	public void move(){
		y=y-speed;
	}
	//绘制
	public void paint(Graphics g){
		g.drawImage(img, x, y, width, height, null);
	}
	
}
