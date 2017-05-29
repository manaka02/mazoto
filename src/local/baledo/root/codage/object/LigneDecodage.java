package local.baledo.root.codage.object;

import java.util.ArrayList;

public class LigneDecodage {
	Nupplets syndrome;
	ArrayList<Nupplets> laterales;
	ArrayList<Nupplets> erreurs;
	
	
	
	/**
	 * @param syndrome
	 * @param laterales
	 * @param erreurs
	 */
	public LigneDecodage(Nupplets syndrome, ArrayList<Nupplets> laterales, ArrayList<Nupplets> erreurs) {
		this.setSyndrome(syndrome);
		this.setLaterales(laterales);
		this.setErreurs(erreurs);
	}
	public Nupplets getSyndrome() {
		return syndrome;
	}
	public void setSyndrome(Nupplets syndrome) {
		this.syndrome = syndrome;
	}
	public ArrayList<Nupplets> getLaterales() {
		return laterales;
	}
	public void setLaterales(ArrayList<Nupplets> laterales) {
		this.laterales = laterales;
	}
	public ArrayList<Nupplets> getErreurs() {
		return erreurs;
	}
	public void setErreurs(ArrayList<Nupplets> erreurs) {
		this.erreurs = erreurs;
	}
	@Override
	public String toString() {
		String desc = " "+syndrome.toString()+" | ";
		for (int i = 0; i < laterales.size(); i++) {
			desc += laterales.get(i).toString();
			if(i!=laterales.size()-1) desc += ",";
		}
		desc += " | ";
		for(int i = 0; i < erreurs.size(); i++){
			desc += erreurs.get(i).toString();
			if(i!=erreurs.size()-1) desc += ",";
		}
		return desc+"\n";
	}
	public boolean compareSyndrome(Nupplets syndrome2) throws Exception {
		return syndrome.compareTo(syndrome2);
	}
	
	

}
