public class questao13 {
    public static void main(String[] args) {
        String expBool = "";
        while (expBool != "FIM") {
            expBool = MyIO.readLine();
            if (expBool.equals("FIM"))
                break;
            int i = 0;
            String resulta = "";
            expBool = expBool.replaceAll("\\s", "");
            expBool = expBool.replaceAll("àèìòùÀÈÌÒÙáéíóúýÁÉÍÓÚÝâêîôûÂÊÎÔÛãñõÃÑÕäëïöüÿÄËÏÖÜŸçÇßØøÅåÆæœ", "");
            String expPqn = expBool.toLowerCase();
            expPqn = expPqn.replaceAll("[^a-z]", "");                 
                String result = subs(expPqn, i, resulta);
                MyIO.println(result);
            }
        }

    public static String subs(String expBool, int is, String result) {
        int tamanho = expBool.length();
        if(tamanho == 0)return "Não deu certo";
        String alfa = "abcdefghijklmnopqrstuvwxyz";
        String alfatres = "defghijklmnopqrstuvwxyzabc";
        int posi = alfa.indexOf(expBool.charAt(is));
        result = result + (alfatres.charAt(posi));
        if (is == tamanho-1) {
            return result;
        } else {
            return subs(expBool, is + 1, result);
        }
    }

}
