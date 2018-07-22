
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alasu_000
 */
public class MMR {

    double[][] cosSim;
    double[] cosSimQ;
    int panjangDok;
    String[] dokumen;
    ArrayList<Integer> summary;

    public MMR(double[][] cosSim, double[] cosSimQ, String[] kalimat) {
        this.cosSim = cosSim;
        this.cosSimQ = cosSimQ;
        this.panjangDok = kalimat.length;
        this.dokumen=kalimat;
    }
    public static String[] makeSentences(String dok) {
//        String delimiters = "(?<=[.?!;])\\s+(?=\\p{Lu})";
        String dokumen= dok.replace('?', '.');
        dokumen= dokumen.replace('!', '.');
        String regex1="((?![.\\n\\s])[^.\\n\"]*(?:\"[^\\n\"]*[^\\n\".]\"[^.\\n\"]*)*"
                + "(?:(Prof|Dr|No|dr|SpOK|Drs|Ph)\\.|[^.])+"
                + "(?:\\.(com|net|COM|NET|org|[0-9]|Kes|id|Id|ID)|[^.])+(?:\"[^\"\\n]+\\.\"|\\.|(?=\\n)))";
//        String regex= "((?![.\\n\\s])"
//                + "[^.\\n\"]*"
//                + "(?:\"[^\\n\"]*[^\\n\".]\"[^.\\n\"]*)*"
//                + "(?:(Dr|No|dr|SpOK|Prof)\\.|[^.])+"
//                + "(?:\\.(com|net|COM|NET|org|[0-9])|[^.])+"
//                + "(?:\"[^\"\\n]+\\.\"|\\.|(?=\\n)))";
//        String pattern = "(?!\\Z)[\\n\\r]*((?:[^.\"]*\"[^\"]*\")+[^.\"]*(?:\\.|\\Z))|"
//				+ "([^.!?\\s]" // First char is non-punct, non-ws
//				+ "[^.!?]*" // Greedily consume up to punctuation.
//				+ "(?:" // Group for unrolling the loop.
//				+ "[.!?]" // (special) inner punctuation ok if
//				+ "(?!['\"]?\\s|$)" // not followed by ws or EOS.
//				+ "[^.!?]*" // Greedily consume up to punctuation.
//				+ ")*" // Zero or more (special normal*)
//				+ "[.!?]?" // Optional ending punctuation.
//				+ "['\"]?" // Optional closing quote.
//				+ "(?=\\s|$))";
//        String[] hasilKalimat = dok.split(regex);
//
//        System.out.println("Panjang kalimat = " + hasilKalimat.length);
//
//        for (String kalimat : hasilKalimat) {
//            System.out.println(kalimat);
//        }
       
        //String[] sentences = null;
        ArrayList<String> kalimat= new ArrayList<String>();
        Pattern re = Pattern.compile(regex1, Pattern.MULTILINE | Pattern.COMMENTS);
        Matcher reMatcher = re.matcher(dokumen);
        while (reMatcher.find()) {
            kalimat.add(reMatcher.group());
            System.out.println(reMatcher.group());
        } 
        String[] hasilKalimat= new String[kalimat.size()];
        for(int i=0;i<kalimat.size();i++){
            hasilKalimat[i]=kalimat.get(i);
        }
        return hasilKalimat;
    }
    
    public String[] algorithm() {
        double lambda = 0.7;
        summary = new ArrayList<Integer>();
        for (int t = 0; t < panjangDok; t++) {
            int bestSentence = -1;
            double bestMMR = -Double.MAX_VALUE;
            for (int i = 0; i < panjangDok; i++) {
                if (summary.contains(i)) {
                    continue;
                }
                double maxSim2 = 0.0;
                for (int j=0;j<summary.size(); j++) {
                    maxSim2 = Math.max(cosSim[i][summary.get(j)], maxSim2);
                    System.out.println("t = "+t+" i= "+i+" e= "+summary.get(j)+" MaxSim= "+maxSim2);
                }
                double tempMMR=(lambda * cosSimQ[i] - ((1 - lambda) * maxSim2));
                if (tempMMR > bestMMR) {
                    bestMMR = tempMMR;
                    bestSentence = i;
                    //System.out.println("bestMMR = "+bestMMR);
                    //System.out.println("bestSentence = "+bestSentence);
                }
            }
            System.out.println("BESTMMR = "+bestMMR);
            System.out.println("BEST= "+bestSentence);
            if(bestMMR>0){
                summary.add(bestSentence);
                System.out.println("HASIL RINGKASAN: "+summary.toString());
            }
        }
        String[] hasilMMR= new String[summary.size()];
        Collections.sort(summary);
            for(int i=0;i<summary.size();i++){
                System.out.println("index : "+summary.get(i));
                hasilMMR[i]=(dokumen[summary.get(i)]+" \n");
                System.out.println(hasilMMR[i]);
            }
       return hasilMMR;
    }
    public String getIndexRingkasan(){
        String hasilIndex="";
        hasilIndex+= "[ ";
        for(int i=0;i<summary.size();i++){
            if(i<summary.size()-1){
                hasilIndex += ( summary.get(i).toString() +" , ");
            }
            else{
                hasilIndex += ( summary.get(i).toString() );
            }
            
        }
        hasilIndex+= " ]";
        return hasilIndex;
    }
    public double getPersen(){
        double panjangRingkasan= (double)summary.size();
        double panjangDokumen= (double)panjangDok;
        double persen=0.0;
        persen= (panjangRingkasan / panjangDokumen)  *100;
        //System.out.println("panjang dok = "+panjangDok+ " , panjangriingkasan = "+summary.size() + " , persen = "+persen);
        return persen;
    }
}
