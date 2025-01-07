package com.projet.pharmacie.model;
import java.sql.*;
import java.util.*;
import com.projet.pharmacie.db.MyConnect;
import com.projet.pharmacie.util.*;
public class Formule {
    private int id;
    private Matiere_premiere mp;
    private Produit produit;
    private double qt_mp;
    public Formule(){}
    public Formule(String mp,String produit,String qt_mp, Connection con) throws Exception{
        setMp(mp, con); 
        setProduit(produit,con); 
        setQt_mp(qt_mp); 
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

    public Matiere_premiere getMp() {
        return mp;
    }

    public void setMp(Matiere_premiere mp) throws Exception {
        this.mp = mp;
    }

    public void setMp(String mp, Connection con) throws Exception {
         //define how this type should be conterted from String ... type : Matiere_premiere
       Matiere_premiere toSet = Matiere_premiere.getById(Integer.parseInt(mp),con );
        setMp(toSet) ;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) throws Exception {
        this.produit = produit;
    }

    public void setProduit(String produit,Connection con) throws Exception {
         //define how this type should be conterted from String ... type : Produit
       Produit toSet = Produit.getById(Integer.parseInt(produit),con );
        setProduit(toSet) ;
    }

    public double getQt_mp() {
        return qt_mp;
    }

    public void setQt_mp(double qt_mp) throws Exception {
        Util.verifyNumericPostive(qt_mp, "qt_mp");
        this.qt_mp = qt_mp;
    }

    public void setQt_mp(String qt_mp) throws Exception {
        double toSet =  Util.convertDoubleFromHtmlInput(qt_mp);

        setQt_mp(toSet) ;
    }

    public static Formule getById(int id, Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        Formule instance = null;

        try {
            String query = "SELECT * FROM formule WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                instance = new Formule();
                instance.setId(rs.getInt("id"));
                instance.setMp(Matiere_premiere.getById(rs.getInt("id_mp") ,con ));
                instance.setProduit(Produit.getById(rs.getInt("id_produit") ,con ));
                instance.setQt_mp(rs.getDouble("qt_mp"));
            }
        } catch (Exception e) {
            throw e ;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
        }

        return instance;
    }
    
    public static Formule[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Formule> items = new ArrayList<>();

        try {
            String query = "SELECT * FROM formule order by id asc ";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Formule item = new Formule();
                item.setId(rs.getInt("id"));
                item.setMp(Matiere_premiere.getById(rs.getInt("id_mp")  ,con ));
                item.setProduit(Produit.getById(rs.getInt("id_produit")  ,con ));
                item.setQt_mp(rs.getDouble("qt_mp"));
                items.add(item);
            }
        } catch (Exception e) {
            throw e ;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !false) con.close();
        }

        return items.toArray(new Formule[0]);
    }
    public int insert(Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = "INSERT INTO formule (id_mp, id_produit, qt_mp) VALUES (?, ?, ?) RETURNING id";
            st = con.prepareStatement(query);
            st.setInt(1, this.mp.getId());
            st.setInt(2, this.produit.getId());
            st.setDouble(3, this.qt_mp);
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
            String query = "UPDATE formule SET id_mp = ?, id_produit = ?, qt_mp = ? WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt (1, this.mp.getId());
            st.setInt (2, this.produit.getId());
            st.setDouble(3, this.qt_mp);
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
            String query = "DELETE FROM formule WHERE id = ?";
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

    public static Formule[] search(String mp , String produit , String qt_min , String qt_max) throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Formule> items = new ArrayList<>();

        try {
            String query = "SELECT * FROM formule where 1=1 ";
            if (mp!=null && !mp.isEmpty()) {
                query+= " and id_mp = ?";
            }
            if (produit!=null && !produit.isEmpty()) {
                query+= " and id_produit = ?";
            }
            if (qt_min!=null && !qt_min.isEmpty()) {
                query+= " and qt_mp >= ?";
            }
            if (qt_max!=null && !qt_max.isEmpty()) {
                query+= " and qt_mp <= ?";
            }
            st = con.prepareStatement(query);
            int h = 1;
            if (mp!=null && !mp.isEmpty()) {
                st.setInt(h, Integer.parseInt(mp));
                h++;
            }
            if (produit!=null && !produit.isEmpty()) {
                st.setInt(h, Integer.parseInt(produit));
                h++;
            }
            if (qt_min!=null && !qt_min.isEmpty()) {
                st.setDouble(h, Double.parseDouble(qt_min));
                h++;
            }
            if (qt_max!=null && !qt_max.isEmpty()) {
                st.setDouble(h, Double.parseDouble(qt_max));
            }

            rs = st.executeQuery();

            while (rs.next()) {
                Formule item = new Formule();
                item.setId(rs.getInt("id"));
                item.setMp(Matiere_premiere.getById(rs.getInt("id_mp")  ,con ));
                item.setProduit(Produit.getById(rs.getInt("id_produit")  ,con ));
                item.setQt_mp(rs.getDouble("qt_mp"));
                items.add(item);
            }
        } catch (Exception e) {
            throw e ;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !false) con.close();
        }

        return items.toArray(new Formule[0]);
    }

    public static Formule[] getFormulesForProduct(int idProduit) throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Formule> items = new ArrayList<>();

        try {
            String query = "SELECT * FROM formule where id_produit = ? order by id asc ";
            st = con.prepareStatement(query);
            st.setInt(1, idProduit);
            rs = st.executeQuery();

            while (rs.next()) {
                Formule item = new Formule();
                item.setId(rs.getInt("id"));
                item.setMp(Matiere_premiere.getById(rs.getInt("id_mp")  ,con ));
                item.setProduit(Produit.getById(rs.getInt("id_produit")  ,con ));
                item.setQt_mp(rs.getDouble("qt_mp"));
                items.add(item);
            }
        } catch (Exception e) {
            throw e ;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !false) con.close();
        }

        return items.toArray(new Formule[0]);
    }
}

// Commun'IT app