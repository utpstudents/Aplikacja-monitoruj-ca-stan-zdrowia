import com.sun.javafx.collections.ObservableListWrapper;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;


public class FXMLDocumentController implements Initializable {

    @FXML
    public Pane tlowstaw;
    public Pane tloTemp1;
    public Pane tloTemp2;
    public Pane tloCis1;
    public Pane tloCis2;
    public Pane tloTet1;
    public Pane tloTet2;
    public TextField podajTemp;
    public TextField podajCis1;
    public TextField podajCis2;
    public TextField podajTet;
    public TextField podajRok;
    public TextField podajMies;
    public TextField podajDzi;
    public ListView listaTemp = new ListView();
    ObservableList<String> linie = FXCollections.observableArrayList();
    public ListView listaCis = new ListView();
    ObservableList<String> linie1 = FXCollections.observableArrayList();
    public ListView listaTet = new ListView();
    ObservableList<String> linie2 = FXCollections.observableArrayList();
    public LineChart wykresTemp;
    public LineChart wykresTet;
    public BarChart wykresCis;

    @FXML
    public void przyciskWstaw(ActionEvent event) {
        tlowstaw.setVisible(true);
        tloTemp1.setVisible(false);
        tloTemp2.setVisible(false);
        tloCis1.setVisible(false);
        tloCis2.setVisible(false);
        tloTet1.setVisible(false);
        tloTet2.setVisible(false);
    }

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
            JOptionPane.showMessageDialog(null, "Dodano dane do bazy", "błąd", 1);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "zły format danych", "błąd", 0);

        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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

    public void rozlacz(Connection polaczenie) {
        try {
            polaczenie.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "bład bazy danych", "błąd", 0);

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert listaTemp != null : "fx:id=\"listView\" was not injected: check your FXML file 'CustomList.fxml'.";
    }

}
