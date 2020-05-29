package Simulation;

import Variates.Poisson;

import java.util.ArrayList;

public class SourceCorp extends Source{
	
	
	
	public SourceCorp(ProductAcceptor q,CEventList l,String n){
		super(q,l,n);
	}
	
	public SourceCorp(ProductAcceptor q,CEventList l,String n,double m){
		super(q,l,n,m);
	}
	
	public SourceCorp(ProductAcceptor q,CEventList l,String n,double[] ia){
		super(q,l,n,ia);
	}
	
	@Override
	public void execute(int type, double tme)
	{
		// show arrival
		System.out.println("Arrival at time = " + tme + " from " + name);
		// give arrived product to queue
		Corp p = new Corp();
		p.stamp(tme,"Creation",name);
		System.out.println("Sent to be queued or carried out by CSA corp");
		ArrayList<Machine> corpreqs = queue.getCorpRequests();
		for(Machine m:corpreqs) {
			System.out.println(m.name);
		}
		ArrayList<Machine> consreqs = queue.getConsRequests();
		for(Machine m:consreqs) {
			System.out.println(m.name);
		}
		
		queue.giveProductCorp(p);

		// generate duration
		if(meanArrTime>0)
		{
			double duration;
			if (list.getTime() > 720)
				duration = drawPoissonRandomExponential(20);
			else
				duration = drawPoissonRandomExponential(60);
			//System.out.println("NEEDED LIKE " + drawRandomExponential(meanArrTime) + " WE HAD: " + duration);
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

	public static double drawPoissonRandomExponential(double rate) {
		return Poisson.randomVariate(rate);
	}
}