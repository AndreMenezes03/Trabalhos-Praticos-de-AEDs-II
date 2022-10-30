import  java.util.Arrays;

class Game {
    private String[] stringGame;
    private String[] stringID;
    private String[] srLista;
    private String currentGame;
    private String name;

    Game(){
        srLista = new String[4403];
    }

    void setGabarito(String[] stringGame, String[] stringID) {
        this.stringGame = stringGame;
        this.stringID = stringID;
    }

    void read() {
        int linha;
        String[] stringID;
        stringID = new String[4403];
        Arq.openRead("/tmp/games.csv", "UTF-8");
        String[] stringGame = Arq.readAll().split("\n");
        for (linha = 0; linha < 4402; linha++) {
            int indexTemp = stringGame[linha].indexOf(",");
            String linhaTemp = stringGame[linha].substring(0, indexTemp);
            stringID[linha] = linhaTemp;
            stringGame[linha] = stringGame[linha].substring(indexTemp + 1);
        }
        setGabarito(stringGame, stringID);
    }

    protected Game clone() {
        Game ra = new Game();
        return ra;
    }

    public void setList(String id, int pos){
        this.srLista[pos] = getResp(id);
    }

    public void print(int pos) {
        String inputSearch = "";
        ordJojos(pos);
        while (inputSearch.equals("FIM") != true) {
            inputSearch = MyIO.readLine();
            if (inputSearch.equals("FIM") == true)
                break;
            pesqBin(inputSearch,pos);
        }
    }


    private void ordJojos(int pos) {
        String orTemp = "";
        for (int n = 0; n < pos; n++) {
            for (int j = n + 1; j < pos; j++) {
                if (this.srLista[n].compareTo(this.srLista[j]) > 0) {
                    orTemp = this.srLista[n];
                    this.srLista[n] = this.srLista[j];
                    this.srLista[j] = orTemp;
                }
            }
        }
    }

    private void pesqBin(String inputSearch, int pos) {
        int esq = 0;
        int dir = pos - 1;
        int meio;
        boolean resp = false;
        while(esq <= dir){
            meio = (esq+dir)/2;
            if((inputSearch.equals(this.srLista[meio])) == true){
                resp = true;
                esq = pos;
            }else if(inputSearch.compareTo(this.srLista[meio]) > 0){
                esq = meio + 1;
            }else{
                dir = meio - 1;
            }
        }
        if(resp == true){
            MyIO.println("SIM");
        }else{
            MyIO.println("NAO");
        } 
    }

    private String getResp(String id) {
        setID(id);
        String resposta = this.name;
        return resposta;
    }

    private void setID(String id) {
        int i = 0;
        while (i != -1) {
            if (this.stringID[i].equals(id) == false)
                i++;
            else {
                int posI = i;
                setVar(posI);
                break;
            }
        }
    }

    private void setVar(int posI) {
        this.currentGame = this.stringGame[posI];
        setName();
    }

    private void setName() {
        String aNameTemp = this.currentGame;
        int indexTemp = aNameTemp.indexOf(",");
        this.name = aNameTemp.substring(0, indexTemp);
    }
}

class questao4 {
    public static void main(String[] args) {
        String id = "";
        Game jogo = new Game();
        Game jojo = jogo.clone();
        jojo.read();
        int pos = 0;
        while (id.equals("FIM") != true) {
            id = MyIO.readLine();
            if (id.equals("FIM") == true)
                break;
            jojo.setList(id,pos);
            pos++;
        }
        jojo.print(pos);
    }

}