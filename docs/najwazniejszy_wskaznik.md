# Film 2 — „Co to znaczy dobra spółka" (20+ parametrów + wizualizacja w Scrub)

> Notatki + spec implementacyjny screenera. **Drugie** z 4 spotkań warsztatów inwestycyjnych.
> Plik źródłowy: `najważniejszy_wskaźnik.txt`.

## Kontekst materiału

- **Prowadzący (godz. 1):** Tomasz Trela — przechodzi przez listę ~20 parametrów fundamentalnych, które jego zdaniem definiują „dobrą spółkę".
- **Prowadzący (godz. 2):** Michał (w transkrypcie raz „Michałowski", raz „Darkowski" — szum transkrypcji; przedstawia się jako odpowiedzialny za technologię w Scrub i twórca narzędzia) — demo modułu **wizualizacji** w `ghostscrub.com`.
- **Chronologia (ważne, bo filmy masz nie po kolei):** ten film to **spotkanie 2**. Sam Trela na wstępie mówi „witam na drugim spotkaniu" i odsyła do nagrania **pierwszego** spotkania (to, które masz już opisane jako *„5 etapów budowy portfela"* — spotkanie 1). Model scoringowy + automatyzacja są zapowiadane **na czwartek** (spotkanie 3), a „całe mięso" na czwartek i przyszły wtorek (spotkania 3–4). Czyli kolejność merytoryczna: `5 etapów (1) → 20 parametrów (2, TEN) → scoring/backtest/HRP (3) → ... (4)`.
- **Co to jest w łańcuchu 5 modułów:** ten film domyka **Moduł 1 (skaner / filtry)** i **Moduł 2 (wizualizacja)**, i przygotowuje pod **Moduł 3 (scoring — czwartek)**.
- **Charakter spotkania:** godzina 1 = realna merytoryka (katalog wskaźników, sporo wartościowego). Godzina 2 = w ~70% demo featurów Scrub + logistyka loginów/haseł + CTA „pobawcie się sami w Scrub". Przenośnej metodyki z godziny 2 jest mało — wynotowana niżej w SPEC.
- **Powtarzane zastrzeżenie prowadzącego (znów):** prawie wszystkie progi są wpisane „uznaniowo / arbitralnie / subiektywnie" — pada to dosłownie kilkanaście razy. Traktuj liczby jako punkty startowe do kalibracji, nie jako prawdy.

---

## TL;DR (wersja skrócona)

„Dobra spółka" wg prowadzącego = przejście przez ~20 parametrów fundamentalnych, ułożonych w **trzy pytania**:

1. **Czy to dobra firma?** → fundamenty: zyskowność i wzrost (Święta Trójca: **przychody, zysk, gotówka operacyjna**) + minimalna stabilność finansowa (zadłużenie, pokrycie odsetek, płynność, ryzyko bankructwa).
2. **Czy jest dziś tania?** → wycena oceniana **kanałem** wskaźnika: P/S, P/E, P/CFO, EV/EBITDA względem własnej historii.
3. **Czy to dobry moment na zakup?** → „sentyment": **Upside** (cel analityków vs cena) i **rewizje price targetu** (czy analitycy podnoszą czy tną cel).

Podział ról wskaźników: **czerwone** = twarde filtry do skanera (zero-jedynkowe Must-Have); **reszta** = do modelu scoringowego (stopniowane). **Wycena i sentyment NIE idą do skanera** — zmieniają się za szybko; trafiają do scoringu, żeby algorytm pilnował, kiedy „dobra ale droga" spółka z watchlisty stanie się tania.

Najmocniejszy implementacyjnie nugget z godziny 2: **definicja kanału = percentyle** (≈15% czasu „wysoko" / 70% „średnio" / 15% „nisko" → praktycznie pas 15.–85. percentyla), działa dla dowolnej metryki. To zastępuje rysowanie kresek „na oko".

**Niezmienna prawda projektu trzyma się i tu:** wąskim gardłem są DANE, nie kod. Najmocniejsze wg prowadzącego sygnały (Upside, EPS LTG) są jednocześnie najdroższe w danych — i, jak zaznaczam w red flags, **najsłabiej udokumentowane empirycznie**.

---

## Notatki szczegółowe

Prowadzący numeruje parametry chaotycznie (przeskakuje 11 → 13 → środek). Poniżej uporządkowane **grupami logicznymi**, tak jak sam je koncepcyjnie poukładał (dobra / tania / dobry moment), a nie wg jego numeracji.

### Grupa 0 — Wielkość (nie-fundament)

**Market Cap** — *czerwony, do skanera.* Kapitalizacja = cena akcji × liczba akcji w obiegu. Próg: **> 5 mld USD** (mówi też „normalnie 10 mld"; pada też zniekształcone „70 mld"/„pięćdziesiąt" — to artefakt transkrypcji, operacyjna liczba to **5 mld USD**, zgodna z demem i poprzednim filmem). Trzy powody unikania małych spółek:

1. **Zmienność i nieefektywność wyceny** — mały kapitał wystarczy, żeby manipulować kursem (spekulanci z portfelem 10–20 mln mogą ruszać kursem; ruchy oderwane od fundamentów).
2. **Brak zainteresowania instytucji** → brak profesjonalnych analiz, brak śledztw (PwC, Financial Times nie zaglądają), brak „dorosłego" pilnującego, by kurs oddawał sytuację firmy.
3. **(„najistotniejszy")** Małych spółek nie ma w ETF-ach. ETF-y „odpowiadają za ponad 80% obrotu" → indeksy ważone kapitalizacją nakręcają spiralę popytu na duże spółki; małe tracą ~80% „naturalnego popytu". *(Liczba 80% jest mocno zawyżona — patrz red flags.)*

### Grupa 1 — Zyskowność i wzrost („Święta Trójca": przychody, zysk, gotówka)

Logika: jako współwłaściciel chcesz, żeby rosły **przychody** (matka wszystkiego — „Top line"), bo bez przychodów nie ma zysków, dywidend, gotówki. Zysk („Bottom line") jest księgowy i podatny na manipulację. **Cash is King** — przychód księguje się przy wystawieniu faktury, a fakturę ktoś musi jeszcze zapłacić (przykład: kontenerowiec z towarem zatopiony przez piratów / bankrutujący odbiorca → przychód zaksięgowany, gotówki nie ma).

- **Revenue — historyczne (5 lat).** *Czerwony częściowo.* Chcemy **płynnego, stabilnego** wzrostu. 5 lat, nie 10–20 (firmy zmieniają produkty/tożsamość — Amazon/Apple sprzedają dziś co innego niż 20 lat temu; wyjątek Coca-Cola). Uwaga na jednorazowe skoki (np. przejęcie → połączone przychody). Ocena „płynności" wzrostu (średnia ważona / mediana YoY) jest **za zaawansowana na sam skaner** — to raczej scoring.
- **Revenue — prognoza (forward).** *Czerwony, do skanera.* Jeden zbiorczy wskaźnik **„Revenue Average Estimate"**: bierze zmianę między TTM → bieżący rok fiskalny (FY) → następny FY → FY+2, liczy zmiany średnioroczne i wyciąga **medianę**. Próg: **≥ 5%** średniorocznie. Interpretacja: 5% to nie „wzrost o 5% za 2 lata", tylko **5% średniorocznie**. Arbitralne — to dolny próg „planktonu"; idealne spółki robią 50%.
- **Revenue — rewizje (60 dni).** *Do scoringu (nie 0/1).* Jak prognoza przychodu **na kolejne 12 miesięcy** zmieniała się przez ostatnie **60 dni**. Logika: na giełdzie obowiązują trendy w estymatach — jeśli analitycy od roku tną prognozy (180 → 160 → 140 → 120), bardziej prawdopodobne, że potną dalej (przyczyny strukturalne nie znikają z dnia na dzień). Chcemy rewizji **rosnących lub płaskich**. Im większa rewizja negatywna, tym więcej punktów w dół. 60 dni arbitralne (mogłoby być 30/90). To sygnał typu „sentyment".
- **EPS — prognoza na 2 lata (current FY + next FY + FY+2).** *Czerwony, do skanera.* Próg: **≥ 10%** średniorocznie. Zysk potrafi rosnąć szybciej niż przychód (manipulacje kosztami/amortyzacją). Arbitralne — jak wyjdzie za mało spółek, luzujemy do 7%, potem 5%.
- **EPS Long-Term Growth (EPS LTG).** Prognozowany średnioroczny wzrost w horyzoncie ~**3–5 lat** do przodu. Próg: **≥ 10%**. *(W filmie 1 wskazany jako jeden z dwóch najmocniejszych pojedynczych sygnałów — i jeden z najdroższych/najrzadszych w danych.)*
- **CFO (Cash from Operations).** Gotówka **z działalności operacyjnej** — nie „Cash Flow" ogółem. Klucz: odróżnić skąd gotówka (operacje = dobrze; sprzedaż majątku albo kredyt = źle z punktu widzenia akcjonariusza). Pada też **„Cash from Operations per Share"** z progiem wzrostu **≥ 10%**. Trochę mniej istotne w tym zestawie, ale warto.

### Grupa 2 — Marże i zwroty (mniej istotne, do scoringu)

- **Operating margin / EBITDA margin.** Wyższa = lepiej. Marża operacyjna bazuje na zysku (manipulowalnym), więc alternatywa: **EBITDA margin** (przed amortyzacją, deprecjacją, odsetkami, podatkami). Mniej istotne niż wzrost przychodów/gotówki. **Marża jest branżowa**: 30% świetne dla tech, ale 13% (Caterpillar) OK dla przemysłu → konieczne porównanie sektorowe/peer. W demie próg pokazany jako op. margin > 10% albo warunkowo > 30% dla tech.
- **ROA (Return on Assets).** Zysk / aktywa. Chcemy firm robiących dużo zysku małymi aktywami (skalowalne: chmura, software > kapitałochłonne fabryki).
- **ROIC (Return on Invested Capital).** Zwrot z pozyskanego kapitału (kredyt, obligacje). **Musi być porównany z WACC** (średni ważony koszt kapitału). Jeśli ROIC < WACC → firma niszczy wartość (przykład: ROIC 5% przy WACC 7% → „pod kreską").

### Grupa 3 — Stabilność finansowa / „fundamenty" (czerwone — minimalne filtry)

Sam autor nazywa to „kwestią dyskusyjną". Teza prowadzącego: **fundamenty nie są skorelowane z ruchem kursu** (analogia: najbezpieczniejszy pojazd — czołg — nie jest najszybszy). Stąd: nie chcemy spółki maksymalnie bezpiecznej (zero długu + góra gotówki + nic nie robi = nie inwestycja), tylko **minimalnie stabilnej**, zdolnej przetrwać 2 lata kryzysu. Dlatego stabilność = lekki filtr w skanerze, nie mocno ważony scoring. *(Tezę „brak korelacji" patrz red flags — jest przesadzona.)*

- **Debt / Assets** — *czerwony.* **< 50%.** (⚠️ poprzedni dokument podawał ≤ 40% — rozbieżność, patrz red flags.) 50% to już „poziom tragiczny", zwłaszcza przy aktywach materialnych, które się szybko amortyzują (mianownik leci w dół).
- **CFO / Debt** — gotówka operacyjna do zadłużenia. Zdrowy poziom ≈ **1/3** (gotówka z 1 roku spłaca 1/3 długu → cały dług w 3 lata). Rzadko > 1.
- **Interest Coverage** — zysk netto / koszt odsetek. Chcemy **≥ 2** (zysk z jednego roku obsługuje 2 lata odsetek → „oddech" dla zarządu).
- **Current Ratio** — aktywa obrotowe / zobowiązania krótkoterminowe. Chcemy **> 1** (Quick Ratio świadomie pominięty jako „wyższa szkoła jazdy").
- **Ohlson O-Score** — zbiorczy model prawdopodobieństwa bankructwa (prof. Ohlson, lata 90.). Chcemy **< 10%**. Michał dopowiada: 100% nie oznacza dosłownie pewnego bankructwa, tylko wskazanie modelu (np. firma już w postępowaniu upadłościowym).

### Grupa 4 — Wycena (NIE do skanera — do scoringu)

Nawet wybitna firma może być „pieruńsko droga" (cytat z Buffeta o kupowaniu dobrej firmy po złej cenie). Wartości nominalne nic nie znaczą (150 USD to dużo czy mało? zależy „za co"). Jako akcjonariusz dostajesz 3 rzeczy: **przychód, zysk, gotówkę operacyjną** (dywidenda to pochodna zysku + gotówki). Każdy wskaźnik wyceny oceniamy **kanałem** względem własnej historii (10 lat, opcjonalnie 3/5/7): dół kanału = tanio = max pkt; góra = drogo = 0.

- **Price-to-Sales (P/S)** — cena do przychodu/akcję. Główny wskaźnik wyceny w demie.
- **Price-to-Earnings (P/E)** — cena do zysku/akcję.
- **Price-to-CFO** — cena do gotówki operacyjnej/akcję.
- **EV/EBITDA** — Enterprise Value (kapitalizacja + dług − gotówka) do EBITDA. „Lepsze" wersje kapitalizacji i zysku.
- **Price-to-Book (P/B)** — *wspomniany i ODRZUCONY.* Sensowny przed erą internetu (wartość księgowa = fabryki/maszyny), dziś nieistotny dla skalowalnych biznesów. Prowadzący: „neutralny, patrzeć nie będziemy". → **nie wdrażamy.**

### Grupa 5 — Dwa sygnały „sentymentu" (najwyższa deklarowana korelacja)

Dwa wskaźniki „sentymentu analityków/rynku/branży" — mówią, czy to **dobry moment** na zakup. Źródło danych: analitycy banków (JP Morgan, BofA Merrill) → prognozy agreguje FactSet → Scrub (firma „z Dubaju") kupuje od FactSet.

- **Upside** = (cel cenowy analityków na 12 mies. − cena dziś) / cena dziś. Zawsze **kolejne 12 miesięcy**, nie rok fiskalny. Logika reward/risk: przy tym samym ryzyku wolimy spółkę z 100% upside niż z 20%. Gdy kurs dojdzie do celu → Upside = 0% → sprzedaj, szukaj wyższego. Typowo 50–60% (w porywach 80%), dolna granica 0%. *(W filmie 1: ~23% CAGR w backteście — ale z przyznanej „strategii testowej", więc bez wartości dowodowej.)*
- **Rewizje price targetu** — jak analitycy zmieniają **cel cenowy** w czasie. Chcemy płaskie lub rosnące (np. miesiąc temu cel 110 → dziś 120 = ideał). Tnące rewizje (200 → 150 → 120) = zły znak, bo prawdopodobnie potną dalej i kupisz „na 120", a za pół roku cel będzie 90.

> Podsumowanie ramy: fundamenty → *czy dobra*; wycena → *czy tania dziś*; Upside + jego rewizje → *czy dobry moment*.

---

## SPEC DO IMPLEMENTACJI

### ⚠️ Klasyfikacja danych (rozbicie MVP vs pełna wersja)

Legenda:
- 🟢 **MVP / dane darmowe** — trailing/historyczne, policzalne ze sprawozdań + cen (yfinance/stooq/FMP free).
- 🟡 **MVP z wysiłkiem / kompromisem** — darmowe dane, ale realna robota lub aproksymacja.
- 🔴 **Pełna wersja** — wymaga estymat analityków (FMP płatne tiery / FactSet).
- ⚫ **Pełna wersja, najtrudniejsze** — wymaga danych **point-in-time** / historii rewizji (najdroższe i najrzadsze).

| #  | Parametr                       | Rola           | Tier | Próg (startowy)   | Uwaga implementacyjna                                                               |
|----|--------------------------------|----------------|------|-------------------|-------------------------------------------------------------------------------------|
| 0  | Market Cap (USD)               | skaner         | 🟢   | ≥ 5 mld USD       | cena × shares outstanding; **wymuś USD**, nie waluty lokalne                        |
| 1  | Revenue historyczne 5 lat      | skaner/scoring | 🟢   | wzrost + płynność | sprawozdania; ocena „płynności" (mediana/śr. ważona YoY) = scoring                  |
| 2  | Revenue forward (avg estimate) | skaner         | 🔴   | ≥ 5% śr./rok      | mediana zmian TTM→FY→FY+1→FY+2                                                      |
| 3  | Revenue revisions (60 dni)     | scoring        | ⚫   | rosnące/płaskie   | wymaga snapshotów estymat w czasie                                                  |
| 4  | EPS forward (2 lata)           | skaner         | 🔴   | ≥ 10% śr./rok     | estymaty EPS FY/FY+1/FY+2                                                           |
| 5  | EPS Long-Term Growth           | skaner/scoring | 🔴   | ≥ 10%             | estymata 3–5 lat; **rzadka i niepewna** nawet w płatnych                            |
| 6  | CFO (i CFO/share)              | scoring        | 🟢   | wzrost ≥ 10%      | cash flow statement; tylko operacyjna                                               |
| 7  | Operating / EBITDA margin      | scoring        | 🟢   | >10% / sektorowo  | porównanie sektorowe konieczne                                                      |
| 8  | ROA                            | scoring        | 🟢   | jak najwyższy     | net income / total assets                                                           |
| 9  | ROIC vs WACC                   | scoring        | 🟡   | ROIC > WACC       | WACC wymaga założeń (CAPM: beta, rf, premia + koszt długu)                          |
| 10 | Debt / Assets                  | skaner         | 🟢   | < 50% (lub 40%)   | rozbieżność z filmem 1 — parametr do ustalenia                                      |
| 11 | CFO / Debt                     | skaner/scoring | 🟢   | ~1/3+             |                                                                                     |
| 12 | Interest Coverage              | skaner/scoring | 🟢   | ≥ 2               | EBIT/odsetki lub zysk/odsetki                                                       |
| 13 | Current Ratio                  | skaner         | 🟢   | > 1               | aktywa obrotowe / zob. krótkoterm.                                                  |
| 14 | Ohlson O-Score                 | skaner/scoring | 🟡   | p(bankr.) < 10%   | wzór publiczny, ale trzeba zebrać wszystkie inputy bilansowe                        |
| 15 | P/S (kanał)                    | scoring        | 🟡   | dół=tanio         | cena darmowa; **długa historia fundamentów ograniczona w free**                     |
| 16 | P/E (kanał)                    | scoring        | 🟡   | dół=tanio         | jw.                                                                                 |
| 17 | P/CFO (kanał)                  | scoring        | 🟡   | dół=tanio         | jw.                                                                                 |
| 18 | EV/EBITDA (kanał)              | scoring        | 🟡   | dół=tanio         | EV = kap. + dług − gotówka                                                          |
| —  | Price-to-Book                  | —              | —    | **ODRZUCONY**     | prowadzący sam wyklucza — nie wdrażać                                               |
| 19 | Upside                         | scoring        | 🔴   | wyżej=lepiej      | konsensus price target 12M; w yfinance sparse/stale                                 |
| 20 | Rewizje price targetu          | scoring        | ⚫   | rosnące/płaskie   | wymaga historii PIT celów cenowych                                                  |
| —  | Sektor / peer average          | pomocnicze     | 🟡   | —                 | sektor darmowy; „similar companies" Scrub = proprietary, aproksymuj sektorem/branżą |

**Wniosek z tabeli:** cała Grupa 3 (stabilność) + ROA + marże + CFO + Market Cap + historia przychodów = **realne MVP na darmowych danych**. Kanały wyceny = MVP z gwiazdką (historia fundamentów płytka w free). Cała Grupa 5 (sentyment) + forward z Grupy 1 = **pełna wersja** — i to są jednocześnie sygnały, na których prowadzący opiera najwięcej.

### Definicja kanału (KLUCZOWY, w pełni implementowalny nugget z godz. 2)

W narzędziu „horizontal channel" = **pas percentylowy**, nie pas odchyleń standardowych:

- przez ~**15%** czasu metryka jest „wysoko" (powyżej górnej linii),
- przez ~**15%** „nisko" (poniżej dolnej),
- przez ~**70%** „średnio" (między liniami).

→ Praktycznie: **górna linia = 85. percentyl, dolna = 15. percentyl** metryki w wybranym oknie. Działa dla dowolnej metryki (P/S, Upside, marże…). To zastępuje rysowanie kresek „na oko" / „w zależności od pogody za oknem".

```
score_kanału(x) ∈ [0,1]:
  dół  (≤ p15)  → 1.0   (tanio / atrakcyjnie)
  góra (≥ p85)  → 0.0   (drogo)
  środek        → interpolacja liniowa między p15 a p85
```

⚠️ **Rozbieżność z filmem 1:** poprzedni dokument opisywał kanał P/S jako **±1–2σ i „ukośny"** (P/S dryfuje w górę), a kanał Upside jako horyzontalny. Tu demo pokazuje **percentyle 15/70/15 i kanał „horizontal"** dla każdej metryki. To dwie różne konstrukcje. Do SPEC bierz **wersję percentylową** (reprodukowalna, pokazana w narzędziu); rozbieżność zostawiam zaznaczoną.

⚠️ **Anty-look-ahead (krytyczne dla backtestu):** kanał/percentyle licz **tylko z danych dostępnych do daty rebalansowania** (okno kroczące wstecz), nie z pełnej historii. Narzędzie liczy je z okna widocznego na wykresie — w backteście to byłby look-ahead. Stałe wersje (median-3y/5y) też muszą być „as-of date".

### Estymaty forward: 12 miesięcy kroczące zamiast roku fiskalnego

Konkretny nugget metodyczny z godz. 2: do porównań z TTM używaj **kroczących prognoz na 12 miesięcy do przodu** (`revenue/EPS/CFO estimates next 12M`), a **nie prognoz na rok fiskalny**. Powód: estymaty FY aktualizują się raz w roku → blisko końca roku fiskalnego różnica TTM↔FY sztucznie maleje i tworzy skoki. Wersja krocząca 12M jest porównywalna w dowolnym momencie roku.
→ Implementacyjnie: jeśli masz tylko estymaty FY, sklej kroczące 12M jako ważoną kombinację FY i FY+1 proporcjonalnie do tego, ile dni zostało do końca FY. (To i tak tier 🔴.)

### Skaner — logika filtrów

- Wszystkie filtry skanera to warunki **`>=`** (lub `<=` dla zadłużenia). Domyślny zestaw startowy: Market Cap ≥ 5 mld USD, Revenue forward > 5%, EPS forward ≥ 10%; dorzucalne: CFO/share ≥ 10%, EPS LTG ≥ 10%.
- **Brak logiki OR** w skanerze (tylko AND). Workaround pokazany w narzędziu: **wyjątki sektorowe**, np. „odrzuć op. margin < 30%, **chyba że** sektor = przemysł". → w MVP zaimplementuj filtry jako AND + opcjonalne reguły warunkowe per-sektor.
- Strategia kalibracji progów: zacznij **rygorystycznie**; jeśli wyjdzie za mało spółek (np. <50) → **luzuj** progi. Nie odwrotnie.
- Odrzucaj spółki z nadmiarem braków danych (spójne z filmem 1).

### Scoring — to, co da się wyłuskać (reszta = czwartek)

W tym filmie **nie ma wag ani pełnej formuły** scoringu. Konkretne reguły punktacji, które padły (do uzupełnienia w spotkaniu 3):
- Revenue forward: `≥15%` wyżej niż TTM → max; `≤5%` → 1 (lub 0); pomiędzy → liniowo.
- Rewizje revenue (60 dni): pozytywne → max; spadek `≥7%` → 0.
- Wycena: przez kanał (p15/p85, patrz wyżej).
- Zasada generalna: stabilność/fundamenty raczej jako filtr (mała waga w scoringu), wzrost + sentyment jako mocno ważone sygnały.

### Pomocnicze (z demo — opcjonalne)

- **Skala log dla ceny** zawsze; liniowa dla reszty metryk. Do porównania spółek różnej wielkości → **skala zmiany %**.
- **Peer average** (w narzędziu „peers/Pierce average") = średnia metryki z listy „podobnych firm" danej spółki. Lista similar-companies jest proprietary → w MVP aproksymuj **średnią sektorową/branżową** (sektor dostępny w darmowych źródłach).
- **Makro overlay** (CPI per kraj, Univ. of Michigan Consumer Sentiment, US recession shading) — Michał sam się w to nie zagłębia; poza core'em screenera, pomiń w MVP.

---

## Czego w tym filmie NIE ma

- **Wag w modelu scoringowym** — w ogóle nie padają. Film 2 to katalog parametrów, nie ich ważenie.
- **Pełnej formuły scoringu / progów punktowych dla większości metryk** — odłożone na czwartek (spotkanie 3); tu tylko 2–3 przykłady.
- **Mechaniki backtestu** — praktycznie nietknięta (była w spotkaniu 1; głębiej w 3). Powtórzona jedynie mantra „zbuduj strategię na jednym wskaźniku i przetestuj vs S&P 500".
- **Budowy portfela** — liczba spółek, rebalancing, ważenie/HRP — **nie ma** (było w spotkaniu 1).
- **DLACZEGO poszczególne wskaźniki działają** — jawnie odłożone („za duży temat na dziś").
- **Reguł alertów / auto-sprzedaży** — nie w tym filmie (były w spotkaniu 1).
- **Quick Ratio** — świadomie pominięty jako zbyt zaawansowany.
- **Wzoru Ohlson O-Score** — nazwany, ale niewyprowadzony (musisz wziąć publiczny wzór z zewnątrz).
- **Definicji „similar companies"** dla peer average — proprietary, nie ujawniona.

---

## Moje uwagi i red flags (do świadomej implementacji)

1. **Strategia opiera się najmocniej na dwóch sygnałach, które są jednocześnie (a) najdroższe w danych i (b) najsłabiej udokumentowane empirycznie: Upside i EPS LTG.** To największy problem merytoryczny. Literatura nt. **cen docelowych analityków** jest raczej **niekorzystna** — price targety są systematycznie zbyt optymistyczne, a strategie kupowania spółek z najwyższym implikowanym upside *nie* dają wiarygodnej nadwyżki (część badań wręcz odwrotnie). „Jedna z najwyższych korelacji z przyszłymi wynikami" pada **bez dowodu**, a jedyne liczby wsparcia (~23% CAGR z filmu 1) sam prowadzący przyznał, że pochodzą z rzucanej „strategii testowej". To podręcznikowy przykład *„brzmi przekonująco" ≠ „ma merit"*. *(Mój osąd z wiedzy ogólnej — zweryfikuj, zanim oprzesz na tym strategię; ale ostrożność jest tu wskazana.)*

2. **Teza „fundamenty nie są skorelowane z ruchem kursu" jest przesadzona.** Analogia czołg-vs-prędkość jest zgrabna, ale przeskok do „więc fundamenty nie korelują ze zwrotem" to non-sequitur. Faktory jakościowe (profitability/quality, np. gross profitability, „quality-minus-junk") mają udokumentowane premie za zwrot. Decyzja projektowa (stabilność jako filtr, nie mocno ważony scoring) jest OK, ale **uzasadnienie jest fałszywe** — nie buduj na nim przekonania, że jakość nie ma znaczenia.

3. **„ETF generują ponad 80% obrotu" — liczba zawyżona.** Realnie udział ETF w *wolumenie obrotu* akcjami w USA to bardziej rząd **~25–35% wartości obrotu** (a udział w *kapitalizacji/własności* jeszcze niższy, ~10–15%). Kierunek argumentu (pasywne przepływy faworyzują duże spółki przez ważenie kapitalizacją) ma sens, ale **skala jest wymyślona**. *(Z pamięci, do weryfikacji — ale 80% to na pewno za dużo.)*

4. **Rozbieżności progów/definicji między filmami — do ujednolicenia w jednym miejscu w kodzie (config):**
   - **Debt/Assets:** ten film `< 50%`, film 1 `≤ 40%`. Wybierz jedną wartość lub zrób tunable; nie zostawiaj dwóch „prawd".
   - **Market Cap:** `5 mld` (operacyjne) vs „normalnie 10 mld" vs zniekształcone „70 mld". Przyjmij 5 mld jako default, 10 mld jako wariant rygorystyczny.
   - **Kanał wyceny:** percentyle 15/85 (ten film, narzędzie) vs ±1–2σ „ukośny" (film 1). Bierz percentyle.

5. **Sygnał rewizji jest najmocniejszy merytorycznie z całej grupy forward — ale MVP go nie zrobi.** Momentum rewizji estymat / dryf po wynikach to jedne z lepiej udokumentowanych anomalii. Ironia: to też najtrudniejszy do pozyskania sygnał (wymaga snapshotów point-in-time estymat). Czyli najbardziej wart implementacji forward-sygnał jest poza zasięgiem darmowych danych — zaplanuj to świadomie (⚫ w tabeli).

6. **Kanały wyceny na darmowych danych = pułapka głębokości historii.** Ceny masz na 10+ lat (yfinance/stooq), ale **historia fundamentów** (przychody/EPS/CFO per share kwartalnie) na darmowych tierach sięga zwykle kilku kwartałów / kilku lat. Bez tego „kanał 10-letni" P/S liczysz na kawałku — albo potrzebujesz osobnego (płatnego/zebranego) źródła historii fundamentów. Inaczej percentyle są liczone na za krótkim oknie i są niestabilne.

7. **ROIC vs WACC i Ohlson O-Score: „darmowe dane, ale realna robota".** Wzory publiczne, ale: WACC wymaga założeń (koszt kapitału własnego przez CAPM = beta + stopa wolna od ryzyka + premia; koszt długu) — łatwo o subtelny błąd, a wynik jest wrażliwy na założenia. Ohlson wymaga zebrania kompletu inputów bilansowych dla każdej spółki i okresu. Oba realne w MVP, ale traktuj jako 🟡, nie 🟢 — i przetestuj na kilku znanych spółkach, czy liczby się zgadzają z publicznymi.

8. **Mantra „przetestuj jeden wskaźnik vs S&P 500" jest sama w sobie metodycznie słaba.** Top-25 spółek z S&P 500 wg jednego wskaźnika → mała próba, wysokie ryzyko przeoptymalizowania, survivorship bias jeśli nie point-in-time. To dobry *sanity check* kierunku, **nie** walidacja. (Spójne z red flagiem z filmu 1 — podtrzymuję.)

9. **Marketing vs merytoryka w godz. 2.** Realnie przenośnej metodyki z drugiej godziny jest mało: definicja kanału (percentyle), kroczące estymaty 12M vs FY, warunkowe filtry sektorowe, log-skala dla ceny. Reszta to UX Scrub (multichart, grupy, skróty, templatki) + logistyka loginów/haseł + „pobawcie się sami". Nie przepisuj featurów narzędzia jako wymagań swojej apki.

10. **Peer average: aproksymacja sektorowa to inny obiekt niż „similar companies" Scrub.** Scrub ma kuratorowaną listę podobnych firm per spółka; Ty na darmowych danych zrobisz średnią po sektorze/branży (GICS-podobne). To grubsze — np. „podobne do Apple" wg Scrub ≠ cały sektor IT. Akceptowalne w MVP, ale oznacz, że to przybliżenie.