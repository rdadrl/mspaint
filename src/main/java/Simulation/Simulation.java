/**
 *	Example program for using eventlists
 *	@author Joel Karel
 *	@version %I%, %G%
 */

package Simulation;

import java.util.ArrayList;

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
	// Initialize the CSAs
	ArrayList csas = new ArrayList();
	CSACons m1 = new CSACons(q,si,l,"Cons CSA 1",0,500);
	CSACons m2 = new CSACons(q,si,l,"Cons CSA 2",500,1440);
	CSACorp m3 = new CSACorp(q,si,l,"Corp CSA 3",0,500);
	CSACorp m4 = new CSACorp(q,si,l,"Corp CSA 4",0,500);
	CSACorp m5 = new CSACorp(q,si,l,"Corp CSA 5",500,1440);
	CSACorp m6 = new CSACorp(q,si,l,"Corp CSA 6",500,1440);
	CSACorp m7 = new CSACorp(q,si,l,"Corp CSA 7",500,1440);
	CSACorp m8 = new CSACorp(q,si,l,"Corp CSA 8",500,1440);
	csas.add(m1);
	csas.add(m2);
	csas.add(m3);
	csas.add(m4);
	csas.add(m5);
	csas.add(m6);
	csas.add(m7);
	csas.add(m8);
	
	//Calculate total cost of CSAs
	double payment = 0;
	for(int i = 0; i<csas.size(); i++) {
		if(csas.get(i) instanceof CSACons) {
			payment += 24*35;
		}
		if(csas.get(i) instanceof CSACorp) {
			payment += 24*60;
		}
	}
	// start the eventlist
	l.start(1440);
	System.out.println("first time-----------------------------------------------------------------");
	// 1440 minutes in 24 hours
	System.out.println("Costs: " + payment);
	
	// Performance measures
	int successCons1 = 0;
	int successCons2 = 0;
	int successCorp1 = 0;
	int successCorp2 = 0;
	int nCons = 0;
	int nCorp = 0;

	ArrayList<Product> ps = si.getProducts();
	for(int i = 0; i < ps.size(); i++) {
		Product p = ps.get(i);
		double timeTaken = (p.getTimes().get(1) - p.getTimes().get(0));
			if(p instanceof Cons) {
				nCons++;
				//90% of cons should be assisted within 5 min
				if(timeTaken < 5) {
					successCons1++;
				//95% of cons should be assisted within 10 min
				}	
				if(timeTaken < 10) {
					successCons2++;
				}	
				
			}
			else if (p instanceof Corp) {
				nCorp++;
				//95% of corp should be assisted within 3 min
				if(timeTaken < 3) {
					successCorp1++;
				}	
				else{
//					System.out.println(timeTaken);
				}
				//99% of corp should be assisted within 7 min
				if(timeTaken < 7) {
					successCorp2++;
				}	
				else{
//					System.out.println(timeTaken);
				}
			}
	}
	System.out.println("Performance: " + ((successCons1*100)/nCons) + "% of cons in 5 min. " + ((successCons2*100)/nCons) + "% of cons in 10 min. " + ((successCorp1*100)/nCorp) + "% of corp in 3 min. " + ((successCorp2*100)/nCorp) + "% of corp in 7 min. ");
	
//	double[] times = si.getTimes();
//	ArrayList<Double> arrivalTimes = new ArrayList();
//	ArrayList<Double> assistTimes = new ArrayList();
//
//	for(int i = 0; i<times.length; i += 3) {
//		arrivalTimes.add(times[i]);
//	}
//	for(int i = 1; i<times.length; i += 3) {
//		assistTimes.add(times[i]);
//	}
//	int successCount = 0;
//	for(int i = 0; i<assistTimes.size(); i++) {
//		if((assistTimes.get(i) - arrivalTimes.get(i)) < 5) {
//			successCount++;
//		}
//	}
//	System.out.println(successCount + " " + assistTimes.size() + " " + times.length/3 + " " +si.get);
	
	//The 3 shifts need different configurations for each
	

//	//config 1
//	CSACons m1 = new CSACons(q,si,l,"Cons CSA 1");
//	CSACorp m2 = new CSACorp(q,si,l,"Cons CSA 2");
//	
//	CallCenterQueue q = new CallCenterQueue(2);
//
//	SourceCons s1 = new SourceCons(q,l,"SourceCons 1", 20);
//	SourceCorp s2 = new SourceCorp(q,l,"SourceCorp 2", 20);
//	//start shift 1
//	l.start(480);
//	
//	
//	//config 2	
//	m1 = new CSACons(q,si,l,"Cons CSA 1");
//	m2 = new CSACorp(q,si,l,"Cons CSA 2");
//		
//	q = new CallCenterQueue(2);
//
//	s1 = new SourceCons(q,l,"SourceCons 1", 20);
//	s2 = new SourceCorp(q,l,"SourceCorp 2", 20);
//	//start shift 2
//	l.start(480);	
//	
//	
//	//config 3	
//	m1 = new CSACons(q,si,l,"Cons CSA 1");
//	m2 = new CSACorp(q,si,l,"Cons CSA 2");
//			
//	q = new CallCenterQueue(2);
//
//	s1 = new SourceCons(q,l,"SourceCons 1", 20);
//	s2 = new SourceCorp(q,l,"SourceCorp 2", 20);
//	//start shift 3
//	l.start(480);	
    }
    
}
