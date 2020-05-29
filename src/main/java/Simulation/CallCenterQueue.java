package Simulation;

import java.util.ArrayList;

public class CallCenterQueue extends Queue {
	
	private ArrayList<Machine> consRequests;
	private ArrayList<Machine> corpRequests;
	private int k;
	private ArrayList<Machine> offShift;

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
		offShift = new ArrayList();
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
					System.out.println("Queue size = " + (getQueueSize()));

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
					
					
					if(!delivered) {
						row.add(p); // Otherwise store it
						System.out.println("Queue size = " + (getQueueSize()));

					}
				}
				return true;
	}
	
	public boolean giveProductCorp(Product p)
	{
		printRequests();
		// Check if the machine accepts it
				if(corpRequests.size()<1) {
					//if no corporate requests
					row.add(p); // Otherwise store it
					System.out.println("Queue size = " + (getQueueSize()));

				}
				else
				{
					boolean delivered = false;
					while(!delivered & (corpRequests.size()>0))
					{
						if(p instanceof Corp || corpRequests.size()>k) {
						delivered=corpRequests.get(0).giveProduct(p);
						// remove the request regardless of whether or not the product has been accepted
						corpRequests.remove(0);
						}
					}
					if(!delivered) {
						row.add(p); // Otherwise store it
						System.out.println("Queue size = " + (getQueueSize()));
					}
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
		System.out.println("shift " + machine.eventlist.getTime() + " " + machine.shiftStart + " " + machine.shiftEnd);
		if(machine.eventlist.getTime()<machine.shiftStart) {
			System.out.println(machine.name + " not this shift yet");
			offShift.add(machine);
			return false;
		}
		if(machine.eventlist.getTime()>machine.shiftEnd) {
			System.out.println(machine.name + " This party's over");
			offShift.remove(machine);
			
			for(int i = 0; i<offShift.size(); i++) {
//				if(machine.eventlist.getTime()>offShift.get(i).shiftEnd) {
//					offShift.remove(machine);
//				}
				if(machine.eventlist.getTime()>offShift.get(i).shiftStart) {
					System.out.println(offShift.get(i).name + " is now on the clock");
					if(offShift.get(i) instanceof CSACorp) {
						corpRequests.add(offShift.get(i));
					}
					else if(offShift.get(i) instanceof CSACons) {
						consRequests.add(offShift.get(i));

					}
				}
				
				
			}
			return false;
		}
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
	
	public ArrayList getCorpRequests() {
		return corpRequests;
	}
	
	public ArrayList getConsRequests() {
		return consRequests;
	}
	
	public int getK() {
		return k;
	}
	

}
