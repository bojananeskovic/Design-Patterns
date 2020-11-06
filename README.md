# Design-Patterns
Korišćenjem *Java Swing* implementirana desktop aplikacija za rad sa 2D grafikom.
Funkcionalnosti:
1. crtanje oblika različitim bojama (boja ivice i boja unutrašnjosti) za šta je korišćena *JColorChooser* klasa,
2. unutrašnjost oblika krug sa rupom transparentna (java.awt.Graphics2D, java.awt.Shape, java.awt.geom.Area, java.awt.geom.Ellipse2D),
3. nazivi klasa, metoda i promenljivih su na engleskom jeziku,
4. aplikacija realizovana u skladu sa MVC arhitektonskim obrascem,
5. dodavanje, brisanje i modifikacija šestougla (hexagon) upotrebom Adapter obrasca za hexagon.jar,
6. poništavanje izvršenih komandi (*undo* funkcionalnost) – *Command* i *Prototype* obrazac, ponovno izvršenje poništenih komandi (*redo* funkcionalnost) – *Command* i *Prototype* obrazac, *undo* i *redo* dugme dostupni samo kada je dostupna i funkcionalnost,
7. generisanje i prikaz loga izvršenih komandi u okviru aplikacije,
8. zapis u tekstualnu datoteku loga izvršenih komandi na eksterni memorijski medijum, zapis kompletnog crteža (*Serialization*) na eksterni memorijski medijum, - *Strategy* obrazac,
9. učitavanje tekstualne datoteke koja sadrži log izvršenih komandi i na osnovu sadržaja, kreiranje odgovarajućeg crteža, komandu po komandu u interakciji sa korisnikom, učitavanje kompletnog crteža,
10. promenu pozicije oblika po Z osi, *To Front* (pozicija po pozicija), *To Back* (pozicija po pozicija), *Bring To Front* (na najvišu poziciju), *Bring To Back* (na najnižu poziciju),
11. prikaz trenutno aktivne boje za crtanje ivice i popunjavanje oblika, klikom na boju, otvara se dijalog sa mogućnošću promene trenutno aktivne boje,
12. omogućena selekcija više oblika,
13. dugme za brisanje dostupno samo u slučaju da postoje selektovani objekti – *Observer* obrazac,
14. dugme za modifikaciju dostupano samo u slučaju kada je selektovan samo jedan oblik – *Observer* obrazac.
