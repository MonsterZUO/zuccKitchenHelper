package KitchenHelper;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import KitchenHelper.ui.FrmMain;

public class KitchenHelperStarter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//
		String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		new FrmMain();
	}

}
