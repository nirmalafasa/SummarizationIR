/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.LinkedList;
import java.util.List;

/**
 * //
 *
 *
 * @author nirmalafasa
 */
public class Weighting {

    LinkedList<String> dokumen[],hasilcossim;
    List<String> term;
    int bnykDokumen, tdkRetrieve=0;
    int[] jumlahDf, urutan,urutanKalimat;
    int[][] jumlahKata;
    double[][] hasilTF, hasilTFIDF,hasilNorm, hasilCosSim3;
    double[] hasilIDF,hasilCosSim, hasilCosSim1,hasilCosSimQ;
    int[] hasilIndex, indexFinal;

    public Weighting(){
    }
    
    public Weighting(List<String> term, LinkedList<String> list[]) {
//        dok1=a;
//        dok2=b;
//        dok3=c;
        hasilcossim= new LinkedList<String>();
        this.term = term;
        dokumen = list;
        this.bnykDokumen = list.length;
        jumlahKata = new int[this.term.size()][bnykDokumen];
        jumlahDf = new int[this.term.size()];
        hasilTF = new double[this.term.size()][bnykDokumen];
        hasilTFIDF = new double[this.term.size()][bnykDokumen];
        hasilIDF = new double[this.term.size()];
        hasilNorm = new double[this.term.size()][bnykDokumen];
        hasilCosSim = new double[bnykDokumen];
        hasilCosSim1 = new double[bnykDokumen];
        hasilCosSim3 = new double[bnykDokumen][bnykDokumen];
    }

    public int[][] jumlahKata() {
        for (int i = 0; i < term.size(); i++) {
            for (int j = 0; j < dokumen.length; j++) {
                for (int k = 0; k < dokumen[j].size(); k++) {
                    if (term.get(i).equalsIgnoreCase(dokumen[j].get(k))) {
                        jumlahKata[i][j] += 1;
                    } else {
                        jumlahKata[i][j] += 0;
                    }
                }
            }
        }
        return jumlahKata;
    }

    public int[] jumlahDf() {
        for (int i = 0; i < term.size(); i++) {
            for (int j = 0; j < bnykDokumen; j++) {
                if (jumlahKata[i][j] >= 1) {
                    jumlahDf[i] += 1;
                } else {
                    jumlahDf[i] += 0;
                }
            }
        }
        return jumlahDf;
    }

    public double[][] TF() {
        for (int i = 0; i < term.size(); i++) {
            for (int j = 0; j < bnykDokumen; j++) {
                if (jumlahKata[i][j] == 0) {
                    hasilTF[i][j] = 0;
                } else {
                    hasilTF[i][j] = 1 + Math.log10(jumlahKata[i][j]);
                }
            }
        }
        return hasilTF;
    }

    public double[] IDF() {
        for (int i = 0; i < term.size(); i++) {
            if (jumlahDf[i] == 0) {
                hasilIDF[i] = 0;
            } else {
                double nilai= (double)bnykDokumen/jumlahDf[i];
                hasilIDF[i] = Math.log10(nilai);
//                if(i==3){
//                    System.out.println(nilai);
//                    System.out.println(bnykDokumen +" , "+ jumlahIdf[i]+ " , "+ hasilIDF[i]);
//                }
            }

        }
        return hasilIDF;
    }
    
    public double[][] TFIDF() {
        for (int i = 0; i < term.size(); i++) {
            for (int j = 0; j < bnykDokumen; j++) {
                hasilTFIDF[i][j] = hasilTF[i][j] * hasilIDF[i];
            }
        }
        return hasilTFIDF;
    }
    public double[][] TFIDF(double[] hasilIDF) {
        for (int i = 0; i < term.size(); i++) {
            for (int j = 0; j < bnykDokumen; j++) {
                hasilTFIDF[i][j] = hasilTF[i][j] * hasilIDF[i];
            }
        }
        return hasilTFIDF;
    }
    
    public double[] getIDF(){
        return hasilIDF;
    }
    public double[][] Normalisasi(){
        double[] pembagi=new double[bnykDokumen];
        for(int i=0;i<bnykDokumen;i++){
            for(int j=0;j<term.size();j++){
                pembagi[i]+=Math.pow(hasilTFIDF[j][i],2);
            }
            pembagi[i]=Math.sqrt(pembagi[i]);
        }
        for(int i=0;i<term.size();i++){
            for(int j=0;j<bnykDokumen;j++){
                if(pembagi[j]==0){
                    hasilNorm[i][j]=0;
                }
                else{
                    hasilNorm[i][j]=hasilTFIDF[i][j]/pembagi[j];
                }
            }
            
        }
        return hasilNorm;
    }
    public double[][] CosSim(int sim1){
        
        for(int i=0;i<term.size();i++){
            for(int j=0;j<bnykDokumen;j++){
                
                    hasilCosSim3[sim1][j]+=(hasilNorm[i][sim1]*hasilNorm[i][j]); 
                
            }
        }
//        for(int i=0;i<hasilCosSim.length;i++){
//            System.out.println("hasil CosSim "+sim1+"& "+(i)+" = "+ hasilCosSim3[sim1][i]);
//        }
        //System.out.println("byk kata = "+bnykDokumen);
        
        return hasilCosSim3;
    }

    public double[] CosSim(double[][] normQ){
        hasilCosSimQ= new double[bnykDokumen];
        for(int i=0;i<term.size();i++){
            for(int j=0;j<bnykDokumen;j++){
                    hasilCosSimQ[j]+=(normQ[i][0]*hasilNorm[i][j]); 
                
            }
        }
        for(int i=0;i<hasilCosSimQ.length;i++){
            System.out.println("hasil CosSim query& "+(i)+" = "+ hasilCosSimQ[i]);
            if(hasilCosSimQ[i]==0){
                tdkRetrieve++;
            }
        }
        System.out.println("byk kata = "+bnykDokumen);
        
        return hasilCosSimQ;
    }
    public double[] sort(double[] data){
        int n = data.length;
        int index[]= new int[n];
        for(int i=0;i<n;i++){
            index[i]=i;
        }
        double temp = 0; 
        int tempIdx=0;
         for(int i=0; i < n; i++){  
                 for(int j=1; j < (n-i); j++){  
                          if(data[j-1] < data[j]){  
                                 //swap elements  
                                 temp = data[j-1];
                                 tempIdx= index[j-1];
                                 data[j-1] = data[j];
                                 index[j-1]= index[j];
                                 data[j] = temp;  
                                 index[j]= tempIdx;
                         }  
                          
                 }  
         }

         hasilIndex=index;
         return data;
    }
//    public int[] getIndex(){
//        return hasilIndex;
//    }
    public int[] getIndex(){
        return indexFinal;
    }
    public LinkedList<String> retrieve(){
        indexFinal= new int[hasilCosSimQ.length-tdkRetrieve];
        for(int i=0;i<(hasilCosSimQ.length-tdkRetrieve);i++){
            indexFinal[i]=hasilIndex[i]+1;
            hasilcossim.add((hasilIndex[i]+1)+".txt");
            System.out.println(hasilcossim.get(i));
        }
        return hasilcossim;
    }
//    public double[] CosSimSorted(){
//        int n = hasilCosSim.length;  
//        double temp = 0;
//        int temp1=0;  
//        double[] arr=hasilCosSim;
//        urutan= new int[arr.length];
//         for(int i=0; i < n; i++){  
//             
//                 for(int j=1; j < (n-i); j++){  
//                          if(arr[j-1] < arr[j]){  
//                                 //swap elements  
//                                 temp = arr[j-1];  
//                                 arr[j-1] = arr[j];  
//                                 arr[j] = temp;
//                         }  
//                          
//                 }
//         }
//         for(int i=0;i<arr.length;i++){
//             System.out.println("i = "+i+" "+hasilCosSim1[i]);
//         }
//         for(int i=0;i<arr.length;i++){
//             for(int j=0;j<hasilCosSim1.length;j++){
//                 if(arr[i]==hasilCosSim1[j]){
//                     urutan[i]=j;
//                     hasilCosSim1[j]=1;
//                     break;
//                 }
//             }
//         }
//         
//        
//        
//        return hasilCosSim;
//    }
//    public double[] sortA(double[][] data,int x, String[] kategori){
//        hasilkategori=kategori;
//        double[] arr=new double[data.length-1];
//        for(int i=0;i<data.length;i++){
//            if(x!=i){
//                arr[i]=data[i][x];
//            }
//        }
//        int n = arr.length;  
//        double temp = 0; 
//        String tempSt="";
//         for(int i=0; i < n; i++){  
//                 for(int j=1; j < (n-i); j++){  
//                          if(arr[j-1] < arr[j]){  
//                                 //swap elements  
//                                 temp = arr[j-1];
//                                 tempSt= hasilkategori[j-1];
//                                 arr[j-1] = arr[j];
//                                 kategori[j-1]=hasilkategori[j];
//                                 arr[j] = temp;  
//                                 hasilkategori[j]=tempSt;
//                         }  
//                          
//                 }  
//         }
//         return arr;
//    }
    


    public void printTF(int bnykDokumen) {
        for (int i = 0; i < term.size(); i++) {
            for (int j = 0; j < bnykDokumen; j++) {
                System.out.println(hasilTF[i][j]);
            }
        }
    }

    public void printIDF() {
        for (int i = 0; i < term.size(); i++) {
            System.out.println(hasilIDF[i]);
        }
    }

    public void printTFIDF() {
        for (int i = 0; i < term.size(); i++) {
            for (int j = 0; j < 5; j++) {
                System.out.println(hasilTFIDF[i][j]);
            }
        }
    }
//    public void printTFdanDF() {
//        System.out.println("======================================================================================");
//        System.out.println("No.     NAMA             DOK1   DOK2   DOK3   DOK4  DOK5      DF ");
//        System.out.println("======================================================================================");
//
//        for (int i = 0; i < term.size(); i++) {
//            System.out.println(String.format(" %03d   %-15s   %.3f  %.3f  %.3f  %.3f  %.3f    %.3f", i, term.get(i), (double)jumlahKata[i][0],
//                    (double)jumlahKata[i][1], (double)jumlahKata[i][2], (double)jumlahKata[i][3], (double)jumlahKata[i][4],  (double)jumlahDf[i]));
//        }
//        System.out.println("======================================================================================");
//
//    }
    public void printTFdanDF() {
        System.out.println("======================================================================================");
        System.out.println("======================================TF dan DF=======================================");
        System.out.println("======================================================================================");
        for (int i = 0; i < term.size(); i++) {
            System.out.printf(" %03d \t %-15s \t ",(i+1),term.get(i));
            for(int j=0;j< bnykDokumen;j++){
                    System.out.printf("%.3f \t",(double)jumlahKata[i][j]);
                    if(j==(bnykDokumen-1)){
                        System.out.printf(" || %.3f \t",(double)jumlahDf[i]);
                    }
            }
            System.out.println("");
        }
        System.out.println("======================================================================================");

    }
    public void printTFdanIDF() {
        System.out.println("======================================================================================");
        System.out.println("======================================TF dan IDF======================================");
        System.out.println("======================================================================================");
        for (int i = 0; i < term.size(); i++) {
            System.out.printf(" %03d \t %-15s \t ",(i+1),term.get(i));
            for(int j=0;j< bnykDokumen;j++){
                    System.out.printf("%.3f \t",(double)hasilTF[i][j]);
                    if(j==(bnykDokumen-1)){
                        System.out.printf(" || %.3f \t",(double)hasilIDF[i]);
                    }
            }
            System.out.println("");
        }
        System.out.println("======================================================================================");

    }
    public void printTfIdf() {
        System.out.println("======================================================================================");
        System.out.println("========================================TF-IDF========================================");
        System.out.println("======================================================================================");
        for (int i = 0; i < term.size(); i++) {
            System.out.printf(" %03d \t %-15s \t ",(i+1),term.get(i));
            for(int j=0;j< bnykDokumen;j++){
                    System.out.printf("%.3f \t",(double)hasilTFIDF[i][j]);
            }
            System.out.println("");
        }
        System.out.println("======================================================================================");

    }
//    public void printTFdanDF6() {
//        System.out.println("======================================================================================");
//        System.out.println("No.     NAMA             DOK1   DOK2   DOK3   DOK4  DOK5      DF ");
//        System.out.println("======================================================================================");
//
//        for (int i = 0; i < term.size(); i++) {
//            System.out.println(String.format(" %03d \t %-15s \t %.3f \t %.3f \t %.3f \t %.3f \t %.3f \t %.3f \t %.3f", i, term.get(i), (double)jumlahKata[i][0],
//                    (double)jumlahKata[i][1], (double)jumlahKata[i][2], (double)jumlahKata[i][3], (double)jumlahKata[i][4], (double)jumlahKata[i][4], (double)jumlahDf[i]));
//        }
//        System.out.println("======================================================================================");
//
//    }
    
//    public void printTFdok(int a) {
//        System.out.println("===============================");
//        System.out.println(" NAMA");
//        System.out.println("===============================");
//
//        for (int i = 0; i < term.size(); i++) {
//            System.out.println(term.get(i)); 
//        }
//        for (int i = 0; i < term.size(); i++) {
//            System.out.println(jumlahKata[i][a-1]); 
//        }
//        System.out.println("======================================================================================");
//
//    }

    
    public void printNorm() {
        System.out.println("======================================================================================");
        System.out.println("====================================NORMALISASI=======================================");
        System.out.println("======================================================================================");
        for (int i = 0; i < term.size(); i++) {
            System.out.printf(" %03d \t %-15s \t ",(i+1),term.get(i));
            for(int j=0;j< bnykDokumen;j++){
                    System.out.printf("%.3f \t",(double)hasilNorm[i][j]);
            }
            System.out.println("");
        }
        System.out.println("======================================================================================");
    }
    public void printCosSim() {
        System.out.println("======================================================================================");
        System.out.println("===============================COSINE SIMILARITY======================================");
        System.out.println("======================================================================================");
        for(int i=0;i<bnykDokumen;i++){
            System.out.printf("\t %d \t",(i+1));
        }
        System.out.println("");
        for (int i = 0; i < bnykDokumen; i++) {
            System.out.printf("%d \t",(i+1));
            for(int j=0;j< bnykDokumen;j++){
                    System.out.printf("%.3f \t",(double)hasilCosSim3[i][j]);
            }
            System.out.println("");
        }
        System.out.println("======================================================================================");
        
        
    }
    public void printCosSimQuery() {
        System.out.println("======================================================================================");
        System.out.println("No.     DOK1   DOK2   DOK3   DOK4   DOK5   ");
        System.out.println("======================================================================================");

        System.out.println(String.format(" %03d   %.3f  %.3f  %.3f  %.3f  %.3f  ", 1, hasilCosSimQ[0],
                    hasilCosSimQ[1], hasilCosSimQ[2], hasilCosSimQ[3], hasilCosSimQ[4]));
        
        
        
    }
    public double[][] getCosSim(){
        return hasilCosSim3;
    }
    public void printMMR(int[] retrieve, String[] hasilRingkasan, double[] persen) {
        System.out.println("======================================================================================");
        System.out.println("No. \tDokumen ke - \t HASIL RINGKASAN                                   PERSEN ");
        System.out.println("======================================================================================");

        for (int i = 0; i < retrieve.length; i++) {
            System.out.println(String.format("%03d \t%d \t\t %-45s     %.3f  ",(i+1), retrieve[i], hasilRingkasan[i], persen[i]));
        }
        System.out.println("======================================================================================");

    }
}
