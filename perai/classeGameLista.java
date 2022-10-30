import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

class Game {
    static SimpleDateFormat default_dateFormat = new SimpleDateFormat("MMM/yyyy", Locale.ENGLISH);

    private String name, owners, website, developers, current_game;
    private ArrayList<String> languages, genres;
    private Date release_date;
    private int app_id, age, dlcs, avg_playtime;
    private float price, upvotes;
    private boolean windows, mac, linux;

    public Game() {
        this.name = this.owners = this.website = this.developers = null;
        this.languages = new ArrayList<String>();
        this.genres = new ArrayList<String>();
        this.release_date = null;
        this.app_id = this.age = this.dlcs = this.avg_playtime = -1;
        this.price = this.upvotes = -1;
        this.windows = this.mac = this.linux = false;
    }

    public Game(String name, String owners, String website, String developers, ArrayList<String> languages,
            ArrayList<String> genres, Date release_date, int app_id, int age, int dlcs, int upvotes, int avg_playtime,
            float price, boolean windows, boolean mac, boolean linux) {

        this.name = name;
        this.owners = owners;
        this.website = website;
        this.developers = developers;
        this.languages = languages;
        this.genres = genres;
        this.release_date = release_date;
        this.app_id = app_id;
        this.age = age;
        this.dlcs = dlcs;
        this.upvotes = upvotes;
        this.avg_playtime = avg_playtime;
        this.price = price;
        this.windows = windows;
        this.mac = mac;
        this.linux = linux;
    }

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

    private void create(int gID, String gString, int linha) {
        this.current_game = gString;
        this.app_id = gID;
        setName();
        setDate();
        setOwn();
        setAge();
        setPri();
        setDLC();
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

    public void print(int id) {

        String upvoteS = formUp();
        String priceS = priceUp();
        String lula_ptS = formPt();
        Arq.println(this.name);
        System.out.println(this.app_id + " " + this.name + " " + default_dateFormat.format(this.release_date) + " "
                + this.owners + " " + this.age + " "
                + priceS + " " + this.dlcs + " " + this.languages + " " + this.website + " " + this.windows + " "
                + this.mac + " " + this.linux + " " + upvoteS.concat("%") + " " + lula_ptS + " " + this.developers + " "
                + this.genres);
    }

    private String priceUp() {
        return String.format("%,02f", this.price);
    }

    private String formPt() {
        int min = this.avg_playtime % 60;
        int hr = (this.avg_playtime - min) / 60;
        return hr + "h" + " " + min + "m";
    }

    private String formUp() {
        if (this.upvotes == (long) this.upvotes)
            return String.format("%d", (long) this.upvotes);
        else
            return String.format("%s", this.upvotes);
    }

    private void setGen() {
        String[] aGenTemp = this.current_game.split(",");
        for (int i = 0; i < aGenTemp.length; i++) {
            this.genres.add(aGenTemp[i]);
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
        } else if (teste.length == 4) {
            if (teste[0].equals("") == false)
                this.developers = teste[0];
            else
                this.developers = teste[1];
            if (teste[2].equals(",") == false)
                this.current_game = teste[2];
            else
                this.current_game = teste[3];
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
        float total = upvotes + downvotes;
        float porco = (upvotes / total) * 100;
        this.upvotes = (float) Math.floor(porco);
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
        this.current_game = aLangTemp.substring(indexTemp + 2);
        String[] arLang = langTemp.split(",");
        for (int i = 0; i < arLang.length; i++) {
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

    public static void main(String[] args) {
        String[] gameTemp;
        String arqLer = "tmp/games.csv";
        int linha;
        int tamArq = 4402;
        Lista games = new Lista(tamArq);
        Arq.openRead(arqLer, "UTF-8");
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

        games.ordenar(0, tamArq - 1);

        Arq.openWrite("tmp/outName2.txt");
        for (int i = 0; i < 4402; i++) {
            games.printNome(i);
        }
        Arq.close();

        Arq.openWrite("tmp/outName.txt");
        String input = "";
        while (input.equals("FIM") != true) {
            input = MyIO.readLine();
            if (input.equals("FIM") == true)
                break;
            int idInput = Integer.parseInt(input);
            games.pesquisa(idInput, tamArq);
        }
    }
}

class Lista {
    private Game[] array;
    private Game tempG = new Game();
    private int n;

    /**
     * Construtor da classe.
     */
    public Lista() {
        this(6);
    }

    /**
     * Construtor da classe.
     * 
     * @param tamanho Tamanho da lista.
     */
    public Lista(int tamanho) {
        array = new Game[tamanho];
        n = 0;
    }

    public void printNome(int i) {
        Arq.print(array[i].getAppId());
        Arq.print(" ");
        Arq.println(array[i].getName());
    }

    /**
     * Insere um elemento na primeira posicao da lista e desloca os demais elementos
     * para o fim da lista.
     * 
     * @param Elemento a ser inserido.
     */
    public boolean inserirInicio(Game item) {
        if (n < array.length) {
            // Desloca elementos para o fim do array
            for (int i = n; i > 0; i--)
                array[i] = array[i - 1];

            array[0] = item;
            n++;
            return true;
        }
        return false;
    }

    /**
     * Insere um elemento na ultima posicao da lista.
     * 
     * @param Elemento a ser inserido.
     */
    public boolean inserirFim(Game item) {
        // validar insercao
        if (n < array.length) {
            array[n] = item;
            n++;
            return true;
        }
        return false;
    }

    /**
     * Insere um elemento em uma posicao especifica e move os demais elementos para
     * o fim da lista.
     * 
     * @param item: elemento a ser inserido.
     * @param pos:  Posicao de insercao.
     */
    public boolean inserir(Game item, int pos) {

        // validar insercao
        if (n < array.length && pos >= 0 && pos <= n) {
            // Desloca elementos para o fim do array
            for (int i = n; i > pos; i--)
                array[i] = array[i - 1];

            array[pos] = item;
            n++;
            return true;
        }
        return false;
    }

    /**
     * Remove um elemento da primeira posicao da lista e movimenta os demais
     * elementos para o inicio da mesma.
     * 
     * @return Elemento a ser removido.
     */
    public Game removerInicio() {
        if (n > 0) {
            Game item = array[0];
            n--;

            for (int i = 0; i < n; i++)
                array[i] = array[i + 1];

            return item;
        }
        return null;
    }

    /**
     * Remove um elemento da ultima posicao da lista.
     * 
     * @return Elemento a ser removido.
     */
    public Game removerFim() {
        if (n > 0)
            return array[--n];
        return null;
    }

    /**
     * Remove um elemento de uma posicao especifica da lista e movimenta os demais
     * elementos para o inicio da mesma.
     * 
     * @param pos: Posicao de remocao.
     * @return Elemento a ser removido.
     */
    public Game remover(int pos) {
        if (n > 0 && pos >= 0 && pos < n) {
            Game item = array[pos];
            n--;

            for (int i = pos; i < n; i++)
                array[i] = array[i + 1];

            return item;
        }
        return null;
    }

    /**
     * Mostra os elementos da lista separados por espacos.
     */
    public void ordenar(int esq, int tamArq) {
        int i = esq;
        int j = tamArq;
        int pivo = ((esq + tamArq) / 2);
        String x = array[pivo].getName();
        while (i <= j) {
            while (array[i].getName().compareTo(x) < 0)
                i++;
            while (array[j].getName().compareTo(x) > 0)
                j--;
            if (i <= j) {
                tempG = array[i];
                array[i] = array[j];
                array[j] = tempG;
                i++;
                j--;
            }
        }
        if (esq < j) {
            ordenar(esq, j);
        }

        if (i < tamArq) {
            ordenar(i, tamArq);
        }
    }

    /**
     * Procura um elemento e retorna se ele existe.
     * 
     * @param x int elemento a ser pesquisado.
     * @return true se o item existir, false caso contrário.
     */
    public void pesquisa(int idInput, int tam) {
        int esq = 0;
        int dir = tam - 1;
        int meio = -1;
        int x = -1;
        boolean resp = false;
        while (esq <= dir) {
            meio = (esq + dir) / 2;
            x = array[meio].getAppId();
            if (idInput == x) {
                resp = true;
                esq = tam;
            } else if (idInput < x) {
                dir = meio - 1;
            } else {
                esq = meio + 1;
            }
        }
        if (resp == true) {
            array[meio].print(idInput);
        } else {
            MyIO.println("NAO");
        }
    }
}