package com.projet.pharmacie.model;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import com.projet.pharmacie.db.MyConnect;
import com.projet.pharmacie.util.*;
public class Produit {
    private int id;
    private String nom;
    private double denorm_prix_vente;
    private Unite unite;
    private double qt_total_du_produit; // Quantité totale achetée
    private double qt_total_produite_produit; // Quantité totale produite
    private double qt_total_vendu; // Quantité totale vendue
    private double qt_actuelle; // Quantité actuelle

    private double qtPanier;

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
       Connection con = MyConnect.getConnection();        Unite toSet = Unite.getById(Integer.parseInt(unite),con );
         con.close();
        setUnite(toSet) ;
    }

    public double getQt_total_du_produit() {
        return qt_total_du_produit;
    }

    public void setQt_total_du_produit(double qt_total_du_produit) {
        this.qt_total_du_produit = qt_total_du_produit;
    }

    public double getQt_total_produite_produit() {
        return qt_total_produite_produit;
    }

    public void setQt_total_produite_produit(double qt_total_produite_produit) {
        this.qt_total_produite_produit = qt_total_produite_produit;
    }

    public double getQt_total_vendu() {
        return qt_total_vendu;
    }

    public void setQt_total_vendu(double qt_total_vendu) {
        this.qt_total_vendu = qt_total_vendu;
    }

    public double getQt_actuelle() {
        return qt_actuelle;
    }

    public void setQt_actuelle(double qt_actuelle) {
        this.qt_actuelle = qt_actuelle;
    }

    public double getQtPanier() {
        return qtPanier;
    }

    public void setQtPanier(double qtPanier) {
        this.qtPanier = qtPanier;
    }

    public Formule[] getFormule() throws Exception {
        Formule[] formule = Formule.getFormulesForProduct(id);
        return formule;
    }


    public static Produit getById(int id, Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        Produit instance = null;

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
    public Histo_prix_produit getLast () throws Exception{
        //;
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Histo_prix_produit instance = null;

        try {
            con = MyConnect.getConnection();
            String query = "select distinct on (id_produit) histo_prix_produit.* from histo_prix_produit  WHERE id = ? order by id_produit , date_ desc";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                instance = new Histo_prix_produit();
                instance.setId(rs.getInt("id"));
                instance.setDate_(rs.getDate("date_"));
                instance.setPrix_vente_produit(rs.getDouble("prix_vente_produit"));
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
    public static Produit[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Produit> items = new ArrayList<>();

        try {
            String query = "SELECT * FROM Produit_with_qt order by id asc ";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Produit item = new Produit();
                item.setId(rs.getInt("id"));
                item.setNom(rs.getString("nom"));
                item.setDenorm_prix_vente(rs.getDouble("denorm_prix_vente"));
                item.setUnite(Unite.getById(rs.getInt("id_unite"), con));
                item.setQt_total_du_produit(rs.getDouble("qt_total_du_produit"));
                item.setQt_total_produite_produit(rs.getDouble("qt_total_produite_produit"));
                item.setQt_total_vendu(rs.getDouble("qt_total_vendu"));
                item.setQt_actuelle(
                    item.getQt_total_produite_produit() + item.getQt_total_du_produit() - item.getQt_total_vendu()
                );
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

    public static Produit[] getProduitAvecFormule() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Produit> items = new ArrayList<>();

        try {
            String query = "SELECT produit.* FROM produit join Formule on produit.id = Formule.ID_PRODUIT order by id asc ";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Produit item = new Produit();
                item.setId(rs.getInt("id"));
                item.setNom(rs.getString("nom"));
                item.setDenorm_prix_vente(rs.getDouble("denorm_prix_vente"));
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
                    Histo_prix_produit histo_prix_produit = new Histo_prix_produit();
                    histo_prix_produit.setDate_(new Date(System.currentTimeMillis()));
                    histo_prix_produit.setPrix_vente_produit(this.denorm_prix_vente);
                    histo_prix_produit.setProduit(this);
                    histo_prix_produit.insertUncommited(con);
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
                Histo_prix_produit histo_prix_produit = new Histo_prix_produit();
                histo_prix_produit.setDate_(new Date(System.currentTimeMillis()));
                histo_prix_produit.setPrix_vente_produit(this.denorm_prix_vente);
                histo_prix_produit.setProduit(this);
                histo_prix_produit.insertUncommited(con);
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

    public Matiere_premiere[] getMatiere_premieresConcernes () throws Exception{
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Matiere_premiere> items = new ArrayList<>();

        try {
            String query = "with concerned_mp as ( " + 
                                "   select distinct(id_mp) id_mp " + 
                                "    from Formule  " + 
                                "    where ID_PRODUIT = ? " + 
                                " ) " + 
                                " select mp.*  " + 
                                "    FROM matiere_premiere mp  " + 
                                "    join CONCERNED_mp " + 
                                "    on id = id_mp;";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            while (rs.next()) {
                Matiere_premiere item = new Matiere_premiere();
                item.setId(rs.getInt("id"));
                item.setNom(rs.getString("nom"));
                item.setUnite(Unite.getById(rs.getInt("id_unite"), con));
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
    public Maladie[] getMaladiesConcernes () throws Exception{
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Maladie> items = new ArrayList<>();

        try {
            String query = "select maladie.* from maladie join maladie_produit on maladie.id = maladie_produit.id_produit where id_produit = ? ;";
            st = con.prepareStatement(query);
            st.setInt(1, id);
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

    public static double getTotal(HashMap<Integer, Produit> panier) {
        double total = 0.0;
        if (panier != null) {
            for (Produit produit : panier.values()) {
                total += produit.getDenorm_prix_vente() * produit.getQtPanier();
            }
        }
        return total;
    }
    
}

// Commun'IT app