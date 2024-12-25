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
public class Fournisseur_mpController {

    @GetMapping("/InitFournisseur_mp")
    public String showAll(Model model) {
        Connection con = null;
        try {
            model.addAttribute("all", Fournisseur_mp.getAll());
            Matiere_premiere[] allMp = Matiere_premiere.getAll();
            model.addAttribute("allMp", allMp);
            Fournisseur[] allFournisseur = Fournisseur.getAll();
            model.addAttribute("allFournisseur", allFournisseur);
            return "Fournisseur_mp";
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

    @PostMapping("/InitFournisseur_mp")
    public String saveOrUpdate(Model model, @RequestParam(required = false) String id,  @RequestParam String mp, @RequestParam String fournisseur, @RequestParam String prix, @RequestParam String date_, @RequestParam(required = false) String mode) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            Fournisseur_mp instance = new Fournisseur_mp();
            instance.setMp(mp) ;
            instance.setFournisseur(fournisseur) ;
            instance.setPrix(prix) ; 
            instance.setDate_(date_) ; 
            if (mode != null && "u".equals(mode)) {
                instance.setId(id);
                instance.update(con);
            } else {
                instance.insert(con);
            }
            return "redirect:/InitFournisseur_mp";
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

    @GetMapping("/InitFournisseur_mp/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        Connection con = null;
        try {
            Fournisseur_mp.deleteById(id);
            return "redirect:/InitFournisseur_mp";
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

    @GetMapping("/TraitFournisseur_mp/{id}")
    public String editForm(Model model, @PathVariable int id) {
        Connection con = null;
        try {
            Fournisseur_mp currentFournisseur_mp = Fournisseur_mp.getById(id);
            model.addAttribute("currentFournisseur_mp", currentFournisseur_mp);
            model.addAttribute("all", Fournisseur_mp.getAll());
            Matiere_premiere[] allMp = Matiere_premiere.getAll();
            model.addAttribute("allMp", allMp);
            Fournisseur[] allFournisseur = Fournisseur.getAll();
            model.addAttribute("allFournisseur", allFournisseur);
            return "Fournisseur_mp";
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
