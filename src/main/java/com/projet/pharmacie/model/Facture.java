package com.projet.pharmacie.model;
import java.sql.*;
import java.util.*;
import com.projet.pharmacie.db.MyConnect;
import com.projet.pharmacie.util.*;
public class Facture {
    private int id;
    private java.sql.Timestamp date_;
    private double total;
    private double total_paye;
    private Client client;
    public Facture(){}
    public Facture(String date_,String total,String total_paye,String client) throws Exception{
        setDate_(date_); 
        setTotal(total); 
        setTotal_paye(total_paye); 
        setClient(client); 
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

    public void setClient(String client) throws Exception {
         //define how this type should be conterted from String ... type : Client
       Connection con = MyConnect.getConnection();        Client toSet = Client.getById(Integer.parseInt(client),con );
         con.close();
        setClient(toSet) ;
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
            if (con != null && !true) con.close();
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
    public int insert(Connection con) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            String query = "INSERT INTO facture (date_, total, total_paye, id_client) VALUES (?, ?, ?, ?) RETURNING id";
            st = con.prepareStatement(query);
            st.setTimestamp(1, this.date_);
            st.setDouble(2, this.total);
            st.setDouble(3, this.total_paye);
            st.setInt(4, this.client.getId());
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
            String query = "UPDATE facture SET date_ = ?, total = ?, total_paye = ?, id_client = ? WHERE id = ?";
            st = con.prepareStatement(query);
            st.setTimestamp(1, this.date_);
            st.setDouble(2, this.total);
            st.setDouble(3, this.total_paye);
            st.setInt (4, this.client.getId());
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
            String query = "DELETE FROM facture WHERE id = ?";
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