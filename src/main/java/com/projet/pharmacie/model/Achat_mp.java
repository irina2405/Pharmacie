package com.projet.pharmacie.model;
import java.sql.*;
import java.util.*;
import com.projet.pharmacie.db.MyConnect;
import com.projet.pharmacie.util.*;
public class Achat_mp {
    private int id;
    private java.sql.Timestamp date_;
    private double qt_mp;
    private Fournisseur_mp fournisseur_mp;
    public Achat_mp(){}
    public Achat_mp(String date_,String qt_mp,String fournisseur_mp) throws Exception{
        setDate_(date_); 
        setQt_mp(qt_mp); 
        setFournisseur_mp(fournisseur_mp); 
    }
    public int getId() {
        return id;
    }

    public void setId(int id) throws Exception {
        Util.verifyNumericPostive(id, "id");
        this.id = id;
    }

    public void setId(String id) throws Exception {
        int toSet =  Util.convertIntFromHtmlInput(id);

        setId(toSet) ;
    }

    public java.sql.Timestamp getDate_() {
        return date_;
    }

    public void setDate_(java.sql.Timestamp date_) throws Exception {
        Util.verifyObjectNotNull(date_, "date_");
        this.date_ = date_;
    }

    public void setDate_(String date_) throws Exception {
        java.sql.Timestamp toSet =  Util.convertTimestampFromHtmlInput(date_);

        setDate_(toSet) ;
    }

    public double getQt_mp() {
        return qt_mp;
    }

    public void setQt_mp(double qt_mp) throws Exception {
        Util.verifyNumericPostive(qt_mp, "qt_mp");
        this.qt_mp = qt_mp;
    }

    public void setQt_mp(String qt_mp) throws Exception {
        double toSet =  Util.convertDoubleFromHtmlInput(qt_mp);

        setQt_mp(toSet) ;
    }

    public Fournisseur_mp getFournisseur_mp() {
        return fournisseur_mp;
    }

    public void setFournisseur_mp(Fournisseur_mp fournisseur_mp) throws Exception {
        this.fournisseur_mp = fournisseur_mp;
    }

    public void setFournisseur_mp(String fournisseur_mp) throws Exception {
         //define how this type should be conterted from String ... type : Fournisseur_mp
       Connection con = MyConnect.getConnection();        Fournisseur_mp toSet = Fournisseur_mp.getById(Integer.parseInt(fournisseur_mp),con );
         con.close();
        setFournisseur_mp(toSet) ;
    }

    public static Achat_mp getById(int id, Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        Achat_mp instance = null;

        try {
            String query = "SELECT * FROM achat_mp WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                instance = new Achat_mp();
                instance.setId(rs.getInt("id"));
                instance.setDate_(rs.getTimestamp("date_"));
                instance.setQt_mp(rs.getDouble("qt_mp"));
                instance.setFournisseur_mp(Fournisseur_mp.getById(rs.getInt("id_fournisseur_mp") ,con ));
            }
        } catch (Exception e) {
            throw e ;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !true) con.close();
        }

        return instance;
    }
    public static Achat_mp[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Achat_mp> items = new ArrayList<>();

        try {
            String query = "SELECT * FROM achat_mp order by id asc ";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Achat_mp item = new Achat_mp();
                item.setId(rs.getInt("id"));
                item.setDate_(rs.getTimestamp("date_"));
                item.setQt_mp(rs.getDouble("qt_mp"));
                item.setFournisseur_mp(Fournisseur_mp.getById(rs.getInt("id_fournisseur_mp")  ,con ));
                items.add(item);
            }
        } catch (Exception e) {
            throw e ;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !false) con.close();
        }

        return items.toArray(new Achat_mp[0]);
    }
    
    public int insert(Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = "INSERT INTO achat_mp (date_, qt_mp, id_fournisseur_mp) VALUES (?, ?, ?) RETURNING id";
            st = con.prepareStatement(query);
            st.setTimestamp(1, this.date_);
            st.setDouble(2, this.qt_mp);
            st.setInt(3, this.fournisseur_mp.getId());
            try {
                rs = st.executeQuery();
                if (rs.next()) {
                    int generatedId = rs.getInt("id");
                    this.setId(generatedId); 
                    // con.commit();
                    return generatedId;
                } else {
                    // con.rollback();
                    throw new Exception("Failed to retrieve generated ID");
                }
            } catch (Exception e) {
                con.rollback();
                throw new Exception("Failed to insert record", e);
            }
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
        }
    }
    public void update(Connection con) throws Exception {
        PreparedStatement st = null;
        try {
            String query = "UPDATE achat_mp SET date_ = ?, qt_mp = ?, id_fournisseur_mp = ? WHERE id = ?";
            st = con.prepareStatement(query);
            st.setTimestamp(1, this.date_);
            st.setDouble(2, this.qt_mp);
            st.setInt (3, this.fournisseur_mp.getId());
            st.setInt(4, this.getId());
            try {
                st.executeUpdate();
                con.commit();
            } catch (Exception e) {
                con.rollback();
                throw new Exception("Failed to update record", e);
            }
        } finally {
            if (st != null) st.close();
        }
    }
    public static void deleteById(int id) throws Exception {
            Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        try {
            String query = "DELETE FROM achat_mp WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            try {
                st.executeUpdate();
                con.commit();
            } catch (Exception e) {
                con.rollback();
                throw new Exception("Failed to delete record", e);
            }
        } finally {
            if (st != null) st.close();
           if (con != null) con.close(); 
        }
    }
}

// Commun'IT app