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
public class FormuleController {

    @GetMapping("/InitFormule")
    public String showAll( @RequestParam(required = false)String search ,@RequestParam(required = false) String mp,@RequestParam(required = false) String produit,@RequestParam(required = false) String qt_mp_min,@RequestParam(required = false) String qt_mp_max ,Model model) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            if (search!=null && !search.isEmpty()) {
                model.addAttribute("all", Formule.search(mp, produit, qt_mp_min , qt_mp_max));
            }else{
                model.addAttribute("all", Formule.getAll());
            }
            Matiere_premiere[] allMp = Matiere_premiere.getAll();
            model.addAttribute("allMp", allMp);
            Produit[] allProduit = Produit.getAll();
            model.addAttribute("allProduit", allProduit);
            return "Formule";
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

    @PostMapping("/InitFormule")
    public String saveOrUpdate(Model model, @RequestParam(required = false) String id,  @RequestParam String mp, @RequestParam String produit, @RequestParam String qt_mp, @RequestParam(required = false) String mode) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            Formule instance = new Formule();
            instance.setMp(mp) ;
            instance.setProduit(produit) ;
            instance.setQt_mp(qt_mp) ; 
            if (mode != null && "u".equals(mode)) {
                instance.setId(id);
                instance.update(con);
            } else {
                instance.insert(con);
            }
            return "redirect:/InitFormule";
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

    @GetMapping("/InitFormule/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        Connection con = null;
        try {
            Formule.deleteById(id);
            return "redirect:/InitFormule";
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

    @GetMapping("/TraitFormule/{id}")
    public String editForm(Model model, @PathVariable int id) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            Formule currentFormule = Formule.getById(id ,con);
            model.addAttribute("currentFormule", currentFormule);
            model.addAttribute("all", Formule.getAll());
            Matiere_premiere[] allMp = Matiere_premiere.getAll();
            model.addAttribute("allMp", allMp);
            Produit[] allProduit = Produit.getAll();
            model.addAttribute("allProduit", allProduit);
            return "Formule";
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
