package quanlysinhvien.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class PanelTKBView extends JPanel{
	private String titleCols[] = {"Thời gian", "Tuần học", "Phòng học", "Mã lớp", "Loại lớp", "Mã HP", "Tên lớp"};
	private JTable table;
	private JLabel labStatus;
	
	public PanelTKBView() {
		setLayout(new BorderLayout(10, 10));
		setBorder(new EmptyBorder(20, 60, 180, 60));
		add(createTitlePanel(), BorderLayout.PAGE_START);
		add(createMainPanel(), BorderLayout.CENTER);
		add(createStatusPanel(), BorderLayout.SOUTH);
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Thời khóa biểu");
		label.setFont(new Font("Caribli", Font.BOLD, 18));
		label.setForeground(Color.YELLOW);
		label.setIcon(new ImageIcon(this.getClass().getResource("/tkb.png")));
		panel.setBackground(new Color(0x009999));
		panel.add(label, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		table = new JTable();
		loadData(table);
		JScrollPane scroll = new JScrollPane(table);
		panel.add(scroll);
		
		return panel;
	}
	
	private JPanel createStatusPanel() {
		JPanel panel = new JPanel();
		labStatus = new JLabel("Sinh viên không có thời khóa biểu kì này");
//		labStatus = new JLabel("");
		labStatus.setFont(new Font("Caribli", Font.BOLD, 16));
		labStatus.setForeground(Color.BLUE);
		panel.add(labStatus);
		
		return panel;
	}
	
	private void loadData(JTable table) {
		SwingUtilities.invokeLater(new Runnable(){public void run(){
			String data[][] = null;
		    //Update the model here
			DefaultTableModel tableModel = new DefaultTableModel(data, titleCols) {
				@Override
				public boolean isCellEditable(int row, int column) {
					// TODO Auto-generated method stub
					return false;
				}
			};
			table.setModel(tableModel);		
		}});
	}
	
}
