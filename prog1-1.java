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

    String Fname  = readEntry("First Name: ");
    String Lname = readEntry("Last Name: ");
    String Bdate = readEntry("Birthdate ");
    String Sex = readEntry("Sex: ");
    String Mid = readEntry("Id #: ");
    String query = "insert into Members values (" +
            "'" + Fname + "','" + Lname + "','" + Bdate + "','" + Sex + "','" + Mid + "')";
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

  void add_course(Connection conn) 
        throws SQLException, IOException {

    String term_in = readEntry("Term         : ");
    String ls      = readEntry("Line Number  : ");
    String cnum    = readEntry("Course Number: ");
    String as      = readEntry("A Cutoff     : ");
    String bs      = readEntry("B Cutoff     : ");
    String cs      = readEntry("C Cutoff     : ");
    String ds      = readEntry("D Cutoff     : ");

    String query = "insert into courses values (" +
            "'" + term_in + "'," + ls + "," + 
            "'" + cnum + "'," + as + "," + 
            bs + "," + cs + "," + ds + ")";
           
    Statement stmt = conn.createStatement (); 
    try {
      stmt.executeUpdate(query);
    } catch (SQLException e) {
      System.out.println("Course was not added! Failure!");
      return;
    }
    System.out.println("Course was added! Success!");
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