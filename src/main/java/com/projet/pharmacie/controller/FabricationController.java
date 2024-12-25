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
public class FabricationController {

    @GetMapping("/InitFabrication")
    public String showAll(Model model) {
        Connection con = null;
        try {
            model.addAttribute("all", Fabrication.getAll());
            Produit[] allProduit = Produit.getAll();
            model.addAttribute("allProduit", allProduit);
            return "Fabrication";
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

    @PostMapping("/InitFabrication")
    public String saveOrUpdate(Model model, @RequestParam(required = false) String id,  @RequestParam String date_, @RequestParam String qt_produit, @RequestParam String produit, @RequestParam(required = false) String mode) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            Fabrication instance = new Fabrication();
            instance.setDate_(date_) ; 
            instance.setQt_produit(qt_produit) ; 
            instance.setProduit(produit) ;
            if (mode != null && "u".equals(mode)) {
                instance.setId(id);
                instance.update(con);
            } else {
                instance.insert(con);
            }
            return "redirect:/InitFabrication";
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

    @GetMapping("/InitFabrication/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        Connection con = null;
        try {
            Fabrication.deleteById(id);
            return "redirect:/InitFabrication";
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

    @GetMapping("/TraitFabrication/{id}")
    public String editForm(Model model, @PathVariable int id) {
        Connection con = null;
        try {
            Fabrication currentFabrication = Fabrication.getById(id);
            model.addAttribute("currentFabrication", currentFabrication);
            model.addAttribute("all", Fabrication.getAll());
            Produit[] allProduit = Produit.getAll();
            model.addAttribute("allProduit", allProduit);
            return "Fabrication";
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
