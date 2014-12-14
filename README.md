# CAIwebProject

Projet réalisé dans le cadre du module CAI ( conception Application intéractives ) à l'ENIB.
Ceci est un site qui utilise vertX en back-end, angularJS en front-end, qui permet d'acheter/commander des worms, tout en les équipants de bonus ou d'armes particulières.

#Technologies Utilisées
- Front-end : AngularJS, Twitter bootstrap
- Back-end : VertX avec Groovy, MongoDb

Pour lancer le module, faire un
gradlew build

puis un
gradlew runMod

Attention, il faut qu'une instance de mongoDb soit lancée ( avec les paramètres par defaut ).
Si besoin le fichier conf.json permet de modifier les paramètres de mongo.

Le site sera alors disponible ( par defaut ) ici : http://localhost:8080/worms.html

Pour transmettre les données depuis le back-end vers le front-end, une API restfull est disponible sur l'adresse
http://localhost:8080/api

plus d'infos ici:
https://github.com/Krisscut/CAI_webProject/tree/DEV/src/main/groovy/com/enib/cai/web
