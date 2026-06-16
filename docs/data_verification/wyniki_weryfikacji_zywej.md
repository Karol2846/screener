# Wyniki weryfikacji na żywo — yfapi.net + AlphaVantage (RapidAPI)

> Data testu: 2026-06-16. Testowano **dokładnie te endpointy/klucze, które są skonfigurowane w `application.yaml`** (`yhfinance.api.base-url=https://yfapi.net`, `alphavantage.api.base-url=https://alpha-vantage.p.rapidapi.com/`) — nie ogólną bibliotekę `yfinance`. Surowe odpowiedzi: `docs/data_verification/raw/{yfapi,alphavantage}/{TICKER}_{endpoint}.json`.
>
> Uniwersum testowe (15 tickerów): US large (`AAPL,MSFT,GOOGL,AMZN,JNJ,NVDA`), US small/mid (`PLTR,SOFI,IONQ,CVNA`), spoza US (`TSM,SAP,ASML,NVO,BABA`).

## 1. yfapi.net (RapidAPI "YH Finance" / financeapi.net) — coverage

| Ticker | `earningsTrend` okresy | `+5y`/LTG na poziomie spółki | `financialData.targetMeanPrice` | `recommendationTrend` |
|---|---|---|---|---|
| AAPL | 0q,+1q,0y,+1y | ❌ brak | ✅ | ✅ |
| MSFT | 0q,+1q,0y,+1y | ❌ brak | ✅ | ✅ |
| GOOGL | 0q,+1q,0y,+1y | ❌ brak | ✅ | ✅ |
| AMZN | 0q,+1q,0y,+1y | ❌ brak | ✅ | ✅ |
| JNJ | 0q,+1q,0y,+1y | ❌ brak | ✅ | ✅ |
| NVDA | 0q,+1q,0y,+1y | ❌ brak | ✅ | ✅ |
| PLTR | 0q,+1q,0y,+1y | ❌ brak | ✅ | ✅ |
| SOFI | 0q,+1q,0y,+1y | ❌ brak | ✅ | ✅ |
| IONQ | 0q,+1q,0y,+1y | ❌ brak | ✅ | ✅ |
| CVNA | 0q,+1q,0y,+1y | ❌ brak | ✅ | ✅ |
| TSM | 0q,+1q,0y,+1y | ❌ brak | ✅ | ✅ |
| SAP | 0q,+1q,0y,+1y | ❌ brak | ✅ | ✅ |
| ASML | 0q,+1q,0y,+1y | ❌ brak | ✅ | ✅ |
| NVO | 0q,+1q,0y,+1y | ❌ brak | ✅ | ⚠️ kwota wyczerpana w trakcie testu |
| BABA | ⚠️ kwota wyczerpana w trakcie testu (wszystkie 3 endpointy) | — | — | — |

**Wniosek:** `earningsTrend` (EPS/revenue forward 0q/+1q/0y/+1y, `epsTrend` 7/30/60/90d, `epsRevisions`) i `financialData` (price targety, Upside) działają **100% na przebadanej próbce, w tym dla spółek spoza US (TSM/SAP/ASML/NVO/BABA)**. To potwierdza większość §1 dokumentu `dostepnosc_danych.md`.

**Rozbieżność z dokumentem:** pole `+5y` (EPS Long-Term Growth, sygnał #2) **nie występuje na poziomie spółki w `earningsTrend` w żadnym z 14 sprawdzonych tickerów**. Sprawdzono też moduły `industryTrend` i `sectorTrend` — zwracają puste `estimates: []`. Jedyne miejsce, gdzie pojawił się token `"period":"LTG"`, to moduł `indexTrend` (poziom indeksu S&P 500, nieużyteczne jako sygnał per-spółka). **Wniosek: dla `yfapi.net` sygnał EPS LTG trzeba oznaczyć ❌, nie ✅** (dokument zakładał ✅ na bazie ogólnej wiedzy o `yfinance`/scrapingu strony Yahoo Analysis, która może iść inną ścieżką danych niż ten komercyjny wrapper).

**Nowa, nieoczekiwana obserwacja:** `yfapi.net` ma **twardy limit zapytań** (komunikat `"Limit Exceeded" — "Upgrade for bigger plan at financeapi.net/pricing"`), trafiony po ok. 90 zapytaniach w ciągu kilku minut testu, i **nie zresetował się po ~5 min oczekiwania** (sugeruje limit dobowy/miesięczny, nie per-minutowy). Dokument w §3 zakładał „brak oficjalnego [limitu]; Yahoo throttluje" — to dotyczyło `yfinance`/scrapingu, nie tego konkretnego płatnego-freemium produktu, który ma jawny, metered plan. **Trzeba zaplanować pod konkretny limit `yfapi.net`, nie pod „brak limitu".**

## 2. AlphaVantage przez RapidAPI (`EARNINGS_ESTIMATES`) — coverage

| Ticker | Status | Liczba pól `date` w odpowiedzi (proxy na liczbę okresów × replik) |
|---|---|---|
| AAPL | ✅ | 40 |
| MSFT | ✅ | 40 |
| GOOGL | ⚠️ **puste** (`estimates: []`) | 0 |
| AMZN | ⚠️ **puste** | 0 |
| JNJ | ⚠️ **puste** | 0 |
| NVDA | ⚠️ **puste** | 0 |
| PLTR | ✅ | 27 |
| SOFI | ✅ | 24 |
| IONQ | ⚠️ **puste** | 0 |
| CVNA | ✅ | 41 |
| TSM | ✅ | 41 |
| SAP | ✅ | 40 |
| ASML | ⚠️ **puste** | 0 |
| NVO | ✅ | 40 |
| BABA | ✅ | 41 |

**Wniosek (potwierdza pole `eps_estimate_average_{7,30,60,90}_days_ago` + `revenue_estimate_average`):** gdy dane są, payload faktycznie zawiera **gotową historię rewizji EPS (7/30/60/90 dni wstecz)** — to jest lepsze niż self-snapshot, dokładnie jak opisano w planie. **ALE coverage jest dziurawe: 9/15 (60%) — i to nie są peryferyjne tickery.** Puste wyniki dla `GOOGL`, `AMZN`, `JNJ`, `NVDA` (mega-capy US!) są zaskakujące i nieprzewidziane przez dokument. Nie udało się ustalić przyczyny (plan `BASIC` RapidAPI może cichо filtrować część tickerów bez błędu, zamiast zwrócić 403/paywall) — **to wymaga dalszego sprawdzenia przed oparciem się na tym jako głównym źródle rewizji revenue.**

**Limit:** plan `BASIC` (RapidAPI) ma rate-limit **~5 zapytań/minutę** (komunikat `"rate limit per minute for your plan, BASIC"` / `"Burst pattern detected... no more than X requests per second"`), potwierdzający dokument (§3: „5/min"). Dobowy limit `500/dzień` (z nagłówków `X-RateLimit-Requests-Limit: 500`) też się zgadza z dokumentem.

## 3. Zaktualizowana tabela sygnałów (do wstawienia w `dostepnosc_danych.md` §1)

| Sygnał | Stary werdykt (dok.) | Werdykt po teście **konkretnego skonfigurowanego API** |
|---|---|---|
| EPS/Revenue forward (0q/+1q/0y/+1y) | ✅ | ✅ potwierdzone, 14/15 tickerów, w tym non-US |
| EPS Long-Term Growth (+5y) | ✅ | ❌ **nie znaleziono na poziomie spółki w `yfapi.net`** |
| Upside (price target) | ✅ | ✅ potwierdzone, 14/15 |
| EPS rewizje (`eps_trend`/`eps_revisions`) | ✅ | ✅ potwierdzone, wbudowane w `earningsTrend` |
| Revenue rewizje 60d | ⚠️ self-snapshot only | ⚠️ **AV ma gotowe pole, ale tylko dla 60% przebadanych tickerów** — nie traktować jako pewne |
| Konsensus rating | ✅ | ✅ potwierdzone (`recommendationTrend` + `upgradeDowngradeHistory`) |

## 4. Nieprzebadane / do dociągnięcia

- `BABA` (3 endpointy yfapi.net) i `NVO recommendationTrend` — nie udało się dociągnąć z powodu wyczerpanej kwoty `yfapi.net` w trakcie sesji testowej. Do powtórzenia po resecie kwoty.
- Przyczyna pustych wyników AV dla `GOOGL/AMZN/JNJ/NVDA/IONQ/ASML` — nieustalona; możliwe że symbol wymaga innego formatu, możliwe że to faktyczna dziura danych na planie `BASIC`.
- Nie sprawdzono jeszcze AV `OVERVIEW` (price target + rating bucket) jako alternatywnego źródła Upside — dokument wspominał to jako opcję cross-check.
