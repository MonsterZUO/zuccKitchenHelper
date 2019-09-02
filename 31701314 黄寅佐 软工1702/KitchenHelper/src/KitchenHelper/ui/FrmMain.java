package KitchenHelper.ui;

import KitchenHelper.control.UserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


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

	public FrmMain() {
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setTitle("����С����");
		dlgLogin = new FrmLogin(this, "��½", true);
		dlgLogin.setVisible(true);
		// �˵�
		// ������Ϣ
		menu_PersonalInfoManager.add(menuItem_PasswordReset);
		menuItem_PasswordReset.addActionListener(this);
//		menu_PersonalInfoManager.add(menuItem_PersonalInfoUpdate);
//		menuItem_PersonalInfoUpdate.addActionListener(this);
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
//			menu_BuyManager.add(menuItem_BuyCheck);
//			menuItem_BuyCheck.addActionListener(this);
			menu_BuyManager.add(menuItem_BuyDetail);
			menuItem_BuyDetail.addActionListener(this);
//			menu_BuyManager.add(menuItem_BuyTotal);
//			menuItem_BuyTotal.addActionListener(this);
//			menu_BuyManager.add(menuItem_BuyUpdate);
//			menuItem_BuyUpdate.addActionListener(this);
			menubar.add(menu_BuyManager);
		} else if ("user".equals(UserManager.currentUser.getUserType())) {
//			menu_RecipeManager.add(menuItem_SearchRecipe);
//			menuItem_SearchRecipe.addActionListener(this);
			menu_RecipeManager.add(menuItem_RecipeManager);
			menuItem_RecipeManager.addActionListener(this);
			menubar.add(menu_RecipeManager);
//			menu_OrderManager.add(menuItem_OrderCheck);
//			menuItem_OrderCheck.addActionListener(this);
			menu_OrderManager.add(menuItem_OrderDetail);
			menuItem_OrderDetail.addActionListener(this);
			menubar.add(menu_OrderManager);
		}

		this.setJMenuBar(menubar);
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
		} else if (e.getSource() == this.menuItem_RecipeManager) {
			FrmRecipeManager dlg = new FrmRecipeManager(this, "���׹���", true);
			dlg.setVisible(true);
		} else if (e.getSource() == this.menuItem_OrderDetail) {
			FrmOrderManager_OrderDetail dlg = new FrmOrderManager_OrderDetail(this, "��������", true);
			dlg.setVisible(true);
		} else if (e.getSource() == this.menuItem_BuyDetail) {
			FrmBuyManager dlg = new FrmBuyManager(this, "�ɹ�����", true);
			dlg.setVisible(true);
		}
	}
}
