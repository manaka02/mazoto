package local.baledo.root.codage.object;
/** DÃ©finition de la classe Matrice2 (Matrix) dans lequel nous trouvons beaucoup
  * de mÃ©thodes, d'opÃ©rations sur les Matrice2s
  * 
  * Remarque : 
  * Cette classe est seulement un outils dans le but d'effectuer des calculs sur
  * les graphes. Ceci n'est pas une classe complÃ¨te pour la manipulation de  
  * Matrice2.
  * 
  * @author ASLAN Hikmet
  * @version 1.1
  */

public class Matrice2
{
	private long[][] coeff = null;
	private int diametre = 0;
	private int distance = 0;
	
	//----------------------------------------------//
	//					   CONSTRUCTOR							//
	//----------------------------------------------//
	/** Constructeur Matrice2
	  * @param
	  * int i - ligne
	  * int j - colonne 
	  */
	public Matrice2(int i, int j)
	{
		this.setLength(i,j);
	}
	
	public Matrice2()
	{
		this(0,0);
	}

	public Matrice2(long[][] mat)
	{
		this.coeff = mat;
	}

	//----------------------------------------------//
	//					  		 SETTER					   	//
	//----------------------------------------------//	
	// dÃ©finit une Matrice2 de type long[][]
	public void setMatrice2(long[][] mat)
	{
		this.coeff = mat;
	}
	
	// dÃ©finit une valeur Ã  la position i et j
	// i - ligne
	// j - col
	public void setValue(int i, int j, long value)
	{
		this.coeff[i][j] = value;
	}
	
	
	// on dÃ©finit la taille de la mtrice
	public void setLength(int i, int j)
	{
		this.coeff = new long[i][j];
	}
	
	
	//----------------------------------------------//
	//					  		 GETTER					   	//
	//----------------------------------------------//	
	// retourne la Matrice2 sous forme du type long[][]
	public long[][] getMatrice2()
	{
		return this.coeff;
	}
	
	// retourne le nombre de ligne
	public int getRows()
	{
		return this.coeff.length;
	}
	
	// retourne le nombre de colonne
	public int getColumns()
	{
		return this.coeff[0].length;
	}
	
	// retourne la valeur Ã  la position i et j
	public long getValue(int i, int j)
	{
		return this.coeff[i][j];
	}
	
	// retourne le dÃ©terminant d'une Matrice2
	public long getDeterminant()
	{
		Matrice2 a = null;
		long value = 0;
	
		if (this.getRows() < 3 && this.getColumns() < 3)
			return (this.getValue(0,0)*this.getValue(1,1) - this.getValue(1,0)*this.getValue(0,1));
		
		
		for (int j=0; j<this.getColumns(); j++)
		{
				a = this.getNewMatrice2(0,j);
				value += (int)Math.pow(-1,j)*(this.getValue(0,j)*a.getDeterminant());
		}
		
		return value;
	}
	
	// retourne la Matrice2 inverse de la Matrice2 this
	public Matrice2 getMatrice2Inverse()
	{
		Matrice2 a = new Matrice2(this.getRows(), this.getColumns());
		Matrice2 tmp = null;
		long det = this.getDeterminant();
		
		for (int i=0; i<this.getRows(); i++)
			for (int j=0; j<this.getColumns(); j++)
			{
				tmp = this.getNewMatrice2(i,j);
				a.setValue(i,j,(int)Math.pow(-1,i+j)*(tmp.getDeterminant()/det));
			}
			
		// on transpose la Matrice2 les coeffcients seront positionnÃ© de faÃ§on incorrect
		return a.getMatrice2Transpose();
	}
	
	
	/* Retourne une nouvelle Matrice2 mais en supprimant 
	 * la ligne row et la colonne columns
	 */
	private Matrice2 getNewMatrice2(int row, int columns)
	{
		Matrice2 a = new Matrice2(this.getRows()-1, this.getColumns()-1);
		int k = -1, m = 0;
		
		for (int i=0; i<this.getRows(); i++)
		{
			
			k++;
			
			if (i == row) 
			{	
				k--;
			 	continue;
			}

			m = -1;
			
			for (int j=0; j<this.getColumns(); j++)
			{
			
				m++;
							
				if (j==columns) 
				{
					m--;
					continue;					
				}
				
				a.setValue(k,m,this.getValue(i,j));
			}		

		}
		
		return a;
	}
	
	/**
	  * Retourne le nombre de combinaison a partir d'une Matrice2
	  * dÃ©finit a partir d'un graphe.
	  * @param 
	  * 	sA - sommet A
	  *	sB - sommet B
	  *	nb - Nombre de arrete (ou nombre de caractere du combinaison)
	  * @return
	  * 	long - nombre de combinaison possible entre 2 sommets
	  */
	public long getGrapheCombiCount(int sA, int sB, int nb)
	{
		if (sB > this.getRows() || sA > this.getColumns())
			return -1;
	
		Matrice2 a = this.Matrice2Pow(nb);
		
		return a.getValue(sB-1,sA-1);
	}
	
	
	// retourne la Matrice2 I en fonction de la mtrice this
	public Matrice2 getMatrice2Identity()
	{
		Matrice2 a = new Matrice2(this.getRows(),this.getColumns());
		
		for (int i=0; i<this.getRows(); i++)
			a.setValue(i,i,1);
		
		return a;		
	}
	
	// transpose la Matrice2 
	public Matrice2 getMatrice2Transpose()
	{
		Matrice2 a = new Matrice2(this.getColumns(), this.getRows());
		long tmp = 0;
		
		for (int i=0; i<a.getRows(); i++)
			for (int j=0; j<a.getColumns(); j++)
			{
				tmp = this.getValue(j,i);
				a.setValue(i,j,tmp);
			}
		
		
		return a;
	}
	
	// retourne la valeur de la trice de la Matrice2
	public long getTraceMatrice2()
	{
		long value = 0;
		
		for (int i=0; i<this.getRows(); i++)
			value += this.getValue(i,i);
		
		return value;
	}
	
	
	/** Retourne la distance (nombre d'arrete) entre 
	  * entre deux sommets sA et sB, tel que sA <= sB
	  * si sA > sB, mÃ©thode renvoi -1 pour erreur
	  */
	public long getDistanceGraphe(int sA, int sB)
	{
		long value = 0;
		
		if (sA > sB) 
			return -1;
		
		if (this.getValue(sB-1,sA-1) != 0)
			return (this.getValue(sB-1,sA-1));
		
		for (int i=sA; i<sB; i++)
			value += this.getValue(i-1, (i+1)-1);

		return value;
	}
	
	// GRAPHE
	// retourne la Matrice2 de distance 
	public Matrice2 getMatrice2DistanceGraphe()
	{
		Matrice2 a = new Matrice2(this.getRows(),this.getColumns());
		int n=1;
		
		for (int i=0; i<this.getRows(); i++)
			for (int j=0; j<this.getColumns(); j++)
			{
				 if (a.getValue(i,j) == 0)
						a = this.Matrice2Pow(n++);

			}

		// n-1 correspond a la distance 
		this.distance = n-1;
	
		return a;
	}
	
	// GRAPHE
	// retourne la distance de la Matrice2
	public int getDistance()
	{
		this.getMatrice2DistanceGraphe();
		return this.distance;
	}
	
	
	// retourne la Matrice2 compagnon en fonction de la Matrice2 this
	public Matrice2 getMatrice2Compagnon()
	{
		Matrice2 a = new Matrice2(this.getRows(),this.getColumns());
		
		for (int i=0; i<a.getRows()-1; i++)
			a.setValue(i+1,i,1);
		
			a.setValue(this.getRows()-1,this.getColumns()-1,-1);				
					
		return a;
	}
	
	
	// GRAPHE
	// retourne la Matrice2 diam
	public Matrice2 getMatrice2Diametre()
	{
		int n=1;
		Matrice2 ai = this.sumMatrice2(this.getMatrice2Identity());

		for (int i=0; i<this.getRows(); i++)
			for (int j=0; j<this.getColumns(); j++)
			{
				 if (ai.getValue(i,j) == 0)
						ai = this.Matrice2Pow(n++);

			}
			
		this.diametre = n-1;
		
		return ai;
	}
	
	// GRAPHE
	// retourne la valeur du diametre de la Matrice2 (ou graphe :))
	public int getDiametre()
	{
		this.getMatrice2Diametre();
		return this.diametre;
	}
	
	// GRAPHE
	// retourne les deux sommets les plus Ã©loignÃ©s
	public int[] getSommetPLusDistant()
	{
		int[] sommets = new int[2];
		Matrice2 m = this.Matrice2Pow(this.getDiametre()-1);
		byte n = 0;
		
		for (int i=0; i<m.getRows(); i++)
			for (int j=0; j<m.getColumns(); j++)
				if (m.getValue(i,j) == 0)
				{
					sommets[n++] = i+1;
				}
		
		return sommets;
	}
	
	//----------------------------------------------//
	//  		  			 OTHERS METHODS				   //
	//----------------------------------------------//	
	// multiplication
	public Matrice2 multiply(final Matrice2 Matrice2)
	{
		Matrice2 a = new Matrice2(this.getRows(), this.getColumns());
		int k,i,j;
		long value = 0;
				
		for (k=0; k<this.getColumns(); k++)
		{
						
			for (i=0; i<this.getRows(); i++)
			{
			
				for (j=0; j<this.getColumns(); j++)
					value += this.getValue(i,j)*Matrice2.getValue(j,k);

				a.setValue(i,k,value);
				value = 0;
			}
		}
		
		return a;
	}
	
	// addition
	public Matrice2 sumMatrice2(final Matrice2 Matrice2)
	{
		Matrice2 a = new Matrice2(this.getRows(), this.getColumns());
		
		for (int i=0; i<this.getRows(); i++)
			for (int j=0; j<this.getColumns(); j++)
				a.setValue(i,j,this.getValue(i,j)+Matrice2.getValue(i,j));
			
		return a;
	}
	
	// puissance -> M^n
	public Matrice2 Matrice2Pow(int n)
	{
		Matrice2 a = this;
		Matrice2 b = a;
		
		for (int i=0; i<n-1; i++)
			b = a.multiply(b);
			
		return b;
	}
	
	// soustraction
	public Matrice2 soustraction(final Matrice2 Matrice2)
	{
		Matrice2 a = new Matrice2(this.getRows(), this.getColumns());
		
		for (int i=0; i<this.getRows(); i++)
			for (int j=0; j<this.getColumns(); j++)
				a.setValue(i,j,this.getValue(i,j)-Matrice2.getValue(i,j));
			
		return a;	
	}
	
	// multiplication d'une Matrice2 par une constante k
	public Matrice2 multiplyByK(long k)
	{
		Matrice2 a = this;
		
		for (int i=0; i<this.getRows(); i++)
			for (int j=0; j<this.getColumns(); j++)
				a.setValue(i,j,this.getValue(i,j)*k);
		
		return a;
	}
	
	
	// division d'une Matrice2 par une constante k
	public Matrice2 divByK(long k)
	{
		Matrice2 a = this;
		
		for (int i=0; i<this.getRows(); i++)
			for (int j=0; j<this.getColumns(); j++)
				a.setValue(i,j,this.getValue(i,j)/k);
		
		return a;
	}
	
	// le fameux toString() :)
	public String toString()
	{
		String out = "";
	
		for (int i=0; i<this.getRows(); i++)
		{
			for (int j=0; j<this.getColumns(); j++)
				out +=this.coeff[i][j]+"\t ";
				
				out+="\n";
		}
				
		return out;
	}
	
	
	// definit si deux Matrice2s sont Ã©quivalentes
	public boolean equals(Matrice2 Matrice2)
	{
		for(int i=0; i<this.getRows(); i++)
			for (int j=0; j<this.getColumns(); j++)
				if (this.getValue(i,j) != Matrice2.getValue(i,j))
						return false;
		
		return true;
	} 
	
	//----------------------------------------------//
	//	   		  		 METHODS IS...					   //
	//----------------------------------------------//	
	// dÃ©termine si la Matrice2 est symetrique
	public boolean isSymetric()
	{
		if (this.getRows() == this.getColumns())
			return false;
			
		for(int i=0; i<this.getRows(); i++)
			for (int j=0; j<this.getColumns(); j++)
				if (this.getValue(i,j) != this.getValue(j,i))
						return false;
						
		return true;				
	}
	
	// dÃ©termine si la Matrice2 est triangulaire
	public boolean isTriangularMatrix()
	{
		for(int i=0; i<this.getRows(); i++)
			for (int j=1; j<this.getColumns(); j++)
				if (this.getValue(i,j) != 0)
						return false;		
		
		return true;
	}
	
	// dÃ©termine si la Matrice2 est une Matrice2 unitÃ©
	public boolean isUnitMatrix()
	{		
		return (this.equals(this.getMatrice2Identity()));
	}
	
	// dÃ©termine si la Matrice2 est inversible
	public boolean isInversible()
	{
		return (this.getDeterminant() != 0);
	}
	
	// determine si la mtrice contient au moins une valeur 0
	public boolean isZero()
	{
		for(int i=0; i<this.getRows(); i++)
			for (int j=0; j<this.getColumns(); j++)
				if (this.getValue(i,j) == 0)
						return true;			
						
		return false;
	}
	
	//----------------------------------------------//
	//	   		  	 LAUNCH PROGRAM					   //
	//----------------------------------------------//
	public static void main(String[] args)
	{
			// Matrice2 d'adjacence d'un graphe
			Matrice2 a = new Matrice2();
			a.setMatrice2(new long[][] { {0,1,1,0,0,0,0,0,0,0,0}, // 1
												 {1,0,1,1,1,0,0,0,0,0,0}, // 2
												 {1,1,0,0,1,0,0,0,0,0,0}, // 3
												 {0,1,0,0,0,1,0,0,0,0,0}, // 4
												 {0,1,1,0,0,0,1,0,0,0,0}, // 5
												 {0,0,0,1,0,0,1,1,1,0,0}, // 6
												 {0,0,0,0,1,1,0,0,0,1,0}, // 7
												 {0,0,0,0,0,1,0,0,1,0,1}, // 8
												 {0,0,0,0,0,1,0,1,0,1,1}, // 9
												 {0,0,0,0,0,0,1,0,1,0,1}, // 10
												 {0,0,0,0,0,0,0,1,1,1,0}  // 11
												});
																
		//System.out.println("Matrice2 A : \n"+a);				
		//System.out.println("Distance : \n"+a.Matrice2Pow(4));
		
		Matrice2 x = new Matrice2(new long[][] { {0,1,0,0}, {1,0,0,0}, {0,0,1,1},{0,0,0,1}});
		System.out.println("Det : \n" + x.getDeterminant());
		System.out.println("Matrice2 inverse : \n" + x.getMatrice2Inverse());

	}
}
