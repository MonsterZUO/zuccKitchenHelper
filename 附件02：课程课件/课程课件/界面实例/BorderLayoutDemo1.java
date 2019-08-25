package cn.edu.zucc.booklib.LayoutDemo;

import javax.swing.*;
import java.awt.*;

public class BorderLayoutDemo1 extends JFrame {
	JPanel p = new JPanel();

	public BorderLayoutDemo1() {
		setLayout(new BorderLayout(5, 5));
		setFont(new Font("Helvetica", Font.PLAIN, 14));
		getContentPane().add("North", new JButton("North"));
		getContentPane().add("South", new JButton("South"));
		getContentPane().add("East", new JButton("East"));
		getContentPane().add("West", new JButton("West"));
		// 设置面板为流式布局居中显示，组件横、纵间距为5个像素
		p.setLayout(new FlowLayout(1, 5, 5));
		// 使用循环添加按钮，注意每次添加的按钮对象名称都是b
		// 但按钮每次均是用new新生成的，所有代表不同的按钮对象。
		for (int i = 1; i < 10; i++) {
			// String.valueOf(i)，将数字转换为字符串
			JButton b = new JButton(String.valueOf(i));
			p.add(b); // 将按钮添加到面板中
		}
		getContentPane().add("Center", p); // 将面板添加到中间位置
	}

	public static void main(String args[]) {
		BorderLayoutDemo1 f = new BorderLayoutDemo1();
		f.setTitle("边界布局");
		f.pack(); // 让窗体自适应组建大小
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null); // 让窗体居中显示
	}
}
