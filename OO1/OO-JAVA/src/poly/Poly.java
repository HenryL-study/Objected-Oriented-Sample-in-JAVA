package poly;

/**
 * @author Nonsense
 *
 */

class PolyException extends Exception  
{  
    /**
	 * 
	 */
	private static final long serialVersionUID = 5860186296843087720L;

	public PolyException(String msg)  
    {  
        super(msg);  
    }  
}  

public class Poly {

	/**
	 * polylist 多项式数组
	 * num 多项式个数
	 */
	private static int MAX_NUM = 1000;
	private mono polylist[];
	private int num;
	
	public Poly(String str) throws PolyException{
		polylist = new mono[MAX_NUM];
		for (int i = 0; i < polylist.length; i++) {
			polylist[i] = new mono(0, 0);
		}
		// TODO Auto-generated constructor stub
		String regex = "[+-]?\\{(\\(-?0*\\d+\\,0*\\d+\\)\\,)*\\(-?0*\\d+\\,0*\\d+\\)";
		//Pattern p = Pattern.compile(regex);
		//Matcher m = p.matcher(str);
		num = 0;
		//System.out.println(str.matches(regex));
		if (str.length() == 0 || !str.matches(regex)) {
			throw new PolyException("Your format is wrong, please check your input.");
		}
		
		str = str.substring(1, str.length()-1);
		String pairs[] = str.split("\\,|\\(|\\)|\\{");
		for (int i = 0; i < pairs.length; i++) {
			while (pairs[i].equals("")) {
				i++;
			}
			//String number[] = pairs[i].split(",");
			if (num > MAX_NUM - 1) {
				throw new PolyException("Too many polys.");
			}
			int a = Integer.parseInt(pairs[i]);
			int b = Integer.parseInt(pairs[i+1]);
			if ((a > 100000000) || (a < -100000000) || (b < 0) || (b > 1000000)) {
				throw new PolyException("Too large coeff or index.");
			}
			polylist[num].setCoeff(Integer.parseInt(pairs[i]));
			polylist[num].setIndex(Integer.parseInt(pairs[i+1]));
			num++;
			i++;
		}
		
	}
	
	/**
	 * @param polylist2
	 */
	public Poly(mono[] polylist2, int num) {
		// TODO Auto-generated constructor stub
		polylist = new mono[MAX_NUM];
		for (int i = 0; i < polylist.length; i++) {
			polylist[i] = new mono(0, 0);
		}
		//System.arraycopy(this.polylist, 0, polylist2, 0, num);
		for (int i = 0; i < num; i++) {
			polylist[i].setCoeff(polylist2[i].getCoeff());
			polylist[i].setIndex(polylist2[i].getIndex());
		}
		this.num = num;
	}

	public void mergePoly()
	{
		for(int i=0;i<num;i++)
	    {
	        if(polylist[i+1].getIndex() == polylist[i].getIndex())
	        {
	        	polylist[i].setCoeff(polylist[i].getCoeff() + polylist[i+1].getCoeff());
	            num--;
	            for(int p=i+1;p<num;p++)
	            {
	            	polylist[p] = polylist[p+1];
	            }
	            polylist[num].setCoeff(0);
	            polylist[num].setIndex(0);              //删除最后一项
	        }
	    }
	}
	
	public Poly plus(Poly aPoly) throws Exception{
		int plusnum = aPoly.getNum();
		Poly result = new Poly(this.polylist, this.num);
		for (int i = 0; i < plusnum; i++) {
			boolean isfind = false;
			for (int k = 0; k < result.getNum(); k++) {
				if (result.getPoly_index_i(k) == aPoly.getPoly_index_i(i)) {
					result.setPoly_coeff_i(k, result.getPoly_coeff_i(k)+aPoly.getPoly_coeff_i(i));
					isfind = true;
				}
				
			}
			if (! isfind) {
				if (result.getNum() == MAX_NUM) {
					throw new ArrayIndexOutOfBoundsException("Too many number of terms");
				}
				result.setPoly_coeff_i(result.getNum(), aPoly.getPoly_coeff_i(i));
				result.setPoly_index_i(result.getNum(), aPoly.getPoly_index_i(i));
				result.setNum(result.getNum()+1);
			}
		}
		return result;
	}
	
	public Poly sub(Poly aPoly) throws Exception{
		int plusnum = aPoly.getNum();
		Poly result = new Poly(this.polylist, this.num);
		for (int i = 0; i < plusnum; i++) {
			boolean isfind = false;
			for (int k = 0; k < result.getNum(); k++) {
				if (result.getPoly_index_i(k) == aPoly.getPoly_index_i(i)) {
					result.setPoly_coeff_i(k, result.getPoly_coeff_i(k)-aPoly.getPoly_coeff_i(i));
					isfind = true;
				}
				
			}
			if (! isfind) {
				if (result.getNum() == MAX_NUM) {
					throw new ArrayIndexOutOfBoundsException("Too many number of terms");
				}
				result.setPoly_coeff_i(result.getNum(), 0 - aPoly.getPoly_coeff_i(i));
				result.setPoly_index_i(result.getNum(), aPoly.getPoly_index_i(i));
				result.setNum(result.getNum()+1);
			}
		}
		return result;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	public int getPoly_index_i(int i) {
		return this.polylist[i].getIndex();
	}
	
	public void setPoly_index_i(int i, int number) {
		this.polylist[i].setIndex(number);
	}
	
	public int getPoly_coeff_i(int i) {
		return this.polylist[i].getCoeff();
	}
	
	public void setPoly_coeff_i(int i, int number) {
		this.polylist[i].setCoeff(number);
	}
	
	public void print() {
		int isempty = 0;
		for (int i = 0; i < num; i++) {
			if (polylist[i].getCoeff() != 0) {
				System.out.println(polylist[i].getCoeff() + "," + polylist[i].getIndex());
				isempty = 1;
			}
		}
		if (isempty == 0) {
			System.out.println("0,0");
		}
	}

}
