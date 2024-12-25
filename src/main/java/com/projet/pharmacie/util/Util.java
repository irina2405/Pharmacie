package com.projet.pharmacie.util;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Util {
    public static void verifyStringNotNullOrEmpty (String toVerify, String attributeName) throws Exception{
        if (toVerify == null || toVerify.isEmpty()) {
            throw new Exception("la valeur : "+ attributeName + " ne peut pas etre vide ");
        }
    }

    public static void verifyObjectNotNull (Object toVerify, String attributeName) throws Exception{
        if (toVerify == null) {
            throw new Exception("la valeur : "+ attributeName + " ne peut pas etre vide ");
        }
    }

    public static void verifyNumericPostive (double toVerify , String attributeName) throws Exception{
        if (toVerify < 0) {
            throw new Exception("la valeur : "+ attributeName + " ne peut pas etre negative ");
        }
    }

    public static Timestamp convertTimestampFromHtmlInput(String input) throws Exception {
        try {
            String dateTime = input.replace("T", " ");
            Timestamp rep = Timestamp.valueOf(dateTime);
            return rep;
        } catch (Exception e1) {
            try {
                String dateTime = input.replace("T", " ") + ":00";
                Timestamp rep = Timestamp.valueOf(dateTime);
                return rep;
            } catch (Exception e2) {
                try {
                    // Si format date-heure échoue, essai de format date seul
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date parsedDate = dateFormat.parse(input);
                    return new Timestamp(parsedDate.getTime());
                } catch (ParseException e3) {
                    throw new Exception("Format de date invalide. Utilisez 'yyyy-MM-dd HH:mm:ss' ou 'yyyy-MM-dd'.", e3);
                }
            }
        }
    }

    public static java.sql.Date convertDateFromHtmlInput(String input) throws Exception {
        try {
            // Si format date-heure échoue, essai de format date seul
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = dateFormat.parse(input);
            return new java.sql.Date(parsedDate.getTime());
        } catch (ParseException e3) {
            throw new Exception("Format de date invalide. Utilisez 'yyyy-MM-dd HH:mm:ss' ou 'yyyy-MM-dd'.", e3);
        }
    }

    public static Time convertTimeFromHtmlInput (String input){
        return java.sql.Time.valueOf(input);
    }

    public static int convertIntFromHtmlInput (String input){
        return Integer.parseInt(input);
    }

    public static double convertDoubleFromHtmlInput (String input){
        return Double.parseDouble(input);
    }


    public static boolean convertBooleanFromCheckBox (String input){
        return input  == null ? false : true ;
    }

    public static String  formatNumber(double number) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator(' '); // Définit l'espace comme séparateur des milliers
    
        // Utilise un pattern qui garde exactement deux chiffres après la virgule sans arrondir
        DecimalFormat formatter = new DecimalFormat("#,###.##", symbols);
        formatter.setMaximumFractionDigits(2); // Limite à 2 décimales sans arrondi
        formatter.setMinimumFractionDigits(2); // Affiche toujours 2 chiffres après la virgule
        
        return formatter.format(Math.floor(number * 100) / 100); // Coupe les décimales supplémentaires
    }

}
