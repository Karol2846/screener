\# Film 3 — „Model scoringowy" (budowa modelu w Scrub + backtesty)



> Notatki + spec implementacyjny scoringu i backtestu. \*\*Trzecie\*\* z 4 spotkań warsztatów inwestycyjnych.

> Plik źródłowy: `model\_scoringowy.txt`.



\## Kontekst materiału



\- \*\*Prowadzący (wstęp, \~11 min + zakończenie, \~ostatnie 20 min):\*\* Tomasz Trela — daje teorię „po co nam model scoringowy" (3 powody) i na koniec zapowiada spotkanie 4.

\- \*\*Prowadzący (główna część, \~1,5 h):\*\* \*\*Michał\*\* (w transkrypcie raz „Derkowski", raz „Darkowski" — ten sam szum co w filmie 2, gdzie był też „Michałowski"; ta sama osoba: technologia w Scrub). Buduje \*\*na żywo\*\* model scoringowy w `ghostscrub.com` i robi dwa backtesty.

\- \*\*Chronologia:\*\* Trela na wstępie mówi „trzecia część warsztatów", odsyła do \*\*wtorku\*\* (= spotkanie 2, „20 parametrów") i do \*\*pierwszego\*\* spotkania. Spotkanie 4 zapowiedziane na \*\*„przyszły wtorek"\*\* (strategie gotowe, ważenie/ryzyko, alerty, więcej backtestów). Kolejność merytoryczna potwierdzona: `5 etapów (1) → 20 parametrów (2) → scoring/backtest (3, TEN) → strategie/HRP/alerty (4)`.

\- \*\*Co to jest w łańcuchu 5 modułów:\*\* ten film \*\*realizuje Moduł 3 (scoring)\*\* i \*\*wchodzi w Moduł 4 (backtest)\*\* z konkretnymi liczbami. To pierwszy materiał, w którym padają \*\*wagi i progi punktacji\*\* (film 2 ich świadomie nie miał).

\- \*\*Charakter spotkania:\*\* \~60–70% to \*\*realna merytoryka\*\* (framework analizy metryk + mechanika scoringu + mechanika backtestu — sporo wartościowego, najlepszy „implementacyjnie" film z dotychczasowych). Reszta to demo featurów Scrub, mantra „pobawcie się sami, to laboratorium, nic nie zepsujecie" i CTA pod \*\*przedłużenie subskrypcji na kolejny rok\*\* (gotowe strategie zapisywane „na rocznych kontach").

\- \*\*Powtarzane zastrzeżenie:\*\* Tomek na wstępie i Michał w trakcie znów mówią, że wagi i progi wpisują \*\*„uznaniowo"\*\*, a pokazany model to „bardzo podstawowy webinarowy" szkielet (12 reguł; realne modele to 35–45 reguł). Traktuj wszystkie liczby jako punkty startowe.



\---



\## TL;DR (wersja skrócona)



Film pokazuje \*\*jak fizycznie zbudować model scoringowy\*\* — i to jest jego wartość. Trzy rzeczy warte przeniesienia do Twojej apki:



1\. \*\*Framework „5 sposobów oceny metryki" (Michał).\*\* Każdy wskaźnik ocenia się na jeden z kilku powtarzalnych sposobów; sprowadza się to do \*\*3 typów reguł\*\*: (a) \*ostatnia wartość\* vs progi, (b) \*zmiana w czasie\* (Over Time, okno w latach lub dniach), (c) \*kanał\* (pozycja bieżącej wartości względem własnej historii). To najlepsza, w pełni przenośna abstrakcja z całej serii — zbuduj scoring wokół tych 3 typów.

2\. \*\*Mechanika punktacji = liniowa interpolacja worst→best.\*\* Reguła ma `worst` (→ 0 pkt) i `best` (→ max pkt); wartości pomiędzy przeliczane proporcjonalnie. Plus flaga `Must-Have` (wartość ≤ worst → czerwony wykrzyknik „nie ruszaj"). Trywialne w kodzie.

3\. \*\*Kanał = percentyle + przełącznik poziomy/ukośny.\*\* Pas „tanio/drogo" to p15/p85 z N-letniej historii (15% czasu wysoko / 70% środek / 15% nisko). \*\*Nowość vs film 1:\*\* kierunek kanału (poziomy vs ukośny) to teraz \*\*decyzja użytkownika\*\* (`horizontal auto channel`), nie stała własność metryki — co godzi sprzeczność z filmu 1 (patrz red flags #? i sekcja rozbieżności).



Konkretny model zbudowany w filmie: \*\*2 kategorie (Growth + Value), 12 reguł, suma = 100 pkt\*\* (Growth 50 / Value 50).



\*\*Dwa backtesty — i tu uwaga:\*\*

\- \*\*S\&P 500, point-in-time membership (uczciwy):\*\* \~\*\*13–13,5% CAGR\*\* / \~300% total vs S\&P \~11,5% CAGR → przewaga \*\*\~2 pp CAGR\*\*. Sam Tomek przyznaje: 2 pp „znika" po podatkach i prowizjach.

\- \*\*Uniwersum ze screenera (\~332 spółki): \~24% CAGR.\*\* To jest headline'owa liczba, którą Tomek zapowiada „podkręcić do \~30%" w spotkaniu 4 — i \*\*najbardziej podejrzana metodycznie\*\*, bo sposób doboru uniwersum wygląda na \*\*survivorship/look-ahead bias\*\* (patrz red flags #1).



\*\*Niezmienna prawda projektu trzyma się i tu — teraz policzona:\*\* z 100 pkt modelu \*\*\~55 pkt\*\* siedzi na regułach forward/rewizje (🔴/⚫ w danych). MVP na darmowych danych policzy \*\*najwyżej \~40/100 pkt i ani jednego z sygnałów, które prowadzący nazywa najmocniejszymi\*\*.



\---



\## Notatki szczegółowe



\### Wstęp Treli — 3 powody, po co model scoringowy



(Czysto motywacyjne; merytoryka zaczyna się przy Michale.)



1\. \*\*Obiektywizacja.\*\* Ręczna analiza jest subiektywna („używam Zooma, wierzę w pracę zdalną → kupuję Zoom" = słaba teza). Model ocenia każdą spółkę \*\*tą samą miarą\*\*, eliminuje błędy poznawcze.

2\. \*\*Skala.\*\* Nie da się zatrudnić armii analityków do oceny dziesiątek tysięcy spółek × kilkadziesiąt wskaźników. \*\*\~90% roboty\*\* zdejmuje algorytm; \*\*\~10%\*\* (finalna kalibracja, miękka analiza, decyzje portfelowe) zostaje przy człowieku. \*(To „10%" jest istotne — patrz red flags #8, bo kłóci się z późniejszym „zapomnij o giełdzie".)\*

3\. \*\*Świeżość dzienna.\*\* Fundamenty zmieniają się kwartalnie, ale \*\*wycena / upside / rewizje zmieniają się codziennie\*\* wraz z kursem. Ręczna codzienna aktualizacja tysięcy spółek jest nierealna (anegdota o weselach/imieninach w sezonie wyników). Model przelicza co noc; „możesz zapomnieć o giełdzie", a algorytm wyśle alert.



\*\*Czym JEST model scoringowy (def. operacyjna):\*\* zbiór reguł/warunków logicznych. Część \*\*0/1\*\* (binarne), część w \*\*skali gradacji\*\* (logarytmicznej/liniowej). Każda reguła ma \*\*wagę\*\*. Wynik = punktacja/ranking. Cel końcowy: pokazać \*\*30 najlepszych spółek świata\*\* w danym momencie; jeśli portfel ≠ te 30 → reaguj.



\### Framework Michała — „5 sposobów oceny metryki" (rdzeń koncepcyjny)



To najlepszy fragment filmu. Michał zauważa, że mimo dziesiątek metryk \*\*sposobów ich oceny jest kilka, powtarzalnych\*\*:



1\. \*\*Ostatnia wartość.\*\* Np. marża — interesuje nas tylko bieżąca/ostatnia wartość: dobra czy nie. Dotyczy metryk zyskowności (marże).

2\. \*\*Kanał / pozycja względem historii.\*\* Np. P/S, P/E, P/CFO — bieżąca wartość \*nic nam nie mówi\* bez historii; oceniamy ją względem własnego zakresu (tanio/drogo). Dotyczy metryk wyceny.

3\. \*\*Wzrost w czasie.\*\* Np. wzrost przychodów — zmiana w ostatnich 1/3/5 latach.

4\. \*\*Obsunięcia po drodze.\*\* Czy wzrost był płynny, czy z dołkami (dwie spółki o tym samym wzroście końcowym, ale jedna stabilna, druga z zapaściami → wolimy stabilną).

5\. \*\*Estymaty/prognozy.\*\* Różnica między wartością bieżącą a prognozą analityków (np. revenue TTM vs estymata next-12M), albo porównanie dwóch prognoz (FY+1 vs FY+2).



→ \*\*Te 5 sposobów mapuje się na 3 typy reguł w narzędziu\*\* (i powinno w Twoim kodzie):



| Typ reguły (Scrub) | Co liczy | Pola konfiguracji |

|---|---|---|

| \*\*Ostatnia wartość\*\* (sposoby 1, 5) | bierze najświeższą wartość metryki | `metric`, `best`, `worst`, `maxScore`, `category`, `must-have`, kierunek (high/low preferred) |

| \*\*Metric Change Over Time\*\* (sposoby 3, 4) | zmiana metryki w oknie | jw. + `Time Frame` (lata \*\*lub\*\* dni) |

| \*\*Kanał\*\* (sposób 2) | pozycja wartości w paśmie historycznym | jw. + `Years for auto channel`, `horizontal auto channel` on/off |



> Jest też wspomniany typ \*\*„Value"\*\* — Michał go świadomie pomija, nie tłumaczy. (Do uzupełnienia, jeśli pojawi się w spotkaniu 4.)



\### Konkretny model zbudowany w filmie (Growth + Value, 100 pkt)



Pierwszy raz w serii padają \*\*realne wagi i progi\*\*. Model: 2 kategorie, suma do 100, 12 reguł. Dlaczego suma do 100: wtedy `maxScore` reguły = jej waga procentowa (wygoda).



\*\*Dlaczego w ogóle kategorie:\*\* Michał rozbija na `Growth` i `Value`, bo (a) radar/porównania per-kategoria są czytelniejsze, (b) backtest może mieć \*\*kryteria kupna/sprzedaży na poziomie kategorii\*\* (np. „kupuj tylko jeśli Value ≥ 60%", „sprzedaj jeśli kategoria-rewizje spadnie") — patrz sekcja backtest.



\#### Kategoria GROWTH (max 50 pkt, 7 reguł)



| # | Reguła | Typ | `worst` → `best` | Max pkt | Tier danych |

|---|---|---|---|---|---|

| 1 | Prognozowany wzrost \*\*EPS, 2 lata\*\* (avg next-2Y) | ostatnia wartość | progi nie dopowiedziane wprost przy tej regule¹ | 10 | 🔴 |

| 2 | \*\*EPS Long-Term Growth\*\* (\~3–5 lat) | ostatnia wartość | 10% → 20% (obniżył z 25% jako „zbyt optymistyczne"; 15% = 5 pkt) | 10 | 🔴 |

| 3 | \*\*Revenue — wzrost historyczny, 5 lat\*\* | Over Time (5 lat) | 20% → 75% — \*\*uwaga: zmiana SUMARYCZNA z 5 lat, nie średnioroczna\*\* (od 2017 do dziś) | 5 | 🟢 / 🟡² |

| 4 | \*\*Revenue — prognoza, 2 lata\*\* (avg next-2Y) | ostatnia wartość | 7% → 20% | 10 | 🔴 |

| 5 | \*\*Revenue — rewizje, 60 dni\*\* (zmiana estymaty next-12M) | Over Time (60 dni) | −7% → +7% (0% → \~połowa pkt) | 5 | ⚫ |

| 6 | \*\*CFO per share — prognoza\*\* (estimated) | ostatnia wartość | 10% → 25% | 5 (inferred³) | 🔴 |

| 7 | \*\*Operating margin\*\* | ostatnia wartość | 7% → 30% | 5 (inferred³) | 🟢 |



\#### Kategoria VALUE (max 50 pkt, 5 reguł)



| # | Reguła | Typ | Ustawienia | Max pkt | Tier danych |

|---|---|---|---|---|---|

| 8 | \*\*Price-to-Sales\*\* | kanał | 5 lat historii, kanał \*\*poziomy\*\* (horizontal ON), \*\*niskie = lepiej\*\* | \~10⁴ | 🟡² |

| 9 | \*\*EV / Cash from Operations\*\*⁵ | kanał (kopia P/S) | jw. (poziomy, niskie lepiej) | \~10⁴ | 🟡² |

| 10 | \*\*Price / Cash from Operations\*\* | kanał (kopia) | jw. | \~10⁴ | 🟡² |

| 11 | \*\*Upside\*\* (price target vs cena) | kanał | 5 lat, kanał \*\*poziomy\*\* (Michał: dla Upside ON jest „bardzo rekomendowane"), \*\*wysokie = lepiej\*\* | \~10 (intencja: nieco > P/S)⁴ | 🔴 |

| 12 | \*\*Rewizje Price Target, 30 dni\*\* | Over Time (30 dni) | −5% → +5% (0% → 5 pkt) | 10 | ⚫ |



\*\*Przypisy do tabel:\*\*

\- ¹ Dla reguły #1 Michał nie dopowiada wprost worst/best przed skopiowaniem jej na EPS LTG; logika identyczna jak #2/#4 (ostatnia wartość, prog \~10% → 20%).

\- ² Cena jest 🟢 i głęboka, ale \*\*mianownik kanału\*\* (przychód/CFO per share) wymaga \*\*historii fundamentów\*\* na 5 lat — na darmowych tierach zwykle płytka → realnie 🟡 (patrz red flags #6).

\- ³ \*\*Wagi #6 i #7 nie padają wprost w transkrypcie.\*\* Reguły #1–5 dają jawnie 40 pkt; skoro Michał potwierdza sumę Growth = \*\*50\*\*, na #6+#7 zostaje 10 pkt (przyjmuję 5+5). Sama suma 50 jest potwierdzona, rozbicie 5/5 — moje, do ewentualnej korekty.

\- ⁴ Per-regułowe wagi w Value nie są wszystkie czysto wypowiedziane. Pewne: po dodaniu Upside licznik pokazuje \*\*„40 punktów"\*\* (P/S + EV/CFO + P/CFO + Upside = 40), a \*\*rewizje PT = 10\*\* → Value = 50. Upside ma być ważony \*\*nieco wyżej niż P/S\*\* (intencja Michała), więc równe 10/10/10/10 nie do końca pasuje — traktuj „\~10" jako przybliżenie, nie kanon.

\- ⁵ Transkrypt: „Enterprise przez Cash from Operations" (EV/CFO). Film 2 wymieniał \*\*EV/EBITDA\*\*. Rozbieżność — patrz sekcja rozbieżności; do configu.



\#### Mechanika kanału (rozszerza percentyle z filmu 2)



\- \*\*Definicja pasma = percentyle.\*\* Założenie: w danym oknie \~\*\*15% czasu wartości wysokie, \~15% niskie, \~70% środek\*\* → praktycznie pas p15–p85. (Spójne z filmem 2; potwierdzone wprost: „przez 15% czasu wysoko, 15% nisko, 70% środek".)

\- \*\*`Years for auto channel`\*\* — ile historii bierzemy do pasma (Michał: 5 lat). Decyzja niesie założenie: czy włączyć anomalię covidową (wyceny skoczyły na „niewidziane poziomy"), czy ją odfiltrować. \*\*Film 1 sugerował 10 lat — tu 5.\*\* Do configu.

\- \*\*`horizontal auto channel` (on/off) — kluczowa nowość:\*\*

&#x20; - \*\*ON (poziomy):\*\* wierzysz, że wyceny \*\*wrócą\*\* do historycznych poziomów (mean-reversion); czekasz cierpliwie. Pas rysowany poziomo.

&#x20; - \*\*OFF (ukośny):\*\* wierzysz, że „rynek wie lepiej", wyceny \*\*trwale się przeszacowały\*\* (stopy, covid…) i nie wrócą; pas rysowany \*\*ukośnie\*\* wzdłuż dryfu, więc dalej dostajesz odczyt tanio/drogo, ale względem trendu — nie czekasz na powrót do poziomów sprzed lat.

&#x20; - \*\*Dla Upside:\*\* Michał mocno rekomenduje \*\*ON\*\*, bo Upside porusza się w ciasnym paśmie (\~0–50%) i rzadko trenduje w górę latami — w przeciwieństwie do P/S, które potrafi.

\- \*\*Ręczna edycja kanału (`Edit Channel`)\*\* — po dodaniu spółki do modelu można \*\*w 3 kliknięcia\*\* narysować pas ręcznie (np. „Apple nigdy nie wróci do wycen z 2018"). Nadpisane kanały oznaczone ikoną pioruna. To „tuningowanie na oko" per spółka — wygodne, ale subiektywne.

\- \*\*Kierunek punktacji:\*\* wycena (P/S, EV/CFO, P/CFO) → \*\*niska = max pkt\*\*; Upside → \*\*wysoka = max pkt\*\*. Przełącznik high/low preferred.



\### „My Companies" / watchlist / radar



\- Model ma listę \*\*My Companies\*\* (spółki śledzone). Po dodaniu spółki dostajesz: pełne rozbicie scoringu per reguła, \*\*porównanie do peers\*\*, \*\*radar\*\* (per kategoria, na tle średniej Twoich śledzonych spółek), oraz \*\*historyczne wyniki kategorii\*\*.

\- \*\*Historyczne wyniki liczone point-in-time\*\* — tylko z danych dostępnych danego dnia (anty-look-ahead). To bezpośrednia odpowiedź na pułapkę „analiza wsteczna zawsze skuteczna": wysoki Value \*dziś\* może znaczyć tylko, że spółka jest \*\*zawsze droga\*\* i wygląda „tanio" jedynie względem swojej jeszcze droższej niedawnej wersji.

\- \*\*Auto-refresh dzienny:\*\* nowe raporty kwartalne wpadają automatycznie, scoring przelicza się co dzień.



\### Mechanika backtestu (część metodycznie solidna — potwierdza film 1)



\- \*\*Zasada:\*\* „udawaj, że jest dzień X; pokaż scoring tylko z danych dostępnych wtedy" — \*\*point-in-time\*\*, ignoruje wszystko zaraportowane po X. Scrub ma „praktycznie pełną historię" fundamentów + estymat + price targetów.

\- \*\*Skala obliczeń (argument marketingowy):\*\* 12 reguł × 500 spółek × 10 lat × 12 miesięcy ≈ \*\*720 tys.\*\* ocen cząstkowych. Realny model (35–45 reguł × 1000 spółek × 15 lat) ≈ \*\*7 mln\*\* ocen. „Powodzenia w robieniu tego ręcznie."

\- \*\*Transparentność:\*\* każdą transakcję, cenę, rozmiar pozycji i wynik per reguła można prześledzić wstecz (przycisk `score` → cofa scoring do danej daty, pomarańczowa kreska pokazuje wartość metryki tego dnia).



\#### Backtest 1 — uniwersum S\&P 500 (point-in-time membership)



\- Od \*\*1 stycznia 2012\*\* (\~11 lat). \*\*Rebalancing miesięczny\*\*, wymóg \*\*3 lata od IPO\*\* (głównie pod kanały P/S — krótsza historia = bezsensowne pasmo).

\- \*\*Point-in-time membership (anti-survivorship):\*\* w każdym rebalansowaniu w portfelu mogą być \*\*tylko spółki, które tego dnia należały do S\&P 500\*\*; jeśli spółka wypadła z indeksu między rebalansami → sprzedaż przy najbliższym rebalansie. ✅ To jest zrobione poprawnie.

\- \*\*Wynik:\*\* \~\*\*13–13,5% CAGR\*\*, \~\*\*300% total\*\* vs S\&P 500 \~\*\*11,5% CAGR\*\*. Przewaga \*\*\~2 pp CAGR\*\* → \~\*\*71% różnicy w total return\*\* (procent składany). Tomek: „2% — nie ma szału", bo dochodzą \*\*podatki + prowizje\*\*.



\#### Backtest 2 — uniwersum ze screenera (\~332 spółki)



\- Ten sam model scoringowy, ale \*\*źródło spółek = wynik screenera\*\*: Market Cap > 5 mld, „Trójca" estymat forward (wzrost przychodów/EPS/EPS), metryki stabilności (zadłużenie, gotówka, CFO/dług, interest coverage, \*\*ryzyko bankructwa\*\* — Michał poprawia się z „Ohlson" na „default/szansa bankructwa"). → \~\*\*332 spółki\*\*.

\- \*\*Rebalancing kwartalny\*\* (raz na 3 mies.), 3 lata od IPO.

\- \*\*Wynik:\*\* \~\*\*24% CAGR\*\* (Tomek: „te wyniki z tych 24%", „ile wyszło 24 Michałowi"). Tak duża przewaga, że „nie musimy się martwić podatkami/prowizjami/dywidendami".

\- ⚠️ \*\*Tu jest haczyk metodyczny\*\* — patrz red flags #1. Krótko: uniwersum wygląda na \*\*dzisiejszych „ocalałych" ze screenera\*\* wstawionych w przeszłość, co kłóci się z point-in-time, który ten sam webinar chwali w Backteście 1.



\#### Dodatkowe gałki backtestu (wymienione, część pod spotkanie 4)



\- `Medium portfolio turnover` — nie wymienia spółek, gdy różnica totalscore jest mała (78% vs 78,5% → nie ruszaj).

\- \*\*Kryterium kupna per kategoria\*\* — np. kupuj tylko jeśli `Value ≥ 60%` → mogą być okresy z gotówką w portfelu (gdy wszystko przewartościowane).

\- \*\*Kryterium sprzedaży per kategoria\*\* — np. osobna kategoria zawierająca \*\*tylko reguły rewizji\*\*; jeśli jej wynik spadnie poniżej progu → auto-sprzedaż („analitycy mówią, że firma się psuje").

\- \*\*Overlay makro\*\* — sentyment konsumencki / biznesowy wpleciony w reguły (doważanie ekspozycji). Michał sam się w to nie zagłębia.

\- `Red score tolerance` — odfiltruj spółki świetne w jednej kategorii, tragiczne w innej (skrajności mimo wysokiego total).



\### Zakończenie Treli — zapowiedź spotkania 4 (głównie marketing + 1 sensowny temat)



\- „Podkręcanie wyników" z \~24% w stronę \~30% = \*\*„zdejmowanie ogranicznika"\*\* (ryzykowne): (a) \*\*porzucenie wymogu dużej kapitalizacji\*\* (small-capy: +150% albo −90%); (b) \*\*porzucenie filtrów stabilności/zyskowności\*\* (spółki zadłużone, bez zysku, „zmieniamy świat" — wyśmiewa narrację WeWork-style).

\- \*\*Dwie dźwignie tuningu\*\* (do spotkania 4):

&#x20; 1. \*\*Wskaźniki fundamentalne/jakościowe\*\* — modele zaawansowane z \*\*momentum\*\*, danymi insiderskimi, \*\*makro\*\* (sentyment, inflacja, stopy) do dynamicznego doważania.

&#x20; 2. \*\*Charakterystyka portfela\*\* — okres rebalansowania (\*\*1–3 mies. optymalnie\*\*, swing \~2–3 pp), \*\*nierówne wagi / risk parity\*\* (przykład: równe $ w spółce 2× bardziej zmiennej → przy 50% trafień i tak strata), \*\*liczba spółek\*\* (20–50, kalibracja backtestem).

&#x20; - Te manewry portfelowe dają \*\*\~2–3 pp rocznie\*\* — „nie game changer; jak wybierzesz złe spółki, optymalizacja nie pomoże".

\- \*\*Kazanie o procencie składanym\*\* (matematycznie poprawne): 100k przez 10 lat @10% = 259k; +2 pp → 310k (+51 pp total); +3 pp → 339k (+80 pp); +5 pp → +145 pp. „Walcz o każdy punkt procentowy."

\- \*\*Teza, że moment sprzedaży > moment kupna\*\* → alerty na rebalansowanie/sprzedaż „muszą być wdrożone" (zapowiedź spotkania 4).

\- \*\*CTA:\*\* gotowe strategie + kryteria screenera zapisywane „na rocznych kontach" dla \*\*przedłużających subskrypcję\*\*; sesja Q\&A w przyszły wtorek; nagrania na YouTube („Tomasz Trela inwestycje/giełda").



\---



\## SPEC DO IMPLEMENTACJI



\### Rdzeń: 3 typy reguł (zaimplementuj jako wspólny interfejs)



Cała logika scoringu sprowadza się do trzech typów. Wspólny kontrakt reguły: `metric`, `category`, `maxScore`, `mustHave: bool`, plus pola specyficzne. Wynik reguły = `\[0, maxScore]`.



```

RuleResult score(company, asOfDate):



\# Typ A — Last value (sposoby 1, 5)

&#x20; v = metric.latest(company, asOfDate)

&#x20; return interp(v, worst, best, maxScore, direction)   # direction: HIGHER\_BETTER | LOWER\_BETTER



\# Typ B — Change over time (sposoby 3, 4)

&#x20; v\_now  = metric.at(company, asOfDate)

&#x20; v\_past = metric.at(company, asOfDate - window)        # window w latach LUB dniach

&#x20; change = v\_now / v\_past - 1                           # dla rewizji: zmiana samej \*estymaty\* w oknie

&#x20; return interp(change, worst, best, maxScore, HIGHER\_BETTER)



\# Typ C — Channel (sposób 2)

&#x20; hist = metric.series(company, asOfDate - years, asOfDate)

&#x20; if horizontal: band = (percentile(hist,15), percentile(hist,85))

&#x20; else:          band = percentile\_of\_detrended(hist,15,85)   # ukośny: percentyle reszt po odjęciu trendu

&#x20; v = metric.latest(company, asOfDate)

&#x20; return position\_in\_band(v, band, maxScore, direction)



interp(v, worst, best, maxScore, dir):

&#x20; # liniowa interpolacja; v<=worst → 0, v>=best → maxScore (dla HIGHER\_BETTER; LOWER\_BETTER symetrycznie)

```



\- \*\*`interp` (worst→best)\*\* to dosłownie cała „magia" punktacji gradowanej. Zero ML, zero magii — proporcja na odcinku.

\- \*\*`mustHave`:\*\* jeśli wynik reguły = 0 → oznacz spółkę „dyskwalifikacja" (czerwony wykrzyknik) niezależnie od total. W filmie użyte do: kapitalizacji, \*\*min. liczby analityków wystawiających PT (≥ 4)\*\*.

\- \*\*Kategorie\*\* to tylko grupowanie reguł z sumą `maxScore`. Total = suma po regułach. Trzymaj sumę = 100, żeby `maxScore` = waga %.



\### Tier danych per reguła (TWARDY filtr tego, co zbudujesz w MVP)



Klasyfikacja 12 reguł modelu z filmu (🟢 free | 🟡 free-z-wysiłkiem/aproksymacja | 🔴 płatne estymaty | ⚫ płatne point-in-time/rewizje):



| Reguła | Tier | Komentarz |

|---|---|---|

| Operating margin (last) | 🟢 | przychód/EBIT z TTM, darmowe |

| Revenue wzrost 5 lat (Over Time) | 🟢 / 🟡 | logika trywialna; \*\*bottleneck = głębokość historii\*\* na darmowym tierze |

| P/S, EV/CFO, P/CFO (channel) | 🟡 | cena głęboka i darmowa, ale \*\*historia mianownika\*\* (rev/CFO per share, 5 lat) płytka → kanał na za krótkim oknie |

| EPS forward 2Y (last) | 🔴 | konsensus estymat EPS |

| Revenue forward 2Y (last) | 🔴 | konsensus estymat przychodów |

| CFO/share forward (last) | 🔴 | estymata CFO — pokrycie jeszcze gorsze niż EPS/rev |

| EPS Long-Term Growth (last) | 🔴 | estymata 3–5 lat — najrzadsza/najdroższa |

| Upside (channel) | 🔴 | konsensus price targetów; \*\*kanał Upside point-in-time\*\* → ⚫ |

| Revenue revisions 60d (Over Time) | ⚫ | wymaga \*\*snapshotów estymat\*\* w czasie |

| Price target revisions 30d (Over Time) | ⚫ | wymaga snapshotów PT w czasie |



\*\*Bilans punktowy (kluczowa liczba dla MVP):\*\*

\- 🟢/🟡 (policzalne na darmowych/aproksymowanych danych): op. margin 5 + rev-hist 5 + P/S \~10 + EV/CFO \~10 + P/CFO \~10 = \*\*\~40 pkt\*\*.

\- 🔴/⚫ (poza zasięgiem MVP): EPS-fwd 10 + EPS-LTG 10 + rev-fwd 10 + CFO-fwd 5 + Upside \~10 + rev-rewizje 5 + PT-rewizje 10 = \*\*\~60 pkt\*\*.

\- \*\*Wniosek:\*\* MVP na darmowych danych odtwarza \*\*\~40/100 pkt\*\* tego modelu i \*\*ani jednego\*\* z sygnałów, które prowadzący nazywa najmocniejszymi (EPS LTG, Upside, rewizje). Połowa Value (Upside + PT-rewizje = \~20/50) też wypada. Czyli MVP = de facto \*\*model „tania + rosnące przychody historycznie + marża"\*\*, bez całej warstwy forward. To trzeba świadomie zaakceptować przy projektowaniu, a nie odkryć w połowie implementacji.



\### Backtest — wymagania (i jedna pułapka do uniknięcia)



\- \*\*Silnik point-in-time scoringu:\*\* dla daty `D` licz każdą regułę tylko z danych o `report\_date`/`estimate\_date ≤ D`. Wymaga \*\*time-stamped snapshotów\*\*: fundamentów (🟡 — restatementy nadpisują wstecz), \*\*estymat i price targetów (⚫ — to zabójca)\*\*.

\- \*\*Point-in-time membership uniwersum\*\* (dla wariantu indeksowego): potrzebna \*\*historyczna lista składowych\*\* S\&P 500 per data (🟡/🔴 — wiarygodny dataset bywa płatny).

\- 🚩 \*\*NIE rób tego, co Backtest 2 w filmie:\*\* nie ustalaj uniwersum jako „dzisiejszych ocalałych ze screenera" i nie testuj ich wstecz — to \*\*survivorship/look-ahead bias\*\*. Albo \*\*re-uruchamiaj screener point-in-time\*\* na każdym rebalansie (drogie w danych: wymaga historycznych estymat), albo jawnie udokumentuj, że uniwersum jest skażone i wynik jest górnym, optymistycznym oszacowaniem.

\- \*\*Gałki do parametryzacji\*\* (z filmu): rebalancing (okno), liczba spółek, sposób ważenia (equal vs risk-based — HRP w spotkaniu 4), `turnover tolerance` (próg różnicy totalscore), kryteria kupna/sprzedaży \*\*per kategoria\*\* (np. Value ≥ 60% na wejściu; kategoria-rewizje < próg → sprzedaż), opcjonalny model kosztów (prowizje + podatki — w narzędziu \*\*pominięte\*\*), opcjonalnie dywidendy (też pominięte → wyniki lekko zaniżone, ale to nie ratuje argumentu).

\- \*\*Raportuj nie tylko CAGR.\*\* Sam Michał mówi, że zysk to „jedna strona medalu". Min.: CAGR, total, \*\*max drawdown, zmienność, Sharpe/Sortino\*\* (w filmie wymienione, ale \*\*nie pokazane liczbowo\*\* — patrz red flags #9).



\### Reguły z template'ów (warte dorzucenia, łatwe)



Michał pokazuje gotowce — kilka jest tanich do zrobienia:

\- \*\*Free cash flow > 0\*\* (ostatni kwartał) → 🟢. Próg minimalny (choćby 1 USD).

\- \*\*Min. liczba analityków (PT) ≥ 4\*\* jako `mustHave` → wymaga liczby estymat (🔴/🟡 zależnie od źródła).

\- \*\*Dywidenda > 0\*\* (odfiltruj niepłacące) → 🟢.

\- \*\*Historia ceny — nie „stoi w miejscu" 3 lata\*\* (jeśli rynek nie wycenia wzrostu, „może wie coś, czego my nie wiemy") → 🟢.

\- \*\*Zakupy insiderów w ostatnich 30 dniach\*\* (sygnał byczy) → 🟡 (dane są publiczne w USA — Form 4/SEC — ale wymaga parsowania).



\---



\## Czego w tym filmie NIE ma



\- \*\*Ważenia portfela / HRP\*\* — jawnie odłożone na spotkanie 4 (Tomek: „za to dawali Nobla" było w filmie 1; tu tylko przykład z volatility, bez metody). Risk parity, dobór liczby spółek — szczegóły w 4.

\- \*\*Pełnego uzasadnienia DLACZEGO te wskaźniki działają\*\* — dalej brak. Framework mówi \*jak\* oceniać, nie \*czy\* dany sygnał ma premię za zwrot.

\- \*\*Definicji „peers / similar companies"\*\* — proprietary (jak w filmie 2). Radar porównuje do średniej Twoich śledzonych spółek, nie do kuratorowanej listy.

\- \*\*Wzoru bankructwa\*\* — Michał myli „Ohlson" z „default score", nie wyprowadza żadnego (Altman/Ohlson weź z zewnątrz — jak zaznaczałem przy filmie 2).

\- \*\*Wyjaśnienia Sharpe/Sortino\*\* — wymienione, „Tomek wyjaśni we wtorek". Liczbowo \*\*nie pokazane\*\* (patrz red flags #9).

\- \*\*Reguł alertów na żywo\*\* — zapowiedziane na spotkanie 4 (mechanika sprzedaży/rebalansowania). W filmie 1 były zarysowane; tu tylko wzmianka, że „moment sprzedaży decyduje".

\- \*\*Typu reguły „Value"\*\* — wymieniony w UI, pominięty bez tłumaczenia.

\- \*\*Konkretnych progów reguły #1 (EPS forward 2Y)\*\* i \*\*per-regułowych wag w kategorii Value\*\* — niedopowiedziane wprost (patrz przypisy ¹³⁴ w tabelach).

\- \*\*Strategii gotowych\*\* (dywidendowa/growth/large-cap) — będą „gratis na rocznych kontach", omówione w spotkaniu 4.



\---



\## Moje uwagi i red flags (do świadomej implementacji)



1\. \*\*Backtest 2 (\~24% CAGR) niemal na pewno ma survivorship/look-ahead bias — i to jest headline'owa liczba pod sprzedaż spotkania 4.\*\* Transkrypt opisuje: uruchom screener (na \*dzisiejszych\* danych) → wyjdzie \~332 spółki → „wybiorę te wszystkie spółki, które wyszły mi ze screenera" jako uniwersum backtestu wstecz 11 lat. To selekcja spółek, które \*\*wyglądają dobrze dziś\*\*, i testowanie ich w przeszłości — podręcznikowy look-ahead. \*\*Backtest 1 (S\&P 500) robi membership poprawnie point-in-time; Backtest 2 — nie.\*\* Ten sam webinar, który słusznie chwali anti-survivorship, łamie go przy liczbie, którą najbardziej eksponuje. \*(To moja interpretacja opisu z transkryptu — możliwe, że Scrub re-screenuje point-in-time na każdym rebalansie i Michał tylko to skrótowo opisał; \*\*zweryfikuj zachowanie narzędzia\*\* zanim uznasz 24% za cokolwiek. Ale jeśli działa jak opisane, 24% jest zawyżone.)\*



2\. \*\*Jedyny uczciwy backtest (S\&P 500) pobił benchmark o \~2 pp CAGR — i sam prowadzący przyznaje, że 2 pp znika po podatkach i prowizjach.\*\* Czyli metodycznie czysty wynik to \*\*mniej więcej break-even po kosztach\*\*. To jest liczba, do której warto kotwiczyć ocenę metody — nie 24%. Świadomie zwracam uwagę: pozytywny (13,5% vs 11,5%) i „rozczarowujący" (po kosztach \~0 przewagi) wynik to \*\*ten sam wynik\*\* — Tomek sam to mówi, zanim przejdzie do „a teraz podkręćmy".



3\. \*\*Wagi i progi dalej „uznaniowe" — struktura 100 pkt / 2 kategorie to wygoda prezentacyjna, nie walidacja.\*\* Obniżenie best EPS LTG z 25% na 20% „bo zbyt optymistycznie" to czysta intuicja na żywo. Nie koduj tych liczb jako prawd; to parametry do kalibracji backtestem na Twoich danych. (Spójne z filmami 1–2 — podtrzymuję.)



4\. \*\*Framework „5 sposobów oceny metryki" to najlepsza, w pełni przenośna rzecz z całej serii.\*\* Tu akurat „brzmi przekonująco" = „ma sens" = „da się zaimplementować" — rzadki tercet w tym materiale. Mapuje się czysto na 3 typy reguł. Zbuduj rdzeń scoringu wokół tej abstrakcji (interfejs `Rule` + 3 implementacje), niezależnie od tego, które konkretne metryki zasilisz.



5\. \*\*Liniowa interpolacja worst→best jest sensowna i trywialna — bez red flaga.\*\* Jedyna uwaga: „logarytmiczna/gradowana skala", o której mówią, to w praktyce zwykła interpolacja liniowa na odcinku (tak to pokazane). Nie szukaj tu ukrytej zaawansowanej matematyki — nie ma jej.



6\. \*\*Kanały wyceny na darmowych danych = pułapka głębokości historii (powtórka red flaga z filmu 2, teraz dotyczy 3 z 5 reguł Value).\*\* Pasmo p15/p85 dla P/S liczone na 1–2 latach (bo tyle masz darmowej historii fundamentów) jest niestabilne. Przełącznik poziomy/ukośny to \*\*uczciwe przyznanie, że zgadujemy reżim\*\* — ale to nadal zgadywanie. W MVP: albo osobne (zebrane/płatne) źródło historii fundamentów, albo jawnie krótsze okno + ostrzeżenie o niestabilności.



7\. \*\*\~55–60/100 pkt modelu siedzi na danych 🔴/⚫ — to jest projekt w pigułce, teraz policzony.\*\* Reguły, które prowadzący sam nazywa najmocniejszymi (EPS LTG, Upside, obie rewizje), są jednocześnie najdroższe/niedostępne. To nie przypadek tego filmu — to struktura całej strategii. Decyzja MVP-vs-pełna-wersja powinna paść \*\*na tej liczbie\*\*: czy budujesz „pół modelu" (40 pkt, bez forward) na darmo, czy płacisz za estymaty.



8\. \*\*„Zapomnij o giełdzie, algorytm pilnuje za ciebie" jest przesadzone — i kłóci się z własnym „10% zostaje przy człowieku".\*\* Tomek na wstępie mówi, że \~10% (finalna kalibracja, miękka analiza, \*\*timing sprzedaży\*\*) zostaje przy człowieku, a na końcu sam podkreśla, że \*\*moment sprzedaży decyduje o zysku bardziej niż kupno\*\*. Czyli najbardziej wpływająca na wynik część jest tą nieautomatyzowalną — co bezpośrednio przeczy „set and forget". Daily auto-scoring + alerty są realne; „passive lifestyle" — nie dla tej strategii.



9\. \*\*Sharpe/Sortino wymienione, ale ani razu nie pokazane liczbowo — a jedyną pokazaną metryką jest CAGR (sam zwrot).\*\* Michał wprost mówi, że „zysk to jedna strona medalu" (zmienność, drawdown), po czym… nie pokazuje drugiej strony. Nie przyjmuj na wiarę, że strategia jest lepsza \*\*ryzykowo-skorygowana\*\* — pokazano tylko, że ma wyższy zwrot (a small-capowy wariant z spotkania 4 z definicji podbije zwrot kosztem właśnie zmienności/drawdownu). W swoim backteście \*\*policz i pokaż drawdown + zmienność + Sharpe od początku\*\*, inaczej powielasz tę samą lukę.



10\. \*\*Kazanie o procencie składanym jest poprawne, ale retorycznie naładowane.\*\* „+2 pp → +51 pp total" to prawda i właściwa intuicja — ale użyte, żeby \~2% przewagi (która, jak Tomek sam przyznał, ginie w kosztach) brzmiało monumentalnie. Procent składany \*\*działa w obie strony\*\*: 2 pp drenażu z podatków/prowizji/poślizgów składa się przeciwko Tobie tak samo. Cytując ich własną matematykę przeciw nim: jeśli „czysty" backtest daje +2 pp brutto, a realne koszty to \~2 pp, to po 10 latach składasz… zero przewagi nad tanim ETF-em na S\&P 500.



\---



\### Rozbieżności między filmami (→ jeden config, nie dwie „prawdy")



| Parametr | Film 1 | Film 2 | Film 3 (TEN) | Rekomendacja |

|---|---|---|---|---|

| \*\*Kanał — kierunek\*\* | P/S „ukośny", Upside „poziomy" (stałe per metryka) | percentyle p15/p85 | \*\*przełącznik `horizontal` per reguła\*\*; Upside ON rekomendowany | \*\*Reconcyliacja:\*\* „ukośny P/S" z f.1 = `horizontal=OFF`. Zrób kierunek \*\*tunable per reguła\*\*, default ON; dla Upside wymuś ON. |

| \*\*Kanał — okno historii\*\* | 10 lat (P/S) | — | \*\*5 lat\*\* (`Years for auto channel`) | Config, tunable. Default 5; pamiętaj o pułapce głębokości danych (red flag #6). |

| \*\*Kanał — definicja pasma\*\* | ±1–2σ | \*\*percentyle 15/85\*\* | \*\*percentyle (15/70/15)\*\* | Bierz \*\*percentyle\*\* (f.2 i f.3 zgodne; σ z f.1 odrzuć). |

| \*\*Rewizje — okno\*\* | 60 dni | 60 dni | \*\*revenue 60 dni, ale price target 30 dni\*\* | Dwa różne okna w \*\*tym samym\*\* modelu. Zrób `revisionWindow` per reguła; nie zakładaj jednej wartości. |

| \*\*EV/cośtam (wycena)\*\* | — | \*\*EV/EBITDA\*\* | \*\*EV/CFO\*\* („Enterprise przez Cash from Operations") | Rozbieżność. Zaimplementuj oba jako wybieralną metrykę; nie hardcode'uj jednego. |

| \*\*Revenue forward — prog\*\* | — | filtr ≥ 5% (średniorocznie) | \*\*scoring\*\* worst 7% / best 20% | Inny obiekt (filtr skanera vs reguła scoringu) + inne liczby. Trzymaj osobno: `screener.revFwdMin` vs `scoring.revFwd.{worst,best}`. |

| \*\*EPS LTG / EPS forward — prog\*\* | EPS LTG „najmocniejszy" (bez progu) | filtr ≥ 10% | \*\*scoring\*\* EPS LTG worst 10% / best 20% (obniżone z 25%) | jw. — filtr vs reguła; wszystkie do configu jako startowe. |

| \*\*Op. margin — prog\*\* | — | demo: >10% (lub >30% tech) | \*\*scoring\*\* worst 7% / best 30% | Zbieżne kierunkowo; do configu. |

| \*\*Liczba spółek / rebalancing\*\* | 20–35 spółek; 1–3 mies. | — | Backtest 2: kwartalny (3 mies.) | Spójne; szczegóły (HRP, optimum liczby spółek) w spotkaniu 4. |



> \*\*Zasada na cały projekt:\*\* wszystkie progi/wagi/okna → \*\*jeden plik config\*\* (per-reguła, tunable), z komentarzem „źródło: film N, wartość uznaniowa wg prowadzącego". Żadnej liczby z tych filmów nie traktuj jako stałej w kodzie.

