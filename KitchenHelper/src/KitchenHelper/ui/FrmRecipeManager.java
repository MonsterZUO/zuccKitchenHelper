package KitchenHelper.ui;

import KitchenHelper.control.RecipeManager;
import KitchenHelper.model.FoodInfo;
import KitchenHelper.model.FoodTypeInfo;
import KitchenHelper.model.RecipeInfo;
import KitchenHelper.model.RecipeStep;
import KitchenHelper.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrmRecipeManager extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("��Ӳ���");
	private Button btnModify = new Button("�޸Ĳ���");
	private Button btnDelete = new Button("ɾ������");
	private Button btnAddStep = new Button("��Ӳ���");
	private Button btnModifyStep = new Button("�޸Ĳ���");
	private Button btnDeleteStep = new Button("ɾ������");

	private JTextField edtKeyword = new JTextField(10);
	private Button btnSearch = new Button("��ѯ");

	private Object tblRecipeTitle[] = {"���ױ��", "��������", "�����û�", "��������", "�ۺϵ÷�", "�ղ���", "�����"};
	private Object tblRecipeData[][];
	DefaultTableModel tabRecipeModel = new DefaultTableModel();
	private JTable dataTableRecipe = new JTable(tabRecipeModel);

	private Object tblStepTitle[] = {"���ױ��", "�������", "��������"};
	private Object tblStepData[][];
	DefaultTableModel tabStepModel = new DefaultTableModel();
	private JTable dataTableStep = new JTable(tabStepModel);

	private RecipeInfo curPlan = null;
	List<RecipeInfo> allRecipe = null;
	List<RecipeStep> recipeSteps = null;

	private void reloadRecipeTable() {//���ǲ������ݣ���Ҫ��ʵ�����滻
		try {
			allRecipe = (new RecipeManager()).loadAll();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblRecipeData = new Object[allRecipe.size()][tblRecipeTitle.length];
		for (int i = 0; i < allRecipe.size(); i++) {
			for (int j = 0; j < tblRecipeTitle.length; j++) {
				tblRecipeData[i][j] = allRecipe.get(i).getCell(j);
			}
		}
		tabRecipeModel.setDataVector(tblRecipeData, tblRecipeTitle);
		this.dataTableRecipe.validate();
		this.dataTableRecipe.repaint();
	}

	private void reloadRecipeStepTabel(int planIdx) {
		if (planIdx < 0) {
			return;
		}
		curPlan = allRecipe.get(planIdx);
		try {
			recipeSteps = (new RecipeManager()).loadSteps(curPlan);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblStepData = new Object[recipeSteps.size()][tblStepTitle.length];
		for (int i = 0; i < recipeSteps.size(); i++) {
			for (int j = 0; j < tblStepTitle.length; j++) {
				tblStepData[i][j] = recipeSteps.get(i).getCell(j);
			}
		}

		tabStepModel.setDataVector(tblStepData, tblStepTitle);
		this.dataTableStep.validate();
		this.dataTableStep.repaint();
	}

	public FrmRecipeManager(Frame f, String s, boolean b) {
		super(f, s, b);
		// ��ȡ��������
		this.getContentPane().add(new JScrollPane(this.dataTableRecipe), BorderLayout.WEST);
		this.dataTableRecipe.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int i = FrmRecipeManager.this.dataTableRecipe.getSelectedRow();
				if (i < 0) {
					return;
				}
				FrmRecipeManager.this.reloadRecipeStepTabel(i);
			}

		});
		this.getContentPane().add(new JScrollPane(this.dataTableStep), BorderLayout.CENTER);

		this.reloadRecipeTable();

		// ��Ļ������ʾ
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2, (int) (height - this.getHeight()) / 2);

		this.validate();
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(btnModify);
		toolBar.add(this.btnDelete);
		toolBar.add(btnAddStep);
		toolBar.add(btnModifyStep);
		toolBar.add(this.btnDeleteStep);
		toolBar.add(edtKeyword);
		toolBar.add(btnSearch);
		this.btnAdd.addActionListener(this);
		this.btnModify.addActionListener(this);
		this.btnDelete.addActionListener(this);
		this.btnSearch.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// System.exit(0);
			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnAdd) {
			FrmRecipeManager_AddRecipe dlg = new FrmRecipeManager_AddRecipe(this, "��Ӳ���", true);
			dlg.setVisible(true);
			if (dlg.getRecipe() != null) {// ˢ�±��
				this.reloadRecipeTable();
			}
		}
//		else if (e.getSource() == this.btnModify) {
//			int i = this.foodTable.getSelectedRow();
//			if (i < 0) {
//				JOptionPane.showMessageDialog(this, "��ѡ��ʳ��", "��ʾ", JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//			FoodInfo food = this.foods.get(i);
//
//			FrmFoodManager_ModifyFood dlg = new FrmFoodManager_ModifyFood(this, "�޸Ķ���", true, this.foodTypeMap_name,
//					food);
//			dlg.setVisible(true);
//			if (dlg.getFood() != null) {// ˢ�±��
//				this.reloadTable();
//			}
//		}
//		else if (e.getSource() == this.btnDelete) {
//			int i = this.foodTable.getSelectedRow();
//			if (i < 0) {
//				JOptionPane.showMessageDialog(null, "��ѡ�����", "��ʾ", JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//			FoodInfo food = this.foods.get(i);
//			if (JOptionPane.showConfirmDialog(this, "ȷ��ɾ���ö�����", "ȷ��",
//					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
//				try {
//					(new FoodManager()).removeFood(food.getReaderid(), SystemUserManager.currentUser.getUserid());
//					this.reloadTable();
//				} catch (BaseException e1) {
//					JOptionPane.showMessageDialog(null, e1.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
//				}
//
//			}
//		} else if (e.getSource() == this.btnSearch) {
//			this.reloadTable();
//		}
	}
}
