package KitchenHelper.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import KitchenHelper.control.FoodManager;
import KitchenHelper.model.FoodInfo;
import KitchenHelper.model.FoodTypeInfo;
import KitchenHelper.util.BaseException;

public class FrmFoodManager extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加");
	private Button btnModify = new Button("修改");
	private Button btnDelete = new Button("删除食材");
	private Map<String, FoodTypeInfo> foodTypeMap_name = new HashMap<String, FoodTypeInfo>();
	private Map<Integer, FoodTypeInfo> foodTypeMap_id = new HashMap<Integer, FoodTypeInfo>();
	private JComboBox cmbFoodtype = null;

	private JTextField edtKeyword = new JTextField(10);
	private Button btnSearch = new Button("查询");

	private Object tblTitle[] = { "食材编号", "食材名称", "食材类别", "食材价格", "库存数量", "食材规格", "食材详情" };

	private Object tblData[][];
	List<FoodInfo> foods = null;
	DefaultTableModel tablmod = new DefaultTableModel();
	private JTable foodTable = new JTable(tablmod);

	private void reloadTable() {
		try {
			int n = this.cmbFoodtype.getSelectedIndex();
			int rtId = 0;
			if (n >= 0) {
				String rtname = this.cmbFoodtype.getSelectedItem().toString();
				FoodTypeInfo rt = this.foodTypeMap_name.get(rtname);
				if (rt != null)
					rtId = rt.getFoodTypeNo();
			}
			foods = (new FoodManager()).searchFood(this.edtKeyword.getText(), rtId);
			tblData = new Object[foods.size()][7];
			System.out.println(foods.size());
			for (int i = 0; i < foods.size(); i++) {
				tblData[i][0] = foods.get(i).getFoodNo();
				tblData[i][1] = foods.get(i).getFoodName();
				FoodTypeInfo t = this.foodTypeMap_id.get(foods.get(i).getFoodTypeNo());
				tblData[i][2] = t == null ? "" : t.getFoodTypeName();
				tblData[i][3] = foods.get(i).getFoodPrice() + "";
				tblData[i][4] = foods.get(i).getFoodAmount();
				tblData[i][5] = foods.get(i).getFoodUnit();
				tblData[i][6] = foods.get(i).getFoodDetail();
			}
			tablmod.setDataVector(tblData, tblTitle);
			this.foodTable.validate();
			this.foodTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public FrmFoodManager(Frame f, String s, boolean b) {
		super(f, s, b);
		// 提取读者类别信息
		List<FoodTypeInfo> types = null;
		try {
			types = (new FoodManager()).loadAllFoodType();
			String[] strTypes = new String[types.size() + 1];
			strTypes[0] = "";
			for (int i = 0; i < types.size(); i++) {
				foodTypeMap_name.put(types.get(i).getFoodTypeName(), types.get(i));
				foodTypeMap_id.put(types.get(i).getFoodTypeNo(), types.get(i));
				strTypes[i + 1] = types.get(i).getFoodTypeName();
			}
			cmbFoodtype = new JComboBox(strTypes);
		} catch (BaseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(btnModify);
		toolBar.add(this.btnDelete);
		toolBar.add(cmbFoodtype);
		toolBar.add(edtKeyword);
		toolBar.add(btnSearch);

		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		// 提取现有数据
		this.reloadTable();
		this.getContentPane().add(new JScrollPane(this.foodTable), BorderLayout.CENTER);

		// 屏幕居中显示
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2, (int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnAdd.addActionListener(this);
		this.btnModify.addActionListener(this);
		this.btnDelete.addActionListener(this);
		this.btnSearch.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// System.exit(0);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this.btnAdd) {
			FrmFoodManager_AddFood dlg = new FrmFoodManager_AddFood(this, "添加食材", true, this.foodTypeMap_name);
			dlg.setVisible(true);
			if (dlg.getFood() != null) {// 刷新表格
				this.reloadTable();
			}
		} else if (e.getSource() == this.btnModify) {
			int i = this.foodTable.getSelectedRow();
			if (i < 0) {
				JOptionPane.showMessageDialog(null, "请选择食材", "提示", JOptionPane.ERROR_MESSAGE);
				return;
			}
			FoodInfo food = this.foods.get(i);

			FrmFoodManager_ModifyFood dlg = new FrmFoodManager_ModifyFood(this, "修改读者", true, this.foodTypeMap_name,
					food);
			dlg.setVisible(true);
			if (dlg.getFood() != null) {// 刷新表格
				this.reloadTable();
			}
		}
//		else if (e.getSource() == this.btnDelete) {
//			int i = this.foodTable.getSelectedRow();
//			if (i < 0) {
//				JOptionPane.showMessageDialog(null, "请选择读者", "提示", JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//			FoodInfo food = this.foods.get(i);
//			if (JOptionPane.showConfirmDialog(this, "确定删除该读者吗？", "确认",
//					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
//				try {
//					(new FoodManager()).removeFood(food.getReaderid(), SystemUserManager.currentUser.getUserid());
//					this.reloadTable();
//				} catch (BaseException e1) {
//					JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
//				}
//
//			}
//		} else if (e.getSource() == this.btnSearch) {
//			this.reloadTable();
//		}
	}
}