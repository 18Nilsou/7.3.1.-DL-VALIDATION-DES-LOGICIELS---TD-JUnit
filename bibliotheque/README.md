# 7.3.1 DL-VALIDATION-DES-LOGICIELS

Création d'un simulateur de bibliothèque en TDD.

## GitHub 

Première partie : [tests](https://github.com/18Nilsou/7.3.1.-DL-VALIDATION-DES-LOGICIELS---TD-JUnit/tree/test) <br>
Deuxième partie : [implémentation](https://github.com/18Nilsou/7.3.1.-DL-VALIDATION-DES-LOGICIELS---TD-JUnit/tree/main)


## Lancer les tests

Les tests initiaux se trouvent dans `LibrairyMockTest.java`<br>
Les tests après écriture du code se trouvent au niveau de `LibrairyTest.java`<br>

Lancer les tests :
```bash
mvn test
```

## Rapport de couverture JaCoCo

Après avoir lancé les tests, un rapport de couverture de code est généré par JaCoCo.
Vous pouvez le consulter dans le fichier `target/site/jacoco/index.html`.
Ouvrez ce fichier dans votre navigateur pour voir le détail de la couverture.

## Choix techniques

- Utilisations d'interfaces pour simuler l'existence de code afin d'écrire les tests.
- Création d'un Enum pour représenter l'état des livres dans le processus d'emprunt.