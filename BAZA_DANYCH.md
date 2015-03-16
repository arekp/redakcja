Co potrzeba samemu stworzyć w bazie aby aplikacja działał

# Tablice dla użytkowników #
Tworzymy dwie tablice w celu zarządzania użytkownikami portalu
```
DROP TABLE IF EXISTS `redakcja`.`users`;
CREATE TABLE  `redakcja`.`users` (
  `login` varchar(50) NOT NULL,
  `password` varchar(45) NOT NULL,
  `enabled` varchar(45) DEFAULT NULL,
  `imie` varchar(100) NOT NULL,
  `nazwisko` varchar(100) NOT NULL,
  `stanowisko` varchar(100) NOT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`login`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `redakcja`.`userroles`;
CREATE TABLE  `redakcja`.`userroles` (
  `login` varchar(50) NOT NULL,
  `role` varchar(45) NOT NULL,
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
```

Reszta tablic będzie tworzona automatycznie za pomocą komponentów encyjnych EJB3



# Dane konfiguracyjne #
W celu poprawnego działania aplikacji należy wstawić pierwsze dane konfiguracyjne

"id","klucz","opis","wartosc"
  * 1,"raporty.lokalizacja.etykiety","Lokalizacja raportu dla wydruków","E:\\projekty\\redakcja\\portal\\redakcja\\redakcja-war\\web\\raport\\"
  * ,"zarzadca.id.ost.zaimport","id ostatniego zaimportowanego zarzadcy ogranicza od jakiedo numeru pobieramy","16551"
  * ,"zarzadca.id.ost.wysla","id ostatniego wys?anego zarzadcy","16550"
  * ,"zarzadca.data.import","data ostatniego importu","Tue Aug 25 14:45:37 CEST 2009"
  * ,"zarzadca.ost.blok","numer ostatniego bloku jaki pobralismy czyli od niego zaczniemy","38800"
  * ,"zarzadca.maxhits","max ilosc blokow do pobrania ostatnio","16965"
  * ,"ilosc.znakow.strona.maszynopisu","ilosc znaków na stronie maszynopisu","1800"
  * ,"dokumenty.lokalizacja","lokalizacja dokumentwo","E:\tmp\dokumenty"
  * 0,"sklad.id","id skladu z bazy kontrahentow","176"