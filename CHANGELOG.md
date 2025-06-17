### 2.4.0 - 2025-06-17

#### :sparkles: Création d'une seconde implémentation AuthenticationProvider pour environnement hors-prod

Cette implémentation se nomme `TestingCustomerAuthenticationProvider` : on ne vérifie que l'adresse mail, **n'importe quel mot de passe peut être utilisé**.

### 2.3.0 - 2025-06-17

#### :sparkles: Création d'une implémentation AuthenticationProvider custom

Cette implémentation reprend en réalité la logique de `DaoAuthenticationProvider`.

Il s'agit d'un proof of concept, expliquant qu'on peut ajouter notre propre logique, dans le flux d'authentification.


### 2.2.0 - 2025-06-17

#### :sparkles: Création d'un controller pour inscrire un nouvel utilisateur

- Création d'un RestController basique : **UserController**
- Désactivation CSRF pour utilisation du RestController ( :warning: temporaire ! )
- Fix sur la migration de suppression des tables par défaut de Spring Security

### 2.1.0 - 2025-06-17

#### :sparkles: Utilisation d'une représentation custom d'un utilisateur pour Spring Security

- Création d'une entité JPA arbitraire représentant un user
- Création d'une table représentant cette entité
- Création d'un service custom implémentant `UserDetailsService` : **CustomerUserDetailsService**
  - Ce service a sa propre logique **pour loadUserByUsername** : on utilise un **repository Spring Data** + logique de correspondance avec les autorités
- Suppression de la bean **UserDetailsService** qui utilisait **JbcUserDetailsManager** (puisque la logique est réécrite dans **CustomerUserDetailsService**)

### 2.0.0 - 2025-06-16

#### :boom: Utilisation d'une BDD MariaDB pour stockage des utilisateurs (JdbcUserDetailsManager)

- Ajout d'une stack compose avec un service **mariadb**
- Ajout des dépendances nécessaires pour la connexion à la BDD
- Ajout de **flyway** pour exécution des **scripts nécessaires** pour JdbcDaoImpl
- **Suppression de l'utilisation de `InMemoryUserDetailsManager`**

### 1.2.0 - 2025-06-16

#### :sparkles: Utilisation d'un algorithme pour chiffrer les mots de passe

Car précédemment, les mots de passe étaient stockés en **clair** : ce qui est acceptable dans un contexte de test, **mais pas du tout dans un contexte de production.**

- Déclaration d'une bean **PasswordEncoder**

```java
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
```

Cette bean, en fonction du **préfixe indiqué dans la déclaration du mot de passe**, va faire déléguer le processus de déchiffrage à la bonne instance de `PasswordEncoder`.

- Les mots de passe ont été remplacés **par des chaînes chiffrées en bcrypt**, afin que la bean `passwordEncoder` puisse les déchiffrer correctement : 

```java
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user").password("{bcrypt}$2a$12$.eaRIKyqmV5OS6ycI5uW.O3iYfjeAyPk7DJwTekVGk3PbXxr3y3DS").authorities("read").build();
        UserDetails admin = User.withUsername("admin").password("{bcrypt}$2a$12$.eaRIKyqmV5OS6ycI5uW.O3iYfjeAyPk7DJwTekVGk3PbXxr3y3DS").authorities("admin").build();
        return new InMemoryUserDetailsManager(user, admin);
    }
```


### 1.1.0 - 2025-06-16

#### :sparkles: Ajout de plusieurs utilisateurs en mémoire

- Création d'une **bean UserDetailsService**

Pour rappel, l'implémentation UserDetailsService a pour rôle, **l'inventaire des utilisateurs disponibles**.

Dans l'exemple, on a créé plusieurs **utilisateurs en mémoire**, durant la déclaration de la bean :

```java
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user").password("{noop}password").authorities("read").build();
        UserDetails admin = User.withUsername("admin").password("{noop}password").authorities("admin").build();
        return new InMemoryUserDetailsManager(user, admin);
    }
```

Comme il s'agit d'une déclaration en mémoire, les utilisateurs seront stockés au sein de l'implémentation **InMemoryUserDetailsManager**.

- Suppression de la property suivante :
```properties
spring.security.user.password=password
```

Cette property n'est plus utile, car on a ajouté plusieurs utilisateurs dans l'application.

### 1.0.1 - 2025-06-16

- Ajout de la property suivante : 

```properties
spring.security.user.password=password
```

Permet d'écraser **le mot de passe généré au démarrage de l'application** par un mot de passe **statique**.

- Création de la configuration **SecurityConfig** : il s'agit de la reprise de la configuration proposée par défaut dans la classe **SpringBootWebSecurityConfiguration**


### 1.0.0 - 2025-05-31

- Ajout de **Spring Security** dans les dépendances

Au démarrage de l'application, on arrive sur un **écran d'authentification**.

Les credentials sont : 

- En **nom d'utilisateur** : **user**
- En **mot de passe** : divulgué dans la console

:warning: À ne pas utiliser **en production !**

