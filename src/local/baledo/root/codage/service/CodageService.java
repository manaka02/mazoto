package local.baledo.root.codage.service;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import local.baledo.root.codage.object.LigneDecodage;
import local.baledo.root.codage.object.Matrice;
import local.baledo.root.codage.object.Nupplets;
import local.baledo.root.codage.object.TableauDecodage;

public class CodageService {
	public static Matrice toBinary(String message,int k){
		byte[] bs = message.getBytes();
		String messageStr = "";
		for(int i=0;i<bs.length;i++){
			messageStr += Integer.toBinaryString(bs[i]);
		}
		System.out.println(messageStr);
		short[][] retS = new short[messageStr.length()/k+1][k];
		int i=0;
		int j=0;
		for(char c : messageStr.toCharArray()){
			if(j<k){
				retS[i][j] = (short)Integer.valueOf(String.valueOf(c)).intValue();
			}
			if(j==k-1) {
				j=0;
				i++;
			}
			else j++;
		}
		return new Matrice(retS).getMatriceTranspose();
	}
	public static Matrice matriceGeneratrice(int n,int k){
		short[][] ret = new short[n][k];
		for(int i=0;i<n;i++){
			short count = 0;
			for(int j=0;j<k;j++){
				if(i==j && i<k){
					ret[i][j] = 1;
				}else if(i<k){
					ret[i][j] = 0;
				}else{
					ret[i][j] = (short)ThreadLocalRandom.current().nextInt(0,2);
					count += ret[i][j];
				}
				if(count<=0 && i>=k) i--;
			}
		}
		return new Matrice(ret);
	}
	public static Matrice getControle(Matrice generatrice,int n,int k){
		Matrice sub = generatrice.getSub(k);
		Matrice id = CodageService.getIdentity(n-k);
		return sub.concatCol(id);
	}
	public static Matrice getMotDeCode(Matrice generatrice,Matrice information){
		return generatrice.multiply(information);
	}
	
	/*public static Matrice getMatriceControle(Matrice generatrice){
		short[][] content = generatrice.getData();
		return new Matrice(content);
	}*/
	public static Matrice generateAllPossibilite(int dimension){
//        System.out.println("longueur +" + Math.pow(2, dimension));
        int longueur = (int)Math.pow(2, dimension);
        Matrice matrice = new Matrice(longueur,dimension);
        for(int i = 0; i < Math.pow(2, dimension) ; i++) {
            String s = String.format("%"+dimension+"s", Integer.toBinaryString(i)).replace(' ', '0');
            char[] listChar = s.toCharArray();
            for (int j = 0; j < listChar.length; j++) {
                matrice.setValue(i, j, Short.parseShort(String.valueOf(listChar[j])));
            }
        }
        return matrice;
    }
	public static Matrice getIdentity(int k){
		Matrice ret = new Matrice(k,k);
		for(int i=0;i<ret.getData().length;i++){
			ret.setValue(i, i, (short)1);
		}
		return ret;
	}
	public static Matrice getSyndrome(Matrice controle,Matrice possibilite) throws Exception{
		if(controle.getColumns() != possibilite.getRows()) throw new Exception("Taille différente");
		return controle.multiply(possibilite);
	}
	static ArrayList<Integer> getIndiceNupplets(Nupplets nup,ArrayList<Nupplets> list) throws Exception{
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for(int i=0;i<list.size();i++){
			if(nup.compareTo(list.get(i))) ret.add(i);
		}
		return ret;
	}
	static ArrayList<Nupplets>  getClasseLatérale(ArrayList<Integer> inds,Matrice tpossible){
		ArrayList<Nupplets> nuppletsC = tpossible.getNupplets();
		ArrayList<Nupplets> ret = new ArrayList<Nupplets>();
		for(Integer i : inds){
			ret.add(nuppletsC.get(i));
		}
		return ret;
	}
	public static TableauDecodage getTableauDecodage(Matrice syndrome,Matrice tpossible) throws Exception{
		ArrayList<Nupplets> doublon = new ArrayList<Nupplets>();
		ArrayList<LigneDecodage> lignes = new ArrayList<LigneDecodage>();
		for(Nupplets n : syndrome.getNupplets()){
			if(contains(n,doublon)) continue;
//			System.out.println("Classe latérale de :"+n);
			ArrayList<Integer> tmp = getIndiceNupplets(n, syndrome.getNupplets());
			ArrayList<Nupplets> classes = getClasseLatérale(tmp, tpossible);
			ArrayList<Nupplets> erreurs = getErreurs(classes);
//			afficheNupplets(classes);
			LigneDecodage temp = new LigneDecodage(n,classes,erreurs);
			lignes.add(temp);
			doublon.add(n);
		}
		return new TableauDecodage(lignes);
	}
	static short lessWeight(ArrayList<Nupplets> list){
		short ret = (short)(list.size()+1);
		for(Nupplets item : list){
			if(item.weight() < ret) ret = item.weight();
		}
		return ret;
	}
	private static ArrayList<Nupplets> getErreurs(ArrayList<Nupplets> classes) {
		ArrayList<Nupplets> ret = new ArrayList<Nupplets>();
		for(Nupplets item : classes){
			if(item.weight() <= lessWeight(classes)) ret.add(item);
		}
		return ret;
	}

	public static boolean contains(Nupplets n,ArrayList<Nupplets> doublon) throws Exception{
		for(Nupplets t : doublon){
			if(n.compareTo(t)) return true;
		}
		return false;
	}
	
	public static void affiche(Matrice m){
		short[][] matrice = m.getData();
		for(short[] s : matrice){
			for(short item : s){
				System.out.print(item+"\t");
			}
			System.out.println();
		}
	}
	public static void afficheNupplets(ArrayList<Nupplets> list){
		for(Nupplets n : list){
			System.out.println(n);
		}
	}
	public static ArrayList<Integer> corriger(Matrice bruitee,Matrice controle,TableauDecodage tabdec) throws Exception{
		ArrayList<Integer> ret = new ArrayList<Integer>();
		System.out.println();
		Matrice mcb = null;
		mcb = controle.multiply(bruitee);
		affiche(mcb);
		tabdec.corriger(bruitee, mcb,controle);
		System.out.println("---------- Corrigé -----------");
		affiche(bruitee);
		return ret;
	}
	public static boolean error(Matrice mcb) throws Exception {
		short[] test = new short[]{(short)0,(short)0};
		Nupplets t = new Nupplets(test);
		for(int i=0;i<mcb.getColumns();i++){
			if(!mcb.getColumn(i).compareTo(t)) return true;
		}
		return false;
	}
	public static Matrice codage(String message,int n,int k) throws Exception{
		Matrice messageM = CodageService.toBinary(message, k);
		Matrice rest = CodageService.matriceGeneratrice(n,k);
		Matrice motDeCode = CodageService.getMotDeCode(rest, messageM);
		CodageService.affiche(motDeCode);
		BruitageService.addNose(motDeCode);
		Matrice sub = rest.getSub(k);
		Matrice possibilite = CodageService.generateAllPossibilite(n);
		Matrice id = CodageService.getIdentity(n-k);
		Matrice controle = sub.concatCol(id);
		Matrice tpossible = possibilite.getMatriceTranspose();
		Matrice syndrome = CodageService.getSyndrome(controle, tpossible);
		TableauDecodage tab = CodageService.getTableauDecodage(syndrome, tpossible);
		CodageService.corriger(motDeCode, controle, tab);
		return motDeCode;
	}
	public static Matrice decoder(Matrice generatrice,Matrice code){
		return generatrice.getMatriceInverse();
	}
}
