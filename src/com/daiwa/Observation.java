package com.daiwa;

import java.util.Optional;

import static com.daiwa.COMMAND.*;
import static com.daiwa.Validation.validCommandType;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public class Observation {
    private Integer id;
    private Long timeStamp;
    private String data;
    private String commandType;

    public Observation(int id, long timeStamp, String data, String commandType) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.data = data;
        this.commandType = commandType;
    }

    public Observation(String commandType) {
        this.commandType = commandType;
    }

    public Observation(Integer id, Long timeStamp, String commandType) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.commandType = commandType;
    }

    public Observation(Integer id, String commandType) {
        this.id = id;
        this.commandType = commandType;
    }

    public Observation() {

    }

    public Integer getId() {
        return id;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public String getData() {
        return data;
    }

    public String getCommandType() {
        return commandType;
    }

    @Override
    public String toString() {
        return "com.daiwa.Observation{" +
                "id=" + id +
                ", timeStamp=" + timeStamp +
                ", data='" + data + '\'' +
                ", commandType='" + commandType + '\'' +
                '}';
    }

    public static Optional<Observation> getObservationFor(String[] input) {
        String commandType = input[0];
        if (validCommandType(commandType)) {
            if (commandType.equals(QUIT.name())) {
                return of(getDataStore(commandType));
            }
            if ((commandType.equals(CREATE.name()) || commandType.equals(UPDATE.name()))
                    && Validation.isValidCreateOrUpdateInput(input)) {
                return of(getDataStore(parseInt(input[1]), parseLong(input[2]), input[3], commandType));
            }

            if (commandType.equals(DELETE.name()) && Validation.isValidDeleteInput(input)) {
                if (input.length == 3)
                    return of(getDataStore(parseInt(input[1]), parseLong(input[2]), commandType));
                else
                    return of(getDataStore(parseInt(input[1]), commandType));
            }

            if (commandType.equals(GET.name()) && Validation.isValidGetInput(input)) {
                return of(getDataStore(parseInt(input[1]), parseLong(input[2]), commandType));
            }

            if (commandType.equals(LATEST.name()) && Validation.isValidLatestInput(input)) {
                return of(getDataStore(parseInt(input[1]), commandType));
            }
        }
        return empty();
    }


    private static Observation getDataStore(String operationType) {
        return new Observation(operationType);
    }

    private static Observation getDataStore(Integer id, String commandType) {
        return new Observation(id, commandType);
    }

    private static Observation getDataStore(Integer id, Long timestamp, String operationType) {
        return new Observation(id, timestamp, operationType);
    }

    private static Observation getDataStore(Integer id, Long timestamp, String data, String operationType) {
        return new Observation(id, timestamp, data, operationType);
    }
}
