/**
 * 
 * This software is released under the licence CeCILL
 * 
 * see LICENSE.TXT
 * 
 * see <http://www.cecill.info/ http://www.cecill.info/
 * 
 * 
 * @copyright IGN
 * 
 * 
 */
package fr.ign.cogit.appariement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.ign.cogit.criteria.Critere;
import fr.ign.cogit.evidence.configuration.Configuration;
import fr.ign.cogit.evidence.configuration.ConfigurationSet;
import fr.ign.cogit.evidence.massvalues.MassPotential;
import fr.ign.cogit.evidence.variable.Variable;
import fr.ign.cogit.evidence.variable.VariableFactory;
import fr.ign.cogit.evidence.variable.VariableSet;



/**
 * Features matching with Dempster-Shafer Theory as implements in the Evidence4J library.
 * 
 * @see <a href="https://github.com/IGNF/evidence4j">here</a> for more details
 *      on Evidence4j
 * 
 * @author A-M Raimond, M-D Van Damme
 */
public class AppariementDST {
  
	private double seuilIndecision = 0.2;
  
	private List<Critere> listCritere;
  
	/** Journalisation. */
	protected static Logger LOGGER = Logger.getLogger(AppariementDST.class.getName());
  
	public void setSeuilIndecision(double seuilIndecision) {
		this.seuilIndecision = seuilIndecision;
	}
	public double getSeuilIndecision() {
		return this.seuilIndecision;
	}
  
	public void setListCritere(List<Critere> listCritere) {
		this.listCritere = listCritere;
	}

	/**
	 * @param topoDico
	 *            le feature de référence
	 * @param candidatListe
	 *            liste de candidat pour l'appariement avec topoDico           
	 * @throws Exception 
	 * 
	 * @see <a href=
	 *      "http://recherche.ign.fr/labos/cogit/pdf/THESES/OLTEANU/TheseOlteanu.pdf"
	 *      >The PhD Thesis of Ana-Maria Olteanu-Raimond<>
	 */
	public List<LigneResultat> appariementObjet(Feature featRef, List<Feature> candidatListe) throws Exception {

		LOGGER.info("----- DEBUT APPARIEMENT --------");
  
		String identifiant = featRef.getId();
		if (featRef.getNom() != null) {
			LOGGER.info("NB candidat pour " + featRef.getNom() + " = " + candidatListe.size());
		} else {
			LOGGER.info("NB candidat pour NULL = " + candidatListe.size());
		}

		// Cette ligne déclare la liste des résultats de selection pour l'appariement
		List<LigneResultat> listeRes = new ArrayList<LigneResultat>();

		// Setting the problem.
		VariableFactory<String> vf = new VariableFactory<String>();

		// The variables considerer (match with Ci or doesn't match with Ci)
		Variable<String> defCadreDiscernement = vf.newVariable();
		for (Feature candidat : candidatListe) {
			defCadreDiscernement.add(candidat.getId());
		}
		defCadreDiscernement.add("NA");

		// The set of all the decision variables (we have only one here)
		VariableSet<String> cadreDiscernement = new VariableSet<String>(vf);
		cadreDiscernement.add(defCadreDiscernement);
		LOGGER.log(Level.FINE, "Configurations = " + cadreDiscernement);

		/*
		 * Each variable configuration is encapsulated in an indexed object
		 * called Configuration to avoid ambiguities.
		 */
		Map<String, Configuration<String>> configCadreDiscernement = new HashMap<String, Configuration<String>>();
		for (Feature candidat : candidatListe) {
			Configuration<String> cc = new Configuration<String>(cadreDiscernement, 
					Arrays.asList(candidat.getId()));
			configCadreDiscernement.put(candidat.getId(), cc);
		} 
		Configuration<String> cna = new Configuration<String>(cadreDiscernement, Arrays.asList("NA"));
		configCadreDiscernement.put("NA", cna);


		/*
		 * ------------------- Approche appriou -------------------
		 */
		Map<String, ConfigurationSet<String>> listeAppC = new HashMap<String, ConfigurationSet<String>>();
		Map<String, ConfigurationSet<String>> listeNonAppC = new HashMap<String, ConfigurationSet<String>>();
		for (Feature candidat : candidatListe) {
			String id = candidat.getId();
			ConfigurationSet<String> appC = new ConfigurationSet<String>(cadreDiscernement);
			appC.add(configCadreDiscernement.get(candidat.getId()));
			listeAppC.put(id, appC);

			ConfigurationSet<String> nonAppC = new ConfigurationSet<String>(cadreDiscernement);
			for (Feature candidat2 : candidatListe) {
				if (!candidat.getId().equals(candidat2.getId())) {
					nonAppC.add(configCadreDiscernement.get(candidat2.getId()));
				}
			}
			nonAppC.add(configCadreDiscernement.get("NA"));
			listeNonAppC.put(id, nonAppC);
		}
		ConfigurationSet<String> csNSP = new ConfigurationSet<String>(cadreDiscernement);
		csNSP.addAllConfigurations();

		/*
		 * -------------------The mass potentials---------------------- In the
		 * Appriou's framework, each mass function is divided in sub-masses. In
		 * the original example, 2 mass functions are defined, thus we have to
		 * create 6 mass potentials.
		 */
		Map<Integer, Map<String, MassPotential<String>>> listeMasseCandCrits = new HashMap<Integer, Map<String, MassPotential<String>>>();
		for (int c = 0; c < this.listCritere.size(); c++) {
			Map<String, MassPotential<String>> listeMasseCand1Crit = new HashMap<String, MassPotential<String>>();
			listeMasseCandCrits.put(c, listeMasseCand1Crit);
		}
  
		for (Feature candidat : candidatListe) {
    
			String id = candidat.getId();
			LOGGER.info("Feature : " + candidat.getNom());
      
			// On initialise les masses
			for (int c = 0; c < this.listCritere.size(); c++) {
          
				// 
				Critere crit = this.listCritere.get(c);
				crit.setFeature(featRef, candidat);
				double[] massesCS = new double[3];
          
				// 
				if (crit.getDistance().getNom().equals("Samal")) {
					if (featRef.getNom() != null) {
						massesCS = crit.getMasse();
					} else {
						massesCS[0] = 0;
						massesCS[1] = 0;
						massesCS[2] = 1;
					}
				} else {
					massesCS = crit.getMasse();
				}
          
				MassPotential<String> masseCandidatCritere = new MassPotential<String>(cadreDiscernement);
				masseCandidatCritere.add(listeAppC.get(id), massesCS[0]);
				masseCandidatCritere.add(listeNonAppC.get(id), massesCS[1]);
				masseCandidatCritere.add(csNSP, massesCS[2]);
				listeMasseCandCrits.get(c).put(id,  masseCandidatCritere);
				LOGGER.info("Distance pour " + crit.getNom() + " = "+ crit.getDistance().getDistance());
				LOGGER.info("  masses = [" + massesCS[0] + ", " + massesCS[1] + ", " + massesCS[2] + "]");
          
			}
      
		} 
  

		// Some printing
		// System.out.println("Mass potentials : ");
		Map<String, Set<MassPotential<String>>> listeMasseCand = new HashMap<String, Set<MassPotential<String>>>();
		Set<MassPotential<String>> cfusion = new HashSet<MassPotential<String>>();
		Map<String, MassPotential<String>> combinationDesCriteresParCandidat = new HashMap<String, MassPotential<String>>();
		for (Feature candidat : candidatListe) {
			String id = candidat.getId();
			// LOGGER.info("id = " + id);

			// The set of mass potentials for the first sub-problem
			Set<MassPotential<String>> mpP = new HashSet<MassPotential<String>>();
			for (int c = 0; c < this.listCritere.size(); c++) {
				// System.out.println(id);
				// System.out.println(listeMasseCandCrits.get(c));
				mpP.add(listeMasseCandCrits.get(c).get(id));
			}

			listeMasseCand.put(id,  mpP);
			// System.out.println("-----------------for the sub-problem : o1 match with C");
			// System.out.println(mpP);

			// The complete mass fusion, aka the Candidate fusion
			for (int c = 0; c < this.listCritere.size(); c++) {
				cfusion.add(listeMasseCandCrits.get(c).get(id));
			}

			/*
			 * -------------------Final mass combination-------------------
			 */  
			MassPotential<String> combination_P1 = MassPotential.combination(mpP, false);
			combinationDesCriteresParCandidat.put(id, combination_P1);
			combination_P1.check();
		}
  
		// Only the following combination one is really necessary; combination_Px can be skipped
		// since they are just intermediary results
		MassPotential<String> combination_cfusion = MassPotential.combination(cfusion, false);
		combination_cfusion.check();
  
		double conflit1 = combination_cfusion.getConflit();
		LOGGER.info("conflit = " + conflit1);

		// compteur de candidats
		int compteurC = 0;

		// Affiche les candidats et leur score
		for (Feature candidat : candidatListe) {
			String id = candidat.getId();
			String nomCandidat = "";
			/*if (candidat.getNom() != null && candidat.getNom() != "") {
				candidat.getNom().toLowerCase();
			}*/
			LOGGER.info("pign pour  " + nomCandidat + " = " + arrondi(combination_cfusion.pignistic(configCadreDiscernement.get(id)), 5));
		}
		LOGGER.info("NA : " + arrondi(combination_cfusion.pignistic(configCadreDiscernement.get("NA")), 5));

		// Décision
		compteurC++;
		double pignisticNA = arrondi(combination_cfusion.pignistic(configCadreDiscernement.get("NA")), 5);

		Double d = new Double(pignisticNA);
		String nomFeatRef = "NR";
		if (featRef.getNom() != null) {
			nomFeatRef = featRef.getNom();
		}
  
		// Autres attributs
		// topoDico.getAttribute(objRef.getNature()).toString()
  
		// Liste des noms des distances
		String[] nomsDistance = new String[this.listCritere.size()];
		double[] distances = new double[this.listCritere.size()];
		for (int c = 0; c < this.listCritere.size(); c++) {
			nomsDistance[c] = this.listCritere.get(c).getDistance().getNom();
		}
  
		String[] attrs = null;
		LigneResultat res = new LigneResultat(identifiant, nomFeatRef, attrs, compteurC, "NA", "NA", attrs, distances, nomsDistance, d);
		listeRes.add(res);

 	int cptMax = 1;
 	double max = pignisticNA;
 	String idMax = "NA";
 	List<Double> listPignistic = new ArrayList<Double>();
 	listPignistic.add(pignisticNA);

 	for (Feature candidat : candidatListe) {
    
 		String id = candidat.getId();
 		compteurC++;
 		double pignisticCandidat = arrondi(combination_cfusion.pignistic(configCadreDiscernement.get(id)), 5);
 		listPignistic.add(pignisticCandidat);
    
 		double conflit = combination_cfusion.getConflit();
 		LOGGER.info("Conflit = " + conflit);
 		// System.out.println("conflit = " + conflit + ", pign = " + pignisticCandidat);
    
 		String nomRef = "";
 		if (featRef.getNom() != null && featRef.getNom() != "") {
 			nomRef = featRef.getNom();
 		}
 		String nomComp = "";
 		if (candidat.getNom() != null && candidat.getNom() != "") {
 			nomComp = candidat.getNom();
 		}
    
 		distances = new double[this.listCritere.size()];
 		for (int c = 0; c < this.listCritere.size(); c++) {
 			Critere crit = this.listCritere.get(c);
 			crit.setFeature(featRef, candidat);
 			crit.getMasse();
 			double dist = arrondi(crit.getDistance().getDistance(), 5);
 			distances[c] = dist;
 		}
        
 		// Rajout ligne tableau
 			// String[] attrs = null;
 		//          topoDico.getAttribute(this.objRef.getNature()).toString()
 		//          candidat.getAttribute(this.objComp.getNature()).toString()
 		LigneResultat res2 = new LigneResultat(identifiant, nomRef, attrs, 
 				compteurC, id, nomComp,  attrs, distances, nomsDistance, pignisticCandidat);
 		res2.setGeom(featRef.getGeom(), candidat.getGeom());
 		listeRes.add(res2);
 		// Fin rajout ligne tableau
    
    
 		if (pignisticCandidat > max) {
 			max = pignisticCandidat;
 			cptMax = 1;
 			idMax = id;
 		} else if (pignisticCandidat == max) {
 			cptMax++;
 		}
 	}

 	// On trie
 	LOGGER.info(Arrays.toString(listPignistic.toArray()));
 	Collections.sort(listPignistic);
 	LOGGER.info(Arrays.toString(listPignistic.toArray()));

  
  // Un seul candidat
  if (cptMax == 1) {
    // On regarde le deuxieme plus grand
    double pignisticMaxMoinsUn = listPignistic.get(listPignistic.size() - 2);
    double difference = Math.abs(pignisticMaxMoinsUn - max);
    
    
//    LOGGER.info("max1 = " + max + " et max2 = " + pignisticMaxMoinsUn);
//    LOGGER.info("max2 - max1 = " + Math.abs(pignisticMaxMoinsUn - max));
//    LOGGER.info("max1 - max2 = " + Math.abs(max - pignisticMaxMoinsUn));
    if (difference < this.seuilIndecision) {
      
      // cas 1
//      LOGGER.info("Candidats trop proches");
//      compteurIndecis++;
      for (LigneResultat res2 : listeRes) {
        res2.initDecision("indécis");
      }
      
    } else {
//      LOGGER.info("On a un bon candidat : " + idMax + "(" + max + ")");
      LOGGER.log(Level.FINE, "diff = " + difference);
      for (LigneResultat res2 : listeRes) {
        res2.initProbaPignistiqueSecond(difference);
        if (idMax.equals(res2.getIdTopoComp())) {
          res2.initDecision("true");
        } else {
          res2.initDecision("false");
        }
      }
    }

      
    } else {
      // Même cas 1
      for (LigneResultat res2 : listeRes) {
        res2.initDecision("indécis");
      }
    }
  


  //    ConfigurationSet<String> conf = combination_cfusion.decide(true);
  //    for (int i = 0; i < conf.size(); i++) {
  //      if (conf.hasConfiguration(i)) {
  //        Configuration<String> resconf = conf.getConfiguration(i);
  //        // TODO : faire les cas où on a 0 ou > 1 valeur
  //        if (resconf.getValues().size() == 1) {
  //          int counter = 0;
  //          for (int index : resconf.getValues()) {
  //            String idDecision = resconf.getNthRealisationVariable(counter).getValue(index);
  //            LOGGER.info("Decision = " + idDecision);
  //            for (LigneResultat res2 : listeRes) {
  //              if ( idDecision.equals(res2.getidBDUni())) {
  //                res2.initDecision(true);
  //              } else {
  //                res2.initDecision(false);
  //              }
  //            }
  //
  //            counter++;
  //          }
  //        } else {
  //          LOGGER.info("????????????????????????????????? 0 ou +1 candidat");
  //        }
  //      }
  //    }


  return listeRes;   

}  


  public static double arrondi(double A, int B) {
    return (double) ( (int) (A * Math.pow(10, B) + .5)) / Math.pow(10, B);
  }
  
}
