package com.projet.pharmacie.model;
import java.sql.*;
import java.util.*;
import com.projet.pharmacie.db.MyConnect;
import com.projet.pharmacie.util.*;
public class Achat_produit {
    private int id;
    private java.sql.Timestamp date_;
    private double qt_produit;
    private Produit_fournisseur produit_fournisseur;
    public Achat_produit(){}
    public Achat_produit(String date_,String qt_produit,String produit_fournisseur, Connection con) throws Exception{
        setDate_(date_); 
        setQt_produit(qt_produit); 
        setProduit_fournisseur(produit_fournisseur, con); 
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

    public double getQt_produit() {
        return qt_produit;
    }

    public void setQt_produit(double qt_produit) throws Exception {
        Util.verifyNumericPostive(qt_produit, "qt_produit");
        this.qt_produit = qt_produit;
    }

    public void setQt_produit(String qt_produit) throws Exception {
        double toSet =  Util.convertDoubleFromHtmlInput(qt_produit);

        setQt_produit(toSet) ;
    }

    public Produit_fournisseur getProduit_fournisseur() {
        return produit_fournisseur;
    }

    public void setProduit_fournisseur(Produit_fournisseur produit_fournisseur) throws Exception {
        this.produit_fournisseur = produit_fournisseur;
    }

    public void setProduit_fournisseur(String produit_fournisseur,Connection con) throws Exception {
         //define how this type should be conterted from String ... type : Produit_fournisseur
        Produit_fournisseur toSet = Produit_fournisseur.getById(Integer.parseInt(produit_fournisseur),con );
        setProduit_fournisseur(toSet) ;
    }

    public static Achat_produit getById(int id, Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        Achat_produit instance = null;

        try {
            String query = "SELECT * FROM achat_produit WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                instance = new Achat_produit();
                instance.setId(rs.getInt("id"));
                instance.setDate_(rs.getTimestamp("date_"));
                instance.setQt_produit(rs.getDouble("qt_produit"));
                instance.setProduit_fournisseur(Produit_fournisseur.getById(rs.getInt("id_produit_fournisseur") ,con ));
            }
        } catch (Exception e) {
            throw e ;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
        }

        return instance;
    }
    public static Achat_produit[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Achat_produit> items = new ArrayList<>();

        try {
            String query = "SELECT * FROM achat_produit order by id asc ";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Achat_produit item = new Achat_produit();
                item.setId(rs.getInt("id"));
                item.setDate_(rs.getTimestamp("date_"));
                item.setQt_produit(rs.getDouble("qt_produit"));
                item.setProduit_fournisseur(Produit_fournisseur.getById(rs.getInt("id_produit_fournisseur")  ,con ));
                items.add(item);
            }
        } catch (Exception e) {
            throw e ;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !false) con.close();
        }

        return items.toArray(new Achat_produit[0]);
    }
    public int insert(Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = "INSERT INTO achat_produit (date_, qt_produit, id_produit_fournisseur) VALUES (?, ?, ?) RETURNING id";
            st = con.prepareStatement(query);
            st.setTimestamp(1, this.date_);
            st.setDouble(2, this.qt_produit);
            st.setInt(3, this.produit_fournisseur.getId());
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
    public void update(Connection con) throws Exception {
        PreparedStatement st = null;
        try {
            String query = "UPDATE achat_produit SET date_ = ?, qt_produit = ?, id_produit_fournisseur = ? WHERE id = ?";
            st = con.prepareStatement(query);
            st.setTimestamp(1, this.date_);
            st.setDouble(2, this.qt_produit);
            st.setInt (3, this.produit_fournisseur.getId());
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
            String query = "DELETE FROM achat_produit WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            try {
                st.executeUpdate();
                Achat_produit me = Achat_produit.getById(id, con);
                Double depot = me.getQt_produit()* me.getProduit_fournisseur().getPrix();
                Tresorerie tresorerie = new Tresorerie("annulation achat produit " , (new Timestamp(System.currentTimeMillis())).toString(),depot.toString(), "0" );
                tresorerie.insertUncommitted(con);
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