package pdf.comparators;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class UTFStringComparator implements Comparator<String> {
    static final HashMap<Character, Integer> chars = new LinkedHashMap<>();
    
    static {
        chars.put(' ', 1);
        chars.put(',', 0);
        
        chars.put('\'', 1);
        chars.put('-', 1);
        chars.put('/', 1);
        chars.put('0', 2);
        chars.put('1', 3);
        chars.put('2', 4);
        chars.put('3', 5);
        chars.put('4', 6);
        chars.put('5', 7);
        chars.put('6', 8);
        chars.put('7', 9);
        chars.put('8', 10);
        chars.put('9', 11);
        
        
        
        chars.put('a', 18);
        chars.put('A', 18);
        chars.put('á', 20);
        chars.put('Á', 20);
        chars.put('Ä', 30);
        chars.put('ä', 30);
        chars.put('b', 40);
        chars.put('B', 40);
        chars.put('c', 50);
        chars.put('C', 50);
        chars.put('č', 60);
        chars.put('Č', 60);
        chars.put('d', 70);
        chars.put('D', 70);
        chars.put('ď', 80);
        chars.put('Ď', 80);
        
        chars.put('e', 90);
        chars.put('E', 90);
        chars.put('É', 100);
        chars.put('é', 100);
        chars.put('f', 110);
        chars.put('F', 110);
        chars.put('g', 120);
        chars.put('G', 120);
        chars.put('h', 130);
        chars.put('H', 130);
        
        chars.put('\\', 135);
        
        chars.put('i', 140);
        chars.put('I', 140);
        chars.put('í', 150);
        chars.put('Í', 150);
        chars.put('j', 160);
        chars.put('J', 160);
        chars.put('k', 170);
        chars.put('K', 170);
        chars.put('l', 180);
        chars.put('L', 180);
        chars.put('ĺ', 190);
        chars.put('Ĺ', 190);
        chars.put('ľ', 200);
        chars.put('Ľ', 200);

        
        chars.put('m', 210);
        chars.put('M', 210);
        chars.put('n', 220);
        chars.put('N', 220);
        chars.put('ň', 230);
        chars.put('Ň', 230);
        chars.put('o', 240);
        chars.put('O', 240);
        chars.put('ó', 250);
        chars.put('Ó', 250);
        chars.put('Ô', 260);
        chars.put('ô', 260);
        
        
        chars.put('p', 270);
        chars.put('P', 270);
        chars.put('q', 280);
        chars.put('Q', 280);
        chars.put('r', 290);
        chars.put('R', 290);
        chars.put('ŕ', 300);
        chars.put('Ŕ', 300);
        chars.put('s', 310);
        chars.put('S', 310);
        chars.put('š', 315);
        chars.put('Š', 315);
        
        chars.put('t', 320);
        chars.put('T', 320);
        chars.put('ť', 330);
        chars.put('Ť', 330);
        chars.put('u', 340);
        chars.put('U', 340);
        chars.put('ú', 350);
        chars.put('Ú', 350);
        chars.put('v', 360);
        chars.put('V', 360);
        chars.put('w', 370);
        chars.put('W', 370);
        
        chars.put('x', 380);
        chars.put('X', 380);
        chars.put('Y', 390);
        chars.put('y', 390);
        chars.put('Ý', 400);
        chars.put('ý', 400);
        chars.put('z', 410);
        chars.put('Z', 410);
        chars.put('ž', 420);
        chars.put('Ž', 420);
    }

    @Override
    public int compare(String o1, String o2) {
        int i = -1, j = -1;
        char first = ' ';
        char second = ' ';
        
        while ((chars.get(first)-chars.get(second))==0) {
            
            i++;
            j++;
            
            if(o1.length()<=i) {
                return -1;
            } else if(o2.length()<=j) {
                return 1;
            }
            
            if(o1.length()>(i+1) && "ch".equalsIgnoreCase(o1.substring(i, i+2))) {
                // riesime 'ch' pre prvy retazec
                first = '\\';
                i++;
            } else {
                first = o1.charAt(i);
            }
            if(o2.length()>(j+1) && "ch".equalsIgnoreCase(o2.substring(j, j+2))) {
                // riesime 'ch' pre druhy retazec
                second = '\\';
                j++;
            } else {
                second = o2.charAt(j);
            }
        }
        return (chars.get(first)-chars.get(second));
    }
    
}
