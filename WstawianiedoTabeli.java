@FXML
    public void dodajDaneTabela(ActionEvent event) {
        String temp = podajTemp.getText();
        String cis1 = podajCis1.getText();
        String cis2 = podajCis2.getText();
        String tet = podajTet.getText();
        String rok = podajRok.getText();
        String mies = podajMies.getText();
        String dzien = podajDzi.getText();
        try {
            float temperatura = Float.valueOf(temp);
            int year = Integer.valueOf(rok);
            int miesiac = Integer.valueOf(mies);
            int day = Integer.valueOf(dzien);
            int cisnienieroz = Integer.valueOf(cis1);
            int cisnienieskur = Integer.valueOf(cis2);
            int tetno = Integer.valueOf(tet);
            String data = rok + "-" + mies + "-" + dzien;
            Connection polaczenie = polacz();
            Statement kw = polaczenie.createStatement();
            String zapytanie = "INSERT INTO pomiary(data,temperatura,cisnienie1,cisnienie2,tetno) VALUES ('"+data+"',"+temp+","+cis1+","+cis2+","+tet+");";
            System.out.println(zapytanie);
            kw.execute(zapytanie);
            rozlacz(polaczenie);
            JOptionPane.showMessageDialog(null, "Dodano dane do bazy", "blad", 1);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "zly format danych", "blad", 0);