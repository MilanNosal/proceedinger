package pdf;

import java.util.regex.Pattern;

public class Constants {

    /**
     * Tieto tri su derivovatelne.
     */
    public static int introductionLength = 1;
    public static int titleLength = 1;
    public static int endingLength = 1;

    public static int contentsLength = 1;
    public static int indexLength = 1;

    public static String pathToBlank = "blank.pdf";
    public static String titlePdf = "title.pdf";
    public static String introductionPdf = "introduction.pdf";
    public static String endingPdf = "ending.pdf";
    public static String inputXML = "input.xml";

    public static String outputTex = "output.tex";
    public static String outputXML = "output.xml";
    public static String combinedOutputPdf = "combined.pdf";
    public static boolean sort = false;
    public static String papersFolder = "papers";

    public static String proceedingsHeader = "";

    public static String encoding = "UTF8"; //"Windows-1250";


    private static Pattern intPat = Pattern.compile("[0-9]+");


    @Deprecated
    private static final String off = "-introductionLength";
    @Deprecated
    private static final String tit = "-titleLength";
    @Deprecated
    private static final String tir = "-endingLength";

    private static final String con = "-contentsLength";
    private static final String ind = "-indexLength";

    private static final String ptb = "-blankPdf";
    private static final String tp = "-titlePdf";
    private static final String op = "-introductionPdf";
    private static final String ep = "-endingPdf";
    private static final String in = "-inputXML";

    private static final String out = "-outputTex";
    private static final String outXML = "-outputXML";
    private static final String com = "-combinedOutputPdf";
    private static final String sor = "-sort";
    private static final String pdb = "-papersFolder";


    private static final String ph = "-proceedingsHeader";

    private static final String enc = "-encoding";

    public static void initialize(String[] args) {
        int i = 0;
        for (; i < args.length - 1; i += 2) {
            switch (args[i]) {
                case ptb:
                    pathToBlank = args[i + 1];
                    break;
                case pdb:
                    papersFolder = args[i + 1];
                    break;
                case off:
                    if (intPat.matcher(args[i + 1]).matches()) {
                        introductionLength = Integer.parseInt(args[i + 1]);
                    } else {
                        System.err.println("Integer expected in -introductionLength.");
                    }
                    break;
                case tit:
                    if (intPat.matcher(args[i + 1]).matches()) {
                        titleLength = Integer.parseInt(args[i + 1]);
                    } else {
                        System.err.println("Integer expected in -titleLength.");
                    }
                    break;
                case tir:
                    if (intPat.matcher(args[i + 1]).matches()) {
                        endingLength = Integer.parseInt(args[i + 1]);
                    } else {
                        System.err.println("Integer expected in -endingLength.");
                    }
                    break;
                case con:
                    if (intPat.matcher(args[i + 1]).matches()) {
                        contentsLength = Integer.parseInt(args[i + 1]);
                    } else {
                        System.err.println("Integer expected in -contentsLength.");
                    }
                    break;
                case ind:
                    if (intPat.matcher(args[i + 1]).matches()) {
                        indexLength = Integer.parseInt(args[i + 1]);
                    } else {
                        System.err.println("Integer expected in -indexLength.");
                    }
                    break;
                case tp:
                    titlePdf = args[i + 1];
                    break;
                case op:
                    introductionPdf = args[i + 1];
                    break;
                case com:
                    combinedOutputPdf = args[i + 1];
                    break;
                case ep:
                    endingPdf = args[i + 1];
                    break;
                case in:
                    inputXML = args[i + 1];
                    break;
                case out:
                    outputTex = args[i + 1];
                    break;
                case outXML:
                    outputXML = args[i + 1];
                    break;
                case ph:
                    proceedingsHeader = args[i + 1];
                    break;
                case enc:
                    encoding = args[i + 1];
                    break;
                case sor:
                    sort = true;
                    i -= 1;
                    break;
                default:
                    System.err.println("Invalid switch " + args[i] + ".");
                    break;
            }
        }
        if (i < args.length && sor.equals(args[i])) {
            sort = true;
        }
    }
}
