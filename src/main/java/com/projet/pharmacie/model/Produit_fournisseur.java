package com.projet.pharmacie.model;
import java.sql.*;
import java.util.*;
import com.projet.pharmacie.db.MyConnect;
import com.projet.pharmacie.util.*;
public class Produit_fournisseur {
    private int id;
    private Fournisseur fournisseur;
    private Produit produit;
    private java.sql.Date date_;
    private double prix;
    public Produit_fournisseur(){}
    public Produit_fournisseur(String fournisseur,String produit,String date_,String prix) throws Exception{
        setFournisseur(fournisseur); 
        setProduit(produit); 
        setDate_(date_); 
        setPrix(prix); 
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

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) throws Exception {
        this.fournisseur = fournisseur;
    }

    public void setFournisseur(String fournisseur) throws Exception {
         //define how this type should be conterted from String ... type : Fournisseur
        Fournisseur toSet = Fournisseur.getById(Integer.parseInt(fournisseur));

        setFournisseur(toSet) ;
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

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) throws Exception {
        Util.verifyNumericPostive(prix, "prix");
        this.prix = prix;
    }

    public void setPrix(String prix) throws Exception {
        double toSet =  Util.convertDoubleFromHtmlInput(prix);

        setPrix(toSet) ;
    }

    public static Produit_fournisseur getById(int id) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        Produit_fournisseur instance = null;

        Connection con = MyConnect.getConnection();
        try {
            String query = "SELECT * FROM produit_fournisseur WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                instance = new Produit_fournisseur();
                instance.setId(rs.getInt("id"));
                instance.setFournisseur(Fournisseur.getById(rs.getInt("id_fournisseur")));
                instance.setProduit(Produit.getById(rs.getInt("id_produit")));
                instance.setDate_(rs.getDate("date_"));
                instance.setPrix(rs.getDouble("prix"));
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
    public static Produit_fournisseur[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Produit_fournisseur> items = new ArrayList<>();

        try {
            String query = "SELECT * FROM produit_fournisseur order by id asc ";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Produit_fournisseur item = new Produit_fournisseur();
                item.setId(rs.getInt("id"));
                item.setFournisseur(Fournisseur.getById(rs.getInt("id_fournisseur")));
                item.setProduit(Produit.getById(rs.getInt("id_produit")));
                item.setDate_(rs.getDate("date_"));
                item.setPrix(rs.getDouble("prix"));
                items.add(item);
            }
        } catch (Exception e) {
            throw e ;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !false) con.close();
        }

        return items.toArray(new Produit_fournisseur[0]);
    }
    public int insert(Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = "INSERT INTO produit_fournisseur (id_fournisseur, id_produit, date_, prix) VALUES (?, ?, ?, ?) RETURNING id";
            st = con.prepareStatement(query);
            st.setInt(1, this.fournisseur.getId());
            st.setInt(2, this.produit.getId());
            st.setDate(3, this.date_);
            st.setDouble(4, this.prix);
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
            String query = "UPDATE produit_fournisseur SET id_fournisseur = ?, id_produit = ?, date_ = ?, prix = ? WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt (1, this.fournisseur.getId());
            st.setInt (2, this.produit.getId());
            st.setDate(3, this.date_);
            st.setDouble(4, this.prix);
            st.setInt(5, this.getId());
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
            String query = "DELETE FROM produit_fournisseur WHERE id = ?";
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