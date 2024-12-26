package com.projet.pharmacie.model;
import java.sql.*;
import java.util.*;
import com.projet.pharmacie.db.MyConnect;
import com.projet.pharmacie.util.*;
public class Matiere_premiere {
    private int id;
    private String nom;
    private Unite unite;
    public Matiere_premiere(){}
    public Matiere_premiere(String nom,String unite) throws Exception{
        setNom(nom); 
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

    public Unite getUnite() {
        return unite;
    }

    public void setUnite(Unite unite) throws Exception {
        this.unite = unite;
    }

    public void setUnite(String unite) throws Exception {
         //define how this type should be conterted from String ... type : Unite
       Connection con = MyConnect.getConnection();        Unite toSet = Unite.getById(Integer.parseInt(unite),con );
         con.close();
        setUnite(toSet) ;
    }

    public static Matiere_premiere getById(int id, Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        Matiere_premiere instance = null;

        try {
            String query = "SELECT * FROM matiere_premiere WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                instance = new Matiere_premiere();
                instance.setId(rs.getInt("id"));
                instance.setNom(rs.getString("nom"));
                instance.setUnite(Unite.getById(rs.getInt("id_unite") ,con ));
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
    public static Matiere_premiere[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Matiere_premiere> items = new ArrayList<>();

        try {
            String query = "SELECT * FROM matiere_premiere order by id asc ";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Matiere_premiere item = new Matiere_premiere();
                item.setId(rs.getInt("id"));
                item.setNom(rs.getString("nom"));
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

        return items.toArray(new Matiere_premiere[0]);
    }
    public int insert(Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = "INSERT INTO matiere_premiere (nom, id_unite) VALUES (?, ?) RETURNING id";
            st = con.prepareStatement(query);
            st.setString(1, this.nom);
            st.setInt(2, this.unite.getId());
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
            String query = "UPDATE matiere_premiere SET nom = ?, id_unite = ? WHERE id = ?";
            st = con.prepareStatement(query);
            st.setString(1, this.nom);
            st.setInt (2, this.unite.getId());
            st.setInt(3, this.getId());
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
            String query = "DELETE FROM matiere_premiere WHERE id = ?";
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