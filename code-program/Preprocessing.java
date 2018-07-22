
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import jsastrawi.morphology.DefaultLemmatizer;
import jsastrawi.morphology.Lemmatizer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author alasu_000
 */
public class Preprocessing {
    String judul="",dokumen="";
    LinkedList<LinkedList> dokumenHasil;
    LinkedList<String> dokPreprocess = new LinkedList<>();
    LinkedList<String> hasil = new LinkedList<>();
    LinkedList<String> hasilAkhir = new LinkedList<>();
    String dok = "";

    public Preprocessing(String document) {
        this.dok = document;

    }

    public Preprocessing() {

    }

    public LinkedList Tokenization() {
        dok = dok.toLowerCase();
        //dok = dok.replaceAll("[0-9]", "");
        StringTokenizer dokPre = new StringTokenizer(dok);
        while (dokPre.hasMoreTokens()) {
            dokPreprocess.addLast(dokPre.nextToken());
        }
        //hapus angka, punctuation
        //System.out.println("Hasil Tokenisasi");
        for (int i = 0; i < dokPreprocess.size(); i++) {
            dokPreprocess.set(i, dokPreprocess.get(i).replaceAll("\\p{P}", "").replaceAll("^\\d+$", ""));
            if(dokPreprocess.get(i).equalsIgnoreCase("")){
                dokPreprocess.remove(i);
                i--;
            }
            //System.out.println((i + 1) + "\t" + dokPreprocess.get(i));
        }
        System.out.println("Hasil Tokenisasi");
        for (int i = 0; i < dokPreprocess.size(); i++) {
            System.out.println((i + 1) + "\t" + dokPreprocess.get(i));
        }
        System.out.println("================");
        return dokPreprocess;
    }

    public LinkedList Filtering() {
        try {
            File stopword = new File("C:\\Users\\alasu_000\\Documents\\NetBeansProjects\\TextMining\\src\\Preprocessing\\stopword.txt");
            Scanner scanner = new Scanner(stopword);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                while(dokPreprocess.contains(line)){
                    dokPreprocess.remove(line);
                }
                //dokPreprocess.remove(line);
            }
        } catch (FileNotFoundException e) {
        }
        System.out.println("Hasil Filtering");
        for (int i = 0; i < dokPreprocess.size(); i++) {
            System.out.println((i + 1) + "\t" + dokPreprocess.get(i));
        }
        System.out.println("================");
        return dokPreprocess;
    }

    public String Sastrawi(String root) {
        Set<String> dictionary = new HashSet<String>();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("C:\\Users\\alasu_000\\Documents\\NetBeansProjects\\MMRSTKI\\root-words.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Preprocessing.class.getName()).log(Level.SEVERE, null, ex);
        }

        String line;
        try {
            while ((line = br.readLine()) != null) {
                dictionary.add(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(Preprocessing.class.getName()).log(Level.SEVERE, null, ex);
        }

        Lemmatizer lemmatizer = new DefaultLemmatizer(dictionary);
        String HasilRoot = lemmatizer.lemmatize(root);

        return HasilRoot;
    }

    public LinkedList Stemming() {

        for (int i = 0; i < dokPreprocess.size(); i++) {
            if (Sastrawi(dokPreprocess.get(i)) == null) {
                hasil.addLast(dokPreprocess.get(i));
            } else {
                String hasilStem = Sastrawi(dokPreprocess.get(i));
                hasil.addLast(hasilStem);
            }
        }
        System.out.println("Hasil Stemming");
        for (int i = 0; i < hasil.size(); i++) {
            System.out.println((i+1) +"\t"+hasil.get(i));
        }
        System.out.println("================");
        return hasil;
    }

//    public String removePunctuation(String word) {
//
//        if (word.charAt(0) > 32 && word.charAt(0) <= 47) {
//            if (word.length() == 1) {
//                return "";
//            } else {
//                return removePunctuation(word.substring(1));
//            }
//        } else if (word.charAt(0) >= 58 && word.charAt(0) <= 64) {
//            if (word.length() == 1) {
//                return "";
//            } else {
//                return removePunctuation(word.substring(1));
//            }
//        } else if (word.charAt(0) >= 91 && word.charAt(0) <= 96) {
//            if (word.length() == 1) {
//                return "";
//            } else {
//                return removePunctuation(word.substring(1));
//            }
//        } else if (word.charAt(0) >= 123 && word.charAt(0) <= 126) {
//            if (word.length() == 1) {
//                return "";
//            } else {
//                return removePunctuation(word.substring(1));
//            }
//        } else {
//            if (word.length() == 1) {
//                return "" + word.charAt(0);
//            } else {
//                return word.charAt(0) + removePunctuation(word.substring(1));
//            }
//        }
//    }

    public <T> List<T> union(LinkedList<T> list1[]) {
        Set<T> set = new HashSet<T>();
        for (int i = 0; i < list1.length; i++) {
            set.addAll(list1[i]);
        }
        ArrayList<T> hasil = new ArrayList<T>(set);
        for (int i = 0; i < hasil.size(); i++) {
            System.out.println(hasil.get(i));
        }

        return hasil;
    }
    public String readFile1(String filename) {
        try {
            //BufferedReader reader = new BufferedReader(new FileReader("E:\\datalatihskripsi\\ala\\tes\\doc" + filename));
            BufferedReader reader = new BufferedReader(new FileReader("E:\\skripsi\\data\\manualisasi\\" + filename));
            //BufferedReader reader = new BufferedReader(new FileReader("E:\\skripsi\\data\\manualisasi2\\" + filename));
            String line;
            int i=0;
            dokumen="";
            while ((line = reader.readLine()) != null) {
//                if(i==0){
//                    judul+=line;
//                }else{
                    dokumen+= line;
//                }
                i++;
            }
            reader.close();
            return dokumen;
        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
            return null;
        }
    }
    public String readFile(String filename) {
        try {
            //BufferedReader reader = new BufferedReader(new FileReader("E:\\datalatihskripsi\\ala\\tes\\doc" + filename));
            BufferedReader reader = new BufferedReader(new FileReader("E:\\skripsi\\data\\manualisasi\\" + filename));
            //BufferedReader reader = new BufferedReader(new FileReader("E:\\skripsi\\data\\manualisasi2\\" + filename));
            String line;
            int i=0;
            judul="";
            dokumen="";
            while ((line = reader.readLine()) != null) {
                if(i==0){
                    judul+=line;
                }else{
                    dokumen+= line;
                }
                i++;
            }
            reader.close();
            return dokumen;
        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
            return null;
        }
    }
    public String getJudul(){
        return judul;
    }
}
