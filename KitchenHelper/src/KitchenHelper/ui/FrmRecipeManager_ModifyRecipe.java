package KitchenHelper.ui;

import KitchenHelper.control.FoodManager;
import KitchenHelper.control.RecipeManager;
import KitchenHelper.model.FoodInfo;
import KitchenHelper.model.FoodTypeInfo;
import KitchenHelper.model.RecipeInfo;
import KitchenHelper.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

public class FrmRecipeManager_ModifyRecipe extends JDialog implements ActionListener {
	private RecipeInfo recipe = null;

	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("È·¶¨");
	private Button btnCancel = new Button("È¡Ïû");
	private JLabel labelId = new JLabel("²ËÆ×±àºÅ£º");
	private JLabel labelName = new JLabel("²ËÆ×Ãû³Æ£º");
	private JLabel labelDetail = new JLabel("²ËÆ×ÏêÇé£º");

	private JTextField edtId = new JTextField(20);
	private JTextField edtName = new JTextField(20);
	private JTextField edtDetail = new JTextField(20);

	public FrmRecipeManager_ModifyRecipe(JDialog f, String s, boolean b, RecipeInfo r) {
		super(f, s, b);
		this.recipe = r;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelId);
		this.edtId.setText(this.recipe.getRecipeNo());
		workPane.add(edtId);
		workPane.add(labelName);
		this.edtName.setText(r.getRecipeName());
		workPane.add(edtName);
		workPane.add(labelDetail);
		this.edtDetail.setText(r.getRecipeDetail());
		workPane.add(edtDetail);

		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(360, 180);
		// ÆÁÄ»¾ÓÖÐÏÔÊ¾
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2, (int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				FrmRecipeManager_ModifyRecipe.this.recipe = null;
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnCancel) {
			this.setVisible(false);
			this.recipe = null;
			return;
		} else if (e.getSource() == this.btnOk) {
			String id = (this.edtId.getText() != "") ? this.edtId.getText() : this.recipe.getRecipeNo();
			String name = (this.edtName.getText() != "") ? this.edtName.getText() : this.recipe.getRecipeName();
			String detail = (this.edtDetail.getText() != "") ? this.edtDetail.getText() : this.recipe.getRecipeDetail();
			RecipeInfo r = new RecipeInfo();
			r.setRecipeNo(id);
			r.setRecipeName(name);
			r.setRecipeDetail(detail);
			try {
				(new RecipeManager()).modifyRecipe(r, this.recipe.getRecipeNo());
//				if (this.food.getFoodTypeNo() != rt.getFoodTypeNo()) {
//					(new FoodManager()).changeFoodType(this.food.getFoodNo(), rt.getFoodTypeNo());
//				}
//				if (!this.getFood().getFoodName().equals(name)) {
//					(new FoodManager()).renameFood(this.getFood().getFoodNo(), name);
//				}
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "´íÎó", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	public RecipeInfo getRecipe() {
		return recipe;
	}

}
