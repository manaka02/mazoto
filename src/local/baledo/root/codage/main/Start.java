package local.baledo.root.codage.main;

import local.baledo.root.codage.object.Matrice;
import local.baledo.root.codage.object.TableauDecodage;
import local.baledo.root.codage.service.BruitageService;
import local.baledo.root.codage.service.CodageService;

public class Start {

	public static void main(String[] args) {
		
//		int n = 5, k = 3;	
		int n = 5, k = 3;
		System.out.println("--------------- Matrice message ---------------");
		String message = "tamby";
		Matrice messageM = CodageService.toBinary(message, k);
		CodageService.affiche(messageM);
		System.out.println("--------------- Matrice generatrice ---------------");
		Matrice rest = CodageService.matriceGeneratrice(n,k);
		CodageService.affiche(rest);
		System.out.println("--------------- Matrice code ---------------");
		Matrice motDeCode = CodageService.getMotDeCode(rest, messageM);
		CodageService.affiche(motDeCode);
		System.out.println("--------------- Matrice bruitée ---------------");
		BruitageService.addNose(motDeCode);
		CodageService.affiche(motDeCode);
		System.out.println("--------------- Sub ---------------");
//		System.out.println(n-k);
		Matrice sub = rest.getSub(k);
//		CodageService.affiche(sub);
		System.out.println("--------------- Cas possibles ---------------");
		Matrice possibilite = CodageService.generateAllPossibilite(n);
//		CodageService.affiche(possibilite);
		
		System.out.println("--------------- Id ---------------");
		Matrice id = CodageService.getIdentity(n-k);
//		CodageService.affiche(id);
		System.out.println("--------------- Matrice de contrôle ---------------");
		Matrice controle = sub.concatCol(id);
//		CodageService.affiche(controle);
		System.out.println("--------------- Transposé des possibilités ---------------");
		Matrice tpossible = possibilite.getMatriceTranspose();
//		CodageService.affiche(tpossible);
		System.out.println("--------------- Syndrôme ---------------");
		Matrice syndrome;
		try {
			syndrome = CodageService.getSyndrome(controle, tpossible);
//			CodageService.affiche(syndrome);
			System.out.println("--------------- Tableau de décodage ---------------");
			TableauDecodage tab = CodageService.getTableauDecodage(syndrome, tpossible);
			System.out.println(tab);
			System.out.println("--------------- Correction ---------------");
			CodageService.corriger(motDeCode, controle, tab);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
