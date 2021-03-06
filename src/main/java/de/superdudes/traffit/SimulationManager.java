package de.superdudes.traffit;

import de.superdudes.traffit.controller.*;
import de.superdudes.traffit.dto.*;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.NonNull;
import org.apache.commons.lang3.SerializationUtils;

import java.util.concurrent.Semaphore;

public class SimulationManager {

	// =============================================================================================
	// Constants
	// =============================================================================================

	private static final int GEN_WAIT = 20;

	// =============================================================================================
	// Static fields
	// =============================================================================================

	private static int generationNr = 0;

	// The startingGrid to run
	private static StartingGrid startingGrid = new StartingGrid("default");
	private static StartingGrid originalStartingGrid = null;

	// Thread and semaphore for pausing, running the simulation
	private static Thread executingThread = null;
	private static Semaphore semaphore = new Semaphore(1);

	// Flag to listen to when to repaint vehicles in gui
	private static SimpleBooleanProperty genWasRendered = new SimpleBooleanProperty(false); // JAVAFX

	// =============================================================================================
	// Static methods
	// =============================================================================================

	// Load and save
	// ======================================================

	public static boolean load() {

		// Do not assign new, but use restoreFrom() so that references are effected
		final StartingGrid loadedStartingGrid = StartingGridController.instance().load();

		if (loadedStartingGrid == null) {
			return false;
		}

		startingGrid.restoreFrom(StartingGridController.instance().load());
		return true;
	}

	public static boolean save() {
		StartingGridController.instance().save(startingGrid);
		return true;
	}

	// Start, stop, ...
	// ======================================================

	public static void start() {
		resetSemaphore();

		if (executingThread == null) {
			originalStartingGrid = SerializationUtils.clone(startingGrid);

			executingThread = new Thread(SimulationManager::run);
			executingThread.setDaemon(true);
			executingThread.start();
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

		startingGrid.restoreFrom(originalStartingGrid);
		originalStartingGrid = null;

		// Reset generation number
		generationNr = 0;

		triggerGuiRepaint();
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

				StartingGridController.instance().render(startingGrid);
				for (Vehicle vehicle : startingGrid.getVehicles()) {
					VehicleController.instance().render(vehicle);
				}

				final Street street = startingGrid.getStreet();
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

				System.out.println("Generation: " + generationNr++);
				triggerGuiRepaint();

				// Release semaphore
				resetSemaphore();
			}
		} catch (InterruptedException e) {
			System.out.println("Simulation interrupted");

			// Do not release, already released ordinary in try block and then interrupted during FOLLOWING Thread.sleep()
			// semaphoreReleaseIfNecessary();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("An exception occurred during simulation");

			// So that a new start is able even if an exception occurred
			resetSemaphore();
		}
	}

	private static void resetSemaphore() {

		while (semaphore.availablePermits() < 1) {
			semaphore.release();
		}

		while (semaphore.availablePermits() > 1) {
			try {
				semaphore.acquire();
			} catch (InterruptedException e) {
				throw new InternalError("Won't happen");
			}
		}
	}

	private static void triggerGuiRepaint() {
		genWasRendered.setValue(true);
		genWasRendered.setValue(false);
	}

	// Setter and getter
	// ======================================================

	public static StartingGrid getStartingGrid() {
		return startingGrid;
	}

	public static void setStartingGrid(@NonNull StartingGrid startingGrid) {
		if (isStarted()) {
			throw new IllegalStateException("Not possible during run");
		}

		SimulationManager.startingGrid = startingGrid;
	}

	public static SimpleBooleanProperty getGenWasRendered() {
		return genWasRendered;
	}
}
