package com.projet.pharmacie.model;
import java.sql.*;
import java.util.*;
import com.projet.pharmacie.db.MyConnect;
import com.projet.pharmacie.util.*;
public class Detail_facture {
    private int id;
    private Produit produit;
    private Facture facture;
    private double denorm_prix_vente;
    private double qt_produit;
    public Detail_facture(){}
    public Detail_facture(String produit,String facture,String denorm_prix_vente,String qt_produit) throws Exception{
        setProduit(produit); 
        setFacture(facture); 
        setDenorm_prix_vente(denorm_prix_vente); 
        setQt_produit(qt_produit); 
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

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) throws Exception {
        this.facture = facture;
    }

    public void setFacture(String facture) throws Exception {
         //define how this type should be conterted from String ... type : Facture
       Connection con = MyConnect.getConnection();        Facture toSet = Facture.getById(Integer.parseInt(facture),con );
         con.close();
        setFacture(toSet) ;
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

    public static Detail_facture getById(int id, Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        Detail_facture instance = null;

        try {
            String query = "SELECT * FROM detail_facture WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                instance = new Detail_facture();
                instance.setId(rs.getInt("id"));
                instance.setProduit(Produit.getById(rs.getInt("id_produit") ,con ));
                instance.setFacture(Facture.getById(rs.getInt("id_facture") ,con ));
                instance.setDenorm_prix_vente(rs.getDouble("denorm_prix_vente"));
                instance.setQt_produit(rs.getDouble("qt_produit"));
            }
        } catch (Exception e) {
            throw e ;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
        }

        return instance;
    }
    public static Detail_facture[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Detail_facture> items = new ArrayList<>();

        try {
            String query = "SELECT * FROM detail_facture order by id asc ";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Detail_facture item = new Detail_facture();
                item.setId(rs.getInt("id"));
                item.setProduit(Produit.getById(rs.getInt("id_produit")  ,con ));
                item.setFacture(Facture.getById(rs.getInt("id_facture")  ,con ));
                item.setDenorm_prix_vente(rs.getDouble("denorm_prix_vente"));
                item.setQt_produit(rs.getDouble("qt_produit"));
                items.add(item);
            }
        } catch (Exception e) {
            throw e ;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !false) con.close();
        }

        return items.toArray(new Detail_facture[0]);
    }
    public int insert(Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = "INSERT INTO detail_facture (id_produit, id_facture, denorm_prix_vente, qt_produit) VALUES (?, ?, ?, ?) RETURNING id";
            st = con.prepareStatement(query);
            st.setInt(1, this.produit.getId());
            st.setInt(2, this.facture.getId());
            st.setDouble(3, this.denorm_prix_vente);
            st.setDouble(4, this.qt_produit);
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
            String query = "UPDATE detail_facture SET id_produit = ?, id_facture = ?, denorm_prix_vente = ?, qt_produit = ? WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt (1, this.produit.getId());
            st.setInt (2, this.facture.getId());
            st.setDouble(3, this.denorm_prix_vente);
            st.setDouble(4, this.qt_produit);
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
            String query = "DELETE FROM detail_facture WHERE id = ?";
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