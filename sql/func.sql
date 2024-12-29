---------------------------- MP
create or replace VIEW v_achat_total_mp as
   select id_mp, sum(qt_mp) qt_total_mp 
   from achat_mp join fournisseur_mp on achat_mp.id_fournisseur_mp = fournisseur_mp.id
   group by id_mp; 

create or replace VIEW v_depense_mp_sur_produit AS
    select id_mp, fabrication.id_produit ,qt_produit, qt_mp, date_ 
    FROM fabrication join Formule
    on fabrication.id_produit = Formule.id_produit

create or replace VIEW v_depense_total_mp AS
    select id_mp, sum(qt_produit*qt_mp) as qt_mp_depense
    from v_depense_mp_sur_produit
    GROUP by id_mp;

create or REPLACE VIEW MP_with_qt as
    select mp.* , COALESCE (qt_total_mp,0) qt_total_mp_achat, COALESCE(qt_mp_depense,0) as qt_total_mp_depense
    from matiere_premiere  mp left join v_achat_total_mp on mp.id = v_achat_total_mp.id_mp
    left join v_depense_total_mp on mp.id = v_depense_total_mp.id_mp;


--------------------------------- produit
create or replace VIEW v_qt_produite_du_produit AS
    select id_produit , sum(qt_produit) qt_total_du_produit
    from fabrication group by id_produit;

create or replace view v_qt_achete_produit AS
    select id_produit , sum(qt_produit) qt_total_produite_produit
    from achat_produit join produit_fournisseur on achat_produit.id_produit_fournisseur = produit_fournisseur.id
    group by id_produit;

create or replace VIEW v_qt_vendu AS
    select id_produit , sum(qt_produit) as qt_total_vendu 
    from detail_facture 
    group by id_produit

create or REPLACE VIEW Produit_with_qt AS
    select produit.* , COALESCE(qt_total_du_produit,0) qt_total_du_produit,
    COALESCE(qt_total_produite_produit,0) qt_total_produite_produit,
    COALESCE(qt_total_vendu,0) qt_total_vendu
    from produit left join v_qt_produite_du_produit on produit.id = v_qt_produite_du_produit.id_produit 
    left join v_qt_achete_produit ON produit.id = v_qt_achete_produit.id_produit
    left join v_qt_vendu on produit.id = v_qt_vendu.id_produit ;