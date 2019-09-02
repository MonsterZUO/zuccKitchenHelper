package KitchenHelper.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import KitchenHelper.control.FoodManager;
import KitchenHelper.model.FoodTypeInfo;
import KitchenHelper.util.BaseException;

public class FrmFoodTypeManager_AddFoodType extends JDialog implements ActionListener {
	private FoodTypeInfo foodtype = null;

	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelName = new JLabel("类别名称：");
	private JLabel labelDetail = new JLabel("类别详情：");

	private JTextField edtName = new JTextField(20);
	private JTextField edtDetail = new JTextField(20);

	public FrmFoodTypeManager_AddFoodType(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelName);
		workPane.add(edtName);
		workPane.add(labelDetail);
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

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnCancel) {
			this.setVisible(false);
			return;
		} else if (e.getSource() == this.btnOk) {
			String name = this.edtName.getText();
			String detail = this.edtDetail.getText();
			this.foodtype = new FoodTypeInfo();
			this.foodtype.setFoodTypeName(name);
			this.foodtype.setFoodTypeDetail(detail);
			try {
				(new FoodManager()).createFoodType(this.foodtype);
				JOptionPane.showMessageDialog(null, "食材类别添加完成", "提示", JOptionPane.INFORMATION_MESSAGE);
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