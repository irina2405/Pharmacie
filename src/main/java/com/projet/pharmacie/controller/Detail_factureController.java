package com.projet.pharmacie.controller;

import java.sql.Connection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.projet.pharmacie.db.MyConnect;
import com.projet.pharmacie.model.*;

@Controller
public class Detail_factureController {

    @GetMapping("/InitDetail_facture")
    public String showAll(Model model) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            model.addAttribute("all", Detail_facture.getAll());
            Produit[] allProduit = Produit.getAll();
            model.addAttribute("allProduit", allProduit);
            Facture[] allFacture = Facture.getAll();
            model.addAttribute("allFacture", allFacture);
            return "Detail_facture";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("eMessage", e.getMessage() + (e.getCause() != null ? "<br> <hr>" + e.getCause().getMessage() : "") ); 
            return "Error";
        } finally {
            if (con != null) {
                try { con.close(); } catch (Exception ignored) {}
            }
        }

    }

    @PostMapping("/InitDetail_facture")
    public String saveOrUpdate(Model model, @RequestParam(required = false) String id,  @RequestParam String produit, @RequestParam String facture, @RequestParam String denorm_prix_vente, @RequestParam String qt_produit, @RequestParam(required = false) String mode) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            Detail_facture instance = new Detail_facture();
            instance.setProduit(produit,con) ;
            instance.setFacture(facture,con) ;
            instance.setDenorm_prix_vente(denorm_prix_vente) ; 
            instance.setQt_produit(qt_produit) ; 
            if (mode != null && "u".equals(mode)) {
                instance.setId(id);
                instance.update(con);
            } else {
                instance.insert(con);
            }
            return "redirect:/InitDetail_facture";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("eMessage", e.getMessage() + (e.getCause() != null ? "<br> <hr>" + e.getCause().getMessage() : "") ); 
            return "Error";
        } finally {
            if (con != null) {
                try { con.close(); } catch (Exception ignored) {}
            }
        }
    }

    @GetMapping("/InitDetail_facture/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        Connection con = null;
        try {
            Detail_facture.deleteById(id);
            return "redirect:/InitDetail_facture";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("eMessage", e.getMessage() + (e.getCause() != null ? "<br> <hr>" + e.getCause().getMessage() : "") ); 
            return "Error";
        } finally {
            if (con != null) {
                try { con.close(); } catch (Exception ignored) {}
            }
        }
    }

    @GetMapping("/TraitDetail_facture/{id}")
    public String editForm(Model model, @PathVariable int id) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            Detail_facture currentDetail_facture = Detail_facture.getById(id ,con);
            model.addAttribute("currentDetail_facture", currentDetail_facture);
            model.addAttribute("all", Detail_facture.getAll());
            Produit[] allProduit = Produit.getAll();
            model.addAttribute("allProduit", allProduit);
            Facture[] allFacture = Facture.getAll();
            model.addAttribute("allFacture", allFacture);
            return "Detail_facture";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("eMessage", e.getMessage() + (e.getCause() != null ? "<br> <hr>" + e.getCause().getMessage() : "") ); 
            return "Error";
        } finally {
            if (con != null) {
                try { con.close(); } catch (Exception ignored) {}
            }
        }
    }

}
