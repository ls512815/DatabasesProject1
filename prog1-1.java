/****************************************************************/
/* GradeBook Application: grade1.java (Section 5.6)             */
/* Needs grade2.java to be compiled                             */
/* Chapter 5; Oracle Programming -- A Primer                    */
/*            by R. Sunderraman                                 */
/****************************************************************/

import java.sql.*; 
import java.io.*; 

class grade1 { 

  void print_menu() {
    System.out.println("      GRADEBOOK PROGRAM\n");
    System.out.println("(1) Select Members Table");
    System.out.println("(2) Add Member");
    System.out.println("(3) Delete Member");
    System.out.println("(4) Change Member Data");
    System.out.println("(q) Quit\n");
  }


  void select_Members(Connection conn) 
  throws SQLException, IOException {

  String query1 = "select *" +
                  "from Members ";
  String query;

  query = query1;
   
  Statement stmt = conn.createStatement (); 
  ResultSet rset = stmt.executeQuery(query);
  System.out.println("");
  while (rset.next ()) { 
    System.out.println(rset.getString(1) + "   " +
                       rset.getString(2) + "   " +
                       rset.getString(3));
  } 
  System.out.println("");
}

  void add_Member(Connection conn) 
    throws SQLException, IOException {
         
    Statement stmt = conn.createStatement(); 

    //to_date('04-05-1967','MM-DD-YYYY'),
    String Fname  = readEntry("First Name: ");
    String Lname = readEntry("Last Name: ");
    String Bdate = readEntry("Birthdate: ");
    String Sex = readEntry("Sex: ");
    String Mid = readEntry("Id #: ");
    String query = "insert into Members values (" +
            "'" + Fname + "','" + Lname + "'," + "to_date('" + Bdate + "','MM-DD-YYYY')" + ",'" + Sex + "','" + Mid + "')";


    try {
      int nrows = stmt.executeUpdate(query);
    } catch (SQLException e) {
        System.out.println("Error Adding Member");
        while (e != null) {
          System.out.println("Message     : " + e.getMessage());
          e = e.getNextException();
        }
        return;
      }
    stmt.close();
    System.out.println("Added Member");
  }

  void delete_member(Connection conn) 
        throws SQLException, IOException {

    String fname = readEntry("First name: ");
    String lname = readEntry("Last Name: ");


    String query = "delete from members where fname=" + "'" + fname + "'" + "and lname=" + "'" + lname + "'";
      
           
    Statement stmt = conn.createStatement (); 
    try {
      stmt.executeUpdate(query);
    } catch (SQLException e) {
      System.out.println("Error deleting member");
      return;
    }
    System.out.println("Member was deleted!");
    stmt.close();
  }


  void change_id(Connection conn) 
  throws SQLException, IOException {

  System.out.println("enter the first and last name of the id you want to change");
  String fname = readEntry("First name: ");
  String lname = readEntry("Last Name: ");
  String id = readEntry("new id: ");



String query = "update members set m_id=" + "'" + id + "'" + "where fname=" + "''" + fname + "''" + "and lname=" + "''" + lname + "'";

     
Statement stmt = conn.createStatement (); 
try {
stmt.executeUpdate(query);
} catch (SQLException e) {
System.out.println("Error updating member");
return;
}
System.out.println("Member id was updated!");
stmt.close();
}


  void add_students(Connection conn) 
      throws SQLException, IOException {

    String id, ln, fn, mi;
    PreparedStatement stmt = conn.prepareStatement(
      "insert into students values (?, ?, ?, ?)"  ); 
    do {
      id = readEntry("ID (0 to stop): ");
      if (id.equals("0"))
        break;
      ln = readEntry("Last  Name    : ");
      fn = readEntry("First Name    : ");
      mi = readEntry("Middle Initial: ");
      try {
        stmt.setString(1,id);
        stmt.setString(2,fn);
        stmt.setString(3,ln);
        stmt.setString(4,mi);
        stmt.executeUpdate();
      } catch (SQLException e) {
        System.out.println("Student was not added! Error!");
      }
    } while (true);  
    stmt.close();
  }


  //readEntry function -- to read input string
  static String readEntry(String prompt) {
     try {
       StringBuffer buffer = new StringBuffer();
       System.out.print(prompt);
       System.out.flush();
       int c = System.in.read();
       while(c != '\n' && c != -1) {
         buffer.append((char)c);
         c = System.in.read();
       }
       return buffer.toString().trim();
     } catch (IOException e) {
       return "";
       }
   }
} 