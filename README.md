# AGH - WIMiIP
### Aplikacja do nauki pytań na egzamin inżynierski
#### Akademia Górniczo-Hutnicza, Wydział Inżynierii Metali i Informatyki Przemysłowej

*Aplikacja jest kompletna, z jej pomocą zdasz egzamin spokojnie na > 90%.* - przynajmniej na rok 2015. Jeśli pojawiają się nowe pytania - to dajcie znać

* Aplikację można pobrać tutaj: https://play.google.com/store/apps/details?id=com.pastew.isexam&hl=pl
* Odpowiedzi można znaleść tutaj: http://is-exam.axartmedia.com/results.php
* Statystyki można obejrzeć tutaj: http://is-exam.axartmedia.com/stats.php

Hint: Jeśli nie masz smartfona z Androidem to ściągnij Bluestacks na PC i tam zainstaluj aplikację.

Wszelkie poprawki mile widziane.

### Jak skompliować program u siebie na PC
1. Pobierz Android Studio 3.0 (starszy też powinien działać).
1. Sklonuj to repo.
1. Otwórz w Android Studio.
1. Android Studio powinno podpowiedzieć jakie build toolsy należy pobrać, pobierz je.
1. Powinno działać.

### Dlaczego pytania są w jpg, a nie jako tekst?
Bo jest dużo dziwnych wzorów których nie da się zapisać jako tekst.

### Jak dodać nową kategorię z pytaniami do aplikacji
Żeby dodać nową kategorię z pytaniami do aplikacji trzeba zmodyfikować 3 miejsca:
1. Do folderu *assets* należy dodać 4 obrazki dla każdego pytania: img_712_p.jpg - obrazek z pytaniem, img_712_a.jpg, img_712_b.jpg, img_712_c.jpg - obrazki z odpowiedziami a,b,c. Uwaga - jpg skompresuj żeby zajmowały jak najmniej miejsca. Zadbaj żeby miały podobny rozmiar jak dotychczasowe pytania.
2. W pliku *subjects.txt* dopisać nową kategorię, z zakresem pytań (obustronnie włącznie)
3. W pliku *answers.txt* dopisać odpowiedzi.
4. I tyle. Nie trzeba nic zmieniać w kodzie programu.

Dla pomocy stworzyłem przykładowy branch w którym dodałem dwa nowe pytania pod jedną nową kategorią, możesz się na nim wzorować:
https://github.com/Pastew/ISEngineerExam/tree/new_category_example

### Kontakt z autorem
* github
* kpastew@gmail.com
* www.facebook.com/kpastew
