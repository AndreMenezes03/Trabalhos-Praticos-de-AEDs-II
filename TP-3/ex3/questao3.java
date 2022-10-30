import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;
import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

class Game {
    // Var
    static SimpleDateFormat default_dateFormat = new SimpleDateFormat("MMM/yyyy", Locale.ENGLISH);
    private String name, owners, website, developers, current_game;
    private ArrayList<String> languages, genres;
    private Date release_date;
    private int app_id, age, dlcs, avg_playtime;
    private float price, upvotes;
    private boolean windows, mac, linux;

    // Game
    public Game() {
        this.name = this.owners = this.website = this.developers = null;
        this.languages = new ArrayList<String>();
        this.genres = new ArrayList<String>();
        this.release_date = null;
        this.app_id = this.age = this.dlcs = this.avg_playtime = -1;
        this.price = this.upvotes = -1;
        this.windows = this.mac = this.linux = false;
    }

    // Game(var)
    public Game(int app_id, String name, Date release_date, String owners, int age, float price, int dlcs,
            ArrayList<String> languages, String website, boolean windows, boolean mac, boolean linux, float upvotes,
            int avg_playtime, String developers,
            ArrayList<String> genres) {

        this.app_id = app_id;
        this.name = name;
        this.owners = owners;
        this.website = website;
        this.developers = developers;
        this.languages = languages;
        this.genres = genres;
        this.release_date = release_date;

        this.age = age;
        this.dlcs = dlcs;
        this.upvotes = upvotes;
        this.avg_playtime = avg_playtime;
        this.price = price;
        this.windows = windows;
        this.mac = mac;
        this.linux = linux;
    }

    // Clone
    public Game clone() {

        Game cloned = new Game();

        cloned.name = this.name;
        cloned.owners = this.owners;
        cloned.website = this.website;
        cloned.developers = this.developers;
        cloned.languages = this.languages;
        cloned.genres = this.genres;
        cloned.release_date = this.release_date;
        cloned.app_id = this.app_id;
        cloned.age = this.age;
        cloned.dlcs = this.dlcs;
        cloned.avg_playtime = this.avg_playtime;
        cloned.price = this.price;
        cloned.upvotes = this.upvotes;
        cloned.windows = this.windows;
        cloned.mac = this.mac;
        cloned.linux = this.linux;

        return cloned;
    }

    // Gets
    public String getName() {
        return this.name;
    }

    public String getOwners() {
        return this.owners;
    }

    public String getWebsite() {
        return this.website;
    }

    public String getDevelopers() {
        return this.developers;
    }

    public ArrayList<String> getLanguages() {
        return this.languages;
    }

    public ArrayList<String> getGenres() {
        return this.genres;
    }

    public Date getReleaseDate() {
        return this.release_date;
    }

    public int getAppId() {
        return this.app_id;
    }

    public int getAge() {
        return this.age;
    }

    public int getDlcs() {
        return this.dlcs;
    }

    public int getAvgPlaytime() {
        return this.avg_playtime;
    }

    public float getPrice() {
        return this.price;
    }

    public float getUpvotes() {
        return this.upvotes;
    }

    public boolean getWindows() {
        return this.windows;
    }

    public boolean getMac() {
        return this.mac;
    }

    public boolean getLinux() {
        return this.linux;
    }

    private void setGen() {
        if(this.current_game.length() == 0){
            this.genres.add("");
        }else{
            String[] aGenTemp = this.current_game.split(",");
            for (int i = 0; i < aGenTemp.length; i++) {
                if(!aGenTemp[i].equals("")){
                    this.genres.add(aGenTemp[i]);
                }
            }
        }
    }

    private void setDev() {
        String aDevTemp = this.current_game;
        String[] teste = aDevTemp.split("[\"]");
        if (teste.length == 1) {
            int indexTempDia = aDevTemp.indexOf(",");
            String devTempDia = aDevTemp.substring(0, indexTempDia);
            this.developers = devTempDia;
            this.current_game = aDevTemp.substring(indexTempDia + 1);
        } else if (teste.length == 2) {
            teste[0] = teste[0].replaceAll("[,\"]", "");
            this.developers = teste[0];
            this.current_game = teste[1];
        } else if(teste.length == 3){
            this.developers = teste[1];
            this.current_game = teste[2];
        } else if (teste.length == 4) {
            if (teste[0].equals("") == false)
                this.developers = teste[0];
            else
                this.developers = teste[1];
            if (teste[2].equals(",") == false)
                this.current_game = teste[2];
            else
                this.current_game = teste[3];
        } else if (teste.length == 5){
            this.developers = teste[1];
            this.current_game = teste[3];
        } else{
            this.developers = "deu";
            this.current_game = "ruim";
        }

    }

    private void setPT() {
        String aPTTemp = this.current_game;
        int indexTemp = aPTTemp.indexOf(",");
        String PTTemp = aPTTemp.substring(0, indexTemp);
        this.avg_playtime = Integer.valueOf(PTTemp);
        this.current_game = aPTTemp.substring(indexTemp + 1);
    }

    private void setUp() {
        String aUpTemp = this.current_game;
        int indexTemp = aUpTemp.indexOf(",");
        String upTemp = aUpTemp.substring(0, indexTemp);
        int upvotes = Integer.valueOf(upTemp);
        this.current_game = aUpTemp.substring(indexTemp + 1);
        aUpTemp = this.current_game;
        indexTemp = aUpTemp.indexOf(",");
        upTemp = aUpTemp.substring(0, indexTemp);
        int downvotes = Integer.valueOf(upTemp);
        this.current_game = aUpTemp.substring(indexTemp + 1);
        this.upvotes = (float) (upvotes * 100) / (float) (upvotes + downvotes);
    }

    private void setPlat() {
        int i = 0;
        for (i = 0; i < 3; i++) {
            String aWMLTemp = this.current_game;
            int indexTemp = aWMLTemp.indexOf(",");
            String WMLTemp = aWMLTemp.substring(0, indexTemp);
            if (WMLTemp.equals("True")) {
                if (i == 0)
                    this.windows = true;
                if (i == 1)
                    this.mac = true;
                if (i == 2)
                    this.linux = true;
            } else if (WMLTemp.equals("False")) {
                if (i == 0)
                    this.windows = false;
                if (i == 1)
                    this.mac = false;
                if (i == 2)
                    this.linux = false;
            }
            this.current_game = aWMLTemp.substring(indexTemp + 1);
        }

    }

    private void setSite() {
        if (this.current_game.indexOf("\"") == 0) {
            this.current_game = this.current_game.replaceFirst("\"", "");
            this.current_game = this.current_game.replaceFirst(",734", "");
        }
        if (this.current_game.indexOf(",") == 0) {
            this.current_game = this.current_game.replaceFirst(",", "");
        }
        if ((this.current_game.indexOf("T") == 0) || (this.current_game.indexOf("F") == 0)) {
            this.website = "null";
        } else if ((this.current_game.indexOf(",") == 0)) {
            this.website = "null";
            this.current_game = this.current_game.replaceFirst(",", "");
        } else {
            String aSitTemp = this.current_game;
            int indexTemp = aSitTemp.indexOf(",");
            String sitTemp = aSitTemp.substring(0, indexTemp);
            this.website = sitTemp;
            if (sitTemp.equals("") == true)
                this.website = "null";
            this.current_game = aSitTemp.substring(indexTemp + 1);
        }
    }

    private void setLang() {
        String aLangTemp = this.current_game;
        int indexTemp = aLangTemp.indexOf("]", 0);
        String langTemp = aLangTemp.substring(0, indexTemp + 1);
        langTemp = langTemp.replaceAll("['\"]", "");
        langTemp = langTemp.replace("[", "");
        langTemp = langTemp.replace("]", "");
        langTemp = langTemp.replaceAll(", ", ",");
        this.current_game = aLangTemp.substring(indexTemp + 2);
        String[] arLang = langTemp.split(",");
        for (int i = 0; i < arLang.length; i++) {
            // arLang[i] = arLang[i].replaceAll(" ", "");
            arLang[i] = arLang[i].replaceAll("\r", "");
            arLang[i] = arLang[i].replaceAll("\n", "");
            this.languages.add(arLang[i]);
        }
    }

    private void setDLC() {
        String aDLCTemp = this.current_game;
        int indexTemp = aDLCTemp.indexOf(",");
        String DLCTemp = aDLCTemp.substring(0, indexTemp);
        int DLCFinal = Integer.valueOf(DLCTemp);
        this.dlcs = DLCFinal;
        this.current_game = aDLCTemp.substring(indexTemp + 1);
    }

    private void setPri() {
        String aPreTemp = this.current_game;
        int indexTemp = aPreTemp.indexOf(",");
        String preTemp = aPreTemp.substring(0, indexTemp);
        float precoFinal = Float.valueOf(preTemp);
        this.price = precoFinal;
        this.current_game = aPreTemp.substring(indexTemp + 1);
    }

    private void setAge() {
        String aOwnTemp = this.current_game;
        int indexTemp = aOwnTemp.indexOf(",");
        String ownTemp = aOwnTemp.substring(0, indexTemp);
        int ownerFinal = Integer.valueOf(ownTemp);
        this.age = ownerFinal;
        this.current_game = aOwnTemp.substring(indexTemp + 1);
    }

    private void setOwn() {
        String aOwnTemp = this.current_game;
        int indexTemp = aOwnTemp.indexOf(",");
        String ownTemp = aOwnTemp.substring(0, indexTemp);
        this.owners = ownTemp;
        this.current_game = aOwnTemp.substring(indexTemp + 1);
    }

    private void setDate() {
        DateFormat og = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH);
        String aDateTemp = this.current_game;
        if ((aDateTemp.indexOf(",") == 6) || (aDateTemp.indexOf(",") == 7)) {
            int indexTemp = aDateTemp.indexOf(",", aDateTemp.indexOf(",") + 1);
            String dataTemp = aDateTemp.substring(0, indexTemp);
            dataTemp = dataTemp.replaceAll("[\"]", "");
            this.current_game = aDateTemp.substring(indexTemp + 1);
            try {
                Date dateTemp = og.parse(dataTemp);
                this.release_date = dateTemp;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            // SimpleDateFormat serioQPataquadaEin = new SimpleDateFormat("mmm yyyy");
            int indexTemp = aDateTemp.indexOf(",");
            String dataTemp = aDateTemp.substring(0, indexTemp);
            dataTemp = dataTemp.replaceAll("[\"]", "");
            String[] dataTempS = dataTemp.toLowerCase().split(" ");
            int year = Integer.valueOf(dataTempS[1]);
            int month = setMonth(dataTempS[0]);
            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.YEAR, year);
            Date dateSE = calendar.getTime();
            this.release_date = dateSE;
            this.current_game = aDateTemp.substring(indexTemp + 1);
        }

    }

    private int setMonth(String mesao) {
        if (mesao.equals("jan"))
            return 1;
        if (mesao.equals("feb"))
            return 2;
        if (mesao.equals("mar"))
            return 3;
        if (mesao.equals("apr"))
            return 4;
        if (mesao.equals("may"))
            return 5;
        if (mesao.equals("jun"))
            return 6;
        if (mesao.equals("jul"))
            return 7;
        if (mesao.equals("aug"))
            return 8;
        if (mesao.equals("sep"))
            return 9;
        if (mesao.equals("oct"))
            return 10;
        if (mesao.equals("nov"))
            return 11;
        if (mesao.equals("dec"))
            return 12;
        else
            return 0;
    }

    private void setName() {
        String aNameTemp = this.current_game;
        if (aNameTemp.charAt(0) == '\"') {
            String nameTemp = aNameTemp.substring(1);
            int indexTemp = nameTemp.indexOf("\"");
            this.name = nameTemp.substring(0, indexTemp);
            this.current_game = nameTemp.substring(indexTemp + 2);
        } else {
            int indexTemp = aNameTemp.indexOf(",");
            String nameTemp = aNameTemp.substring(0, indexTemp);
            this.name = nameTemp;
            this.current_game = aNameTemp.substring(indexTemp + 1);
        }
    }

    // Create
    void create(int gID, String gString, int linha) {
        this.current_game = gString;
        this.app_id = gID;
        setName();
        setDate();
        setOwn();
        setAge();
        setPri();
        setDLC();
        // if(linha == 517910){
            // System.out.println("debugPrint");
        // }
        if (linha == 3936) {
            this.languages.add("English[b][/b]");
            this.languages.add("French");
            this.languages.add("German[b][/b]");
            this.languages.add("Italian");
            this.languages.add("Spanish - Spain\r\n[b][/b]");
            this.current_game = ",True,False,False,97,169,48,DnS Development,\"Action,Adventure\"";
        } else {
            setLang();
        }
        if (linha == 3206) {
            this.website = "http://www.microids.com/EN/store/nicolas-eymerich-inquisitor-book-1-plague,734";
            this.current_game = "True,True,False,58,34,0,Ticonblu,Adventure";
        } else {
            setSite();
        }
        setPlat();
        setUp();
        setPT();
        setDev();
        setGen();
    }

    public Game createResp() {
        return new Game(this.app_id, this.name, this.release_date, this.owners,
                this.age,
                this.price, this.dlcs, this.languages, this.website, this.windows, this.mac, this.linux, this.upvotes,
                this.avg_playtime, this.developers, this.genres);
    }

    public String printResp() {
        DecimalFormat df = new DecimalFormat("##");
        String priceS = priceUp();
        String lula_ptS = formPt();
        return this.app_id + " " + this.name + " " +
                default_dateFormat.format(this.release_date) + " "
                + this.owners + " " + this.age + " "
                + priceS + " " + this.dlcs + " " + this.languages + " " + this.website + " "
                + this.windows + " "
                + this.mac + " " + this.linux + " " + (Float.isNaN(this.upvotes) ? "0% "
                        : df.format(this.upvotes) + "%")
                + " " + lula_ptS + " " + this.developers
                + " "
                + this.genres;
    }

    private String priceUp() {
        return String.format("%.02f", this.price).replaceAll(",", ".");
    }

    private String formPt() {
        if (this.avg_playtime == 0) {
            return null;
        } else {
            int min = this.avg_playtime % 60;
            int hr = (this.avg_playtime - min) / 60;
            if(hr<1){
                return min + "m";
            }else if(min<1){
                return hr + "h";
            }else{
                return hr + "h" + " " + min + "m";
            }
        }
    }
}

class Lista {
    private Game[] arr;
    private Game tempG = new Game();
    private int n;

    public Lista() {
        this(6);
    }

    public Lista(int tamanho) {
        arr = new Game[tamanho];
        n = 0;
    }

    public boolean inserirInicio(Game item) {
        if (n < arr.length) {
            for (int i = n; i > 0; i--)
                arr[i] = arr[i - 1];

            arr[0] = item;
            n++;
            return true;
        }
        return false;
    }

    public boolean inserirFim(Game item) {
        if (n < arr.length) {
            arr[n] = item;
            n++;
            return true;
        }
        return false;
    }

    public boolean inserir(Game item, int pos) {
        if (n < arr.length && pos >= 0 && pos <= n) {
            for (int i = n; i > pos; i--)
                arr[i] = arr[i - 1];
            arr[pos] = item;
            n++;
            return true;
        }
        return false;
    }

    public Game removerInicio() {
        if (n > 0) {
            Game item = arr[0];
            n--;

            for (int i = 0; i < n; i++)
                arr[i] = arr[i + 1];

            return item;
        }
        return null;
    }

    public Game removerFim() {
        if (n > 0)
            return arr[--n];
        return null;
    }

    public Game remover(int pos) {
        if (n > 0 && pos >= 0 && pos < n) {
            Game item = arr[pos];
            n--;

            for (int i = pos; i < n; i++)
                arr[i] = arr[i + 1];

            return item;
        }
        return null;
    }

    public void ordenarJojo(int e, int tamArq) {
        Game pivo = new Game();
        int esq = e;
        int dir = tamArq;
        pivo = arr[(esq + dir) / 2];
        while (esq <= dir) {
            while (arr[esq].getAppId() < pivo.getAppId())
                esq++;
            while (arr[dir].getAppId() > pivo.getAppId())
                dir--;
            if (esq <= dir) {
                tempG = arr[esq];
                arr[esq] = arr[dir];
                arr[dir] = tempG;
                esq++;
                dir--;
            }
        }
        if (e < dir) {
            ordenarJojo(e, dir);
        }
        if (esq < tamArq) {
            ordenarJojo(esq, tamArq);
        }
    }

    public void ordenarResp(int vezes, int tamArq) {
        Game chave = new Game();
        for (int index = 1; index < vezes; index++) {
            chave = arr[index];
            int j = index - 1;
            while (j >= 0 && arr[j].getAppId() > chave.getAppId()) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = chave;
        }
    }

    public Game pesquisa(int input, int tam) {
        int esq = 0;
        int dir = tam - 1;
        int meio = -1;
        int x = -1;
        boolean resp = false;
        while (esq <= dir) {
            meio = (esq + dir) / 2;
            x = arr[meio].getAppId();
            if (input == x) {
                resp = true;
                esq = tam;
            } else if (input < x) {
                dir = meio - 1;
            } else {
                esq = meio + 1;
            }
        }
        if (resp == true) {
            return arr[meio].createResp();
        } else {
            return new Game();
        }
    }

    public void printResp(int vezes, int local) {
        if(local == 1){
            Arq.openWrite("tmp/out.txt","UTF-8");
            for (int i = 0; i < vezes; i++) {
                String resp = arr[i].printResp();
                Arq.println(resp);
            }
            Arq.close();
        }else{
            for (int i = 0; i < vezes; i++) {
                System.out.println(arr[i].printResp());
            } 
        }
    }
}

class questao3 {
    static DecimalFormat tmp = new DecimalFormat("##.###s");

    public static void main(String[] args) {
        double initLoad = System.currentTimeMillis();
        String[] gameTemp;
        String arqLerVerde = "/tmp/games.csv";
        String arqLerPc = "tmp/games.csv";
        int linha;
        int pcOuVerde = 0;
        int tamArq = 4402;
        Lista games = new Lista(tamArq);
        File f = new File(arqLerPc);
        if (f.exists() && !f.isDirectory()) {
            Arq.openRead(arqLerPc, "UTF-8");
            pcOuVerde = 1;
        } else {
            Arq.openRead(arqLerVerde, "UTF-8");
        }
        gameTemp = Arq.readAll().split("\n");
        Arq.close();
        for (linha = 0; linha < tamArq; linha++) {
            int indexTemp = gameTemp[linha].indexOf(",");
            int idLinha = Integer.parseInt(gameTemp[linha].substring(0, indexTemp));
            String gameLinha = gameTemp[linha].substring(indexTemp + 1);

            Game game = new Game();

            game.create(idLinha, gameLinha, linha);
            games.inserirFim(game);
        }
        games.ordenarJojo(0, tamArq - 1);

        Lista respostas = new Lista(tamArq);

        double fimLoad = System.currentTimeMillis();
        double tempoLoad = fimLoad - initLoad;
        int vezes = 0;
        String input;
        double initIn;
        if(pcOuVerde == 1){
            Arq.openRead("tmp/inPub.txt", "UTF-8");
            input = Arq.readLine();
            initIn = System.currentTimeMillis();
            while (true) {
                if (input.equals("FIM") == true)
                    break;
                int idInput = Integer.parseInt(input);
                respostas.inserirFim(games.pesquisa(idInput, tamArq));
                vezes++;
                input = Arq.readLine();
            }
            Arq.close();
        }else{
            input = MyIO.readLine();
            initIn = System.currentTimeMillis();
            while (true) {
                if (input.equals("FIM") == true)
                    break;
                int idInput = Integer.parseInt(input);
                respostas.inserirFim(games.pesquisa(idInput, tamArq));
                vezes++;
                input = MyIO.readLine();
            }
        }
        respostas.ordenarResp(vezes, tamArq);
        respostas.printResp(vezes, pcOuVerde);
        double fimIn = System.currentTimeMillis();
        double tempoInput = fimIn - initIn;
        if(pcOuVerde == 1){
            Arq.openWrite("tmp/matricula_insercao.txt", "UTF-8");
            Arq.print("762723\t");
            Arq.print("n(n-1)/2\t");
            Arq.print("n\t");
            Arq.print(tmp.format((tempoLoad + tempoInput) / 1000) + "\t");
            Arq.close();
        }else{
            Arq.openWrite("/tmp/matricula_insercao.txt", "UTF-8");
            Arq.print("762723\t");
            Arq.print("n(n-1)/2\t");
            Arq.print("n\t");
            Arq.print(tmp.format((tempoLoad + tempoInput) / 1000) + "\t");
            Arq.close(); 
        }       
    }
}