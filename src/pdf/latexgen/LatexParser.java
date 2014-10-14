package pdf.latexgen;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class LatexParser {

    static final HashMap<Character, String> map = new LinkedHashMap<>();

    static {
        map.put('á', "\\'{a}");
        map.put('Á', "\\'{A}");
        map.put('Ä', "\\\"{A}");
        map.put('ä', "\\\"{a}");
        map.put('č', "\\v{c}");
        map.put('Č', "\\v{C}");
        map.put('ď', "\\v{d}");
        map.put('Ď', "\\v{D}");
        map.put('É', "\\'{E}");
        map.put('é', "\\'{e}");
        map.put('í', "\\'{i}");
        map.put('Í', "\\'{I}");
        map.put('ĺ', "\\'{l}");
        map.put('Ĺ', "\\'{L}");
        
        map.put('ľ', "\\v{l}");
        map.put('Ľ', "\\v{L}");
        map.put('ň', "\\v{n}");
        map.put('Ň', "\\v{N}");
        map.put('ó', "\\'{o}");
        map.put('Ó', "\\'{O}");
        map.put('Ô', "\\^{O}");
        map.put('ô', "\\^{o}");
        map.put('ŕ', "\\'{r}");
        map.put('Ŕ', "\\'{R}");
        
        map.put('š', "\\v{s}");
        map.put('Š', "\\v{S}");
        map.put('ť', "\\v{t}");
        map.put('Ť', "\\v{T}");
        map.put('ú', "\\'{u}");
        map.put('Ú', "\\'{U}");
        map.put('Ý', "\\'{Y}");
        map.put('ý', "\\'{y}");
        map.put('ž', "\\v{z}");
        map.put('Ž', "\\v{Z}");
        
        map.put('&', "\\&");
    }

    public void parseLatex(BufferedWriter bw, char[] latex) throws IOException {
        BufferedReader br = new BufferedReader(new CharArrayReader(latex));
        String line;
        while ((line = br.readLine()) != null) {
            line = parseLine(line);
            line = postParse(line);
            bw.write(line);
            bw.newLine();
        }
    }

    private String parseLine(String line) {
        StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0; i < line.length(); i++) {
            c = line.charAt(i);
            sb.append((map.containsKey(c))?map.get(c):c);
        }
        return sb.toString();
    }
    
    private String postParse(String line) {
        return line.replace(" - ", " -- ");
    }
}
