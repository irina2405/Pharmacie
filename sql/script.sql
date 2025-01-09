CREATE DATABASE pharmacie;
\c pharmacie;
CREATE TABLE Fournisseur(
   id SERIAL,
   nom VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE unite(
   id SERIAL,
   nom VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE maladie(
   id SERIAL,
   nom VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE tresorerie(
   id SERIAL,
   motif VARCHAR(50) default '',
   date_ TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   depot NUMERIC(15,2) CHECK (depot >= 0)  ,
   retrait NUMERIC(15,2) CHECK (retrait >=0) ,
   PRIMARY KEY(id)
);

CREATE TABLE client(
   id SERIAL,
   nom VARCHAR(50) ,
   PRIMARY KEY(id)
);

CREATE TABLE matiere_premiere(
   id SERIAL,
   nom VARCHAR(50)  NOT NULL,
   id_unite INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_unite) REFERENCES unite(id)
);

CREATE TABLE produit(
   id SERIAL,
   nom VARCHAR(50)  NOT NULL,
   denorm_prix_vente NUMERIC(15,2)   NOT NULL CHECK (denorm_prix_vente>=0),
   min_age INTEGER,
   max_age INTEGER,
   id_unite INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_unite) REFERENCES unite(id)
);

CREATE TABLE fabrication(
   id SERIAL,
   date_ TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   qt_produit NUMERIC(15,2)   NOT NULL  CHECK (qt_produit>=0),
   cout NUMERIC(15,2)  ,
   id_produit INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_produit) REFERENCES produit(id)
);


CREATE TABLE fournisseur_mp(
   id SERIAL PRIMARY KEY,
   id_mp INTEGER NOT NULL ,
   id_fournisseur INTEGER NOT NULL ,
   prix NUMERIC(15,2)  DEFAULT 0.00 CHECK (prix >= 0),
   date_ DATE DEFAULT CURRENT_DATE,
   FOREIGN KEY(id_mp) REFERENCES matiere_premiere(id),
   FOREIGN KEY(id_fournisseur) REFERENCES Fournisseur(id)
);


CREATE TABLE achat_mp(
   id SERIAL,
   date_ TIMESTAMP NOT NULL,
   qt_mp NUMERIC(15,2)   NOT NULL CHECK(qt_mp >= 0),
   reste_mp NUMERIC(15,2) CHECK(reste_mp >= 0) ,
   id_fournisseur_mp INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_fournisseur_mp) REFERENCES Fournisseur_mp(id)
);

CREATE TABLE facture(
   id SERIAL,
   date_ TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   total NUMERIC(15,2)   NOT NULL CHECK (total >= 0),
   total_paye NUMERIC(15,2)   NOT NULL CHECK (total_paye >= 0),
   id_client INTEGER,
   PRIMARY KEY(id),
   FOREIGN KEY(id_client) REFERENCES client(id)
);

CREATE TABLE histo_prix_produit(
   id SERIAL,
   date_ DATE NOT NULL DEFAULT CURRENT_DATE,
   prix_vente_produit NUMERIC(15,2)  NOT NULL  CHECK (prix_vente_produit>=0),
   id_produit INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_produit) REFERENCES produit(id)
);

CREATE TABLE produit_fournisseur(
   id SERIAL PRIMARY KEY,
   id_fournisseur INTEGER NOT NULL ,
   id_produit INTEGER NOT NULL ,
   date_ DATE DEFAULT CURRENT_DATE,
   prix NUMERIC(15,2)   NOT NULL DEFAULT 0.00,
   FOREIGN KEY(id_fournisseur) REFERENCES Fournisseur(id),
   FOREIGN KEY(id_produit) REFERENCES produit(id)
);

CREATE TABLE achat_produit(
   id SERIAL,
   date_ TIMESTAMP NOT NULL,
   qt_produit NUMERIC(15,2)   NOT NULL CHECK (qt_produit >=0 ),
   id_produit_fournisseur INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_produit_fournisseur) REFERENCES produit_fournisseur(id)
);

CREATE TABLE Formule(
   id SERIAL PRIMARY KEY,
   id_mp INTEGER NOT NULL ,
   id_produit INTEGER NOT NULL ,
   qt_mp NUMERIC(15,2)   NOT NULL  CHECK(qt_mp>=0),
   FOREIGN KEY(id_mp) REFERENCES matiere_premiere(id),
   FOREIGN KEY(id_produit) REFERENCES produit(id)
);

CREATE TABLE maladie_produit(
   id SERIAL PRIMARY KEY,
   id_produit INTEGER NOT NULL ,
   id_maladie INTEGER NOT NULL ,
   FOREIGN KEY(id_produit) REFERENCES produit(id),
   FOREIGN KEY(id_maladie) REFERENCES maladie(id)
); 

CREATE TABLE detail_facture(
   id SERIAL PRIMARY KEY,
   id_produit INTEGER NOT NULL ,
   id_facture INTEGER NOT NULL ,
   denorm_prix_vente NUMERIC(15,2)   NOT NULL CHECK (denorm_prix_vente >=0),
   qt_produit NUMERIC(15,2)   NOT NULL CHECK (qt_produit >= 0),
   FOREIGN KEY(id_produit) REFERENCES produit(id),
   FOREIGN KEY(id_facture) REFERENCES facture(id)
);

