package poly;

/**
 * @author Nonsense
 *
 */
public class mono {

	/**
	 * 单项式节点
	 */
	private int coeff;      //系数
    private int index;     //幂
    
	public mono(int coeff, int index) {
		// TODO Auto-generated constructor stub
		this.coeff = coeff;
		this.index = index;
	}

	public int getCoeff() {
		return coeff;
	}

	public void setCoeff(int coeff) {
		this.coeff = coeff;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}