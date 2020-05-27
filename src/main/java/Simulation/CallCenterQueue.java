package Simulation;

import java.util.ArrayList;

public class CallCenterQueue extends Queue {
	
	private ArrayList<Machine> consRequests;
	private ArrayList<Machine> corpRequests;
	private int k;

	public CallCenterQueue() {
		// TODO Auto-generated constructor stub
		super();
		consRequests = new ArrayList<>();
		corpRequests = new ArrayList<>();
		k = 3;
	}
	
	public CallCenterQueue(int kvalue) {
		// TODO Auto-generated constructor stub
		super();
		consRequests = new ArrayList<>();
		corpRequests = new ArrayList<>();
		k = kvalue;
	}
	
	public int getQueueSize() {
		return row.size();
	}
	
	public void printRequests() {
		for(Machine m:requests) {
			System.out.println(m.name);
		}
	}
	
	/**
	*	Offer a product to the queue
	*	It is investigated whether a machine wants the product, otherwise it is stored
	*/
	@Override
	public boolean giveProduct(Product p)
	{
		printRequests();
		// Check if the machine accepts it
				if(requests.size()<1)
					row.add(p); // Otherwise store it
				else
				{
					boolean delivered = false;
					while(!delivered & (requests.size()>0))
					{
						delivered=requests.get(0).giveProduct(p);
						// remove the request regardless of whether or not the product has been accepted
						requests.remove(0);
					}
					if(!delivered)
						row.add(p); // Otherwise store it
				}
				return true;
	}
	
	public boolean giveProductCons(Product p)
	{
		printRequests();
		// Check if the machine accepts it
				if(consRequests.size()<1) {
					row.add(p); // Otherwise store it
				}
				else
				{
					boolean delivered = false;
					while(!delivered & (consRequests.size()>0))
					{
						delivered=consRequests.get(0).giveProduct(p);
						// remove the request regardless of whether or not the product has been accepted
						consRequests.remove(0);
					}
					
					
					if(!delivered)
						row.add(p); // Otherwise store it
				}
				return true;
	}
	
	public boolean giveProductCorp(Product p)
	{
		printRequests();
		// Check if the machine accepts it
				if(corpRequests.size()<1)
					row.add(p); // Otherwise store it
				else
				{
					boolean delivered = false;
					while(!delivered & (corpRequests.size()>0))
					{
						delivered=corpRequests.get(0).giveProduct(p);
						// remove the request regardless of whether or not the product has been accepted
						corpRequests.remove(0);
					}
					if(!delivered)
						row.add(p); // Otherwise store it
				}
				return true;
	}
	
	/**
	*	Asks a queue to give a product to a machine
	*	True is returned if a product could be delivered; false if the request is queued
	*/
	@Override
	public boolean askProduct(Machine machine)
	{
		
		// This is only possible with a non-empty queue
				if(row.size()>0)
				{
					// If the machine accepts the product
					if(machine.giveProduct(row.get(0)))
					{
						row.remove(0);// Remove it from the queue
						return true;
					}
					else
						return false; // Machine rejected; don't queue request
				}
				else
				{
					if(machine instanceof CSACons) {
						consRequests.add(machine);
					}
					else if(machine instanceof CSACorp) {
						corpRequests.add(machine);
					}
					return false; // queue request
				}
			}
	

}
