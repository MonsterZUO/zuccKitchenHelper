package KitchenHelper.ui;

import KitchenHelper.control.RecipeManager;
import KitchenHelper.model.FoodInfo;
import KitchenHelper.model.RecipeInfo;
import KitchenHelper.model.RecipeStep;
import KitchenHelper.model.RecipeUse;
import KitchenHelper.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class FrmRecipeManager_AddRecipeFood extends JDialog implements ActionListener {
	private RecipeInfo recipe;
	private RecipeStep recipeStep = null;
	private RecipeUse recipeUse = null;

	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	private JLabel labelFoodName = new JLabel("ʳ�ģ�");
	private JLabel labelAmount = new JLabel("ʳ��������");
	private JLabel labelUnit = new JLabel("ʳ�ĵ�λ��");

	private JTextField edtStepNo = new JTextField(20);
	private JTextField edtAmount = new JTextField(20);
	private JTextField edtUnit = new JTextField(20);
	private JTextField edtDetail = new JTextField(20);
	private Map<String, FoodInfo> foodMap_name;
	private JComboBox cmbFoodName;

	public FrmRecipeManager_AddRecipeFood(JDialog f, String s, boolean b, Map<String, FoodInfo> foodMap_name, RecipeInfo recipe) {
		super(f, s, b);
		this.foodMap_name = foodMap_name;
		this.recipe = recipe;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelFoodName);

		//��ȡʳ������
		String[] strTypes = new String[this.foodMap_name.size() + 1];
		strTypes[0] = "";
		java.util.Iterator<FoodInfo> itRt = this.foodMap_name.values().iterator();
		int i = 1;
		while (itRt.hasNext()) {
			strTypes[i] = itRt.next().getFoodName();
			i++;
		}
		cmbFoodName = new JComboBox(strTypes);

		workPane.add(cmbFoodName);
		workPane.add(labelAmount);
		workPane.add(edtAmount);
		workPane.add(labelUnit);
		workPane.add(edtUnit);

		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(175, 221);
		// ��Ļ������ʾ
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
			RecipeUse recipeUse = new RecipeUse();
			String fName = this.cmbFoodName.getSelectedItem().toString();
			FoodInfo f = this.foodMap_name.get(fName);
			if (f == null) {
				JOptionPane.showMessageDialog(null, "��ѡ��ʳ��", "����", JOptionPane.ERROR_MESSAGE);
				return;
			}
			recipeUse.setFoodNo(f.getFoodNo());
			recipeUse.setRecipeNo(recipe.getRecipeNo());
			recipeUse.setAmount(Double.parseDouble(this.edtAmount.getText()));
			recipeUse.setUnit(this.edtUnit.getText());
			try {
				(new RecipeManager()).addRecipeUse(recipeUse);
				JOptionPane.showMessageDialog(null, "�����", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				this.recipeUse = recipeUse;
				this.setVisible(false);
			} catch (BaseException e1) {
				this.recipe = null;
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

	}
	public RecipeUse getRecipeUse() {
		return recipeUse;
	}

}
