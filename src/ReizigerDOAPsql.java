import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDOAPsql implements ReizigerDAO{
    private Connection conn;

    public ReizigerDOAPsql(Connection conn){
        this.conn = conn;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try{
            Statement st = conn.createStatement();
            String query = "INSERT INTO public.reiziger(reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, reiziger.getID());
            pst.setString(2, reiziger.getVoorletters());
            pst.setString(3, reiziger.getTussenvoegsel());
            pst.setString(4, reiziger.getAchternaam());
            pst.setDate(5, reiziger.getGeboortedatum());
            pst.execute();
            st.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try{
            Statement st = conn.createStatement();
            String query = "UPDATE public.reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?;";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(5, reiziger.getID());
            pst.setString(1, reiziger.getVoorletters());
            pst.setString(2, reiziger.getTussenvoegsel());
            pst.setString(3, reiziger.getAchternaam());
            pst.setDate(4, reiziger.getGeboortedatum());
            pst.execute();
            st.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try{
            Statement st = conn.createStatement();
            String query = "DELETE FROM public.reiziger WHERE reiziger_id = ? AND voorletters = ? AND tussenvoegsel = ? AND achternaam = ? AND geboortedatum = ?;";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, reiziger.getID());
            pst.setString(2, reiziger.getVoorletters());
            pst.setString(3, reiziger.getTussenvoegsel());
            pst.setString(4, reiziger.getAchternaam());
            pst.setDate(5, reiziger.getGeboortedatum());
            pst.execute();
            st.close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public Reiziger findById(int id) {
        Reiziger output = null;
        try{Statement st = conn.createStatement();
            String query = "Select * From reiziger WHERE reiziger.reiziger_id = ? ;";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                output = new Reiziger(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5));
            }
            rs.close();
            st.close();
            return output;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        List<Reiziger> output = new ArrayList<Reiziger>();
        try {
            Statement st = conn.createStatement();
            String query = "Select * From reiziger WHERE reiziger.geboortedatum = ? ;";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setDate(1, java.sql.Date.valueOf(datum));
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Reiziger toAdd = new Reiziger(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5));
                output.add(toAdd);
            }
            rs.close();
            st.close();
            return output;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reiziger> findAll(){
        List<Reiziger> output = new ArrayList<Reiziger>();
        try {
            Statement st = conn.createStatement();
            String query = "Select * From reiziger";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Reiziger toAdd = new Reiziger(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5));
                output.add(toAdd);
            }
            rs.close();
            st.close();
            return output;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
