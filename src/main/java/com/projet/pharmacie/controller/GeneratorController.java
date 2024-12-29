package com.projet.pharmacie.controller;
import com.projet.pharmacie.model.*;




import java.sql.Connection;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class GeneratorController {

    @GetMapping("/")
    public String sayHello() {
        return "index";
    }
    @GetMapping("/V_depense_mp_sur_produit")
    public String getV_depense_mp_sur_produit(Model model) {
        try {
            Connection con = com.projet.pharmacie.db.MyConnect.getConnection();
            model.addAttribute("V_depense_mp_sur_produit", V_depense_mp_sur_produit.getAll(con));
            return "V_depense_mp_sur_produit";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("eMessage", e.getMessage() + (e.getCause() != null ? "<br> <hr>" + e.getCause().getMessage() : "") );
            return "Error";
        }
    }

}
