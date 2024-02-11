# Mon Vieux Grimoire - Java edition

J'ai décidé de réécrire le backend du [projet 7](https://github.com/SamiNassim/OCProject7) de ma formation OpenClassrooms (NodeJS / ExpressJS) entièrement en Java. (Spring Boot)</br>
Pour rappel, ce projet de ma formation était un projet entièrement porté sur la création de la partie backend du site, le côté frontend étant déjà créé.</br>
Une fois le projet terminé, le frontend devait pouvoir se connecter parfaitement au backend créé pour avoir un site parfaitement fonctionnel.</br>
Lien du projet original : https://github.com/SamiNassim/OCProject7

## Pourquoi refaire ce projet en Java ?

Pour me familiariser avec Spring Boot. En effet, c'est mon tout premier projet Spring Boot entièrement codé de A à Z. </br>
J'ai commencé à adapter ce projet en ayant pour but d'avoir les mêmes fonctionnalités que la version originale sans pour autant pouvoir connecté cette version à la partie frontend.</br>
Au fur et à mesure de mon avancé, et une fois la majorité des fonctionnalités implémentés, j’ai finalement décidé d’adapter l’API pour avoir une connexion au frontend fonctionnelle.</br>
Le challenge était que, malgré les obstacles, je ne devais pas modifier le code original de la partie frontend. (Ecrite donc par OpenClassrooms)</br>
Cette restriction a donnée parfois des changements importants / peu commun par rapport à la version "sans frontend".

## Obstacles rencontrés

Le premier obstacle était bien sûr le type de base de donnée. Dans le projet original, la base de donnée était MongoDB. (NoSQL)</br>
J'ai choisi PostgreSQL pour ma version du projet.</br>
J'ai dû donc créé des relations entre mes entités. (Ex: Book et BookRating)

MVG :</br>
<img width="498" alt="Screenshot 2024-02-11 at 04 48 20" src="https://github.com/SamiNassim/mvg-java/assets/125296128/5cb29321-6677-4905-be30-b4387203a5c3"></br>

MVG-Java :</br>
<img width="484" alt="Screenshot 2024-02-11 at 04 49 25" src="https://github.com/SamiNassim/mvg-java/assets/125296128/14a8de09-816e-40ab-866f-8df9c0dc0fcc"></br>

Un autre obstacle majeur était d'adapter de traiter certaines requêtes du frontend qui pouvait être différentes selon certains scénarios.</br>
Dans l'exemple suivant, lorsque l'on modifie un livre sans modifier l'image (uniquement le texte donc), la requête est au format JSON.</br>
Lorsque l'on modifie l'image, ou le texte + l'image, la requête est au format FormData.</br>
J'ai essayé plusieurs techniques (@ModelAttribute pour formdata et @RequestBody pour le JSON sur le même controller) mais sans succès.</br>
Finalement, j'ai créé 2 controllers pour le même endpoint en fonction du format de la requête.</br>

MVG : (frontend)</br>
<img width="558" alt="Screenshot 2024-02-11 at 04 58 13" src="https://github.com/SamiNassim/mvg-java/assets/125296128/89d37dfd-1732-4845-b702-3de68617de1b"></br>

MVG-Java :</br>
<img width="883" alt="Screenshot 2024-02-11 at 04 59 37" src="https://github.com/SamiNassim/mvg-java/assets/125296128/13e3ea9a-77f4-4783-9aa0-806f40936ace"></br>

