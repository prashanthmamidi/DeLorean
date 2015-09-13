package com.daiwa;

import static com.daiwa.COMMAND.valueOf;
import static java.lang.Long.parseLong;

public class Validation {

    public static Boolean validCommandType(String commandType) {
        try {
            valueOf(commandType);
        } catch (IllegalArgumentException ex) {
            System.out.println("Err Please provide a valid operation");
            return false;
        }
        return true;
    }
    public static Boolean isValidCreateOrUpdateInput(String[] input) {
        try {
            getId(input);
            getTimestamp(input);
            String data = input[3];
            if (input.length > 4) {
                System.out.println("Err Please provide a valid input");
                return false;
            }
        } catch (Exception ex) {
            System.out.println("Err Please provide a valid input");
            return false;
        }
        return true;
    }

    public static Boolean isValidDeleteInput(String[] input) {
        try {
            getId(input);
            if (input.length == 3) getTimestamp(input);
        } catch (Exception ex) {
            System.out.println("Err Please provide a valid input");
            return false;
        }
        return true;
    }

    public static Boolean isValidGetInput(String[] input) {
        try {
            getId(input);
            getTimestamp(input);
        } catch (Exception ex) {
            System.out.println("Err Please provide a valid input");
            return false;
        }
        return true;
    }

    public static Boolean isValidLatestInput(String[] input) {
        try {
            getId(input);
        } catch (Exception ex) {
            System.out.println("Err Please provide a valid input");
            return false;
        }
        return true;
    }

    private static Long getTimestamp(String[] input) throws Exception {
        if (input.length >= 3)
            return parseLong(input[2]);
        throw new Exception("Invalid input");
    }

    private static Integer getId(String[] input) throws Exception {
        if (input.length >= 2)
            return Integer.parseInt(input[1]);
        throw new Exception("Invalid input");
    }
}
