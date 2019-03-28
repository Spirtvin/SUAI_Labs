package Helpers;

import Common.Messages;

import java.util.ArrayList;

public class Converter {
    public static class ArrayLists {
        public static Integer[] Convert(ArrayList<Integer> values) {
            Integer[] result = new Integer[values.size()];
            for (int i = 0; i < values.size(); i++)
                result[i] = values.get(i);
            return result;
        }

        public static ArrayList<Integer> Convert(Integer[] values) {
            ArrayList<Integer> result = new ArrayList<>();
            for (int i = 0; i < values.length; i++)
                result.add(values[i]);
            return result;
        }
    }

    public static class Arrays {
        public static int[] Convert(Integer[] values) {
            int[] result = new int[values.length];
            for (int i = 0; i < values.length; i++)
                result[i] = values[i].intValue();
            return result;
        }
    }

    public static class Integers {

        public static Boolean Convert(Integer value) throws Exception {
            if (value > -1 && value < 2) {
                return !(value == 0);
            } else
                throw new Exception(Messages.Exceptions.valueIncorrect);
        }

        public static Boolean Convert(Long value) throws Exception {
            if (value > -1 && value < 2) {
                return !(value == 0);
            } else
                throw new Exception(Messages.Exceptions.valueIncorrect);
        }
    }



    public static class Booleans {
        public static Integer Convert(Boolean value) {
            if (value)
                return 1;
            else
                return 0;
        }
    }
}
