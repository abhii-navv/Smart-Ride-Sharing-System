package Uber.ride;

import java.util.*;

public class cmap {

    public static boolean allDigits(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String[] adrs(String address) {
        if (address == null || address.length() == 0) {
            return new String[0];
        }
        
        List<String> parts = new ArrayList<>();
        Scanner sc = new Scanner(address);
        while (sc.hasNext()) {
            parts.add(sc.next());
        }
        sc.close();
        
        return parts.toArray(new String[0]);
    }

    public static boolean validAddress(String address) {
        if (address == null || address.isEmpty()) {
            return false;
        }
        
        if (address.contains("Station") || address.contains("Airport") || 
            address.contains("Terminal") || address.contains("Downtown") ||
            address.contains("City Center") || address.contains("Tech Park") ||
            address.contains("University") || address.contains("Central Mall") ||
            address.contains("Old Town")) {
            return true;
        }
        
        String[] p = adrs(address);
        if (p.length < 3) return false;
        
        if (!(allDigits(p[0]) || p[0].length() == 2)) {
            return false;
        }
        
        String sp = p[1].toLowerCase();
        if (!(sp.equals("1st") || sp.equals("2nd") || sp.equals("3rd") ||
             (sp.length() == 3 && sp.charAt(1) == 't' && sp.charAt(2) == 'h' &&
              Character.isDigit(sp.charAt(0)) && sp.charAt(0) != '0'))) {
            return false;
        }
        
        String tp = p[2].toLowerCase();
        if (!(tp.equals("street") || tp.equals("avenue"))) {
            return false;
        }
        
        return true;
    }

    public static int[] getCordinates(String address) {
        int[] b = {-1, -1};
        
        if (address.contains("Downtown")) b = new int[]{5, 5};
        else if (address.contains("Airport")) b = new int[]{10, 10};
        else if (address.contains("Station")) b = new int[]{3, 7};
        else if (address.contains("Terminal")) b = new int[]{9, 9};
        else if (address.contains("City Center")) b = new int[]{6, 6};
        else if (address.contains("Tech Park")) b = new int[]{8, 2};
        else if (address.contains("University")) b = new int[]{4, 8};
        else if (address.contains("Central Mall")) b = new int[]{7, 4};
        else if (address.contains("Old Town")) b = new int[]{2, 3};
        else {
            String[] p = adrs(address);
            if (p.length < 3) {
                return b;
            }

            int fd = Character.getNumericValue(p[0].charAt(0));

            String sp = p[1].toLowerCase();
            int sd;
            if (sp.equals("1st")) sd = 1;
            else if (sp.equals("2nd")) sd = 2;
            else if (sp.equals("3rd")) sd = 3;
            else if (sp.length() == 3 && sp.charAt(1) == 't' && sp.charAt(2) == 'h') {
                sd = Character.getNumericValue(sp.charAt(0));
            } else {
                return b;
            }

            if (p[2].toLowerCase().equals("street")) {
                b[0] = fd;
                b[1] = sd;
            } else {  // avenue
                b[0] = sd;
                b[1] = fd;
            }
        }
        
        return b;
    }

    public static int distance(String from, String to) {
        if (!(validAddress(from) && validAddress(to))) {
            return -1;
        }
        
        int[] fb = getCordinates(from);
        int[] tb = getCordinates(to);
        
        if (fb[0] == -1 || fb[1] == -1 || tb[0] == -1 || tb[1] == -1) {
            return -1;
        }
        
        return Math.abs(fb[0] - tb[0]) + Math.abs(fb[1] - tb[1]);
    }

    public static void main(String args[]) {
        String address1 = "Home";
        String address2 = "Park";
        String address3 = "Police Station";
        String address4 = "Bus Station";

        System.out.println("Distance 1-2: " + distance(address1, address2));
        System.out.println("Distance 3-4: " + distance(address3, address4));

        // Original test
        System.out.println("Downtown Station to Airport Terminal 2: " + 
                          distance("Downtown Station", "Airport Terminal 2"));

        // New test cases
        System.out.println("City Center to Tech Park: " +
                          distance("City Center", "Tech Park"));
        
        System.out.println("University Campus to Central Mall: " +
                          distance("University Campus", "Central Mall"));

        System.out.println("Old Town to 3rd Avenue: " +
                          distance("Old Town", "5 3rd Avenue"));
    }
}
