class Mytester extends GroovyTestCase {

}

class S{
	static void r(char[]b,int p){
		long u=0;
		if(--p<0)
			System.out.print(b);
		else
			if(b[p]>48)
				r(b,p);
			else{
				for(int i=81;i-->0;)
					u|=(p-i)%9*(p/9^i/9)*(p/27^i/27|p%9/3 ^i%9/3) == 0?1L<<b[i]:0;for(b[p]=58;--b[p]>48;)if((u>>b[p]&1)<1)r(b,p);
			}
	}

	public static void main(String[]a){
		r(a[0].toCharArray(),81);
	}
}
	

// SudokuSolver ??
def r(a){
	def i=a.indexOf(48);
	if(i<0)
		print a 
	else
		(('1'..'9') - (0..80).collect{j->g =
							{(int)it(i)==(int)it(j)};
							g{it/9} | g{it%9} | g{it/27} & g{it%9/3} ? a[j]:'0'}
		).each{r(a[0..<i]+it+a[i+1..-1])}
}


def myproblem ='200375169639218457571964382152496873348752916796831245900100500800007600400089001'

S.main(myproblem)
