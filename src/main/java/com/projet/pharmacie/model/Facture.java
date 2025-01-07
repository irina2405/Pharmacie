package com.projet.pharmacie.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.projet.pharmacie.db.MyConnect;
import com.projet.pharmacie.util.Util;
public class Facture {
    private int id;
    private java.sql.Timestamp date_;
    private double total;
    private double total_paye;
    private Client client;
    public Facture(){}
    public Facture(String date_,String total,String total_paye,String client,Connection con) throws Exception{
        setDate_(date_); 
        setTotal(total); 
        setTotal_paye(total_paye); 
        setClient(client, con); 
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) throws Exception {
        Util.verifyNumericPostive(total, "total");
        this.total = total;
    }

    public void setTotal(String total) throws Exception {
        double toSet =  Util.convertDoubleFromHtmlInput(total);

        setTotal(toSet) ;
    }

    public double getTotal_paye() {
        return total_paye;
    }

    public void setTotal_paye(double total_paye) throws Exception {
        Util.verifyNumericPostive(total_paye, "total_paye");
        if (total>=0 && total_paye> total) {
            throw new Exception("total paye en exces .");
        }
        this.total_paye = total_paye;
    }

    public void setTotal_paye(String total_paye) throws Exception {
        double toSet =  Util.convertDoubleFromHtmlInput(total_paye);

        setTotal_paye(toSet) ;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) throws Exception {
        this.client = client;
    }

    public void setClient(String client, Connection con) throws Exception {
         //define how this type should be conterted from String ... type : Client
         if (client!=null && !client.isEmpty()) {
              Client toSet = Client.getById(Integer.parseInt(client),con );
              setClient(toSet) ;
         }
    }

    public static Facture getById(int id, Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        Facture instance = null;

        try {
            String query = "SELECT * FROM facture WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();
            
            if (rs.next()) {
                instance = new Facture();
                instance.setId(rs.getInt("id"));
                instance.setDate_(rs.getTimestamp("date_"));
                instance.setTotal(rs.getDouble("total"));
                instance.setTotal_paye(rs.getDouble("total_paye"));
                instance.setClient(Client.getById(rs.getInt("id_client") ,con ));
            }
        } catch (Exception e) {
            throw e ;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
        }

        return instance;
    }
    public static Facture[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Facture> items = new ArrayList<>();

        try {
            String query = "SELECT * FROM facture order by id asc ";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Facture item = new Facture();
                item.setId(rs.getInt("id"));
                item.setDate_(rs.getTimestamp("date_"));
                item.setTotal(rs.getDouble("total"));
                item.setTotal_paye(rs.getDouble("total_paye"));
                item.setClient(Client.getById(rs.getInt("id_client")  ,con ));
                items.add(item);
            }
        } catch (Exception e) {
            throw e ;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !false) con.close();
        }

        return items.toArray(new Facture[0]);
    }
    public int insertUncommitted(Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = "INSERT INTO facture (date_, total, total_paye, id_client) VALUES (?, ?, ?, ?) RETURNING id";
            st = con.prepareStatement(query);
            st.setTimestamp(1, this.date_);
            st.setDouble(2, this.total);
            st.setDouble(3, this.total_paye);
            if (client != null) {
                st.setInt(4, this.client.getId());
            } else {
                st.setNull(4, java.sql.Types.INTEGER); // Insérer une valeur NULL dans la colonne id_client
            }
            try {
                rs = st.executeQuery();
                if (rs.next()) {
                    int generatedId = rs.getInt("id");
                    this.setId(generatedId); 
                    // con.commit();
                    return generatedId;
                } else {
                    // con.rollback();
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
    public void updateUncommitted(Connection con) throws Exception {
        PreparedStatement st = null;
        try {
            String query = "UPDATE facture SET date_ = ?, total = ?, total_paye = ?, id_client = ? WHERE id = ?";
            st = con.prepareStatement(query);
            st.setTimestamp(1, this.date_);
            st.setDouble(2, this.total);
            st.setDouble(3, this.total_paye);
            st.setInt (4, this.client.getId());
            st.setInt(5, this.getId());
            try {
                st.executeUpdate();
                // con.commit();
            } catch (Exception e) {
                // con.rollback();
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
            String query = "DELETE FROM facture WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            try {
                Facture me = Facture.getById(id, con);
                Double retrait_rendu = me.getTotal_paye();
                Tresorerie tresorerie = new Tresorerie("annulation vente produit " , (new Timestamp(System.currentTimeMillis())).toString(),"0", retrait_rendu.toString() );
                tresorerie.insertUncommitted(con);
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
    public void payertotalement () throws Exception{
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            Double depot_reste = total - total_paye;
            setTotal_paye(getTotal());
            Tresorerie tresorerie = new Tresorerie("payement en totalite " + this.getClient().getNom(), (new Timestamp(System.currentTimeMillis())).toString(), depot_reste.toString() , "0");
            tresorerie.insertUncommitted(con);
            updateUncommitted(con);
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw e;
        }finally{
            try {
                if(con!= null) con.close();
            } catch (Exception e) {
            }
        }
    }
}

// Commun'IT app