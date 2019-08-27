package KitchenHelper.ui;

import KitchenHelper.control.FoodManager;
import KitchenHelper.control.RecipeManager;
import KitchenHelper.model.*;
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
	private Button btnAdd = new Button("添加菜谱");
	private Button btnModify = new Button("修改菜谱");
	private Button btnDelete = new Button("删除菜谱");
	private Button btnAddStep = new Button("添加步骤");
	private Button btnModifyStep = new Button("修改步骤");
	private Button btnDeleteStep = new Button("删除步骤");
	private Map<String, FoodInfo> foodMap_name = new HashMap<String, FoodInfo>();
	private Map<String, FoodInfo> foodMap_id = new HashMap<String, FoodInfo>();
	private JComboBox cmbFoodName = null;

	private JTextField edtKeyword = new JTextField(10);
	private Button btnSearch = new Button("查询");

	private Object tblRecipeTitle[] = {"菜谱编号", "菜谱名称", "贡献用户", "菜谱详情", "综合得分", "收藏数", "浏览数"};
	private Object tblRecipeData[][];
	DefaultTableModel tabRecipeModel = new DefaultTableModel();
	private JTable dataTableRecipe = new JTable(tabRecipeModel);

	private Object tblRecipeUseTitle[] = {"菜谱编号", "食材名称", "数量", "单位",};
	private Object tblRecipeUseData[][];
	DefaultTableModel tabRecipeUseModel = new DefaultTableModel();
	private JTable dataTableRecipeUse = new JTable(tabRecipeUseModel);

	private Object tblStepTitle[] = {"菜谱编号", "步骤序号", "步骤详情"};
	private Object tblStepData[][];
	DefaultTableModel tabStepModel = new DefaultTableModel();
	private JTable dataTableStep = new JTable(tabStepModel);

	private RecipeInfo curPlan = null;
	List<RecipeInfo> allRecipe = null;
	List<RecipeStep> recipeSteps = null;
	List<RecipeUse> recipeUse = null;

	private void reloadRecipeTable() {//这是测试数据，需要用实际数替换
		try {
			allRecipe = (new RecipeManager()).loadAll();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
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

	private void reloadRecipeUseTable(int planIdx) {//这是测试数据，需要用实际数替换
		if (planIdx < 0) {
			return;
		}
		curPlan = allRecipe.get(planIdx);
		try {
			recipeUse = (new RecipeManager()).loadRecipeUse(curPlan);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblRecipeUseData = new Object[recipeUse.size()][tblRecipeUseTitle.length];
		for (int i = 0; i < recipeUse.size(); i++) {
			for (int j = 0; j < tblRecipeUseTitle.length; j++) {
				tblRecipeUseData[i][j] = recipeUse.get(i).getCell(j);
			}
		}
		tabRecipeUseModel.setDataVector(tblRecipeUseData, tblRecipeUseTitle);
		this.dataTableRecipeUse.validate();
		this.dataTableRecipeUse.repaint();
	}

	private void reloadRecipeStepTable(int planIdx) {
		if (planIdx < 0) {
			return;
		}
		curPlan = allRecipe.get(planIdx);
		try {
			recipeSteps = (new RecipeManager()).loadSteps(curPlan);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
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
		//提取食材名称信息
		List<FoodInfo> foods;
		try {
			foods = (new FoodManager()).loadAllFoodName();
			String[] strTypes = new String[foods.size() + 1];
			strTypes[0] = "";
			for (int i = 0; i < foods.size(); i++) {
				foodMap_name.put(foods.get(i).getFoodName(), foods.get(i));
				foodMap_id.put(foods.get(i).getFoodNo(), foods.get(i));
				strTypes[i + 1] = foods.get(i).getFoodName();
			}
			cmbFoodName = new JComboBox(strTypes);
		} catch (BaseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 提取现有数据
		this.getContentPane().add(new JScrollPane(this.dataTableRecipe), BorderLayout.WEST);
		this.dataTableRecipe.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int i = FrmRecipeManager.this.dataTableRecipe.getSelectedRow();
				if (i < 0) {
					return;
				}
				FrmRecipeManager.this.reloadRecipeUseTable(i);
				FrmRecipeManager.this.reloadRecipeStepTable(i);
			}

		});
		this.getContentPane().add(new JScrollPane(this.dataTableRecipeUse), BorderLayout.CENTER);
		this.getContentPane().add(new JScrollPane(this.dataTableStep), BorderLayout.EAST);

		this.reloadRecipeTable();

		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(btnModify);
		toolBar.add(this.btnDelete);
		toolBar.add(btnAddStep);
		toolBar.add(btnModifyStep);
		toolBar.add(this.btnDeleteStep);
		toolBar.add(edtKeyword);
		toolBar.add(btnSearch);

		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		// 屏幕居中显示
		this.setSize(1394, 727);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2, (int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnAdd.addActionListener(this);
		this.btnModify.addActionListener(this);
		this.btnDelete.addActionListener(this);
		this.btnSearch.addActionListener(this);
		this.btnAddStep.addActionListener(this);
		this.btnModifyStep.addActionListener(this);
		this.btnDeleteStep.addActionListener(this);

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
			FrmRecipeManager_AddRecipe dlg = new FrmRecipeManager_AddRecipe(this, "添加菜谱", true);
			dlg.setVisible(true);
			if (dlg.getRecipe() != null) {// 刷新表格
				this.reloadRecipeTable();
			}
		}
//		else if (e.getSource() == this.btnModify) {
//			int i = this.foodTable.getSelectedRow();
//			if (i < 0) {
//				JOptionPane.showMessageDialog(this, "请选择菜谱", "提示", JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//			FoodInfo food = this.foods.get(i);
//
//			FrmFoodManager_ModifyFood dlg = new FrmFoodManager_ModifyFood(this, "修改菜谱", true, this.foodTypeMap_name,
//					food);
//			dlg.setVisible(true);
//			if (dlg.getFood() != null) {// 刷新表格
//				this.reloadTable();
//			}
//		}
//		else if (e.getSource() == this.btnDelete) {
//			int i = this.foodTable.getSelectedRow();
//			if (i < 0) {
//				JOptionPane.showMessageDialog(null, "请选择读者", "提示", JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//			FoodInfo food = this.foods.get(i);
//			if (JOptionPane.showConfirmDialog(this, "确定删除该读者吗？", "确认",
//					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
//				try {
//					(new FoodManager()).removeFood(food.getReaderid(), SystemUserManager.currentUser.getUserid());
//					this.reloadTable();
//				} catch (BaseException e1) {
//					JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
//				}
//
//			}
//		}
		else if (e.getSource() == this.btnAddStep) {
			int i = this.dataTableRecipe.getSelectedRow();
			if (i < 0) {
				JOptionPane.showMessageDialog(null, "请选择菜谱", "提示", JOptionPane.ERROR_MESSAGE);
				return;
			}
			RecipeInfo recipe = this.allRecipe.get(i);

			FrmRecipeManager_AddRecipeStep dlg;
			dlg = new FrmRecipeManager_AddRecipeStep(this, "添加步骤", true, this.foodMap_name,
					recipe);
			dlg.setVisible(true);
			if (dlg.getRecipeStep() != null) {// 刷新表格
				this.reloadRecipeStepTable(i);
			}
			if (dlg.getRecipeUse() != null) {// 刷新表格
				this.reloadRecipeUseTable(i);
			}
		}
//		else if (e.getSource() == this.btnSearch) {
//			this.reloadTable();
//		}

	}
}
