package Simulation;

import java.util.ArrayList;

/**
 *	Queue that stores products until they can be handled on a machine machine
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public class Queue implements ProductAcceptor
{
	/** List in which the products are kept */
	protected ArrayList<Product> row;
	/** Requests from machine that will be handling the products */
	protected ArrayList<Machine> requests;
	
	/**
	*	Initializes the queue and introduces a dummy machine
	*	the machine has to be specified later
	*/
	public Queue()
	{
		row = new ArrayList<>();
		requests = new ArrayList<>();
	}
	
	/**
	*	Asks a queue to give a product to a machine
	*	True is returned if a product could be delivered; false if the request is queued
	*/
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
			requests.add(machine);
			return false; // queue request
		}
	}
	
	/**
	*	Offer a product to the queue
	*	It is investigated whether a machine wants the product, otherwise it is stored
	*/
	public boolean giveProduct(Product p)
	{
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
	
	public int getQueueSize() {
		return row.size();
	}

	@Override
	public boolean giveProductCons(Product p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean giveProductCorp(Product p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList getCorpRequests() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList getConsRequests() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getK() {
		// TODO Auto-generated method stub
		return 0;
	}
}