package hu.alkfejl.model;

import hu.alkfejl.App;
import hu.alkfejl.model.bean.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO_DB implements DAO{
    private static final String DB_FILE = "jdbc:sqlite:kerdoiv.db";

    private static final String INSERT_KERDOIV =
            "INSERT INTO Kerdoiv(nev, kezdesiIdo, befejezesiIdo, kitoltesiIdo, letrehozoID) VALUES (?, ?, ?, ?, ?);";
    private static final String INSERT_KERDES =
            "INSERT INTO Kerdes(kerdoivID, szoveg, tipus, kep, sorszam) VALUES (?,?,?,?,?);";
    private static final String SELECT_USER = "SELECT id FROM Adminok WHERE username=? AND password=?;";
    private static final String INSERT_ADMIN = "INSERT INTO Adminok(username, email, password) VALUES (?,?,?)";
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

    public static int searchUser(String username, String password) {
        List<Integer> userID = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(DB_FILE);
        PreparedStatement ps = conn.prepareStatement(SELECT_USER);){
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                userID.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(userID.size()==1){
            return userID.get(0);
        }else{
            return -1;
        }
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
            ps.setString(2, k.getKezdet().toString());
            ps.setString(3, k.getVege().toString());
            ps.setInt(4, k.getIdo());
            ps.setString(5, k.getLetrehozo());
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
    public List<Kerdes> getKerdesekList(int kerdoivID) {
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
    public List<Kerdoiv> getKerdoivList(int adminID) {
        List<Kerdoiv> list = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(DB_FILE); Statement st = conn.createStatement();){
            ResultSet rs = st.executeQuery("SELECT Kerdoiv.id, Kerdoiv.nev, COUNT(Kerdes.id), COUNT(Kitoltes.id)," +
                    " kezdesiIdo, befejezesiIdo, kitoltesiIdo, link, Adminok.username FROM Kerdoiv LEFT JOIN Kerdes ON Kerdoiv.id=Kerdes.kerdoivID " +
                    "LEFT JOIN Kitoltes ON Kitoltes.kerdoivID=Kerdes.id LEFT JOIN Adminok ON Adminok.id=Kerdoiv.letrehozoID " +
                    "WHERE Kerdoiv.letrehozoID="+adminID+" GROUP BY Kerdoiv.id;");
            while(rs.next()){
                Kerdoiv k = new Kerdoiv(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getTimestamp(5),
                        rs.getTimestamp(6),
                        rs.getInt(7),
                        rs.getString(8),
                        rs.getString(9)
                );
                list.add(k);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Kerdes getKerdes(int kerdesID) {
        Kerdes k = null;
        try(Connection conn = DriverManager.getConnection(DB_FILE); Statement st = conn.createStatement();){
            ResultSet rs = st.executeQuery("SELECT szoveg, sorszam, kep, tipus FROM Kerdes where id="+kerdesID);
            while(rs.next()){
                k=new Kerdes(rs.getString(1), KerdesTipus.tipusStringek[rs.getInt(4)], rs.getInt(2), rs.getString(3));
                k.setId(kerdesID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return k;
    }

    @Override
    public Kerdoiv getKerdoiv(int kerdoivID) {
        Kerdoiv k = null;
        try(Connection conn = DriverManager.getConnection(DB_FILE); Statement st = conn.createStatement();){
            ResultSet rs = st.executeQuery("SELECT nev, kezdesiIdo, befejezesiIdo, kitoltesiIdo FROM Kerdoiv where id="+kerdoivID);
            while(rs.next()){
                k=new Kerdoiv(rs.getString(1));
                k.setKezdet(rs.getTimestamp(2));
                k.setVege(rs.getTimestamp(3));
                k.setIdo(rs.getInt(4));
                k.setId(kerdoivID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return k;
    }

    @Override
    public boolean deleteEverything(Kerdoiv delKerdoiv, List<Kerdes> delKerdes, List<Valasz> delValasz) {
        try (Connection conn = DriverManager.getConnection(DB_FILE); Statement st = conn.createStatement();){
            conn.setAutoCommit(false);
            if(delKerdoiv!=null){
                st.execute("DELETE FROM Kerdoiv WHERE id="+delKerdoiv.getId());
            }
            for (Kerdes k:delKerdes) {
                st.execute("DELETE FROM Kerdes WHERE id="+k.getId());
            }
            for (Valasz v:delValasz) {
                st.execute("DELETE FROM Valasz WHERE id="+v.getId());
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean editKerdoiv(Kerdoiv k) {
        try (Connection conn = DriverManager.getConnection(DB_FILE); PreparedStatement st = conn.prepareStatement(
                "UPDATE Kerdoiv SET nev=?, kezdesiIdo=?, befejezesiIdo=?, kitoltesiIdo=? WHERE id=?;"
        );) {
            conn.setAutoCommit(false);
            st.setString(1, k.getNev());
            st.setTimestamp(2, k.getKezdet());
            st.setTimestamp(3, k.getVege());
            st.setInt(4, k.getIdo());
            st.setInt(5, k.getId());
            try{
                st.execute();
            }catch (SQLException sqlE){
                sqlE.printStackTrace();
                conn.rollback();
                return false;
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean editKerdes(Kerdes k, int kerdesID) {
        //System.out.println("DAO-ban: "+k.toString());
        try (Connection conn = DriverManager.getConnection(DB_FILE); PreparedStatement st = conn.prepareStatement(
                "UPDATE Kerdes SET szoveg=?, sorszam=?, tipus=?, kep=? WHERE id=?;"
        );) {
            conn.setAutoCommit(false);
            st.setString(1, k.getSzoveg());
            st.setInt(2, k.getSorszam());
            st.setInt(3, k.getTipus());
            st.setString(4, k.getKep());
            st.setInt(5, k.getId());
            try{
                st.execute();
            }catch (SQLException sqlE){
                sqlE.printStackTrace();
                conn.rollback();
                return false;
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Valasz> getValaszokList(int kerdesID) {
        List<Valasz> list = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(DB_FILE); Statement st = conn.createStatement();){
            ResultSet rs = st.executeQuery("SELECT id, szoveg FROM Valasz WHERE kerdesID="+kerdesID+" ORDER BY sorszam");
            while(rs.next()){
                int id=+rs.getInt(1);
                String szoveg=rs.getString(2);
                Valasz v = new Valasz(id, szoveg);
                System.out.println(v.toString());
                list.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Valasz getValasz(int valaszID) {
        Valasz k = null;
        try(Connection conn = DriverManager.getConnection(DB_FILE); Statement st = conn.createStatement();){
            ResultSet rs = st.executeQuery("SELECT id, szoveg, kerdesID, sorszam FROM Valasz where id="+valaszID);
            while(rs.next()){
                k=new Valasz(rs.getInt(1), rs.getInt(3), rs.getString(2), rs.getInt(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return k;
    }

    @Override
    public boolean addValasz(Valasz v) {
        try (Connection conn = DriverManager.getConnection(DB_FILE); PreparedStatement st = conn.prepareStatement("INSERT INTO Valasz (szoveg, kerdesID, sorszam) VALUES (?, ?, ?)");){
            st.setString(1, v.getSzoveg());
            st.setInt(2, v.getKerdesID());
            st.setInt(3, v.getSorszam());
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean editValasz(Valasz v) {
        System.out.println(v.toString());
        try (Connection conn = DriverManager.getConnection(DB_FILE); PreparedStatement st = conn.prepareStatement("UPDATE Valasz SET szoveg=?, sorszam=? WHERE id=?");){
            st.setString(1, v.getSzoveg());
            st.setInt(3, v.getId());
            st.setInt(2, v.getSorszam());
            if(st.executeUpdate()==1){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getAdminList(){
        List<String> admins = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(DB_FILE); Statement st = conn.createStatement();){
            ResultSet rs = st.executeQuery("SELECT username FROM Adminok;");
            while(rs.next()){
                    admins.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }

    public boolean addAdmin(String username, String email, String password){
        try(Connection conn = DriverManager.getConnection(DB_FILE);
            PreparedStatement ps = conn.prepareStatement(INSERT_ADMIN);){
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, password);
            int res = ps.executeUpdate();
            if(res==1){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
