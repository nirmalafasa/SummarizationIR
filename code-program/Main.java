
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author alasu_000
 */
public class Main {

    public static void main(String[] args) {
        LinkedList hasilStem[] = new LinkedList[5];
        Preprocessing obj = null;
        Preprocessing objek = new Preprocessing();
        String kalimat[]=null;
        for (int i = 66; i <= 150; i++) {
            System.out.println("======================");
            System.out.println("====DOK-"+i+"==========");
            System.out.println("======================");
            String dokumen = objek.readFile(i + ".txt");
            String judul= objek.getJudul();
            System.out.println("Judul: "+judul);
            //System.out.println(dokumen);
            kalimat = MMR.makeSentences(dokumen);
            for(int j=0;j<kalimat.length;j++){
                System.out.println((1+j)+": "+kalimat[j]);
            }
//            obj = new Preprocessing(dokumen);
//            obj.Tokenization();
//            obj.Filtering();
//            hasilStem[i - 1] = obj.Stemming();
        }
        
//        List<String> bagofword = obj.union(hasilStem);
//        Collections.sort(bagofword);
//        for (int i = 0; i < bagofword.size(); i++) {
//            System.out.println(bagofword.get(i));
//        }
//        //query
//        LinkedList preQuery[] = new LinkedList[1];
//        String query = "cara menjaga atau menurunkan berat badan";
//        Preprocessing queryP = new Preprocessing(query);
//        queryP.Tokenization();
//        queryP.Filtering();
//        preQuery[0] = queryP.Stemming();
//
//        //weigthing
//        Weighting dokW = new Weighting(bagofword, hasilStem);
//        dokW.jumlahKata();
//        dokW.jumlahDf();
//        System.out.println("TF");
//        dokW.TF();
//        System.out.println("IDF");
//        dokW.IDF();
//        System.out.println("TFIDF");
//        dokW.TFIDF();
//        dokW.printTFdanDF();
//        dokW.printTFdanIDF();
//        System.out.println("\n===\n");
//        dokW.printTfIdf();
//        System.out.println("NORM");
//        dokW.Normalisasi();
//        dokW.printNorm();
//
//        //weighting query
//        Weighting queryW = new Weighting(bagofword, preQuery);
//        queryW.jumlahKata();
//        queryW.jumlahDf();
//        queryW.TF();
//        queryW.TFIDF(dokW.getIDF());
//        double[][] normQ = queryW.Normalisasi();
//
//        double[] cosSimQ = dokW.CosSim(normQ);
//        dokW.printCosSimQuery();
//        dokW.sort(cosSimQ);
//        int hasilIndex[] = dokW.getIndex();
//        for (int i = 0; i < hasilIndex.length; i++) {
//            System.out.println(hasilIndex[i]);
//        }
//        //dokW.retrieve();
//        //MMR
//        LinkedList<String> retrieve = dokW.retrieve();
//        String hasilRingkasan[] = new String[retrieve.size()];
//        for (int k = 0; k < retrieve.size(); k++) {
//            String dokumen = Preprocessing.readFile(retrieve.get(k));
//            String kalimat[] = MMR.makeSentences(dokumen);
//            LinkedList<String> hasilStem2[] = new LinkedList[kalimat.length];
//            for (int i = 0; i < kalimat.length; i++) {
//                System.out.println("===============" + (i + 1) + "================");
//                Preprocessing a = new Preprocessing(kalimat[i]);
//                a.Tokenization();
//                a.Filtering();
//                hasilStem2[i] = a.Stemming();
//            }
//
//            System.out.println("hasil union");
//            List<String> bagofword2 = obj.union(hasilStem2);
//
//            Weighting abcd = new Weighting(bagofword2, hasilStem2);
//            abcd.jumlahKata();
//            abcd.jumlahDf();
//            System.out.println("TF");
//            abcd.TF();
//            System.out.println("IDF");
//            abcd.IDF();
//            System.out.println("TFIDF");
//            abcd.TFIDF();
//            abcd.printTFdok(1);
//            abcd.printTFdok(2);
//            abcd.printTFdok(3);
//            abcd.printTFdok(4);
//            abcd.printTFdanIDF();
//            System.out.println("\n===\n");
//            abcd.printTfIdf();
//            System.out.println("NORM");
//            abcd.Normalisasi();
//            abcd.printNorm();
//            System.out.println("COSSIM");
//            for (int i = 0; i < kalimat.length; i++) {
//                abcd.CosSim(i);
//            }
//            abcd.printCosSim();
//            double[][] cosSim= abcd.getCosSim();
//            
//            Weighting query1 = new Weighting(bagofword2, preQuery);
//            query1.jumlahKata();
//            query1.jumlahDf();
//            query1.TF();
//            query1.TFIDF(abcd.getIDF());
//            System.out.println("VSM Query");
//            double[][] normQMMR = query1.Normalisasi();
//
//            double[] cosSimQMMR = abcd.CosSim(normQMMR);
//
//            MMR ringkasan = new MMR(cosSim, cosSimQMMR, kalimat);
//            System.out.println("hasil ringkasan : ");
//            String hasil[] = ringkasan.algorithm();
//
//            for (int i = 0; i < hasil.length; i++) {
//                hasilRingkasan[k] += hasil[i] + "\n";
//                System.out.println(hasil[i] + "\n");
//            }
//        }
//        for (int i = 0; i < hasilRingkasan.length; i++) {
//            System.out.println("Ringkasan " + i + " = " + hasilRingkasan[i]);
//        }
    }
}
