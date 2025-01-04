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
            con = MyConnect.getConnection();
            model.addAttribute("all", Achat_mp.getAll());
            Fournisseur_mp[] allFournisseur_mp = Fournisseur_mp.getAllDistinct();
            model.addAttribute("allFournisseur_mp", allFournisseur_mp);
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
    public String saveOrUpdate(Model model, @RequestParam(required = false) String id,  @RequestParam String date_, @RequestParam String qt_mp, @RequestParam String fournisseur_mp, @RequestParam(required = false) String mode) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            Achat_mp instance = new Achat_mp();
            instance.setDate_(date_) ; 
            instance.setQt_mp(qt_mp) ;
            Fournisseur_mp fournisseur_mp_ = Fournisseur_mp.getById(Integer.parseInt(fournisseur_mp), con);
            Fournisseur_mp correspondant = Fournisseur_mp.getCorrespondant (fournisseur_mp_.getFournisseur(),fournisseur_mp_.getMp(), date_  ); 
            instance.setFournisseur_mp(correspondant) ;
            if (mode != null && "u".equals(mode)) {
                instance.setId(id);
                instance.update(con);
            } else {
                instance.insertUncommitted(con);
                Double retrait = correspondant.getPrix()*Double.parseDouble(qt_mp);
                Tresorerie tresorerie = new Tresorerie("achat mp",date_, "0", retrait.toString());
                tresorerie.retirer(con);
            }
            con.commit();
            return "redirect:/InitAchat_mp";
        } catch (Exception e) {
            try {
                con.rollback();
            } catch (Exception ignored) {System.out.println("ignored e ...............");}
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
            con = MyConnect.getConnection();
            Achat_mp currentAchat_mp = Achat_mp.getById(id ,con);
            model.addAttribute("currentAchat_mp", currentAchat_mp);
            model.addAttribute("all", Achat_mp.getAll());
            Fournisseur_mp[] allFournisseur_mp = Fournisseur_mp.getAll();
            model.addAttribute("allFournisseur_mp", allFournisseur_mp);
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
