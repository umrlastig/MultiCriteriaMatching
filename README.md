# Multi Criteria Matching (Dempster-Shafer Theory)

Features matching with Dempster-Shafer Theory as implements in the Evidence4J library

+
+Appariement de données multicritères basé sur la théorie des fonctions de croyances implémentée dans la librairie Evidence4J.
+
+## Appariement multi-critères
+L’algorithme d’appariement se base sur un jeu de données de référence et sur un jeu de données de comparaison donnant ainsi une direction à 
+l’appariement (pour chaque donnée du jeu de référence, l’algorithme recherche les données homologues et candidates dans le jeu de comparaison). 
+Mentionnons que le jeu de données de référence peut être un jeu de données faisant autorité ou un jeu de données collaboratif.
+



[![Project Status: Active – The project has reached a stable, usable state and is being actively developed.](https://www.repostatus.org/badges/latest/active.svg)](https://www.repostatus.org/#active)
[![CircleCI](https://img.shields.io/circleci/project/github/umrlastig/MultiCriteriaMatching/master.svg?style=flat-square&label=CircleCI)](https://circleci.com/gh/umrlastig/MultiCriteriaMatching)
[![codecov](https://codecov.io/gh/umrlastig/MultiCriteriaMatching/branch/master/graph/badge.svg?token=pHLaV21j2O)](https://codecov.io/gh/umrlastig/MultiCriteriaMatching)
[![Software License](https://img.shields.io/badge/Licence-Cecill--C-blue.svg?style=flat)](https://github.com/umrlastig/MultiCriteriaMatching/blob/master/Licence-en.html)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)

* Institute: LASTIG, Univ Gustave Eiffel, ENSG, IGN
* License: Cecill-C
* Authors:
	- Marie-Dominique Van Damme
	- Ana-Maria Raimond
	- Imran Lokhat


# Using the Java library
- source code: https://github.com/IGNF/MultiCriteriaMatching
- with *Maven* in a Java project:

To include *MultiCriteriaMatching* in a Maven project, add a dependency block like the following:

```xml
<dependency>
    <groupId>fr.ign.cogit</groupId>
    <artifactId>MultiCriteriaMatching</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```


# User Guide

## Lancer le code d'appariement

De manière globale, faire un appariement multi-critères entre un objet de référence et des potentiels candidats à apparier, en utilisant la librairie `MultiCriteriaMatching`, consiste à :
* instancier un "objet appariement"
* créer une liste de critères, avec pour chacun, une distance associée
* affecter la liste de critères précédemment définie à l'appariement
* charger les données, i.e. une feature de référence et un ensemble de features candidates
* lancer l'appariement sur les données et analyser les résultats



# Use Cases
<ul>
<li> Olteanu Raimond, AM., Mustière, S. (2008). <a href='https://doi.org/10.1007/978-3-540-68566-1_29'>Data Matching - a Matter of Belief</a>. In: Ruas, A., Gold, C. (eds) Headway in Spatial Data Handling. Lecture Notes in Geoinformation and Cartography. Springer, Berlin, Heidelberg. </li>
<li> Olteanu Raimond, Ana-Maria & Mustiere, Sebastien & Ruas, Anne. (2015). <a href='http://dx.doi.org/10.5311/JOSIS.2015.10.194'>Knowledge formalization for vector data matching using Belief Theory</a>. JOURNAL OF SPATIAL INFORMATION SCIENCE. in press. </li>
<li> G. Touya, V. Antoniou, A.-M. Olteanu-Raimond, M.-D. Van Damme, <a href='https://doi.org/10.3390/ijgi6030080'>Assessing Crowdsourced POI Quality: Combining Methods Based on Reference Data, History, and Spatial Relations</a>. ISPRS Int. J. Geo-Inf. 2017, 6, 80.  </li>
<li> Marie-Dominique Van Damme, Ana-Maria Olteanu-Raimond, Yann Méneroux. <a href='https://dx.doi.org/10.1080/23729333.2019.1615730'>Potential of crowdsourced data for integrating landmarks and routes for rescue in mountain areas</a>. International Journal of Cartography, Taylor & Francis, 2019, 5 (2-3), pp.195-213.] </li>
</ul>


