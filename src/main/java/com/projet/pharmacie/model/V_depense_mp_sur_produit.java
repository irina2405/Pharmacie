package com.projet.pharmacie.model;
import java.sql.*;
import java.util.*;

public class V_depense_mp_sur_produit {
    private int id_mp;
    private int id_produit;
    private double qt_produit;
    private double qt_mp;
    private java.sql.Timestamp date_;

    public int getId_mp() {
        return id_mp;
    }

    public int getId_produit() {
        return id_produit;
    }

    public double getQt_produit() {
        return qt_produit;
    }

    public double getQt_mp() {
        return qt_mp;
    }

    public java.sql.Timestamp getDate_() {
        return date_;
    }

    public static List<V_depense_mp_sur_produit> getAll(Connection connection) {
        List<V_depense_mp_sur_produit> results = new ArrayList<>();
        String query = "SELECT * FROM v_depense_mp_sur_produit";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                V_depense_mp_sur_produit item = new V_depense_mp_sur_produit();
                item.id_mp = rs.getInt("id_mp");
                item.id_produit = rs.getInt("id_produit");
                item.qt_produit = rs.getDouble("qt_produit");
                item.qt_mp = rs.getDouble("qt_mp");
                item.date_ = rs.getObject("date_", java.sql.Timestamp.class);
                results.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}