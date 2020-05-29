package Simulation;

public class CSACorp extends Machine {

	public CSACorp(Queue q, ProductAcceptor s, CEventList e, String n) {
		super(q, s, e, n);
		// TODO Auto-generated constructor stub
	}
	
	public CSACorp(Queue q, ProductAcceptor s, CEventList e, String n, double start, double end) {
		super(q, s, e, n, start, end);
		// TODO Auto-generated constructor stub
	}

	public CSACorp(Queue q, ProductAcceptor s, CEventList e, String n, double m) {
		super(q, s, e, n, m);
		// TODO Auto-generated constructor stub
	}

	public CSACorp(Queue q, ProductAcceptor s, CEventList e, String n, double[] st) {
		super(q, s, e, n, st);
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Override
	public boolean giveProduct(Product p)
	{
		// Only accept something if the machine is idle
		if(status=='i')
		{
			// accept the product
			product=p;
			// mark starting time
			product.stamp(eventlist.getTime(),"Production started",name);
			System.out.println("product " + (product.getTimes()).get(0) + ": production started in " + name);
			if(queue.getQueueSize() > 0){
//				System.out.println("Queue size = " + (queue.getQueueSize()-1));
			}			// start production
			startProduction();
			// Flag that the product has arrived
			return true;
		}
		// Flag that the product has been rejected
		else return false;
	}

	/**
	 *	Starting routine for the production
	 *	Start the handling of the current product with an exponentionally distributed processing time with average 30
	 *	This time is placed in the eventlist
	 */
	@Override
	public void startProduction()
	{
		// generate duration
		if(meanProcTime>0)
		{
			double duration = Variates.NormalTruncated.drawRandomNormalVariate(1.2, 3.6, 0.75);
			System.out.println("CSA Cons Duration:	" + duration);
			// Create a new event in the eventlist
			double tme = eventlist.getTime();
			eventlist.add(this,0,tme+duration); //target,type,time
			// set status to busy
			status='b';
		}
		else
		{
			if(processingTimes.length>procCnt)
			{
				eventlist.add(this,0,eventlist.getTime()+processingTimes[procCnt]); //target,type,time
				// set status to busy
				status='b';
				procCnt++;
			}
			else
			{
				eventlist.stop();
			}
		}
	}
    
    /**
	*	Method to have this object execute an event
	*	@param type	The type of the event that has to be executed
	*	@param tme	The current time
	*/
    @Override
	public void execute(int type, double tme)
	{
		// show arrival
		System.out.println(product.getStations().get(0) + " ----> Product " +(product.getTimes()).get(0) + " ----> " + name + " ----> finished at time = " + tme);
		// Remove product from system
		product.stamp(tme,"Production complete",name);
		sink.giveProduct(product);
		product=null;
		// set machine status to idle
		status='i';
		// Ask the queue for products
		queue.askProduct(this);
	}

}
