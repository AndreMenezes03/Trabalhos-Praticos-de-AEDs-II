public class questao14 {
    public static void main(String[] args) {
        String expBool = "";
        while (expBool != "FIM") {
            expBool = MyIO.readLine();
            if (expBool == "FIM")
                break;
            int i = 0;
            String result = subs(expBool, i);
            MyIO.println(result);
        }

    }

    public static String subs(String expBool, int is) {
        Char[] teste;
        Char[] alfa = { d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, a, b, c };
        teste[is] = alfa[expBool.charAt(i) - 48];
        if (is = expBool.length() - 1) {
            return teste;
        }
        return subs(expBool, is + 1);
    }
}
