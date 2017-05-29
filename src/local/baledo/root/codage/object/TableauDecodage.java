package local.baledo.root.codage.object;

import java.util.ArrayList;

import local.baledo.root.codage.service.CodageService;

public class TableauDecodage {
	ArrayList<LigneDecodage> liste;
	
	

	/**
	 * @param liste
	 */
	public TableauDecodage(ArrayList<LigneDecodage> liste) {
		this.setListe(liste);
	}

	public ArrayList<LigneDecodage> getListe() {
		return liste;
	}

	public void setListe(ArrayList<LigneDecodage> liste) {
		this.liste = liste;
	}

	@Override
	public String toString() {
		String desc = "";
		for(LigneDecodage dec : liste){
			desc += dec.toString();
		}
		return desc;
	}
	
	public void corriger(Matrice bruitee,Matrice controlee,Matrice controle) throws Exception{
		for(int i=0;i<controlee.getColumns();i++){
			Nupplets temp = controlee.getColumn(i);
			LigneDecodage tmpL = getLigne(temp);
			if(tmpL != null)
				rectifier(bruitee,controlee,controle,tmpL.getErreurs(),i);
		}
	}
	public LigneDecodage getLigne(Nupplets syndrome) throws Exception{
		for(LigneDecodage item : liste){
			if(item.compareSyndrome(syndrome)) return item;
		}
		return null;
	}
	void rectifier(Matrice bruitee,Matrice controlee,Matrice controle,ArrayList<Nupplets> erreurs,int ind) throws Exception{
		for(int i=0;i<erreurs.size();i++){
			bruitee.addNupplets(erreurs.get(i),ind);
			Matrice temp = controle.multiply(bruitee);
			controlee.setData(temp.getData());
			if(!CodageService.error(controlee)) break;
		}
	}
}
