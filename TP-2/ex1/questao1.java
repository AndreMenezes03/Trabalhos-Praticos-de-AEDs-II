import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;
import javax.xml.datatype.DatatypeConfigurationException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

class Game {
    private String[] stringGame;
    private String[] stringID;
    private String currentGame;
    private int app_id;
    private String name;
    private Date release_date;
    private String owners;
    private int age;
    private float price;
    private int dlcs;
    private String[] languages;
    private String website;
    private boolean windows;
    private boolean mac;
    private boolean linux;
    private float upvotes;
    private int avg_pt;
    private String developers;
    private String[] genres;

    // Game(){

    // }

    void setGabarito(String[] stringGame, String[] stringID) {
        this.stringGame = stringGame;
        this.stringID = stringID;
    }

    void read() {
        int linha;
        String[] stringID;
        stringID = new String[4403];
        Arq.openRead("tmp/games.csv", "UTF-8");
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

    public void print(String id) {
        MyIO.println(getResp(id));
    }

    private String getResp(String id) {
        setID(id);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM/yyyy", Locale.ENGLISH);
        String dataS = sdf.format(this.release_date);
        String linguaS = Arrays.toString(this.languages);
        String genreS = Arrays.toString(this.genres);
        String upvoteS = formUp();
        String priceS = priceUp();
        String lula_ptS = formPt();
        String resposta = this.app_id + " " + this.name + " " + dataS + " " + this.owners + " " + this.age + " "
                + this.price + " " + this.dlcs + " " + linguaS + " " + this.website + " " + this.windows + " "
                + this.mac + " " + this.linux + " " + upvoteS.concat("%") + " " + lula_ptS + " " + this.developers + " "
                + genreS;
        return resposta;
    }

    private String priceUp() {
            return String.format("%,02f",this.price);
    }

    private String formPt() {
        int min = this.avg_pt % 60;
        int hr = (this.avg_pt - min) / 60;
        return hr + "h" + " " + min + "m";
    }

    private String formUp() {
        if (this.upvotes == (long) this.upvotes)
            return String.format("%d", (long) this.upvotes);
        else
            return String.format("%s", this.upvotes);
    }

    private void setID(String id) {
        int i = 0;
        while (i != -1) {
            if (this.stringID[i].equals(id) == false)
                i++;
            else {
                int pos = i;
                this.app_id = Integer.valueOf(id);
                setVar(pos);
                break;
            }
        }
    }

    private void setVar(int pos) {
        this.currentGame = this.stringGame[pos];
        setName();
        setDate();
        setOwn();
        setAge();
        setPri();
        setDLC();
        setLang();
        setSite();
        setPlat();
        setUp();
        setPT();
        setDev();
        setGen();
    }

    private void setGen() {
        String[] aGenTemp = this.currentGame.split(",");
        this.genres = aGenTemp;
    }

    private void setDev() {
        String aDevTemp = this.currentGame;
        String[] teste = aDevTemp.split("[\"]");
        if (teste.length == 1) {
            int indexTempDia = aDevTemp.indexOf(",");
            String devTempDia = aDevTemp.substring(0, indexTempDia);
            this.developers = devTempDia;
            this.currentGame = aDevTemp.substring(indexTempDia + 1);
        } else if (teste.length == 2) {
            teste[0] = teste[0].replaceAll("[,\"]", "");
            this.developers = teste[0];
            this.currentGame = teste[1];
        } else if (teste.length == 4) {
            if (teste[0].equals("") == false)
                this.developers = teste[0];
            else
                this.developers = teste[1];
            if (teste[2].equals(",") == false)
                this.currentGame = teste[2];
            else
                this.currentGame = teste[3];
        }

    }

    private void setPT() {
        String aPTTemp = this.currentGame;
        int indexTemp = aPTTemp.indexOf(",");
        String PTTemp = aPTTemp.substring(0, indexTemp);
        this.avg_pt = Integer.valueOf(PTTemp);
        this.currentGame = aPTTemp.substring(indexTemp + 1);
    }

    private void setUp() {
        String aUpTemp = this.currentGame;
        int indexTemp = aUpTemp.indexOf(",");
        String upTemp = aUpTemp.substring(0, indexTemp);
        int upvotes = Integer.valueOf(upTemp);
        this.currentGame = aUpTemp.substring(indexTemp + 1);
        aUpTemp = this.currentGame;
        indexTemp = aUpTemp.indexOf(",");
        upTemp = aUpTemp.substring(0, indexTemp);
        int downvotes = Integer.valueOf(upTemp);
        this.currentGame = aUpTemp.substring(indexTemp + 1);
        float total = upvotes + downvotes;
        float porco = (upvotes / total) * 100;
        this.upvotes = (float) Math.floor(porco);
    }

    private void setPlat() {
        int i = 0;
        for (i = 0; i < 3; i++) {
            String aWMLTemp = this.currentGame;
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
            this.currentGame = aWMLTemp.substring(indexTemp + 1);
        }

    }

    private void setSite() {
        if (this.currentGame.indexOf(",") == 0) {
            this.currentGame = this.currentGame.replaceFirst(",", "");
        }
        if ((this.currentGame.indexOf("T") == 0) || (this.currentGame.indexOf("F") == 0)) {
            this.website = "null";
        } else if ((this.currentGame.indexOf(",") == 0)) {
            this.website = "null";
            this.currentGame = this.currentGame.replaceFirst(",", "");
        } else {
            String aSitTemp = this.currentGame;
            int indexTemp = aSitTemp.indexOf(",");
            String sitTemp = aSitTemp.substring(0, indexTemp);
            this.website = sitTemp;
            if (sitTemp.equals("") == true)
                this.website = "null";
            this.currentGame = aSitTemp.substring(indexTemp + 1);
        }
    }

    private void setLang() {
        String aLangTemp = this.currentGame;
        int indexTemp = aLangTemp.indexOf("]", 0);
        String langTemp = aLangTemp.substring(0, indexTemp + 1);
        langTemp = langTemp.replaceAll("['\"]", "");
        langTemp = langTemp.replace("[", "");
        langTemp = langTemp.replace("]", "");
        this.currentGame = aLangTemp.substring(indexTemp + 2);
        String[] arLang = langTemp.split(",");
        this.languages = arLang;
    }

    private void setDLC() {
        String aDLCTemp = this.currentGame;
        int indexTemp = aDLCTemp.indexOf(",");
        String DLCTemp = aDLCTemp.substring(0, indexTemp);
        int DLCFinal = Integer.valueOf(DLCTemp);
        this.dlcs = DLCFinal;
        this.currentGame = aDLCTemp.substring(indexTemp + 1);
    }

    private void setPri() {
        String aPreTemp = this.currentGame;
        int indexTemp = aPreTemp.indexOf(",");
        String preTemp = aPreTemp.substring(0, indexTemp);
        float precoFinal = Float.valueOf(preTemp);
        this.price = precoFinal;
        this.currentGame = aPreTemp.substring(indexTemp + 1);
    }

    private void setAge() {
        String aOwnTemp = this.currentGame;
        int indexTemp = aOwnTemp.indexOf(",");
        String ownTemp = aOwnTemp.substring(0, indexTemp);
        int ownerFinal = Integer.valueOf(ownTemp);
        this.age = ownerFinal;
        this.currentGame = aOwnTemp.substring(indexTemp + 1);
    }

    private void setOwn() {
        String aOwnTemp = this.currentGame;
        int indexTemp = aOwnTemp.indexOf(",");
        String ownTemp = aOwnTemp.substring(0, indexTemp);
        this.owners = ownTemp;
        this.currentGame = aOwnTemp.substring(indexTemp + 1);
    }

    private void setDate() {
        DateFormat og = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH);
        String aDateTemp = this.currentGame;
        if ((aDateTemp.indexOf(",") == 6) || (aDateTemp.indexOf(",") == 7)) {
            int indexTemp = aDateTemp.indexOf(",", aDateTemp.indexOf(",") + 1);
            String dataTemp = aDateTemp.substring(0, indexTemp);
            dataTemp = dataTemp.replaceAll("[\"]", "");
            this.currentGame = aDateTemp.substring(indexTemp + 1);
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
            this.currentGame = aDateTemp.substring(indexTemp + 1);
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
        String aNameTemp = this.currentGame;
        int indexTemp = aNameTemp.indexOf(",");
        String nameTemp = aNameTemp.substring(0, indexTemp);
        this.name = nameTemp;
        this.currentGame = aNameTemp.substring(indexTemp + 1);
    }
}

class questao1 {
    public static void main(String[] args) {
        String id = "";
        Game jogo = new Game();
        Game jojo = jogo.clone();
        jojo.read();
        while (id.equals("FIM") != true) {
            id = MyIO.readLine();
            if (id.equals("FIM") == true)
                break;
            jojo.print(id);
        }
    }

}