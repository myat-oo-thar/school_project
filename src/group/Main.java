package group;
//hello1

import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.SQLException;
import java.awt.Font;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.Color;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import raven.datetime.DatePicker;
import raven.datetime.event.DateSelectionEvent;
import raven.datetime.event.DateSelectionListener;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicInteger;

public class Main extends JFrame {


    //    String host = "mysql-26ea90b1-myatoothar5-1f5f.c.aivencloud.com";
//    String port = "10007";
//    String database = "defaultdb";
//    String user = "avnadmin";//avnadmin
    //    String password = "AVNS_Z5VaWvb_pxjqBMtks36";//AVNS_Z5VaWvb_pxjqBMtks36
    //    //String url = "jdbc:mysql://avnadmin:AVNS_Z5VaWvb_pxjqBMtks36@mysql-26ea90b1-myatoothar5-1f5f.c.aivencloud.com:10007/project?ssl-mode=REQUIRED";
//    String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?sslMode=REQUIRED";//aiven
//    String url = "jdbc:mysql://localhost:3306/project_copy";//local
//    String user = "root";
//    String password = "694737";
    static String Librarian;



    public class DBConnection {

        static String URL = "jdbc:mysql://localhost:3306/project_copy";
        static String USER = "root";
        static String PASSWORD = "694737";

//        static String URL = "jdbc:mysql://mysql-26ea90b1-myatoothar5-1f5f.c.aivencloud.com:10007/defaultdb?sslMode=REQUIRED";
//        static String USER = "avnadmin";
//        static String PASSWORD = "AVNS_Z5VaWvb_pxjqBMtks36";

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }
    public String[] getCategories() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("All");
        String sql = "SELECT DISTINCT Category FROM booklist";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                categories.add(rs.getString("category"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories.toArray(new String[0]);
    }

    JPanel pTop = new JPanel();
    JPanel pBottom = new JPanel(new GridLayout(1,3));
    JPanel pMenu = new JPanel(new GridLayout(6, 1));
    JPanel pMain = new JPanel(new BorderLayout());
    JPanel pMainPanel = new JPanel();
    JPanel panel = new JPanel(new BorderLayout());

    JLabel lTop = new JLabel("Library Management System");
    JLabel lMenu = new JLabel("DashBoard");
    JLabel login = new JLabel("Logged in: "+Librarian);

    JButton b1 = new JButton("Books");
    JButton b2 = new JButton("Members");
    JButton b3 = new JButton("Borrows");
    JButton b4 = new JButton("Returns");
    JButton b5 = new JButton("Librarian");
    //JButton b6 = new JButton("Setting");
    JButton bEdit = new JButton("Rename");
    JButton deleteBook = new JButton("Delete");
    JButton deleteBorrow = new JButton("Delete");
    JButton deleteMember = new JButton("Delete");
    JButton deleteReturn = new JButton("Delete");
    JButton deleteLibrarian = new JButton("Delete");
    JButton bBookEntry = new JButton("Add new Book");
    JButton bMemberEntry = new JButton("Add new Member");
    JButton bBorrowEntry = new JButton("Add new Borrow");
    JButton bReturnEntry = new JButton("Add new Return");
    JButton bLibrarianEntry = new JButton("Add new Librarian");

    JButton newBtn1 = new JButton("OK");
    JButton newBtn2 = new JButton("Delete");
    JButton newBtn3 = new JButton("Cancel");

    JButton returnInBorrow = new JButton("Return");

    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String formattedDate= now.format(formatter);

    static ImageIcon imageIcon = new ImageIcon("/home/bullet/Desktop/school_project-main/src/group/img.png");



    Object selectedValue;
    Object inputedValue;
    int selectedRow = -1;
    int selectedColumn = -1;
    int bookId;
    int memberId;
    int borrowId;
    int returnId;
    int userId;








    JPanel searchPanel = new JPanel();

    private boolean bookEntryOpen = false;
    private boolean memberEntryOpen = false;
    private boolean borrowEntryOpen = false;
    private boolean returnEntryOpen = false;
    private boolean librarianEntryOpen = false;




    class ShowBookList implements ActionListener{

        String selectedBox;
        public void actionPerformed(ActionEvent e) {
            pMainPanel.removeAll();
            pMainPanel.revalidate();
            pMainPanel.repaint();

            String[] categories= getCategories();;

            JComboBox<String> categoryBox = new JComboBox<>(categories);




            for (ActionListener listener : bEdit.getActionListeners()) {
                bEdit.removeActionListener(listener);
            }


//hello
            pMainPanel.add(bBookEntry);
            pMainPanel.add(categoryBox);
            pMain.removeAll();
            pMain.setLayout(new BorderLayout());





            searchPanel.removeAll();
            searchPanel.revalidate();
            searchPanel.repaint();
            searchPanel.setVisible(true);

            JTextField searchField = new JTextField("Search by book name or author");
            JButton searchButton = new JButton("Search book");




            searchField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            });

            searchField.addActionListener(se -> {
                searchButton.doClick();
            });

            searchField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        searchButton.doClick();
                    }
                }
            });

            searchField.setPreferredSize(new Dimension(250, 40));

            searchPanel.add(searchField);
            searchPanel.add(searchButton);
            panel.add(searchPanel,BorderLayout.NORTH);





            categoryBox.addActionListener(ce -> {
                selectedBox = (String) categoryBox.getSelectedItem();

                b1.doClick();
            });

//            pMainPanel.remove(bBorrowEntry);
//            pMainPanel.remove(bReturnEntry);
//            pMainPanel.remove(bLibrarianEntry);
//            pMainPanel.remove(bMemberEntry);



            pMainPanel.add(categoryBox);


            pMainPanel.add(bEdit);
            pMainPanel.add(deleteBook);


            deleteBook.setVisible(false);
            deleteMember.setVisible(false);
            deleteBorrow.setVisible(false);
            deleteReturn.setVisible(false);
            deleteLibrarian.setVisible(false);

            bEdit.setVisible(false);

            bBookEntry.setVisible(true);
            bMemberEntry.setVisible(false);
            bBorrowEntry.setVisible(false);
            bReturnEntry.setVisible(false);
            bLibrarianEntry.setVisible(false);
            returnInBorrow.setVisible(false);







            // creating table for data outputs
            String[] columns = {"Book ID","Book Title","Author Name","Publisher","Year", "Category"};
            DefaultTableModel model = new DefaultTableModel(columns,0);
            JTable table = new JTable(model);
            table.setRowHeight(25);
            table.getTableHeader().setFont(table.getTableHeader().getFont().deriveFont(Font.BOLD));// set table header font bold
            table.getTableHeader().setBackground(new Color(207, 108, 37));//table header box bgcolor
            table.getTableHeader().setForeground(new Color(255,255,255));
            table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getPreferredSize().width,30));//table header box size
            //make the text in table CENTER
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }


            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    deleteBook.setVisible(true);
                    bEdit.setVisible(true);


                    selectedRow = table.rowAtPoint(e.getPoint());
                    selectedColumn = table.columnAtPoint(e.getPoint());

                    if (selectedRow != -1 && selectedColumn != -1) {
                        bookId = (int) table.getValueAt(selectedRow, 0);


                        Object selectedValue = table.getValueAt(selectedRow, selectedColumn);

                        newBtn1.setBackground(Color.GREEN);
                        newBtn2.setBackground(Color.RED);
                        newBtn3.setBackground(Color.YELLOW);

                    }
                }
            });

            deleteBook.addActionListener(be-> {


                String sqlDelete = "DELETE FROM booklist WHERE bookID = "+bookId;
                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {
                    pstmt.executeUpdate();
                    pstmt.close();
                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                }
                b1.doClick();


            });

            String sqlSelect = "SELECT * FROM booklist";
            try (Connection conn = DBConnection.getConnection();
                 Statement statement = conn.createStatement()){

                if (selectedBox != null && !selectedBox.equals("All")) {
                    sqlSelect = "SELECT * FROM booklist WHERE Category = '" + selectedBox + "'";
                }

                try(ResultSet resultSet = statement.executeQuery(sqlSelect)){
                    while(resultSet.next()) {
                        model.addRow(new Object[] {
                                resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getInt(5),
                                resultSet.getString(6)
                        });
                    }
                }


                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setPreferredSize(new java.awt.Dimension(500,250));
                JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
                table.getTableHeader().setForeground(new Color(255,255,255));

                barUIsetting(verticalBar);
                pMain.add(scrollPane, BorderLayout.CENTER);
                pMain.revalidate();
                pMain.repaint();

            }

            catch(SQLException se) {
                se.printStackTrace();
            }//catch


            bEdit.addActionListener(be -> {
//                pMainPanel.remove(bBookEntry);
                bBookEntry.setVisible(false);
                bEdit.setVisible(false);
                JFrame bookEditFrame = new JFrame("Book Edit Frame");
                JPanel p1 = new JPanel(new FlowLayout());
                JPanel p2 = new JPanel(new FlowLayout());
                JPanel panel = new JPanel(new BorderLayout(10,10));
                JButton okButton = new JButton("OK");
                JButton cancelButton = new JButton("Cancel");

                JLabel l1 = new JLabel("New Value");

                JTextField jt1 = new JTextField();

                jt1.setPreferredSize(new Dimension(200,30));

                panel.add(p1,BorderLayout.CENTER);
                panel.add(p2,BorderLayout.SOUTH);

                p1.add(l1);
                p1.add(jt1);

                p2.add(okButton);
                p2.add(cancelButton);

                okButton.addActionListener(a -> {
//                    pMainPanel.add(bBookEntry);
                    bEdit.setVisible(false);
                    bBookEntry.setVisible(true);

                    inputedValue = jt1.getText();


                    if (selectedColumn == -1) {

                        return;
                    }

                    String[] columnsql = { "bookID", "bookTitle", "author", "publisher", "publishYear", "Category" };

                    if (selectedColumn < 0 || selectedColumn >= columnsql.length) {

                        return;
                    }

                    String columnName = columnsql[selectedColumn];




                    String booksql = "UPDATE booklist SET " + columnName + " = ? WHERE bookID = ?";
                    try (Connection conn = DBConnection.getConnection();
                         PreparedStatement pstmt = conn.prepareStatement(booksql)) {
                        pstmt.setObject(1, inputedValue);
                        pstmt.setInt(2, bookId);
                        int updated = pstmt.executeUpdate();


                    }
                    catch (SQLException ex) {
                        ex.printStackTrace();
                    }



                    jt1.setText("");
                    inputedValue = null;
                    removeEditFrame(bookEditFrame);

                    b1.doClick();

                });


                bookEditFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                bookEditFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {bEdit.setVisible(true);
                    }
                });
                bookEditFrame.setSize(300,200);
                bookEditFrame.setResizable(false);
                bookEditFrame.setVisible(true);
                bookEditFrame.setLocation(1350,250);
                bookEditFrame.add(panel);

                cancelButton.addActionListener( b3e -> {
                    // pMainPanel.add(bBookEntry);
                    bBookEntry.setVisible(true);
                    bEdit.setVisible(false);
                    table.clearSelection();
                    bookEditFrame.dispose();
                });

                jt1.addActionListener(oe -> {
                    okButton.doClick();
                });

                jt1.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            okButton.doClick();
                        }
                    }
                });



                okButton.setPreferredSize(new Dimension(100,50));
                cancelButton.setPreferredSize(new Dimension(100,50));


                okButton.setBackground(Color.WHITE);

                cancelButton.setBackground(Color.WHITE);

                b2.addActionListener(e2 ->{removeEditFrame(bookEditFrame);});
                b3.addActionListener(e2 ->{removeEditFrame(bookEditFrame);});
                b4.addActionListener(e2 ->{removeEditFrame(bookEditFrame);});
                b5.addActionListener(e2 ->{removeEditFrame(bookEditFrame);});

                // Crucial step: Tell Swing to rebuild the layout and redraw the screen
                pMainPanel.revalidate();
                pMainPanel.repaint();


            });

            bBookEntry.addActionListener(be2 -> {

                if (bookEntryOpen) {
                    return;
                }

                bookEntryOpen = true;

                JFrame bookentry = new JFrame();
                bookentry.setTitle("Book Entry");



                bookentry.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        bookEntryOpen = false;
                        bEdit.setVisible(true);
                        bBookEntry.setVisible(true);
                    }
                });

                // Keep the rest of your existing code here.


//                pMainPanel.remove(bEdit);
//                pMainPanel.remove(bBookEntry);
                bEdit.setVisible(false);
                bBookEntry.setVisible(false);


                bookentry.setLocation(1372,200);
                bookentry.setSize(550,300);
                bookentry.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                bookentry.setVisible(true);
                JLabel lbname = new JLabel("Book Name");
                JLabel laname = new JLabel("Author Name");
                JLabel lpyear = new JLabel("Publish Year");
                JLabel lpname = new JLabel("Publisher Name");
                JLabel lcategory = new JLabel("Category");

                JTextField tbname = new JTextField(20);
                JTextField taname = new JTextField(20);
                JTextField tpyear = new JTextField(20);
                JTextField tpname = new JTextField(20);
                JTextField tcategory = new JTextField(20);
                JButton save = new JButton("Save Book");
                JButton cancel = new JButton("Cancel");
                JPanel pmain = new JPanel(new BorderLayout());
                JPanel p1 = new JPanel(new GridLayout(5, 2));
                Font myFont = new Font("Roboto", Font.PLAIN, 20);

                //setting labels fonts
                lbname.setFont(myFont);
                laname.setFont(myFont);
                lpyear.setFont(myFont);
                lpname.setFont(myFont);
                lcategory.setFont(myFont);

                Color fontColor = new Color(32, 32, 32);
                //setting labels fonts color
                lbname.setForeground(fontColor);
                laname.setForeground(fontColor);
                lpyear.setForeground(fontColor);
                lpname.setForeground(fontColor);
                lcategory.setForeground(fontColor);

                //setting size of text field box
                tbname.setPreferredSize(new Dimension(20, 30));
                taname.setPreferredSize(new Dimension(20, 30));
                tpyear.setPreferredSize(new Dimension(20, 30));
                tpname.setPreferredSize(new Dimension(20, 30));
                tcategory.setPreferredSize(new Dimension(20, 30));

                // for alignment of label and text fields
                LayoutManager layout = new FlowLayout(FlowLayout.CENTER, 5, 10);
                //setting label panels alignments
                JPanel plb = new JPanel(layout);
                JPanel pla = new JPanel(layout);
                JPanel ply = new JPanel(layout);
                JPanel plp = new JPanel(layout);
                JPanel pcl = new JPanel(layout);

                //adding labels into aligned panels
                plb.add(lbname);
                pla.add(laname);
                ply.add(lpyear);
                plp.add(lpname);
                pcl.add(lcategory);

                //setting text field panels alignments
                JPanel ptb = new JPanel(layout);
                JPanel pta = new JPanel(layout);
                JPanel pty = new JPanel(layout);
                JPanel ptp = new JPanel(layout);
                JPanel pct = new JPanel(layout);
                //adding text fields into aligned panels
                ptb.add(tbname);
                pta.add(taname);
                pty.add(tpyear);
                ptp.add(tpname);
                pct.add(tcategory);

                //adding small panels into main GridLayout panel
                p1.add(plb);
                p1.add(ptb);

                p1.add(pla);
                p1.add(pta);

                p1.add(ply);
                p1.add(pty);

                p1.add(plp);
                p1.add(ptp);

                p1.add(pcl);
                p1.add(pct);

                //creating panel for buttons
                JPanel p2 = new JPanel(new FlowLayout());
                p2.add(save);
                p2.add(cancel);


                //creating main panel
                JPanel panel = new JPanel(new BorderLayout());
                panel.add(p1, BorderLayout.CENTER);
                panel.add(p2, BorderLayout.SOUTH);
                add(panel);

                // background color of main panel
                Color bgcolor = new Color(240, 240, 240);
                // background color of button panel
                p2.setBackground(new Color(192, 192, 192));

                //setting background color of label panels and textfield panels
                plb.setBackground(bgcolor);
                pla.setBackground(bgcolor);
                ply.setBackground(bgcolor);
                plp.setBackground(bgcolor);
                pcl.setBackground(bgcolor);


                ptb.setBackground(bgcolor);
                pta.setBackground(bgcolor);
                pty.setBackground(bgcolor);
                ptp.setBackground(bgcolor);
                pct.setBackground(bgcolor);

                save.addActionListener(se -> {
//                    pMainPanel.add(bEdit);
//                    pMainPanel.add(bBookEntry);
                    bEdit.setVisible(true);
                    bBookEntry.setVisible(true);
                    String bname = tbname.getText();
                    String aname = taname.getText();
                    String pyear = tpyear.getText();
                    String pname = tpname.getText();
                    String pcategory = tcategory.getText();

                    if(bname.equals("") || aname.equals("") || pyear.equals("") || pname.equals("") || pcategory.equals("")) {
                        JOptionPane.showMessageDialog(null, "You should input first!");
                    }else {


                        String sqlInsert = "INSERT INTO booklist(bookTitle, author, publisher, publishYear, Category) VALUES (?, ?, ?, ?, ?)";
                        try (Connection conn = DBConnection.getConnection();
                             PreparedStatement pstatement = conn.prepareStatement(sqlInsert)) {


                            pstatement.setString(1, bname);
                            pstatement.setString(2, aname);
                            pstatement.setString(3, pname);
                            pstatement.setString(4, pyear);
                            pstatement.setString(5, pcategory);
                            pstatement.executeUpdate();

                            tbname.setText("");
                            taname.setText("");
                            tpyear.setText("");
                            tpname.setText("");
                            tcategory.setText("");

                        }
                        catch(SQLException se2) {

                            se2.printStackTrace();
                        }//catch
                    }//else



                    b1.doClick();
                    bookentry.dispose();


                });

                cancel.addActionListener(ce -> {

                    bEdit.setVisible(true);
                    bBookEntry.setVisible(true);
                    bookentry.dispose();

                });



                tcategory.addActionListener(oe -> {
                    save.doClick();
                });

                tcategory.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            save.doClick();
                        }
                    }
                });

                pmain.add(p1,BorderLayout.CENTER);
                pmain.add(p2,BorderLayout.SOUTH);
                bookentry.add(pmain);



            });

            searchButton.addActionListener(se -> {
                String searchValue =  searchField.getText().trim();

                if(searchValue.isEmpty()){
                    JOptionPane.showMessageDialog(null, "You should input first!");
                }else{



                    String sqlSearch = "select * from booklist where bookTitle like ? OR author like ?";
                    try (Connection conn = DBConnection.getConnection();
                         PreparedStatement pstmt = conn.prepareStatement(sqlSearch)) {

                        pstmt.setString(1,"%" + searchValue + "%");
                        pstmt.setString(2, "%" + searchValue + "%");

                        model.setRowCount(0);
                        try(ResultSet resultSet = pstmt.executeQuery()){
                            while(resultSet.next()) {
                                model.addRow(new Object[] {
                                        resultSet.getInt(1),
                                        resultSet.getString(2),
                                        resultSet.getString(3),
                                        resultSet.getString(4),
                                        resultSet.getInt(5),
                                        resultSet.getString(6)
                                });
                            }
                        }



                    }//try
                    catch(SQLException se1) {
                        se1.printStackTrace();
                    }
                }
            });


        }

    }

    class ShowMembers implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            pMainPanel.removeAll();
            pMainPanel.revalidate();
            pMainPanel.repaint();

            for (ActionListener listener : bEdit.getActionListeners()) {
                bEdit.removeActionListener(listener);
            }

            pMain.removeAll();
            pMain.setLayout(new BorderLayout());

            searchPanel.removeAll();
            searchPanel.revalidate();
            searchPanel.repaint();
            searchPanel.setVisible(true);

            JTextField searchField = new JTextField("Search by member name");
            JButton searchButton = new JButton("Search");




            searchField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            });

            searchField.addActionListener(se -> {
                searchButton.doClick();
            });

            searchField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        searchButton.doClick();
                    }
                }
            });

            searchField.setPreferredSize(new Dimension(250, 40));

            searchPanel.add(searchField);
            searchPanel.add(searchButton);
            panel.add(searchPanel,BorderLayout.NORTH);






//            pMainPanel.remove(bBorrowEntry);
//            pMainPanel.remove(bReturnEntry);
//            pMainPanel.remove(bLibrarianEntry);
//            pMainPanel.remove(bBookEntry);

            pMainPanel.add(bEdit);
            pMainPanel.add(deleteMember);
            pMainPanel.add(bMemberEntry);

            deleteBook.setVisible(false);
            deleteMember.setVisible(false);
            deleteBorrow.setVisible(false);
            deleteReturn.setVisible(false);
            deleteLibrarian.setVisible(false);

            bEdit.setVisible(false);

            bBookEntry.setVisible(false);
            bMemberEntry.setVisible(true);
            bBorrowEntry.setVisible(false);
            bReturnEntry.setVisible(false);
            bLibrarianEntry.setVisible(false);
            returnInBorrow.setVisible(false);


            // creating table for data outputs
            String[] columns = {"Member ID","Name","Phone Number","Email","Address","Registration Date"};
            DefaultTableModel model = new DefaultTableModel(columns,0);
            JTable table = new JTable(model);
            table.setRowHeight(25);

            table.getTableHeader().setFont(table.getTableHeader().getFont().deriveFont(Font.BOLD));// set table header font bold
            table.getTableHeader().setBackground(new Color(207, 108, 37));//table header box bgcolor
            table.getTableHeader().setForeground(new Color(255,255,255));
            table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getPreferredSize().width,30));//table header box size
            //make the text in table CENTER
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }


            String sqlSelect = "select * from members";
            try (Connection conn = DBConnection.getConnection();
                 Statement statement = conn.createStatement()){


                try(ResultSet resultSet = statement.executeQuery(sqlSelect)){
                    while(resultSet.next()) {
                        model.addRow(new Object[] {
                                resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getString(5),
                                resultSet.getString(6)
                        });
                    }
                }



                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setPreferredSize(new java.awt.Dimension(500,250));
                JScrollBar verticalBar = scrollPane.getVerticalScrollBar();

                barUIsetting(verticalBar);
                pMain.add(scrollPane, BorderLayout.CENTER);
                pMain.revalidate();
                pMain.repaint();


            }
            catch(SQLException se) {
                se.printStackTrace();
            }//catch

            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    bEdit.setVisible(true);
                    deleteMember.setVisible(true);


                    selectedRow = table.rowAtPoint(e.getPoint());
                    selectedColumn = table.columnAtPoint(e.getPoint());


                    if (selectedRow != -1 && selectedColumn != -1) {
                        memberId = (int) table.getValueAt(selectedRow, 0);


                        selectedValue = table.getValueAt(selectedRow, selectedColumn);

                    }
                }
            });

            deleteMember.addActionListener(be-> {


                String sqlDelete = "DELETE FROM members WHERE memberId = "+memberId;
                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {
                    pstmt.executeUpdate();

                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                }
                b2.doClick();


            });

            bEdit.addActionListener(be -> {
                bEdit.setVisible(false);
                bMemberEntry.setVisible(false);
                JFrame memberEditFrame = new JFrame("Member Edit Frame");

                JPanel p1 = new JPanel(new FlowLayout());
                JPanel p2 = new JPanel(new FlowLayout());
                JPanel panel = new JPanel(new BorderLayout(10,10));
                JButton okBtn = new JButton("OK");

                JButton cancelBtn = new JButton("Cancel");

                JLabel l1 = new JLabel("New Value");

                JTextField jt1 = new JTextField();
                jt1.setPreferredSize(new Dimension(200,30));

                panel.add(p1,BorderLayout.CENTER);
                panel.add(p2,BorderLayout.SOUTH);

                p1.add(l1);
                p1.add(jt1);

                p2.add(okBtn);
                p2.add(cancelBtn);

                okBtn.addActionListener(a -> {
                    bEdit.setVisible(true);
                    bMemberEntry.setVisible(true);

                    inputedValue = jt1.getText();


                    if (selectedColumn == -1) {

                        return;
                    }



                    String[] columnsql = { "memberId", "memberName", "phone", "email", "address", "registrationDate" };

                    if (selectedColumn < 0 || selectedColumn >= columnsql.length) {

                        return;
                    }

                    String columnName = columnsql[selectedColumn];




                    String membersql = "UPDATE members SET " + columnName + " = ? WHERE memberId = ?";
                    try (Connection conn = DBConnection.getConnection();
                         PreparedStatement pstmt = conn.prepareStatement(membersql)) {
                        pstmt.setObject(1, inputedValue);
                        pstmt.setInt(2, memberId);
                        int updated = pstmt.executeUpdate();

                        pstmt.close();

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }


                    jt1.setText("");
                    inputedValue = null;
                    removeEditFrame(memberEditFrame);
                    b2.doClick();


                });


                memberEditFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                memberEditFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {bEdit.setVisible(true);
                    }
                });
                memberEditFrame.setSize(300,200);
                memberEditFrame.setResizable(false);
                memberEditFrame.setVisible(true);
                memberEditFrame.setLocation(1350,250);
                memberEditFrame.add(panel);

                cancelBtn.addActionListener( b3e -> {
                    bEdit.setVisible(true);
                    bMemberEntry.setVisible(true);
                    table.clearSelection();
                    memberEditFrame.dispose();
                });

                jt1.addActionListener(se -> {
                    okBtn.doClick();
                });

                jt1.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            okBtn.doClick();
                        }
                    }
                });



                okBtn.setPreferredSize(new Dimension(100,50));
                cancelBtn.setPreferredSize(new Dimension(100,50));


                okBtn.setBackground(Color.WHITE);

                cancelBtn.setBackground(Color.WHITE);

                b1.addActionListener(e2 ->{removeEditFrame(memberEditFrame);});
                b3.addActionListener(e2 ->{removeEditFrame(memberEditFrame);});
                b4.addActionListener(e2 ->{removeEditFrame(memberEditFrame);});
                b5.addActionListener(e2 ->{removeEditFrame(memberEditFrame);});

                // Crucial step: Tell Swing to rebuild the layout and redraw the screen
                pMainPanel.revalidate();
                pMainPanel.repaint();


            });

            bMemberEntry.addActionListener( be2 -> {
                if (memberEntryOpen) {
                    return;
                }

                memberEntryOpen = true;

                JFrame memberentry = new JFrame();
                memberentry.setTitle("Member Entry");



                memberentry.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        memberEntryOpen = false;
                        bEdit.setVisible(true);
                        bMemberEntry.setVisible(true);
                    }
                });

                bEdit.setVisible(false);
                bMemberEntry.setVisible(false);

                memberentry.setLocation(1372,550);
                memberentry.setSize(550,300);
                memberentry.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                memberentry.setVisible(true);
                JLabel membername = new JLabel("Member Name");
                JLabel memberphone = new JLabel("Phone Number");
                JLabel memberemail = new JLabel("Email");
                JLabel memberaddress = new JLabel("Address");
                JLabel memberRDate = new JLabel("Registration Date");

                JTextField tmembername = new JTextField(20);
                JTextField tmemberphone = new JTextField(20);
                JTextField tmemberemail = new JTextField(20);
                JTextField tmemberaddress = new JTextField(20);
                JTextField tmemberRDate = new JTextField(20);

                tmemberRDate.setText("Select date from calendar");
                new ThemedDatePicker(tmemberRDate);


                JButton save = new JButton("Save");
                JButton cancel = new JButton("Cancel");
                JPanel pmain = new JPanel(new BorderLayout());
                JPanel p1 = new JPanel(new GridLayout(5, 2));
                Font myFont = new Font("Roboto", Font.PLAIN, 20);

                //setting labels fonts
                membername.setFont(myFont);
                memberphone.setFont(myFont);
                memberemail.setFont(myFont);
                memberaddress.setFont(myFont);
                memberRDate.setFont(myFont);

                Color fontColor = new Color(32, 32, 32);
                //setting labels fonts color
                membername.setForeground(fontColor);
                memberphone.setForeground(fontColor);
                memberemail.setForeground(fontColor);
                memberaddress.setForeground(fontColor);
                tmemberRDate.setForeground(fontColor);

                //setting size of text field box
                tmembername.setPreferredSize(new Dimension(20, 30));
                tmemberphone.setPreferredSize(new Dimension(20, 30));
                tmemberemail.setPreferredSize(new Dimension(20, 30));
                tmemberaddress.setPreferredSize(new Dimension(20, 30));
                tmemberRDate.setPreferredSize(new Dimension(20, 30));

                // for alignment of label and text fields
                LayoutManager layout = new FlowLayout(FlowLayout.CENTER, 5, 10);
                //setting label panels alignments
                JPanel plb = new JPanel(layout);
                JPanel pla = new JPanel(layout);
                JPanel ply = new JPanel(layout);
                JPanel plp = new JPanel(layout);
                JPanel pld = new JPanel(layout);
                //adding labels into aligned panels
                plb.add(membername);
                pla.add(memberphone);
                ply.add(memberemail);
                plp.add(memberaddress);
                pld.add(memberRDate);

                //setting text field panels alignments
                JPanel ptb = new JPanel(layout);
                JPanel pta = new JPanel(layout);
                JPanel pty = new JPanel(layout);
                JPanel ptp = new JPanel(layout);
                JPanel ptd = new JPanel(layout);
                //adding text fields into aligned panels
                ptb.add(tmembername);
                pta.add(tmemberphone);
                pty.add(tmemberemail);
                ptp.add(tmemberaddress);
                ptd.add(tmemberRDate);

                //adding small panels into main GridLayout panel
                p1.add(plb);
                p1.add(ptb);

                p1.add(pla);
                p1.add(pta);

                p1.add(ply);
                p1.add(pty);

                p1.add(plp);
                p1.add(ptp);

                p1.add(pld);
                p1.add(ptd);

                //creating panel for buttons
                JPanel p2 = new JPanel(new FlowLayout());
                p2.add(save);
                p2.add(cancel);


                //creating main panel
                JPanel panel = new JPanel(new BorderLayout());
                panel.add(p1, BorderLayout.CENTER);
                panel.add(p2, BorderLayout.SOUTH);
                add(panel);

                // background color of main panel
                Color bgcolor = new Color(240, 240, 240);
                // background color of button panel
                p2.setBackground(new Color(192, 192, 192));

                //setting background color of label panels and textfield panels
                plb.setBackground(bgcolor);
                pla.setBackground(bgcolor);
                ply.setBackground(bgcolor);
                plp.setBackground(bgcolor);

                ptb.setBackground(bgcolor);
                pta.setBackground(bgcolor);
                pty.setBackground(bgcolor);
                ptp.setBackground(bgcolor);

                save.addActionListener(se -> {
                    bEdit.setVisible(true);
                    bMemberEntry.setVisible(true);
                    String name = tmembername.getText();
                    String phone = tmemberphone.getText();
                    String email = tmemberemail.getText();
                    String address = tmemberaddress.getText();
                    String date = tmemberRDate.getText();

                    if(name.equals("") || phone.equals("") || email.equals("") || address.equals("") || date.equals("")) {
                        JOptionPane.showMessageDialog(null, "You should input first!");
                    }else {

                        String sqlInsert = "INSERT INTO members(memberName,phone,email,address,registrationDate) VALUES (?, ?, ?, ?, ?)";
                        try (Connection conn = DBConnection.getConnection();
                             PreparedStatement pstatement = conn.prepareStatement(sqlInsert)) {


                            pstatement.setString(1, name);
                            pstatement.setString(2, phone);
                            pstatement.setString(3, email);
                            pstatement.setString(4, address);
                            pstatement.setString(5, date);
                            pstatement.executeUpdate();

                            tmembername.setText("");
                            tmemberphone.setText("");
                            tmemberemail.setText("");
                            tmemberaddress.setText("");
                            tmemberRDate.setText("");

                        }//try

                        catch(SQLException se2) {

                            se2.printStackTrace();
                        }//catch
                    }//else

                    b2.doClick();
                    memberentry.dispose();


                });

                cancel.addActionListener(ce -> {
                    bEdit.setVisible(true);
                    bMemberEntry.setVisible(true);
                    memberentry.dispose();
                });

                tmemberRDate.addActionListener(oe -> {
                    save.doClick();
                });

                tmemberRDate.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            save.doClick();
                        }
                    }
                });

                pmain.add(p1,BorderLayout.CENTER);
                pmain.add(p2,BorderLayout.SOUTH);
                memberentry.add(pmain);
                memberentry.add(pmain);


            });

            searchButton.addActionListener(se -> {
                String searchValue =  searchField.getText().trim();

                if(searchValue.isEmpty()){
                    JOptionPane.showMessageDialog(null, "You should input first!");
                }else{


                    String sqlSearch = "select * from members where memberName like ?";
                    try (Connection conn = DBConnection.getConnection();
                         PreparedStatement pstmt = conn.prepareStatement(sqlSearch)) {

                        pstmt.setString(1,"%" + searchValue + "%");

                        model.setRowCount(0);
                        try(ResultSet resultSet = pstmt.executeQuery()){
                            while(resultSet.next()) {
                                model.addRow(new Object[] {
                                        resultSet.getInt(1),
                                        resultSet.getString(2),
                                        resultSet.getString(3),
                                        resultSet.getString(4),
                                        resultSet.getString(5),
                                        resultSet.getString(6)
                                });
                            }
                        }



                    }//try
                    catch(SQLException se1) {
                        se1.printStackTrace();
                    }//catch
                }
            });



        }

    }


    class ShowBorrow implements ActionListener{
        public void actionPerformed(ActionEvent e){

            pMainPanel.removeAll();
            pMainPanel.revalidate();
            pMainPanel.repaint();

            for (ActionListener listener : bEdit.getActionListeners()) {
                bEdit.removeActionListener(listener);
            }

            pMain.removeAll();
            pMain.setLayout(new BorderLayout());

            searchPanel.setVisible(false);




//            pMainPanel.remove(bReturnEntry);
//            pMainPanel.remove(bMemberEntry);
//            pMainPanel.remove(bLibrarianEntry);
//            pMainPanel.remove(bBookEntry);

            pMainPanel.add(bBorrowEntry);
            pMainPanel.add(bEdit);
            pMainPanel.add(deleteBorrow);
            pMainPanel.add(returnInBorrow);


            deleteBook.setVisible(false);
            deleteMember.setVisible(false);
            deleteBorrow.setVisible(false);
            deleteReturn.setVisible(false);
            deleteLibrarian.setVisible(false);

            bEdit.setVisible(false);
            returnInBorrow.setVisible(false);

            bBookEntry.setVisible(false);
            bMemberEntry.setVisible(false);
            bBorrowEntry.setVisible(true);
            bReturnEntry.setVisible(false);
            bLibrarianEntry.setVisible(false);
            returnInBorrow.setVisible(false);

            // creating table for data outputs
            String[] columns = {"Borrow ID","Book ID","Member ID","Borrow Date","Due Date"};
            DefaultTableModel model = new DefaultTableModel(columns,0);
            JTable table = new JTable(model);
            table.setRowHeight(25);

            table.getTableHeader().setFont(table.getTableHeader().getFont().deriveFont(Font.BOLD));// set table header font bold
            table.getTableHeader().setBackground(new Color(207, 108, 37));//table header box bgcolor
            table.getTableHeader().setForeground(new Color(255,255,255));
            table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getPreferredSize().width,30));//table header box size
            //make the text in table CENTER
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }


            String sqlSelect = "select * from borrows";
            try (Connection conn = DBConnection.getConnection();
                 Statement statement = conn.createStatement()){


                try(ResultSet resultSet = statement.executeQuery(sqlSelect)){
                    while(resultSet.next()) {
                        model.addRow(new Object[] {
                                resultSet.getInt(1),
                                resultSet.getInt(2),
                                resultSet.getInt(3),
                                resultSet.getString(4),
                                resultSet.getString(5)
                        });
                    }
                }



                JScrollPane scrollPane = new JScrollPane(table);
                JScrollBar verticalBar = scrollPane.getVerticalScrollBar();


                barUIsetting(verticalBar);
                scrollPane.setPreferredSize(new java.awt.Dimension(500,250));
                pMain.add(scrollPane, BorderLayout.CENTER);
                pMain.revalidate();
                pMain.repaint();


            }//try
            catch(SQLException se) {
                se.printStackTrace();
            }//catch

            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    bEdit.setVisible(true);
                    deleteBorrow.setVisible(true);
                    returnInBorrow.setVisible(true);

                    selectedRow = table.rowAtPoint(e.getPoint());
                    selectedColumn = table.columnAtPoint(e.getPoint());


                    if (selectedRow != -1 && selectedColumn != -1) {
                        borrowId = (int) table.getValueAt(selectedRow, 0);


                        Object selectedValue = table.getValueAt(selectedRow, selectedColumn);

                    }
                }
            });

            deleteBorrow.addActionListener(be-> {


                String sqlDelete = "DELETE FROM borrows WHERE borrowId = "+borrowId;
                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {
                    pstmt.executeUpdate();

                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                }



            });

            returnInBorrow.addActionListener(be-> {
                boolean isReturnFrameOpen = false;
                if (isReturnFrameOpen) {
                    return;
                }

                isReturnFrameOpen = true;

                JFrame frame = new JFrame("Return Book");

                JPanel mainPanel = new JPanel();
                mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
                mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));



                JLabel description = new JLabel("Select the return date.");
                description.setAlignmentX(Component.LEFT_ALIGNMENT);

                JLabel dateLabel = new JLabel("Return Date");
                dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

                JTextField returnDateField = new JTextField(20);
                new ThemedDatePicker(returnDateField);

                returnDateField.setPreferredSize(new Dimension(300, 35));
                returnDateField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
                returnDateField.setAlignmentX(Component.LEFT_ALIGNMENT);

                JButton okButton = new JButton("OK");
                JButton cancelButton = new JButton("Cancel");

                JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
                buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

                buttonPanel.add(okButton);
                buttonPanel.add(cancelButton);


                mainPanel.add(Box.createVerticalStrut(5));
                mainPanel.add(description);
                mainPanel.add(Box.createVerticalStrut(25));
                mainPanel.add(dateLabel);
                mainPanel.add(Box.createVerticalStrut(8));
                mainPanel.add(returnDateField);
                mainPanel.add(Box.createVerticalStrut(25));
                mainPanel.add(buttonPanel);

                frame.add(mainPanel);

                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(350, 230);
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);

                int tborrowIdText = borrowId;
                String tborrowDateText = "";





                String sqlSelect1 = "select * from borrows where borrowId = "+borrowId;
                try (Connection conn = DBConnection.getConnection();
                     Statement statement = conn.createStatement()){
                    try(ResultSet resultSet = statement.executeQuery(sqlSelect1)){

                        while(resultSet.next()) {
                            tborrowDateText = resultSet.getString(4);

                        }
                        System.out.println(tborrowDateText);

                        }
                    } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }



                String finalTborrowDateText2 = tborrowDateText;


                okButton.addActionListener(le -> {
                    double tfineText = 0;
                    String treturnDateText = returnDateField.getText();
                    LocalDate borrowDate = LocalDate.parse(finalTborrowDateText2);
                    LocalDate returnDate2 = LocalDate.parse(treturnDateText);

                    long days = ChronoUnit.DAYS.between(borrowDate, returnDate2);

                    if(days<=10){
                        tfineText= 0;
                    }else if(days > 10 && days <=30){
                        tfineText = 3000;
                    }else if(days > 30){
                        tfineText= 5000;
                    } else {
                        tfineText = 0;
                    }

                    String sqlInsert = "INSERT INTO returns(borrowId,borrowDate,returnDate,fine) VALUES (?, ?, ?, ?)";
                    try (Connection conn = DBConnection.getConnection();
                         PreparedStatement pstatement = conn.prepareStatement(sqlInsert)) {

                        pstatement.setInt(1, tborrowIdText);
                        pstatement.setString(2, finalTborrowDateText2);
                        pstatement.setString(3, treturnDateText);
                        pstatement.setDouble(4, tfineText);

                        pstatement.executeUpdate();
                    }
                    catch(SQLException ex1){
                        ex1.printStackTrace();
                    }
                    String sqlDelete = "DELETE FROM borrows WHERE borrowId = "+borrowId;
                    try (Connection conn = DBConnection.getConnection();
                         PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {
                        pstmt.executeUpdate();

                    }
                    catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    frame.dispose();
                    frame.setVisible(false);

                    b3.doClick();
                });

                cancelButton.addActionListener(le2 -> {
                    frame.dispose();
                    b3.doClick();
                });

                frame.setSize(250,250);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setLocation(1385,300);
                frame.setVisible(true);




            });

            bEdit.addActionListener(be -> {
                bEdit.setVisible(false);
                JFrame borrowEditFrame = new JFrame("Borrow Edit Frame");
                JPanel p1 = new JPanel(new FlowLayout());
                JPanel p2 = new JPanel(new FlowLayout());
                JPanel panel = new JPanel(new BorderLayout(10,10));
                JButton okBtn = new JButton("OK");
                JButton cancelBtn = new JButton("Cancel");

                JLabel l1 = new JLabel("New Value");

                JTextField jt1 = new JTextField();
                jt1.setPreferredSize(new Dimension(200,30));

                panel.add(p1,BorderLayout.CENTER);
                panel.add(p2,BorderLayout.SOUTH);

                p1.add(l1);
                p1.add(jt1);

                p2.add(okBtn);
                p2.add(cancelBtn);

                okBtn.addActionListener(a -> {

                    inputedValue = jt1.getText();


                    if (selectedColumn == -1) {

                        return;
                    }


                    String[] columnsql = { "borrowId", "bookID", "memberId", "borrowDate", "dueDate" };

                    if (selectedColumn < 0 || selectedColumn >= columnsql.length) {

                        return;
                    }

                    String columnName = columnsql[selectedColumn];




                    String borrowsql = "UPDATE borrows SET " + columnName + " = ? WHERE borrowId = ?";
                    try (Connection conn = DBConnection.getConnection();
                         PreparedStatement pstmt = conn.prepareStatement(borrowsql)) {
                        pstmt.setObject(1, inputedValue);
                        pstmt.setInt(2, borrowId);
                        int updated = pstmt.executeUpdate();


                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }


                    jt1.setText("");
                    inputedValue = null;
                    removeEditFrame(borrowEditFrame);

                    b3.doClick();

                });


                borrowEditFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                borrowEditFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        bEdit.setVisible(true);
                    }
                });
                borrowEditFrame.setSize(300,200);
                borrowEditFrame.setResizable(false);
                borrowEditFrame.setVisible(true);
                borrowEditFrame.setLocation(1350,250);
                borrowEditFrame.add(panel);

                cancelBtn.addActionListener( b3e -> {
                    table.clearSelection();
                    borrowEditFrame.dispose();
                });

                jt1.addActionListener(se -> {
                    okBtn.doClick();
                });

                jt1.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            okBtn.doClick();
                        }
                    }
                });



                okBtn.setPreferredSize(new Dimension(100,50));
                cancelBtn.setPreferredSize(new Dimension(100,50));


                okBtn.setBackground(Color.WHITE);

                cancelBtn.setBackground(Color.WHITE);

                b2.addActionListener(e2 ->{removeEditFrame(borrowEditFrame);});
                b3.addActionListener(e2 ->{removeEditFrame(borrowEditFrame);});
                b4.addActionListener(e2 ->{removeEditFrame(borrowEditFrame);});
                b5.addActionListener(e2 ->{removeEditFrame(borrowEditFrame);});

                // Crucial step: Tell Swing to rebuild the layout and redraw the screen
                pMainPanel.revalidate();
                pMainPanel.repaint();


            });// end of bEdit





            AtomicInteger selectedMemberID = new AtomicInteger();
            AtomicInteger selectedBookID = new AtomicInteger();
            bBorrowEntry.addActionListener( be2 -> {
                if (borrowEntryOpen) {
                    return;
                }

                borrowEntryOpen = true;

                JFrame borrowentry = new JFrame();

                borrowentry.setTitle("Borrow Entry");



                borrowentry.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        borrowEntryOpen = false;
                        bEdit.setVisible(true);
                        bBorrowEntry.setVisible(true);
                    }
                });



                JLabel bookId = new JLabel("Book Id");
                JLabel memberId = new JLabel("Member ID");
                JLabel borrowDate = new JLabel("Borrow Date");
                JLabel dueDate = new JLabel("Due Date");


                //JTextField tbookId = new JTextField(20);
                //JTextField tmemberId = new JTextField(20);
                JTextField tborrowDate = new JTextField(20);
                JTextField tdueDate = new JTextField(20);

                tborrowDate.setText("Select date from calendar");
                tdueDate.setText("Select date from calendar");

                new ThemedDatePicker(tdueDate);
                new ThemedDatePicker(tborrowDate);



                JButton save = new JButton("Save");
                JButton cancel = new JButton("Cancel");
                JPanel pmain = new JPanel(new BorderLayout());
                JPanel p1 = new JPanel(new GridLayout(4, 2));
                Font myFont = new Font("Roboto", Font.PLAIN, 20);

                //this is getting the memberid from the membertable and try to insert into dropdown in borrow list
                JComboBox<String> dropdown = new JComboBox<>();
                ArrayList<Integer> memberids = new ArrayList<Integer>();
                ArrayList<String> names = new ArrayList<String>();
                String sqlSelectm = "select * from members";

                try (Connection conn = DBConnection.getConnection();
                     Statement statement = conn.createStatement()) {
                    try (ResultSet resultSet = statement.executeQuery(sqlSelectm)) {
                        while (resultSet.next()) {
                            int memberid = resultSet.getInt(1);
                            String membername = resultSet.getString(2);
                            memberids.add(memberid);
                            names.add(membername);
//                            model.addRow(new Object[] {
//                                    resultSet.getInt(1),
//                                    resultSet.getString(2),
//                                    resultSet.getString(3),
//                                    resultSet.getString(4),
//                                    resultSet.getString(5),
//                                    resultSet.getString(6)
//                            });
                        }
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                for (int i = 0; i < memberids.size(); i++) {
                    String item = memberids.get(i) + " (" + names.get(i) + ")";
                    dropdown.addItem(item);
                }

                dropdown.addActionListener(de -> {


                    int selectedIndex = dropdown.getSelectedIndex();

                    if (selectedIndex != -1) {
                        selectedMemberID.set(memberids.get(selectedIndex));

                        System.out.println(selectedMemberID.get());
                    }
                });


                //this is getting the bookid from the booktable and try to insert into dropdown in borrow list
                JComboBox<String> dropdownbook = new JComboBox<>();
                ArrayList<Integer> bookids = new ArrayList<Integer>();
                ArrayList<String> booknames = new ArrayList<String>();

                String sqlSelectbook = "SELECT * FROM booklist";
                try (Connection conn = DBConnection.getConnection();
                     Statement statement = conn.createStatement()) {



                    try (ResultSet resultSet = statement.executeQuery(sqlSelectbook)) {
                        while (resultSet.next()) {
                            int bookid = resultSet.getInt(1);
                            String bookname = resultSet.getString(2);
                            bookids.add(bookid);
                            booknames.add(bookname);

//                            model.addRow(new Object[]{
//                                    resultSet.getInt(1),
//                                    resultSet.getString(2),
//                                    resultSet.getString(3),
//                                    resultSet.getString(4),
//                                    resultSet.getInt(5),
//                                    resultSet.getString(6)
//                            });
                        }
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                for (int i = 0; i < bookids.size(); i++) {
                    String item = bookids.get(i) + " (" + booknames.get(i) + ")";
                    dropdownbook.addItem(item);
                }

                dropdownbook.addActionListener(de -> {


                    int selectedIndex = dropdownbook.getSelectedIndex();

                    if (selectedIndex != -1) {
                        selectedBookID.set(bookids.get(selectedIndex));

                        System.out.println(selectedBookID.get());
                    }
                });


                dropdown.setPreferredSize(new Dimension(200, 35));
                dropdownbook.setPreferredSize(new Dimension(200, 35));

                //setting labels fonts
                bookId.setFont(myFont);
                memberId.setFont(myFont);
                borrowDate.setFont(myFont);
                dueDate.setFont(myFont);


                Color fontColor = new Color(32, 32, 32);
                //setting labels fonts color
                bookId.setForeground(fontColor);
                memberId.setForeground(fontColor);
                borrowDate.setForeground(fontColor);
                dueDate.setForeground(fontColor);


                //setting size of text field box
                //tbookId.setPreferredSize(new Dimension(15, 30));
                //tmemberId.setPreferredSize(new Dimension(20, 30));
                tborrowDate.setPreferredSize(new Dimension(200, 35));
                tdueDate.setPreferredSize(new Dimension(200, 35));


                // for alignment of label and text fields
                LayoutManager layout = new FlowLayout(FlowLayout.CENTER, 5, 10);
                //setting label panels alignments
                JPanel plb = new JPanel(layout);
                JPanel pla = new JPanel(layout);
                JPanel ply = new JPanel(layout);
                JPanel plp = new JPanel(layout);
                //JPanel pld = new JPanel(layout);
                //adding labels into aligned panels
                plb.add(bookId);
                pla.add(memberId);
                ply.add(borrowDate);
                plp.add(dueDate);


                //setting text field panels alignments
                JPanel ptb = new JPanel(layout);
                JPanel pta = new JPanel(layout);
                JPanel pty = new JPanel(layout);
                JPanel ptp = new JPanel(layout);
                //JPanel ptd = new JPanel(layout);
                //adding text fields into aligned panels
                //ptb.add(tbookId);
                //pta.add(tmemberId);
                ptb.add(dropdownbook);
                pta.add(dropdown);
                pty.add(tborrowDate);
                ptp.add(tdueDate);
                //ptd.add(treturnDate);

                //adding small panels into main GridLayout panel
                p1.add(plb);
                p1.add(ptb);

                p1.add(pla);
                p1.add(pta);

                p1.add(ply);
                p1.add(pty);

                p1.add(plp);
                p1.add(ptp);

//                p1.add(pld);
//                p1.add(ptd);

                //creating panel for buttons
                JPanel p2 = new JPanel(new FlowLayout());
                p2.add(save);
                p2.add(cancel);


                //creating main panel
                JPanel panel = new JPanel(new BorderLayout());
                panel.add(p1, BorderLayout.CENTER);
                panel.add(p2, BorderLayout.SOUTH);
                borrowentry.add(panel);

                // background color of main panel
                Color bgcolor = new Color(240, 240, 240);
                // background color of button panel
                p2.setBackground(new Color(192, 192, 192));

                //setting background color of label panels and textfield panels
                plb.setBackground(bgcolor);
                pla.setBackground(bgcolor);
                ply.setBackground(bgcolor);
                plp.setBackground(bgcolor);

                ptb.setBackground(bgcolor);
                pta.setBackground(bgcolor);
                pty.setBackground(bgcolor);
                ptp.setBackground(bgcolor);





                save.addActionListener(se -> {
                    Integer tbookIdText = selectedBookID.get();
                    Integer tmemberIdText = selectedMemberID.get();
                    String tborrowDateText = tborrowDate.getText();
                    String tdueDateText = tdueDate.getText();
                    //String treturnDateText = treturnDate.getText();

                    if(tbookIdText == null || tmemberIdText == null || tborrowDateText.equals("") || tdueDateText.equals("")) {
                        JOptionPane.showMessageDialog(null, "You should input first!");
                    }else {

                        String sqlInsert = "INSERT INTO borrows(bookID,memberId,borrowDate,dueDate) VALUES (?, ?, ?, ?)";
                        try (Connection conn = DBConnection.getConnection();
                             PreparedStatement pstatement = conn.prepareStatement(sqlInsert)) {

                            pstatement.setInt(1, tbookIdText);
                            pstatement.setInt(2, tmemberIdText);
                            pstatement.setString(3, tborrowDateText);
                            pstatement.setString(4, tdueDateText);

                            pstatement.executeUpdate();

                            //tbookId.setText("");
//                            tmemberId.setText("");
                            tborrowDate.setText("");
                            tdueDate.setText("");

                        }//try

                        catch(SQLException se2) {

                            se2.printStackTrace();
                        }//catch
                    }//else

                    b3.doClick();
                    borrowentry.dispose();


                });

                cancel.addActionListener(ce -> {
                    borrowentry.dispose();
                });

                tdueDate.addActionListener(oe -> {
                    save.doClick();
                });

                tdueDate.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            save.doClick();
                        }
                    }
                });

                pmain.add(p1,BorderLayout.CENTER);
                pmain.add(p2,BorderLayout.SOUTH);
                borrowentry.add(pmain);
                borrowentry.add(pmain);
                borrowentry.setLocation(-3,200);
                borrowentry.setSize(530,300);
                borrowentry.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                borrowentry.setVisible(true);

            });



        }
    }

    class ShowReturn implements ActionListener{
        public void actionPerformed(ActionEvent e){
            pMainPanel.removeAll();
            pMainPanel.revalidate();
            pMainPanel.repaint();

            for (ActionListener listener : bEdit.getActionListeners()) {
                bEdit.removeActionListener(listener);
            }

            pMain.removeAll();
            pMain.setLayout(new BorderLayout());

            searchPanel.setVisible(false);



//            pMainPanel.remove(bBorrowEntry);
//            pMainPanel.remove(bMemberEntry);
//            pMainPanel.remove(bLibrarianEntry);
//            pMainPanel.remove(bBookEntry);

            pMainPanel.add(bReturnEntry);
            pMainPanel.add(bEdit);
            pMainPanel.add(deleteReturn);


            deleteBook.setVisible(false);
            deleteMember.setVisible(false);
            deleteBorrow.setVisible(false);
            deleteReturn.setVisible(false);
            deleteLibrarian.setVisible(false);

            bEdit.setVisible(false);

            bBookEntry.setVisible(false);
            bMemberEntry.setVisible(false);
            bBorrowEntry.setVisible(false);
            bReturnEntry.setVisible(true);
            bLibrarianEntry.setVisible(false);
            returnInBorrow.setVisible(false);

            // creating table for data outputs
            String[] columns = {"Return ID","Borrow ID","Borrow Date","Return Date","Fine"};
            DefaultTableModel model = new DefaultTableModel(columns,0);
            JTable table = new JTable(model);
            table.setRowHeight(25);

            table.getTableHeader().setFont(table.getTableHeader().getFont().deriveFont(Font.BOLD));// set table header font bold
            table.getTableHeader().setBackground(new Color(207, 108, 37));//table header box bgcolor
            table.getTableHeader().setForeground(new Color(255,255,255));
            table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getPreferredSize().width,30));//table header box size
            //make the text in table CENTER
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }


            String sqlSelect = "select * from returns";
            try (Connection conn = DBConnection.getConnection();
                 Statement statement = conn.createStatement()){


                try(ResultSet resultSet = statement.executeQuery(sqlSelect)){
                    while(resultSet.next()) {
                        model.addRow(new Object[] {
                                resultSet.getInt(1),
                                resultSet.getInt(2),
                                resultSet.getString(3),
                                resultSet.getString(4),
                                resultSet.getDouble(5),
                        });
                    }
                }


                JScrollPane scrollPane = new JScrollPane(table);
                JScrollBar verticalBar = scrollPane.getVerticalScrollBar();


                barUIsetting(verticalBar);
                scrollPane.setPreferredSize(new java.awt.Dimension(500,250));
                pMain.add(scrollPane, BorderLayout.CENTER);
                pMain.revalidate();
                pMain.repaint();


            }//try
            catch(SQLException se) {
                se.printStackTrace();
            }//catch

            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    bEdit.setVisible(true);
                    deleteReturn.setVisible(true);
                    selectedRow = table.rowAtPoint(e.getPoint());
                    selectedColumn = table.columnAtPoint(e.getPoint());

                    if (selectedRow != -1 && selectedColumn != -1) {
                        returnId = (int) table.getValueAt(selectedRow, 0);


                        selectedValue = table.getValueAt(selectedRow, selectedColumn);

                    }
                }
            });

            deleteReturn.addActionListener(be-> {


                String sqlDelete = "DELETE FROM returns WHERE returnId = "+returnId;
                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {
                    pstmt.executeUpdate();

                }catch (SQLException ex) {
                    ex.printStackTrace();
                }
                b4.doClick();


            });

            bEdit.addActionListener(be -> {
                bEdit.setVisible(false);
                JFrame returnEditFrame = new JFrame("Return Edit Frame");
                JPanel p1 = new JPanel(new FlowLayout());
                JPanel p2 = new JPanel(new FlowLayout());
                JPanel panel = new JPanel(new BorderLayout(10,10));
                JButton okBtn = new JButton("OK");
                JButton cancelBtn = new JButton("Cancel");

                JLabel l1 = new JLabel("New Value");

                JTextField jt1 = new JTextField();
                jt1.setPreferredSize(new Dimension(200,30));

                panel.add(p1,BorderLayout.CENTER);
                panel.add(p2,BorderLayout.SOUTH);

                p1.add(l1);
                p1.add(jt1);

                p2.add(okBtn);
                p2.add(cancelBtn);

                okBtn.addActionListener(a -> {

                    inputedValue = jt1.getText();


                    if (selectedColumn == -1) {

                        return;
                    }

                    String[] columnsql = { "returnId", "borrowId", "borrowDate", "returnDate", "fine" };

                    if (selectedColumn < 0 || selectedColumn >= columnsql.length) {

                        return;
                    }

                    String columnName = columnsql[selectedColumn];




                    String returnsql = "UPDATE returns SET " + columnName + " = ? WHERE returnId = ?";
                    try (Connection conn = DBConnection.getConnection();
                         PreparedStatement pstmt = conn.prepareStatement(returnsql)) {
                        pstmt.setObject(1, inputedValue);
                        pstmt.setInt(2, returnId);
                        int updated = pstmt.executeUpdate();

                        pstmt.close();

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }


                    jt1.setText("");
                    inputedValue = null;
                    removeEditFrame(returnEditFrame);

                    b4.doClick();

                });


                returnEditFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                returnEditFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        bEdit.setVisible(true);
                    }
                });
                returnEditFrame.setSize(300,200);
                returnEditFrame.setResizable(false);
                returnEditFrame.setVisible(true);
                returnEditFrame.setLocation(1350,250);
                returnEditFrame.add(panel);

                cancelBtn.addActionListener( b3e -> {
                    table.clearSelection();
                    returnEditFrame.dispose();
                });

                jt1.addActionListener(se -> {
                    okBtn.doClick();
                });

                jt1.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            okBtn.doClick();
                        }
                    }
                });



                newBtn1.setPreferredSize(new Dimension(100,50));
                newBtn3.setPreferredSize(new Dimension(100,50));


                okBtn.setBackground(Color.WHITE);

                cancelBtn.setBackground(Color.WHITE);

                b2.addActionListener(e2 ->{removeEditFrame(returnEditFrame);});
                b3.addActionListener(e2 ->{removeEditFrame(returnEditFrame);});
                b1.addActionListener(e2 ->{removeEditFrame(returnEditFrame);});
                b5.addActionListener(e2 ->{removeEditFrame(returnEditFrame);});

                // Crucial step: Tell Swing to rebuild the layout and redraw the screen
                pMainPanel.revalidate();
                pMainPanel.repaint();


            });//end of bEdit

            bReturnEntry.addActionListener( be2 -> {
                if (returnEntryOpen) {
                    return;
                }

                returnEntryOpen = true;

                JFrame returnentry = new JFrame();
                returnentry.setTitle("Return Entry");



                returnentry.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        returnEntryOpen = false;
                        bEdit.setVisible(true);
                        bReturnEntry.setVisible(true);
                    }
                });


                JLabel borrowId = new JLabel("Borrow ID");
                JLabel borrowDate = new  JLabel("Borrow Date");
                JLabel returnDate = new JLabel("Return Date");
                JLabel fine = new JLabel("Fine");
                Font myFont = new Font("Roboto", Font.PLAIN, 20);
                JPanel p1 = new JPanel(new GridLayout(4, 2));


                JTextField tborrowId = new JTextField(20);
                JTextField tborrowDate = new JTextField(20);
                JTextField treturnDate = new JTextField(20);
                JTextField tfine = new JTextField(20);

                tborrowDate.setText("Select date from calendar");
                treturnDate.setText("Select date from calendar");

                new ThemedDatePicker(tborrowDate);
                new ThemedDatePicker(treturnDate);


                JButton save = new JButton("Save");
                JButton cancel = new JButton("Cancel");
                JPanel pmain = new JPanel(new BorderLayout());


                //setting labels fonts
                borrowId.setFont(myFont);
                borrowDate.setFont(myFont);
                returnDate.setFont(myFont);
                fine.setFont(myFont);


                Color fontColor = new Color(32, 32, 32);
                //setting labels fonts color
                borrowId.setForeground(fontColor);
                borrowDate.setForeground(fontColor);
                returnDate.setForeground(fontColor);
                fine.setForeground(fontColor);


                //setting size of text field box
                tborrowId.setPreferredSize(new Dimension(20, 30));
                tborrowDate.setPreferredSize(new Dimension(20, 30));
                treturnDate.setPreferredSize(new Dimension(20, 30));
                tfine.setPreferredSize(new Dimension(20, 30));


                // for alignment of label and text fields
                LayoutManager layout = new FlowLayout(FlowLayout.CENTER, 5, 10);
                //setting label panels alignments
                JPanel plb = new JPanel(layout);
                JPanel pbd = new JPanel(layout);
                JPanel pla = new JPanel(layout);
                JPanel ply = new JPanel(layout);


                //adding labels into aligned panels
                plb.add(borrowId);
                pbd.add(borrowDate);
                pla.add(returnDate);
                ply.add(fine);


                //setting text field panels alignments
                JPanel ptb = new JPanel(layout);
                JPanel ptd = new JPanel(layout);
                JPanel pta = new JPanel(layout);
                JPanel pty = new JPanel(layout);

                //adding text fields into aligned panels
                ptb.add(tborrowId);
                ptd.add(tborrowDate);
                pta.add(treturnDate);
                pty.add(tfine);


                //adding small panels into main GridLayout panel
                p1.add(plb);
                p1.add(ptb);

                p1.add(pbd);
                p1.add(ptd);

                p1.add(pla);
                p1.add(pta);

                p1.add(ply);
                p1.add(pty);




                //creating panel for buttons
                JPanel p2 = new JPanel(new FlowLayout());
                p2.add(save);
                p2.add(cancel);


                //creating main panel
                JPanel panel = new JPanel(new BorderLayout());
                panel.add(p1, BorderLayout.CENTER);
                panel.add(p2, BorderLayout.SOUTH);
                add(panel);

                // background color of main panel
                Color bgcolor = new Color(240, 240, 240);
                // background color of button panel
                p2.setBackground(new Color(192, 192, 192));

                //setting background color of label panels and textfield panels
                plb.setBackground(bgcolor);
                pbd.setBackground(bgcolor);
                pla.setBackground(bgcolor);
                ply.setBackground(bgcolor);


                ptb.setBackground(bgcolor);
                ptd.setBackground(bgcolor);
                pta.setBackground(bgcolor);
                pty.setBackground(bgcolor);


                save.addActionListener(se -> {
                    String tborrowIdText = tborrowId.getText();
                    String tborrowDateText = tborrowDate.getText();
                    String treturnDateText = treturnDate.getText();
                    String tfineText = tfine.getText();


                    if(tborrowIdText.equals("") || treturnDateText.equals("") || tfineText.equals("") || tborrowDateText.equals("")) {
                        JOptionPane.showMessageDialog(null, "You should input first!");
                    }else {

                        String sqlInsert = "INSERT INTO returns(borrowId,borrowDate,returnDate,fine) VALUES (?, ?, ?, ?)";
                        try (Connection conn = DBConnection.getConnection();
                             PreparedStatement pstatement = conn.prepareStatement(sqlInsert)) {

                            pstatement.setString(1, tborrowIdText);
                            pstatement.setString(2, tborrowDateText);
                            pstatement.setString(3, treturnDateText);
                            pstatement.setString(4, tfineText);

                            pstatement.executeUpdate();

                            tborrowId.setText("");
                            tborrowDate.setText("");
                            treturnDate.setText("");
                            tfine.setText("");

                        }//try

                        catch(SQLException se2) {

                            se2.printStackTrace();
                        }//catch
                    }//else

                    b4.doClick();
                    returnentry.dispose();


                });

                cancel.addActionListener(ce -> {
                    returnentry.dispose();
                });

                tfine.addActionListener(oe -> {
                    save.doClick();
                });

                tfine.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            save.doClick();
                        }
                    }
                });

                pmain.add(p1,BorderLayout.CENTER);
                pmain.add(p2,BorderLayout.SOUTH);
                returnentry.add(pmain);
                returnentry.add(pmain);
                returnentry.setLocation(-3,550);
                returnentry.setSize(550,300);
                returnentry.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                returnentry.setVisible(true);

            });


        }
    }

    class ShowLibrarians implements ActionListener{
        public void actionPerformed(ActionEvent e){
            pMainPanel.removeAll();
            pMainPanel.revalidate();
            pMainPanel.repaint();

            for (ActionListener listener : bEdit.getActionListeners()) {
                bEdit.removeActionListener(listener);
            }

            pMain.removeAll();
            pMain.setLayout(new BorderLayout());


            searchPanel.removeAll();
            searchPanel.revalidate();
            searchPanel.repaint();
            searchPanel.setVisible(true);

            JTextField searchField = new JTextField("Search by Librarian name");
            JButton searchButton = new JButton("Search");




            searchField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            });

            searchField.addActionListener(se -> {
                searchButton.doClick();
            });

            searchField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        searchButton.doClick();
                    }
                }
            });

            searchField.setPreferredSize(new Dimension(250, 40));

            searchPanel.add(searchField);
            searchPanel.add(searchButton);
            panel.add(searchPanel,BorderLayout.NORTH);




//            pMainPanel.remove(bBorrowEntry);
//            pMainPanel.remove(bMemberEntry);
//            pMainPanel.remove(bReturnEntry);
//            pMainPanel.remove(bBookEntry);

            pMainPanel.add(bLibrarianEntry);
            pMainPanel.add(bEdit);
            pMainPanel.add(deleteLibrarian);


            deleteBook.setVisible(false);
            deleteMember.setVisible(false);
            deleteBorrow.setVisible(false);
            deleteReturn.setVisible(false);
            deleteLibrarian.setVisible(false);

            bEdit.setVisible(false);

            bBookEntry.setVisible(false);
            bMemberEntry.setVisible(false);
            bBorrowEntry.setVisible(false);
            bReturnEntry.setVisible(false);
            bLibrarianEntry.setVisible(true);
            returnInBorrow.setVisible(false);


            // creating table for data outputs
            String[] columns = {"Librarian ID","Librarian Name","Email","Phone","Role"};
            DefaultTableModel model = new DefaultTableModel(columns,0);
            JTable table = new JTable(model);
            table.setRowHeight(25);

            table.getTableHeader().setFont(table.getTableHeader().getFont().deriveFont(Font.BOLD));// set table header font bold
            table.getTableHeader().setBackground(new Color(207, 108, 37));//table header box bgcolor
            table.getTableHeader().setForeground(new Color(255,255,255));
            table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getPreferredSize().width,30));//table header box size
            //make the text in table CENTER
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

            for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }


            String sqlSelect = "select * from users";
            try (Connection conn = DBConnection.getConnection();
                 Statement statement = conn.createStatement()){


                try(ResultSet resultSet = statement.executeQuery(sqlSelect)){
                    while(resultSet.next()) {
                        model.addRow(new Object[] {
                                resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getString (3),
                                resultSet.getString(4),
                                resultSet.getString(5)
                        });
                    }
                }



                JScrollPane scrollPane = new JScrollPane(table);
                JScrollBar verticalBar = scrollPane.getVerticalScrollBar();


                barUIsetting(verticalBar);
                scrollPane.setPreferredSize(new java.awt.Dimension(500,250));
                pMain.add(scrollPane, BorderLayout.CENTER);
                pMain.revalidate();
                pMain.repaint();


            }//try
            catch(SQLException se) {
                se.printStackTrace();
            }//catch

            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    bEdit.setVisible(true);
                    deleteLibrarian.setVisible(true);
                    selectedRow = table.rowAtPoint(e.getPoint());
                    selectedColumn = table.columnAtPoint(e.getPoint());
                    userId = (int) table.getValueAt(selectedRow, 0);

                    if (selectedRow != -1 && selectedColumn != -1) {



                        selectedValue = table.getValueAt(selectedRow, selectedColumn);

                    }
                }
            });

            deleteLibrarian.addActionListener(be-> {


                String sqlDelete = "DELETE FROM users WHERE userId = "+userId;
                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {
                    pstmt.executeUpdate();

                }catch (SQLException ex) {
                    ex.printStackTrace();
                }
                b5.doClick();


            });

            bEdit.addActionListener(be -> {
                bEdit.setVisible(false);
                JFrame userEditFrame = new JFrame("User Edit Frame");
                JPanel p1 = new JPanel(new FlowLayout());
                JPanel p2 = new JPanel(new FlowLayout());
                JPanel panel = new JPanel(new BorderLayout(10,10));
                JButton okBtn = new JButton("OK");
                JButton cancelBtn = new JButton("Cancel");

                JLabel l1 = new JLabel("New Value");

                JTextField jt1 = new JTextField();
                jt1.setPreferredSize(new Dimension(200,30));

                panel.add(p1,BorderLayout.CENTER);
                panel.add(p2,BorderLayout.SOUTH);

                p1.add(l1);
                p1.add(jt1);

                p2.add(okBtn);
                p2.add(cancelBtn);

                okBtn.addActionListener(a -> {

                    inputedValue = jt1.getText();


                    if (selectedColumn == -1) {

                        return;
                    }

                    String[] columnsql = { "userId", "userName", "email", "phone", "role", "password" };

                    if (selectedColumn < 0 || selectedColumn >= columnsql.length) {

                        return;
                    }

                    String columnName = columnsql[selectedColumn];





                    String usersql = "UPDATE users SET " + columnName + " = ? WHERE userId = ?";
                    try (Connection conn = DBConnection.getConnection();
                         PreparedStatement pstmt = conn.prepareStatement(usersql)) {
                        pstmt.setObject(1, inputedValue);
                        pstmt.setInt(2, userId);
                        int updated = pstmt.executeUpdate();


                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }


                    jt1.setText("");
                    inputedValue = null;
                    removeEditFrame(userEditFrame);

                    b5.doClick();

                });


                userEditFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                userEditFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        bEdit.setVisible(true);
                    }
                });
                userEditFrame.setSize(300,200);
                userEditFrame.setResizable(false);
                userEditFrame.setVisible(true);
                userEditFrame.setLocation(1350,250);
                userEditFrame.add(panel);

                cancelBtn.addActionListener( b3e -> {
                    table.clearSelection();
                    userEditFrame.dispose();
                });

                jt1.addActionListener(se -> {
                    okBtn.doClick();
                });

                jt1.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            okBtn.doClick();
                        }
                    }
                });



                okBtn.setPreferredSize(new Dimension(100,50));
                cancelBtn.setPreferredSize(new Dimension(100,50));


                okBtn.setBackground(Color.WHITE);

                cancelBtn.setBackground(Color.WHITE);

                b2.addActionListener(e2 ->{removeEditFrame(userEditFrame);});
                b3.addActionListener(e2 ->{removeEditFrame(userEditFrame);});
                b4.addActionListener(e2 ->{removeEditFrame(userEditFrame);});
                b5.addActionListener(e2 ->{removeEditFrame(userEditFrame);});

                // Crucial step: Tell Swing to rebuild the layout and redraw the screen
                pMainPanel.revalidate();
                pMainPanel.repaint();


            });

            bLibrarianEntry.addActionListener( be2 -> {
                if (librarianEntryOpen) {
                    return;
                }

                librarianEntryOpen = true;

                JFrame librarianentry = new JFrame();
                librarianentry.setTitle("Librarian Entry");




                librarianentry.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        librarianEntryOpen = false;
                        bEdit.setVisible(true);
                        bLibrarianEntry.setVisible(true);
                    }
                });

                librarianentry.setLocation(1372,400);
                librarianentry.setSize(550,300);
                librarianentry.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                librarianentry.setVisible(true);

                JLabel name = new JLabel("Librarian Name");
                JLabel email = new JLabel("Email");
                JLabel phone = new JLabel("Phone");
                JLabel role = new JLabel("Role");
                JLabel passwordLabel = new JLabel("Password");

                JTextField tname = new JTextField(20);
                JTextField temail = new JTextField(20);
                JTextField tphone = new JTextField(20);
                JTextField trole = new JTextField(20);
                JTextField tpasswordLabel = new JTextField(20);
                JButton save = new JButton("Save");
                JButton cancel = new JButton("Cancel");
                JPanel pmain = new JPanel(new BorderLayout());
                JPanel p1 = new JPanel(new GridLayout(5, 2));
                Font myFont = new Font("Roboto", Font.PLAIN, 20);

                //setting labels fonts
                name.setFont(myFont);
                email.setFont(myFont);
                phone.setFont(myFont);
                role.setFont(myFont);
                passwordLabel.setFont(myFont);

                Color fontColor = new Color(32, 32, 32);
                //setting labels fonts color
                name.setForeground(fontColor);
                email.setForeground(fontColor);
                phone.setForeground(fontColor);
                role.setForeground(fontColor);
                passwordLabel.setForeground(fontColor);

                //setting size of text field box
                tname.setPreferredSize(new Dimension(20, 30));
                temail.setPreferredSize(new Dimension(20, 30));
                tphone.setPreferredSize(new Dimension(20, 30));
                trole.setPreferredSize(new Dimension(20, 30));
                tpasswordLabel.setPreferredSize(new Dimension(20, 30));

                // for alignment of label and text fields
                LayoutManager layout = new FlowLayout(FlowLayout.CENTER, 5, 10);
                //setting label panels alignments
                JPanel plb = new JPanel(layout);
                JPanel pla = new JPanel(layout);
                JPanel ply = new JPanel(layout);
                JPanel plp = new JPanel(layout);
                JPanel plpw = new JPanel(layout);
                //adding labels into aligned panels
                plb.add(name);
                pla.add(email);
                ply.add(phone);
                plp.add(role);
                plpw.add(passwordLabel);

                //setting text field panels alignments
                JPanel ptb = new JPanel(layout);
                JPanel pta = new JPanel(layout);
                JPanel pty = new JPanel(layout);
                JPanel ptp = new JPanel(layout);
                JPanel ptpw = new JPanel(layout);
                //adding text fields into aligned panels
                ptb.add(tname);
                pta.add(temail);
                pty.add(tphone);
                ptp.add(trole);
                ptpw.add(tpasswordLabel);

                //adding small panels into main GridLayout panel
                p1.add(plb);
                p1.add(ptb);

                p1.add(pla);
                p1.add(pta);

                p1.add(ply);
                p1.add(pty);

                p1.add(plp);
                p1.add(ptp);

                p1.add(plpw);
                p1.add(ptpw);

                //creating panel for buttons
                JPanel p2 = new JPanel(new FlowLayout());
                p2.add(save);
                p2.add(cancel);


                //creating main panel
                JPanel panel = new JPanel(new BorderLayout());
                panel.add(p1, BorderLayout.CENTER);
                panel.add(p2, BorderLayout.SOUTH);
                add(panel);

                // background color of main panel
                Color bgcolor = new Color(240, 240, 240);
                // background color of button panel
                p2.setBackground(new Color(192, 192, 192));

                //setting background color of label panels and textfield panels
                plb.setBackground(bgcolor);
                pla.setBackground(bgcolor);
                ply.setBackground(bgcolor);
                plp.setBackground(bgcolor);
                plpw.setBackground(bgcolor);

                ptb.setBackground(bgcolor);
                pta.setBackground(bgcolor);
                pty.setBackground(bgcolor);
                ptp.setBackground(bgcolor);
                ptpw.setBackground(bgcolor);


                save.addActionListener(se -> {
                    pMainPanel.add(bEdit);
                    pMainPanel.add(bBookEntry);
                    String tnameText = tname.getText();
                    String temailText = temail.getText();
                    String tphoneText = tphone.getText();
                    String troleText = trole.getText();
                    String tpasswordText = tpasswordLabel.getText();

                    if(tnameText.equals("") || temailText.equals("") || tphoneText.equals("") || troleText.equals("") || tpasswordText.equals("")) {
                        JOptionPane.showMessageDialog(null, "You should input first!");
                    }else {

                        String sqlInsert = "INSERT INTO users(userName,email,phone,role,password) VALUES (?, ?, ?, ?, ?)";
                        try (Connection conn = DBConnection.getConnection();
                             PreparedStatement pstatement = conn.prepareStatement(sqlInsert)) {


                            pstatement.setString(1, tnameText);
                            pstatement.setString(2, temailText);
                            pstatement.setString(3, tphoneText);
                            pstatement.setString(4, troleText);
                            pstatement.setString(5, tpasswordText);
                            pstatement.executeUpdate();

                            tname.setText("");
                            temail.setText("");
                            tphone.setText("");
                            trole.setText("");
                            tpasswordLabel.setText("");

                        }//try

                        catch(SQLException se2) {

                            se2.printStackTrace();
                        }//catch
                    }//else

                    b5.doClick();
                    librarianentry.dispose();


                });

                cancel.addActionListener(ce -> {
                    pMainPanel.add(bEdit);
                    pMainPanel.add(bBookEntry);
                    librarianentry.dispose();
                });

                tpasswordLabel.addActionListener(oe -> {
                    save.doClick();
                });

                tpasswordLabel.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            save.doClick();
                        }
                    }
                });

                pmain.add(p1,BorderLayout.CENTER);
                pmain.add(p2,BorderLayout.SOUTH);
                librarianentry.add(pmain);
                librarianentry.add(pmain);


            });

            searchButton.addActionListener(se -> {
                String searchValue =  searchField.getText().trim();

                if(searchValue.isEmpty()){
                    JOptionPane.showMessageDialog(null, "You should input first!");
                }else{

                    String sqlSearch = "select * from users where userName like ?";
                    try (Connection conn = DBConnection.getConnection();
                         PreparedStatement pstmt = conn.prepareStatement(sqlSearch)) {

                        pstmt.setString(1,"%" + searchValue + "%");

                        model.setRowCount(0);
                        try(ResultSet resultSet = pstmt.executeQuery()){
                            while(resultSet.next()) {
                                model.addRow(new Object[] {
                                        resultSet.getInt(1),
                                        resultSet.getString(2),
                                        resultSet.getString(3),
                                        resultSet.getString(4),
                                        resultSet.getString(5),
                                        resultSet.getString(6)
                                });
                            }
                        }



                    }//try
                    catch(SQLException se1) {
                        se1.printStackTrace();
                    }//catch
                }
            });


        }
    }



    // ===== Warm Library Theme (style-only additions) =====
    private static final Color WOOD = new Color(62, 43, 32);          // #3E2B20
    private static final Color TERRACOTTA = new Color(169, 83, 43);  // #A9532B
    private static final Color CREAM = new Color(245, 235, 221);     // #F5EBDD
    private static final Color BEIGE = new Color(232, 220, 203);     // #E8DCCB
    private static final Color ESPRESSO = new Color(53, 35, 24);     // #352318
    private static final Color TABLE_ALT = new Color(248, 242, 233);
    private static final Color GRID = new Color(220, 207, 190);

    private void installWarmLibraryTheme() {
        applyWarmLibraryStyle(this);

        // Style frames/dialogs created later (for example, Add New Book) without
        // changing their existing listeners, layout, database code, or behavior.
        Toolkit.getDefaultToolkit().addAWTEventListener(event -> {
            if (event instanceof WindowEvent
                    && ((WindowEvent) event).getID() == WindowEvent.WINDOW_OPENED) {
                Window openedWindow = ((WindowEvent) event).getWindow();
                SwingUtilities.invokeLater(() -> applyWarmLibraryStyle(openedWindow));
            }
        }, AWTEvent.WINDOW_EVENT_MASK);
    }

    private void applyWarmLibraryStyle(Component component) {
        if (component == null) return;

        if (component instanceof JFrame) {
            JFrame frame = (JFrame) component;
            frame.getContentPane().setBackground(CREAM);
            styleChildren(frame.getContentPane());
            return;
        }

        if (component instanceof JDialog) {
            JDialog dialog = (JDialog) component;
            dialog.getContentPane().setBackground(CREAM);
            styleChildren(dialog.getContentPane());
            return;
        }

        if (component instanceof JPanel) {
            JPanel currentPanel = (JPanel) component;
            if (currentPanel == pMenu) {
                currentPanel.setBackground(WOOD);
                currentPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, BEIGE));
            } else if (currentPanel == pTop) {
                currentPanel.setBackground(BEIGE);
                currentPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, WOOD));
            } else if (currentPanel == pBottom) {
                currentPanel.setBackground(BEIGE);
                currentPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, GRID));
            } else if (currentPanel == pMain || currentPanel == pMainPanel || currentPanel == panel) {
                currentPanel.setBackground(CREAM);
                currentPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
            } else if (currentPanel == searchPanel) {
                currentPanel.setBackground(CREAM);
            } else {
                currentPanel.setBackground(CREAM);
            }
            currentPanel.setOpaque(true);
            styleChildren(currentPanel);
            return;
        }

        if (component instanceof JScrollPane) {
            JScrollPane scrollPane = (JScrollPane) component;
            scrollPane.setBackground(CREAM);
            scrollPane.getViewport().setBackground(CREAM);
            styleChildren(scrollPane);
            return;
        }

        if (component instanceof JTable) {
            styleWarmTable((JTable) component);
            return;
        }

        if (component instanceof JComboBox) {
            JComboBox<?> combo = (JComboBox<?>) component;
            combo.setBackground(CREAM);
            combo.setForeground(ESPRESSO);
            combo.setFont(new Font("SansSerif", Font.PLAIN, 13));
            combo.setOpaque(true);
            return;
        }

        if (component instanceof JTextField || component instanceof JPasswordField
                || component instanceof JTextArea) {
            component.setBackground(Color.WHITE);
            component.setForeground(ESPRESSO);
            component.setFont(new Font("SansSerif", Font.PLAIN, 13));
            if (component instanceof JTextField) {
                ((JTextField) component).setCaretColor(TERRACOTTA);
                ((JTextField) component).setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(GRID),
                        BorderFactory.createEmptyBorder(5, 8, 5, 8)));
            }
            return;
        }

        if (component instanceof JButton) {
            styleWarmButton((JButton) component);
            return;
        }

        if (component instanceof JLabel) {
            JLabel label = (JLabel) component;
            label.setForeground(ESPRESSO);
            if (label == lTop) {
                label.setForeground(ESPRESSO);
                label.setFont(new Font("Serif", Font.BOLD, 20));
            } else if (label == lMenu) {
                label.setForeground(CREAM);
                label.setFont(new Font("Serif", Font.BOLD, 16));
            } else {
                label.setFont(new Font("SansSerif", Font.PLAIN, 13));
            }
        }
    }

    private void styleChildren(Container container) {
        // Also style components added later by the existing page/navigation code.
        if (container instanceof JComponent) {
            JComponent swingContainer = (JComponent) container;
            if (swingContainer.getClientProperty("warmLibraryThemeListener") == null) {
                swingContainer.putClientProperty("warmLibraryThemeListener", Boolean.TRUE);
                swingContainer.addContainerListener(new ContainerAdapter() {
                    @Override
                    public void componentAdded(ContainerEvent event) {
                        SwingUtilities.invokeLater(() -> applyWarmLibraryStyle(event.getChild()));
                    }
                });
            }
        }
        for (Component child : container.getComponents()) {
            applyWarmLibraryStyle(child);
        }
    }

    private void styleWarmButton(JButton button) {
        String buttonText = button.getText() == null ? "" : button.getText().trim().toLowerCase();
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        if (button == b1 || button == b2 || button == b3 || button == b4 || button == b5) {
            button.setBackground(WOOD);
            button.setForeground(CREAM);
            button.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        } else if (buttonText.contains("delete")) {
            button.setBackground(new Color(173, 58, 48));
            button.setForeground(Color.WHITE);
        } else if (buttonText.contains("cancel") || buttonText.contains("close")) {
            button.setBackground(BEIGE);
            button.setForeground(ESPRESSO);
            button.setBorder(BorderFactory.createLineBorder(GRID));
            button.setBorderPainted(true);
        } else if (buttonText.contains("edit") || buttonText.contains("rename")) {
            button.setBackground(Color.GREEN);
            button.setForeground(ESPRESSO);

        } else {
            button.setBackground(TERRACOTTA);
            button.setForeground(Color.WHITE);
        }
    }

    private void styleWarmTable(JTable table) {
        table.setBackground(Color.WHITE);
        table.setForeground(ESPRESSO);
        table.setSelectionBackground(new Color(230, 198, 170));
        table.setSelectionForeground(ESPRESSO);
        table.setGridColor(GRID);
        table.setRowHeight(Math.max(table.getRowHeight(), 25));
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));

        javax.swing.table.JTableHeader header = table.getTableHeader();
        if (header != null) {
            header.setBackground(WOOD);
            header.setForeground(CREAM);
            header.setFont(new Font("SansSerif", Font.BOLD, 12));
            header.setOpaque(true);
        }

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable source, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(
                        source, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    cell.setBackground(new Color(230, 198, 170));
                    cell.setForeground(ESPRESSO);
                } else {
                    cell.setBackground(row % 2 == 0 ? Color.WHITE : TABLE_ALT);
                    cell.setForeground(ESPRESSO);
                }
                setHorizontalAlignment(SwingConstants.CENTER);
                setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
                return cell;
            }
        };
        for (int column = 0; column < table.getColumnCount(); column++) {
            table.getColumnModel().getColumn(column).setCellRenderer(renderer);
        }
    }
    // ===== End warm theme additions =====

    public Main() {




        SwingUtilities.invokeLater(() -> b1.doClick());// starting the program by showing Book Lists


        lMenu.setVerticalAlignment(SwingConstants.TOP);
        lMenu.setFont(new Font("Roboto", Font.BOLD, 15));

        pTop.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        pBottom.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        pMenu.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        pMain.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        pTop.add(lTop);

        pMenu.add(lMenu, BorderLayout.NORTH);
        lMenu.setVerticalAlignment(SwingConstants.CENTER);
        lMenu.setHorizontalAlignment(SwingConstants.CENTER);

        pMenu.add(b1);
        pMenu.add(b2);
        pMenu.add(b3);
        pMenu.add(b4);
        pMenu.add(b5);
        //pMenu.add(b6);

        pMainPanel.add(bBookEntry);
        pMainPanel.add(bMemberEntry);
        pMainPanel.add(bBorrowEntry);
        pMainPanel.add(bReturnEntry);
        pMainPanel.add(bLibrarianEntry);



        bBookEntry.setVisible(false);
        bMemberEntry.setVisible(false);
        bBorrowEntry.setVisible(false);
        bReturnEntry.setVisible(false);
        bLibrarianEntry.setVisible(false);


        pBottom.add(login,BorderLayout.WEST);
        pBottom.setPreferredSize(new Dimension(500,50));

        JLabel lDate = new JLabel("Date: "+formattedDate);

        pBottom.add(lDate,BorderLayout.CENTER);
        pBottom.add(lDate,BorderLayout.EAST);

        b1.addActionListener(new ShowBookList());
        b2.addActionListener(new ShowMembers());
        b3.addActionListener(new ShowBorrow());
        b4.addActionListener(new ShowReturn());
        b5.addActionListener(new ShowLibrarians());


        add(pTop, BorderLayout.NORTH);
        add(pBottom, BorderLayout.SOUTH);
        add(pMenu, BorderLayout.WEST);


        panel.add(pMain,BorderLayout.CENTER);
        panel.add(pMainPanel, BorderLayout.SOUTH);
        pMainPanel.setPreferredSize(new Dimension(500,40));
        add(panel, BorderLayout.CENTER);

        // Apply the warm wood-and-cream theme after existing components are assembled.
        installWarmLibraryTheme();

    }
    void removeEditFrame(JFrame frame){
        frame.dispose();
    }

    public void barUIsetting(JScrollBar bar){
        bar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbColor = new Color(191, 159, 126);       // Terracotta
                thumbDarkShadowColor = new Color(191, 159, 126);
                thumbHighlightColor = new Color(191, 159, 126);
                thumbLightShadowColor = new Color(191, 159, 126);
                trackColor = new Color(245, 235, 221);      // Cream
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }

        });
        bar.setBackground(new Color(191, 159, 126));
        bar.setOpaque(true);
    }







    public class ThemedDatePicker {

        // Library Management theme
        private final Color cream = new Color(245, 235, 224);
        private final Color brown = new Color(139, 111, 71);
        private final Color selectedBrown = new Color(166, 124, 82);
        private final Color darkText = new Color(73, 58, 45);
        private final Color border = new Color(214, 194, 168);

        private final DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd");

        private final DatePicker datePicker;
        private final JFormattedTextField editor;

        public ThemedDatePicker(JTextField dateField) {

            editor = new JFormattedTextField();
            datePicker = new DatePicker();

            datePicker.setEditor(editor);
            datePicker.setDateFormat("yyyy-MM-dd");
            datePicker.setColor(brown);
            datePicker.setStartWeekOnMonday(true);
            datePicker.setAnimationEnabled(true);
            datePicker.setCloseAfterSelected(true);
            datePicker.setSelectionArc(8);
            datePicker.setSelectedDate(LocalDate.now());

            // Text field theme
            dateField.setBackground(cream);
            dateField.setForeground(darkText);
            dateField.setCaretColor(brown);
            dateField.setFont(new Font("SansSerif", Font.PLAIN, 13));

            dateField.setBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(border),
                            BorderFactory.createEmptyBorder(6, 9, 6, 9)
                    )
            );

            // Calendar editor theme
            editor.setBackground(cream);
            editor.setForeground(darkText);
            editor.setCaretColor(brown);
            editor.setFont(new Font("SansSerif", Font.PLAIN, 13));

            editor.setBorder(
                    BorderFactory.createLineBorder(border)
            );

            // Date selection
            datePicker.addDateSelectionListener(
                    new DateSelectionListener() {
                        @Override
                        public void dateSelected(DateSelectionEvent event) {

                            LocalDate selectedDate =
                                    datePicker.getSelectedDate();

                            if (selectedDate != null) {
                                dateField.setText(
                                        selectedDate.format(formatter)
                                );
                            }
                        }
                    }
            );

            // Open calendar
            dateField.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (dateField.isShowing()) {
                        datePicker.showPopup(dateField);
                    }
                }
            });
        }
    }








    public static class LoginFrame extends JFrame {

        private JTextField usernameField;
        private JPasswordField passwordField;
        private JButton loginButton;

        // Theme colors
        private final Color background = new Color(245, 235, 221);
        private final Color panelColor = new Color(255, 250, 240);
        private final Color brown = new Color(145, 105, 70);
        private final Color textColor = new Color(65, 55, 45);
        private final Color borderColor = new Color(210, 195, 175);

        public LoginFrame() {

            setTitle("Library Management System - Login");
            setSize(450, 320);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            // Main panel
            JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
            mainPanel.setBackground(background);
            mainPanel.setBorder(new EmptyBorder(25, 30, 25, 30));

            // Title
            JLabel titleLabel = new JLabel("Library Management System");
            titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
            titleLabel.setForeground(textColor);
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

            mainPanel.add(titleLabel, BorderLayout.NORTH);

            // Form panel
            JPanel formPanel = new JPanel(new GridLayout(2, 2, 12, 15));
            formPanel.setBackground(panelColor);
            formPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(borderColor),
                    new EmptyBorder(20, 15, 20, 15)
            ));

            JLabel usernameLabel = new JLabel("Username:");
            JLabel passwordLabel = new JLabel("Password:");

            usernameLabel.setForeground(textColor);
            passwordLabel.setForeground(textColor);

            usernameField = new JTextField();
            passwordField = new JPasswordField();

            // Text field sizes
            Dimension fieldSize = new Dimension(220, 38);

            usernameField.setPreferredSize(fieldSize);
            passwordField.setPreferredSize(fieldSize);

            usernameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
            passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));

            usernameField.setBorder(
                    BorderFactory.createLineBorder(borderColor)
            );

            passwordField.setBorder(
                    BorderFactory.createLineBorder(borderColor)
            );

            formPanel.add(usernameLabel);
            formPanel.add(usernameField);
            formPanel.add(passwordLabel);
            formPanel.add(passwordField);

            mainPanel.add(formPanel, BorderLayout.CENTER);

            // Button panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.setBackground(background);

            loginButton = new JButton("Login");

            // Button size
            loginButton.setPreferredSize(new Dimension(160, 42));

            loginButton.setBackground(brown);
            loginButton.setForeground(Color.WHITE);
            loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
            loginButton.setFocusPainted(false);
            loginButton.setBorderPainted(false);
            loginButton.setOpaque(true);
            loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

            buttonPanel.add(loginButton);

            mainPanel.add(buttonPanel, BorderLayout.SOUTH);

            add(mainPanel);

            // Login event
            loginButton.addActionListener(e -> login());

            setVisible(true);

            passwordField.addActionListener(se -> {
                loginButton.doClick();
            });

            passwordField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        loginButton.doClick();
                    }
                }
            });
        }

        private void login() {

            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            String sql = "SELECT * FROM users WHERE userName = ? AND password = ?";

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, username);
                pstmt.setString(2, password);

                ResultSet resultSet = pstmt.executeQuery();

                if (resultSet.next()) {
                    Librarian = username;
                    JOptionPane pane = new JOptionPane(
                            "Login Successful!",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    JDialog dialog = pane.createDialog(this, "Success");

                    Timer timer = new Timer(600, e -> dialog.dispose());
                    timer.setRepeats(false);
                    timer.start();

                    dialog.setVisible(true);
                    dispose();


                    Main frame = new Main();
                    frame.setTitle("Library Management System");
                    frame.setSize(850, 700);
                    frame.setResizable(false);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setLocationRelativeTo(null);
                    frame.setIconImage(imageIcon.getImage());
                    frame.setVisible(true);

                } else {

                    passwordField.setText("");

                    JOptionPane.showMessageDialog(this,
                            "Invalid username or password!");


                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
//        LoginFrame frame = new LoginFrame();
        Main frame = new Main();
        frame.setTitle("Library Management System");
        frame.setSize(850, 700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
//hello