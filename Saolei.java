package MineSweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Saolei implements ActionListener {		//����ʵ���������

	//���ݽṹ
	int ROW = 20;			//����
	int COL = 20;			//����
	int LEICOUNT = 50;		//�׵���Ŀ
	int[][] data = new int[ROW][COL];			//��������֣���Ϊ-1������Ϊ0-8
	JButton [][]btns = new JButton [ROW][COL];	//��ť
	int LEICODE = -1;		//�׵Ĵ���
	int unopened = ROW * COL;	//δ��
	int opened = 0;				//�ѿ�
	
	JFrame frame = new JFrame();				//��Ϸ����
	ImageIcon bannerIcon = new ImageIcon("src/MineSweeper/pic/banner.png");		//ͼƬ
	ImageIcon guessIcon = new ImageIcon("src/MineSweeper/pic/guess.png");		//ͼƬ
	ImageIcon bombIcon = new ImageIcon("src/MineSweeper/pic/bomb.png");
	ImageIcon failIcon = new ImageIcon("src/MineSweeper/pic/fail.png");
	JButton bannerBtn = new JButton(bannerIcon);			//��ť
	JLabel label1 = new JLabel("����: " + LEICOUNT);				//��񣬼���GridBagLayout������
	JLabel label2 = new JLabel("�ѿ�: " + 0);
	JLabel label3 = new JLabel("��ʱ����: " + 2 + "s");
	
	
	public Saolei() {
		frame.setSize(600, 700);
		frame.setLocation(660, 190);
		frame.setResizable(false);		//���ɸı䴰�ڴ�С
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//����ر�ͼ�꼴�رճ���
		frame.setLayout(new BorderLayout());
		
		setHeader();			//����ͷ��ͼƬ
		
		addLei();
		
		setButtons();
		
		
		frame.setVisible(true);		//��ʾ
	}
	
	private void addLei() {
		//����
		Random rand = new Random();
		for (int i = 0; i < LEICOUNT; ) {
			int r = rand.nextInt(ROW);
			int c = rand.nextInt(COL);
			if (data[r][c] != LEICODE) {
				data[r][c] = LEICODE;
				i ++;
			}
		}
		
		//�����ܱߵ��׵�����
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				if (data[i][j] == LEICODE) {		//���ף���ִ��ѭ��
					continue;
				}
				
				int leiCount = 0;
				if (i > 0 && j > 0 && data[i-1][j-1] == LEICODE) {		//i-1, j-1
					leiCount ++;
				}
				if (i > 0 && j < COL-1 && data[i-1][j+1] == LEICODE) {		//i-1, j+1
					leiCount ++;
				}
				if (i < ROW-1 && j > 0 && data[i+1][j-1] == LEICODE) {		//i+1, j-1
					leiCount ++;
				}
				if (i < ROW-1 && j < COL-1 && data[i+1][j+1] == LEICODE) {		//i+1, j+1
					leiCount ++;
				}
				if (i > 0 && data[i-1][j] == LEICODE) {		//i-1, j
					leiCount ++;
				}
				if (i < ROW-1 && data[i+1][j] == LEICODE) {		//i+1, j
					leiCount ++;
				}
				if (j > 0 && data[i][j-1] == LEICODE) {		//i, j-1
					leiCount ++;
				}
				if (j < COL-1 && data[i][j+1] == LEICODE) {		//i, j+1
					leiCount ++;
				}
				
				data[i][j] = leiCount;
			}
			
		}
	}

	private void setButtons() {
		Container con = new Container();	//����
		con.setLayout(new GridLayout(ROW, COL));
		
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				JButton btn = new JButton(guessIcon);				//��ť
				//JButton btn = new JButton(data[i][j] + "");		//���ڲ��ԣ���ʾ���������, JButton ����ֻ�ܷ��ַ��������Խ�������""����һ��
				btn.setMargin(new Insets(0, 0, 0, 0));			//InsetsΪָ��������ⲿ���, �����������ʾ�����Ե֮�������С��, �������ü��Ϊ0.
				
				btn.setOpaque(true);
				btn.setBackground(new Color(244, 183, 113));			//��ť�ı�����ɫ(�ٻ�ɫ)
				btn.setDisabledIcon(guessIcon);
				
				btn.addActionListener(this);					//���ڼ����Ĳ���
				
				con.add(btn);			//btn����con���������Զ�����˳��
				btns[i][j] = btn;		//���밴ť����
			}
			
		}
		
		frame.add(con, BorderLayout.CENTER);
		
	}

	private void setHeader() {
		JPanel panel = new JPanel(new GridBagLayout());			//����һ������,��������һ����ť������label�ӽ���
		
		//����bannerBtn �ӵ�panel �ϵ���������(GridBagConstraints)
		//����: ��λ��, ��λ��, ����, �����
		GridBagConstraints c1 = new GridBagConstraints(0, 0, 3, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,0,0,0), 0, 0);
		panel.add(bannerBtn, c1);
		
		label1.setOpaque(true);						//����Ϊ��͸������͸��������ʾ�Լ�����ɫ������ֻ����ʾ��������ɫ
		label1.setBackground(Color.white);
		label1.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));		//���ûұ�
		
		label2.setOpaque(true);
		label2.setBackground(Color.white);
		label2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		
		label3.setOpaque(true);
		label3.setBackground(Color.white);
		label3.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		
		bannerBtn.setOpaque(true);
		bannerBtn.setBackground(Color.white);
		bannerBtn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		
		GridBagConstraints c2 = new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,0,0,0), 0, 0);
		panel.add(label1, c2);
		
		GridBagConstraints c3 = new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,0,0,0), 0, 0);
		panel.add(label2, c3);
		
		GridBagConstraints c4 = new GridBagConstraints(2, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,0,0,0), 0, 0);
		panel.add(label3, c4);
		
		frame.add(panel, BorderLayout.NORTH);		//��panel���ڴ����ϣ�λ����NORTH
		
	}

	public static void main(String[] args) {
		new Saolei();

	}

	
	//���õ㿪��ť֮��Ĳ���
	@Override
	public void actionPerformed(ActionEvent e) {		//ActionEvent��һ�ֲ���
		JButton btn = (JButton)e.getSource();			//getSource()�õ����ʱ�䴥���ĸ�Դ, ��ǿ������ת��
		for (int i = 0; i < ROW; i++) {					//Ѱ��������ĸ�Դ��ͬ�İ�ť
			for (int j = 0; j < COL; j++) {
				if (btn.equals(btns[i][j])) {
					if (data[i][j] == LEICODE) {
						lose();							//�ȵ���, ִ��lose����
					} else {
						openCell(i, j);
					}
					
					return ;							//�ҵ����������Ѱ��
				}
			}
		}
	}
	
	private void lose() {
		bannerBtn.setIcon(failIcon);
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				if (btns[i][j].isEnabled()) {				//����ǰ�ť
					JButton btn = btns[i][j];
					if (data[i][j] == LEICODE) {			//�������	
						btn.setEnabled(false);
						btn.setOpaque(true);
						btn.setIcon(bombIcon);
						btn.setDisabledIcon(bombIcon);		//���ð�ť�Ľ���ͼ��
					} else {								//���׺���׿鲻һ������, Ҳ�ɻ���ɫ����
						btn.setIcon(null);
						btn.setOpaque(true);
						btn.setEnabled(false);
						btn.setBackground(Color.LIGHT_GRAY);
						btn.setText(data[i][j] + "");
					}
				}
			}
		}
	}
	
	private void openCell(int i, int j) {
		JButton btn = btns[i][j];
		if (!btn.isEnabled()) {			//����Ѿ����ǰ�ť, ���Ѿ���, ���˳�����
			return ;
		}
		
		btn.setIcon(null);					//���Icon
		btn.setOpaque(true);				//���ò�͸��
		btn.setEnabled(false);				//���ò��ǰ�ť, true��ʾ�����ǰ�ť
		btn.setBackground(new Color(202, 235, 216));	//�±�����ɫ
		btn.setText(data[i][j] + "");		//����Ϊ��ʾ���������
		
		if (data[i][j] == 0) {				//����ǿո�, ��Ҫ�ݹ�ش��ԱߵĿո�
			if (i > 0 && j > 0 && data[i-1][j-1] == 0) {		//i-1, j-1
				openCell(i-1, j-1);
			}
			if (i > 0 && j < COL-1 && data[i-1][j+1] == 0) {		//i-1, j+1
				openCell(i-1, j+1);
			}
			if (i < ROW-1 && j > 0 && data[i+1][j-1] == 0) {		//i+1, j-1
				openCell(i+1, j-1);
			}
			if (i > ROW-1 && j < COL-1 && data[i+1][j+1] == 0) {		//i+1, j+1
				openCell(i+1, j+1);
			}
			if (i > 0 && data[i-1][j] == 0) {		//i-1, j
				openCell(i-1, j);
			}
			if (i < ROW-1 && data[i+1][j] == 0) {		//i+1, j
				openCell(i+1, j);
			}
			if (j > 0 && data[i][j-1] == 0) {		//i, j-1
				openCell(i, j-1);
			}
			if (j < COL-1 && data[i][j+1] == 0) {		//i, j+1
				openCell(i, j+1);
			}
		}
	}

}
