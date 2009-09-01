/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package klient.encje;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

/**
 *
 * @author ArekP
 */
@Entity
public class kontrahent implements Serializable {

    @TableGenerator(name = "KONTRAHENT_GENERATOR", table = "KONTRAHENT", schema = "redakcja", pkColumnName = "ID")
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String typ;//tablica DIC_TYPflaga(autor,reklama,redaktor,barter)
    private String imie;
    private String nazwisko;
    private String ulica;
    private String miasto;
    private String email;
    private String tel;
    private String nip;
    private String pesel;
    private String urzadSkarbowy;
    private String numerKonta;
    private double stawka;
     private String info;

 public  kontrahent(){
     
 }

    public  kontrahent( String typ, String imie, String nazwisko, String ulica, String miasto, String email, String tel,
    String nip, String pesel, String urzadSkarbowy, String numerKonta,double stawka)
    {
    this.typ=typ;
    this.imie=imie;
    this.nazwisko=nazwisko;
    this.ulica=ulica;
    this.miasto=miasto;
    this.email=email;
    this.tel=tel;
    this.nip=nip;
    this.pesel=pesel;
    this.urzadSkarbowy=urzadSkarbowy;
    this.numerKonta=numerKonta;
    this.stawka=stawka;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof kontrahent)) {
            return false;
        }
        kontrahent other = (kontrahent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "klient.encje.kontrahent[id=" + id + "]";
    }

    /**
     * @return the typ
     */
    public String getTyp() {
        return typ;
    }

    /**
     * @param typ the typ to set
     */
    public void setTyp(String typ) {
        this.typ = typ;
    }

    /**
     * @return the imie
     */
    public String getImie() {
        return imie;
    }

    /**
     * @param imie the imie to set
     */
    public void setImie(String imie) {
        this.imie = imie;
    }

    /**
     * @return the nazwisko
     */
    public String getNazwisko() {
        return nazwisko;
    }

    /**
     * @param nazwisko the nazwisko to set
     */
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    /**
     * @return the ulica
     */
    public String getUlica() {
        return ulica;
    }

    /**
     * @param ulica the ulica to set
     */
    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    /**
     * @return the miasto
     */
    public String getMiasto() {
        return miasto;
    }

    /**
     * @param miasto the miasto to set
     */
    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel the tel to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return the nip
     */
    public String getNip() {
        return nip;
    }

    /**
     * @param nip the nip to set
     */
    public void setNip(String nip) {
        this.nip = nip;
    }

    /**
     * @return the pesel
     */
    public String getPesel() {
        return pesel;
    }

    /**
     * @param pesel the pesel to set
     */
    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    /**
     * @return the urzadSkarbowy
     */
    public String getUrzadSkarbowy() {
        return urzadSkarbowy;
    }

    /**
     * @param urzadSkarbowy the urzadSkarbowy to set
     */
    public void setUrzadSkarbowy(String urzadSkarbowy) {
        this.urzadSkarbowy = urzadSkarbowy;
    }

    /**
     * @return the numerKonta
     */
    public String getNumerKonta() {
        return numerKonta;
    }

    /**
     * @param numerKonta the numerKonta to set
     */
    public void setNumerKonta(String numerKonta) {
        this.numerKonta = numerKonta;
    }

    /**
     * @return the stawka
     */
    public double getStawka() {
        return stawka;
    }

    /**
     * @param stawka the stawka to set
     */
    public void setStawka(double stawka) {
        this.stawka = stawka;
    }

    /**
     * @return the info
     */
    public String getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(String info) {
        this.info = info;
    }
    
        public String getNazwiskoImie() {
        return getNazwisko()+" "+getImie();
    }
}
