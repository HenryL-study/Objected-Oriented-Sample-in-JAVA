/**
 * 
 */
package poly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * @author Nonsense
 * {(12,23),(2,1),(5,4)} + {(12,3),(0,1),(5,4)}
 * {(12,23),(5,4)} + {(12,3),(0,1),(5,4)}
 * {(12,23),(2,1),(5,4)} + {(12,3),(0,1),(5,asdf4)}
 * {(12,23),(2,1),(5,4)} + {(12,3),(0,1),(5,4)} - {(12,23),(2,1),(5,4)} - {(12,3),(0,1),(5,4)}
 * {(12,23),(2,1),(5,4)} - {(12,23),(2,1),(5,4)}
 * {(1,2),(2,3)}+{}
 * -{(1,2),(2,3)}
 * {(-12,0)}
 * {}
 * {()}
 * {(,)}
 * {(0,0)}
 * {(,}
 */
public class Main {

	/**
	 * 
	 */
	public Main() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in ));
		String read = null;
		System.out.print("Please input your polynomials, the space will be automatically ignored.\n");
		Poly anspoly = null;
		Poly temp = null;
		//∂¡»Î ˝æ›
		try {
			read = br.readLine();
		  }catch (IOException e) {
			  e.printStackTrace();
			  return;
		  }
		if (read == null) {
			System.out.println("Please input something.");
			return;
		}
		read = read.replaceAll("\\s", "");
		String polys[] = read.split("}");
//		if (polys[0].equals("")) {
//			polys[0] = "{(0,0)}";
//		}
		read = read.replaceAll("[\\d\\,\\(\\)\\{\\}]", "");
		char symbols[] = read.toCharArray();
		int start = 1;
		try {
			if (polys[0].startsWith("+") || polys[0].startsWith("-")) {
				start = 0;
				anspoly = new Poly("{(0,0)");
			}else{
				anspoly = new Poly("{(0,0)");
				try {
					anspoly = anspoly.plus(new Poly(polys[0]));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
					return;
				}
			}
		} catch (PolyException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
			//e1.printStackTrace();
			return;
		}
		for (int i = start, sym = 0; i < polys.length; i++, sym++) {
			//System.out.println(polys[i].length());
			try {
				temp = new Poly(polys[i]);
			} catch (PolyException e1) {
				// TODO Auto-generated catch block
				System.out.println(e1.getMessage());
				return;
			}
			if (symbols[sym] == '+') {
				try {
					anspoly = anspoly.plus(temp);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
					return;
				}
			}else if (symbols[sym] == '-') {
				try {
					anspoly = anspoly.sub(temp);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
					return;
				}
			}
		}
		//char str[] = read.toCharArray();
		anspoly.print();
	}

}
