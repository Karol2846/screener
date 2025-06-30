CREATE TABLE stock (
symbol                      VARCHAR(20) PRIMARY KEY NOT NULL,
seeking_alpha_tracker_id    BIGINT NOT NULL,
company_name                VARCHAR(255) NOT NULL,
sector                      VARCHAR(100) NOT NULL,
created_at                  DATE NOT NULL,
updated_at                  TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE price_history (
id                          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
symbol                      VARCHAR(20) NOT NULL REFERENCES stock (symbol) ON DELETE CASCADE,
current_price               DECIMAL(12,4) NOT NULL,
average_50_price            DECIMAL(12,4),
average_200_price           DECIMAL(12,4),
created_at                  DATE NOT NULL,

UNIQUE(symbol, created_at)
);

CREATE TABLE fundamental_data (
id                          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
symbol                      VARCHAR(20) NOT NULL REFERENCES stock (symbol) ON DELETE CASCADE,

market_cap                  BIGINT,
enterprise_value            BIGINT, -- (calculated: evEbitda * ebitda)

pe_ratio                    DECIMAL(8,2),
pb_ratio                    DECIMAL(8,2),
peg_ratio                   DECIMAL(8,2),
pe_forward                  DECIMAL(8,2),
ev_ebitda                DECIMAL(8,2),
ev_sales                 DECIMAL(8,2),

price_target_high       DECIMAL(12,4),
price_target_low        DECIMAL(12,4),
price_target_consensus  DECIMAL(12,4),
eps                     DECIMAL(8,4),

created_at          DATE NOT NULL,
UNIQUE(symbol, created_at)
);

CREATE TABLE analyst_recommendation (
id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
symbol              VARCHAR(20) NOT NULL REFERENCES stock (symbol) ON DELETE CASCADE,

strong_buy          INTEGER DEFAULT 0,
buy                 INTEGER DEFAULT 0,
hold                INTEGER DEFAULT 0,
sell                INTEGER DEFAULT 0,
strong_sell         INTEGER DEFAULT 0,
total         INTEGER DEFAULT 0,

created_at          DATE NOT NULL,
UNIQUE(symbol, created_at)
);

-- indexes
CREATE INDEX idx_price_history_symbol_date ON price_history(symbol, created_at DESC);
CREATE INDEX idx_price_history_date ON price_history(created_at DESC);

CREATE INDEX idx_fundamental_data_symbol_date ON fundamental_data(symbol, created_at DESC);
CREATE INDEX idx_fundamental_data_date ON fundamental_data(created_at DESC);

CREATE INDEX idx_analyst_recommendation_symbol_date ON analyst_recommendation(symbol, created_at DESC);
