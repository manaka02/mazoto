package local.baledo.root.codage.service;

import java.util.concurrent.ThreadLocalRandom;

import local.baledo.root.codage.object.Matrice;

public class BruitageService {
	public static void addNose(Matrice motdecode){
		int i = ThreadLocalRandom.current().nextInt(0,motdecode.getRows());
		int j = ThreadLocalRandom.current().nextInt(0,motdecode.getColumns());
		if(motdecode.getValue(i, j) == 0)motdecode.setValue(i, j, (short)1);
		else motdecode.setValue(i, j, (short)0);
	}
}
