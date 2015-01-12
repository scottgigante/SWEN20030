package minimumFreeEnergyPath.core;

import java.io.FileNotFoundException;
import java.util.List;

import minimumFreeEnergyPath.weightedVertexGraph.WeightedVertexGraph;

import org.jgrapht.graph.DefaultWeightedEdge;


/** Finds the molecular shortest path between a point on the free energy surface and the minimum
 * @author Scott Gigante
 * @since 07-Jan-2015
 */
public class MinimumFreeEnergyPath {
	
	// constants
	private static final String OUTPUT_SUFFIX = "_path.dat";
	
	private static WeightedVertexGraph g;

	/** Main method run from command line. Requires tabulated free energy surface and x and y coordinates of the starting position
	 * @param args Command line arguments. Usage: java MinimumFreeEnergyPath <filename> <xcoord> <ycoord>
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			//printUsageMessage();
			args = new String[3];
			args[0] = "dat/free_energy_select_above_delete_yes_20_not-normalized.dat";
			args[1] = "30";
			args[2] = "0";
		}
		try {
			String fileName = args[0];
			double xStart = Double.parseDouble(args[1]);
			double yStart = Double.parseDouble(args[2]);
			g = FileUtility.readToGraph(fileName, xStart, yStart);
			
			List<DefaultWeightedEdge> path = MolecularShortestPath.findPathBetween(g, g.getStartVertex(), g.getEndVertex());
			
			FileUtility.writeFromPath(fileName + OUTPUT_SUFFIX, g, path);
		} catch (NumberFormatException e) {
			System.out.println("Coordinates must be numeric.");
			e.printStackTrace(System.out);
			printUsageMessage();
		} catch (FileNotFoundException e) {
			System.out.println("File " + args[0] + " not found.");
			System.exit(0);
		}
	}
	
	private static void printUsageMessage() {
		System.out.println("Usage: java MinimumFreeEnergyPath <filename> <xcoord> <ycoord>");
		System.exit(0);
	}
}
