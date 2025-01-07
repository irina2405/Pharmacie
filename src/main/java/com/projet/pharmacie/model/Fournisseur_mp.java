package com.projet.pharmacie.model;
import java.sql.*;
import java.util.*;
import com.projet.pharmacie.db.MyConnect;
import com.projet.pharmacie.util.*;
public class Fournisseur_mp {
    private int id;
    private Matiere_premiere mp;
    private Fournisseur fournisseur;
    private double prix;
    private java.sql.Date date_;
    public Fournisseur_mp(){}
    public Fournisseur_mp(String mp,String fournisseur,String prix,String date_, Connection con) throws Exception{
        setMp(mp,con); 
        setFournisseur(fournisseur, con); 
        setPrix(prix); 
        setDate_(date_); 
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

    public void setMp(String mp,Connection con) throws Exception {
         //define how this type should be conterted from String ... type : Matiere_premiere
        Matiere_premiere toSet = Matiere_premiere.getById(Integer.parseInt(mp),con );
        setMp(toSet) ;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) throws Exception {
        this.fournisseur = fournisseur;
    }

    public void setFournisseur(String fournisseur, Connection con) throws Exception {
         //define how this type should be conterted from String ... type : Fournisseur
       Fournisseur toSet = Fournisseur.getById(Integer.parseInt(fournisseur),con );
        setFournisseur(toSet) ;
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

    public static Fournisseur_mp getById(int id, Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        Fournisseur_mp instance = null;

        try {
            String query = "SELECT * FROM fournisseur_mp WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                instance = new Fournisseur_mp();
                instance.setId(rs.getInt("id"));
                instance.setMp(Matiere_premiere.getById(rs.getInt("id_mp") ,con ));
                instance.setFournisseur(Fournisseur.getById(rs.getInt("id_fournisseur") ,con ));
                instance.setPrix(rs.getDouble("prix"));
                instance.setDate_(rs.getDate("date_"));
            }
        } catch (Exception e) {
            throw e ;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
        }

        return instance;
    }
    public static Fournisseur_mp[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Fournisseur_mp> items = new ArrayList<>();

        try {
            String query = "SELECT * FROM fournisseur_mp order by id asc ";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Fournisseur_mp item = new Fournisseur_mp();
                item.setId(rs.getInt("id"));
                item.setMp(Matiere_premiere.getById(rs.getInt("id_mp")  ,con ));
                item.setFournisseur(Fournisseur.getById(rs.getInt("id_fournisseur")  ,con ));
                item.setPrix(rs.getDouble("prix"));
                item.setDate_(rs.getDate("date_"));
                items.add(item);
            }
        } catch (Exception e) {
            throw e ;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !false) con.close();
        }

        return items.toArray(new Fournisseur_mp[0]);
    }
    public static Fournisseur_mp[] getAllDistinct() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Fournisseur_mp> items = new ArrayList<>();

        try {
            String query = "SELECT DISTINCT ON (id_mp, id_fournisseur) * FROM fournisseur_mp ORDER BY id_mp, id_fournisseur, date_ DESC";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Fournisseur_mp item = new Fournisseur_mp();
                item.setId(rs.getInt("id"));
                item.setMp(Matiere_premiere.getById(rs.getInt("id_mp")  ,con ));
                item.setFournisseur(Fournisseur.getById(rs.getInt("id_fournisseur")  ,con ));
                item.setPrix(rs.getDouble("prix"));
                item.setDate_(rs.getDate("date_"));
                items.add(item);
            }
        } catch (Exception e) {
            throw e ;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !false) con.close();
        }

        return items.toArray(new Fournisseur_mp[0]);
    }
    public int insert(Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = "INSERT INTO fournisseur_mp (id_mp, id_fournisseur, prix, date_) VALUES (?, ?, ?, ?) RETURNING id";
            st = con.prepareStatement(query);
            st.setInt(1, this.mp.getId());
            st.setInt(2, this.fournisseur.getId());
            st.setDouble(3, this.prix);
            st.setDate(4, this.date_);
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
            String query = "UPDATE fournisseur_mp SET id_mp = ?, id_fournisseur = ?, prix = ?, date_ = ? WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt (1, this.mp.getId());
            st.setInt (2, this.fournisseur.getId());
            st.setDouble(3, this.prix);
            st.setDate(4, this.date_);
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
            String query = "DELETE FROM fournisseur_mp WHERE id = ?";
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

    public static Fournisseur_mp getCorrespondant (Fournisseur fournisseur, Matiere_premiere mp , String date) throws Exception{
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Fournisseur_mp instance = null;

        try {
            con = MyConnect.getConnection();
            String query = "SELECT DISTINCT ON (id_mp, id_fournisseur) * FROM fournisseur_mp WHERE id_fournisseur = ? and id_mp = ? and date_ <= ? order by id_mp, id_fournisseur, date_ DESC ";
            st = con.prepareStatement(query);
            st.setInt(1, fournisseur.getId() );
            st.setInt(2, mp.getId() );
            st.setDate(3, Util.convertDateFromHtmlInput(date));
            rs = st.executeQuery();

            if (rs.next()) {
                instance = new Fournisseur_mp();
                instance.setId(rs.getInt("id"));
                instance.setMp(Matiere_premiere.getById(rs.getInt("id_mp") ,con ));
                instance.setFournisseur(Fournisseur.getById(rs.getInt("id_fournisseur") ,con ));
                instance.setPrix(rs.getDouble("prix"));
                instance.setDate_(rs.getDate("date_"));
            }else{
                throw new Exception("no price found for fournisseur :"+fournisseur.getNom() + " mp :" + mp.getNom() + " date:"+date );
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
}

// Commun'IT app