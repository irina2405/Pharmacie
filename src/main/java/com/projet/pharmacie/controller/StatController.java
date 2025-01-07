package com.projet.pharmacie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.projet.pharmacie.db.MyConnect;
import com.projet.pharmacie.model.*;
import com.projet.pharmacie.util.Stat;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

@Controller
public class StatController {
    @GetMapping ("stat")
    public String stat (){
        return "stat";
    }
    @GetMapping("statVente")
    @ResponseBody
    public Stat statVente(
            @RequestParam(required = false) String date_start,
            @RequestParam(required = false) String date_end) {
        Vector<String> suivantX = new Vector<>();
        Vector<Double> suivantY = new Vector<>();
        try (Connection con = MyConnect.getConnection()) {
            List<V_stat_vente> result = V_stat_vente.getAllBetween(con, date_start, date_end);

            for (V_stat_vente v_stat_vente : result) {
                suivantX.add((int)v_stat_vente.getAnnee()+"/"+ (int)v_stat_vente.getMois());
                suivantY.add(v_stat_vente.getCa());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Stat(suivantX, suivantY);
    }
    
}
