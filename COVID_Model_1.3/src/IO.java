import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class IO{
    public static ArrayList<String> Countryname = new ArrayList<>();
    public static ArrayList<String> Countrycode = new ArrayList<>();
    public static ArrayList<Double> BasicReproduction = new ArrayList<>();
    public static ArrayList<double[]> AgeS = new ArrayList<>();
    public static ArrayList<String> Event = new ArrayList<>();

    public static ArrayList<Double> Immunity_wane_mild = new ArrayList<>();
    public static ArrayList<Double> Immunity_wane_severe = new ArrayList<>();

    public static ArrayList<double[][][]> Matrices = new ArrayList<>();

    public static ArrayList<double[][]> Rural_Matrices = new ArrayList<>();
    public static ArrayList<double[][]> Urban_Matrices = new ArrayList<>();

    public static ArrayList[][] MatricesByCategory = new ArrayList[2][4];

    public static ArrayList<double[]> Biases = new ArrayList<>();

    public IO(){
    }
    public static void main(String[] args) {
        IO io = new IO();
    }

    public static void Combined_Input(){
        Age_distribution_IO();
        Matrix_IO();
        Immunity_level_IO();
    }

    public static void Age_distribution_IO(){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(Parameters.ReadPath+"Country_Files/Country_Age_Distribution.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String buffer = "";

        while (true) {
            try {
                if (!((buffer = reader.readLine())!=null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            int comma = buffer.indexOf(",");
            String sub1 = buffer.substring(comma+1);
            String sub2 = buffer.substring(0,comma);
            String c = sub1.substring(0,sub1.indexOf(","));
            sub1 = sub1.substring(sub1.indexOf(",")+1);
            String r = sub1.substring(0,sub1.indexOf(","));
            sub1 = sub1.substring(sub1.indexOf(",")+1);
            double[] ages = new double[16];
            for (int i = 0; i < 16; i++) {
                String b;
                if(i==16){
                    b = sub1;
                }
                else {
                    b = sub1.substring(0,sub1.indexOf(","));
                }
                sub1 = sub1.substring(sub1.indexOf(",")+1);
                double d = Double.parseDouble(b);
                ages[i] = d;
            }
            Countryname.add(sub2);
            Countrycode.add(c);
            BasicReproduction.add(Double.parseDouble(r));
            AgeS.add(ages);
        }

    }

    public static void Matrix_IO(){
        BufferedReader[] Readers = new BufferedReader[Countrycode.size()];


        String Categories[] = {"work","school","home","others"};
        String County_Category[] = {"rural","urban"};

        for (int County_type = 0; County_type < 2; County_type++) {
            for (int Category = 0; Category < 4; Category++) {
                MatricesByCategory[County_type][Category] = new ArrayList<double[][]>();
                for (int Country = 0; Country < Countrycode.size(); Country++) {
                    String Suf = ".csv";
                    String name = Parameters.ReadPath + "Matrix_IO/Matrix_by_Category/" + County_Category[County_type] + "/" + Categories[Category] + "/"+Countrycode.get(Country)+Suf;
                    try {
                        Readers[Country] = new BufferedReader(new FileReader(name));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println(County_Category[County_type]);
                System.out.println(Categories[Category]);

                for (int country = 0; country < Countrycode.size(); country++) {
                    System.out.println(Countryname.get(country));
                    double[][] Matrix = new double[16][16];
                    try {
                        Readers[country].readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (int i1 = 0; i1 < 16; i1++) {

                        String line = null;
                        try {
                            line = Readers[country].readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println(line);
                        line = line.substring(line.indexOf(",")+1);
                        for (int i2 = 0; i2 < 15; i2++) {
                            String Number = line.substring(0,line.indexOf(","));
                            line = line.substring(line.indexOf(",")+1);
                            double floatnumber = Double.parseDouble(Number);
                            Matrix[i1][i2] = floatnumber;
                        }
                        String Number = line;
                        double floatnumber = Double.parseDouble(Number);
                        Matrix[i1][15] = floatnumber;
                    }
                    MatricesByCategory[County_type][Category].add(Matrix);
                }
            }
        }
    }

    public static void Immunity_level_IO(){

        ArrayList<String> Lines = new ArrayList<>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(Parameters.ReadPath+"Vaccine_Pfizer/Efficacy.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String str = "";

        try {
            str = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                if (!((str = reader.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            Lines.add(str);
        }

        for (int line = 0; line < Lines.size(); line++) {
            String Efficacy_on_day = Lines.get(line);
            int Sep_index = Efficacy_on_day.indexOf(",");
            String Mild_str = Efficacy_on_day.substring(0,Sep_index);
            String Severe_str = Efficacy_on_day.substring(Sep_index+1);
            Immunity_wane_mild.add(Double.parseDouble(Mild_str));
            Immunity_wane_severe.add(Double.parseDouble(Severe_str));
        }
    }

    public ArrayList<String> Buffered_read(String Path){

        ArrayList<String> Lines = new ArrayList<>();

        FileReader read = null;
        try {
            read = new FileReader(Path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader buffread = new BufferedReader(read);

        String str = null;

        while (true) {
            try {
                if (!((str = buffread.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            Lines.add(str);
        }

        return Lines;
    }

    public ArrayList<String> Buffered_read(String Path, boolean Remove_first_line){

        ArrayList<String> Lines = new ArrayList<>();

        FileReader read = null;
        try {
            read = new FileReader(Path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader buffread = new BufferedReader(read);

        String str = null;

        if(Remove_first_line){
            try {
                str = buffread.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        while (true) {
            try {
                if (!((str = buffread.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            Lines.add(str);
        }

        return Lines;
    }

}
   