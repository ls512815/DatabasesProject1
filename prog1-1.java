/****************************************************************/
/* GradeBook Application: grade1.java (Section 5.6)             */
/* Needs grade2.java to be compiled                             */
/* Chapter 5; Oracle Programming -- A Primer                    */
/*            by R. Sunderraman                                 */
/****************************************************************/

import java.sql.*; 
import java.io.*; 

class members { 

  void print_menu() {
    System.out.println("      GRADEBOOK PROGRAM\n");
    System.out.println("(1) Select Members Table");
    System.out.println("(2) Add Member");
    System.out.println("(3) Delete Member");
    System.out.println("(4) Change Member Data");
    System.out.println("(5) Display Information About New Exhibits");
    System.out.println("(6) Display Report 2");
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
                       rset.getString(3).substring(0,10) + "   " +
                       rset.getString(4) + "   " +
                       rset.getString(5));
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
  String newlname = readEntry("New Last Name: ");

  String query = "update members set lname=" + "'" + newlname + "'" +  " where fname=" + "'" + fname + "'" + " and lname=" + "'" + lname + "'";

  Statement stmt = conn.createStatement (); 
  try {
  stmt.executeUpdate(query);
  } catch (SQLException e) {
  System.out.println("Error updating member");
  return;
  }
  System.out.println("Member bdate was updated!");
  stmt.close();
  }


  void report1(Connection conn) 
  throws SQLException, IOException {

  String query1 = "select distinct start_date "
                    + "from pieces "
                    + "where end_date is not null ";
  String query;
  query = query1;
   
  String query2 = "select artist_name " +
                  "from pieces " +
                  "where end_date is not null ";
               
                  
  String query3 = "select distinct gnum " +
                  "from pieces " +
                  " where end_date is not null ";

  String query4 = "select name " +
                  "from pieces " +
                  " where end_date is not null";

  String query5 = "select fname, lname " +
                  "from members m, pieces p " +
                  " where m.m_id=p.m_id and end_date is not null ";
  
  Statement stmt = conn.createStatement (); 

  ResultSet rset = stmt.executeQuery(query);
  System.out.println("_______________________________________________________________________________" + "\n");
  while (rset.next ()) { 
    System.out.println("           There are some new paintings arriving on " +
                        rset.getString(1).substring(0,10) + "! " + "\n" +
                        "        __________________________________________________________" + "\n" + "\n" +
                        "      There will be paintings from some of our best artists, including: ");
  } 
  ResultSet rset2 = stmt.executeQuery(query2);
  System.out.println("");
  while (rset2.next ()) { 
    System.out.println("                              - " + 
                        rset2.getString(1) + " ");
  } 

  ResultSet rset3 = stmt.executeQuery(query3);
  System.out.println("");
  while (rset3.next ()) { 
    System.out.println("      Come to gallery "+ 
                        rset3.getString(1) + ", and experience all the new wonderful pieces" + "\n");
  } 

  ResultSet rset4 = stmt.executeQuery(query4);
  System.out.println("              We are featuring the following pieces of art: ");
  System.out.print("                   ");
  while (rset4.next ()) { 
    System.out.print("  " + rset4.getString(1) + " ");
  } 
  System.out.println("\n");

  ResultSet rset5 = stmt.executeQuery(query5);
  System.out.println("\n" + "                       Special thanks to our donors, ");

  while (rset5.next ()) { 
    System.out.print("       " + rset5.getString(1) + " " + rset5.getString(2) + " ");
  } 
  System.out.println("\n");

  System.out.println("_______________________________________________________________________________" + "\n");
}






void report2(Connection conn) 
throws SQLException, IOException {

String query1 = "select fname, lname "  
                + "from members "
                + "where m_id= " 
                + "(select m_id " 
                + "from monatery_donations "
                + "where don_amount= "
                + "(select max(don_amount) "
                + "from monatery_donations))";
String query;

query = query1;

 

Statement stmt = conn.createStatement (); 
ResultSet rset = stmt.executeQuery(query);
System.out.println("_______________________________________________________________________________" + "\n");
System.out.println("We have so many generous members at our museum," + "\n");
System.out.println("but we wanted to recognise a few specific members " + "\n");

System.out.println("           Donated the most money to the museum");
System.out.println("           -------------------------------------");

while (rset.next ()) { 
  System.out.println("               " + rset.getString(1));
} 
System.out.println("");
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