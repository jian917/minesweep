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

public class Saolei implements ActionListener {		//声明实现这个方法

	//数据结构
	int ROW = 20;			//行数
	int COL = 20;			//列数
	int LEICOUNT = 50;		//雷的数目
	int[][] data = new int[ROW][COL];			//背后的数字，雷为-1，其余为0-8
	JButton [][]btns = new JButton [ROW][COL];	//按钮
	int LEICODE = -1;		//雷的代码
	int unopened = ROW * COL;	//未开
	int opened = 0;				//已开
	
	JFrame frame = new JFrame();				//游戏窗口
	ImageIcon bannerIcon = new ImageIcon("src/MineSweeper/pic/banner.png");		//图片
	ImageIcon guessIcon = new ImageIcon("src/MineSweeper/pic/guess.png");		//图片
	ImageIcon bombIcon = new ImageIcon("src/MineSweeper/pic/bomb.png");
	ImageIcon failIcon = new ImageIcon("src/MineSweeper/pic/fail.png");
	JButton bannerBtn = new JButton(bannerIcon);			//按钮
	JLabel label1 = new JLabel("待开: " + LEICOUNT);				//表格，加在GridBagLayout布局上
	JLabel label2 = new JLabel("已开: " + 0);
	JLabel label3 = new JLabel("用时待开: " + 2 + "s");
	
	
	public Saolei() {
		frame.setSize(600, 700);
		frame.setLocation(660, 190);
		frame.setResizable(false);		//不可改变窗口大小
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//点击关闭图标即关闭程序
		frame.setLayout(new BorderLayout());
		
		setHeader();			//设置头部图片
		
		addLei();
		
		setButtons();
		
		
		frame.setVisible(true);		//显示
	}
	
	private void addLei() {
		//埋雷
		Random rand = new Random();
		for (int i = 0; i < LEICOUNT; ) {
			int r = rand.nextInt(ROW);
			int c = rand.nextInt(COL);
			if (data[r][c] != LEICODE) {
				data[r][c] = LEICODE;
				i ++;
			}
		}
		
		//计算周边的雷的数量
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				if (data[i][j] == LEICODE) {		//是雷，不执行循环
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
		Container con = new Container();	//容器
		con.setLayout(new GridLayout(ROW, COL));
		
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				JButton btn = new JButton(guessIcon);				//按钮
				//JButton btn = new JButton(data[i][j] + "");		//便于测试，显示背后的数字, JButton 里面只能放字符串，所以将整数与""连接一下
				btn.setMargin(new Insets(0, 0, 0, 0));			//Insets为指定组件的外部填充, 即组件与其显示区域边缘之间间距的最小量, 这里设置间距为0.
				
				btn.setOpaque(true);
				btn.setBackground(new Color(244, 183, 113));			//按钮的背景颜色(橘黄色)
				btn.setDisabledIcon(guessIcon);
				
				btn.addActionListener(this);					//用于监听的操作
				
				con.add(btn);			//btn加入con容器，会自动排列顺序
				btns[i][j] = btn;		//加入按钮数组
			}
			
		}
		
		frame.add(con, BorderLayout.CENTER);
		
	}

	private void setHeader() {
		JPanel panel = new JPanel(new GridBagLayout());			//建立一个画布,接下来将一个按钮和三个label加进来
		
		//设置bannerBtn 加到panel 上的限制条件(GridBagConstraints)
		//参数: 横位置, 竖位置, 横宽度, 竖宽度
		GridBagConstraints c1 = new GridBagConstraints(0, 0, 3, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0,0,0,0), 0, 0);
		panel.add(bannerBtn, c1);
		
		label1.setOpaque(true);						//设置为不透明，不透明才能显示自己的颜色，否则只能显示容器的颜色
		label1.setBackground(Color.white);
		label1.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));		//设置灰边
		
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
		
		frame.add(panel, BorderLayout.NORTH);		//将panel放在窗口上，位置是NORTH
		
	}

	public static void main(String[] args) {
		new Saolei();

	}

	
	//设置点开按钮之后的操作
	@Override
	public void actionPerformed(ActionEvent e) {		//ActionEvent是一种操作
		JButton btn = (JButton)e.getSource();			//getSource()得到这个时间触发的根源, 并强制类型转换
		for (int i = 0; i < ROW; i++) {					//寻找与上面的根源相同的按钮
			for (int j = 0; j < COL; j++) {
				if (btn.equals(btns[i][j])) {
					if (data[i][j] == LEICODE) {
						lose();							//踩到雷, 执行lose函数
					} else {
						openCell(i, j);
					}
					
					return ;							//找到就无需继续寻找
				}
			}
		}
	}
	
	private void lose() {
		bannerBtn.setIcon(failIcon);
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				if (btns[i][j].isEnabled()) {				//如果是按钮
					JButton btn = btns[i][j];
					if (data[i][j] == LEICODE) {			//如果是雷	
						btn.setEnabled(false);
						btn.setOpaque(true);
						btn.setIcon(bombIcon);
						btn.setDisabledIcon(bombIcon);		//设置按钮的禁用图标
					} else {								//踩雷后非雷块不一定显现, 也可换颜色显现
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
		if (!btn.isEnabled()) {			//如果已经不是按钮, 即已经打开, 就退出函数
			return ;
		}
		
		btn.setIcon(null);					//清空Icon
		btn.setOpaque(true);				//设置不透明
		btn.setEnabled(false);				//设置不是按钮, true表示继续是按钮
		btn.setBackground(new Color(202, 235, 216));	//新背景颜色
		btn.setText(data[i][j] + "");		//设置为显示背后的数字
		
		if (data[i][j] == 0) {				//如果是空格, 就要递归地打开旁边的空格
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
