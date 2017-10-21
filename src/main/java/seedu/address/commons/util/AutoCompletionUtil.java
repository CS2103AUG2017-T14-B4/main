package seedu.address.commons.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import seedu.address.logic.commands.CommandList;
import seedu.address.logic.util.CommandWord;

/**
 * Helper function to enable auto-completition for command box
 */
public class AutoCompletionUtil {
    /**
     * Return a correct command word that is the closet to the one provided by user.
     * @param commandWord by the user
     * @return the closest correct command
     */
    public static String getClosestCommand(String commandWord) {
        HashMap<String, Integer> distanceMap = new HashMap<>();
        ArrayList<String> commandListString = new CommandList().getCommandList();
        List<CommandWord> commandListCommand = new ArrayList<>();

        for (String command : commandListString) {
            commandListCommand.add(new CommandWord(command,
                    levenshteinDistance(commandWord, commandWord.length(), command, command.length())));
        }
        commandListCommand.sort((CommandWord cm1, CommandWord cm2) -> cm1.distance - cm2.distance);

        return commandListCommand.get(0).commandWord;
    }
//    /**
//     * Sort a hashmap by values
//     * @param passedMap HashMap to be sorted
//     * @return a sorted LinkedHashMap
//     */
//    public static LinkedHashMap<String, Integer> sortHashMapByValues(
//            HashMap<String, Integer> passedMap) {
//        List<String> mapKeys = new ArrayList<>(passedMap.keySet());
//        List<Integer> mapValues = new ArrayList<>(passedMap.values());
//        Collections.sort(mapValues);
//        Collections.sort(mapKeys);
//
//        LinkedHashMap<String, Integer> sortedMap =
//                new LinkedHashMap<>();
//
//        Iterator<Integer> valueIt = mapValues.iterator();
//        while (valueIt.hasNext()) {
//            Integer val = valueIt.next();
//            Iterator<String> keyIt = mapKeys.iterator();
//
//            while (keyIt.hasNext()) {
//                String key = keyIt.next();
//                Integer comp1 = passedMap.get(key);
//                Integer comp2 = val;
//
//                if (comp1.equals(comp2)) {
//                    keyIt.remove();
//                    sortedMap.put(key, val);
//                    break;
//                }
//            }
//        }
//        return sortedMap;
//    }

    /**
     * Compare the distance between two string
     * @param s first string
     * @param len_s length of first string
     * @param t second string
     * @param len_t length of second string
     * @return levenshtein distance between 2 strings
     */
    public static Integer levenshteinDistance(String s, Integer len_s, String t, Integer len_t) {
        Integer cost;

    /* base case: empty strings */
        if (len_s == 0) return len_t;
        if (len_t == 0) return len_s;

    /* test if last characters of the strings match */
        if (s.charAt(len_s - 1) == t.charAt(len_t - 1)) {
            cost = 0;
        } else {
            cost = 1;
        }

    /* return minimum of delete char from s, delete char from t, and delete char from both */
        return Math.min(Math.min(levenshteinDistance(s, len_s - 1, t, len_t    ) + 1,
                levenshteinDistance(s, len_s    , t, len_t - 1) + 1),
                levenshteinDistance(s, len_s - 1, t, len_t - 1) + cost);
    }
}
