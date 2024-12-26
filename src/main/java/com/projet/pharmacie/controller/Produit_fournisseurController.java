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
public class Produit_fournisseurController {

    @GetMapping("/InitProduit_fournisseur")
    public String showAll(Model model) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            model.addAttribute("all", Produit_fournisseur.getAll());
            Fournisseur[] allFournisseur = Fournisseur.getAll();
            model.addAttribute("allFournisseur", allFournisseur);
            Produit[] allProduit = Produit.getAll();
            model.addAttribute("allProduit", allProduit);
            return "Produit_fournisseur";
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

    @PostMapping("/InitProduit_fournisseur")
    public String saveOrUpdate(Model model, @RequestParam(required = false) String id,  @RequestParam String fournisseur, @RequestParam String produit, @RequestParam String date_, @RequestParam String prix, @RequestParam(required = false) String mode) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            Produit_fournisseur instance = new Produit_fournisseur();
            instance.setFournisseur(fournisseur) ;
            instance.setProduit(produit) ;
            instance.setDate_(date_) ; 
            instance.setPrix(prix) ; 
            if (mode != null && "u".equals(mode)) {
                instance.setId(id);
                instance.update(con);
            } else {
                instance.insert(con);
            }
            return "redirect:/InitProduit_fournisseur";
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

    @GetMapping("/InitProduit_fournisseur/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        Connection con = null;
        try {
            Produit_fournisseur.deleteById(id);
            return "redirect:/InitProduit_fournisseur";
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

    @GetMapping("/TraitProduit_fournisseur/{id}")
    public String editForm(Model model, @PathVariable int id) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            Produit_fournisseur currentProduit_fournisseur = Produit_fournisseur.getById(id ,con);
            model.addAttribute("currentProduit_fournisseur", currentProduit_fournisseur);
            model.addAttribute("all", Produit_fournisseur.getAll());
            Fournisseur[] allFournisseur = Fournisseur.getAll();
            model.addAttribute("allFournisseur", allFournisseur);
            Produit[] allProduit = Produit.getAll();
            model.addAttribute("allProduit", allProduit);
            return "Produit_fournisseur";
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
