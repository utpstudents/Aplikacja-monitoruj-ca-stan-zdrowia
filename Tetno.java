@FXML
    public void przyciskTet(ActionEvent event) {
        linie2.clear();
        listaTet.setItems(linie2);
        tloTemp1.setVisible(false);
        tloTemp2.setVisible(false);
        tlowstaw.setVisible(false);
        tloCis1.setVisible(false);
        tloCis2.setVisible(false);
        tloTet1.setVisible(true);
        tloTet2.setVisible(true);
        Connection polaczenie = polacz();
        Statement kw2;
        try {
            kw2 = polaczenie.createStatement();
            String sql = "SELECT * FROM pomiary WHERE data>date_add(curdate(),interval -10 day); ";
            ResultSet wynik = kw2.executeQuery(sql);
            wynik.next();
            while(!wynik.isAfterLast())
            {
                String linia = "Data: "+wynik.getString("data")+"  pomiar: "+wynik.getString("Tetno");
                linie2.add(linia);
                wynik.next();
            }
            listaTet.setItems(linie2);
            
            tloTet1.getChildren().clear();
            CategoryAxis x = new CategoryAxis();
            NumberAxis y = new NumberAxis();
            wykresTet = new LineChart(x,y);
            XYChart.Series seria = new XYChart.Series();
            seria.setName("Tetna");
            sql = "SELECT * FROM pomiary WHERE data>date_add(curdate(),interval -30 day); ";
            ResultSet wynik1 = kw2.executeQuery(sql);
            wynik1.next();
            List <Integer> lista1Tet = new ArrayList();
            List <String> lista2Tet = new ArrayList();
            while(!wynik1.isAfterLast())
            {
                lista1Tet.add(wynik1.getInt("Tetno"));
                lista2Tet.add(wynik1.getString("Data"));
                wynik1.next();
            }
            for(int i=0;i<lista1Tet.size();i++)
            {
                seria.getData().add(new XYChart.Data<>(lista2Tet.get(i),lista1Tet.get(i)));
            }
            wykresTet.getData().add(seria);
            tloTet1.getChildren().add(wykresTet);
            wykresTet.setPrefSize(401, 179);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        rozlacz(polaczenie);
        
    }