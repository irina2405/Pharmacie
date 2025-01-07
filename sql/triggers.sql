-- Création de la fonction de déclenchement
CREATE OR REPLACE FUNCTION set_reste_mp_before_insert()
RETURNS TRIGGER AS $$
BEGIN
   -- Initialiser reste_mp avec la valeur de qt_mp
   NEW.reste_mp := NEW.qt_mp;
   RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Création du trigger associé à la table achat_mp
CREATE TRIGGER before_insert_set_reste_mp
BEFORE INSERT ON achat_mp
FOR EACH ROW
EXECUTE FUNCTION set_reste_mp_before_insert();