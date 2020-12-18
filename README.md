# BatailleNavale
Projet bataille navale pour le cours de Programmation Réseaux à Polytech Nancy

## Disclamers
Le projet est totalement fonctionnels, bien que des petits bugs non trouvés peuvent toujours apparaître au grand jour.

Ce projet était finit depuis longtemps effectivement, mais je n'ai pas rien fait pour autant.
En effet, je travaille actuellement sur la version Full de cette bataille navale, et j'ai décidé de commencer ce travaille par le plus gros du projet : La base de donnée.
Le projet full dans son état actuel est disponible ici : https://github.com/F-Begin/BatailleNavale/tree/Version-Full
(Il s'agit de la branche Version-Full)
Les details de cette branche sont disponibles dans le README de cette dernière si vous souhaitez l'essayer.

Enfin, je continuerais de travailler sur ce projet après sa date butoir car je compte implémenter cette bataille navale dans le projet Web que nous devons remettre pour fin Janvier. Cette dernière aura une interface graphique pour navigateur web et sera reliée à une base de donnée (Sur laquelle je travaille actuellement).

## Principe de fonctionnement

### Serveur : 
1. Démarrage de MainServer.class
2. Démarrage du ThreadGestion qui lancera les parties des joueurs en attente toutes les 10 secondes.
3. Entrée dans la boucle while(true) pour accepter les joueurs quand ils se connectent et en faire des objet User.
4. Le User est envoyé dans un ThreadLobby qui lui permet de se mettre en recherche de partie ou de se déconnecter.
5. Une fois que le User est en recherche de partie et qu'une partie est trouvée, le Thread Lobby du User est détruit et il est envoyé dans un ThreadGame.
6. Dans le ThreadGame l'Objet Game va être instancié est la partie va débuter.
7. Les joueurs place tour à tour leurs bateaux (Les coordonnées incorrectes sont gérées).
8. Les joueurs attaque à tour de rôle (Les coordonnées incorrectes sont gérées).
9. Un joueur gagne la partie.
10. Les deux joueurs sont notifiés de la fin de la partie et du gagant, ensuite leur ThreadGame est détruit et ils envoyés dans des ThreadLobby individuels.
11. Retour au 4.
