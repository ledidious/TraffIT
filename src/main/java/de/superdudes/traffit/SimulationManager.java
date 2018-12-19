package de.superdudes.traffit;

import java.util.concurrent.Semaphore;

import de.superdudes.traffit.controller.CellController;
import de.superdudes.traffit.controller.LaneController;
import de.superdudes.traffit.controller.StartingGridController;
import de.superdudes.traffit.controller.StreetController;
import de.superdudes.traffit.controller.StreetSignController;
import de.superdudes.traffit.controller.VehicleController;
import de.superdudes.traffit.dto.Cell;
import de.superdudes.traffit.dto.Lane;
import de.superdudes.traffit.dto.StartingGrid;
import de.superdudes.traffit.dto.Street;
import de.superdudes.traffit.dto.StreetSign;
import de.superdudes.traffit.dto.Vehicle;
import de.superdudes.traffit.test.TestUtils;
import lombok.NonNull;

public class SimulationManager {

	private static final int MAX_RUNNING_SIMULATION = 1;
	private static final int GEN_WAIT = 10;

	private static StartingGrid runningSimulation = null;
	private static Thread executingThread = null;
	private static Semaphore semaphore = new Semaphore(MAX_RUNNING_SIMULATION);

	public static void load(Integer id) {
		// todo implement when db is ready
	}

	public static void save(Integer id) {
		// todo implement when db is ready
	}

	public static void start() {
		if (executingThread != null) {
			executingThread = new Thread(SimulationManager::run);
			executingThread.start();
		} else {
			semaphore.release();
		}
	}

	public static void halt() {
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			throw new InternalError("Unwanted interrupt", e);
		}
	}
	
	public static void stop() {
		executingThread.interrupt();
	}

	public static boolean isStarted() {
		return executingThread != null;
	}
	
	public static boolean isRunning() {
		return isStarted() && executingThread.holdsLock(semaphore);
	}

	private static void run() {
		try {
			while (true) {
				semaphore.acquire();

				Thread.sleep(GEN_WAIT);

				StartingGridController.instance().render(runningSimulation);
				for (Vehicle vehicle : runningSimulation.getVehicles()) {
					VehicleController.instance().render(vehicle);
				}

				final Street street = runningSimulation.getStreet();
				StreetController.instance().render(street);

				for (Lane lane : street.getLanes()) {
					LaneController.instance().render(lane);

					for (Cell cell : lane.getCells()) {
						CellController.instance().render(cell);

						final StreetSign streetSign = cell.getStreetSign();
						if (streetSign != null) {
							StreetSignController.instance().render(streetSign);
						}
					}
				}

				// Output current simulation
				TestUtils.outputOnConsole(runningSimulation);
				
				semaphore.release();
			}
		} catch (InterruptedException e) {
			System.out.println("Simulation interrupted");
		}
	}

	public static StartingGrid getRunningSimulation() {
		return runningSimulation;
	}

	public static void setRunningSimulation(@NonNull StartingGrid startingGrid) {
		if (isRunning()) {
			throw new IllegalStateException("Not possible during run");
		}

		runningSimulation = startingGrid;
	}
}
