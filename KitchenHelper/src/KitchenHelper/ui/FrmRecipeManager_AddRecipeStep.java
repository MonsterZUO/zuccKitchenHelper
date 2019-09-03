package KitchenHelper.ui;

import KitchenHelper.control.FoodManager;
import KitchenHelper.control.RecipeManager;
import KitchenHelper.model.*;
import KitchenHelper.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class FrmRecipeManager_AddRecipeStep extends JDialog implements ActionListener {
	private RecipeInfo recipe;
	private RecipeStep recipeStep = null;
	private RecipeUse recipeUse = null;

	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelStepNo = new JLabel("步骤编号：");
	private JLabel labelDetail = new JLabel("步骤详情：");


	private JTextField edtStepNo = new JTextField(20);
	private JTextField edtAmount = new JTextField(20);
	private JTextField edtUnit = new JTextField(20);
	private JTextField edtDetail = new JTextField(20);

	public FrmRecipeManager_AddRecipeStep(JDialog f, String s, boolean b, RecipeInfo recipe) {
		super(f, s, b);
		this.recipe = recipe;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelStepNo);
		workPane.add(edtStepNo);
		workPane.add(labelDetail);
		workPane.add(edtDetail);

		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(260, 173);
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
			RecipeStep recipeStep = new RecipeStep();
			recipeStep.setRecipeNo(recipe.getRecipeNo());
			recipeStep.setStepNo(Integer.parseInt(this.edtStepNo.getText()));
			recipeStep.setStepDetail(this.edtDetail.getText());
			try {
				(new RecipeManager()).addRecipeStep(recipeStep);
				JOptionPane.showMessageDialog(null, "已完成", "提示", JOptionPane.INFORMATION_MESSAGE);
				this.recipeStep = recipeStep;
				this.setVisible(false);
			} catch (BaseException e1) {
				this.recipe = null;
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

	}

	public RecipeStep getRecipeStep() {
		return recipeStep;
	}

	public RecipeUse getRecipeUse() {
		return recipeUse;
	}

}
