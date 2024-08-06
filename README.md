# An open source multi-criteria data-matching algorithm based on Belief Theory

[![Project Status: Active – The project has reached a stable, usable state and is being actively developed.](https://www.repostatus.org/badges/latest/active.svg)](https://www.repostatus.org/#active)
[![MultiCriteriaMatching build & test](https://github.com/umrlastig/MultiCriteriaMatching/actions/workflows/ci.yml/badge.svg)](https://github.com/umrlastig/MultiCriteriaMatching/actions/workflows/ci.yml)
[![codecov](https://codecov.io/gh/umrlastig/MultiCriteriaMatching/branch/master/graph/badge.svg?token=pHLaV21j2O)](https://codecov.io/gh/umrlastig/MultiCriteriaMatching)


[![Software License](https://img.shields.io/badge/Licence-Cecill--C-blue.svg?style=flat)](https://github.com/umrlastig/MultiCriteriaMatching/blob/master/Licence-en.html)
[![Maven Central](https://img.shields.io/maven-central/v/fr.umr-lastig/MultiCriteriaMatching.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22fr.umr-lastig%22%20AND%20a:%22MultiCriteriaMatching%22)


A goal of data matching is to define homologous geographic features in two differents sources, features representing the same object from the real world. The **MultiCriteriaMatching**  data matching algorithm requires defining a reference and a comparison dataset giving in this way the direction of matching (for each feature from the reference dataset, the algorithm looks for homologous features in the comparison dataset). Let us mention that the reference dataset can be either an authoritative or a crowdsourced dataset. Knowing the characteristics of our datasets, criteria can be choosen (*e.g.* position, toponym, semantic, etc.). Each criteria must be associated with a similarity measure. For example, the position criterion is based on the distance between the reference feature and a candidate (*e.g.* Euclidean distance for landmarks and average of minimum of Hausdorff distance between every roads segments for lines), the name criterion compares the name of the reference feature with the name of the candidate (different measures exists to compare strings, Samal distance (Samal et al., 2005) and Cosinus distance is considered as most appropriate for points and itinerary), semantic criterion compares feature types, etc. All these criteria are merged to take a final decision in the process, the **MultiCriteriaMatching** algorithm do not take any decision (i.e. features are not matched) if the criteria are contradictories; these cases are tagged as uncertainty.


The theory of belief functions is implemented in the [Evidence4J](https://github.com/IGNF/evidence4j) library.


See the doc: [launch a data matching](UserGuide.md)


## Publications

### Data matching using Belief Theory algorithm
<ul>
<li>Olteanu Raimond, AM., Mustière, S. (2008). <a href='https://doi.org/10.1007/978-3-540-68566-1_29'>Data Matching - a Matter of Belief</a>. In: Ruas, A., Gold, C. (eds) Headway in Spatial Data Handling. Lecture Notes in Geoinformation and Cartography. Springer, Berlin, Heidelberg. </li>
<li>Olteanu Raimond, Ana-Maria & Mustiere, Sebastien & Ruas, Anne. (2015). <a href='http://dx.doi.org/10.5311/JOSIS.2015.10.194'>Knowledge formalization for vector data matching using Belief Theory</a>. JOURNAL OF SPATIAL INFORMATION SCIENCE. in press. </li>
</ul>


### Scientific papers whose described experiments use the **MultiCriteriaMatching** library.
<ul>
<li>G. Touya, V. Antoniou, A.-M. Olteanu-Raimond, M.-D. Van Damme, <a href='https://doi.org/10.3390/ijgi6030080'>Assessing Crowdsourced POI Quality: Combining Methods Based on Reference Data, History, and Spatial Relations</a>. ISPRS Int. J. Geo-Inf. 2017, 6, 80.  </li>
<li>Marie-Dominique Van Damme, Ana-Maria Olteanu-Raimond, Yann Méneroux. <a href='https://dx.doi.org/10.1080/23729333.2019.1615730'>Potential of crowdsourced data for integrating landmarks and routes for rescue in mountain areas</a>. International Journal of Cartography, Taylor & Francis, 2019, 5 (2-3), pp.195-213. </li>
<li>Ibrahim Maidaneh Abdi, Arnaud Le Guilcher, Ana-Maria Olteanu-Raimond. <a href='https://doi.org/10.5194/isprs-annals-V-4-2020-39-2020'>A regression model of spatial accuracy prediction for Openstreetmap buildings</a>. XXIVth ISPRS Congress, Aug 2020, Nice, France. pp.39-47.</li>
<li>Marie-Dominique van Damme, Ana-Maria Olteanu-Raimond. <a href='https://doi.org/10.5194/agile-giss-3-17-2022'>A method to produce metadata describing and assessing the quality of spatial landmark datasets in mountain area</a>. 25th AGILE Conference 2022, Jun 2022, Vilnus, France. pp.1-11.</li>
</ul>



## Using the MultiCriteriaMatching Java library

**! from version ≥ 1.0 Relocated → fr.umr-lastig » MultiCriteriaMatching**

- source code: https://github.com/umrlastig/MultiCriteriaMatching
- with *Maven* in a Java project:

To include *MultiCriteriaMatching* in a Maven project, add a dependency block like the following:

```xml
<!-- https://mvnrepository.com/artifact/fr.umr-lastig/MultiCriteriaMatching -->
<dependency>
    <groupId>fr.umr-lastig</groupId>
    <artifactId>MultiCriteriaMatching</artifactId>
    <version>1.1</version>
</dependency>
```

## Development & Contributions
* Institute: LASTIG, Univ Gustave Eiffel, ENSG, IGN
* License: Cecill-C
* Authors:
    - Marie-Dominique Van Damme
    - Ana-Maria Raimond
    - Imran Lokhat





