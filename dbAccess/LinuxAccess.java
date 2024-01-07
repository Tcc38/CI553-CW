package dbAccess;

/**
  * Implements management of an mySQL database on Linux.
  * @author  Mike Smith University of Brighton
  * @version 2.0
  */
class LinuxAccess extends DBAccess
{
  public void loadDriver() throws Exception
  {
    System.out.println("Setting up your database...");
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");

      System.out.println("Database drivers ready to go...");
    } catch (Exception e) {
      System.out.println("Failed to load driver: " + e.getMessage());
      throw e;
    }
  }

  public String urlOfDatabase()
  {
    System.out.println("Connecting you to a Linux-Based Database");
    String url = "jdbc:mysql://178.128.37.54:3306/tcc38_CI553";
    System.out.println("Brighton Domains Database URL: " + url);
    return url;
  }
}

