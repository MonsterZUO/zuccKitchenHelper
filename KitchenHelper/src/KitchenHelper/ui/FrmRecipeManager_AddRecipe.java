package KitchenHelper.ui;

import KitchenHelper.control.RecipeManager;
import KitchenHelper.model.RecipeInfo;
import KitchenHelper.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmRecipeManager_AddRecipe extends JDialog implements ActionListener {
	private RecipeInfo recipe = null;

	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelId = new JLabel("菜谱编号：");
	private JLabel labelName = new JLabel("菜谱名称：");
	private JLabel labelDetail = new JLabel("菜谱详情：");


	private JTextField edtId = new JTextField(20);
	private JTextField edtName = new JTextField(20);
	private JTextField edtDetail = new JTextField(20);

	public FrmRecipeManager_AddRecipe(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelId);
		workPane.add(edtId);
		workPane.add(labelName);
		workPane.add(edtName);
		workPane.add(labelDetail);
		workPane.add(edtDetail);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(258, 168);
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
			RecipeInfo recipe = new RecipeInfo();
			recipe.setRecipeNo(this.edtId.getText());
			recipe.setRecipeName(this.edtName.getText());
			recipe.setRecipeDetail(this.edtDetail.getText());
			try {
				(new RecipeManager()).createRecipe(recipe);
				JOptionPane.showMessageDialog(null, "已完成", "提示", JOptionPane.INFORMATION_MESSAGE);
				this.recipe = recipe;
				this.setVisible(false);
			} catch (BaseException e1) {
				this.recipe = null;
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

	}

	public RecipeInfo getRecipe() {
		return recipe;
	}

}
