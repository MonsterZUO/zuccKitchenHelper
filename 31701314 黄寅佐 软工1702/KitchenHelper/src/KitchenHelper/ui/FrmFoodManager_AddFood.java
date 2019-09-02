package KitchenHelper.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class FrmFoodManager_AddFood extends JDialog implements ActionListener {
	private FoodInfo food = null;

	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	private JLabel labelFoodid = new JLabel("ʳ�ı�ţ�");
	private JLabel labelFoodName = new JLabel("ʳ��������");
	private JLabel labelFoodType = new JLabel("ʳ�����");
	private JLabel labelFoodPrice = new JLabel("ʳ�ļ۸�");
	private JLabel labelFoodAmount = new JLabel("ʳ�Ŀ�棺");
	private JLabel labelFoodUnit = new JLabel("ʳ�Ĺ��");
	private JLabel labelFoodDetail = new JLabel("ʳ�����飺");

	private JTextField edtId = new JTextField(20);
	private JTextField edtName = new JTextField(20);
	private JTextField edtPrice = new JTextField(20);
	private JTextField edtAmount = new JTextField(20);
	private JTextField edtUnit = new JTextField(20);
	private JTextField edtDetail = new JTextField(20);
	private Map<String, FoodTypeInfo> foodTypeMap_name;
	private JComboBox cmbFoodtype;

	public FrmFoodManager_AddFood(JDialog f, String s, boolean b, Map<String, FoodTypeInfo> rtMap) {
		super(f, s, b);
		this.foodTypeMap_name = rtMap;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelFoodid);
		workPane.add(edtId);
		workPane.add(labelFoodName);
		workPane.add(edtName);
		workPane.add(labelFoodType);

		// ��ȡʳ�������Ϣ
		String[] strTypes = new String[this.foodTypeMap_name.size() + 1];
		strTypes[0] = "";
		java.util.Iterator<FoodTypeInfo> itRt = this.foodTypeMap_name.values().iterator();
		int i = 1;
		while (itRt.hasNext()) {
			strTypes[i] = itRt.next().getFoodTypeName();
			i++;
		}
		cmbFoodtype = new JComboBox(strTypes);
		workPane.add(cmbFoodtype);
		workPane.add(labelFoodPrice);
		workPane.add(edtPrice);
		workPane.add(labelFoodAmount);
		workPane.add(edtAmount);
		workPane.add(labelFoodUnit);
		workPane.add(edtUnit);
		workPane.add(labelFoodDetail);
		workPane.add(edtDetail);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(360, 180);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2, (int) (height - this.getHeight()) / 2);

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
			if (this.cmbFoodtype.getSelectedIndex() < 0) {
				JOptionPane.showMessageDialog(null, "��ѡ��ʳ�����", "����", JOptionPane.ERROR_MESSAGE);
				return;
			}
			FoodInfo r = new FoodInfo();
			r.setFoodNo(this.edtId.getText());
			r.setFoodName(this.edtName.getText());
			r.setFoodPrice(Double.parseDouble(this.edtPrice.getText()));
			r.setFoodAmount(Double.parseDouble(this.edtAmount.getText()));
			r.setFoodUnit(this.edtUnit.getText());
			r.setFoodDetail(this.edtDetail.getText());
			String rtName = this.cmbFoodtype.getSelectedItem().toString();
			FoodTypeInfo rt = this.foodTypeMap_name.get(rtName);
			if (rt == null) {
				JOptionPane.showMessageDialog(null, "��ѡ��ʳ�����", "����", JOptionPane.ERROR_MESSAGE);
				return;
			}
			r.setFoodTypeNo(rt.getFoodTypeNo());

			try {
				(new FoodManager()).createFood(r);
				this.food = r;
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	public FoodInfo getFood() {
		return food;
	}

}