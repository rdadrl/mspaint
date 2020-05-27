package Simulation;

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
		queue.giveProductCorp(p);
		// generate duration
		if(meanArrTime>0)
		{
			double duration = drawRandomExponential(meanArrTime);
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
}