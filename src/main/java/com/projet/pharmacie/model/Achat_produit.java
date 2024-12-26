package com.projet.pharmacie.model;
import java.sql.*;
import java.util.*;
import com.projet.pharmacie.db.MyConnect;
import com.projet.pharmacie.util.*;
public class Achat_produit {
    private int id;
    private java.sql.Timestamp date_;
    private double qt_produit;
    private double denorm_prix_achat;
    private Fournisseur fournisseur;
    private Produit produit;
    public Achat_produit(){}
    public Achat_produit(String date_,String qt_produit,String denorm_prix_achat,String fournisseur,String produit) throws Exception{
        setDate_(date_); 
        setQt_produit(qt_produit); 
        setDenorm_prix_achat(denorm_prix_achat); 
        setFournisseur(fournisseur); 
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

    public double getDenorm_prix_achat() {
        return denorm_prix_achat;
    }

    public void setDenorm_prix_achat(double denorm_prix_achat) throws Exception {
        Util.verifyNumericPostive(denorm_prix_achat, "denorm_prix_achat");
        this.denorm_prix_achat = denorm_prix_achat;
    }

    public void setDenorm_prix_achat(String denorm_prix_achat) throws Exception {
        double toSet =  Util.convertDoubleFromHtmlInput(denorm_prix_achat);

        setDenorm_prix_achat(toSet) ;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) throws Exception {
        this.fournisseur = fournisseur;
    }

    public void setFournisseur(String fournisseur) throws Exception {
         //define how this type should be conterted from String ... type : Fournisseur
       Connection con = MyConnect.getConnection();        Fournisseur toSet = Fournisseur.getById(Integer.parseInt(fournisseur),con );
         con.close();
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
       Connection con = MyConnect.getConnection();        Produit toSet = Produit.getById(Integer.parseInt(produit),con );
         con.close();
        setProduit(toSet) ;
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
                instance.setDenorm_prix_achat(rs.getDouble("denorm_prix_achat"));
                instance.setFournisseur(Fournisseur.getById(rs.getInt("id_fournisseur") ,con ));
                instance.setProduit(Produit.getById(rs.getInt("id_produit") ,con ));
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
                item.setDenorm_prix_achat(rs.getDouble("denorm_prix_achat"));
                item.setFournisseur(Fournisseur.getById(rs.getInt("id_fournisseur")  ,con ));
                item.setProduit(Produit.getById(rs.getInt("id_produit")  ,con ));
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
            String query = "INSERT INTO achat_produit (date_, qt_produit, denorm_prix_achat, id_fournisseur, id_produit) VALUES (?, ?, ?, ?, ?) RETURNING id";
            st = con.prepareStatement(query);
            st.setTimestamp(1, this.date_);
            st.setDouble(2, this.qt_produit);
            st.setDouble(3, this.denorm_prix_achat);
            st.setInt(4, this.fournisseur.getId());
            st.setInt(5, this.produit.getId());
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
            String query = "UPDATE achat_produit SET date_ = ?, qt_produit = ?, denorm_prix_achat = ?, id_fournisseur = ?, id_produit = ? WHERE id = ?";
            st = con.prepareStatement(query);
            st.setTimestamp(1, this.date_);
            st.setDouble(2, this.qt_produit);
            st.setDouble(3, this.denorm_prix_achat);
            st.setInt (4, this.fournisseur.getId());
            st.setInt (5, this.produit.getId());
            st.setInt(6, this.getId());
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