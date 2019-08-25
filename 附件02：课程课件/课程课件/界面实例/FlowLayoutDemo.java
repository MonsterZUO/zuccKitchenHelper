package cn.edu.zucc.booklib.LayoutDemo;

import javax.swing.*;
import java.awt.*;

public class FlowLayoutDemo extends JFrame {
	public FlowLayoutDemo() {
		// 设置窗体为流式布局，无参数默认为居中对齐
		setLayout(new FlowLayout());
		// 设置窗体中显示的字体样式
		setFont(new Font("Helvetica", Font.PLAIN, 14));
		// 将按钮添加到窗体中
		getContentPane().add(new JButton("Button 1"));
		getContentPane().add(new JButton("Button 2"));
		getContentPane().add(new JButton("Button 3"));
		getContentPane().add(new JButton("Button 4"));
	}

	public static void main(String args[]) {
		FlowLayoutDemo window = new FlowLayoutDemo();
		window.setTitle("流式布局");
		// 该代码依据放置的组件设定窗口的大小使之正好能容纳你放置的所有组件
		window.pack();
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null); // 让窗体居中显示
	}
}
