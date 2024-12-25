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
public class Achat_mpController {

    @GetMapping("/InitAchat_mp")
    public String showAll(Model model) {
        Connection con = null;
        try {
            model.addAttribute("all", Achat_mp.getAll());
            Fournisseur[] allFournisseur = Fournisseur.getAll();
            model.addAttribute("allFournisseur", allFournisseur);
            Matiere_premiere[] allMp = Matiere_premiere.getAll();
            model.addAttribute("allMp", allMp);
            return "Achat_mp";
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

    @PostMapping("/InitAchat_mp")
    public String saveOrUpdate(Model model, @RequestParam(required = false) String id,  @RequestParam String date_, @RequestParam String qt_mp, @RequestParam String denorm_prix_achat, @RequestParam String fournisseur, @RequestParam String mp, @RequestParam(required = false) String mode) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            Achat_mp instance = new Achat_mp();
            instance.setDate_(date_) ; 
            instance.setQt_mp(qt_mp) ; 
            instance.setDenorm_prix_achat(denorm_prix_achat) ; 
            instance.setFournisseur(fournisseur) ;
            instance.setMp(mp) ;
            if (mode != null && "u".equals(mode)) {
                instance.setId(id);
                instance.update(con);
            } else {
                instance.insert(con);
            }
            return "redirect:/InitAchat_mp";
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

    @GetMapping("/InitAchat_mp/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        Connection con = null;
        try {
            Achat_mp.deleteById(id);
            return "redirect:/InitAchat_mp";
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

    @GetMapping("/TraitAchat_mp/{id}")
    public String editForm(Model model, @PathVariable int id) {
        Connection con = null;
        try {
            Achat_mp currentAchat_mp = Achat_mp.getById(id);
            model.addAttribute("currentAchat_mp", currentAchat_mp);
            model.addAttribute("all", Achat_mp.getAll());
            Fournisseur[] allFournisseur = Fournisseur.getAll();
            model.addAttribute("allFournisseur", allFournisseur);
            Matiere_premiere[] allMp = Matiere_premiere.getAll();
            model.addAttribute("allMp", allMp);
            return "Achat_mp";
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
