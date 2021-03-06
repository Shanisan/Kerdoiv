package hu.alkfejl.model;

import hu.alkfejl.App;
import hu.alkfejl.model.bean.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAO_DB implements DAO{

    public static final String APP_HOME = System.getProperty("user.home")+"/Kerdoiv-BSG/";
    public static final String DB_FILENAME = "data/kerdoiv.db";
    private static final String DB_FILE = "jdbc:sqlite:"+APP_HOME+DB_FILENAME;

    private static final String INSERT_KERDOIV =
            "INSERT INTO Kerdoiv(nev, kezdesiIdo, befejezesiIdo, kitoltesiIdo, letrehozoID) VALUES (?, ?, ?, ?, ?);";
    private static final String INSERT_KERDES =
            "INSERT INTO Kerdes(kerdoivID, szoveg, tipus, kep, sorszam) VALUES (?,?,?,?,?);";
    private static final String SELECT_USER = "SELECT id FROM Adminok WHERE username=? AND password=?;";
    private static final String INSERT_ADMIN = "INSERT INTO Adminok(username, email, password) VALUES (?,?,?)";
//region db_sql
    private static final String CREATE_DB =
            "BEGIN TRANSACTION;\n" +
                    "CREATE TABLE \"Adminok\" (\n" +
                    "\t\"id\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                    "\t\"username\"\tTEXT NOT NULL,\n" +
                    "\t\"email\"\tTEXT NOT NULL,\n" +
                    "\t\"password\"\tTEXT NOT NULL\n" +
                    ");"+
                    "CREATE TABLE \"Kerdes\" (\n" +
                    "\t\"id\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                    "\t\"kerdoivID\"\tINTEGER NOT NULL,\n" +
                    "\t\"szoveg\"\tTEXT NOT NULL,\n" +
                    "\t\"sorszam\"\tINTEGER,\n" +
                    "\t\"kep\"\tTEXT\n" +
                    ", tipus NOT NULL DEFAULT 1);"+
                    "CREATE TABLE \"Kerdoiv\" (\n" +
                    "\t\"id\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                    "\t\"nev\"\tTEXT UNIQUE,\n" +
                    "\t\"kezdesiIdo\"\tdatetime NOT NULL DEFAULT '2001-09-11 9:11:00.000',\n" +
                    "\t\"befejezesiIdo\"\tdatetime NOT NULL DEFAULT '2001-09-11 9:11:00.000',\n" +
                    "\t\"kitoltesiIdo\"\tNUMERIC NOT NULL DEFAULT 0,\n" +
                    "\t\"link\"\tTEXT NOT NULL DEFAULT '',\n" +
                    "\t\"letrehozoID\"\tNUMERIC NOT NULL DEFAULT 0\n" +
                    ");"+
                    "CREATE TABLE \"Kitoltes\" (\n" +
                    "\t\"id\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                    "\t\"kerdoivID\"\tINTEGER NOT NULL,\n" +
                    "\t\"ip\"\tTEXT,\n" +
                    "\t\"valaszok\"\tBLOB NOT NULL\n" +
                    ");"+
                    "CREATE TABLE \"Person\" (\n" +
                    "\t\"ip\"\tINTEGER NOT NULL UNIQUE,\n" +
                    "\t\"nev\"\tTEXT,\n" +
                    "\tPRIMARY KEY(\"ip\")\n" +
                    ");"+
                    "CREATE TABLE \"Valasz\" (\n" +
                    "\t\"id\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                    "\t\"szoveg\"\tTEXT NOT NULL,\n" +
                    "\t\"kerdesID\"\tINTEGER NOT NULL,\n" +
                    "\t\"sorszam\"\tINTEGER NOT NULL\n" +
                    ");"+
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
        System.out.println(DB_FILE);
        File db = null;
        try {
            db = new File(APP_HOME+DB_FILENAME);
            db.getParentFile().mkdirs();
            db.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(Connection conn = DriverManager.getConnection(DB_FILE);
            Statement st = conn.createStatement();){
            if(db.length()==0){
                st.executeUpdate(CREATE_DB);
            }
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
        try(Connection conn = DriverManager.getConnection(DB_FILE); Statement st = conn.createStatement(); Statement st2 = conn.createStatement(); Statement st3 = conn.createStatement()){
            ResultSet rs = st.executeQuery("SELECT Kerdoiv.id, Kerdoiv.nev, COUNT(Kerdes.id), COUNT(Kitoltes.id)," +
                    " kezdesiIdo, befejezesiIdo, kitoltesiIdo, link, Adminok.username FROM Kerdoiv LEFT JOIN Kerdes ON Kerdoiv.id=Kerdes.kerdoivID " +
                    "LEFT JOIN Kitoltes ON Kitoltes.kerdoivID=Kerdes.id LEFT JOIN Adminok ON Adminok.id=Kerdoiv.letrehozoID " +
                    "WHERE Kerdoiv.letrehozoID="+adminID+" GROUP BY Kerdoiv.id;");
            ResultSet rs2 = st2.executeQuery("select count(kitoltes.id) from kerdoiv left join Kitoltes on Kerdoiv.id=Kitoltes.kerdoivID group by kerdoiv.id");
            ResultSet rs3 = st3.executeQuery("select count(kerdes.id) from kerdoiv left join kerdes on Kerdoiv.id=kerdes.kerdoivID group by kerdoiv.id");
            while(rs.next() && rs2.next() && rs3.next()){
                Kerdoiv k = new Kerdoiv(
                        rs.getInt(1),
                        rs.getString(2),
                        rs3.getInt(1),
                        rs2.getInt(1),
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
            st.setString(2, k.getKezdet().toString());
            st.setString(3, k.getVege().toString());
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
            //System.out.println("Kep: "+k.getKep());
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

    public List<Kitoltes> getKitoltesek(int kerdoivID) {
        List<Kitoltes> list = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(DB_FILE); Statement st = conn.createStatement();){
            ResultSet rs = st.executeQuery("SELECT id, kitolto, valaszok, kitoltesIdeje FROM Kitoltes WHERE kerdoivID="+kerdoivID);
            System.out.println("Query vegrehajtva");
            while(rs.next()){
                Map<String, String> map = processKitoltes(rs.getString(3), kerdoivID);
                Kitoltes k = new Kitoltes(rs.getInt(1), kerdoivID, rs.getString(2), map, rs.getString(4));
                list.add(k);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Map<String, String> processKitoltes(String kitoltesek, int kerdoivID){
        String[] strs = kitoltesek.split("\\|");
        List<Kerdes> kerdesek = getKerdesekList(kerdoivID);
        Map<Integer, String> kerdMap = new HashMap<>();
        for (Kerdes k:kerdesek) {
            kerdMap.put(k.getId(), k.getSzoveg());
        }
        Map<String, String> map = new HashMap<>();
        for (String s:strs) {
            //System.out.println("Splitting :" + s);
            String[] s2 = s.split("~");
            String kerdesSzoveg = kerdMap.get(Integer.parseInt(s2[0]));
            map.put(kerdesSzoveg, s2[1]);
        }
        return map;
    }
}
