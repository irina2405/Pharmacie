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
public class FactureController {

    @GetMapping("/InitFacture")
    public String showAll(Model model) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            model.addAttribute("all", Facture.getAll());
            Client[] allClient = Client.getAll();
            model.addAttribute("allClient", allClient);
            return "Facture";
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

    @PostMapping("/InitFacture")
    public String saveOrUpdate(Model model, @RequestParam(required = false) String id,  @RequestParam String date_, @RequestParam String total, @RequestParam String total_paye, @RequestParam String client, @RequestParam(required = false) String mode) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            Facture instance = new Facture();
            instance.setDate_(date_) ; 
            instance.setTotal(total) ; 
            instance.setTotal_paye(total_paye) ; 
            instance.setClient(client, con) ;
            if (mode != null && "u".equals(mode)) {
                instance.setId(id);
                instance.updateUncommitted(con);
            } else {
                instance.insertUncommitted(con);
            }
            return "redirect:/InitFacture";
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

    @GetMapping("/InitFacture/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        Connection con = null;
        try {
            Facture.deleteById(id);
            return "redirect:/InitFacture";
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

    @GetMapping("/TraitFacture/{id}")
    public String editForm(Model model, @PathVariable int id) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            Facture currentFacture = Facture.getById(id ,con);
            model.addAttribute("currentFacture", currentFacture);
            model.addAttribute("all", Facture.getAll());
            Client[] allClient = Client.getAll();
            model.addAttribute("allClient", allClient);
            return "Facture";
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
