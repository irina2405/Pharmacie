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
public class Maladie_produitController {

    @GetMapping("/InitMaladie_produit")
    public String showAll(Model model) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            model.addAttribute("all", Maladie_produit.getAll());
            Produit[] allProduit = Produit.getAll();
            model.addAttribute("allProduit", allProduit);
            Maladie[] allMaladie = Maladie.getAll();
            model.addAttribute("allMaladie", allMaladie);
            return "Maladie_produit";
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

    @PostMapping("/InitMaladie_produit")
    public String saveOrUpdate(Model model, @RequestParam(required = false) String id,  @RequestParam String produit, @RequestParam String maladie, @RequestParam(required = false) String mode) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            Maladie_produit instance = new Maladie_produit();
            instance.setProduit(produit,con) ;
            instance.setMaladie(maladie,con) ;
            if (mode != null && "u".equals(mode)) {
                instance.setId(id);
                instance.update(con);
            } else {
                instance.insert(con);
            }
            return "redirect:/InitMaladie_produit";
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

    @GetMapping("/InitMaladie_produit/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        Connection con = null;
        try {
            Maladie_produit.deleteById(id);
            return "redirect:/InitMaladie_produit";
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

    @GetMapping("/TraitMaladie_produit/{id}")
    public String editForm(Model model, @PathVariable int id) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            Maladie_produit currentMaladie_produit = Maladie_produit.getById(id ,con);
            model.addAttribute("currentMaladie_produit", currentMaladie_produit);
            model.addAttribute("all", Maladie_produit.getAll());
            Produit[] allProduit = Produit.getAll();
            model.addAttribute("allProduit", allProduit);
            Maladie[] allMaladie = Maladie.getAll();
            model.addAttribute("allMaladie", allMaladie);
            return "Maladie_produit";
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
