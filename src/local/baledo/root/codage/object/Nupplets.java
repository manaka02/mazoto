package local.baledo.root.codage.object;

public class Nupplets {
	short[] data;
	public Nupplets(short[] data) {
		this.data = data;
	}
	public boolean compareTo(Nupplets to) throws Exception{
		if(to.data.length != data.length) throw new Exception("Taille différente");
		for(int i=0;i<data.length;i++){
			if(data[i] != to.data[i]) return false;
		}
		return true;
	}
	@Override
	public String toString() {
		String ret = "(";
		for(int i=0;i<data.length;i++){
			ret += data[i];
			if(i!=data.length-1) ret += ",";
		}
		ret += ")";
		return ret;
	}
	public short[] getData() {
		return data;
	}
	public void setData(short[] data) {
		this.data = data;
	}
	public short weight(){
		short sum = 0;
		for(short item : getData()){
			sum += item;
		}
		return sum;
	}
	public void addNupplets(Nupplets n){
		for(int i=0;i<data.length;i++){
			data[i] = (short)((data[i]+n.data[i])%2);
		}
	}
}
