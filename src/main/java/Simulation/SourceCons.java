package Simulation;

import Variates.Poisson;

import java.util.ArrayList;

public class SourceCons extends Source{
	
	
	
	public SourceCons(ProductAcceptor q,CEventList l,String n){
		super(q,l,n);
	}
	
	public SourceCons(ProductAcceptor q,CEventList l,String n,double m){
		super(q,l,n,m);
	}
	
	public SourceCons(ProductAcceptor q,CEventList l,String n,double[] ia){
		super(q,l,n,ia);
	}
	
	@Override
	public void execute(int type, double tme)
	{
		// show arrival
		System.out.println("Arrival at time = " + tme + " from " + name);
		// give arrived product to queue
		Cons p = new Cons();
		p.stamp(tme,"Creation",name);
		ArrayList<Machine> corpreqs = queue.getCorpRequests();
		for(Machine m:corpreqs) {
			System.out.println(m.name);
		}
		ArrayList<Machine> consreqs = queue.getConsRequests();
		for(Machine m:consreqs) {
			System.out.println(m.name);
		}
		if(queue.getConsRequests().size() < 1 & queue.getCorpRequests().size() > queue.getK()) {
			System.out.println("All consumer CSAs are busy, and there are more than k corporate CSAs available");
			queue.giveProductCorp(p);
		}
		else {
			System.out.println("Sent to either be queued or be carried out by CSA cons");
			queue.giveProductCons(p);
		}
		// generate duration
		if(meanArrTime>0)
		{
			double duration = drawNonStationaryRandomExponential(meanArrTime, 2);
			System.out.println("STUPID DURATION: " + duration);
			// Create a new event in the eventlist
			list.add(this,0,tme+duration); //target,type,time
		}
		else
		{
			interArrCnt++;
			if(interarrivalTimes.length>interArrCnt)
			{
				list.add(this,0,tme+interarrivalTimes[interArrCnt]); //target,type,time
			}
			else
			{
				list.stop();
			}
		}
	}

	private static double t = 0;
	public static double drawNonStationaryRandomExponential(double mean, int rateMax) {
		double uiidy = Math.random();

		while (uiidy > Poisson.randomVariate(rateMax) / rateMax) {
			t += drawRandomExponential(mean);
			uiidy = Math.random();
		}
		return Poisson.randomVariate(t);
	}
}