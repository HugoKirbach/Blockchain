# Projet Blockchain

## Nos choix
### Durée des threads
Afin de respecter les demandes lors de l'explication du sujet, nous avons pris la décision d'instancier une transaction toutes les secondes, prenant 2 wallets au hasard parmis les wallets instanciés, d'un montant allant de 0 à 25 unités. Toutes les 60 secondes, nous instancions un nouveau bloc, ce qui "clot" le précédent et le minage de ce dernier demarre. Toutes les valeurs cités ci-dessus sont celles que nous utilisons par défaut, mais elles sont modifiables dans la classe Constantes.java que nous présenterons a la fin de ce document.

### Gestion de la blockchain
Pour la partie minage nous avons donc décidé, comme expliqué juste au dessus, de lancer un minage toutes les 60 secondes après création d'un nouveau bloc. Nous l'avons implémenté avec un ThreadScheduler toutes les 60secondes, ce qui créé une "file d'attente" dans le cas où un minage dure plus longtemps que cette durée.

## Implémentation
### UTxO
Cette classe représente donc un UTxO, contenant un montant et l'adresse publique d'à qui appartient cet UTxO. Elle contient également un hashTransaction, instancié avec le hash de la transaction d'où cet UTxO est créée.

### Wallet
Nous avons créé une classe Wallet qui contient un attribut "utxos" qui est une liste d'UTxO, représentant donc le solde du wallet. A l'instanciation des premiers wallets lors du démarrage de l'application, nous avons pris la décision de remplir le wallet d'un montant aléatoire entre 0 et 25 unités, sous forme d'un unique UTxO associé à ce wallet.

### Transaction
Pour ce qui est de la gestion des transactions, notre classe Transaction comprend un attribut "hash" sous forme d'UUID (qui n'est pas réellement un hash mais plus un ID unique, nous l'avons appelé Hash pour la cohérence avec le sujet). Nous avons également un attribut "previousHash" qui comme son nom l'indique stock le hash de la transaction précédente.
Les attributs les plus importants maintenant sont les 2 attributs de type Wallet nommés sender et receiver, stockant les deux wallets impliqués dans la transaction, ainsi qu'un attribut "amount" qui stock le montant de la transaction. 
Pour ce qui est des UTxO reçus et envoyés, nous avons deux listes d'UTxO dans la transaction, une liste pour le ou les UTxO envoyés et une seconde liste pour le ou les UTxO reçus. 
Pour calculer les UTxO envoyés et reçus, nous avons créé une méthode "splitUTxOs" qui prend en paramètre une liste d'UTxO et le montant de la transaction, et qui va calculer les UTxO envoyés et reçus en fonction du montant et des UTxO du wallet sender. Cette méthode va prendre les premiers UTxO de la liste dans l'ordre jusqu'à atteindre le montant, et va donc créer un premier UTxO de sortie avec le montant de la transaction, et un second UTxO de sortie avec le reste de la somme des UTxOs du wallet sender.
Pour savoir quel UTxO est envoyé à qui, nous faisons donc un get(0) sur la liste pour l'UTxO vers le sender, et un get(1) pour l'UTxO du receiver.

### Blockchain
La classe Blockchain est la classe principale de notre projet, elle contient une liste de blocs et une liste de wallets. Elle contient également un attribut INSTANCE qui est une instance de la classe Blockchain, permettant d'avoir une instance unique de la blockchain.

### Block (et minage associé)
La classe la plus importante de notre projet est la classe Block, qui contient un attribut "id" qui est l'identifiant du bloc, un attribut "previousHash" qui est le hash du bloc précédent, un attribut "hash" qui est le hash du bloc actuel, un attribut "timestamp" qui est le timestamp de création du bloc, un attribut "transactions" qui est une liste de transactions, et un attribut "nonce" qui est le nonce du bloc.
Pour le calcul du hash, nous avons créé une méthode "calculateHash" qui va calculer le hash du bloc en fonction du previousHash, timestamp, nonce et de la liste de transactions dans le block. Ces données sont concaténées et hashées avec SHA-256 grâce à la librairie DigestUtils.
Cette methode de calcul de hash est appelé dans chaque fonction qui modifie le bloc, pour recalculer le hash du bloc à chaque modification et s'assurer que le hash soit bien correct à chaque étape de la blockchain.

#### Premier block
Pour l'instanciation de notre génesis block, nous créons un block simple avec l'id 0 et un previousHash à "000000000000000000000000000000000000000" (pour ne pas poser de problème lors de l'affichage et du traitement du hash précédent dans les blocs suivants).

#### Minage
Pour ce qui est du minage, nous avons créé une fonction "mineBloc" qui prend un unique attribut étant la difficulté, que nous mettons dans une classe "Constantes.java". 
Dans cette fonction, nous définissons un string "target" qui est une chaine de caractère de la taille de la difficulté, remplie de 0. Nous avons ensuite une boucle qui va incrémenter le nonce du bloc et mettre à jour son hash jusqu'à ce que le hash du bloc commence par la chaine de caractère "target". Une fois cela fait, nous affichons en console que le bloc est miné avec une preview de son nouveau hash, puis nous retournons le hash du bloc.

### Signature
Pour la gestion de la signature, nous avons créé une simple classe avec deux méthodes : sign et verifySignature. 
La méthode sign prend en paramètre un message et une clé privée, et va signer le message avec la clé privée. 
La méthode verifySignature prend en paramètre un message, une signature et une clé privée, et va vérifier que la signature est bien valide pour le message et la clé privée donnée.

### Constantes
Nous avons créé une classe Constante qui contient les constantes de notre projet. Ces constantes sont les suivantes : 
- DIFFICULTY : difficulté du minage
- DUREE_ENTRE_TRANSAC : durée entre chaque transaction, en secondes
- DUREE_ENTRE_BLOC : durée entre chaque nouveau bloc, en secondes
- MAX_MONTANT_TRANSAC : montant maximum d'une transaction
- NB_WALLET_INIT : nombre de wallets à instancier au démarrage de l'application
Ces constantes sont utilisées tout au long de l'exécution de notre blockchain, c'est pourquoi nous les avons toutes centralisées dans une seule classe, pour une configuration plus rapide.