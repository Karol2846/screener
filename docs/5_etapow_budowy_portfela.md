# Film 1 — „5 etapów budowy portfela"

> Notatki + spec implementacyjny screenera. Pierwsze z 4 spotkań warsztatów inwestycyjnych.

## Kontekst materiału

- **Prowadzący:** Tomasz Trela (przedstawia się jako zarządzający funduszem hedgingowym New Horizon Management z Luksemburga + prywatny fundusz z USA). Pozostali wymienieni: Michał Tarkowski (twórca narzędzia), Paweł Kondrat (dane/research).
- **Co tak naprawdę sprzedają:** narzędzie SaaS o nazwie **Scrub** (`ghostscrub.com`) — profesjonalny screener + scoring + backtest + monitoring. Spotkanie jest lead-genem; uczestnicy dostają darmowy dostęp na czas warsztatów.
- **Charakter spotkania 1:** ~90% to diagnoza problemu i demo narzędzia. **Konkretna strategia, wagi i wskaźniki są celowo odłożone na spotkania 2–4.** To, co tu jest, traktuj jako szkielet, nie gotowy przepis.
- **Ważne zastrzeżenie samego prowadzącego:** większość liczb (wagi, progi) wpisuje *„uznaniowo"*, *„na wariata"*, a pokazana strategia to *„strategia testowa"*, nie realna. Headline'owe wyniki backtestu (22% CAGR, +760%) pochodzą z tego rzucanego na szybko zestawu — **nie traktuj ich jako walidacji metody.**

---

## TL;DR (wersja skrócona)

Cała filozofia w jednym zdaniu: **zamień subiektywną, ręczną analizę popularnych spółek na obiektywny, algorytmiczny scoring spółek z całego świata, oparty głównie na prognozach (forward), i waliduj reguły kroczącym backtestem.**

Pięć etapów = pięć modułów narzędzia:

1. **Skaner** — przefiltruj ~40 tys. spółek globalnie do ~2 tys. twardymi kryteriami (kapitalizacja, wzrost, zadłużenie).
2. **Wizualizacja** — szukaj wzorców/korelacji między wskaźnikiem a kursem (np. P/S w kanale, upside w kanale).
3. **Model scoringowy** — automatyczna, ważona ocena spółek w kategoriach (zyskowność, wycena, upside…), punktacja stopniowana, nie 0/1.
4. **Backtest** — testuj **strategię (reguły)**, nie konkretny portfel; z **point-in-time** składem indeksu (obrona przed survivorship bias).
5. **Monitoring + alerty** — pilnuj psujących się metryk (zwłaszcza spadkowych rewizji), alertuj kiedy sprzedać / kiedy spółka z watchlisty staje się tania.

Dwie najmocniejsze pojedyncze przewagi wg prelegenta: **EPS Long-Term Growth** i **Upside** (oba to dane forward). Optymalne parametry portfela: rebalancing **1–3 mies.**, **20–35 spółek**, wagi nie-równe.

---

## Notatki szczegółowe

### Diagnoza: dlaczego 90%+ inwestorów nie pokonuje benchmarku

Benchmark = S&P 500, ~10% średniorocznie. Cel: pobić go. Pięć przyczyn porażki:

1. **Wszyscy trzymają te same popularne spółki.** Nie da się pobić większości, mając w portfelu to samo, co większość (Apple, MS, Amazon, Nvidia…). → Rozwiązanie: skanować globalnie, szukać spółek mało znanych.
2. **Błędy poznawcze / decyzje subiektywne.** Spółkom „lubianym/znanym" wybaczamy rosnące zadłużenie, spadające rewizje itd.; nieznanym — nie. → Rozwiązanie: obiektywny scoring algorytmiczny, identyczny dla każdej spółki.
3. **Nie wiemy, które wskaźniki (i kombinacje) realnie korelują z przyszłym kursem.** Tysiące możliwych kombinacji. → Rozwiązanie: backtestować pojedyncze wskaźniki i ich kombinacje.
4. **Wadliwy backtest.** Większość narzędzi bierze *dzisiejsze* spółki i cofa je w czasie (survivorship bias) oraz porównuje do *dzisiejszego* składu indeksu. → Rozwiązanie: backtest kroczący ze składem indeksu point-in-time.
5. **Monitoring + inercja.** Trzymanie ręki na pulsie staje się full-time jobem; do tego psychologiczna niechęć do cięcia stratnych pozycji. → Rozwiązanie: automatyczny monitoring + alerty (docelowo też auto-sprzedaż).

### Logika modelu scoringowego (na przykładzie prowadzącego)

- Spółka musi najpierw przejść **twarde warunki „Must-Have"**, żeby w ogóle trafiła do oceny.
- Potem dostaje punkty w kategoriach. Kluczowa zasada: **przyszłość waży więcej niż przeszłość** (forward revenue growth ma ~3× wagę historii).
- Punktacja jest **stopniowana/logarytmiczna** (progi z częściowymi punktami), nie zero-jedynkowa.
- **Rewizje** prognoz (kierunek zmian estymat w czasie) są traktowane jako osobny, mocno ważony sygnał — bo pojedynczy punkt prognozy szybko się dezaktualizuje (przykład: embargo na półprzewodniki → analitycy z dnia na dzień tną prognozy → score leci z 10 na 0).
- Wycena oceniana przez **kanał** wskaźnika (np. P/S z 10 lat, ±1–2 odchylenia std): odczyt przy dolnej granicy = tanio = max pkt, przy górnej = drogo = 0 pkt. Kanał P/S rysowany **ukośnie** (P/S historycznie dryfuje w górę), kanał upside — **horyzontalnie**.

### Wskaźniki z konkretnymi wynikami backtestów

Wszystkie testy: uniwersum S&P 500, top 25 spółek wg danego wskaźnika, rebalancing miesięczny.

| Wskaźnik                  | Definicja                                            | Wynik backtestu                                     |
|---------------------------|------------------------------------------------------|-----------------------------------------------------|
| **EPS Long-Term Growth**  | oczekiwany wzrost zysku netto w perspektywie 3–5 lat | ~13% CAGR (vs ~10% S&P)                             |
| **Upside**                | (cel analityków na 12 mies. − cena dziś) / cena dziś | ~23% CAGR (>2× benchmark)                           |
| **Kombinacja kaskadowa**  | np. top 100 wg EPS LTG → z tego top 25 wg Upside     | sugerowane jako jeszcze lepsze (bez podanej liczby) |

> Uwaga: pojedynczy wskaźnik to nie strategia — prowadzący używa tego tylko do ilustracji, że dany czynnik „ma sens".

### Co prowadzący świadomie pominął (do uzupełnienia z filmów 2–4)

- Szczegółowe wyjaśnienie **dlaczego** poszczególne wskaźniki działają.
- Pełna lista i wagi wskaźników w realnej strategii.
- Mechanika **doboru wag w portfelu** i zarządzania ryzykiem (wspomina HRP, „za to dawali Nobla" — film w czwartek).
- Konkretne strategie gotowe (dywidendowe, growth, large-cap) i jak się różnią parametrami.

---

## SPEC DO IMPLEMENTACJI

### ⚠️ Krytyczne ograniczenie: dane forward-looking

To jest punkt decydujący o tym, ile z tej strategii w ogóle da się zbudować.

Strategia opiera się **głównie na danych prognozowanych przez analityków**: forward EPS growth, price target (→ upside), forward revenue (FY+1, FY+2), oraz **rewizje** tych estymat w czasie. Prowadzący jawnie mówi, że płacą za to FactSet + Financial Modeling Prep — rzędu setek tys. zł rocznie.

Konsekwencje dla Twojej implementacji:

- **Realne do zrobienia na tanich/darmowych danych:** kapitalizacja, filtr zadłużenia, historyczny wzrost przychodów/zysku (trailing), kanał P/S z danych historycznych, cash flow operacyjny historyczny.
- **Trudne/niemożliwe bez płatnych estymat:** forward EPS growth, upside (potrzebny konsensus price targetów), forward revenue (FY+1/FY+2), rewizje estymat (60-dniowe). FMP ma estymaty na płatnych tierach (ograniczone pokrycie i historia), yfinance daje szczątkowe i mało wiarygodne. FactSet to enterprise — poza zasięgiem.
- **Wniosek praktyczny:** zanim cokolwiek kodujesz, zdecyduj się na dostawcę danych i sprawdź, czy ma point-in-time estymaty i rewizje. To zdefiniuje, którą część modelu w ogóle implementujesz. **Najmocniejsze sygnały strategii (EPS LTG, Upside) są jednocześnie najdroższe w danych.**

### Filtry wstępne (skaner / „Must-Have")

Twarde, binarne filtry odrzucające spółki przed scoringiem:

- Kapitalizacja `>=` próg (prelegent: **5 mld USD**, „normalnie 10 mld")
- Prognozowany wzrost zysku (EPS) `>=` **10%** średniorocznie (forward, 2 lata)
- Prognozowany wzrost przychodów `>=` **5%** średniorocznie
- Zadłużenie `<=` **40%** (prawdopodobnie debt/assets — do doprecyzowania)
- Min. **3 lata od IPO** (żeby był materiał do kanałów/backtestu)
- Opcjonalnie (strategie dywidendowe): dywidenda `>=` **2%**
- Odrzucać spółki ze zbyt dużą liczbą brakujących danych

### Model scoringowy — kategorie i punktacja

Punktacja stopniowana (progi z interpolacją między min a max), nie 0/1.

**Kategoria: Zyskowność (max ~60 pkt)** — kilka wskaźników, m.in.:

| Wskaźnik                            | Reguła punktacji                                                                              | Max pkt     |
|-------------------------------------|-----------------------------------------------------------------------------------------------|-------------|
| Historia przychodów (5 lat)         | wzrost 25%/5lat ≈ świetnie; 15%/5lat (= 3%/rok) ≈ słabo                                       | niska waga  |
| Forward revenue (1 rok)             | TTM revenue vs estimate next-12M: `>=15%` wyżej → max; `<=5%` → 1 (lub 0); pomiędzy → liniowo | 10          |
| Rewizje prognoz przychodów (60 dni) | średnia rewizji dla FY bieżącego, FY+1, FY+2: pozytywne → max; spadek `>= 7%` → 0             | wysoka waga |
| Cash flow operacyjny                | rośnie / nie rośnie                                                                           | —           |

**Kategoria: Wycena (Value, max 20 pkt)**

| Wskaźnik       | Reguła                                                                                       | Max pkt       |
|----------------|----------------------------------------------------------------------------------------------|---------------|
| Price-to-Sales | kanał 10-letni, ±1–2 σ, **ukośny**; przy dolnej granicy → 20; środek → 10; górna (drogo) → 0 | 20            |
| Upside         | kanał **horyzontalny**; wyżej = lepiej                                                       | osobny sygnał |

> Realnych modeli „powinno być więcej parametrów" — to tylko szkielet pokazany na demo.

### Parametry budowy portfela (do tuningu backtestem)

- **Rebalancing:** optymalnie **1–3 miesiące, nigdy > 3** (do przetestowania na własnej strategii).
- **Liczba spółek:** **20–35** (czasem do 40). Nie 1 (za duże ryzyko), nie 500 (= benchmark).
- **Wagi:** equal weight „nigdy nie najlepszy". Lepiej: ważenie ryzykiem (**Hierarchical Risk Parity**) lub proporcjonalnie do metryki (np. im wyższy upside, tym większa waga).
- Parametry portfela są **zależne od strategii** — inny optimum dla dywidendowych, inny dla growth, inny dla large-cap.

### Metodologia backtestu (wymagania)

To najbardziej merytoryczna część filmu — warto zaimplementować poprawnie:

- **Testuj STRATEGIĘ (reguły), nie konkretny portfel.** System sam dobiera spółki spełniające reguły w danym momencie historycznym i podmienia je przy każdym rebalansowaniu.
- **Point-in-time membership indeksu:** przy każdej dacie rebalansowania sprawdzaj, czy spółka *wtedy* należała do indeksu (np. S&P 500). Bez tego masz survivorship bias i wynik „na śmietnik".
- **Obsłuż corporate actions:** zmiany tickerów, fuzje (3 spółki → 1), delistingi, brak danych wstecz.
- **Restatements:** dane historyczne (przychody/zyski/zadłużenie) bywają nadpisywane wstecz po audycie — uważaj na look-ahead.
- **Podatki/prowizje:** w narzędziu NIE uwzględnione. Argument prelegenta: prowizje u brokera typu IBKR są pomijalne (~kilkadziesiąt centów), a podatek i tak płacisz niezależnie od strategii. Dla Twojej apki: rozważ opcjonalny model kosztów + opcję optymalizacji podatkowej przy rebalansowaniu.

### Źródła danych (wymienione)

- **Financial Modeling Prep** — dane fundamentalne.
- **FactSet** — prognozy/estymaty (uznane przez prelegenta za najlepsze, lepsze niż S&P Global / Refinitiv). Enterprise, bardzo drogie.

### Reguły alertów / monitoringu

- **Negatywne rewizje** estymat → alert „zwróć uwagę / rozważ sprzedaż".
- **Watchlist:** spółka fundamentalnie dobra, ale dziś droga → alert, gdy stanie się atrakcyjnie wyceniona (kanał wyceny przy dole).
- **Auto-sprzedaż (przykład reguły):** gdy Upside dotyka dolnej granicy swojego kanału → sygnał sprzedaży.
- Monitoring na dwóch poziomach: pojedyncza spółka **oraz** cały portfel/watchlista.

---

## Moje uwagi / red flags (do świadomej implementacji)

1. **Większość progów jest arbitralna** — sam autor to przyznaje. Nie kopiuj liczb jako „prawd"; to parametry do kalibracji backtestem na Twoich danych.
2. **Headline'owe wyniki (22% / 760%) są bezwartościowe jako dowód** — pochodzą z przyznanej „strategii testowej" złożonej na wariata. Nie cytuj ich jako walidacji.
3. **Survivorship bias / point-in-time membership** — to jedyny w pełni solidny, wart przeniesienia 1:1 punkt metodologiczny z tego filmu. Jeśli zrobisz tylko tyle dobrze, już będziesz nad większością darmowych narzędzi.
4. **Wąskie gardło to dane, nie kod.** Logika scoringu jest prosta do napisania. Cała wartość (i koszt) siedzi w danych forward + rewizjach + point-in-time. Zdecyduj o tym najpierw.
5. **„Pure single-indicator" backtesty mylą** — wysokie CAGR z jednego wskaźnika na całym S&P 500 to nie strategia i łatwo o przeoptymalizowanie; traktuj jako kierunkowskaz, nie target.