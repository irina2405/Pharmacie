create or replace VIEW v_achat_total_mp as
   select id_mp, sum(qt_mp) qt_total_mp 
   from achat_mp join fournisseur_mp on achat_mp.id_fournisseur_mp = fournisseur_mp.id
   group by id_mp; 