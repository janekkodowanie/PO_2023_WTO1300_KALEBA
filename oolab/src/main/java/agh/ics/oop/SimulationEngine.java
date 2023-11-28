package agh.ics.oop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {

    private static final int THREAD_POOL_SIZE = 4;

    private final Collection<Simulation> simulations;

    private final List<Thread> threads;

    private ExecutorService executorService;

    public SimulationEngine(Collection<Simulation> simulations) {
        this.simulations = simulations;
        this.threads = new ArrayList<>();
    }

    public void runSync() {
        for(Simulation simulation : simulations) {
            simulation.run();
        }
    }

    public void runAsync() {
        for (Simulation simulation : simulations) {
            Thread thread = new Thread(simulation);
            threads.add(thread);
        }
    }

    public void runAsyncInThreadPool() {
        this.executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        for (Simulation simulation : simulations) {
            executorService.submit(simulation);
        }
    }

    public void awaitSimulationsEnd() {
        try {
            for (Thread thread : threads) {
                thread.join();
            }
            executorService.shutdown();

            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }

        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
