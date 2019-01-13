package helpers.connector;

public class EgovCurrency {
    private String id;
    private String name_kaz;
    private String edinica_izmerenia;
    private String sootnowenie;
    private String name_rus;
    private String kurs;
    private String kod;

    public EgovCurrency(){}

    public EgovCurrency(String id, String name_kaz, String edinica_izmerenia, String sootnowenie, String name_rus, String kurs, String kod) {
        this.id = id;
        this.name_kaz = name_kaz;
        this.edinica_izmerenia = edinica_izmerenia;
        this.sootnowenie = sootnowenie;
        this.name_rus = name_rus;
        this.kurs = kurs;
        this.kod = kod;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName_kaz() {
        return name_kaz;
    }

    public void setName_kaz(String name_kaz) {
        this.name_kaz = name_kaz;
    }

    public String getEdinica_izmerenia() {
        return edinica_izmerenia;
    }

    public void setEdinica_izmerenia(String edinica_izmerenia) {
        this.edinica_izmerenia = edinica_izmerenia;
    }

    public String getSootnowenie() {
        return sootnowenie;
    }

    public void setSootnowenie(String sootnowenie) {
        this.sootnowenie = sootnowenie;
    }

    public String getName_rus() {
        return name_rus;
    }

    public void setName_rus(String name_rus) {
        this.name_rus = name_rus;
    }

    public String getKurs() {
        return kurs;
    }

    public void setKurs(String kurs) {
        this.kurs = kurs;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }
}
