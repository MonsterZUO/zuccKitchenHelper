package KitchenHelper.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import KitchenHelper.control.FoodManager;
import KitchenHelper.model.FoodTypeInfo;
import KitchenHelper.util.BaseException;

public class FrmFoodTypeManager_ModifyFoodType extends JDialog implements ActionListener {
	private FoodTypeInfo foodtype = null;

	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelName = new JLabel("类别名称：");
	private JLabel labelDetail = new JLabel("类别详情：");

	private JTextField edtName = new JTextField(20);
	private JTextField edtDetail = new JTextField(20);

	public FrmFoodTypeManager_ModifyFoodType(JDialog f, String s, boolean b, FoodTypeInfo rt) {
		super(f, s, b);
		this.foodtype = rt;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelName);
		this.edtName.setText(rt.getFoodTypeName());
		workPane.add(edtName);
		workPane.add(labelDetail);
		this.edtDetail.setText(rt.getFoodTypeDetail() + "");
		workPane.add(edtDetail);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(360, 140);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2, (int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				FrmFoodTypeManager_ModifyFoodType.this.foodtype = null;
			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnCancel) {
			this.setVisible(false);
			this.foodtype = null;
			return;
		} else if (e.getSource() == this.btnOk) {
			String name = this.edtName.getText();
			String detail = this.edtDetail.getText();
			this.foodtype.setFoodTypeDetail(detail);
			this.foodtype.setFoodTypeName(name);
			try {
				(new FoodManager()).modifyFoodType(this.foodtype);
				this.setVisible(false);
			} catch (BaseException e1) {
				this.foodtype = null;
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	public FoodTypeInfo getFoodtype() {
		return foodtype;
	}

}