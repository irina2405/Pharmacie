package com.projet.pharmacie.model;
import java.sql.*;
import java.util.*;
import com.projet.pharmacie.db.MyConnect;
import com.projet.pharmacie.util.*;
public class Produit {
    private int id;
    private String nom;
    private double denorm_prix_vente;
    private int min_age;
    private int max_age;
    private Unite unite;
    private Categorie categorie;

    
    private double qt_total_du_produit; // Quantité totale achetée
    private double qt_total_produite_produit; // Quantité totale produite
    private double qt_total_vendu; // Quantité totale vendue
    private double qt_actuelle; // Quantité actuelle

    private double qtPanier;

    public Produit(){}
    public Produit(String nom,String denorm_prix_vente,String min_age,String max_age,String unite,String categorie,Connection con) throws Exception{
        setNom(nom); 
        setDenorm_prix_vente(denorm_prix_vente); 
        setMin_age(min_age); 
        setMax_age(max_age); 
        setUnite(unite,con); 
        setCategorie(categorie,con); 
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

    public int getMin_age() {
        return min_age;
    }

    public void setMin_age(int min_age) throws Exception {
        Util.verifyNumericPostive(min_age, "min_age");
        this.min_age = min_age;
    }

    public void setMin_age(String min_age) throws Exception {
        int toSet =  Util.convertIntFromHtmlInput(min_age);

        setMin_age(toSet) ;
    }

    public int getMax_age() {
        return max_age;
    }

    public void setMax_age(int max_age) throws Exception {
        Util.verifyNumericPostive(max_age, "max_age");
        this.max_age = max_age;
    }

    public void setMax_age(String max_age) throws Exception {
        int toSet =  Util.convertIntFromHtmlInput(max_age);

        setMax_age(toSet) ;
    }

    public Unite getUnite() {
        return unite;
    }

    public void setUnite(Unite unite) throws Exception {
        this.unite = unite;
    }

    public void setUnite(String unite,Connection con) throws Exception {
         //define how this type should be conterted from String ... type : Unite
        Unite toSet = Unite.getById(Integer.parseInt(unite),con );

        setUnite(toSet) ;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) throws Exception {
        this.categorie = categorie;
    }

    public void setCategorie(String categorie,Connection con) throws Exception {
         //define how this type should be conterted from String ... type : Categorie
        Categorie toSet = Categorie.getById(Integer.parseInt(categorie),con );

        setCategorie(toSet) ;
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
                instance.setMin_age(rs.getInt("min_age"));
                instance.setMax_age(rs.getInt("max_age"));
                instance.setUnite(Unite.getById(rs.getInt("id_unite") ,con ));
                instance.setCategorie(Categorie.getById(rs.getInt("id_categorie") ,con ));
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
                item.setMin_age(rs.getInt("min_age"));
                item.setMax_age(rs.getInt("max_age"));
                item.setUnite(Unite.getById(rs.getInt("id_unite")  ,con ));
                item.setCategorie(Categorie.getById(rs.getInt("id_categorie")  ,con ));
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

    public static Produit[] search(String min, String max, String id) throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Produit> items = new ArrayList<>();

        try {
            String query = "SELECT produit.* FROM produit join maladie_produit on produit.id = maladie_produit.id_produit   WHERE 1 = 1 ";
            if (min!=null && !min.isEmpty()) {
                query+=" and (min_age >= ? or max_age >= ? )";
            }
            if (max!=null && !max.isEmpty()) {
                query+=" and (max_age <= ? or min_age <= ?)";
            }
            if (id!=null && !id.isEmpty()) {
                query+= " and id_maladie = ? ";
            }
            st = con.prepareStatement(query);
            int param = 1;
            if (min!=null && !min.isEmpty()) {
                st.setInt(param, Integer.parseInt(min));
                param ++;
                st.setInt(param, Integer.parseInt(min));
                param ++;
            }
            if (max!=null && !max.isEmpty()) {
                st.setInt(param, Integer.parseInt(max));
                param ++;
                st.setInt(param, Integer.parseInt(max));
                param ++;
            }
            if (id!=null && !id.isEmpty()) {
                st.setInt(param, Integer.parseInt(id));
            }
            rs = st.executeQuery();

            while (rs.next()) {
                Produit item = new Produit();
                item.setId(rs.getInt("id"));
                item.setNom(rs.getString("nom"));
                item.setDenorm_prix_vente(rs.getDouble("denorm_prix_vente"));
                item.setMin_age(rs.getInt("min_age"));
                item.setMax_age(rs.getInt("max_age"));
                item.setUnite(Unite.getById(rs.getInt("id_unite")  ,con ));
                item.setCategorie(Categorie.getById(rs.getInt("id_categorie")  ,con ));
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
        // new
        return items.toArray(new Produit[0]);
    }

    public int insert(Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = "INSERT INTO produit (nom, denorm_prix_vente, min_age, max_age, id_unite, id_categorie) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
            st = con.prepareStatement(query);
            st.setString(1, this.nom);
            st.setDouble(2, this.denorm_prix_vente);
            st.setInt(3, this.min_age);
            st.setInt(4, this.max_age);
            st.setInt(5, this.unite.getId());
            st.setInt(6, this.categorie.getId());
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
            String query = "UPDATE produit SET nom = ?, denorm_prix_vente = ?, min_age = ?, max_age = ?, id_unite = ?, id_categorie = ? WHERE id = ?";
            st = con.prepareStatement(query);
            st.setString(1, this.nom);
            st.setDouble(2, this.denorm_prix_vente);
            st.setInt(3, this.min_age);
            st.setInt(4, this.max_age);
            st.setInt (5, this.unite.getId());
            st.setInt (6, this.categorie.getId());
            st.setInt(7, this.getId());
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