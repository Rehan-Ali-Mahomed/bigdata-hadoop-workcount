## Contexte

Ce projet a été réalisé dans le cadre d'un TP de Big Data. Il a pour objectif de comprendre comment utiliser Hadoop avec du Map Reduce.

## Requirements

- Docker
- JDK v1.8

## Partie 1 : WordCount

La partie une consiste à écrire un prgramme permettant de calculer le nombre de fois que chaque mot est utilisé dans un fichier texte fourni en argument.

Pour lancer le programme avec Hadoop (sur un système HDFS), il faut entrer la commande : 
```
hadoop jar marketbenefits-1.10.jar tn.bigdata.tp1.WordCount input output
```

**Attention : Le programme renverra une erreur si le répertoire "output" existe déjà.**

## Partie 2 : MarketBenefits

Dans cette partie, je vais expliquer ce que j'ai fais.

### Consigne 
Écrire un Job Map Reduce permettant, à partir du fichier purchases initial, de déterminer le total des ventes par magasin.

### Structure du fichier

Voici quelques lignes du fichier indiquant la structure du fichier $src/main/resources/input/file.txt$

```
Date            Heure   Magasin         Produit             Coût    Paiement
2012-12-31      17:59   Birmingham      CDs                 118.04  Cash
2012-12-31      17:59   Las Vegas       Health and Beauty   420.46  Amex
2012-12-31      17:59   Wichita Toys    383.9               Cash
2012-12-31      17:59   Glendale        Women's Clothing    68.05   Amex
2012-12-31      17:59   Albuquerque     Toys                345.7   MasterCard
```

**Remarque** : Le format du fichier ayant une structure irrégulière (ligne 4), le programme a du mal à séparer correctement la colonne "Magasin" et "Produit" pouvant donc résulter sur une concaténation des 2 colonnes (Magasin + Produit, e.g: Wichita Toys).

### Démarche

Pour commencer, j'ai compris comment fonctionné le programme de la partie 1 afin de pouvoir le modifier et y ajouter la fonction permettant de répondre au besoin.

Après avoir compris, j'ai cherché un moyen et de séparer les colonnes et d'extraire les données. Pour se faire, j'ai utilisé un regex permettant d'identifier les données à partir d'un pattern (voir [SplitterMapper.java](https://github.com/Rehan-Ali-Mahomed/hadoop-mapreduce-workcount/tree/main/src/main/java/tn/bigdata/tp1/marketbenefits/SplitterMapper.java)).

Une fois que j'ai réussi à recupérer des données potables, j'ai ajouté 3 fichiers en m'inspirant du programme de la partie 1. J'ai ensuite modifié tout les *IntWritable* en *FloatWritable* afin de prendre également la virugle pour le calcul total, donnant ainsi les 2 fichier suivants :

- [FloatSumReducer.java](https://github.com/Rehan-Ali-Mahomed/hadoop-mapreduce-workcount/tree/main/src/main/java/tn/bigdata/tp1/marketbenefits/FloatSumReducer.java)

- [MarketBenefits.java](https://github.com/Rehan-Ali-Mahomed/hadoop-mapreduce-workcount/tree/main/src/main/java/tn/bigdata/tp1/MarketBenefits.java)

Enfin, après avoir testé et vérifier que ça fonctionné, j'ai package l'application et je l'ai lancé sur le docker grâce à la commande :
```
hadoop jar marketbenefits-1.10.jar tn.bigdata.tp1.MarketBenefits input output
```