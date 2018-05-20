import javax.swing.JFrame;

/**
 * 飞机大战
 * @author smj 2016 12 05
 *
 */
public class Frame01 {

	/**
	 * @param 入口方法
	 */
	public static void main(String[] args) {
		//实例化JFrame
		JFrame frame = new JFrame();
		//设置窗体标题
		frame.setTitle("飞机大战");
		//设置窗体大小，位置
		frame.setBounds(0, 0, 430, 640);
		//设置窗体关闭事件
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//实例化JPanel01 
		JPanel01 panel=new JPanel01();
		//初始化线程
		Thread thread=new Thread(panel);
		//启动线程
		thread.start();
		//添加监听
		frame.addMouseMotionListener(panel);
		frame.addMouseListener(panel);
		//将JPanel01对象加入到窗体中
		frame.add(panel);
		//设置窗体可见
		frame.setVisible(true);
	}
	

	

}

