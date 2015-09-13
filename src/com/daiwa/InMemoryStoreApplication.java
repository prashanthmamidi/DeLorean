package com.daiwa;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.daiwa.COMMAND.valueOf;
import static com.daiwa.Observation.getObservationFor;
import static java.lang.Math.ceil;

public class InMemoryStoreApplication {

    public static final String REGEX = " +";

    public static void main(String[] args) {
        int initialCapacity = (int) ceil(10000 / 0.75);
        Map<Integer, List<Observation>> inMemoryDataStore = new ConcurrentHashMap<>(initialCapacity);
        InMemoryStore inMemoryStore = new InMemoryStoreService();
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;

        while (flag) {
            Optional<Observation> maybeObservation = getObservationFor(scanner.nextLine().split(REGEX));
            if (maybeObservation.isPresent()) {
                Observation observation = maybeObservation.get();
                switch (valueOf(observation.getCommandType())) {
                    case QUIT:
                        flag = false;
                        break;
                    case CREATE:
                        inMemoryStore.create(inMemoryDataStore, observation);
                        break;
                    case UPDATE:
                        inMemoryStore.update(inMemoryDataStore, observation);
                        break;
                    case DELETE:
                        inMemoryStore.delete(inMemoryDataStore, observation);
                        break;
                    case GET:
                        inMemoryStore.get(inMemoryDataStore, observation);
                        break;
                    case LATEST:
                        inMemoryStore.latest(inMemoryDataStore, observation);
                }
            }
        }
    }

}

