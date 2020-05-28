package Simulation;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
/**
 *	A sink
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public class Sink implements ProductAcceptor
{
	/** All products are kept */
	private ArrayList<Product> products;
	/** All properties of products are kept */
	private ArrayList<Integer> numbers;
	private ArrayList<Double> times;
	private ArrayList<String> events;
	private ArrayList<String> stations;
	/** Counter to number products */
	private int number;
	/** Name of the sink */
	private String name;
	
	/**
	*	Constructor, creates objects
	*/
	public Sink(String n)
	{
		name = n;
		products = new ArrayList<>();
		numbers = new ArrayList<>();
		times = new ArrayList<>();
		events = new ArrayList<>();
		stations = new ArrayList<>();
		number = 0;
	}
	
        @Override
	public boolean giveProduct(Product p)
	{
		number++;
		products.add(p);
		// store stamps
		ArrayList<Double> t = p.getTimes();
		ArrayList<String> e = p.getEvents();
		ArrayList<String> s = p.getStations();
		for(int i=0;i<t.size();i++)
		{
			numbers.add(number);
			times.add(t.get(i));
			events.add(e.get(i));
			stations.add(s.get(i));
		}
		return true;
	}
	
	public int[] getNumbers()
	{
		numbers.trimToSize();
		int[] tmp = new int[numbers.size()];
		for (int i=0; i < numbers.size(); i++)
		{
			tmp[i] = (numbers.get(i)).intValue();
		}
		return tmp;
	}

	public double[] getTimes()
	{
		times.trimToSize();
		double[] tmp = new double[times.size()];
		for (int i=0; i < times.size(); i++)
		{
			tmp[i] = (times.get(i)).doubleValue();
		}
		return tmp;
	}

	public String[] getEvents()
	{
		String[] tmp = new String[events.size()];
		tmp = events.toArray(tmp);
		return tmp;
	}

	public String[] getStations()
	{
		String[] tmp = new String[stations.size()];
		tmp = stations.toArray(tmp);
		return tmp;
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

	public void saveAsCSV() {
        File file = new File(System.getProperty("user.dir") + "/results/result.csv");
        try {
            FileWriter outputfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outputfile);

            String[] header = { "creation_time", "creation_station", "production_start_time", "production_start_station", "production_complete_time", "production_complete_station"};
            writer.writeNext(header);

            for (Product p : products) {
                ArrayList<Double> times = p.getTimes();
                ArrayList<String> stations = p.getStations();

                String[] rowData = {Double.toString(times.get(0)), stations.get(0), Double.toString(times.get(1)), stations.get(1), Double.toString(times.get(2)), stations.get(2)};
                writer.writeNext(rowData);
            }
            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String toString() {
	    String result = "";
        for (Product p : products) {
            ArrayList<Double> times = p.getTimes();
            ArrayList<String> events = p.getEvents();
            ArrayList<String> stations = p.getStations();

            String printString = "Product:{ times: {";

            for (Double t : times) {
                printString += "[" + t + "]";
            }
            printString += " }, events: {";
            for (String e : events) {
                printString += "[" + e + "]";
            }
            printString += " }, stations: {";
            for (String s : stations) {
                printString += "[" + s + "]";
            }
            printString += " }};";

            result+= printString + "\n";
        }
        return result;
    }
}