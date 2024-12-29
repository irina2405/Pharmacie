package com.projet.pharmacie.model;
import java.sql.*;
import java.util.*;
import com.projet.pharmacie.db.MyConnect;
import com.projet.pharmacie.util.*;
public class Tresorerie {
    private int id;
    private java.sql.Timestamp date_;
    private double depot;
    private double retrait;
    public Tresorerie(){}
    public Tresorerie(String date_,String depot,String retrait) throws Exception{
        setDate_(date_); 
        setDepot(depot); 
        setRetrait(retrait); 
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

    public double getDepot() {
        return depot;
    }

    public void setDepot(double depot) throws Exception {
        Util.verifyNumericPostive(depot, "depot");
        this.depot = depot;
    }

    public void setDepot(String depot) throws Exception {
        double toSet =  Util.convertDoubleFromHtmlInput(depot);

        setDepot(toSet) ;
    }

    public double getRetrait() {
        return retrait;
    }

    public void setRetrait(double retrait) throws Exception {
        Util.verifyNumericPostive(retrait, "retrait");
        this.retrait = retrait;
    }

    public void setRetrait(String retrait) throws Exception {
        double toSet =  Util.convertDoubleFromHtmlInput(retrait);

        setRetrait(toSet) ;
    }

    public static Tresorerie getById(int id, Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        Tresorerie instance = null;

        try {
            String query = "SELECT * FROM tresorerie WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                instance = new Tresorerie();
                instance.setId(rs.getInt("id"));
                instance.setDate_(rs.getTimestamp("date_"));
                instance.setDepot(rs.getDouble("depot"));
                instance.setRetrait(rs.getDouble("retrait"));
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
    public static Tresorerie[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Tresorerie> items = new ArrayList<>();

        try {
            String query = "SELECT * FROM tresorerie order by id asc ";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Tresorerie item = new Tresorerie();
                item.setId(rs.getInt("id"));
                item.setDate_(rs.getTimestamp("date_"));
                item.setDepot(rs.getDouble("depot"));
                item.setRetrait(rs.getDouble("retrait"));
                items.add(item);
            }
        } catch (Exception e) {
            throw e ;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !false) con.close();
        }

        return items.toArray(new Tresorerie[0]);
    }
    public int insert(Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = "INSERT INTO tresorerie (date_, depot, retrait) VALUES (?, ?, ?) RETURNING id";
            st = con.prepareStatement(query);
            st.setTimestamp(1, this.date_);
            st.setDouble(2, this.depot);
            st.setDouble(3, this.retrait);
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
                // con.rollback();
                throw new Exception("Failed to insert record", e);
            }
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
        }
    }
    public void retirer(Connection con) throws Exception {
        if (getSolde(date_) < retrait) {
            throw new Exception("solde insuffisant") ;
        }
        this.insert(con);
    }
    public void update(Connection con) throws Exception {
        PreparedStatement st = null;
        try {
            String query = "UPDATE tresorerie SET date_ = ?, depot = ?, retrait = ? WHERE id = ?";
            st = con.prepareStatement(query);
            st.setTimestamp(1, this.date_);
            st.setDouble(2, this.depot);
            st.setDouble(3, this.retrait);
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
            String query = "DELETE FROM tresorerie WHERE id = ?";
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

    public static double getSolde (Timestamp d) throws Exception{
        double rep = 0.0;
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            String query = "select COALESCE(sum(depot) - sum(retrait),0) as solde from tresorerie  ";
            if (d != null) {
                query += "where date_ <= ?";
            }
            PreparedStatement st = con.prepareStatement(query);
            if (d!=null) {
                st.setTimestamp(1, d);
            }
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                rep = rs.getDouble(1);
            }
            if (st!=null) st.close();
            if (rs!=null) rs.close();
        } catch (Exception e) {
            throw e;
        }finally{
            if (con !=null) con.close(); 
        }
        return rep;
    }
}

// Commun'IT app