package com.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class SwingDemo  implements ActionListener{
	JFrame f;
	JLabel id,name,gender,address,contact;
	JTextField t1,t2,t4,t5;
	JButton b1,b2,b3,b4;
	JRadioButton r1,r2;
	JScrollPane sp;
	JTable table;
	DefaultTableModel model;
	Connection conn=null;
	public SwingDemo() {
		f=new JFrame("REGISTRATION FORM");
		f.setVisible(true);
		f.setSize(1000, 500);
//		f.setLayout(new FlowLayout());
		f.setLayout(null);
		
		
		id=new JLabel("ID");
		name=new JLabel("NAME");
		gender=new JLabel("GENDER");
		address=new JLabel("ADDRESS");
		contact=new JLabel("CONTACT");
		
		t1=new JTextField(20);
		t2=new JTextField(20);
		
		t4=new JTextField(20);
		t5=new JTextField(20);
		
		r1=new JRadioButton("Male");
		r2=new JRadioButton("Female");
		
		
		
		b1=new JButton("INSERT");
		b2=new JButton("SEARCH");
		b3=new JButton("UPDATE");
		b4=new JButton("DELETE");
		//create model
		model=new DefaultTableModel();
		//create table
		table=new JTable(model);
		//add table to scroll pane
		sp=new JScrollPane(table);
		
		f.add(id);
		f.add(t1);
		f.add(name);
		f.add(t2);
		f.add(gender);
		f.add(r1);
		f.add(r2);
		f.add(address);
		f.add(t4);
		f.add(contact);
		f.add(t5);
		f.add(b1);
		f.add(b2);
		f.add(b3);
		f.add(b4);
		f.add(sp,BorderLayout.CENTER);
		
		id.setBounds(50, 50, 100, 20);
		name.setBounds(50, 100, 100, 20);
		gender.setBounds(50, 150, 100, 20);
		address.setBounds(50, 200, 100, 20);
		contact.setBounds(50, 250, 100, 20);
		
		t1.setBounds(200, 50, 100, 20); 
		t2.setBounds(200, 100, 100, 20);
		r1.setBounds(200, 150, 100, 20);
		r2.setBounds(300, 150, 100, 20);
		
		t4.setBounds(200, 200, 100, 20);
		t5.setBounds(200, 250, 100, 20); 
		
		
		b1.setBounds(50, 300, 100, 20);
		b2.setBounds(200, 300, 100, 20);//(margin left,margin top,width,verti gap)
		b3.setBounds(50, 350, 100, 20);
		b4.setBounds(200, 350, 100, 20);
		
		b1.addActionListener((ActionListener) this);
		b2.addActionListener((ActionListener) this);
		b3.addActionListener((ActionListener) this);
		b4.addActionListener((ActionListener) this);
		
		sp.setBounds(400, 50, 500, 300);
		
	}
public static void main(String[] args) {
	new SwingDemo();
	
}
@Override
public void actionPerformed(ActionEvent e) {
	
	if(e.getSource()==b1) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		
		Connection conn;
		
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/adv_java", "root", "");
			// TODO Auto-generated catch block
		
		String sql="insert into swing_demo(id,name,gender,address,contact)values(?,?,?,?,?)";
		
		PreparedStatement pst=conn.prepareStatement(sql);
		
		String s1,s2,s3,s4,s5;
		
		s1=t1.getText();
		s2=t2.getText();
		s4=t4.getText();
		s5=t5.getText();
		s3=null;
		if(r1.isSelected()) {
			s3=r1.getText();
		}else if (r2.isSelected()) {
			s3=r2.getText();
		}
		
		pst.setString(1, s1);
		pst.setString(2, s2);
		pst.setString(3, s3);
		pst.setString(4, s4);
		pst.setString(5, s5);
		
		pst.executeUpdate();
		//empty the text box
			t1.setText("");
			t2.setText("");
			t4.setText("");
			t5.setText("");
			r1.setSelected(false);
			r2.setSelected(false);
			JOptionPane.showMessageDialog(f, "Data Inserted Sucessfully");
			System.out.println("data inserted");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}else if (e.getSource()==b2) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn =DriverManager.getConnection("jdbc:mysql://localhost:3306/adv_java", "root", "");
			
			String sql="select * from student where id=?";
			
			PreparedStatement pst=conn.prepareStatement(sql);
			
			
			pst.setInt(1,Integer.parseInt(t1.getText()));	
			
			//rs me data padha hoga jo id ke hisab se mila hoga
		ResultSet rs =pst.executeQuery();
		int col_count= rs.getMetaData().getColumnCount();
		
		String[] col_name=new String[col_count];
		for (int i = 1; i <=col_count; i++) {
			col_name[i-1]=rs.getMetaData().getColumnName(i);
		}
		
		//set col name
		model.setColumnIdentifiers(col_name);
		
		//next() cursor ko dekhe ga jab cursor last row k bad khatam hoga to o false return karega
			if(rs.next()) {
				
				Object[] rowData=new Object[col_count];
				for (int i = 1; i <=rowData.length; i++) {
					rowData[i-1]=rs.getObject(i);
				}
				
				model.addRow(rowData);
			}
			
			} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}else if (e.getSource()== b3) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/adv_java","root","");
			
			String sql="update swing_demo set contact=? where id=?";
			
			PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,t5.getText());
			
			pst.setInt(2,Integer.parseInt( t1.getText()));
			
			pst.executeUpdate();
			
			t1.setText("");
			t2.setText("");
			t4.setText("");
			t5.setText("");
			r1.setSelected(false);
			r2.setSelected(false);
			
			JOptionPane.showMessageDialog(f, "Data updated sucessfully");
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}else if (e.getSource()==b4) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost/adv_java","root","");
			
			String sql="delete from swing_demo where id=?";
			
			PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, Integer.parseInt (t1.getText()));
			pst.executeUpdate();
			
			t1.setText("");
			t2.setText("");
			t4.setText("");
			t5.setText("");
			r1.setSelected(false);
			r2.setSelected(false);
			
			JOptionPane.showMessageDialog(f, "Data Deleted Successfully");
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
}
