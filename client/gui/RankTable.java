package client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import common.TransData;

public class RankTable extends JFrame {

	private static final long serialVersionUID = 3463792256615362871L;
	private JPanel p_right;
	private Container contentPane;
	private JScrollPane sp_right;
	private Object[][] table_model = new Object[10][3];
	private JTable table_rank;
	private JFrame gui;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	public RankTable(JFrame gui, ObjectInputStream ois, ObjectOutputStream oos) {
		this.ois = ois;
		this.oos = oos;
		this.gui = gui;
		drawFrame();
	}
	
	public void drawFrame(){
		setTitle("Ranking Board");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		contentPane = getContentPane();
		p_right = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) p_right.getLayout();
		flowLayout_2.setVgap(0);
		flowLayout_2.setHgap(0);
		p_right.setForeground(new Color(255, 255, 255));
		p_right.setBackground(new Color(51, 51, 51));
		contentPane.add(p_right, BorderLayout.EAST);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		
		sp_right = new JScrollPane();
		sp_right.setBorder(new LineBorder(UIManager.getColor("SplitPaneDivider.draggingColor")));
		sp_right.setBackground(new Color(51, 51, 51));
		sp_right.setPreferredSize(new Dimension(238, 388));
		p_right.add(sp_right);

		for(int i = 0; i < 10; i++){
				table_model[i][0] = i+1;
		}
		
		table_rank = new JTable();
		table_rank.setFillsViewportHeight(true);
		table_rank.setFont(new Font("Arial Narrow", Font.PLAIN, 15));
		table_rank.setForeground(Color.WHITE);
		table_rank.setBackground(Color.DARK_GRAY);
		table_rank.setRequestFocusEnabled(false);
		table_rank.setRowHeight(36);
		table_rank.setModel(new DefaultTableModel(table_model, new String[] {"순위", "ID", "버틴 시간"}) {
			private static final long serialVersionUID = 3856238676296868388L;
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_rank.getColumnModel().getColumn(0).setResizable(false);
		table_rank.getColumnModel().getColumn(0).setPreferredWidth(35);
		table_rank.getColumnModel().getColumn(1).setResizable(false);
		table_rank.getColumnModel().getColumn(1).setPreferredWidth(107);
		table_rank.getColumnModel().getColumn(2).setResizable(false);
		table_rank.getColumnModel().getColumn(2).setPreferredWidth(70);
		sp_right.setViewportView(table_rank);
		if(gui instanceof GUI)	setLocation(gui.getX() + 1005, gui.getY() - 5);
		if(gui instanceof SelectWindow) setLocationRelativeTo(null);
		setFocusable(false);
		pack();
		setVisible(true);
		
		TransData data = new TransData();
		data.setCommand(TransData.TABLE_REFRESH);
		try {
			oos.writeObject(data);
			@SuppressWarnings("unchecked")
			HashMap<String, Double> ranking = (HashMap<String, Double>) ois.readObject();
			rankSetModel(ranking);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void rankSetModel(HashMap<String, Double> ranking){
		Object[] idSet = ranking.keySet().toArray();
		ArrayList<Double> scoreList = new ArrayList<>();
		for(int i = 0; i < idSet.length; i++){
			scoreList.add(ranking.get(idSet[i]));
			if(i == 9) break;
		}
		
		for(int i = 0; i < idSet.length; i++){
			table_model[i][1] = idSet[i];
			table_model[i][2] = scoreList.get(i);
			if(i == 9) break;
		}
		
		table_rank.setModel(new DefaultTableModel(table_model, new String[] {"순위", "ID", "버틴 시간"}) {
			private static final long serialVersionUID = 3856238676296868388L;
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_rank.getColumnModel().getColumn(0).setResizable(false);
		table_rank.getColumnModel().getColumn(0).setPreferredWidth(35);
		table_rank.getColumnModel().getColumn(1).setResizable(false);
		table_rank.getColumnModel().getColumn(1).setPreferredWidth(107);
		table_rank.getColumnModel().getColumn(2).setResizable(false);
		table_rank.getColumnModel().getColumn(2).setPreferredWidth(70);
	}

}
