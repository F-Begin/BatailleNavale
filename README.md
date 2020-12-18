# BatailleNavale
Projet bataille navale pour le cours de Programmation Réseaux à Polytech Nancy

## Disclamers

Cette bataille navale est aussi fonctionnelles (Certains petit "fix" qui sont présents sur la version de base ne sont pas encore présents sur cette version : Nottament la gestion de coordonnées éronnées que pourrait donner l'utilisateur).

Cette version est encore instable évidemment.

Si vous voulez essayer la base de données, vous serez obligé de l'essayer en mode Database et non en mode Json, le mode Json n'est pas encore fonctionnel. (Répondre oui à la question "Utiliser une base de donnée ?").
La partie base de donnée à été testé et à retourné un résultat concluant avec une BDD crée sous PhpMyAdmin avec WampServer (Serveur MySQL).

## TodoList des choses à faire :
- [x] Base de donnée MySQL
- [ ] Alternative JSON base de donnée (-> WiP <-)
- [ ] Interpréteur 
- [ ] "Coulé" pour les bâteaux -> Utilisation des state
- [ ] Liste d'ami
- [ ] Chat
- [ ] Duel d'un joueur précis
- [ ] LadderBoard (Classement -> ELO)
- [ ] Commandes Administrateur
- [ ] Commandes Joueurs
- [ ] Auto-modération -> Flag et enregistrement de log pour actions suspectes
- Liste non exhaustives et qui pourra être sujet à changement

## Qu'est ce que tout cela ? 

### La base de donnée MySQL
Une base de donnée qui sera utilisée, rien de plus.
Les mots de passe sont hashé en MD5 pour plus de sécurité.

### Alternative JSON
Proposer une alternative à ceux ne désirant pas utiliser de Base de donnée. Les données seront alors stocké dans un fichier .Json.
Utilisation de l'API Jackson

### Interpréteur
Au lieu que le serveur envoie de maniere brute les informations au client il n'enverra qu'un objet ou un String contenant les arguments de sa demande, le client recoit ca et gere la requete en local pour ensuite renvoyer un objet Réponse.

Exemple objet pour une connexion (Question) :
ObjetQuestion {
  String Type = "Connexion"
}

Exemple String pour une connexion (Question) :
String question = "Connexion;"

L'utilisateur recevra alors cela et le traitera en local pour renvoyer un réponse un objet ou un String de la sorte : 

Exemple objet pour une connexion (Réponse) :
ObjetReponse {
  String Type : "Connexion"
  ArrayList<Strig> = {Identifiant;MotDePasseMD5}
}
  
Exemple String pour une connexion (Réponse) :
String reponse = "Connexion;Identifiant;MotDePasseMD5"

### Coûlé

Utilisation des states des objets bateaux pour annoncer quand ceux-ci sont coulés.

### Liste d'ami

Liste d'ami pour chaque utilisateur

### Chat

Chat global, de groupe et privé avec les ami dans la liste d'ami de l'utilisateur.

### Duel d'un joueur précis

Pouvoir chercher un joueur par plusieurs critère (Nom, ELO, etc...) et pouvoir le défier.

### LadderBoard

Classement par ELO

### Commandes Administrateurs

Un admin devra pouvoir : 
- Regarder les parties qui se jouent.
- Bannir un joueur.
- Envoyer un avertissement à un joueur.
- Ban Temp un joueur.
- Annuler une partie.
- Acceder aux logs des parties.
- Surement d'autres choses...

### Commandes Joueurs 

Un joueur devra pouvoir : 

- Abandonner
- Report un joueur

### Auto-modération

Flag les parties suspectes ou les joueurs suspects pour enregistrer les logs de ces derniers pour qu'un admin puisse ensuite analyser les logs et comprendre ce qu'il se passe.
