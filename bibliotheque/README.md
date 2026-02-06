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

## Choix techniques

- Utilisations d'interfaces pour simuler l'existence de code afin d'écrire les tests.
- Création d'un Enum pour représenter l'état des livres dans le processus d'emprunt.