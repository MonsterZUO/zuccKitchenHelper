package KitchenHelper.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import KitchenHelper.control.RecipeManager;
import KitchenHelper.control.UserManager;
import KitchenHelper.model.RecipeInfo;
import KitchenHelper.model.RecipeStep;
import KitchenHelper.util.BaseException;


public class FrmMain extends JFrame implements ActionListener {
	private JMenuBar menubar = new JMenuBar();
	;
	private JMenu menu_PersonalInfoManager = new JMenu("个人信息管理");
	private JMenu menu_RecipeManager = new JMenu("菜谱管理");
	private JMenu menu_BuyManager = new JMenu("采购管理");
	private JMenu menu_OrderManager = new JMenu("订单管理");

	private JMenuItem menuItem_UserManager = new JMenuItem("用户管理");
	private JMenuItem menuItem_FoodTypeManager = new JMenuItem("食材类别管理");
	private JMenuItem menuItem_FoodManager = new JMenuItem("食材管理");

	private JMenuItem menuItem_PasswordReset = new JMenuItem("密码修改");
	private JMenuItem menuItem_PersonalInfoUpdate = new JMenuItem("信息完善");

	private JMenuItem menuItem_SearchRecipe = new JMenuItem("查询菜谱");
//	private JMenuItem menuItem_CreateRecipe = new JMenuItem("创建菜谱");
	private JMenuItem menuItem_RecipeManager = new JMenuItem("菜谱管理");

	private JMenuItem menuItem_OrderCheck = new JMenuItem("订单查询");
	private JMenuItem menuItem_OrderDetail = new JMenuItem("订单详情");

	private JMenuItem menuItem_BuyCheck = new JMenuItem("采购单查询");
	private JMenuItem menuItem_BuyDetail = new JMenuItem("采购单详情");
	private JMenuItem menuItem_BuyTotal = new JMenuItem("采购食材量统计");
	private JMenuItem menuItem_BuyUpdate = new JMenuItem("采购情况登记");

	private FrmLogin dlgLogin = null;
	private JPanel statusBar = new JPanel();

//	private Object tblRecipeTitle[] = {"菜谱编号", "菜谱名称", "贡献用户", "菜谱详情", "综合得分", "收藏数", "浏览数"};
//	private Object tblRecipeData[][];
//	DefaultTableModel tabRecipeModel = new DefaultTableModel();
//	private JTable dataTableRecipe = new JTable(tabRecipeModel);
//	private Object tblStepTitle[] = {"菜谱编号", "步骤序号", "步骤详情"};
//	private Object tblStepData[][];
//	DefaultTableModel tabStepModel = new DefaultTableModel();
//	private JTable dataTableStep = new JTable(tabStepModel);
//
//	private RecipeInfo curPlan = null;
//	List<RecipeInfo> allRecipe = null;
//	List<RecipeStep> recipeSteps = null;
//
//	private void reloadRecipeTable() {//这是测试数据，需要用实际数替换
//		try {
//			allRecipe = (new RecipeManager()).loadAll();
//		} catch (BaseException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
//			return;
//		}
//		tblRecipeData = new Object[allRecipe.size()][tblRecipeTitle.length];
//		for (int i = 0; i < allRecipe.size(); i++) {
//			for (int j = 0; j < tblRecipeTitle.length; j++) {
//				tblRecipeData[i][j] = allRecipe.get(i).getCell(j);
//			}
//		}
//		tabRecipeModel.setDataVector(tblRecipeData, tblRecipeTitle);
//		this.dataTableRecipe.validate();
//		this.dataTableRecipe.repaint();
//	}
//
//	private void reloadRecipeStepTabel(int planIdx) {
//		if (planIdx < 0) {
//			return;
//		}
//		curPlan = allRecipe.get(planIdx);
//		try {
//			recipeSteps = (new RecipeManager()).loadSteps(curPlan);
//		} catch (BaseException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
//			return;
//		}
//		tblStepData = new Object[recipeSteps.size()][tblStepTitle.length];
//		for (int i = 0; i < recipeSteps.size(); i++) {
//			for (int j = 0; j < tblStepTitle.length; j++) {
//				tblStepData[i][j] = recipeSteps.get(i).getCell(j);
//			}
//		}
//
//		tabStepModel.setDataVector(tblStepData, tblStepTitle);
//		this.dataTableStep.validate();
//		this.dataTableStep.repaint();
//	}

	public FrmMain() {
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setTitle("厨房小帮手");
		dlgLogin = new FrmLogin(this, "登陆", true);
		dlgLogin.setVisible(true);
		// 菜单
		// 个人信息
		menu_PersonalInfoManager.add(menuItem_PasswordReset);
		menuItem_PasswordReset.addActionListener(this);
		menu_PersonalInfoManager.add(menuItem_PersonalInfoUpdate);
		menuItem_PersonalInfoUpdate.addActionListener(this);
		menubar.add(menu_PersonalInfoManager);
		if ("admin".equals(UserManager.currentUser.getUserType())) {
			JMenu menu_InfoManager = new JMenu("信息维护");
			menu_InfoManager.add(menuItem_UserManager);
			menuItem_UserManager.addActionListener(this);
			menu_InfoManager.add(menuItem_FoodTypeManager);
			menuItem_FoodTypeManager.addActionListener(this);
			menu_InfoManager.add(menuItem_FoodManager);
			menuItem_FoodManager.addActionListener(this);
			menubar.add(menu_InfoManager);
			menu_RecipeManager.add(menuItem_RecipeManager);
			menuItem_RecipeManager.addActionListener(this);
			menubar.add(menu_RecipeManager);
			menu_BuyManager.add(menuItem_BuyCheck);
			menuItem_BuyCheck.addActionListener(this);
			menu_BuyManager.add(menuItem_BuyDetail);
			menuItem_BuyDetail.addActionListener(this);
			menu_BuyManager.add(menuItem_BuyTotal);
			menuItem_BuyTotal.addActionListener(this);
			menu_BuyManager.add(menuItem_BuyUpdate);
			menuItem_BuyUpdate.addActionListener(this);
			menubar.add(menu_BuyManager);
		} else if ("user".equals(UserManager.currentUser.getUserType())) {
			menu_RecipeManager.add(menuItem_SearchRecipe);
			menuItem_SearchRecipe.addActionListener(this);
			menu_RecipeManager.add(menuItem_RecipeManager);
			menuItem_RecipeManager.addActionListener(this);
			menubar.add(menu_RecipeManager);
			menu_OrderManager.add(menuItem_OrderCheck);
			menuItem_OrderCheck.addActionListener(this);
			menu_OrderManager.add(menuItem_OrderDetail);
			menuItem_OrderDetail.addActionListener(this);
			menubar.add(menu_OrderManager);
		}

		this.setJMenuBar(menubar);

//		this.getContentPane().add(new JScrollPane(this.dataTableRecipe), BorderLayout.WEST);
//		this.dataTableRecipe.addMouseListener(new MouseAdapter() {
//
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				int i = FrmMain.this.dataTableRecipe.getSelectedRow();
//				if (i < 0) {
//					return;
//				}
//				FrmMain.this.reloadRecipeStepTabel(i);
//			}
//
//		});
//		this.getContentPane().add(new JScrollPane(this.dataTableStep), BorderLayout.CENTER);
//
//		this.reloadRecipeTable();
		// 状态栏
		statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel label = new JLabel("您好!" + UserManager.currentUser.getUserName());
		statusBar.add(label);
		this.getContentPane().add(statusBar, BorderLayout.SOUTH);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setVisible(true);
	}

//	@Override
//	public void actionPerformed(ActionEvent arg0) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		// TODO Auto-generated method stub
		if (e.getSource() == this.menuItem_PasswordReset) {
			FrmPasswordModify dlg = new FrmPasswordModify(this, "修改密码", true);
			dlg.setVisible(true);
		} else if (e.getSource() == this.menuItem_UserManager) {
			FrmUserManager dlg = new FrmUserManager(this, "用户管理", true);
			dlg.setVisible(true);
		} else if (e.getSource() == this.menuItem_FoodTypeManager) {
			FrmFoodTypeManager dlg = new FrmFoodTypeManager(this, "食材类别管理", true);
			dlg.setVisible(true);
		} else if (e.getSource() == this.menuItem_FoodManager) {
			FrmFoodManager dlg = new FrmFoodManager(this, "食材管理", true);
			dlg.setVisible(true);
		}
		else if (e.getSource() == this.menuItem_RecipeManager) {
			FrmRecipeManager dlg = new FrmRecipeManager(this, "菜谱管理", true);
			dlg.setVisible(true);
		}
//		else if (e.getSource() == this.menuItem_Lend) {
//			FrmLend dlg = new FrmLend(this, "借阅", true);
//			dlg.setVisible(true);
//		}
//		else if (e.getSource() == this.menuItem_Return) {
//			FrmReturn dlg = new FrmReturn(this, "归还", true);
//			dlg.setVisible(true);
//		} else if (e.getSource() == this.menuItem_BookLendSearch) {
//			FrmBookLendSearch dlg = new FrmBookLendSearch(this, "图书借阅情况查询", true);
//			dlg.setVisible(true);
//		} else if (e.getSource() == this.menuItem_ReaderLendSearch) {
//			FrmReaderLendSearch dlg = new FrmReaderLendSearch(this, "读者借阅情况查询", true);
//			dlg.setVisible(true);
//		} else if (e.getSource() == this.menuItem_BookLendStatic) {
//			FrmBookLendStatic dlg = new FrmBookLendStatic(this, "图书借阅统计", true);
//			dlg.setVisible(true);
//		} else if (e.getSource() == this.menuItem_ReaderLendStatic) {
//			FrmReaderLendStatic dlg = new FrmReaderLendStatic(this, "读者借阅统计", true);
//			dlg.setVisible(true);
//		}
	}
}
