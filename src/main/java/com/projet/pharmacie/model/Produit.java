package com.projet.pharmacie.model;
import java.sql.*;
import java.util.*;
import com.projet.pharmacie.db.MyConnect;
import com.projet.pharmacie.util.*;
public class Produit {
    private int id;
    private String nom;
    private double denorm_prix_vente;
    private Unite unite;
    public Produit(){}
    public Produit(String nom,String denorm_prix_vente,String unite) throws Exception{
        setNom(nom); 
        setDenorm_prix_vente(denorm_prix_vente); 
        setUnite(unite); 
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) throws Exception {
        Util.verifyStringNotNullOrEmpty(nom, "nom");
        this.nom = nom;
    }

    public double getDenorm_prix_vente() {
        return denorm_prix_vente;
    }

    public void setDenorm_prix_vente(double denorm_prix_vente) throws Exception {
        Util.verifyNumericPostive(denorm_prix_vente, "denorm_prix_vente");
        this.denorm_prix_vente = denorm_prix_vente;
    }

    public void setDenorm_prix_vente(String denorm_prix_vente) throws Exception {
        double toSet =  Util.convertDoubleFromHtmlInput(denorm_prix_vente);

        setDenorm_prix_vente(toSet) ;
    }

    public Unite getUnite() {
        return unite;
    }

    public void setUnite(Unite unite) throws Exception {
        this.unite = unite;
    }

    public void setUnite(String unite) throws Exception {
         //define how this type should be conterted from String ... type : Unite
        Unite toSet = Unite.getById(Integer.parseInt(unite));

        setUnite(toSet) ;
    }

    public static Produit getById(int id) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        Produit instance = null;

        Connection con = MyConnect.getConnection();
        try {
            String query = "SELECT * FROM produit WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                instance = new Produit();
                instance.setId(rs.getInt("id"));
                instance.setNom(rs.getString("nom"));
                instance.setDenorm_prix_vente(rs.getDouble("denorm_prix_vente"));
                instance.setUnite(Unite.getById(rs.getInt("id_unite")));
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
    public static Produit[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Produit> items = new ArrayList<>();

        try {
            String query = "SELECT * FROM produit order by id asc ";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Produit item = new Produit();
                item.setId(rs.getInt("id"));
                item.setNom(rs.getString("nom"));
                item.setDenorm_prix_vente(rs.getDouble("denorm_prix_vente"));
                item.setUnite(Unite.getById(rs.getInt("id_unite")));
                items.add(item);
            }
        } catch (Exception e) {
            throw e ;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !false) con.close();
        }

        return items.toArray(new Produit[0]);
    }
    public int insert(Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = "INSERT INTO produit (nom, denorm_prix_vente, id_unite) VALUES (?, ?, ?) RETURNING id";
            st = con.prepareStatement(query);
            st.setString(1, this.nom);
            st.setDouble(2, this.denorm_prix_vente);
            st.setInt(3, this.unite.getId());
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
            String query = "UPDATE produit SET nom = ?, denorm_prix_vente = ?, id_unite = ? WHERE id = ?";
            st = con.prepareStatement(query);
            st.setString(1, this.nom);
            st.setDouble(2, this.denorm_prix_vente);
            st.setInt (3, this.unite.getId());
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
            String query = "DELETE FROM produit WHERE id = ?";
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