package com.projet.pharmacie.model;
import java.sql.*;
import java.util.*;

public class V_facture_impaye {
    private int id;
    private java.sql.Timestamp date_;
    private double total;
    private double total_paye;
    private int id_client;

    public int getId() {
        return id;
    }

    public java.sql.Timestamp getDate_() {
        return date_;
    }

    public double getTotal() {
        return total;
    }

    public double getTotal_paye() {
        return total_paye;
    }

    public int getId_client() {
        return id_client;
    }

    public static List<V_facture_impaye> getAll(Connection connection) {
        List<V_facture_impaye> results = new ArrayList<>();
        String query = "SELECT * FROM v_facture_impaye";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                V_facture_impaye item = new V_facture_impaye();
                item.id = rs.getInt("id");
                item.date_ = rs.getObject("date_", java.sql.Timestamp.class);
                item.total = rs.getDouble("total");
                item.total_paye = rs.getDouble("total_paye");
                item.id_client = rs.getInt("id_client");
                results.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}