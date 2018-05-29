@FXML
    public void przyciskTemp(ActionEvent event) {
        linie.clear();
        listaTemp.setItems(linie);
        tloTemp1.setVisible(true);
        tloTemp2.setVisible(true);
        tlowstaw.setVisible(false);
        tloCis1.setVisible(false);
        tloCis2.setVisible(false);
        tloTet1.setVisible(false);
        tloTet2.setVisible(false);
        Connection polaczenie = polacz();
        Statement kw;
        try {
            kw = polaczenie.createStatement();
            String sql = "SELECT * FROM pomiary WHERE data>date_add(curdate(),interval -10 day); ";
            ResultSet wynik = kw.executeQuery(sql);
            wynik.next();
            while(!wynik.isAfterLast())
            {
                String linia = "Data: "+wynik.getString("data")+"  pomiar: "+wynik.getString("Temperatura");
                linie.add(linia);
                wynik.next();
            }
            listaTemp.setItems(linie);
            
            
            tloTemp1.getChildren().clear();
            CategoryAxis x = new CategoryAxis();
            NumberAxis y = new NumberAxis();
            wykresTemp = new LineChart(x,y);
            XYChart.Series seria = new XYChart.Series();
            seria.setName("Temperatury");
            sql = "SELECT * FROM pomiary WHERE data>date_add(curdate(),interval -30 day); ";
            ResultSet wynik1 = kw.executeQuery(sql);
            wynik1.next();
            List <Double> lista1Temp = new ArrayList();
            List <String> lista2Temp = new ArrayList();
            while(!wynik1.isAfterLast())
            {
                lista1Temp.add(wynik1.getDouble("Temperatura"));
                lista2Temp.add(wynik1.getString("Data"));
                wynik1.next();
            }
            for(int i=0;i<lista1Temp.size();i++)
            {
                seria.getData().add(new XYChart.Data<>(lista2Temp.get(i),lista1Temp.get(i)));
            }
            wykresTemp.getData().add(seria);
            tloTemp1.getChildren().add(wykresTemp);
            wykresTemp.setPrefSize(401, 179);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        rozlacz(polaczenie);
    }