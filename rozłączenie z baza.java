public void rozlacz(Connection polaczenie) {
        try {
            polaczenie.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "bład bazy danych", "błąd", 0);

        }

    }
