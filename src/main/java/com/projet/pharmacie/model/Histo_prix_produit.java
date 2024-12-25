package com.projet.pharmacie.model;
import java.sql.*;
import java.util.*;
import com.projet.pharmacie.db.MyConnect;
import com.projet.pharmacie.util.*;
public class Histo_prix_produit {
    private int id;
    private java.sql.Date date_;
    private double prix_vente_produit;
    private Produit produit;
    public Histo_prix_produit(){}
    public Histo_prix_produit(String date_,String prix_vente_produit,String produit) throws Exception{
        setDate_(date_); 
        setPrix_vente_produit(prix_vente_produit); 
        setProduit(produit); 
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

    public java.sql.Date getDate_() {
        return date_;
    }

    public void setDate_(java.sql.Date date_) throws Exception {
        Util.verifyObjectNotNull(date_, "date_");
        this.date_ = date_;
    }

    public void setDate_(String date_) throws Exception {
        java.sql.Date toSet =  Util.convertDateFromHtmlInput(date_);

        setDate_(toSet) ;
    }

    public double getPrix_vente_produit() {
        return prix_vente_produit;
    }

    public void setPrix_vente_produit(double prix_vente_produit) throws Exception {
        Util.verifyNumericPostive(prix_vente_produit, "prix_vente_produit");
        this.prix_vente_produit = prix_vente_produit;
    }

    public void setPrix_vente_produit(String prix_vente_produit) throws Exception {
        double toSet =  Util.convertDoubleFromHtmlInput(prix_vente_produit);

        setPrix_vente_produit(toSet) ;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) throws Exception {
        this.produit = produit;
    }

    public void setProduit(String produit) throws Exception {
         //define how this type should be conterted from String ... type : Produit
        Produit toSet = Produit.getById(Integer.parseInt(produit));

        setProduit(toSet) ;
    }

    public static Histo_prix_produit getById(int id) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        Histo_prix_produit instance = null;

        Connection con = MyConnect.getConnection();
        try {
            String query = "SELECT * FROM histo_prix_produit WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                instance = new Histo_prix_produit();
                instance.setId(rs.getInt("id"));
                instance.setDate_(rs.getDate("date_"));
                instance.setPrix_vente_produit(rs.getDouble("prix_vente_produit"));
                instance.setProduit(Produit.getById(rs.getInt("id_produit")));
            }
        } catch (Exception e) {
            throw e ;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !false) con.close();
        }

        return instance;
    }
    public static Histo_prix_produit[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Histo_prix_produit> items = new ArrayList<>();

        try {
            String query = "SELECT * FROM histo_prix_produit order by id asc ";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Histo_prix_produit item = new Histo_prix_produit();
                item.setId(rs.getInt("id"));
                item.setDate_(rs.getDate("date_"));
                item.setPrix_vente_produit(rs.getDouble("prix_vente_produit"));
                item.setProduit(Produit.getById(rs.getInt("id_produit")));
                items.add(item);
            }
        } catch (Exception e) {
            throw e ;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !false) con.close();
        }

        return items.toArray(new Histo_prix_produit[0]);
    }
    public int insert(Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = "INSERT INTO histo_prix_produit (date_, prix_vente_produit, id_produit) VALUES (?, ?, ?) RETURNING id";
            st = con.prepareStatement(query);
            st.setDate(1, this.date_);
            st.setDouble(2, this.prix_vente_produit);
            st.setInt(3, this.produit.getId());
            try {
                rs = st.executeQuery();
                if (rs.next()) {
                    int generatedId = rs.getInt("id");
                    this.setId(generatedId); 
                    con.commit();
                    return generatedId;
                } else {
                    con.rollback();
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
            String query = "UPDATE histo_prix_produit SET date_ = ?, prix_vente_produit = ?, id_produit = ? WHERE id = ?";
            st = con.prepareStatement(query);
            st.setDate(1, this.date_);
            st.setDouble(2, this.prix_vente_produit);
            st.setInt (3, this.produit.getId());
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
            String query = "DELETE FROM histo_prix_produit WHERE id = ?";
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