import javax.swing.JFrame;


public class DownBallJFrame {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame=new JFrame();
		frame.setTitle("ÏÂÂäµÄÐ¡Çò");
		frame.setBounds(40, 50, 300, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DownBallPanel downBallPanel=new DownBallPanel();
		Thread thread=new Thread(downBallPanel);
		thread.start();
		frame.add(downBallPanel);
		frame.setVisible(true);
	}

}
