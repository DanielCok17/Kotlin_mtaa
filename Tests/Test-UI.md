# Akceptačné testy

## 1. Pridanie knihy

### pre-condition:

Prihlásenie ako admin.

### post-conditon:

Vzniknutá kniha v **DB** a ukázaná v katalógu.

### postup:

1. Admin stlačí iconu menu, ktoré sa vyroluje z ľavej strany

2. Stlačí položku `Add book`

3. Zobrazí sa formulár pridania knihy

4. Admin vyplní položky formuláru

5. Admin stlačí tlačidlo `Add`

6. Zobrazí sa pop-up o pridaní knihy

7. Po stlačeni alebo po 3 sekundách pop-up zmizne

### výsledok:

Pridanie knihy do **DB**.

## 2. Zlé pridanie knihy

### pre-condition

Prihlásenie ako admin.

### post-conditon

Zaniknutá kniha v **DB**, a nezobrazenie v katalógu.

### postup:

1. Admin stlačí iconu menu, ktoré sa vyroluje z ľavej strany

2. Stlači položku `Add book`

3. Zobrazí sa formulár pridania knihy

4. Admin vyplní aspoň jednu položku z formuláru zle

5. Admin stlačí tlačidlo `Add`

6. Zobrazí sa pop-up o chybnom pridaní knihy

7. Po stlačení alebo po 3s pop-up zmizne

### výsledok:

Vypísanie chybovej hlášky a nepridanie knihy do DB.

## 3. Zmazanie knihy

### pre-condition

Prihlásenie ako admin.

### post-conditon

Odobratá book_count v **DB**, upravený počet kníh v rezervačnom okne knihy.

### postup:

1. Admin vyberie želanú knihu z katalógu alebo ju vyhľadá pomocou funkcie `Search` alebo prostredníctvom `ID` vo `Find book by ID`

2. Zobrazí sa profil knihy

3. Admin stlačí ikonu `-` (mínus)

4. Zobrazí sa potvrdzovacie pop-up odobratia, ktoré musí Admin potvrdiť

5. Zobrazí sa pop-up o potvrdení odobratia

6. Po stlačeni alebo po 3s pop-up zmizne

### výsledok:

Odobratie z množstva voľných kníh.

## 4. Rezervovanie knihy

### pre-condition

Prihlásenie sa ako obyčajný používateľ.

### post-conditon

Rezervovaná kniha.

### postup:

1. Používateľ vyberie želanú knihu z katalógu alebo ju vyhľadá pomocou funkcie `Search`

2. Zobrazí sa profil knihy

3. Používateľ klikne na ikonu `+` (plus)

4. Zobrazí sa potvrdzovacie pop-up rezervácií, ktoré musí Používateľ potvrdiť

5. Zobrazí sa pop-up o potvrdení rezervácie

6. Po stlačeni alebo po 3s pop-up zmizne

### výsledok:

Používateľovi sa pridá kniha do profilu a odpočíta sa množstvo volných kníh.

## 5. Chybné upravenie stavu knihy

### pre-condition

Prihlásenie sa ako admin.

### post-conditon

Neupravený stav/kondícia knihy.

### postup:

1. Admin vyberie želanú knihu z katalógu alebo ju vyhľadá pomocou funkcie `Search` alebo prostredníctvom `ID` vo `Find book by ID`

2. Zobrazí sa profil knihy

3. Admin stlačí ikonu `History`

4. Zobrazí sa okno s históriou knihy, kde admin klikne na tlačidlo `Edit state`

5. Zobrazí sa okno, kde admin upravý stav/kondíciu knihy

6. Po stlačení tlačidla `Change state` vybehne chybová správa pop-up, o zlom upravení stavu knižky

### výsledok:

Po chybovom zadaní stavu knihy aplikácia vypiše pop-up správu o chybovom/nepoznanom stave. 





------


## 6. Filtrovanie kníh podľa kategórií

### pre-condition

Prihlásenie sa ako obyčajný používateľ alebo admin.

### post-conditon

Zobrazenie kníh, ktoré spadajú do daného filtra.

### postup:

1. Používateľ v pravom hornom rohu klikne na ikonu `Filter`

2. Zobrazí sa okno s možnosťou vyberu rôznych filtrov

3. Po zvolení filtra alebo viacerých filtrov musí používateľ potvrdit navolený filter tlačidlom `Confirm`

4. Aplikácia vráti používateľa naspäť na hlavnú stránku aplikácie a vypíše knihy, ktoré spadajú do zadanej kategórie

### výsledok:

Používateľovi sa zobrazia filtrované knihy.

## 7. Pridanie komentára

### pre-condition

Prihlásenie sa ako obyčajný používateľ.

### post-conditon

Pridaný komentár

### postup:

1. Používateľ vyberie želanú knihu z katalógu alebo ju vyhľadá pomocou funkcie `Search`

2. Zobrazí sa profil knihy

3. Používateľ napíše komentár a klikne na tlačidlo `Add comment`

4. Zobrazí sa pop-up o úspešnom pridaní komentára

5. Po stlačení alebo po 3s pop-up zmizne

### výsledok:

Používateľ bude mať v profile knihy pridaný komentár.

## 8. Upravenie stavu knihy

### pre-condition

Prihlásenie sa ako admin.

### post-conditon

Upravený stav/kondícia knihy.

### postup:

1. Admin vyberie želanú knihu z katalógu alebo ju vyhľadá pomocou funkcie `Search` alebo prostredníctvom `ID` vo `Find book by ID`

2. Zobrazí sa profil knihy

3. Admin stlačí ikonu `History`

4. Zobrazí sa okno s históriou knihy, kde admin klikne na tlačidlo `Edit state`

5. Zobrazí sa okno, kde admin zadá čislo, ktoré reprezentuje stav/kondíciu knihy

6. Po stlačení tlačidla `Change state` sa v book_item upraví stav knihy

7. Aplikácia vypíše pop-up o úspešnom upravení stavu knihy

### výsledok:

Kniha bude mat v profile upravený záznam o stave, poprípade bude stiahnutá z možností výberu ak stav bude nevyhovujúci. Vtedy sa upraví aj počet volných kníh na požičanie.

## 9. Vypísanie kníh používateľa

### pre-condition

Prihlásenie sa ako obyčajný používateľ.

### post-conditon

Vypísané zapožičané knihy.

### postup:

1. Používateľ stlačí iconu menu, ktoré sa vyroluje z ľavej strany

2. Stlačí položku `My books`

3. Zobrazí sa okno s vypísanými knihami

### výsledok:

Používateľ bude vediet, ktoré knihy ma zapožičané a na ako dlho.

## 10. Vyhľadanie knihy

### pre-condition

Prihlásenie sa ako obyčajný používateľ alebo admin.

### post-conditon

Nájdená kniha

### postup:

1. Používateľ v pravom hornom rohu klikne na ikonu `Search` alebo v ľavom hornom rohu vyroluje menu a zvolí možnosť `Search`

   1.  Ak sa používateľ prihlási ako admin, môže knihu vyhladať cez `Find book by ID` pomocou `ID`

   2.  Po zadaní `ID` knihy sa zobrazí profil knihy

2. Zobrazí sa vyhľadávacie okno s možnosťou zadania názvu knihy

3. Po zadaní názvu knihy, sa zobrazia knihy s daným názvom

### výsledok:

Používateľovi sa zobrazia hľadané knihy, ak admin hľadá knihu podla `ID`, vtedy mu zobrazí priamo profil knihy.


























