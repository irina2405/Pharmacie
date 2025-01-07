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
public class Matiere_premiereController {

    @GetMapping("/InitMatiere_premiere")
    public String showAll(Model model) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            model.addAttribute("all", Matiere_premiere.getAll());
            Unite[] allUnite = Unite.getAll();
            model.addAttribute("allUnite", allUnite);
            return "Matiere_premiere";
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
    
    @PostMapping("/InitMatiere_premiere")
    public String saveOrUpdate(Model model, @RequestParam(required = false) String id,  @RequestParam String nom, @RequestParam String unite, @RequestParam(required = false) String mode) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            Matiere_premiere instance = new Matiere_premiere();
            instance.setNom(nom) ; 
            instance.setUnite(unite,con) ;
            if (mode != null && "u".equals(mode)) {
                instance.setId(id);
                instance.update(con);
            } else {
                instance.insert(con);
            }
            return "redirect:/InitMatiere_premiere";
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

    @GetMapping("/InitMatiere_premiere/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        Connection con = null;
        try {
            Matiere_premiere.deleteById(id);
            return "redirect:/InitMatiere_premiere";
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

    @GetMapping("/TraitMatiere_premiere/{id}")
    public String editForm(Model model, @PathVariable int id) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            Matiere_premiere currentMatiere_premiere = Matiere_premiere.getById(id ,con);
            model.addAttribute("currentMatiere_premiere", currentMatiere_premiere);
            model.addAttribute("all", Matiere_premiere.getAll());
            Unite[] allUnite = Unite.getAll();
            model.addAttribute("allUnite", allUnite);
            return "Matiere_premiere";
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

    @GetMapping("/produits_concernes_par_mp/{id_mp}")
    public String produits_concernes_par_mp(Model model , @PathVariable("id_mp") int id_mp) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            Matiere_premiere mp = Matiere_premiere.getById(id_mp, con);
            Produit[] produits_concernes_par_mp = mp.getProduitsConcernes ();
            model.addAttribute("all", produits_concernes_par_mp);
            return "produits_concernes";
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
