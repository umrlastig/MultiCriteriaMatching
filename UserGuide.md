# User guide: launch the data matching code

De manière globale, faire un appariement multi-critères entre un objet de référence et des potentiels candidats à apparier, 
en utilisant la librairie `MultiCriteriaMatching`, consiste à :
* instancier un "objet appariement"
* créer une liste de critères, avec pour chacun, une distance associée
* affecter la liste de critères précédemment définie à l'appariement
* charger les données, i.e. une feature de référence et un ensemble de features candidates
* lancer l'appariement sur les données et analyser les résultats


## Appariement

Pour créer un appariement, on instancie la classe `fr.ign.cogit.appariement.AppariementDST` et on lui attribue un seuil d'indécision.

Il est aussi nécessaire d'affecter à l'appariement deux objets implémentant l'interface `fr.ign.cogit.metadata.Objet`, pour décrire les attributs des features de référence et candidates. Cela se fait à l'aide de la méthode `setMetadata`.

Par exemple pour créer une classe `MyObj` représentant des features ayant comme attributs un identifiant, un nom, une nature, et une importance, on pourra faire quelque chose comme :
```java
public class MyObj implements Objet{
    public String cle = "ID";
    public String nom = "nom";
    public String nature = "nature";
    public String importance = "NIVEAU";

    public MyObj(String cle, String nom, String nature, String importance){
        this.cle = cle;
        this.nom = nom;
        this.nature = nature;
        this.importance = importance;
    }
    
    @Override
    public String getCle() {
        return this.cle;
    }

    @Override
    public String getNom() {
        return this.nom;
    }

    @Override
    public String getAttrNameSemantique() {
        return this.nature;
    }

    public String getAttrImportance() {
        return this.importance;
    }
}
```

Puis on instancie cette classe et on la lie à l'appariement :

```java
AppariementDST evidenceAlgoFusionCritere = new AppariementDST();
evidenceAlgoFusionCritere.setSeuilIndecision(0.15);
MyObj ref = new MyObj("ID", "", "SYMBOLISAT", "NIVEAU");
MyObj cand = new MyObj("ID", "", "NATURE", "IMPORTANCE");
evidenceAlgoFusionCritere.setMetadata(ref, cand);
```

## Critères
On crée un critère en implémentant l'interface `fr.ign.cogit.criteria.Critere`.

Un certain nombre de critères sont écrits dans `fr.ign.cogit.criteria` dont on peut s'inspirer :
 - `CritereGeom`
 - `CritereOrientation`
 - `CritereSemantique`
 - `CritereToponymique`

En héritant de `fr.ign.cogit.criteria.CritereAbstract`, on a quelques méthodes déja écrites :
 - `public void setFeature(IFeature featureRef, IFeature featureComp)`
 - `public void checkSommeMasseEgale1(double[] tableau)`
 - `public Distance getDistance()`

La méthode la plus importante est `getMasse()` qui doit renvoyer un tableau de 3 valeurs doubles qui représentent la probabilité que le candidat soit apparié, qu'il ne soit pas apparié, ou qu'on ne sache pas. Leur somme doit être égale à 1.

(ce sont des fonctions en général, voir le code existant dans les critères déjà définis..)

À chaque critère est associée une distance.

## Distance
Une distance doit implémenter l'interface `fr.ign.cogit.distance.Distance`. Un certain nombre de distances sont déjà implémentées dans `fr.ign.cogit.distance` classées suivant qu'elle soient sémantiques, géométriques ou concernent du texte.

Il n'y a en réalité que deux méthodes à implémenter :
 - `double getDistance()`
 - `String getNom()`

Prenons un exemple simple, une distance qui évaluerait la distance entre deux attributs numériques (un pour la référence et l'autre pour le candidat considéré) qui représenteraient un niveau d'importance :
```java
public class DistanceImp implements Distance{
    public int impRef;
    public int impComp;
    
    @Override
    public double getDistance() {
        int diff =  Math.abs(impRef - impComp);
        if (diff == 0)
            return 0;
        else if (diff <= 1)
            return 0.2;
        else if (diff <= 2)
            return 0.5;
        return 0.8;
    }
    
    @Override
    public String getNom() {
        return "Distance Nawak";
    }

    public void setImps(int ref, int comp) {
        this.impRef = ref;
        this.impComp = comp;
    }

}

```

On pourra écrire un critère comme suit :
```java
public class CritereImportance extends CritereAbstract implements Critere{
    private String attrRefImportance = "";
    private String attrCompImportance = "";
    private double seuil = 0.6;
      
    private double eps = 0.01;


    public CritereImportance(Distance distance) {
        super(distance);
    }

    public void setMetadata(MyObj ref, MyObj cand) {
        this.attrRefImportance = ref.getAttrImportance();
        this.attrCompImportance = cand.getAttrImportance();
    }
    
    @Override
    public String getNom() {
        return "Critere Importance";
    }

    @Override
    public double[] getMasse() throws Exception {
        int impRef = -1;
        int impComp = -1;
        
        if (featureComp.getAttribute(attrCompImportance) != null && featureComp.getAttribute(attrCompImportance) != "") {
            impComp = Integer.parseInt((String)featureComp.getAttribute(attrCompImportance));
         }
            
        if (featureRef.getAttribute(attrRefImportance) != null) {
               impRef=((Long)(featureRef.getAttribute(attrRefImportance))).intValue();
        }
        ((DistanceImp)distance).setImps(impRef, impComp);
        Double distNorm = distance.getDistance();
    
        double[] tableau = new double[3];
        if (distNorm < seuil) {
            tableau[0] = (-(0.5 - eps)/seuil)*distNorm + 0.5;
            tableau[1] = ((0.1-eps)/seuil) * distNorm;
            tableau[2] = (0.4/seuil) * distNorm + 0.5;
        } else {
            tableau[0] = eps;
            tableau[1] = 0.1 - eps;
            tableau[2] = 0.9;
        }
    
        // si un des deux n'existe pas
        if (impRef == -1 || impComp == -1 ) {
            tableau[0] = 0;
            tableau[1] = 0;
            tableau[2] = 1;
        }
    
        try {
            checkSommeMasseEgale1(tableau);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        //  Return 3 masses sous forme de tableau
        return tableau;
    }
}
```


Une fois les critères et leurs distances associées crées, on les met dans une liste, que l'on passe à l'instance d'appariement via sa méthode `setListCritere`:
```Java
// ...
//Critere importance
DistanceImp di = new DistanceImp();
CritereImportance ci = new CritereImportance(di);
ci.setMetadata(ref, cand);
listCritere.add(ci);

// Critere géométrique
DistanceEuclidienne dg = new DistanceEuclidienne();
CritereGeom cg = new CritereGeom(dg);
cg.setSeuil(2, 10);

List<Critere> listCritere = new ArrayList<Critere>();
listCritere.add(critereGeom);
listCritere.add(critereImportance);
// ...
evidenceAlgoFusionCritere.setListCritere(listCritere);
```

## Données
On lance l'appariement en invoquant la méthode `appariementObjet` qui attend comme entrées une feature de référence `IFeature`, et une population de features candidates `IPopulation<IFeature>` et renvoie une liste de `fr.ign.cogit.dao.LigneResultat`.

## Résultats
l'appariement renvoie une liste de lignes de résultats, une par feature candidate. (une de plus en fait, la première ligne indique qu'il est sûr qu'il n'y a pas d'appariement)

Cet objet résultat contient diverses informations, telles que les distances calculées (dans l'ordre des critères qui ont été passés), l'identifiant de la feature candidate, les géométries candidates/référence, etc...

Il existe une classe utilitaire, `fr.ign.cogit.gui.TableauResultatFrame`, qu'on peut utiliser pour visualiser les résultats :

```Java
List<LigneResultat> lres = evidenceAlgoFusionCritere.appariementObjet(ref, candidats);
TableauResultatFrame tableauPanel = new TableauResultatFrame();
tableauPanel.displayEnsFrame("tests", lres);
```
