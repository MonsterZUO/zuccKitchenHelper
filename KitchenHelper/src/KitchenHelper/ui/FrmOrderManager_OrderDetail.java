package KitchenHelper.ui;

import KitchenHelper.control.FoodManager;
import KitchenHelper.control.OrderManager;
import KitchenHelper.control.RecipeManager;
import KitchenHelper.control.UserManager;
import KitchenHelper.model.*;
import KitchenHelper.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrmOrderManager_OrderDetail extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("��Ӷ���");
	private Button btnModify = new Button("�޸Ķ���");
	private Button btnDelete = new Button("ɾ������");
	private Button btnAddFood = new Button("���ʳ��");
	private Button btnModifyFood = new Button("�޸�ʳ��");
	private Button btnDeleteFood = new Button("ɾ��ʳ��");

	private Map<String, FoodInfo> foodMap_name = new HashMap<String, FoodInfo>();
	private Map<String, FoodInfo> foodMap_id = new HashMap<String, FoodInfo>();

	private JTextField edtKeyword = new JTextField(10);
	private Button btnSearch = new Button("��ѯ");
	private Object tblOrderTitle[] = {"�������", "�ջ�������", "�ջ���ַ", "�ջ����ֻ�����", "����״̬"};
	private Object tblOrderData[][];
	DefaultTableModel tabOrderModel = new DefaultTableModel();
	private JTable dataTableOrder = new JTable(tabOrderModel);

	private Object tblOrderDetailTitle[] = {"�������", "ʳ������", "����", "�۸�", "�ۿ�"};
	private Object tblOrderDetailData[][];
	DefaultTableModel tabOrderDetailModel = new DefaultTableModel();
	private JTable dataTableOrderDetail = new JTable(tabOrderDetailModel);

	private FoodOrder curOrder = null;
	List<FoodOrder> allOrders = null;
	List<OrderDetail> orderDetails = null;
	private JComboBox cmbFoodName = null;


	private void reloadOrderTable() {//���ǲ������ݣ���Ҫ��ʵ�����滻
		try {
			allOrders = (new OrderManager()).loadAll(this.edtKeyword.getText(), UserManager.currentUser);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblOrderData = new Object[allOrders.size()][tblOrderTitle.length];
		for (int i = 0; i < allOrders.size(); i++) {
			for (int j = 0; j < tblOrderTitle.length; j++) {
				tblOrderData[i][j] = allOrders.get(i).getCell(j);
			}
		}
		tabOrderModel.setDataVector(tblOrderData, tblOrderTitle);
		this.dataTableOrder.validate();
		this.dataTableOrder.repaint();
	}

	private void reloadOrderDetailTable(int planIdx) {//���ǲ������ݣ���Ҫ��ʵ�����滻
		if (planIdx < 0) {
			return;
		}
		curOrder = allOrders.get(planIdx);
		try {
			orderDetails = (new OrderManager()).loadOrderDetail(curOrder);
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblOrderDetailData = new Object[orderDetails.size()][tblOrderDetailTitle.length];
		for (int i = 0; i < orderDetails.size(); i++) {
			for (int j = 0; j < tblOrderDetailTitle.length; j++) {
				tblOrderDetailData[i][j] = orderDetails.get(i).getCell(j);
			}
		}
		tabOrderDetailModel.setDataVector(tblOrderDetailData, tblOrderDetailTitle);
		this.dataTableOrderDetail.validate();
		this.dataTableOrderDetail.repaint();
	}

	public FrmOrderManager_OrderDetail(Frame f, String s, boolean b) {
		super(f, s, b);
		//��ȡʳ��������Ϣ
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
		// ��ȡ��������
		this.getContentPane().add(new JScrollPane(this.dataTableOrder), BorderLayout.WEST);
		this.dataTableOrder.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int i = FrmOrderManager_OrderDetail.this.dataTableOrder.getSelectedRow();
				if (i < 0) {
					return;
				}
				FrmOrderManager_OrderDetail.this.reloadOrderDetailTable(i);
			}

		});
		this.getContentPane().add(new JScrollPane(this.dataTableOrderDetail), BorderLayout.CENTER);

		this.reloadOrderTable();

		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(btnModify);
		toolBar.add(this.btnDelete);
		toolBar.add(btnAddFood);
		toolBar.add(btnModifyFood);
		toolBar.add(this.btnDeleteFood);
		toolBar.add(edtKeyword);
		toolBar.add(btnSearch);

		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		// ��Ļ������ʾ
		this.setSize(1394, 727);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2, (int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnAdd.addActionListener(this);
		this.btnModify.addActionListener(this);
		this.btnDelete.addActionListener(this);

		this.btnAddFood.addActionListener(this);
		this.btnModifyFood.addActionListener(this);
		this.btnDeleteFood.addActionListener(this);

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
		if (e.getSource() == this.btnAddFood) {
			int i = this.dataTableOrder.getSelectedRow();
			if (i < 0) {
				JOptionPane.showMessageDialog(null, "��ѡ�񶩵�", "��ʾ", JOptionPane.ERROR_MESSAGE);
				return;
			}
			FoodOrder order = this.allOrders.get(i);
			FrmRecipeManager_AddOrderFood dlg = new FrmRecipeManager_AddOrderFood(this, "���ʳ��", true, this.foodMap_name,
					order);
			dlg.setVisible(true);
			if (dlg.getOrderDetail() != null) {// ˢ�±��
				this.reloadOrderDetailTable(i);
			}
		} else if (e.getSource() == this.btnModifyFood) {
			int i = this.dataTableOrderDetail.getSelectedRow();
			if (i < 0) {
				JOptionPane.showMessageDialog(null, "��ѡ�񶩵�", "��ʾ", JOptionPane.ERROR_MESSAGE);
				return;
			}
			OrderDetail orderDetail = this.orderDetail.get(i);
			FrmRecipeManager_ModifyOrderFood dlg = new FrmRecipeManager_ModifyOrderFood(this, "�޸�ʳ��", true, this.foodMap_name,
					orderDetail);
			dlg.setVisible(true);
			if (dlg.getOrderDetail() != null) {// ˢ�±��
				this.reloadOrderDetailTable(i);
			}
		} else if (e.getSource() == this.btnDeleteFood) {
			int i = this.dataTableOrderDetail.getSelectedRow();
			if (i < 0) {
				JOptionPane.showMessageDialog(null, "��ѡ�񶩵�", "��ʾ", JOptionPane.ERROR_MESSAGE);
				return;
			}
			OrderDetail orderDetail = this.orderDetail.get(i);
			if (JOptionPane.showConfirmDialog(this, "ȷ��ɾ����ʳ����", "ȷ��",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				try {
					(new OrderManager()).removeOrderFood(orderDetail);
					this.reloadOrderDetailTable(i);
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
				}

			}
		}
	}
}
