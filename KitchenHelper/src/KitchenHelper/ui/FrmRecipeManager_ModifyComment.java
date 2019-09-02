package KitchenHelper.ui;

import KitchenHelper.control.*;
import KitchenHelper.model.RecipeComment;
import KitchenHelper.model.RecipeInfo;
import KitchenHelper.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmRecipeManager_ModifyComment extends JDialog implements ActionListener {
	RecipeComment recipeComment = new RecipeComment();
	RecipeComment comment = new RecipeComment();
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelComment = new JLabel("评论内容：");
	private JTextField edtComment = new JTextField(20);

	public FrmRecipeManager_ModifyComment(JDialog f, String s, boolean b, RecipeComment recipeComment) {
		super(f, s, b);
		this.recipeComment = recipeComment;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelComment);
		this.edtComment.setText(this.recipeComment.getComment());
		workPane.add(edtComment);

		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 180);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnCancel) {
			this.setVisible(false);
			return;
		} else if (e.getSource() == this.btnOk) {
			this.recipeComment.setComment(this.edtComment.getText());
			try {
				(new RecipeManager()).modifyComment(this.recipeComment);
				JOptionPane.showMessageDialog(null, "已完成", "提示", JOptionPane.INFORMATION_MESSAGE);
				this.comment = this.recipeComment;
				this.setVisible(false);
			} catch (BaseException e1) {
				this.comment = null;
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}
	public RecipeComment getComment() {
		return comment;
	}

}




