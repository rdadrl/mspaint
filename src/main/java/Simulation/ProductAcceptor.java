package Simulation;

import java.util.ArrayList;

/**
 *	Blueprint for accepting products
 *	Classes that implement this interface can accept products
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public interface ProductAcceptor
{
	/**
	*	Method to have this object process an event
	*	@param p	The product that is accepted
        *       @return true if accepted
	*/
	public boolean giveProduct(Product p);

	public boolean giveProductCons(Product p);

	public boolean giveProductCorp(Product p);

	public ArrayList getCorpRequests();

	public ArrayList getConsRequests();

	public int getK();
}
