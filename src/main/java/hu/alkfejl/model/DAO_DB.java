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
//region db_sql
    private static final String CREATE_DB =
            "BEGIN TRANSACTION;\n" +
                    "CREATE TABLE IF NOT EXISTS \"Kerdes\" (\n" +
                    "\t\"id\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                    "\t\"kerdoivID\"\tINTEGER NOT NULL,\n" +
                    "\t\"szoveg\"\tTEXT NOT NULL,\n" +
                    "\t\"sorszam\"\tINTEGER,\n" +
                    "\t\"kep\"\tTEXT,\n" +
                    "\t\"tipus\"\tTEXT NOT NULL DEFAULT 1\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS \"Person\" (\n" +
                    "\t\"id\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                    "\t\"nev\"\tTEXT\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS \"Kitoltes\" (\n" +
                    "\t\"id\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                    "\t\"kerdoivID\"\tINTEGER NOT NULL,\n" +
                    "\t\"ip\"\tTEXT,\n" +
                    "\t\"valaszok\"\tBLOB NOT NULL\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS \"Valasz\" (\n" +
                    "\t\"id\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                    "\t\"szoveg\"\tTEXT NOT NULL,\n" +
                    "\t\"kerdesID\"\tINTEGER NOT NULL,\n" +
                    "\t\"sorszam\"\tINTEGER NOT NULL\n" +
                    ");\n" +
                    "CREATE TABLE IF NOT EXISTS \"Kerdoiv\" (\n" +
                    "\t\"id\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                    "\t\"nev\"\tTEXT\n" +
                    ");\n" +
                    "COMMIT;";
//endregion

    public DAO_DB(){
        initTable();
    }

    private void initTable(){
        try(Connection conn = DriverManager.getConnection(DB_FILE);
            Statement st = conn.createStatement();){
            st.executeUpdate(CREATE_DB);
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
            ps.setString(2, k.getSzoveg());
            ps.setInt(3, k.getTipus());
            ps.setString(4, k.getKep());
            if(k.getSorszam()==Integer.MIN_VALUE){
                ps.setNull(5, Types.INTEGER);
            }else{
                ps.setInt(5, k.getSorszam());
            }
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
            ResultSet rs = st.executeQuery("SELECT Kerdes.id, Kerdes.szoveg, Kerdes.kerdoivID, Kerdes.tipus, Kerdes.sorszam, Kerdes.kep, \n" +
                    "COUNT(Valasz.id) FROM Kerdes LEFT JOIN Valasz ON Kerdes.id=Valasz.kerdesID \n" +
                    "WHERE Kerdes.kerdoivID="+kerdoivID+" GROUP BY Kerdes.id ORDER BY Kerdes.sorszam;");
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
