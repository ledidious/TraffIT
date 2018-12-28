package de.superdudes.traffit;

import de.superdudes.traffit.controller.*;
import de.superdudes.traffit.dto.*;
import de.superdudes.traffit.test.TestUtils;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.NonNull;

import java.util.concurrent.Semaphore;

public class SimulationManager {

	// =============================================================================================
	// Constants
	// =============================================================================================

	private static final int MAX_RUNNING_SIMULATION = 1;
	private static final int GEN_WAIT = 10;

	// =============================================================================================
	// Static fields
	// =============================================================================================

	// Fields for the simulation management
	private static StartingGrid runningSimulation = new StartingGrid("default");
	private static Thread executingThread = null;
	private static Semaphore semaphore = new Semaphore(MAX_RUNNING_SIMULATION);

	// Flag to listen to when to repaint vehicles in gui
	private static SimpleBooleanProperty genWasRendered = new SimpleBooleanProperty(true); // JAVAFX

	// =============================================================================================
	// Static methods
	// =============================================================================================

	// Load and save
	// ======================================================

	public static boolean load() {
		runningSimulation = StartingGridController.instance().load();
		return runningSimulation != null;
	}

	public static boolean save() {
		StartingGridController.instance().save(runningSimulation);
		return true;
	}

	// Start, stop, ...
	// ======================================================

	public static void start() {
		if (executingThread == null) {
			executingThread = new Thread(SimulationManager::run);
			executingThread.setDaemon(true);
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
		executingThread = null;
	}

	public static boolean isStarted() {
		return executingThread != null;
	}

	public static boolean isRunning() {
		// If executing thread waits for semaphore, semaphore has queued threads
		return isStarted() && !semaphore.hasQueuedThreads();
	}

	// Internal
	// ======================================================

	private static void run() {
		try {
			while (true) {
				Thread.sleep(GEN_WAIT);

				semaphore.acquire();

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

				// Trigger listener
				genWasRendered.setValue(true);
				genWasRendered.setValue(false);

				// Release semaphore
				semaphore.release();
			}
		} catch (InterruptedException e) {
			System.out.println("Simulation interrupted");
		}
	}

	// Setter and getter
	// ======================================================

	public static StartingGrid getRunningSimulation() {
		return runningSimulation;
	}

	public static void setRunningSimulation(@NonNull StartingGrid startingGrid) {
		if (isRunning()) {
			throw new IllegalStateException("Not possible during run");
		}

		runningSimulation = startingGrid;
	}

	public static SimpleBooleanProperty getGenWasRendered() {
		return genWasRendered;
	}
}
