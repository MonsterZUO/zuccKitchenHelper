package KitchenHelper.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import KitchenHelper.control.FoodManager;
import KitchenHelper.model.FoodInfo;
import KitchenHelper.model.FoodTypeInfo;
import KitchenHelper.util.BaseException;

public class FrmFoodManager_ModifyFood extends JDialog implements ActionListener {
	private FoodInfo food = null;

	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelFoodid = new JLabel("食材编号：");
	private JLabel labelFoodName = new JLabel("食材名称：");
	private JLabel labelFoodType = new JLabel("食材类别：");
	private JLabel labelFoodPrice = new JLabel("食材价格：");
	private JLabel labelFoodAmount = new JLabel("食材库存：");
	private JLabel labelFoodUnit = new JLabel("食材规格：");
	private JLabel labelFoodDetail = new JLabel("食材详情：");

	private JTextField edtId = new JTextField(20);
	private JTextField edtName = new JTextField(20);
	private JTextField edtPrice = new JTextField(20);
	private JTextField edtAmount = new JTextField(20);
	private JTextField edtUnit = new JTextField(20);
	private JTextField edtDetail = new JTextField(20);
	private Map<String, FoodTypeInfo> foodTypeMap_name = null;
	private JComboBox cmbFoodtype = null;

	public FrmFoodManager_ModifyFood(JDialog f, String s, boolean b, Map<String, FoodTypeInfo> rtMap, FoodInfo r) {
		super(f, s, b);
		this.food = r;
		this.foodTypeMap_name = rtMap;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelFoodid);
		this.edtId.setEnabled(false);
		this.edtId.setText(this.food.getFoodNo());
		workPane.add(edtId);
		workPane.add(labelFoodName);
		this.edtName.setText(r.getFoodName());
		workPane.add(edtName);
		workPane.add(labelFoodType);
		// 提取类别信息
		String[] strTypes = new String[this.foodTypeMap_name.size() + 1];
		strTypes[0] = "";
		java.util.Iterator<FoodTypeInfo> itRt = this.foodTypeMap_name.values().iterator();
		int i = 1;
		int oldIndex = 0;
		while (itRt.hasNext()) {
			FoodTypeInfo rt = itRt.next();
			strTypes[i] = rt.getFoodTypeName();
			if (this.food.getFoodTypeNo() == rt.getFoodTypeNo()) {
				oldIndex = i;
			}
			i++;
		}
		cmbFoodtype = new JComboBox(strTypes);
		this.cmbFoodtype.setSelectedIndex(oldIndex);
		workPane.add(cmbFoodtype);
		workPane.add(labelFoodPrice);
		this.edtPrice.setText(String.valueOf(r.getFoodPrice()));
		workPane.add(edtPrice);
		workPane.add(labelFoodAmount);
		this.edtAmount.setText(String.valueOf(r.getFoodAmount()));
		workPane.add(edtAmount);
		workPane.add(labelFoodUnit);
		this.edtUnit.setText(r.getFoodUnit());
		workPane.add(edtUnit);
		workPane.add(labelFoodDetail);
		this.edtDetail.setText(r.getFoodDetail());
		workPane.add(edtDetail);

		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(360, 180);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2, (int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				FrmFoodManager_ModifyFood.this.food = null;
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnCancel) {
			this.setVisible(false);
			this.food = null;
			return;
		} else if (e.getSource() == this.btnOk) {
			if (this.cmbFoodtype.getSelectedIndex() < 0) {
				JOptionPane.showMessageDialog(null, "请选择食材类别", "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
			String id = (this.edtId.getText() != "") ? this.edtId.getText() : food.getFoodNo();
			String name = (this.edtName.getText() != "") ? this.edtName.getText() : food.getFoodName();
			double price = (this.edtPrice.getText() != "") ? Double.parseDouble(this.edtPrice.getText())
					: food.getFoodPrice();
			double amount = (this.edtAmount.getText() != "") ? Double.parseDouble(this.edtAmount.getText())
					: food.getFoodAmount();
			String unit = (this.edtUnit.getText() != "") ? this.edtUnit.getText() : food.getFoodUnit();
			String detail = (this.edtDetail.getText() != "") ? this.edtDetail.getText() : food.getFoodDetail();
			String rtName = this.cmbFoodtype.getSelectedItem().toString();
			FoodInfo r = new FoodInfo();
			r.setFoodNo(id);
			r.setFoodName(name);
			r.setFoodPrice(price);
			r.setFoodAmount(amount);
			r.setFoodUnit(unit);
			r.setFoodDetail(detail);
			FoodTypeInfo rt = this.foodTypeMap_name.get(rtName);
			if (rt == null) {
				JOptionPane.showMessageDialog(null, "请选择食材类别", "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
			r.setFoodTypeNo(rt.getFoodTypeNo());
			try {
				(new FoodManager()).modifyFood(r);
				JOptionPane.showMessageDialog(null, "食材已修改", "提示", JOptionPane.INFORMATION_MESSAGE);
//				if (this.food.getFoodTypeNo() != rt.getFoodTypeNo()) {
//					(new FoodManager()).changeFoodType(this.food.getFoodNo(), rt.getFoodTypeNo());
//				}
//				if (!this.getFood().getFoodName().equals(name)) {
//					(new FoodManager()).renameFood(this.getFood().getFoodNo(), name);
//				}
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	public FoodInfo getFood() {
		return food;
	}

}