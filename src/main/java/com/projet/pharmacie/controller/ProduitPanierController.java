package com.projet.pharmacie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.projet.pharmacie.db.MyConnect;
import com.projet.pharmacie.model.*;

import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.HashMap;

@Controller
public class ProduitPanierController {

    @GetMapping("/InitAchatPanier")
    public String achatPanier (Model model){
        try {
            model.addAttribute("all_produit", Produit.getAll());
            model.addAttribute("all_client", Client.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "achat_panier";
    }

    @PostMapping("/mise_en_panier")
    public String ajouterAuPanier(@RequestParam int idProduit, @RequestParam double quantite, HttpSession session) {
        HashMap<Integer, Produit> panier = (HashMap<Integer, Produit>) session.getAttribute("panier");
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            if (panier == null) {
                panier = new HashMap<>();
            }

            Produit produit = Produit.getById(idProduit, con);
            if (produit != null) {
                if (panier.containsKey(idProduit)) {
                    Produit existant = panier.get(idProduit);
                    existant.setQtPanier(existant.getQtPanier() + quantite);
                } else {
                    produit.setQtPanier(quantite);
                    panier.put(idProduit, produit);
                }
            }else{
                System.out.println("Erreur de manipulation ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if (con!=null) con.close();
            } catch (Exception e) {
            }
        }
        session.setAttribute("panier", panier);
        return "redirect:/InitAchatPanier";
    }

    @PostMapping("/elever_du_panier")
    public String enleverDuPanier(@RequestParam int idProduit, HttpSession session) {
        HashMap<Integer, Produit> panier = (HashMap<Integer, Produit>) session.getAttribute("panier");
        if (panier != null) {
            panier.remove(idProduit);
            session.setAttribute("panier", panier);
        }
        return "redirect:/InitAchatPanier";
    }

    @GetMapping("/valider_panier")
    public String validerPanier(@RequestParam(required = false) String id_client, @RequestParam(required = false) Double total_paye ,HttpSession session,Model model) {
        HashMap<Integer, Produit> panier = (HashMap<Integer, Produit>) session.getAttribute("panier");
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            // Exemple d'insertion dans une facture
            Facture facture = new Facture();
            facture.setDate_(new Timestamp(System.currentTimeMillis()));
            facture.setClient(id_client);
            facture.setTotal(Produit.getTotal(panier));
            if (id_client==null || id_client.isEmpty()) {
                facture.setTotal_paye(facture.getTotal());
            }else{
                facture.setTotal_paye(total_paye);
            }
            facture.insertUncommitted(con);

            for (Produit produit : panier.values()) {
                Detail_facture detail = new Detail_facture();
                detail.setProduit(produit);
                detail.setFacture(facture);
                detail.setDenorm_prix_vente(produit.getDenorm_prix_vente());
                detail.setQt_produit(produit.getQtPanier());
                detail.insert(con);
            }
            Tresorerie tresorerie = new Tresorerie("vente a : "+(facture.getClient()==null?" divers": facture.getClient().getNom()), (new Timestamp(System.currentTimeMillis())).toString(), total_paye.toString(), "0");
            tresorerie.insertUncommitted(con);
            con.commit();
            session.removeAttribute("panier");
            
        } catch (Exception e) {
            try {
                con.rollback();
            } catch (Exception ign) {
            }
            e.printStackTrace();
            model.addAttribute("eMessage", e.getMessage() + (e.getCause() != null ? "<br> <hr>" + e.getCause().getMessage() : "") );
            return "Error";
        }finally{
            try {
                if (con!=null) con.close();
            } catch (Exception e) {
            }
        }
        
        return "redirect:/";
    }

    @GetMapping("/payer_en_totalite/{id_facture}")
    public String payer_en_totalite (@PathVariable int id_facture){
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            Facture facture = Facture.getById(id_facture, con);
            facture.payertotalement();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if (con!=null) con.close();
            } catch (Exception e) {
            }
        }
        return "redirect:/V_facture_impaye";
    }
}
