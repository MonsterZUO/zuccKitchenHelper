package KitchenHelper.ui;

import KitchenHelper.control.BuyManager;
import KitchenHelper.control.OrderManager;
import KitchenHelper.model.FoodBuy;
import KitchenHelper.model.FoodInfo;
import KitchenHelper.model.OrderDetail;
import KitchenHelper.model.RecipeInfo;
import KitchenHelper.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;


public class FrmBuyManager_ModifyBuy extends JDialog implements ActionListener {
	private FoodBuy foodBuy = null;

	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelFoodName = new JLabel("食材：");
	private JLabel labelAmount = new JLabel("食材数量：");

	private JTextField edtAmount = new JTextField(20);
	private Map<String, FoodInfo> foodMap_name;
	private JComboBox cmbFoodName;

	public FrmBuyManager_ModifyBuy(JDialog f, String s, boolean b, Map<String, FoodInfo> foodMap_name, FoodBuy r) {
		super(f, s, b);
		this.foodMap_name = foodMap_name;
		this.foodBuy = r;
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
		int oldIndex = 0;
		while (itRt.hasNext()) {
			FoodInfo fn = itRt.next();
			strTypes[i] = fn.getFoodName();
			if(this.foodBuy.getFoodNo().equals(fn.getFoodNo())){
				oldIndex = i;
			}
			i++;
		}
		cmbFoodName = new JComboBox(strTypes);
		this.cmbFoodName.setSelectedIndex(oldIndex);
		workPane.add(cmbFoodName);
		workPane.add(labelAmount);
		workPane.add(edtAmount);

		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(313, 180);
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
			FoodBuy foodBuy = new FoodBuy();
			String fName = this.cmbFoodName.getSelectedItem().toString();
			FoodInfo f = this.foodMap_name.get(fName);
			if (f == null) {
				JOptionPane.showMessageDialog(null, "请选择食材", "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
			foodBuy.setFoodNo(f.getFoodNo());
			foodBuy.setAmount(Double.parseDouble(this.edtAmount.getText()));
			foodBuy.setBuyNo(this.foodBuy.getBuyNo());
			try {
				(new BuyManager()).modifyBuy(foodBuy);
				this.foodBuy = foodBuy;
				this.setVisible(false);
			} catch (BaseException e1) {
				this.foodBuy = null;
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

	}

	public FoodBuy getFoodBuy() {
		return foodBuy;
	}

}



