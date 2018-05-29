public Connection polacz() {
        String DBDRIVER = "com.mysql.jdbc.Driver";
        String DBURL = "jdbc:mysql://127.0.0.1:3306/baza";
        String DBUSER = "root";
        String DBPASS = "";

        Connection polaczenie = null;

        try {
            Class.forName(DBDRIVER).newInstance();
            polaczenie = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "bład bazy danych", "błąd", 0);
        }

        return polaczenie;
    }
