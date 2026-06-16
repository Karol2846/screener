# Dostępność danych na darmowych API — weryfikacja (addendum do konsolidacji)

> Odpowiedź na pytanie: „czy warstwa forward (🔴/⚫), na której stoi cała przewaga strategii, jest osiągalna za darmo?". Sprawdzone realnie (web + introspekcja yfinance), nie z pamięci. Gdzie coś jest z wiedzy ogólnej / niezweryfikowane na żywych danych — oznaczam wprost.
>
> **Data weryfikacji: 2026-06-05.** Tiery i limity API zmieniają się — przyjmij to jako stan na teraz, nie stałą.

---

## Werdykt w 3 zdaniach (zmienia ocenę konsolidacji)

1. **Warstwa forward JEST osiągalna za darmo — ale praktycznie tylko przez `yfinance` (Yahoo).** Wystawia on estymaty EPS/revenue, price target (→ Upside), **EPS LTG** (wiersz `+5y` w growth_estimates) i nawet snapshoty rewizji EPS (`eps_trend`, ~90 dni wstecz). Wszystkie pozostałe darmowe API (Finnhub, FMP, Alpha Vantage) **bramkują dokładnie te dane** za paywallem lub limitem 25 req/dzień.
2. **Trzeba rozdzielić dwa tryby projektu — i tu leży jedyna twarda granica.** Pełną (niemal) strategię da się uruchomić **NA ŻYWO** za darmo. Ale **BACKTESTU warstwy forward na przeszłości nie da się zrobić za darmo** — historyczne point-in-time estymaty/PT/rewizje (⚫) to jedyna rzecz realnie niedostępna. Twój pomysł „budujmy historię sami" działa, ale **tylko od dziś w przód** (snapshoty), nie wstecz.
3. **Korekta liczby z konsolidacji: MVP to nie ~40/100 pkt, tylko ~85–90/100 pkt modelu — DLA TRYBU LIVE.** Z 12 reguł darmowo policzysz 11 (brakuje tylko forward CFO/share = 5 pkt). Dla trybu BACKTEST forward zostaje ~40/100, dokładnie jak pisała konsolidacja. Różnica live-vs-backtest to teraz najważniejsze rozróżnienie projektu.

---

## 1. Mapowanie: każdy sygnał 🔴/⚫ → konkretne darmowe źródło

Legenda dostępności po weryfikacji: ✅ darmowe i realne · ⚠️ darmowe z istotnym zastrzeżeniem · ❌ niedostępne za darmo.

| #  | Sygnał (z modelu f3)     | Stary tier | Darmowe źródło                                                                                                                 | Live? | Backtest hist.? |
|----|--------------------------|------------|--------------------------------------------------------------------------------------------------------------------------------|-------|-----------------|
| 1  | EPS forward 2Y           | 🔴         | `yf.Ticker.earnings_estimate` (`0y`,`+1y`) + `growth_estimates`                                                                | ✅    | ❌             |
| 2  | EPS Long-Term Growth     | 🔴         | `yf.Ticker.growth_estimates` wiersz **`+5y`**                                                                                  | ✅    | ❌             |
| 4  | Revenue forward 2Y       | 🔴         | `yf.Ticker.revenue_estimate`                                                                                                   | ✅    | ❌             |
| 6  | CFO/share forward        | 🔴         | **brak** (yfinance daje tylko EPS+revenue estimates)                                                                           | ❌    | ❌             |
| 11 | Upside (PT vs cena)      | 🔴         | `yf.Ticker.analyst_price_targets` {mean,median,low,high,current}                                                               | ✅    | ❌             |
| 5  | Revenue rewizje 60d      | ⚫         | self-snapshot `revenue_estimate` (od dziś); brak gotowej historii                                                              | ⚠️     | ❌             |
| 12 | Price Target rewizje 30d | ⚫         | self-snapshot `analyst_price_targets` (od dziś)                                                                                | ⚠️     | ❌             |
| —  | (bonus) EPS rewizje      | ⚫         | `yf.Ticker.eps_trend` (snapshoty `7/30/60/90daysAgo`) + `eps_revisions` (liczba up/down) — **dostępne OD RAZU, 90 dni wstecz** | ✅    | ❌             |
| —  | (bonus) konsensus rating | 🔴         | `yf.recommendations` **lub Finnhub `recommendation_trends` (free!)** — jedyny forward-sygnał z darmowym backupem               | ✅    | ⚠️¹             |

¹ Finnhub recommendation trends daje kilka ostatnich miesięcznych snapshotów — czyli minimalną historię rewizji ratingu „za darmo", ale płytką.

**Schematy kolumn yfinance — z wiedzy/dokumentacji, NIEzweryfikowane na żywym tickerze** (Yahoo jest zablokowane w moim sandboxie — potwierdziłem tylko, że *metody istnieją* w yfinance 1.4.1 przez introspekcję klasy). Zanim zakodujesz, odpal na 2–3 tickerach i sprawdź realne nazwy kolumn — yfinance bywa niestabilny między wersjami:
- `earnings_estimate` / `revenue_estimate`: index `['0q','+1q','0y','+1y']`, kolumny ~`avg, low, high, numberOfAnalysts, growth, yearAgo*`.
- `eps_trend`: index jw., kolumny `['current','7daysAgo','30daysAgo','60daysAgo','90daysAgo']` → **to jest gotowa rewizja**, ale tylko bieżący snapshot + 90 dni.
- `eps_revisions`: kolumny `['upLast7days','upLast30days','downLast7days','downLast30days']` (liczba analityków).
- `growth_estimates`: index zawiera `+5y` (= LTG) i `-5y`, plus porównanie do sektora/indeksu.
- `analyst_price_targets`: dict, nie DataFrame.

---

## 2. Drugi filar: głęboka historia fundamentów (problem 🟡 z kanałami wyceny) — ROZWIĄZANY dla US

Konsolidacja słusznie martwiła się, że kanały P/S, EV/CFO, P/CFO liczone na płytkiej darmowej historii fundamentów są niestabilne. **Dla spółek US to jest rozwiązane darmowo przez SEC EDGAR** (zweryfikowane):

- **`data.sec.gov/api/xbrl/companyfacts/CIK{...}.json`** — bez klucza, bez limitu dziennego, 10 req/s, wymaga tylko nagłówka `User-Agent`.
- Głębokość: **od 2009** (wprowadzenie XBRL), dla większości dużych filerów pełne 10-K/10-Q. Revenue, CFO, shares outstanding, total assets, debt, EPS itd.
- **Każdy fakt ma datę złożenia (`filed`) i `accn`** → możesz robić **poprawne point-in-time fundamenty** (`filed ≤ asOfDate`) ORAZ obsłużyć restatementy (oryginał i wersja po korekcie są oba w danych, z datami). To jest dokładnie ten anti-look-ahead fundament, który konsolidacja nazwała „sercem projektu" — i jest **za darmo dla US**.

Zastrzeżenia (wszystkie 🟡 = robota, nie blocker):
- **US-only.** Foreign private issuers składają 20-F/40-F (roczne, mniej granularne, IFRS). Globalne 40k z czystymi, głębokimi, point-in-time fundamentami na darmowych danych **nie istnieje** — realny darmowy investable universe to US large/mid + ewentualnie kilka rynków rozwiniętych przez yfinance (płytsze).
- **Concept fragmentation:** spółki używają różnych tagów XBRL na to samo (`Revenues` vs `SalesRevenueNet` vs własne rozszerzenia). Mapowanie tagów = realna robota porządkowa.
- EDGAR to filings, nie ceny — ceny osobno (stooq/yfinance).

---

## 3. Stan pozostałych darmowych API (dlaczego yfinance nie ma realnej darmowej alternatywy na forward)

| API | Free limit | Forward (estymaty/PT/LTG) na free? | Werdykt jako backbone |
|---|---|---|---|
| **Finnhub** | 60 req/min (hojne) | **Nie.** `recommendation_trends` = free; `price-target` = **„Premium required"** (zweryfikowane w docs); EPS/revenue estimates = premium | Świetny do cen/rec-trend/alt-data; forward zabramkowany |
| **FMP** | 250 req/dzień, ~5 lat, US EOD | `analyst-estimates`/`price-target` istnieją, ale efektywnie płatne; 250/dzień + 5 lat i tak dyskwalifikują do screeningu/backtestu. Fundamenty FMP i tak są z EDGAR | Nie jako backbone |
| **Alpha Vantage** | **25 req/dzień** (ścięte 500→100→25) | `OVERVIEW` ma `AnalystTargetPrice` + bucket ratingów; jest `EARNINGS_ESTIMATES`. Ale 25/dzień = nierealne | Co najwyżej punktowy cross-check |
| **yfinance / Yahoo** | brak oficjalnego; Yahoo throttluje | **Tak — pełen zestaw** (jedyny) | **Jedyny darmowy backbone forward**, ale: nieoficjalne, scrapowane, łamie się między wersjami, szara strefa ToS, pokrycie analityczne poza US cienkie |

**Konsekwencja, którą trzeba zaakceptować:** cała darmowa warstwa forward wisi na jednym, niestabilnym źródle bez równorzędnego fallbacku. Jedyny forward-sygnał z darmowym backupem to konsensus ratingu (Finnhub). To realne ryzyko architektoniczne — patrz §5.

---

## 4. Twój pomysł „budujmy historię sami" — gdzie działa, a gdzie nie

To dobry instynkt i **częściowo ratuje tier ⚫**, ale ma twardą asymetrię czasową:

- ✅ **Rewizje NA ŻYWO / monitoring:** snapshotuj `earnings_estimate` + `analyst_price_targets` codziennie do własnej bazy → po kilku tygodniach masz własny szereg rewizji (revenue 60d, PT 30d). Dla EPS masz nawet headstart: `eps_trend` daje 90 dni wstecz od pierwszego dnia. **Moduł 5 (monitoring rewizji) staje się realny** — nie „niedostępny", jak zakładała konsolidacja.
- ❌ **Backtest warstwy forward na przeszłości:** self-snapshot **nie cofa czasu**. Nie zrekonstruujesz, jaki był konsensus EPS na 2015-06-30. Żeby zbacktestować Upside/rewizje/EPS-fwd na 10 latach, musisz albo (a) zbierać snapshoty miesiącami/latami zanim dostaniesz sensowny zakres, albo (b) zapłacić za point-in-time historię (i nawet wtedy ⚫ jest drogie/rzadkie).

Mapa drogowa, która z tego wynika: **zacznij snapshotować forward od dnia 1**, równolegle do budowy MVP. Po ~6–12 mies. masz własny, czysty (bo PIT z natury) dataset rewizji do pierwszych forward-backtestów — coś, czego Scrub przy całym swoim aparacie nie pokazał uczciwie.

---

## 5. Residual hard problem, którego ŻADNE z tych źródeł nie rozwiązuje: survivorship

Darmowe ceny + darmowe fundamenty (EDGAR) **nie dają czystego, survivorship-free uniwersum** — a to dokładnie ta rzecz, na której wykłada się headline'owy backtest Scrub (24,54%). Konkretnie brakuje:

- **Point-in-time membership / investable universe per data.** Co było w S&P 500 (albo „spełniało skaner") na 2013-04-01? Darmowe listy historycznego składu S&P 500 istnieją (datasety na GitHub, logi zmian z Wikipedii), ale są niekompletne i wymagają czyszczenia.
- **Spółki martwe/delistowane/przejęte ze stratą.** Dobra wiadomość: **EDGAR trzyma filingi zbankrutowanych** → ich fundamenty zostają w danych (w przeciwieństwie do Yahoo, które delistowane dropuje). Zła: i tak potrzebujesz ceny delistingowej / zwrotu końcowego, a yfinance dla martwych tickerów zwykle nie oddaje pełnego szeregu.

Czyli: darmowe dane dają Ci fundamenty i ceny żywych spółek; **nie wręczają gotowego uniwersum bez biasu**. To jest realna, najtrudniejsza robota backtestu — i jednocześnie (jak pisała konsolidacja) Twoja faktyczna przewaga, jeśli zrobisz ją porządnie. Survivorship nie znika dlatego, że fundamenty są darmowe.

---

## 6. Rekomendowany stack źródeł (multi-source, bo na to się zgodziłeś)

| Warstwa | Źródło | Tier | Rola |
|---|---|---|---|
| Ceny (głębokie, US+global) | **stooq** (lub yfinance) | 🟢 | OHLC do kanałów, zwrotów, position sizingu |
| Fundamenty US (głębokie, PIT) | **SEC EDGAR companyfacts** | 🟢 | mianowniki kanałów wyceny, stabilność, marże, historia 10+ lat z datami filed → anti-look-ahead |
| Forward (estymaty/PT/LTG/rewizje) | **yfinance** | 🔴/⚫ darmowo, ale kruche | warstwa forward LIVE; źródło snapshotów do własnej historii rewizji |
| Konsensus rating (backup) | **Finnhub `recommendation_trends`** | 🟢 | jedyny forward-sygnał z darmowym, stabilniejszym backupem + minimalna historia |
| Mapowanie ticker↔CIK | **SEC `company_tickers.json`** | 🟢 | spinanie EDGAR (CIK) z Yahoo/stooq (ticker) |
| Historyczny skład indeksu | datasety GitHub/Wiki | 🟡 | uniwersum point-in-time do backtestu (niekompletne — czyść) |

Komplikacje stitchingu (do zaplanowania, nie blokery): mapowanie CIK↔ticker↔symbol-stooq, waluty (EDGAR w USD, yfinance lokalnie — wymuś USD), concept fragmentation w XBRL, reconcyliacja kalendarzy fiskalnych.

---

## 7. Zaktualizowany bilans punktowy (vs konsolidacja §1)

| | Konsolidacja (zakładała forward = paywall) | Po weryfikacji — **tryb LIVE** | Po weryfikacji — **tryb BACKTEST hist.** |
|---|---|---|---|
| Growth (50) | 10 | **45** (brak tylko CFO-fwd 5) | 10 |
| Value (50) | 30 | **50** | 30 |
| **Total (100)** | **~40** | **~85–90** | **~40** |

> **Sedno korekty:** konsolidacja policzyła ~40/100, bo traktowała forward jako jednolicie płatny. To prawda **dla backtestu historycznego**. Dla **żywego narzędzia** yfinance przesuwa prawie całą warstwę forward do zasięgu — z 12 reguł darmowo liczysz 11. To nie jest już „generyczny value+quality screen" — to (prawie) pełna strategia z warsztatów, działająca na bieżąco, za darmo. Cena: zależność od kruchego źródła + brak możliwości uczciwego zbacktestowania forward-reguł wstecz.

---

## 8. Odpowiedź wprost na „czy projekt jest wykonalny na darmowych danych"

**Tak — bardziej niż sugerowała konsolidacja — z jedną twardą granicą:**

- ✅ **Żywy screener + scoring (~85–90/100) + monitoring/alerty (w tym rewizje)**: wykonalne za darmo dziś.
- ✅ **Uczciwy, point-in-time backtest warstwy value+quality (US)**: wykonalne za darmo (EDGAR + ceny). To metodycznie najczystsza i najwartościowsza część — ta sama, którą konsolidacja wskazała jako serce projektu.
- ❌ **Backtest warstwy forward (Upside, EPS-fwd, rewizje) na przeszłości**: NIE za darmo. Albo zbieraj snapshoty od dziś (i backtestuj forward dopiero za pół roku+), albo płać za PIT historię.
- ⚠️ **Globalne 40k z czystymi fundamentami**: nie na darmowych danych — realny darmowy zasięg to US + trochę rynków rozwiniętych.

Innymi słowy: **nie utknąłeś na braku danych.** Możesz zbudować i uruchomić niemal całą rzecz. Jedyne, czego darmowe dane Ci nie dadzą, to *udowodnić wstecz, że warstwa forward działa* — a akurat ta warstwa (Upside, price targety) ma najsłabsze wsparcie empiryczne w literaturze *(z wiedzy ogólnej, do weryfikacji)*, więc to mniejsza strata, niż się wydaje.

---

## 9. Następny krok (proponowany)

Najwięcej ryzyka technicznego siedzi w jednym miejscu: **czy yfinance realnie oddaje forward dla Twojego uniwersum, z jaką kompletnością i jak często się sypie.** Zanim zaprojektujesz `MetricProvider`, proponuję 1-dniowy spike:
1. Weź ~30 tickerów (15 US large, 5 US small, 10 spoza US).
2. Odpal `earnings_estimate`, `revenue_estimate`, `analyst_price_targets`, `growth_estimates` (`+5y`), `eps_trend` — zmierz **coverage** (ile tickerów ma nie-null) i zweryfikuj realne nazwy kolumn.
3. Równolegle: jeden `companyfacts` z EDGAR dla 3 z nich — sprawdź głębokość revenue/CFO i daty `filed`.

To da twarde dane do decyzji „free-only MVP vs free-live + paid-backtest-później", zamiast projektować w ciemno. Mogę rozpisać ten spike jako konkretny skrypt + interfejs `MetricProvider`, za którym schowana jest decyzja free-vs-paid — daj znać.

---

## 10. Szerszy krajobraz darmowych API (dodane po rozszerzonym researchu)

> Pytanie kontrolne: „może te dane istnieją w szerszym kontekście — na marketplace'ach, w mniej znanych API?". Sprawdzone. Odpowiedź dwuczęściowa, bo to dwa różne problemy.

### 10a. Przepustowość — TAK, da się znaleźć dużo lepsze darmowe quoty niż 25/dzień

Twój instynkt z RapidAPI jest słuszny: limity bezpośrednie ≠ limity na marketplace'ach ani w innych API. Realnie użyteczne darmowe quoty (stan 2026-06):

| Źródło | Darmowy limit | Co realnie dostajesz za darmo |
|---|---|---|
| **SEC EDGAR** | 10 req/s, **brak limitu dziennego** | pełne fundamenty US, point-in-time, restatementy |
| **stooq** | bulk (cały rynek 1 plikiem) | ceny EOD, głębokie, globalne |
| **Finnhub** | **60 req/min** | ceny, fundamenty bazowe, **recommendation trends**, alt-data |
| **Twelve Data** | **800 kredytów/dzień**, 8/min | ceny + 30 lat EOD + bazowe fundamenty (analiza forward — patrz 10b) |
| **Alpha Vantage (RapidAPI)** | **500/dzień**, 5/min, 10 GB/mc | fundamenty (OVERVIEW, financials, **EARNINGS z estymatą+surprise**, EARNINGS_ESTIMATES, **status delistingu**) |
| Alpha Vantage (direct) | 25/dzień | te same endpointy, mocno zdławione |

**Wniosek dla cron→baza:** problem przepustowości jest **rozwiązany z naddatkiem**. Nie potrzebujesz nawet AV — EDGAR (bez limitu) + Finnhub (60/min) + stooq (bulk) cyklują uniwersum US w kadencji dziennej bez wysiłku. AV-500 z RapidAPI dorzuca przyzwoity **drugi, niezależny** strumień fundamentów + **częściowo forward EPS** (EARNINGS_ESTIMATES + surprise są w darmowej kategorii AV) — przydatne jako cross-check, żeby nie wisieć na jednym źródle dla sygnału EPS-forward.

> ⚠️ Do zweryfikowania po Twojej stronie na listingu RapidAPI: czy darmowy AV-500 nie blokuje osobno „premium functions" AV i jak endpointy fundamentów liczą się do quoty 10 GB/mc (dla JSON-a fundamentów to praktycznie niewiążące, ale potwierdź jednym callem OVERVIEW na nie-demo tickerze).

### 10b. Istnienie danych forward — wzorzec się NIE łamie: structured API bramkują warstwę analityczną

To jest sedno, którego marketplace'y nie zmieniają. Każde *profesjonalne, ustrukturyzowane* API bramkuje dokładnie warstwę estymat/price-target/rewizji — różni się tylko sposób bramki:

| API | Warstwa forward (estymaty/PT/rewizje/LTG) na free? | Bramka |
|---|---|---|
| Finnhub | ❌ | `price-target` = „Premium required"; EPS/rev estimates = premium |
| FMP | ❌ | `analyst-estimates`/`price-target` efektywnie płatne + 250/dzień + 5 lat |
| Alpha Vantage | ⚠️ częściowo | EARNINGS (actual+estimate+surprise) i EARNINGS_ESTIMATES są free; **price target i LTG — brak**; dławienie |
| **Twelve Data** | ❌ (mimo marketingu) | endpointy `price_target`/`eps_trend`/`eps_revisions`/`earnings_estimate` **istnieją i są udokumentowane**, ale „analysis datasets" to feature **Ultra ($1099/mc)**; darmowy Basic testuje je tylko na **symbolach demo**. Hasło „all datasets in all plans" jest mylące — fine print bramkuje analizę planem |
| **Yahoo (yfinance / rehosty RapidAPI)** | ✅ | jedyna w pełni darmowa ścieżka do pełnej warstwy forward — bo to consumer-facing konsensus, nie licencjonowany produkt |

**Meta-lekcja, którą warto zapamiętać na cały projekt:** *quota ≠ dostępność danych*. „Znalazłem 500/dzień zamiast 25" rozwiązuje przepustowość, nie istnienie warstwy forward. Warstwa forward za darmo to wciąż **wyłącznie Yahoo** — niezależnie od tego, ile marketplace'ów przejrzysz. Profesjonalne API mają tę warstwę, ale jej nie oddają za darmo, bo to ich produkt premium.

### 10c. Realna poprawa, którą daje ten research: stabilniejszy dostęp do Yahoo

Skoro forward = Yahoo-only za darmo, a moim głównym zastrzeżeniem była kruchość surowego `yfinance` (scraping, łamie się między wersjami) — **rehosty Yahoo na RapidAPI** (np. Mboum / „Real-Time Finance Data" / „YH Finance" / „Seeking Alpha" unofficial) to te **same dane analityczne** (estymaty, price target, recommendation trend), ale jako utrzymywane produkty API z darmowymi tierami → mniej podatne na nagłe pęknięcie niż własny scraping. To realny upgrade odporności dla warstwy forward, bez zmiany tego, jakie dane dostajesz (to nadal Yahoo pod spodem → to samo pokrycie, ten sam brak historii PIT).

> ⚠️ Dwa zastrzeżenia: (1) rehosty to strona trzecia — ToS, niepewna trwałość, jakość bywa zmienna; nie traktuj jako gwarancji. (2) Yahoo zaczął bramkować *część* danych analitycznych (ratingi Morningstar, fair value) za płatnym planem Silver na własnym portalu — to inne dane niż quoteSummary, które ciągnie yfinance, ale to sygnał, że darmowy dostęp do warstwy analitycznej Yahoo może się z czasem zawężać. Nie buduj fundamentu strategii na założeniu, że ten kanał jest wieczny.

### 10d. Ściana ⚫ (historia point-in-time) — bez zmian, z trzema drobnymi darmowymi wyjątkami

Żadne z przejrzanych źródeł nie ma **historycznych snapshotów konsensusu** za darmo. Self-snapshot od dziś w przód zostaje jedyną darmową drogą. Trzy drobne darmowe wyjątki dające *trochę* gotowej historii od ręki (wszystkie z Yahoo/yfinance):
- `eps_trend` — snapshoty estymaty EPS sprzed 7/30/60/90 dni (gotowe ~90 dni rewizji EPS).
- `upgrades_downgrades` — historia akcji upgrade/downgrade analityków z datami i firmą ratingującą (zdarzeniowa historia rewizji ratingu).
- `recommendations` — kubełki `0m/-1m/-2m/-3m` (kilkumiesięczna historia trendu rekomendacji).

To nie wystarczy na 10-letni backtest forward, ale daje punkt startowy do walidacji świeżo zbieranych snapshotów i do pierwszych, krótkich testów sygnału rewizji.

### Werdykt sekcji 10

Szerszy research **niczego nie odblokował na froncie danych forward** (Yahoo pozostaje jedynym darmowym źródłem), ale **realnie poprawił dwie rzeczy**: (a) przepustowość ingestii przestała być jakimkolwiek ograniczeniem (EDGAR bez limitu + Finnhub 60/min + stooq bulk + AV-500/TD-800 jako wsparcie), i (b) masz teraz stabilniejszą drogę do warstwy Yahoo (rehosty) niż surowy scraping. Rekomendowany stack z §6 zostaje, z dopiskiem: rozważ rehost Yahoo zamiast/obok `yfinance` dla odporności, i trzymaj AV-500 jako drugi strumień fundamentów + częściowego forward EPS (cross-check).