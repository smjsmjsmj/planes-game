import javax.swing.JFrame;

/**
 * �ɻ���ս
 * @author smj 2016 12 05
 *
 */
public class Frame01 {

	/**
	 * @param ��ڷ���
	 */
	public static void main(String[] args) {
		//ʵ����JFrame
		JFrame frame = new JFrame();
		//���ô������
		frame.setTitle("�ɻ���ս");
		//���ô����С��λ��
		frame.setBounds(0, 0, 430, 640);
		//���ô���ر��¼�
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//ʵ����JPanel01 
		JPanel01 panel=new JPanel01();
		//��ʼ���߳�
		Thread thread=new Thread(panel);
		//�����߳�
		thread.start();
		//��Ӽ���
		frame.addMouseMotionListener(panel);
		frame.addMouseListener(panel);
		//��JPanel01������뵽������
		frame.add(panel);
		//���ô���ɼ�
		frame.setVisible(true);
	}
	

	

}

