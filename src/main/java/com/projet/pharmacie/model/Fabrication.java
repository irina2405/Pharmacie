package com.projet.pharmacie.model;
import java.sql.*;
import java.util.*;
import com.projet.pharmacie.db.MyConnect;
import com.projet.pharmacie.util.*;
public class Fabrication {
    private int id;
    private java.sql.Timestamp date_;
    private double qt_produit;
    private double cout;
    private Produit produit;
    public Fabrication(){}
    public Fabrication(String date_,String qt_produit,String cout,String produit,Connection con) throws Exception{
        setDate_(date_); 
        setQt_produit(qt_produit); 
        setCout(cout); 
        setProduit(produit, con); 
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

    public double getCout() {
        return cout;
    }

    public void setCout(double cout) throws Exception {
        Util.verifyNumericPostive(cout, "cout");
        this.cout = cout;
    }

    public void setCout(String cout) throws Exception {
        double toSet =  Util.convertDoubleFromHtmlInput(cout);

        setCout(toSet) ;
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

    public static Fabrication getById(int id, Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        Fabrication instance = null;

        try {
            String query = "SELECT * FROM fabrication WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                instance = new Fabrication();
                instance.setId(rs.getInt("id"));
                instance.setDate_(rs.getTimestamp("date_"));
                instance.setQt_produit(rs.getDouble("qt_produit"));
                instance.setCout(rs.getDouble("cout"));
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
    public static Fabrication[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Fabrication> items = new ArrayList<>();

        try {
            String query = "SELECT * FROM fabrication order by id asc ";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Fabrication item = new Fabrication();
                item.setId(rs.getInt("id"));
                item.setDate_(rs.getTimestamp("date_"));
                item.setQt_produit(rs.getDouble("qt_produit"));
                item.setCout(rs.getDouble("cout"));
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

        return items.toArray(new Fabrication[0]);
    }
    public int insert(Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = "INSERT INTO fabrication (date_, qt_produit, cout, id_produit) VALUES (?, ?, ?, ?) RETURNING id";
            st = con.prepareStatement(query);
            st.setTimestamp(1, this.date_);
            st.setDouble(2, this.qt_produit);
            st.setInt(4, this.produit.getId());
            try {
                Formule[] forFabric = this.produit.getFormule();
                double cout_fabrication = 0.0;
                for (int i = 0; i < forFabric.length; i++) {
                    double qtRest = forFabric[i].getMp().getQuantiteRestante(con,date_);
                    double besoin = forFabric[i].getQt_mp()*this.qt_produit;
                    if (besoin > qtRest) {
                        throw new Exception("la matiere premiere "+ forFabric[i].getMp().getNom() + " est insuffisante -> qt actuelle : " +qtRest + " contre besoin : " + forFabric[i].getQt_mp() + "mp * "+this.qt_produit+"prod" );
                    }
                    Achat_mp[] mesAchat_mps = forFabric[i].getMp().getMesAchat(date_);
                    for (int j = 0; j < mesAchat_mps.length; j++) {
                        if (mesAchat_mps[j].getReste_mp()== besoin) {
                            cout_fabrication += mesAchat_mps[j].getReste_mp()*mesAchat_mps[j].getFournisseur_mp().getPrix();
                            besoin = 0.0;
                            mesAchat_mps[j].setReste_mp(0.0);
                            mesAchat_mps[j].updateUncommitted(con);
                            break;
                        }else if (mesAchat_mps[j].getReste_mp()> besoin){
                            cout_fabrication += (mesAchat_mps[j].getReste_mp()-besoin)*mesAchat_mps[j].getFournisseur_mp().getPrix();
                            mesAchat_mps[j].setReste_mp(mesAchat_mps[j].getReste_mp()-besoin);
                            besoin = 0.0;
                            mesAchat_mps[j].updateUncommitted(con);
                        }else{
                            cout_fabrication += mesAchat_mps[j].getReste_mp()*mesAchat_mps[j].getFournisseur_mp().getPrix();
                            besoin -= mesAchat_mps[j].getReste_mp();
                            mesAchat_mps[j].setReste_mp(0.0);
                            mesAchat_mps[j].updateUncommitted(con);
                        }
                    }
                    if (besoin > 0) {
                        System.out.println("reverif besoin ");
                    }
                }
                // execution de la requette 
                st.setDouble(3, cout_fabrication);
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
            String query = "UPDATE fabrication SET date_ = ?, qt_produit = ?, cout = ?, id_produit = ? WHERE id = ?";
            st = con.prepareStatement(query);
            st.setTimestamp(1, this.date_);
            st.setDouble(2, this.qt_produit);
            st.setDouble(3, this.cout);
            st.setInt (4, this.produit.getId());
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
            String query = "DELETE FROM fabrication WHERE id = ?";
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