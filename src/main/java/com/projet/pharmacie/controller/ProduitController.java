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
public class ProduitController {

    @GetMapping("/InitProduit")
    public String showAll(Model model,  @RequestParam(required = false)String min_age, @RequestParam(required = false) String max_age, @RequestParam(required = false) String id ) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            if (min_age!=null || max_age!=null) {
                model.addAttribute("all", Produit.search(min_age,max_age, id));
            }else{
                model.addAttribute("all", Produit.getAll());
            }
            Unite[] allUnite = Unite.getAll();
            model.addAttribute("allUnite", allUnite);
            return "Produit";
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

    // @PostMapping("/InitProduit")
    // public String saveOrUpdate(Model model, @RequestParam(required = false) String id,  @RequestParam String nom, @RequestParam String denorm_prix_vente, @RequestParam String unite, @RequestParam(required = false) String mode) {
    //     Connection con = null;
    //     try {
    //         con = MyConnect.getConnection();
    //         Produit instance = new Produit();
    //         instance.setNom(nom) ; 
    //         instance.setDenorm_prix_vente(denorm_prix_vente) ; 
    //         instance.setUnite(unite,con) ;
    //         if (mode != null && "u".equals(mode)) {
    //             instance.setId(id);
    //             instance.update(con);
    //         } else {
    //             instance.insert(con);
    //         }
    //         return "redirect:/InitProduit";
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         model.addAttribute("eMessage", e.getMessage() + (e.getCause() != null ? "<br> <hr>" + e.getCause().getMessage() : "") ); 
    //         return "Error";
    //     } finally {
    //         if (con != null) {
    //             try { con.close(); } catch (Exception ignored) {}
    //         }
    //     }
    // }

    // @GetMapping("/InitProduit/delete/{id}")
    // public String delete(Model model, @PathVariable int id) {
    //     Connection con = null;
    //     try {
    //         Produit.deleteById(id);
    //         return "redirect:/InitProduit";
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         model.addAttribute("eMessage", e.getMessage() + (e.getCause() != null ? "<br> <hr>" + e.getCause().getMessage() : "") ); 
    //         return "Error";
    //     } finally {
    //         if (con != null) {
    //             try { con.close(); } catch (Exception ignored) {}
    //         }
    //     }
    // }

    // @GetMapping("/TraitProduit/{id}")
    // public String editForm(Model model, @PathVariable int id) {
    //     Connection con = null;
    //     try {
    //         con = MyConnect.getConnection();
    //         Produit currentProduit = Produit.getById(id ,con);
    //         model.addAttribute("currentProduit", currentProduit);
    //         model.addAttribute("all", Produit.getAll());
    //         Unite[] allUnite = Unite.getAll();
    //         model.addAttribute("allUnite", allUnite);
    //         return "Produit";
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         model.addAttribute("eMessage", e.getMessage() + (e.getCause() != null ? "<br> <hr>" + e.getCause().getMessage() : "") ); 
    //         return "Error";
    //     } finally {
    //         if (con != null) {
    //             try { con.close(); } catch (Exception ignored) {}
    //         }
    //     }
    // }

    // @GetMapping("/maladies_concernes_par_produit/{id_produit}")
    // public String maladies_concernes_par_produit(Model model , @PathVariable("id_produit") int id_produit) {
    //     Connection con = null;
    //     try {
    //         con = MyConnect.getConnection();
    //         Produit produit = Produit.getById(id_produit, con);
    //         Maladie[] maladies_concernes_par_produit = produit.getMaladiesConcernes ();
    //         model.addAttribute("all", maladies_concernes_par_produit);
    //         return "maladies_concernes_par_produit";
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         model.addAttribute("eMessage", e.getMessage() + (e.getCause() != null ? "<br> <hr>" + e.getCause().getMessage() : "") ); 
    //         return "Error";
    //     } finally {
    //         if (con != null) {
    //             try { con.close(); } catch (Exception ignored) {}
    //         }
    //     }

    // }
    // @GetMapping("/mp_concernes_par_produit/{id_produit}")
    // public String mp_concernes_par_produit(Model model , @PathVariable("id_produit") int id_produit) {
    //     Connection con = null;
    //     try {
    //         con = MyConnect.getConnection();
    //         Produit produit = Produit.getById(id_produit, con);
    //         Matiere_premiere[] mp_concernes_par_produit = produit.getMatiere_premieresConcernes ();
    //         model.addAttribute("all", mp_concernes_par_produit);
    //         return "mp_concernes_par_produit";
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         model.addAttribute("eMessage", e.getMessage() + (e.getCause() != null ? "<br> <hr>" + e.getCause().getMessage() : "") ); 
    //         return "Error";
    //     } finally {
    //         if (con != null) {
    //             try { con.close(); } catch (Exception ignored) {}
    //         }
    //     }

    // }

    @PostMapping("/InitProduit")
    public String saveOrUpdate(Model model, @RequestParam(required = false) String id,  @RequestParam String nom, @RequestParam String denorm_prix_vente, @RequestParam String min_age, @RequestParam String max_age, @RequestParam String unite, @RequestParam(required = false) String mode) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            Produit instance = new Produit();
            instance.setNom(nom) ; 
            instance.setDenorm_prix_vente(denorm_prix_vente) ; 
            instance.setMin_age(min_age) ; 
            instance.setMax_age(max_age) ; 
            instance.setUnite(unite,con ) ;
            if (mode != null && "u".equals(mode)) {
                instance.setId(id);
                instance.update(con);
            } else {
                instance.insert(con);
            }
            return "redirect:/InitProduit";
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

    @GetMapping("/InitProduit/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        Connection con = null;
        try {
            Produit.deleteById(id);
            return "redirect:/InitProduit";
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

    @GetMapping("/TraitProduit/{id}")
    public String editForm(Model model, @PathVariable int id) {
        Connection con = null;
        try {
            con = MyConnect.getConnection();
            Produit currentProduit = Produit.getById(id ,con);
            model.addAttribute("currentProduit", currentProduit);
            model.addAttribute("all", Produit.getAll());
            Unite[] allUnite = Unite.getAll();
            model.addAttribute("allUnite", allUnite);
            return "Produit";
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
