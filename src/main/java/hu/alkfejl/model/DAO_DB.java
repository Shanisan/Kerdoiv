package hu.alkfejl.model;

import hu.alkfejl.model.bean.Kerdes;
import hu.alkfejl.model.bean.Kerdoiv;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO_DB implements DAO{
    private static final String DB_FILE = "jdbc:sqlite:kerdoiv.db";

    private static final String INSERT_KERDOIV =
            "INSERT INTO Kerdoiv(nev) VALUES (?);";
    private static final String INSERT_KERDES =
            "INSERT INTO Kerdes(kerdoivID, szoveg, tipus, kep, sorszam) VALUES (?,?,?,?,?);";

    private static final String CREATE_KERDOIV_TABLE =
            "CREATE TABLE IF NOT EXISTS Person(" +
                    "\"id\"INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                    "\"nev\"TEXT);";

    public DAO_DB(){
        initTable();
    }

    private void initTable(){
        try(Connection conn = DriverManager.getConnection(DB_FILE);
            Statement st = conn.createStatement();){
            st.executeUpdate(CREATE_KERDOIV_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addKerdoiv(Kerdoiv k) {
        try(Connection conn = DriverManager.getConnection(DB_FILE);
            PreparedStatement ps = conn.prepareStatement(INSERT_KERDOIV);){
            ps.setString(1, k.getNev());
            int res = ps.executeUpdate();
            if(res==1){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addKerdes(Kerdes k) {
        try(Connection conn = DriverManager.getConnection(DB_FILE);
            PreparedStatement ps = conn.prepareStatement(INSERT_KERDES);){
            ps.setInt(1, k.getKerdoivId());
            ps.setInt(2, k.getKerdoivId());
            ps.setInt(3, k.getKerdoivId());
            ps.setInt(4, k.getKerdoivId());
            ps.setInt(5, k.getKerdoivId());
            int res = ps.executeUpdate();
            if(res==1){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Kerdes> getKerdesek(int kerdoivID) {
        List<Kerdes> list = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(DB_FILE); Statement st = conn.createStatement();){
            ResultSet rs = st.executeQuery("SELECT Kerdes.id, Kerdes.szoveg, Kerdes.kerdoivID, Kerdes.tipus, Kerdes.sorszam, Kerdes.kep, COUNT(Valasz.id) FROM Kerdes LEFT JOIN Valasz ON Kerdes.id=Valasz.kerdesID WHERE Kerdes.kerdoivID="+kerdoivID+" GROUP BY Kerdes.id ORDER BY Kerdes.sorszam;");
            while(rs.next()){
                Kerdes k = new Kerdes(rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getInt(7)
                );
                list.add(k);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Kerdoiv> getKerdoiv() {
        List<Kerdoiv> list = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(DB_FILE); Statement st = conn.createStatement();){
            ResultSet rs = st.executeQuery("SELECT Kerdoiv.id, Kerdoiv.nev, COUNT(Kerdes.id), COUNT(Kitoltes.id) FROM Kerdoiv LEFT JOIN Kerdes ON Kerdoiv.id=Kerdes.kerdoivID LEFT JOIN Kitoltes ON Kitoltes.kerdoivID=Kerdes.id GROUP BY Kerdoiv.id;");
            while(rs.next()){
                Kerdoiv k = new Kerdoiv(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4)
                );
                list.add(k);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
