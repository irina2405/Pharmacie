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
public class MaladieController {

    @GetMapping("/InitMaladie")
    public String showAll(Model model) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            model.addAttribute("all", Maladie.getAll());
            return "Maladie";
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

    @PostMapping("/InitMaladie")
    public String saveOrUpdate(Model model, @RequestParam(required = false) String id,  @RequestParam String nom, @RequestParam(required = false) String mode) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            Maladie instance = new Maladie();
            instance.setNom(nom) ; 
            if (mode != null && "u".equals(mode)) {
                instance.setId(id);
                instance.update(con);
            } else {
                instance.insert(con);
            }
            return "redirect:/InitMaladie";
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

    @GetMapping("/InitMaladie/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        Connection con = null;
        try {
            Maladie.deleteById(id);
            return "redirect:/InitMaladie";
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

    @GetMapping("/TraitMaladie/{id}")
    public String editForm(Model model, @PathVariable int id) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            Maladie currentMaladie = Maladie.getById(id ,con);
            model.addAttribute("currentMaladie", currentMaladie);
            model.addAttribute("all", Maladie.getAll());
            return "Maladie";
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

    @GetMapping("/produits_concernes_par_maladie/{id_maladie}")
    public String produits_concernes_par_maladie(Model model , @PathVariable("id_maladie") int id_maladie) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            Maladie maladie = Maladie.getById(id_maladie, con);
            Produit[] produits_concernes_par_maladie = maladie.getProduitsConcernes ();
            model.addAttribute("all", produits_concernes_par_maladie);
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
