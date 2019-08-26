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
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import KitchenHelper.control.FoodManager;
import KitchenHelper.model.FoodTypeInfo;
import KitchenHelper.util.BaseException;

public class FrmFoodTypeManager extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("���ʳ�����");
	private Button btnModify = new Button("�޸�ʳ�������Ϣ");
	private Button btnDelete = new Button("ɾ��ʳ�����");
	private Object tblTitle[] = { "���ID", "�������", "�������" };
	private Object tblData[][];
	DefaultTableModel tablmod = new DefaultTableModel();
	private JTable readerTypeTable = new JTable(tablmod);

	private void reloadTable() {
		try {
			List<FoodTypeInfo> types = (new FoodManager()).loadAllFoodType();
			tblData = new Object[types.size()][3];
			for (int i = 0; i < types.size(); i++) {
				tblData[i][0] = types.get(i).getFoodTypeNo() + "";
				tblData[i][1] = types.get(i).getFoodTypeName();
				tblData[i][2] = types.get(i).getFoodTypeDetail() + "";
			}
			tablmod.setDataVector(tblData, tblTitle);
			this.readerTypeTable.validate();
			this.readerTypeTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public FrmFoodTypeManager(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(btnModify);
		toolBar.add(this.btnDelete);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		// ��ȡ��������
		this.reloadTable();
		this.getContentPane().add(new JScrollPane(this.readerTypeTable), BorderLayout.CENTER);

		// ��Ļ������ʾ
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2, (int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnAdd.addActionListener(this);
		this.btnModify.addActionListener(this);
		this.btnDelete.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				 System.exit(0);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this.btnAdd) {
			FrmFoodTypeManager_AddFoodType dlg = new FrmFoodTypeManager_AddFoodType(this, "���ʳ�����", true);
			dlg.setVisible(true);
			if (dlg.getFoodtype() != null) {// ˢ�±��
				this.reloadTable();
			}
		} else if (e.getSource() == this.btnModify) {
			int i = this.readerTypeTable.getSelectedRow();
			if (i < 0) {
				JOptionPane.showMessageDialog(null, "��ѡ��ʳ�����", "��ʾ", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int n = Integer.parseInt(this.tblData[i][0].toString());
			FoodTypeInfo foodtype = new FoodTypeInfo();
			foodtype.setFoodTypeNo(n);
			foodtype.setFoodTypeName(this.tblData[i][1].toString());
			foodtype.setFoodTypeDetail(this.tblData[i][2].toString());
			FrmFoodTypeManager_ModifyFoodType dlg = new FrmFoodTypeManager_ModifyFoodType(this, "��Ӷ������", true,
					foodtype);
			dlg.setVisible(true);
			if (dlg.getFoodtype() != null) {// ˢ�±��
				this.reloadTable();
			}
		} else if (e.getSource() == this.btnDelete) {
			int i = this.readerTypeTable.getSelectedRow();
			if (i < 0) {
				JOptionPane.showMessageDialog(null, "��ѡ��ʳ�����", "��ʾ", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (JOptionPane.showConfirmDialog(this, "ȷ��ɾ���������", "ȷ��",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				int n = Integer.parseInt(this.tblData[i][0].toString());
				try {
					(new FoodManager()).deleteFoodType(n);
					this.reloadTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
				}

			}
		}
	}
}