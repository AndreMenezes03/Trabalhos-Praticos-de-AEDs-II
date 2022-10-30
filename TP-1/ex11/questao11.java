public class questao11 {
    public static void main(String[] args) {
        String palin = "temp";
        while (palin != "FIM") {
            palin = MyIO.readLine();
            if (palin.equals("FIM")){
                break;
            }
            int i = 0;
            int j = (palin.length() - 1);
            boolean result = palindromo(palin, i, j);
            if (result == true)
                MyIO.println("SIM");
            else
                MyIO.println("NAO");
        }
    }

    static boolean palindromo(String palin, int i, int j) {
        boolean resp = true;
        if (i <= j) {
            if (palin.charAt(i) != palin.charAt(j))
                resp = false;
            else
                resp = palindromo(palin, i + 1, j - 1);
        }
        return resp;
    }
}