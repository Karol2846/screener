\# Film 4 — „Zarządzanie portfelem" (optymalizacja portfela + alerty/monitoring + Q\&A)



> Notatki + spec implementacyjny Modułu 5 (monitoring/alerty) i parametrów budowy portfela. \*\*Czwarte i ostatnie\*\* z 4 spotkań warsztatów inwestycyjnych.

> Plik źródłowy: `zarzadzanie\_portfelem.txt` (auto-transkrypt YT, 2h48m, sporo szumu w nazwiskach i liczbach).



\## Kontekst materiału



\- \*\*Chronologia — POTWIERDZONA z nagrania.\*\* Pierwsze zdanie: \*„witam na czwartej części naszych warsztatów"\* (Trela, 0:00). Michał wita się \*„po raz trzeci podczas czwartego spotkania"\* (38:26) i odsyła do swojego wystąpienia \*„w zeszły czwartek"\* (= spotkanie 3). Trela zapowiada zamknięte spotkanie nr 5 na \*„przyszły wtorek, 20 grudnia"\* (1:54). Łańcuch z briefu się zgadza: `5 etapów (1) → 20 parametrów (2) → scoring/backtest (3) → TEN film (4)`. Brak rozbieżności w numeracji.

\- \*\*Prowadzący (godz. 1, \~0:00–36:40):\*\* Tomasz Trela — teoria optymalizacji portfela (liczba spółek, ważenie, rebalancing, „zdejmowanie ogranicznika") + backtesty demonstracyjne.

\- \*\*Prowadzący (godz. 1 cd., \~36:40–1:24):\*\* \*\*Michał\*\* — tu nazwisko \*\*Borkowski\*\* (kolejny wariant; w filmach 2–3 było „Tarkowski / Darkowski / Michałowski / Derkowski" — ten sam człowiek od technologii, szum transkrypcji). Moduł 5 na żywo: alerty, portfolio, rebalancing.

\- \*\*Trela (\~1:24–1:59):\*\* podsumowanie „idealnego workflow" (5 kroków) + blok CTA/sprzedaż.

\- \*\*Sesja Q\&A (\~1:59–2:48):\*\* \~50 min pytań i odpowiedzi. \*\*Tu pada najważniejszy fragment metodyczny całego filmu\*\* (patrz red flag #1).

\- \*\*Charakter spotkania:\*\* \*\*najgęstszy marketingowo film z całej serii\*\* — dokładnie jak zapowiadał brief. Realnej, przenośnej merytoryki jest mniej niż w filmie 3; sporo czasu to demo featurów Scrub, powtarzane CTA i obietnice funkcji, \*\*których jeszcze nie ma\*\*. Merytoryczne jądro: (a) parametry budowy portfela, (b) mechanika alertów/portfolio (Moduł 5), (c) jedno realne przyznanie metodyczne w Q\&A.

\- \*\*Powtarzane zastrzeżenie (znów):\*\* progi/wagi „uznaniowe", gotowe strategie to „punkt startowy, nie wynik czteroletnich backtestów" (Michał, 41:38). Traktuj wszystkie liczby jak punkty startowe — spójne z filmami 1–3.



\---



\## TL;DR (wersja skrócona)



Film 4 robi dwie rzeczy: (1) \*\*domyka Moduł 5\*\* (monitoring/alerty/portfolio) realną, ale \*\*trywialną implementacyjnie\*\* mechaniką, i (2) pokazuje, \*\*jak „podkręcać" wyniki\*\* parametrami portfela — przy czym całe „podkręcanie" stoi na \*\*skażonej bazie 24,54% CAGR z filmu 3\*\*, a twórcy \*\*sami przyznają w Q\&A, że point-in-time „skaner przeszłości" jeszcze nie istnieje\*\*.



Co warto wynieść (przenośne):



1\. \*\*Position sizing ≠ equal weight.\*\* Wagi dobierane wg ryzyka: im niższa zmienność spółki, tym większa waga (wygładza krzywą, ogranicza drawdown). Miary: \*\*Sharpe\*\* (zysk/zmienność), \*\*Sortino\*\* (zysk/downside-deviation), Calmar. \*\*Pierwszy raz w serii Sharpe i Sortino są wytłumaczone koncepcyjnie\*\* — i to jest poprawne. Dane: liczone z samych cen → 🟢; robota realna → 🟡.

2\. \*\*Liczba spółek: 20–30 (częściej 20).\*\* Powyżej \~35–40 dywersyfikacja przestaje obniżać ryzyko, a zaczyna dociągać Cię do benchmarku. Poniżej \~20 ryzyko rośnie. To \*\*zakres do kalibracji backtestem per strategia\*\*, nie stała.

3\. \*\*Rebalancing: miesięczny optymalny w większości przypadków.\*\* Co kwartał/pół roku „potrafi obniżyć zysk o kilka pp rocznie". Parametr do configu.

4\. \*\*Mechanika alertów + portfolio (Moduł 5) = czysty kod, zero drogich danych.\*\* Trzy typy alertów; najlepszy — \*\*Company Score z progiem względnym do własnego kanału historycznego spółki\*\* (reguła „kanał" z filmu 3 zastosowana do szeregu czasowego score'u, auto-aktualizowana dziennie). Rebalancing = `planowana − bieżąca alokacja → kup/sprzedaj`. \*\*To najtańszy implementacyjnie moduł z całej serii.\*\*



Co \*\*NIE\*\* zostało dostarczone (wbrew zapowiedziom z filmu 3 — patrz sekcje niżej):



\- \*\*Czystego „\~30% CAGR" nie ma.\*\* Pada 24,54% → \*\*25,54%\*\* (+1pp z parametrów) i „niemal podwojenie total profit" po zdjęciu market-capu. „30%" w transkrypcie dotyczy \*\*wyłącznie progów Upside\*\*, nigdy CAGR portfela.

\- \*\*HRP — trzeci namedrop w serii, nadal bez wyjaśnienia\*\* („czarna magia, zapraszam na płatny uniwersytet").

\- \*\*Sharpe/Sortino/Calmar — ani jednej wartości liczbowej.\*\* Tylko koncepcja + „większy". Jedyne twarde liczby ryzyka w całym filmie: \*\*drawdown 29% vs 33,92% S\&P\*\* i \*\*zmienność 20% vs 17%\*\*.



\*\*Niezmienna prawda projektu trzyma się i tu:\*\* wąskim gardłem są DANE. Mechanika Modułu 5 jest darmowa do napisania, ale \*\*wartość\*\* alertów/monitoringu zależy od tego, co monitorują — a najmocniejsze sygnały (rewizje, Upside, Total z forward) to dalej 🔴/⚫. MVP odpali Moduł 5 na „pół-modelu" (\~40/100 pkt): ta sama mechanika, słabszy sygnał.



\---



\## Notatki szczegółowe



\### Część 1 — optymalizacja portfela (Trela, godz. 1)



\#### Liczba spółek (3:00–9:34)



\- Oparcie: Modern Portfolio Theory (Markowitz — Trela mówi „56 roku"; \*\*faktycznie 1952\*\*, „Portfolio Selection"; Nobel 1990 — drobny błąd, patrz red flag #10).

\- Mechanizm: dywersyfikacja obniża zmienność do \*\*ryzyka systemowego\*\* (rynkowego). Krzywa płaska od \~30–35 spółek; przy 500 spółkach masz wahania indeksu „co do drugiego miejsca po przecinku".

\- \*\*Wniosek: 20–30 spółek\*\* (zakres, nie wartość). >40 = praktycznie benchmark (słabe szanse na alfę); <20 = rośnie ryzyko. Optimum \*\*zależy od strategii\*\* (inne dla dywidendowej, growth, large-cap) → kalibruj backtestem.



\#### Position sizing / ważenie (9:34–17:00)



\- \*\*Equal weight „się nie sprawdza"\*\* (powtórka z filmu 1).

\- \*\*Ważenie wg ryzyka:\*\* mniejsza zmienność → większa waga (wygładza krzywą zysków/strat, ogranicza drawdown). Trade-off: spółki niskozmienne dają zwykle niższy zwrot → potrzeba ich więcej.

\- \*\*Sharpe\*\* (13:02): zysk ÷ zmienność; >1 = zyski przewyższają wahania → doważaj. \*\*Definicja wytłumaczona poprawnie.\*\*

\- \*\*Sortino\*\* (14:28): wariant Sharpe'a liczący tylko \*\*downside deviation\*\* (wahania w dół), bo wahań w górę „nie ma się co bać". >1 = dobrze. \*\*Też wytłumaczone poprawnie.\*\*

\- „Tych wskaźników jest masa, my w Scrabie mamy je wszystkie" (expected ratio, qualirisk, Calmar…). Position sizing „nie jest tematem na dziś, zapraszam na uniwersytet inwestycyjny" → faktyczna metoda nieomówiona, tylko nazwy.

\- Po wyliczeniu idealnych wag (anegdota: A=8%, B=7,5%, C=9%) wagi z czasem \*\*dryfują\*\* (jedne akcje rosną, inne spadają) → potrzeba \*\*rebalansowania\*\* (analogia: dopompowanie koła w aucie).



\#### Rebalancing (17:00–25:00)



\- Kiedy? Meta-analiza setek backtestów: \*\*miesięczny rebalancing optymalny w większości przypadków\*\*; przyjmuje się okno \*\*1–3 mies., nigdy >3\*\*.

\- Co kwartał/pół roku „potrafi obniżyć zysk o kilka pp rocznie".

\- Demo na bazowej strategii Michała z filmu 3: \*\*kwartalnie/30 spółek/równe wagi → 24,54% CAGR, $100k → $957k\*\*. Po zmianie na \*\*miesięcznie/20 spółek/equal-risk + tolerancja braków → +1pp → 25,54%, $100k → $1,096k\*\* (Trela: +1pp = +$139k po 10 latach).

\- 25 spółek (miesięcznie) → niżej; 2-mies. rebalancing → \~2× mniejszy zysk; 3-mies. → jeszcze gorzej. → miesięczny wygrywa.



> ⚠️ Liczby total-return w transkrypcie miejscami się nie spinają (957k / 1096k / 572k) — auto-transkrypt. Solidna jest \*\*baza 24,54% i kierunek (miesięczny rebalancing optymalny)\*\*; kwot nie cytuj co do dolara.



\#### „Zdejmowanie ogranicznika" — kluczowa sekcja podkręcania (25:43–33:30)



To miało być (wg zapowiedzi z filmu 3) podbicie \~24% → \~30%. Co faktycznie pokazano:



\- \*\*Zdjęcie Must-Have kapitalizacji (5 mld):\*\* „robimy to samo co Michał w zeszłym tygodniu, odhaczamy jeden parametr" → \*\*Total Profit „niemal się podwaja"\*\*. (Dopuszcza small-capy, egzotyczne giełdy.)

\- \*\*+ lepsza alokacja (mniej wagi spółkom ryzykownym) + zdjęcie wymogu 3 lat od IPO\*\* (dopuszcza spółki <2 lata na giełdzie → gorsza jakość historii, kanał P/S liczony z 2 lat zamiast 10, zaburzony anomaliami/pandemią).

\- \*\*TWARDE LICZBY RYZYKA (jedyne w całym filmie):\*\* max drawdown portfela \*\*29% vs 33,92% S\&P\*\* (2020); zmienność \*\*20% vs 17%\*\*. Sharpe/Calmar/Sortino — \*\*„większe", bez wartości\*\*.

\- Trela uczciwie: redukcja drawdownu przy zdjętym ograniczniku to \*\*„wyjątek, nie reguła"\*\* — zwykle wyższy zwrot = wyższy drawdown. (Patrz red flag #4: ale to liczone na skażonej puli bez martwych spółek.)

\- Filozofia ryzyka (30:08–33:00): magnat z $100k „kieszonkowego" może kupować small-capy; przy oszczędnościach życia unikaj <1 mld i rynków nieprzejrzystych (przywołuje Wirecard — „sprawozdania fałszowane"). Rekomendacja: market-cap jako \*\*„wentyl bezpieczeństwa"\*\*, + 3–5 ryzykownych small-capów jako \*\*wyjątek, nie reguła\*\*, i tak wyciągasz +2pp.

\- Inna, „bardziej zaawansowana" strategia (z momentum / analizą techniczną / dodatkowymi kategoriami fundamentów) → „udostępnimy za free". Dla niej `20 → 30 spółek` dało wyższy zwrot (957k → \~1200k; „300% niemalże lepszy" = prawdopodobnie szum transkryptu, nie bierz dosłownie).



\#### Ogólne wnioski Treli (34:30–36:40)



\- Najczęściej sprawdza się: \*\*20–30 spółek (częściej 20), rebalancing miesięczny, wagi wg ryzyka.\*\*

\- Ważenie: parametrem \*\*Sortino / „qualirisk" / HRP\*\* — \*„nie będę tego rozwijał, koncept zbyt zaawansowany… w Scrabie sprowadzamy to do zaznaczenia tikerem"\* (36:04). → \*\*HRP = checkbox, metoda nietłumaczona\*\* (patrz red flag #5).



\### Część 2 — Moduł 5: alerty + portfolio (Michał, \~38:00–1:24)



\#### Gotowe strategie (template'y) (39:40–42:40)



\- Przy `New scoring model` są template'y: \*\*dywidendowy / growth / growth-z-rozsądną-wyceną / mocno value\*\*. Disclaimer (41:38): \*„to nie są strategie najlepsze, nie wynik naszych czteroletnich backtestów — punkt startowy"\*. → To są \*\*gotowce poglądowe\*\*, nie zwalidowane strategie. (Zapowiadane w filmie 3 „gratisy za przedłużenie subskrypcji".)



\#### Alerty — trzy typy (42:54–1:03:00)



1\. \*\*Prosty próg na metryce jednej spółki\*\* (np. „Apple Upside > 60%"). Znany z innych narzędzi. Wada: trzeba ustawiać per spółka i ręcznie pilnować, który z 5 progów się odpalił. Michał zbywa jako mało ciekawe.

2\. \*\*Alert na cały scoring model:\*\*

&#x20;  - \*\*(a) Zmiana składu Top-N\*\* (per kategoria LUB Total): np. „Total Top 10" → mail, gdy ktoś wypada z dziesiątki, a ktoś wchodzi, z tabelką 4 spółek (2 wypadłe + 2 nowe) i ich bieżącymi wynikami. Analogicznie „20 najtańszych wg Value".

&#x20;  - \*\*(b) Przekroczenie progu absolutnego kategorii\*\*: „Value ≥ 80%" → alert dla każdej spółki, która przekroczy. (Michał przyznaje \*\*błąd UI\*\*: tekst mówi „20 Companies", powinien „20%".)

&#x20;  - Sens: zamiast 5 alertów na 5 metryk wyceny, jeden alert na \*\*wypadkową kategorię\*\* Value.

3\. \*\*Company Score (najsprytniejszy, 55:00–1:02:00):\*\* alert per spółka na wynik kategorii/Total „high" lub „low" — ale \*\*próg jest WZGLĘDNY do własnego kanału historycznego spółki\*\*, nie absolutny. Mechanika identyczna jak kanał wyceny: w oknie 5 lat wynik był „wysoki" \~15% czasu, „niski" \~15%, środek \~70% → \*\*percentyle p15/p85 na szeregu czasowym samego score'u\*\*. Przykład: Apple Value avg 37 (5 lat), próg „high" ≈ 60. \*\*Auto-aktualizowany dziennie\*\* — nie musisz edytować progu, gdy historia spółki się przesuwa (w przeciwieństwie do sztywnego „Upside > 30%", które się dezaktualizuje). Działa w obie strony (kupno: spółka stała się tania; sprzedaż: przestała rosnąć / zdrożała).



\#### Portfolio + rebalancing (1:03:00–1:24:00)



\- \*\*4 poziomy automatyzacji:\*\*

&#x20; 1. Lista tickerów (sama lista).

&#x20; 2. + \*\*bazowy scoring model\*\* → druga tabelka z wynikami Twoich spółek wg modelu (Growth/Value), bez klonowania reguł.

&#x20; 3. \*\*Monitoring-only + śledzenie transakcji\*\*: wpisujesz „12 Apple, 4 MSFT, 8 PayPal" + ceny zakupu → P/L, planowana alokacja vs bieżąca.

&#x20; 4. \*\*`on + Top companies`\*\* = mechaniczne odwzorowanie backtestu: kup top 20–30 spółek modelu z wagami Scrub; przycisk `Rebalancing` pokazuje dokładne transakcje, by utrzymać top-N (np. „sprzedaj całkiem Apple/MSFT — poza top 30; PayPal najbliżej, pozycja 53").

\- \*\*Zakładka Rebalancing:\*\* `planowana alokacja (wg ustawień) − bieżąca pozycja (sztuki × kurs) → kup/sprzedaj firmę X, kwota Y` (a w wersji ulepszanej także liczba sztuk). Trywialne.

\- \*\*Osobny „monitoring scoring model"\*\* (1:05–1:11): domyślnie zawiera \*\*wyłącznie rewizje analityków + Upside\*\* (price target revisions 30d/7d) — wykrywa „czy w spółce nie zaczyna się psuć". Przykład: Apple ma najniższy wynik przez \*\*PT revisions −1% (30d) / −1,13% (7d)\*\* = prognozy cięte; PayPal — płaskie/rosnące. To rozdziela „czy dobra/tania" (model główny) od „czy się psuje" (model monitoringu). \*\*Rdzeń = rewizje = ⚫\*\* (najdroższe dane).

\- \*\*Details tab:\*\* zmiana ceny, data wyników, \*\*konsensus analityków 1–5\*\* (5 = wszyscy „kup"), podział portfela na branże/sektory (sanity check: czy 20–30 „cudownych" spółek nie jest z jednej branży).

\- \*\*Allocation (uproszczony widok, 2:35):\*\* tickery + kwota + sposób ważenia → jedno kliknięcie → ile czego kupić, bez pełnej konfiguracji portfolio.



\### Część 3 — „idealny workflow" Treli (1:30–1:38)



1\. \*\*Skaner\*\* — z \~37 tys. spółek odfiltruj 90–99% „badziewia" konserwatywnymi kryteriami (near-bankruptcy: 20% prawd. w 12 mies., <3 mies. gotówki, płynność).

2\. \*\*Zbuduj/przerób scoring model\*\* (= strategia). Sky is the limit.

3\. \*\*Backtest\*\* — zweryfikuj, czy założenia mają sens (feedback loop: słaby wynik → wróć do kroku 2, przekalibruj wagi). Przykład: strategia dywidendowa może dać 9% total-return (z dywidendami) i \*\*nie pobić benchmarku\*\* = „brutalna pobudka".

4\. \*\*Ręczny przegląd KAŻDEJ spółki\*\* — \*„na litość boską nie omijajcie tego kroku"\* (mimo że „przeczy idei automatyzacji"). Bo np. „11% wzrostu/5 lat" może być \*\*4 lata na minusie + 1 skok jednorazowy\*\* (wyprzedaż majątku). Ręczne nadpisanie punktów + notatki (np. „za pół roku odchodzi CEO") w Scrabie jako „jedno źródło prawdy". → Patrz red flag #7.



\### Część 4 — Q\&A (\~1:59–2:48): kluczowe odpowiedzi



\- \*\*🔑 Survivorship/look-ahead (1:13 + 1:49–1:52):\*\* patrz red flag #1 — to jest sedno.

\- \*\*Koszty/podatki/FX (2:13–2:14):\*\* \*\*koszty transakcyjne POMINIĘTE\*\* (uzasadnienie: IBKR \~30¢; planują pole konfigurowalne); \*\*koszty walutowe POMINIĘTE\*\* („nie wiemy, kiedy wymienisz walutę z powrotem"); podatki — jak w filmie 1 (pominięte). Patrz red flag #6.

\- \*\*Broker (2:07):\*\* rekomendacja \*\*Interactive Brokers\*\* (najtańszy, \~30–40¢, otwiera Polakom, najszerszy dostęp globalny; „nie mam z tego bonusów"). Żaden polski broker nie pokrywa tych giełd.

\- \*\*Pokrycie globalne (2:08–2:09):\*\* najlepsze Ameryka Płn./Europa/Chiny-Azja; trochę Australii, Afryki (nie cała), Bliski Wschód (Katar, planowane UAE). → słabsza jakość danych na rynkach wschodzących.

\- \*\*Makro overlay (2:41):\*\* tylko \*\*prymitywnie\*\* — binarne reguły ekonomiczne (np. „100% SPY gdy inflacja spada, 0% gdy rośnie"), \*\*nie\*\* dynamiczne doważanie akcje/obligacje (część obligacyjna „problematyczna"). „Przyszłościowy kierunek." → jedna z „dwóch dźwigni" z filmu 3 dostarczona w wersji szczątkowej.

\- \*\*Zakres dat backtestu (2:42–2:46):\*\* rzetelne dane forward/price-targety od \*\*2006-06-01\*\* (najwcześniejszy start backtestu); część cen sięga lat 70., ale brak pokrycia analitycznego. 10 lat pokazują jako „reprezentatywne" (Chiny 2016, pandemia, spadki 2022). Chcą wydłużyć, ale \*\*ograniczeni dostępnością danych forward\*\* → potwierdza, że cała strategia jest data-bound.

\- \*\*Jakość danych non-US (2:39–2:40):\*\* „bierzemy dane jak publikowane, zakładamy 99,99% prawdziwe, weryfikowane audytem"; przyznaje fraudy (Luckin Coffee, Wirecard, Enron) jako „kroplę w morzu". → łagodne napięcie z wcześniejszym ostrzeganiem przed nieprzejrzystymi rynkami; \*\*nie robią forensic accounting\*\* — ryzyko garbage-in dla EM realne.

\- \*\*Strategy vs portfolio backtest (2:36–2:37):\*\* dwa tryby — dynamiczny dobór (domyślny) vs test Twoich konkretnych spółek / np. 60% obligacje / 40% SPY wstecz. Oba warte implementacji.

\- \*\*Funkcje „na przyszły rok" (rozsiane):\*\* kroczący skaner, backtest na pełnym uniwersum 40k, własne wskaźniki, dane forward dla całych ETF/indeksów, integracja z brokerem (1-stronna → potem auto-egzekucja, „blokowana regulacyjnie"). → patrz red flag #8.

\- \*\*CTA/cennik (1:46, 1:58–2:17):\*\* \*\*$950 jednorazowo / rok\*\* lub \*\*$99/mc jako zobowiązanie na 12 mies.\*\* (raty; $99×12=$1188 > $950); konta „wygasają jutro", deadline \*\*18 grudnia\*\* na bonusy (upgrade Professional, zamknięte szkolenie nr 5, konsultacje 1-na-1 z Michałem). Ceny „najprawdopodobniej wzrosną" w przyszłym roku. Anchor: Bloomberg $24k/rok. → patrz red flag #9.



\---



\## SPEC DO IMPLEMENTACJI



> Dobra wiadomość: \*\*mechanika całego Modułu 5 (alerty, portfolio, rebalancing) to czysty kod, niezależny od drogich danych → 🟢\*\*. To najtańszy implementacyjnie moduł z serii. Jedyna „nowa" rzecz wymagająca realnej roboty poza trywialną to \*\*position sizing wg ryzyka\*\* (Sortino/Sharpe/korelacje) — dane z cen (🟢), robota 🟡. Cała reszta filmu (podkręcanie wyników) to \*\*konfiguracja + backtest demonstracyjny, nie nowy algorytm.\*\*

>

> Klasyfikacja: 🟢 free | 🟡 free-z-wysiłkiem/aproksymacja | 🔴 płatne estymaty | ⚫ płatne point-in-time/rewizje.



\### A. Parametry budowy portfela (config, tunable — NIE kod)



| Parametr | Wartość z filmu | Tier | Komentarz / „da się na darmowych?" |

|---|---|---|---|

| `portfolio.numStocks` | 20–30 (częściej 20); >40 ≈ benchmark | 🟢 | To tylko liczba. Kalibruj backtestem per strategia. |

| `portfolio.rebalanceMonths` | 1 (optimum); zakres 1–3, nigdy >3 | 🟢 | Parametr. Miesięczny zwykle najlepszy. |

| `portfolio.weighting` | `equal` (odradzane) / `byMetric` / `correlationAdj` / `HRP` | 🟢 dane | Mechanika ważenia trywialna; \*\*liczenie wag\*\* patrz niżej. |



\### B. Position sizing wg ryzyka (jedyna realna robota z filmu 4)



```

\# wagi proporcjonalne do miary ryzyka (z historii cen)

ret\_i      = roczny zwrot spółki i (z cen)

sigma\_i    = odchylenie std zwrotów i

downside\_i = odchylenie std tylko ujemnych zwrotów i

sharpe\_i   = (ret\_i - rf) / sigma\_i

sortino\_i  = (ret\_i - rf) / downside\_i        # rf = stopa wolna od ryzyka



weight\_i   = sortino\_i / sum\_j(sortino\_j)      # „wysokie Sortino → większa waga"

\# wariant correlationAdj: skoryguj o macierz korelacji (spółki z jednej branży nie liczą się podwójnie)

```



\- \*\*Tier danych: 🟢\*\* (wszystko z cen, dostępne za darmo: yfinance/stooq). \*\*Robota: 🟡\*\* — liczenie zwrotów/zmienności/downside/korelacji + rebalansowanie wag. Realne w MVP.

\- `HRP`: \*\*patrz red flag #5\*\* — metoda NIE wytłumaczona w serii. Z wiedzy ogólnej (do weryfikacji): klastrowanie hierarchiczne macierzy korelacji + recursive bisection; macierz kowariancji z cen = 🟢, ale to realna robota i jest wrażliwa na estymację kowariancji (López de Prado 2016). \*\*W MVP pomiń HRP; zacznij od `byMetric(sortino)`\*\* — daje 80% korzyści przy 20% złożoności.



\### C. Alerty (mechanika 🟢; sygnał dziedziczy tier modelu)



```

nightly:

&#x20; recompute\_scores(all\_companies, asOfToday)       # przelicz model

&#x20; for alert in alerts:

&#x20;   match alert.type:

&#x20;     THRESHOLD\_SINGLE:   # typ 1 — próg na metryce jednej spółki

&#x20;       if cross(metric\[company], alert.value): fire()

&#x20;     MODEL\_TOPN:         # typ 2a — zmiana składu Top-N (per kategoria/Total)

&#x20;       today = topN(model, category, N)

&#x20;       if today != yesterday\_topN: fire(diff(today, yesterday\_topN))

&#x20;     MODEL\_THRESHOLD:    # typ 2b — próg absolutny kategorii (Value >= 80%)

&#x20;       for c in model.companies:

&#x20;         if cross(score\[c]\[category], alert.value): fire(c)

&#x20;     COMPANY\_CHANNEL:    # typ 3 — próg WZGLĘDNY do kanału historycznego spółki

&#x20;       hist = score\_series\[company]\[category]\[asOfToday - 5y : asOfToday]

&#x20;       band = (percentile(hist,15), percentile(hist,85))

&#x20;       if position\_crossed(score\[company]\[category], band, direction): fire()

```



\- \*\*Typ 3 (Company Channel)\*\* to najlepszy pomysł z filmu — reguła „kanał" z filmu 3 zastosowana do \*\*szeregu czasowego score'u\*\*. Mechanika 🟢, ale wymaga \*\*przechowywania dziennej historii score'u\*\* (osobny szereg do utrzymania). W pełni przenośny.

\- \*\*Tier sygnału:\*\* sama mechanika 🟢. Ale alert na `Total` lub `Upside` dziedziczy tier modelu: Total z forward = 🔴/⚫; Value z Upside = 🔴. \*\*W MVP alerty działają na „pół-modelu"\*\* (P/S, wzrost historyczny, marża = \~40/100 pkt) — ta sama mechanika, słabszy sygnał.



\### D. Portfolio + rebalancing (mechanika 🟢, trywialna)



```

portfolio = \[{ticker, shares, buyPrice}, ...]

plannedAlloc\[i] = cash \* weight\_i            # equal: cash/N; byMetric: wg sekcji B

currentVal\[i]   = shares\_i \* price\_i

delta\[i]        = plannedAlloc\[i] - currentVal\[i]

action\[i]       = delta\[i] > 0 ? BUY : SELL, kwota = |delta\[i]|, sztuki = kwota/price\_i

```



\- 4 poziomy automatyzacji = warstwy nad tym samym rdzeniem (1: lista → 2: + podgląd score → 3: + transakcje → 4: „Top companies" = auto top-N modelu). Wszystkie 🟢; poziom 4 dziedziczy tier modelu.

\- \*\*Osobny „monitoring model"\*\* (rewizje + Upside): mechanika 🟢, ale \*\*rdzeń = rewizje = ⚫\*\* → w MVP niedostępny. Aproksymacja: zmiana konsensusu/PT z darmowego źródła (yfinance szczątkowo i niewiarygodnie) — traktuj jako 🟡/🔴, oznacz niepewność.

\- `Details`: konsensus 1–5, podział sektorowy (sanity check branżowy). Konsensus rating bywa płatny/szczątkowy → 🟡.



\### E. Backtest — uzupełnienia z filmu 4



\- \*\*Dwa tryby:\*\* `strategyBacktest` (dynamiczny dobór — domyślny) i `portfolioBacktest` (Twoje konkretne spółki / np. 60-40 bond-SPY wstecz, max drawdown, charakterystyka). Oba warte implementacji.

\- \*\*Gałki:\*\* `rebalanceMonths`, `numStocks`, `weighting`, `turnoverTolerance` (nie ruszaj przy małej różnicy total-score), `missingDataTolerance`.

\- \*\*Raportuj NIE tylko CAGR:\*\* + total + \*\*max drawdown + zmienność + Sharpe + Sortino + Calmar (LICZBOWO!)\*\* — film pokazał tylko drawdown i zmienność, wskaźniki zostawił jako „większe" (red flag #3). Nie powielaj tej luki.

\- \*\*Constraint danych (nie config):\*\* reguły forward (EPS-fwd, Upside, rewizje) \*\*nie do backtestu przed \~2006-06\*\*. Wcześniej brak pokrycia analitycznego.

\- 🚩 \*\*Uniwersum point-in-time (red flag #1):\*\* albo re-screen point-in-time na każdym rebalansie (drogie: wymaga historycznych estymat \*\*oraz listy spółek delistowanych/zbankrutowanych\*\* — inaczej survivorship zostaje), albo \*\*jawnie udokumentuj, że uniwersum jest skażone i wynik to górny, optymistyczny szacunek\*\*. Nie powielaj podejścia Scrub (patrz niżej).



\### Rozbieżności / config — uzupełnienie do tabeli z filmu 3



> Dopisuję do \*\*jednej tabeli rozbieżności\*\* z filmu 3 (zasada projektu: jeden config per-reguła, tunable, komentarz „źródło: film N, wartość uznaniowa"). Nie powtarzam tabeli z filmu 3 — tu tylko \*\*nowe/uściślone\*\* pozycje z filmu 4.



| Parametr | Film 1 | Film 3 | Film 4 (TEN) | Rekomendacja → config |

|---|---|---|---|---|

| \*\*Liczba spółek\*\* | 20–35 | spójne | \*\*20–30, częściej 20\*\*; zakres per strategia | `portfolio.numStocks` default 20–30, tunable, kalibracja backtestem. Brak konfliktu, doprecyzowanie. |

| \*\*Rebalancing\*\* | 1–3 mies., nigdy >3 | kwartalnie (Backtest 2) | \*\*miesięczny optymalny\*\*; kwartał obniża o kilka pp | `portfolio.rebalanceMonths` default \*\*1\*\*, tunable 1–3. |

| \*\*Ważenie\*\* | equal „nigdy najlepszy"; HRP / proporcjonalnie do metryki | (do filmu 4) | equal / byMetric(Sortino) / correlationAdj / HRP — opcje Scrub | `portfolio.weighting` enum; default `byMetric(sortino)`. HRP = patrz red flag #5. |

| \*\*Market Cap Must-Have\*\* | 5 mld / „10 mld" | 5 mld (Backtest 2) | zdjęcie \~2× total profit, ale ↑ryzyko; trzymaj jako „wentyl" + 3–5 wyjątków | `screener.marketCapMin` default 5 mld + `allowExceptions` (liczba small-capów). Film 4 \*\*kwantyfikuje trade-off\*\*. |

| \*\*Lata od IPO\*\* | 3 lata | 3 lata | luzowane do <2 lat (gorszy kanał) | `screener.minYearsSinceIPO` default 3, tunable; <3 → ostrzeżenie o niestabilności kanałów. |

| \*\*Koszty/podatki/FX\*\* | pominięte (argument: IBKR \~0) | pominięte | \*\*pominięte\*\* (transakcyjne + walutowe + podatki) | `backtest.costModel` opcjonalny; w narzędziu OFF — \*\*w MVP rekomendowane ON\*\* (red flag #6). |

| \*\*Zakres backtestu\*\* | — | od 2012 (Backtest 1) | forward rzetelne \*\*od 2006-06\*\* | Constraint: reguły forward niepoliczalne wstecz przed \~2006. |



\---



\## Czego w tym filmie NIE ma



\- \*\*Czystego „\~30% CAGR"\*\* — zapowiedź z filmu 3 nie została dostarczona jako twardy wynik (red flag #2). Pada 24,54% → 25,54% i „niemal 2× total profit"; „30%" w transkrypcie = wyłącznie progi Upside.

\- \*\*Wyjaśnienia HRP\*\* — trzeci namedrop, odesłany do płatnego uniwersytetu (red flag #5).

\- \*\*Liczbowych Sharpe/Sortino/Calmar\*\* — tylko koncepcja + „większy" (red flag #3).

\- \*\*Faktycznej metody position sizingu\*\* — „masa wskaźników, mamy wszystkie", ale jak konkretnie liczone wagi — nieomówione (poza intuicją „niskie ryzyko → większa waga"). Wzór w SPEC sekcja B jest mój (standardowy), nie z filmu.

\- \*\*Dynamicznego makro overlay\*\* — tylko binarne reguły on/off wg inflacji; doważanie akcje/obligacje „przyszłościowy kierunek".

\- \*\*Danych insiderskich / momentum jako osobnych modułów\*\* — wspomniane przy „bardziej zaawansowanej strategii za free", ale niepokazane (były w zapowiedzi filmu 3 jako „dźwignia 1").

\- \*\*Kroczącego skanera / backtestu na pełnym uniwersum / własnych wskaźników / integracji z brokerem\*\* — wszystko „przyszły rok", nieistniejące (red flag #8).

\- \*\*Analizy tekstu/newsów\*\* (Q\&A 2:02): świadomie poza zakresem Scrub („przewaga jest w liczbach"); do tego trzeba TipRanks/Seeking Alpha osobno.



\---



\## Moje uwagi i red flags (do świadomej implementacji)



> Stanowisko z briefu: oznaczam, gdzie „brzmi przekonująco" ≠ „ma sens" ≠ „da się zaimplementować" — i \*\*to samo stosuję do własnego pushbacku\*\* (oznaczam, co jest z wiedzy ogólnej/do weryfikacji). Numeracja własna; krzyżowo odsyłam do red flagów z filmu 3.



\*\*1. 🔴 NAJWAŻNIEJSZE — domknięcie red flagu #1 z filmu 3: całe „podkręcanie" filmu 4 stoi na skażonej bazie 24,54%, a twórcy SAMI przyznają, że point-in-time skaner nie istnieje.\*\*

W Q\&A pada \*\*dokładnie\*\* moje pytanie z filmu 3 (1:49): \*„czy obecne backtesty na obecnym screenerze są rzetelne, skoro to spółki spełniające kryteria DZIŚ, niekoniecznie 7 lat temu?"\*. Trela odpowiada na dwa sposoby, które trzeba rozdzielić:

\- \*\*(a) Look-ahead na KRYTERIACH:\*\* twierdzi, że backtest „przed każdym rebalansem sprawdza, czy firma WTEDY spełniała kryteria" (1:50). \*\*Jeśli to prawda, to faktycznie usuwa look-ahead na kryteriach\*\* — i to jest więcej, niż zakładał mój najczarniejszy scenariusz z filmu 3 (że dzisiejsze 332 spółki są po prostu scorowane wstecz). Uczciwie: tę część należy mu zaliczyć.

\- \*\*(b) Survivorship na ISTNIENIU — ZOSTAJE:\*\* pula kandydatów to \*\*spółki żyjące dziś w bazie Scrub\*\*. Spółki, które spełniały kryteria w 2013, ale potem \*\*zbankrutowały / zostały delistowane / przejęte ze stratą\*\*, \*\*nie ma w puli w ogóle\*\* — więc nigdy nie zostaną wybrane wstecz, nawet jeśli wtedy spełniały kryteria. To podręcznikowy survivorship bias, który \*\*zawyża\*\* CAGR (brakuje przegranych).

\- \*\*Jego zapewnienie jest ekonomicznie ODWRÓCONE:\*\* mówi, że dołożenie spółek spełniających kryteria kiedyś, a nie dziś, „tylko podniosłoby wyniki, bo łowilibyśmy z większej bazy" (1:50–1:51). \*\*Nieprawda\*\* — survivorship działa odwrotnie: dołożenie spółek, które potem padły, \*\*obniżyłoby\*\* wynik (trzymałbyś coś, co poszło do zera). To red flag co do jego rozumienia (lub ramowania) biasu.

\- \*\*Sprzeczność:\*\* skoro backtest „już sprawdza point-in-time kryteria", to po co „kroczący skaner dopiero na początek przyszłego roku" (1:13, 1:49)? Najbardziej spójne czytanie: backtest re-sprawdza \*\*kryteria\*\* point-in-time \*\*na zamkniętej puli dziś żyjących spółek\*\*, ale \*\*nie potrafi zrekonstruować pełnego historycznego uniwersum\*\* (martwych/delistowanych) — i to jest ten „kroczący skaner", którego \*\*nie ma\*\*.

\- \*\*Wniosek dla apki i dla oceny:\*\* 24,54% (i wszystkie pochodne filmu 4: +1pp, +2pp, „niemal 2× total") to \*\*optymistyczny górny szacunek, nie walidacja\*\*. Kotwicą metody zostaje uczciwy backtest S\&P-500 z filmu 3 (\~13–13,5% CAGR, \~2pp nad benchmark, \*\*≈break-even po kosztach\*\*). To prawdopodobnie najważniejszy pojedynczy wniosek z całego filmu 4: \*\*obietnica „30%" buduje na zawyżonej bazie\*\* — dokładnie jak przewidywał brief.

\- \*(Część (b) i kierunek biasu to standardowa wiedza o backtestingu — pewny. „Czy Scrub faktycznie re-sprawdza kryteria point-in-time" znam tylko z deklaracji Treli — \*\*niezweryfikowane\*\*; gdyby chcieć użyć tego narzędzia, trzeba by to sprawdzić empirycznie.)\*



\*\*2. 🟡 Film NIE dostarcza czystego „\~30% CAGR" — zapowiedź z filmu 3 została przeramowana na inkrementy.\*\* Brief (za zapowiedzią filmu 3) spodziewał się „podkręcenia \~24% → \~30%". Faktycznie: 24,54% → \*\*25,54%\*\* (+1pp z parametrów portfela), plus „niemal podwojenie \*\*total profit\*\*" po zdjęciu market-capu (to o \*\*total return\*\*, nie CAGR), plus porównanie liczby spółek (957k vs \~1200k). \*\*Żaden czysty „30% CAGR" nie pada\*\* — wszystkie „30%" w transkrypcie dotyczą \*\*progów Upside\*\*. Możliwe, że z tych +2pp wychodzi \~26–27%, ale \*\*nie jest to nazwane ani pokazane\*\*. Efekt: mocna obietnica liczbowa rozmyta w retorykę „walcz o każdy punkt procentowy". Nie cytuj „30%" jako dostarczonego wyniku.



\*\*3. 🟡 Domknięcie red flagu #9 z filmu 3 — ale POŁOWICZNE.\*\* Plusy: \*\*pierwszy raz w serii\*\* Sharpe i Sortino wytłumaczone koncepcyjnie (poprawnie), pada nawet Calmar, i \*\*pokazane są twarde liczby ryzyka\*\*: max drawdown 29% vs 33,92% S\&P (2020), zmienność 20% vs 17%. Minus: \*\*same wskaźniki Sharpe/Sortino/Calmar — ani jednej wartości\*\*, tylko „większy". Czyli nadal nie wiesz, czy strategia jest lepsza \*\*ryzykownie-skorygowana\*\* — wiesz tylko, że ma wyższy zwrot, wyższą zmienność i (w jednym przykładzie) ciut niższy drawdown. W swoim backteście \*\*policz i POKAŻ Sharpe/Sortino/Calmar liczbowo od początku\*\* — inaczej powielasz tę samą lukę.



\*\*4. 🟢/🟡 „Zdejmowanie ogranicznika a zmienność" — tu Trela jest względnie uczciwy, z jednym zastrzeżeniem.\*\* Pokazuje drugą stronę medalu (drawdown 29%, zmienność 20% vs 17%) i sam zaznacza, że redukcja drawdownu przy zdjętym ograniczniku to \*\*„wyjątek, nie reguła"\*\* (zwykle wyższy zwrot = wyższy drawdown). To dobrze. \*\*ALE\*\* cały ten przykład stoi na skażonym uniwersum (red flag #1), więc „ciut niższy drawdown niż S\&P" jest liczony na puli \*\*bez martwych spółek\*\* — a to właśnie martwe spółki (bankructwa small-capów, które tu dopuszczamy) generują najgłębsze obsunięcia. \*\*Pokazany drawdown 29% jest prawdopodobnie zaniżony\*\* dla strategii, która realnie trzymałaby small-capy z rynków wschodzących.



\*\*5. 🔴 HRP — TRZECI namedrop w serii, nadal bez metody, teraz jawnie za paywallem.\*\* „Za to dawali Nobla" (f.1) → „zbyt zaawansowane" (f.3) → \*\*„czarna magia… zapraszam na uniwersytet inwestycyjny"\*\* (f.4, 2:35), sprowadzone do zaznaczenia tikera. \*\*Z serii NIE da się zaimplementować HRP — nie ma żadnej metody do przeniesienia.\*\* W SPEC celowo odradzam HRP w MVP na rzecz `byMetric(sortino)`. \*(Wzór HRP i ocena „macierz kowariancji z cen = 🟢, ale realna robota wrażliwa na estymację" — z wiedzy ogólnej, do weryfikacji w źródle, np. López de Prado 2016.)\*



\*\*6. 🟡 Koszty transakcyjne, podatki i FX — wszystkie pominięte, a to schlebia AKURAT tej strategii.\*\* Uzasadnienie (IBKR \~30¢) jest słabe dla profilu: \*\*miesięczny\*\* rebalancing × 20–30 spółek × rynki globalne/wschodzące = realny turnover, spready i koszty walutowe — zwłaszcza \*\*po zdjęciu market-capu\*\* (small-capy, egzotyczne giełdy, o których sam Trela mówił przy Wietnamie/Bangladeszu). Procent składany działa w obie strony (red flag #10 z filmu 3): 1–2 pp kosztów/poślizgów składa się przeciwko Tobie tak samo jak +2pp na korzyść. \*\*W MVP: model kosztów ON\*\* (prowizje + spread + FX + ewentualnie podatek od rebalansowania).



\*\*7. 🟢 Domknięcie red flagu #8 z filmu 3, jeszcze mocniejsze: krok 4 workflow wprost przeczy marketingowi „zapomnij o giełdzie".\*\* Sami mówią: \*„część osób będzie ten krok pomijała, bo przeczy idei automatyzacji — ale na litość boską nie omijajcie"\* (1:34). Czyli \*\*najbardziej wpływowy na jakość krok\*\* (wykrycie, że automatyczne „11% wzrostu" to 4 lata na minusie + 1 skok jednorazowy) jest \*\*ręczny i nieautomatyzowalny\*\*, podobnie jak timing sprzedaży. Daily auto-scoring + alerty są realne; „set \& forget / passive lifestyle" — \*\*nie dla tej strategii\*\*. Projektując apkę: nie obiecuj pasywności; obiecuj „mniej ręcznej roboty + alerty", a krok ręcznej weryfikacji zostaw jawnie w workflow.



\*\*8. 🟡 Ogromna część „wartości" to funkcje NIEISTNIEJĄCE, za przyszłym droższym abonamentem.\*\* Kroczący skaner, backtest na pełnym uniwersum 40k, własne wskaźniki, dane forward dla całych ETF/indeksów, integracja z brokerem (1-stronna → 2-stronna auto-egzekucja „blokowana regulacyjnie"), 5. poziom automatyzacji — wszystko „początek/pierwsza połowa przyszłego roku", „gratis dla państwa", w wersji „Professional / super-hiper-mega-Pro". \*\*To CTA, nie dostarczony produkt.\*\* Oceniaj (i ewentualnie wzoruj się na) tym, co działa \*\*dziś\*\*.



\*\*9. 🟡 Marketing/scarcity — film 4 najgęstszy sprzedażowo (zgodnie z przewidywaniem briefu).\*\* Pełny zestaw technik: \*\*urgency\*\* („konta wygasają jutro", deadline 18 grudnia, „od którego nie ma wyjątku"), \*\*scarcity/exclusivity\*\* („nikt nigdy tego nie dostanie od przyszłego roku", zamknięte szkolenie „bez osób z ulicy", 1-na-1 z twórcą), \*\*anchor-high\*\* (Bloomberg $24k/rok), \*\*mission-washing\*\* („demokratyzacja", „ze spekulanta w inwestora"), oraz wybór architektury cenowej wymuszający roczną płatność ($950 vs $99×12; miesięczny „dla tych, co nie wiedzą, co kupują"). Nic z tego nie waliduje metody. Oddzielaj twardo od merytoryki.



\*\*10. 🟢 Drobne nieścisłości i dług definicyjny.\*\* (a) Markowitz „56 roku" — \*\*faktycznie 1952\*\* („Portfolio Selection"), Nobel 1990; drobny błąd. (b) Niespójność „skaner przeszłości będzie" vs „backtest już sprawdza point-in-time" (patrz #1). (c) Liczby total-return zaszumione (957/1096/572/1200k; „$79/$99/$950") — auto-transkrypt; \*\*nie cytuj co do dolara\*\*, trzymaj się 24,54% bazowej i kierunków. (d) „300% niemalże lepszy zysk" przy 957k→1200k matematycznie się nie zgadza — prawie na pewno szum transkrypcji.



\---



> \*\*Status serii:\*\* to ostatni film. Po tym deliverable jesteśmy gotowi na \*\*końcową konsolidację całego projektu\*\* — propozycja (osobny `.md`) w wiadomości obok.

