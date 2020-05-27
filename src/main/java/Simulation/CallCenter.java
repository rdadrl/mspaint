package Simulation;

import java.util.ArrayList;

public class CallCenter extends Machine {
	
	private int k;
	private int corpReady;
	private int consReady;
	private ArrayList<Product> inProgress;

	public CallCenter(Queue q, ProductAcceptor s, CEventList e, String n) {
		super(q, s, e, n);
		// TODO Auto-generated constructor stub
	}
	
	public CallCenter(Queue q, ProductAcceptor s, CEventList e, String n, int kcorp, int maxCorporate, int maxConsumer) {
		super(q, s, e, n);
		k = kcorp;
		corpReady = maxCorporate;
		consReady = maxConsumer;
		inProgress = new ArrayList();
		
		// TODO Auto-generated constructor stub
	}

	public CallCenter(Queue q, ProductAcceptor s, CEventList e, String n, double m) {
		super(q, s, e, n, m);
		// TODO Auto-generated constructor stub
	}

	public CallCenter(Queue q, ProductAcceptor s, CEventList e, String n, double[] st) {
		super(q, s, e, n, st);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void execute(int type, double tme)
	{
		// show arrival
		if(product instanceof Cons) {
			System.out.println("Consumer call finished at time = " + tme);
		}
		if(product instanceof Corp) {
			System.out.println("Corporate customer call finished at time = " + tme);
//			System.out.println(k);
//			k++;
		}
//		System.out.println("Product finished at time = " + tme);
		// Remove product from system
		product.stamp(tme,"Production complete",name);
		sink.giveProduct(product);
		product=null;
		// set machine status to idle
		status='i';
		// Ask the queue for products
		queue.askProduct(this);
	}
	
	/**
	*	Let the machine accept a product and let it start handling it
	*	@param p	The product that is offered
	*	@return	true if the product is accepted and started, false in all other cases
	*/
        @Override
	public boolean giveProduct(Product p)
	{
		// Only accept something if the machine is idle
//		if(p instanceof Cons)
        if((p instanceof Cons && consReady > 0) || (p instanceof Corp && corpReady > 0))
		{
        	if(p instanceof Cons) {
        		consReady--;
        	}
        	if(p instanceof Corp) {
        		corpReady--;
        	}
			// accept the product
			product=p;
			// mark starting time
			product.stamp(eventlist.getTime(),"Production started",name);
			// start production
			startProduction();
			inProgress.add(product);
			System.out.println("InProgress size = " + inProgress.size());
			System.out.println("production started, " + product.getClass());
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
			double duration = drawRandomExponential(meanProcTime);
			// Create a new event in the eventlist
			double tme = eventlist.getTime();
			eventlist.add(this,0,tme+duration);//target,type,time
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
    
    public ArrayList<Product> getInProgress(){
    	return inProgress;
    }
        

}
