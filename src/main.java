import java.security.PrivateKey;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class main {

    private static Connection Connection;

    public static void main(String[] args){
        /*
        Opdracht P1
        String url = "jdbc:postgresql://localhost:5432/ovchip";
        try{
            Properties props = new Properties();
            props.setProperty("user","postgres");
            props.setProperty("password","Skool12");
            props.setProperty("ssl","false");
            Connection conn = DriverManager.getConnection(url, props);

            Statement st = conn.createStatement();
            String query = "Select * From reiziger";
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                System.out.println(rs.getString(2)+rs.getString(3)+rs.getString(4));
            }
            rs.close();
            st.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        */
        /*
        test code
        Connection = getConnection();
        ReizigerDOAPsql R_DAOPsql = new ReizigerDOAPsql(Connection);
        Reiziger me = new Reiziger(11, "W.T.", "van", "Kempen", new java.sql.Date(new java.util.Date().getTime()));
        System.out.println(R_DAOPsql.delete(me));
        closeConnection();
        */
        Connection = getConnection();
        ReizigerDOAPsql R_DAOPsql = new ReizigerDOAPsql(Connection);
        try {
            testReizigerDAO(R_DAOPsql);
        }catch (SQLException E){
            System.err.println(E.getMessage());
        }
        closeConnection();

    }

    public static java.sql.Connection getConnection() {
        String url = "jdbc:postgresql://localhost:5432/ovchip";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "Skool12");
        props.setProperty("ssl", "false");
        try {
            return DriverManager.getConnection(url, props);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static void closeConnection(){
        try{
            Connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Find by id
        System.out.println("[Test] Find by id: "+rdao.findById(sietske.getID()).toString());

        // Find by Geboortedatum
        System.out.println("[Test] Find by Geboortedatum: "+rdao.findByGbdatum(gbdatum));

        // Update reiziger
        System.out.println("[Test] update before: "+rdao.findById(sietske.getID()).toString());
        sietske.setTussenvoegsel("van");
        rdao.update(sietske);
        System.out.println("[Test] update after: "+rdao.findById(sietske.getID()).toString());

        // Delete reiziger
        reizigers = rdao.findAll();
        System.out.println("[Test] Delete before: "+reizigers.size());
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println("[Test] Delete after: "+reizigers.size());
    }

}
