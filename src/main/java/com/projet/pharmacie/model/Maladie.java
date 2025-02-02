package com.projet.pharmacie.model;
import java.sql.*;
import java.util.*;
import com.projet.pharmacie.db.MyConnect;
import com.projet.pharmacie.util.*;
public class Maladie {
    private int id;
    private String nom;
    public Maladie(){}
    public Maladie(String nom) throws Exception{
        setNom(nom); 
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

    public static Maladie getById(int id, Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        Maladie instance = null;

        try {
            String query = "SELECT * FROM maladie WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                instance = new Maladie();
                instance.setId(rs.getInt("id"));
                instance.setNom(rs.getString("nom"));
            }
        } catch (Exception e) {
            throw e ;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
        }

        return instance;
    }
    public static Maladie[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Maladie> items = new ArrayList<>();

        try {
            String query = "SELECT * FROM maladie order by id asc ";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Maladie item = new Maladie();
                item.setId(rs.getInt("id"));
                item.setNom(rs.getString("nom"));
                items.add(item);
            }
        } catch (Exception e) {
            throw e ;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !false) con.close();
        }

        return items.toArray(new Maladie[0]);
    }
    public int insert(Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = "INSERT INTO maladie (nom) VALUES (?) RETURNING id";
            st = con.prepareStatement(query);
            st.setString(1, this.nom);
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
            String query = "UPDATE maladie SET nom = ? WHERE id = ?";
            st = con.prepareStatement(query);
            st.setString(1, this.nom);
            st.setInt(2, this.getId());
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
            String query = "DELETE FROM maladie WHERE id = ?";
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

    public Produit[] getProduitsConcernes() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Produit> items = new ArrayList<>();

        try {
            String query = "select produit.* from produit join maladie_produit on produit.id = maladie_produit.id_produit where id_maladie = ?;";
            st = con.prepareStatement(query);
            st.setInt(1, getId());
            rs = st.executeQuery();

            while (rs.next()) {
                Produit item = new Produit();
                item.setId(rs.getInt("id"));
                item.setNom(rs.getString("nom"));
                item.setDenorm_prix_vente(rs.getDouble("denorm_prix_vente"));
                item.setMin_age(rs.getInt("min_age"));
                item.setMax_age(rs.getInt("max_age"));
                item.setUnite(Unite.getById(rs.getInt("id_unite")  ,con ));
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
}

// Commun'IT app