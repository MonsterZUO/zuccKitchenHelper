package cn.edu.zucc.booklib.LayoutDemo;

import java.awt.*;

import javax.swing.*;

public class NullLayoutDemo {
	JFrame fr;
	JButton a, b;
	NullLayoutDemo() {
		fr = new JFrame();
		fr.setBounds(100, 100, 250, 150);

		// 设置窗体为空布局
		fr.setLayout(null);
		a = new JButton("按钮a");
		b = new JButton("按钮b");
		fr.getContentPane().add(a);

		// 设置按钮a的精确位置
		a.setBounds(30, 30, 80, 25);
		fr.getContentPane().add(b);
		b.setBounds(150, 40, 80, 25);
		fr.setTitle("NullLayoutDemo");
		fr.setVisible(true);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setLocationRelativeTo(null); // 让窗体居中显示
	}

	public static void main(String args[]) {
		new NullLayoutDemo();
	}

}