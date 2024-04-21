package Package_1;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;


import java.sql.*;

class Class_1 extends Frame implements ActionListener {    
    JLabel lb1, lb2, lb3;
    TextField txt1, txt2;
    Button but_ok, but_reset, but_exit;    
    JComboBox combobox_1;
    JTable table_1;
    JScrollPane scrollpane_1;  
    JPanel pn0, pn1, pn2, pn3, pn_main, pn_center;
    JFrame f1;
    Connection con;
    
    public void GUI()
    {   
        
        lb1 = new JLabel("CONNECTING TO DATABASE");
        lb2 = new JLabel("Input Information");
        lb3 = new JLabel("SQL:");
        lb1.setHorizontalAlignment(SwingConstants.CENTER);
        
        txt1 = new TextField(20);
        txt2 = new TextField(20);
                
        but_ok = new Button("OK");
        but_reset = new Button("Reset");
        but_exit = new Button("Thoat");
        
        but_ok.addActionListener(this);
        but_reset.addActionListener(this);
        but_exit.addActionListener(this);
        
        String[] items = {"select", "insert", "update", "delete"};
        combobox_1 = new JComboBox(items);
        
       
        
        pn0 = new JPanel(new GridLayout(3, 1));
        pn1 = new JPanel(new FlowLayout());
        pn2 = new JPanel(new FlowLayout());
        table_1=new JTable();
	    scrollpane_1 = new JScrollPane();
	    scrollpane_1.setViewportView(table_1);
	    scrollpane_1.setEnabled(true);
	    scrollpane_1.setPreferredSize(new Dimension(200,200));
	    scrollpane_1.setVisible (true);
	     
        
        pn_center = new JPanel(new GridLayout(2, 1));
        pn3 = new JPanel(new FlowLayout());
        pn_main = new JPanel(new BorderLayout());

        pn1.add(lb2);
        pn1.add(txt1);
        
        pn2.add(lb3);
        pn2.add(txt2);
        pn2.add(combobox_1);
        
        pn0.add(lb1);
        pn0.add(pn1);
        pn0.add(pn2);
        
        pn_center.add(pn0);
        pn_center.add(scrollpane_1);
        
        pn3.add(but_ok);
        pn3.add(but_reset);
        pn3.add(but_exit);
        
        pn_main.add(pn_center, BorderLayout.CENTER);
        pn_main.add(pn3, BorderLayout.SOUTH);
        pn_main.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        JFrame f = new JFrame("Connecting to DB");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(pn_main);
        f.pack();
        f.setSize(500,500);
        f.setVisible(true);        
        
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == but_ok)
        {   
        	if(txt2.getText().equals(""))
        	{
        	    connect_database_query("select * from table1");
        	    
        	}
        	else if(check_combobox())
            {
                if(combobox_1.getSelectedItem().toString()=="select") connect_database_query(txt2.getText());
                else {connect_database_nonquery(txt2.getText());}
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Thao tác không trùng khớp", "Message", JOptionPane.INFORMATION_MESSAGE);

            }
        }
        if(e.getSource() == but_reset)
        {
            txt1.setText("");
            txt2.setText("");
        }
        if(e.getSource() == but_exit)
        {
            System.exit(0);
        }
    }
    
    public Class_1(String st)
    {
        super(st);
        GUI();
    }
    
    public boolean check_combobox()
    {  
    	String s_0=txt2.toString().toLowerCase();    	
       if(s_0.contains(combobox_1.getSelectedItem().toString()))
       return true;
       return false;
    }
    
    public void connect_database_query(String sql_1)
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url =txt1.getText();
            con = DriverManager.getConnection(url, "root", "");
            Statement stmt = con.createStatement();           
            ResultSet rs=stmt.executeQuery(sql_1);
            ResultSetMetaData rsmd=rs.getMetaData();
            DefaultTableModel model=(DefaultTableModel)table_1.getModel();
            model.setRowCount(0);
            int cols=rsmd.getColumnCount();
            String[] col_name=new String[cols];
            for(int i=0;i<cols;i++)
            {
            	col_name[i]=rsmd.getColumnName(i+1);
            }
            model.setColumnIdentifiers(col_name);
            String[] row=new String[cols];
            while(rs.next())
            {
            	
            	for(int i=0;i<cols;i++)
            	{
            		row[i]=rs.getString(i+1);
            	}
            	model.addRow(row);            	
            }
            rs.close();
            stmt.close();
            con.close();
        } catch(Exception e) {
        	       	
            JOptionPane.showMessageDialog(null, "Error" +e, "Message", JOptionPane.INFORMATION_MESSAGE);

        }
    }
    public void connect_database_nonquery(String sql_1)
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = txt1.getText();
            con = DriverManager.getConnection(url, "root", "");
            Statement stmt = con.createStatement();           
            stmt.executeUpdate(sql_1);
            JOptionPane.showMessageDialog(null, "Thao tác thành công", "Message", JOptionPane.INFORMATION_MESSAGE);
            stmt.close();
            con.close();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error" +e, "Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    public static void main(String[] args)
    {
        new Class_1("Connecting to DB");
    }
}
