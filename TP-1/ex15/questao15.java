import java.io.*;

public class questao15 {
    public static void main(String[] args) {
        String iSi = "";
        while (iSi != "FIM") {
            iSi = MyIO.readLine();
            if (iSi.equals("FIM")) {
                break;
            }
            String outPut = "";
            int id = 0;
            String result = iS(iSi.toLowerCase(), outPut, id);
            MyIO.println(result);
        }
    }

    static String iS(String iSi, String x1, int id) {
        if (id == 0) {
            if (iSi.matches("(\\.|,){1}\\d+") || iSi.matches("^[0123-9]+((\\.|,)\\d*)?$")) {
                x1 = "NAO NAO ";
                x1 = iS(iSi, x1, id + 2);
            } else if(iSi.matches(".*[0-9].*[0-9].*")) {
                x1 = "NAO NAO NAO NAO";
            }else{
                x1 = iS(iSi, x1, id + 1);
            }
        }
        if (id == 1) {
            if (iSi.matches("^[aeiou]+$")) {
                x1 = "SIM NAO ";
            } else if (iSi.matches("^[^aeiou]+$")) {
                x1 = "NAO SIM ";
            } else {
                x1 = "NAO NAO ";
            }
            x1 = x1.concat("NAO NAO");
        }
        if (id == 2) {
            if (iSi.matches("^[0-9]+$")) {
                x1 = x1.concat("SIM SIM");
            } else {
                x1 = x1.concat("NAO SIM");
            } 
        }
        return x1;
    }
}