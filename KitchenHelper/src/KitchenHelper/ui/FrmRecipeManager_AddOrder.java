package KitchenHelper.ui;

import KitchenHelper.control.OrderManager;
import KitchenHelper.control.UserManager;
import KitchenHelper.model.FoodOrder;
import KitchenHelper.model.OrderDetail;
import KitchenHelper.model.RecipeInfo;
import KitchenHelper.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmRecipeManager_AddOrder extends JDialog implements ActionListener {
	private FoodOrder order;

	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelReceiverName = new JLabel("收件人姓名：");
	private JLabel labelAddress = new JLabel("收件地址：");
	private JLabel labelPhone = new JLabel("手机号码：");


	private JTextField edtReceiverName = new JTextField(20);
	private JTextField edtAddress = new JTextField(20);
	private JTextField edtPhone = new JTextField(20);

	public FrmRecipeManager_AddOrder(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelReceiverName);
		workPane.add(edtReceiverName);
		workPane.add(labelAddress);
		workPane.add(edtAddress);
		workPane.add(labelPhone);
		workPane.add(edtPhone);

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
			FoodOrder fo = new FoodOrder();
			fo.setReceiverName(this.edtReceiverName.getText());
			fo.setAddress(this.edtAddress.getText());
			fo.setPhone(this.edtPhone.getText());
			try {
				this.order = (new OrderManager()).addFoodOrder(fo, UserManager.currentUser);
				this.setVisible(false);
			} catch (BaseException e1) {
				this.order = null;
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	}
	public FoodOrder getOrder() {
		return this.order;
	}
}

