/**
 *	Example program for using eventlists
 *	@author Joel Karel
 *	@version %I%, %G%
 */

package Simulation;

public class Simulation {

    public CEventList list;
    public Queue queue;
    public Source source;
    public Sink sink;
    public Machine mach;
	

        /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	// Create an eventlist
	CEventList l = new CEventList();
	// A queue for the machine
	CallCenterQueue q = new CallCenterQueue(2);
	// A source
	SourceCons s1 = new SourceCons(q,l,"SourceCons 1", 20);
	SourceCorp s2 = new SourceCorp(q,l,"SourceCorp 2", 20);
//	Source s1 = new Source(q,l,"Source 1");
//	Source s2 = new Source(q,l,"Source 2");


	// A sink
	Sink si = new Sink("Sink 1");
	// A machine
	CSACons m1 = new CSACons(q,si,l,"Cons CSA 1");
//	CSACons m2 = new CSACons(q,si,l,"Cons CSA 2");
	CSACorp m3 = new CSACorp(q,si,l,"Corp CSA 3");
	CSACorp m4 = new CSACorp(q,si,l,"Corp CSA 4");
	//CSACorp m5 = new CSACorp(q,si,l,"Corp CSA 5");
	//CSACorp m6 = new CSACorp(q,si,l,"Corp CSA 6");
	//CSACorp m7 = new CSACorp(q,si,l,"Corp CSA 7");
	//CSACorp m8 = new CSACorp(q,si,l,"Corp CSA 8");



	// start the eventlist
	l.start(1440); // 2000 is maximum time

    si.saveAsCSV();
    }
    
}
