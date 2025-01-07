package com.projet.pharmacie.model;
import java.sql.*;
import java.util.*;

import com.projet.pharmacie.util.Util;

public class V_stat_vente {
    private double annee;
    private double mois;
    private double ca;

    public double getAnnee() {
        return annee;
    }

    public double getMois() {
        return mois;
    }

    public double getCa() {
        return ca;
    }

    public static List<V_stat_vente> getAll(Connection connection) {
        List<V_stat_vente> results = new ArrayList<>();
        String query = "SELECT * FROM v_stat_vente";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                V_stat_vente item = new V_stat_vente();
                item.annee = rs.getDouble("annee");
                item.mois = rs.getDouble("mois");
                item.ca = rs.getDouble("ca");
                results.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static List<V_stat_vente> getAllBetween(Connection connection, String date_start, String date_end) throws Exception {
        List<V_stat_vente> results = new ArrayList<>();
    
        // Construction de la requête SQL
        String query = """
            SELECT EXTRACT(YEAR FROM date_) AS annee, 
                   EXTRACT(MONTH FROM date_) AS mois, 
                   SUM(total_paye) AS ca 
            FROM facture 
            WHERE 1=1
        """;
        if (date_start != null && !date_start.isEmpty()) {
            query += " AND date_ >= ? ";
        }
        if (date_end != null && !date_end.isEmpty()) {
            query += " AND date_ <= ? ";
        }
        query += " GROUP BY EXTRACT(YEAR FROM date_), EXTRACT(MONTH FROM date_) ";
        query += " ORDER BY annee, mois ";
    
        // Préparation et exécution de la requête
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            int parameterIndex = 1;
            if (date_start != null && !date_start.isEmpty()) {
                stmt.setTimestamp(parameterIndex, Util.convertTimestampFromHtmlInput(date_start));
                parameterIndex ++;
            }
            if (date_end != null && !date_end.isEmpty()) {
                stmt.setTimestamp(parameterIndex, Util.convertTimestampFromHtmlInput(date_end));
            }
    
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    V_stat_vente item = new V_stat_vente();
                    item.annee = rs.getDouble("annee");
                    item.mois = rs.getDouble("mois");
                    item.ca = rs.getDouble("ca");
                    results.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
    
}