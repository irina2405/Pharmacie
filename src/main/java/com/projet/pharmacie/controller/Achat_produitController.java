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
public class Achat_produitController {

    @GetMapping("/InitAchat_produit")
    public String showAll(Model model) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            model.addAttribute("all", Achat_produit.getAll());
            Fournisseur[] allFournisseur = Fournisseur.getAll();
            model.addAttribute("allFournisseur", allFournisseur);
            Produit[] allProduit = Produit.getAll();
            model.addAttribute("allProduit", allProduit);
            return "Achat_produit";
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

    @PostMapping("/InitAchat_produit")
    public String saveOrUpdate(Model model, @RequestParam(required = false) String id,  @RequestParam String date_, @RequestParam String qt_produit, @RequestParam String denorm_prix_achat, @RequestParam String fournisseur, @RequestParam String produit, @RequestParam(required = false) String mode) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            Achat_produit instance = new Achat_produit();
            instance.setDate_(date_) ; 
            instance.setQt_produit(qt_produit) ; 
            instance.setDenorm_prix_achat(denorm_prix_achat) ; 
            instance.setFournisseur(fournisseur) ;
            instance.setProduit(produit) ;
            if (mode != null && "u".equals(mode)) {
                instance.setId(id);
                instance.update(con);
            } else {
                instance.insert(con);
            }
            return "redirect:/InitAchat_produit";
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

    @GetMapping("/InitAchat_produit/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        Connection con = null;
        try {
            Achat_produit.deleteById(id);
            return "redirect:/InitAchat_produit";
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

    @GetMapping("/TraitAchat_produit/{id}")
    public String editForm(Model model, @PathVariable int id) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            Achat_produit currentAchat_produit = Achat_produit.getById(id ,con);
            model.addAttribute("currentAchat_produit", currentAchat_produit);
            model.addAttribute("all", Achat_produit.getAll());
            Fournisseur[] allFournisseur = Fournisseur.getAll();
            model.addAttribute("allFournisseur", allFournisseur);
            Produit[] allProduit = Produit.getAll();
            model.addAttribute("allProduit", allProduit);
            return "Achat_produit";
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
