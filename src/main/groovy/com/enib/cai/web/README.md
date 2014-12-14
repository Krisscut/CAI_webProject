# Back-end

*Détails des API*

## Worms API



```html
http://localhost:8080/api/worms/getAll
```

```html
http://localhost:8080/api/worms/get;_id ( replace _id with the id of the worm you want to select )
```

##Equipements API

```html
http://localhost:8080/api/equipments/getAll
```

```html
http://localhost:8080/api/equipments/get;_id
```

##Bonuses API

```html
http://localhost:8080/api/bonuses/getAll
```

```html
http://localhost:8080/api/bonuses/get;_id
```

## Users API

L'api users est plus complexe que les autres, car elle permet d'identifier une personne, mais aussi de vérifier si sa session n'est pas expirée, etc

### Créer un compte
Pour crée un compte il faut effectuer une requête get sur l'url ci dessous, en envoyant les paramètres sous forme de header
```html
http://localhost:8080/api/users/createAccount

Headers :
    username
    password
    email
```

### Se connecter à un compte
Pour se connecter à un compte, il faut envoyer les bonnes informations sur l'url ci-dessous.
Si la bonne combinaison est envoyée, un token est renvoyé.
Valable une heure, ( se renouvelle chaque fois qu'on l'utilise), il permet à l'utilisateur d'effectuer des actions liés à son compte, il doit donc le stocker côté client pour le ré-utiliser

```html
http://localhost:8080/api/users/login

Headers :
    username
    password
```

### Se déconnecter à un compte
Permet de volontairement détruire le token de la base de donnée, pour clore une connexion. ( si le token expire il sera aussi retiré automatiquement )

```html
http://localhost:8080/api/users/logout

Headers :
    token
```

### Renouveler un accès
Permet de savoir si un token est valide ( non expiré ), il renouvelle aussi le token pendant une heure. ( sert pour toutes les opérations de commande )

```html
http://localhost:8080/api/users/authorize

Headers :
    token
```