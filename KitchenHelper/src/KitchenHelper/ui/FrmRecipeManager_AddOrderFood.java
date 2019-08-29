package KitchenHelper.ui;

import KitchenHelper.control.OrderManager;
import KitchenHelper.control.RecipeManager;
import KitchenHelper.model.*;
import KitchenHelper.util.BaseException;

import javax.swing.*;
import javax.xml.stream.events.Comment;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class FrmRecipeManager_AddOrderFood extends JDialog implements ActionListener {
	private FoodOrder order = null;
	private OrderDetail orderDetail = null;

	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelFoodName = new JLabel("食材：");
	private JLabel labelAmount = new JLabel("数量：");

	private Map<String, FoodInfo> foodMap_name;
	private JComboBox cmbFoodName;
	private JTextField edtAmount = new JTextField(20);

	public FrmRecipeManager_AddOrderFood(JDialog f, String s, boolean b, Map<String, FoodInfo> foodMap_name, FoodOrder order) {
		super(f, s, b);
		this.foodMap_name = foodMap_name;
		this.order = order;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelFoodName);

		//提取食材名称
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

		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 180);
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
			OrderDetail orderDetail = new OrderDetail();
			String fName = this.cmbFoodName.getSelectedItem().toString();
			FoodInfo f = this.foodMap_name.get(fName);
			if (f == null) {
				JOptionPane.showMessageDialog(null, "请选择食材", "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
			orderDetail.setFoodNo(f.getFoodNo());
			orderDetail.setOrderNo(order.getOrderNo());
			orderDetail.setAmount(Double.parseDouble(this.edtAmount.getText()));
			try {
				(new OrderManager()).addOrderFood(orderDetail);
				this.orderDetail = orderDetail;
				this.setVisible(false);
			} catch (BaseException e1) {
				this.order = null;
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

	}
	public OrderDetail getOrderDetail() {
		return orderDetail;
	}

}

