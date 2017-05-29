package local.baledo.root.codage.object;

import java.util.ArrayList;

public class Matrice {
	short[][] data;
	
	

	/**
	 * @param data
	 */
	public Matrice(short[][] data) {
		this.data = data;
	}
	public void setValue(int i, int j, short value){
		this.data[i][j] = value;
	}
	public Matrice(int i, int j)
	{
		this.setLength(i,j);
	}
	public void setLength(int i, int j)
	{
		this.data = new short[i][j];
	}

	public short[][] getData() {
		return data;
	}

	public void setData(short[][] data) {
		this.data = data;
	}
	public Matrice multiply(final Matrice matrice2)
	{
		Matrice a = new Matrice(this.getRows(), matrice2.getColumns());
		int k,i,j;
		short value = 0;
				
		for (k=0; k<matrice2.getColumns(); k++)
		{
			for (i=0; i<this.getRows(); i++)
			{
				for (j=0; j<this.getColumns(); j++){
					value += this.getValue(i,j)*matrice2.getValue(j,k);
				}
				a.setValue(i,k,(short)(value%2));
				value = 0;
			}
		}
		
		return a;
	}
	public int getRows()
	{
		return this.data.length;
	}
	
	// retourne le nombre de colonne
	public int getColumns()
	{
		return this.data[0].length;
	}
	public short getValue(int i, int j)
	{
		return this.data[i][j];
	}
	public Matrice getSub(int row){
		Matrice ret = new Matrice(this.getRows()-row,this.getColumns());
//		System.out.println(ret.getRows()+"x"+ret.getColumns());
		for(int i=0;i<ret.getRows();i++){
			for(int j=0;j<ret.getColumns();j++){
				ret.setValue(i, j, this.getValue(row, j));
			}
			row++;
		}
		return ret;
	}
	public Matrice MatricePow(int n)
	{
		Matrice a = this;
		Matrice b = a;
		
		for (int i=0; i<n-1; i++)
			b = a.multiply(b);
			
		return b;
	}
	public Matrice concatCol(Matrice m){
		short[][] s = new short[data.length][data[0].length+m.getData()[0].length];
		short[][] mdata = m.getData();
		for(int i=0;i<s.length;i++){
			for(int j=0;j<s[0].length;j++){
				if(j<this.getColumns())s[i][j] = data[i][j];
				else s[i][j] = mdata[i][j-this.getColumns()];
			}
		}
		return new Matrice(s);
	}
	public Matrice getMatriceTranspose()
	{
		Matrice a = new Matrice(this.getColumns(), this.getRows());
		short tmp = 0;
		
		for (int i=0; i<a.getRows(); i++)
			for (int j=0; j<a.getColumns(); j++)
			{
				tmp = this.getValue(j,i);
				a.setValue(i,j,tmp);
			}
		
		
		return a;
	}
	public Nupplets getColumn(int col){
		short[] ret = new short[data.length];
		for(int i=0;i<ret.length;i++){
			ret[i] = data[i][col];
		}
		return new Nupplets(ret);
	}
	public ArrayList<Nupplets> getNupplets(){
		ArrayList<Nupplets> ret = new ArrayList<Nupplets>();
		for(int i=0;i<getColumns();i++){
			ret.add(getColumn(i));
		}
		return ret;
	}
	public void addNupplets(Nupplets n,int i){
		for(int j=0;j<n.data.length;j++){
			data[j][i] = (short)((data[j][i] + n.data[j])%2);
		}
	}
	public short getDeterminant()
	{
		Matrice a = null;
		short value = 0;
		System.out.println("row = "+this.getRows());
		System.out.println("col = "+this.getColumns());
		if (this.getRows() < 3 && this.getColumns() < 3)
			return (short)(this.getValue(0,0)*this.getValue(1,1) - this.getValue(1,0)*this.getValue(0,1));
		
		
		for (int j=0; j<this.getColumns(); j++)
		{
				a = this.getNewMatrice(0,j);
				value += (int)Math.pow(-1,j)*(this.getValue(0,j)*a.getDeterminant());
		}
		
		return value;
	}
	private Matrice getNewMatrice(int row, int columns)
	{
		Matrice a = new Matrice(this.getRows()-1, this.getColumns()-1);
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
	public Matrice getMatriceInverse()
	{
		Matrice a = new Matrice(this.getRows(), this.getColumns());
		Matrice tmp = null;
		long det = this.getDeterminant();
		
		for (int i=0; i<this.getRows(); i++)
			for (int j=0; j<this.getColumns(); j++)
			{
				tmp = this.getNewMatrice(i,j);
				a.setValue(i,j,(short)(Math.pow(-1,i+j)*(tmp.getDeterminant()/det)));
			}
			
		// on transpose la Matrice2 les coeffcients seront positionnÃ© de faÃ§on incorrect
		return a.getMatriceTranspose();
	}
}
