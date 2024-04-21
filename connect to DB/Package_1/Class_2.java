package Package_1;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.sql.*;

class Class_2 extends Frame implements ActionListener {    
    JLabel lb1, lb2, lb3;
    TextField txt1;
    Button but_search, but_reset, but_exit;    
    JTable table_1;
    JScrollPane scrollpane_1;  
    JPanel pn0, pn1, pn2,pn3,pn4, pn_main;
    JRadioButton but1,but2,but3,but4,but5;
    JFrame f1;
    Connection con;
    
    public void GUI()
    {   
        
        lb1 = new JLabel("SEARCH INFORMATION");
        lb2 = new JLabel("Input Information");
        lb3 = new JLabel("Search as: ");
        lb1.setHorizontalAlignment(SwingConstants.CENTER);
        
        txt1 = new TextField(15);        
                
        but_search = new Button("Search");
        but_reset = new Button("Reset");
        but_exit = new Button("Thoat");
        
        but1=new JRadioButton("Maso");
        but2=new JRadioButton("Hoten");
        but3=new JRadioButton("Ngaysinh");
        but4=new JRadioButton("Diachi");
        but5=new JRadioButton("Gioitinh");
        ButtonGroup bg=new ButtonGroup();
        bg.add(but1);
        bg.add(but2);
        bg.add(but3);
        bg.add(but4);
        bg.add(but5);
        
        but_search.addActionListener(this);
        but_reset.addActionListener(this);
        but_exit.addActionListener(this);                  
       
        
        pn0 = new JPanel(new GridLayout(3, 1));
        pn1 = new JPanel(new FlowLayout());
        pn2 = new JPanel(new FlowLayout());
        pn3=new JPanel(new FlowLayout());
        pn4 = new JPanel(new GridLayout(2,1));
        pn_main=new JPanel(new BorderLayout());

        
        table_1=new JTable();
	    scrollpane_1 = new JScrollPane();
	    scrollpane_1.setViewportView(table_1);             
	    scrollpane_1.setEnabled(true);
	    scrollpane_1.setPreferredSize(new Dimension(400,200));
	    scrollpane_1.setVisible (true);
	    connect_database_query("select * from sinhvien");
	     
        
       
        pn1.add(lb2);
        pn1.add(txt1);
        pn1.add(but_search);
        pn1.add(but_reset);
        pn1.add(but_exit);
        
        pn2.add(lb3);
        pn2.add(but1);
        pn2.add(but2);
        pn2.add(but3);
        pn2.add(but4);
        pn2.add(but5);
        
        pn0.add(lb1);
        pn0.add(pn1);
        pn0.add(pn2);
        
        pn3.add(scrollpane_1);
        
        pn4.add(pn0);
        pn4.add(pn3);
        
        pn_main.add(pn4,BorderLayout.CENTER);
        //System.out.print(but1.getText());
        //pn_main.setBorder(new EmptyBorder(20,20, 20, 20));
        
        JFrame f = new JFrame("Connecting to DB");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(pn_main);
        f.pack();
        f.setSize(500,500);
        f.setVisible(true);       
        
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == but_search)
        {   
           String name_col=new String();
           if(but1.isSelected()) name_col=but1.getText();
           if(but2.isSelected()) name_col=but2.getText();
           if(but3.isSelected()) name_col=but3.getText();
           if(but4.isSelected()) name_col=but4.getText();
           if(but5.isSelected()) name_col=but5.getText();
           String sql_query=new String();
           if(name_col.equals("id")) sql_query="SELECT * FROM data.sinhvien WHERE "+ name_col +" = "+txt1.getText().toString();
           else sql_query="SELECT * FROM data.sinhvien WHERE "+ name_col +" LIKE '%"+txt1.getText().toString()+"%'";
        	System.out.print(sql_query+"\n");
        	
        	connect_database_query(sql_query);
        }
        if(e.getSource() == but_reset)
        {
            txt1.setText("");
    	    connect_database_query("select * from sinhvien");
        }
        if(e.getSource() == but_exit)
        {
            System.exit(0);
        }
    }
    
    public Class_2(String st)
    {
        super(st);
        GUI();
    } 
    
    
    public void connect_database_query(String sql_1)
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url ="jdbc:mysql://127.0.0.1:3307/data";
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
            //model.addRow(col_name);
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

    public static void main(String[] args)
    {
        new Class_2("Connecting to DB");
    }
}
