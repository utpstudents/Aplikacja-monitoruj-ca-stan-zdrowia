@FXML
    public void przyciskCis(ActionEvent event) {
        linie1.clear();
        listaCis.setItems(linie1);
        tloTemp1.setVisible(false);
        tloTemp2.setVisible(false);
        tlowstaw.setVisible(false);
        tloCis1.setVisible(true);
        tloCis2.setVisible(true);
        tloTet1.setVisible(false);
        tloTet2.setVisible(false);
        Connection polaczenie = polacz();
        Statement kw1;
        try {
            kw1 = polaczenie.createStatement();
            String sql = "SELECT * FROM pomiary WHERE data>date_add(curdate(),interval -10 day); ";
            ResultSet wynik = kw1.executeQuery(sql);
            wynik.next();
            while(!wynik.isAfterLast())
            {
                String linia = "Data: "+wynik.getString("data")+"  pomiar: "+wynik.getString("Cisnienie1")+" / "+wynik.getString("Cisnienie2");
                linie1.add(linia);
                wynik.next();
            }
            listaCis.setItems(linie1);
            
            tloCis1.getChildren().clear();
            CategoryAxis x = new CategoryAxis();
            NumberAxis y = new NumberAxis();
            wykresCis = new BarChart(x,y);
            XYChart.Series seria = new XYChart.Series();
            XYChart.Series seria1 =new XYChart.Series();
            seria.setName("Cisnieniaroz");
            seria1.setName("Cisnieniazku");
            sql = "SELECT * FROM pomiary WHERE data>date_add(curdate(),interval -30 day); ";
            ResultSet wynik1 = kw1.executeQuery(sql);
            wynik1.next();
            List <Integer> lista1Cis = new ArrayList();
            List <String> lista2Cis = new ArrayList();
            List <Integer> lista3Cis = new ArrayList();
            while(!wynik1.isAfterLast())
            {
                lista1Cis.add(wynik1.getInt("Cisnienie1"));
                lista2Cis.add(wynik1.getString("Data"));
                lista3Cis.add(wynik1.getInt("Cisnienie2"));
                wynik1.next();
            }
            for(int i=0;i<lista1Cis.size();i++)
            {
                seria.getData().add(new XYChart.Data<>(lista2Cis.get(i),lista1Cis.get(i)));
                seria1.getData().add(new XYChart.Data<>(lista2Cis.get(i),lista3Cis.get(i)));
            }
            wykresCis.getData().addAll(seria, seria1);
            tloCis1.getChildren().add(wykresCis);
            wykresCis.setPrefSize(401, 179);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        rozlacz(polaczenie);
        
    }