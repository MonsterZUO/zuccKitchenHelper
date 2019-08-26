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
	private JMenu menu_PersonalInfoManager = new JMenu("������Ϣ����");
	private JMenu menu_RecipeManager = new JMenu("���׹���");
	private JMenu menu_BuyManager = new JMenu("�ɹ�����");
	private JMenu menu_OrderManager = new JMenu("��������");

	private JMenuItem menuItem_UserManager = new JMenuItem("�û�����");
	private JMenuItem menuItem_FoodTypeManager = new JMenuItem("ʳ��������");
	private JMenuItem menuItem_FoodManager = new JMenuItem("ʳ�Ĺ���");

	private JMenuItem menuItem_PasswordReset = new JMenuItem("�����޸�");
	private JMenuItem menuItem_PersonalInfoUpdate = new JMenuItem("��Ϣ����");

	private JMenuItem menuItem_SearchRecipe = new JMenuItem("��ѯ����");
//	private JMenuItem menuItem_CreateRecipe = new JMenuItem("��������");
	private JMenuItem menuItem_RecipeManager = new JMenuItem("���׹���");

	private JMenuItem menuItem_OrderCheck = new JMenuItem("������ѯ");
	private JMenuItem menuItem_OrderDetail = new JMenuItem("��������");

	private JMenuItem menuItem_BuyCheck = new JMenuItem("�ɹ�����ѯ");
	private JMenuItem menuItem_BuyDetail = new JMenuItem("�ɹ�������");
	private JMenuItem menuItem_BuyTotal = new JMenuItem("�ɹ�ʳ����ͳ��");
	private JMenuItem menuItem_BuyUpdate = new JMenuItem("�ɹ�����Ǽ�");

	private FrmLogin dlgLogin = null;
	private JPanel statusBar = new JPanel();

//	private Object tblRecipeTitle[] = {"���ױ��", "��������", "�����û�", "��������", "�ۺϵ÷�", "�ղ���", "�����"};
//	private Object tblRecipeData[][];
//	DefaultTableModel tabRecipeModel = new DefaultTableModel();
//	private JTable dataTableRecipe = new JTable(tabRecipeModel);
//	private Object tblStepTitle[] = {"���ױ��", "�������", "��������"};
//	private Object tblStepData[][];
//	DefaultTableModel tabStepModel = new DefaultTableModel();
//	private JTable dataTableStep = new JTable(tabStepModel);
//
//	private RecipeInfo curPlan = null;
//	List<RecipeInfo> allRecipe = null;
//	List<RecipeStep> recipeSteps = null;
//
//	private void reloadRecipeTable() {//���ǲ������ݣ���Ҫ��ʵ�����滻
//		try {
//			allRecipe = (new RecipeManager()).loadAll();
//		} catch (BaseException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
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
//			JOptionPane.showMessageDialog(null, e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
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
		this.setTitle("����С����");
		dlgLogin = new FrmLogin(this, "��½", true);
		dlgLogin.setVisible(true);
		// �˵�
		// ������Ϣ
		menu_PersonalInfoManager.add(menuItem_PasswordReset);
		menuItem_PasswordReset.addActionListener(this);
		menu_PersonalInfoManager.add(menuItem_PersonalInfoUpdate);
		menuItem_PersonalInfoUpdate.addActionListener(this);
		menubar.add(menu_PersonalInfoManager);
		if ("admin".equals(UserManager.currentUser.getUserType())) {
			JMenu menu_InfoManager = new JMenu("��Ϣά��");
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
		// ״̬��
		statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel label = new JLabel("����!" + UserManager.currentUser.getUserName());
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
			FrmPasswordModify dlg = new FrmPasswordModify(this, "�޸�����", true);
			dlg.setVisible(true);
		} else if (e.getSource() == this.menuItem_UserManager) {
			FrmUserManager dlg = new FrmUserManager(this, "�û�����", true);
			dlg.setVisible(true);
		} else if (e.getSource() == this.menuItem_FoodTypeManager) {
			FrmFoodTypeManager dlg = new FrmFoodTypeManager(this, "ʳ��������", true);
			dlg.setVisible(true);
		} else if (e.getSource() == this.menuItem_FoodManager) {
			FrmFoodManager dlg = new FrmFoodManager(this, "ʳ�Ĺ���", true);
			dlg.setVisible(true);
		}
		else if (e.getSource() == this.menuItem_RecipeManager) {
			FrmRecipeManager dlg = new FrmRecipeManager(this, "���׹���", true);
			dlg.setVisible(true);
		}
//		else if (e.getSource() == this.menuItem_Lend) {
//			FrmLend dlg = new FrmLend(this, "����", true);
//			dlg.setVisible(true);
//		}
//		else if (e.getSource() == this.menuItem_Return) {
//			FrmReturn dlg = new FrmReturn(this, "�黹", true);
//			dlg.setVisible(true);
//		} else if (e.getSource() == this.menuItem_BookLendSearch) {
//			FrmBookLendSearch dlg = new FrmBookLendSearch(this, "ͼ����������ѯ", true);
//			dlg.setVisible(true);
//		} else if (e.getSource() == this.menuItem_ReaderLendSearch) {
//			FrmReaderLendSearch dlg = new FrmReaderLendSearch(this, "���߽��������ѯ", true);
//			dlg.setVisible(true);
//		} else if (e.getSource() == this.menuItem_BookLendStatic) {
//			FrmBookLendStatic dlg = new FrmBookLendStatic(this, "ͼ�����ͳ��", true);
//			dlg.setVisible(true);
//		} else if (e.getSource() == this.menuItem_ReaderLendStatic) {
//			FrmReaderLendStatic dlg = new FrmReaderLendStatic(this, "���߽���ͳ��", true);
//			dlg.setVisible(true);
//		}
	}
}
