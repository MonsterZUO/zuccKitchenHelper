package KitchenHelper.ui;

import KitchenHelper.control.RecipeManager;
import KitchenHelper.model.*;
import KitchenHelper.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class FrmRecipeManager_ModifyRecipeStep extends JDialog implements ActionListener {
	private RecipeInfo recipe;
	private RecipeStep recipeStep;

	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelStepNo = new JLabel("步骤编号：");
	private JLabel labelDetail = new JLabel("步骤详情：");


	private JTextField edtStepNo = new JTextField(20);
	private JTextField edtDetail = new JTextField(20);

	public FrmRecipeManager_ModifyRecipeStep(JDialog f, String s, boolean b, RecipeStep recipeStep)  {
		super(f, s, b);
		this.recipeStep = recipeStep;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelStepNo);
		this.edtStepNo.setText(String.valueOf(this.recipeStep.getStepNo()));
		this.edtStepNo.setEnabled(false);
		workPane.add(edtStepNo);
		workPane.add(labelDetail);
		this.edtDetail.setText(this.recipeStep.getStepDetail());
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
			RecipeStep r = new RecipeStep();
			r.setRecipeNo(this.recipeStep.getRecipeNo());
			r.setStepNo(Integer.parseInt(this.edtStepNo.getText()));
			r.setStepDetail(this.edtDetail.getText());
			try {
				(new RecipeManager()).modifyRecipeStep(r);
				JOptionPane.showMessageDialog(null, "已完成", "提示", JOptionPane.INFORMATION_MESSAGE);
				this.recipeStep = r;
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

}
