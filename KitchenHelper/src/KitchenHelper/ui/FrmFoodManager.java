package KitchenHelper.ui;

import KitchenHelper.control.FoodManager;
import KitchenHelper.model.FoodInfo;
import KitchenHelper.model.FoodTypeInfo;
import KitchenHelper.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrmFoodManager extends JDialog implements ActionListener {
	List<FoodInfo> foods = null;
	DefaultTableModel tablmod = new DefaultTableModel();
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("���");
	private Button btnModify = new Button("�޸�");
	private Button btnDelete = new Button("ɾ��ʳ��");
	private Map<String, FoodTypeInfo> foodTypeMap_name = new HashMap<String, FoodTypeInfo>();
	private Map<Integer, FoodTypeInfo> foodTypeMap_id = new HashMap<Integer, FoodTypeInfo>();
	private JComboBox cmbFoodtype = null;
	private JTextField edtKeyword = new JTextField(10);
	private Button btnSearch = new Button("��ѯ");
	private Object tblTitle[] = {"ʳ�ı��", "ʳ������", "ʳ�����", "ʳ�ļ۸�", "�������", "ʳ�Ĺ��", "ʳ������"};
	private Object tblData[][];
	private JTable foodTable = new JTable(tablmod);

	public FrmFoodManager(Frame f, String s, boolean b) {
		super(f, s, b);
		// ��ȡʳ�������Ϣ
		List<FoodTypeInfo> types;
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
		// ��ȡ��������
		this.reloadTable();
		this.getContentPane().add(new JScrollPane(this.foodTable), BorderLayout.CENTER);

		// ��Ļ������ʾ
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this.btnAdd) {
			FrmFoodManager_AddFood dlg = new FrmFoodManager_AddFood(this, "���ʳ��", true, this.foodTypeMap_name);
			dlg.setVisible(true);
			if (dlg.getFood() != null) {// ˢ�±��
				this.reloadTable();
			}
		} else if (e.getSource() == this.btnModify) {
			int i = this.foodTable.getSelectedRow();
			if (i < 0) {
				JOptionPane.showMessageDialog(null, "��ѡ��ʳ��", "��ʾ", JOptionPane.ERROR_MESSAGE);
				return;
			}
			FoodInfo food = this.foods.get(i);

			FrmFoodManager_ModifyFood dlg = new FrmFoodManager_ModifyFood(this, "�޸�ʳ��", true, this.foodTypeMap_name,
					food);
			dlg.setVisible(true);
			if (dlg.getFood() != null) {// ˢ�±��
				this.reloadTable();
			}
		} else if (e.getSource() == this.btnDelete) {
			int i = this.foodTable.getSelectedRow();
			if (i < 0) {
				JOptionPane.showMessageDialog(null, "��ѡ��ʳ��", "��ʾ", JOptionPane.ERROR_MESSAGE);
				return;
			}
			FoodInfo food = this.foods.get(i);
			if (JOptionPane.showConfirmDialog(this, "ȷ��ɾ����ʳ����", "ȷ��",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				try {
					(new FoodManager()).removeFood(food);
					JOptionPane.showMessageDialog(null, "ʳ����ɾ��", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
					this.reloadTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
				}
			}
		} else if (e.getSource() == this.btnSearch) {
			this.reloadTable();
		}
	}
}