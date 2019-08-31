package KitchenHelper.ui;

import KitchenHelper.control.BuyManager;
import KitchenHelper.control.FoodManager;
import KitchenHelper.control.RecipeManager;
import KitchenHelper.model.FoodBuy;
import KitchenHelper.model.FoodInfo;
import KitchenHelper.model.RecipeInfo;
import KitchenHelper.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrmBuyManager extends JDialog implements ActionListener {
	DefaultTableModel tablmod = new DefaultTableModel();
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加菜谱");
	private Button btnModify = new Button("修改菜谱");
	private Button btnDelete = new Button("删除菜谱");
	private JTextField edtKeyword = new JTextField(10);
	private Button btnSearch = new Button("查询");
	private Map<String, FoodInfo> foodMap_name = new HashMap<String, FoodInfo>();
	private Map<String, FoodInfo> foodMap_id = new HashMap<String, FoodInfo>();
	private JComboBox cmbFoodName = null;
	private Object tblTitle[] = {"采购单号", "名称", "数量", "状态"};
	private Object tblData[][];
	private JTable dataTable = new JTable(tablmod);

	List<FoodBuy> records = null;

	private void reloadTable() {
		try {
			records = (new BuyManager()).loadAll(this.edtKeyword.getText());
			tblData = new Object[records.size()][tblTitle.length];
			for (int i = 0; i < records.size(); i++) {
				tblData[i][0] = records.get(i).getBuyNo();
				tblData[i][1] = records.get(i).getFoodName();
				tblData[i][2] = records.get(i).getAmount();
				tblData[i][3] = records.get(i).getBuyStatus();
			}
			tablmod.setDataVector(tblData, tblTitle);
			this.dataTable.validate();
			this.dataTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public FrmBuyManager(Frame f, String s, boolean b) {
		super(f, s, b);
		//创建MAP
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
		//提取现有数据
		this.reloadTable();
		this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);


		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(btnModify);
		toolBar.add(this.btnDelete);
		toolBar.add(edtKeyword);
		toolBar.add(btnSearch);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		// 屏幕居中显示
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnAdd.addActionListener(this);
		this.btnModify.addActionListener(this);
		this.btnDelete.addActionListener(this);
		this.btnSearch.addActionListener(this);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSearch) {
			this.reloadTable();
		} else if (e.getSource() == this.btnAdd) {
			FrmBuyManager_AddBuy dlg = new FrmBuyManager_AddBuy(this, "添加采购单", true, foodMap_name);
			dlg.setVisible(true);
			if (dlg.getFoodBuy() != null) {// 刷新表格
				this.reloadTable();
			}
		} else if (e.getSource() == this.btnModify) {
			int i = this.dataTable.getSelectedRow();
			if (i < 0) {
				JOptionPane.showMessageDialog(this, "请选择采购单", "提示", JOptionPane.ERROR_MESSAGE);
				return;
			}
			FoodBuy buy = this.records.get(i);

			FrmBuyManager_ModifyBuy dlg = new FrmBuyManager_ModifyBuy(this, "修改采购单", true, foodMap_name, buy);
			dlg.setVisible(true);
			if (dlg.getFoodBuy() != null) {// 刷新表格
				this.reloadTable();
			}
		} else if (e.getSource() == this.btnDelete) {
			int i = this.dataTable.getSelectedRow();
			if (i < 0) {
				JOptionPane.showMessageDialog(null, "请选择采购单", "提示", JOptionPane.ERROR_MESSAGE);
				return;
			}
			FoodBuy buy = this.records.get(i);
			if (JOptionPane.showConfirmDialog(this, "确定删除该采购单吗？", "确认",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				try {
					(new BuyManager()).removeBuy(buy.getBuyNo());
					this.reloadTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
				}

			}
		}
	}
}
