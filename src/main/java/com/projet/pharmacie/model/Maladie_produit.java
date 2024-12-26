package com.projet.pharmacie.model;
import java.sql.*;
import java.util.*;
import com.projet.pharmacie.db.MyConnect;
import com.projet.pharmacie.util.*;
public class Maladie_produit {
    private int id;
    private Produit produit;
    private Maladie maladie;
    public Maladie_produit(){}
    public Maladie_produit(String produit,String maladie) throws Exception{
        setProduit(produit); 
        setMaladie(maladie); 
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

    public Maladie getMaladie() {
        return maladie;
    }

    public void setMaladie(Maladie maladie) throws Exception {
        this.maladie = maladie;
    }

    public void setMaladie(String maladie) throws Exception {
         //define how this type should be conterted from String ... type : Maladie
       Connection con = MyConnect.getConnection();        Maladie toSet = Maladie.getById(Integer.parseInt(maladie),con );
         con.close();
        setMaladie(toSet) ;
    }

    public static Maladie_produit getById(int id, Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        Maladie_produit instance = null;

        try {
            String query = "SELECT * FROM maladie_produit WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                instance = new Maladie_produit();
                instance.setId(rs.getInt("id"));
                instance.setProduit(Produit.getById(rs.getInt("id_produit") ,con ));
                instance.setMaladie(Maladie.getById(rs.getInt("id_maladie") ,con ));
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
    public static Maladie_produit[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Maladie_produit> items = new ArrayList<>();

        try {
            String query = "SELECT * FROM maladie_produit order by id asc ";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Maladie_produit item = new Maladie_produit();
                item.setId(rs.getInt("id"));
                item.setProduit(Produit.getById(rs.getInt("id_produit")  ,con ));
                item.setMaladie(Maladie.getById(rs.getInt("id_maladie")  ,con ));
                items.add(item);
            }
        } catch (Exception e) {
            throw e ;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !false) con.close();
        }

        return items.toArray(new Maladie_produit[0]);
    }
    public int insert(Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = "INSERT INTO maladie_produit (id_produit, id_maladie) VALUES (?, ?) RETURNING id";
            st = con.prepareStatement(query);
            st.setInt(1, this.produit.getId());
            st.setInt(2, this.maladie.getId());
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
            String query = "UPDATE maladie_produit SET id_produit = ?, id_maladie = ? WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt (1, this.produit.getId());
            st.setInt (2, this.maladie.getId());
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
            String query = "DELETE FROM maladie_produit WHERE id = ?";
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