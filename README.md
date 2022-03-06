# AudioDB

Cette application mobile utilise les données de l'API TheAudioDB pour présenter des informations autour de la musique :
- artistes
- albums
- titres

## Fonctionnalités implémentées
### Page d'accueil (Classements)
Cette page présente les classements itunes pour les titres et les albums, répartis sur deux onglets.

Il est possible de naviguer vers la page Artiste ou la page Album par clic sur un élément de la liste.

Une barre de navigation permet d'accéder à la page Recherche ou Favoris.

![image classement Chansons](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/e3756ab6-e2a7-4a36-9842-2265b6995188/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220306%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220306T080658Z&X-Amz-Expires=86400&X-Amz-Signature=a74183de970cb9ef12784b78867e3fb5eb7ca56972b763edfa4fb36065326e90&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)
![image classement Albums](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/0b67e3ec-5a31-4b0b-acbe-274b28985c37/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220306%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220306T081231Z&X-Amz-Expires=86400&X-Amz-Signature=47276bc523414793f2ee7a4485c6a6c5ca9b073b4ed517539688142538c44b46&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)
Navigation :
```mermaid
    flowchart LR
        subgraph Classements
            A
            B
        end
        A[Liste] --> Artiste
        A --> Album
        B[Barre de navigation] --> Recherche
        B --> Favoris
```
### Page Recherche

![image recherche](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/7262c648-5026-45b6-8adb-d4a898cae6d0/Untitled.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220306%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220306T081403Z&X-Amz-Expires=86400&X-Amz-Signature=bab4e43741a1d3757316cb78a1db4de9d042bb6f2e56feac5f41f1c94dfbf951&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Untitled.png%22&x-id=GetObject)

Cette page permet de réaliser une recherche par artiste. 
- Saisir un nom d'artiste dans le champ de recherche et appuyer sur le bouton associé.
- Le résultat s'affiche pour cet artiste et toute sa discographie.

Par clic sur l'artiste ou un de ses albums, on accède respectivement à la page Artiste ou la page Album.

```mermaid
    flowchart LR
        subgraph Recherche
            A
            B
        end
        A[Liste] --> Artiste
        A --> Album
        B[Barre de navigation] --> Classements
        B --> Favoris
```

### Page Favoris
Cette page permet de réaliser une recherche par artiste. Elle affiche le résultat de cet artiste et toute sa discographie.

Par clic sur un artiste ou un album, on accède respectivement à la page Artiste ou la page Album.

```mermaid
    flowchart LR
        subgraph Favoris
            A
            B
        end
        A[Liste] --> Artiste
        A --> Album
        B[Barre de navigation] --> Classements
        B --> Recherche
```

### Page Artiste
Cette page affiche le détail d'un artiste.
- description
- albums
- titres les plus appréciés

Elle donne la possibilité d'ajouter ou retirer cet artiste des favoris par un clic sur le coeur.

Par clic sur un album ou un titre, on accède respectivement à la page Album ou la page Titre.

```mermaid
    flowchart LR
        subgraph Artiste
            A
        end
        A[Liste] --> Album
        A --> Titre
```

### Page Album
Cette page affiche le détail d'un album.
- description
- titres de l'album

Elle donne la possibilité d'ajouter ou retirer cet album des favoris par un clic sur le coeur.

Par clic sur un titre, on accède à la page Titre.

```mermaid
    flowchart LR
        subgraph Album
            A
        end
        A[Liste] --> Titre
```

### Page Titre
Cette page affiche les paroles d'une chanson.

### Précisions
- l'application tient compte de la langue du téléphone graçe aux Strings et à la getLocal pour les descriptions Album et Artist.
- comme vu précédement avec vous lors du CC précédent la navigation ne fonctionne pas pour le retour en arrière, celle ci quitte l'application, on a néanmoins implémentées le onBackPressed qui aurait été implémenté si elle fonctionnait. Nous sommes preneur de vos retoure si vosu arrivez à fixe le problème mais il me semble que pur le CC nous n'avions déjà pas pu fix ce problème.
