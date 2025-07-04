INSERT INTO authorities (customer_id, name)
    VALUES (1, 'read'),
           -- Le second utilisateur est un administrateur : donnons-lui tous les droits
           (2, 'read'),
           (2, 'write'),
           (2, 'delete');