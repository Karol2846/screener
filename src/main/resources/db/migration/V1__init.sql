CREATE TABLE stocks (
                        symbol VARCHAR(20) PRIMARY KEY,
                        company_name VARCHAR(255),
                        sector VARCHAR(100),
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE price_history (
id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
symbol              VARCHAR(20)             NOT NULL REFERENCES stocks(symbol) ON DELETE CASCADE,
date                DATE            NOT NULL,
current_price       DECIMAL(12,4),
average_50_price    DECIMAL(12,4),
average_200_price   DECIMAL(12,4),
created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

UNIQUE(symbol, date)
);

CREATE TABLE fundamental_data (
        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
            symbol VARCHAR(20) NOT NULL REFERENCES stocks(symbol) ON DELETE CASCADE,
            data_date DATE NOT NULL, -- kiedy dane zostały pobrane

            market_cap BIGINT,
            ebitda BIGINT,
            revenue BIGINT,
            ev BIGINT, -- enterprise value (calculated: evToEbitda * ebitda)

            pe_ratio DECIMAL(8,2),
            peg_ratio DECIMAL(8,2),
            pe_forward DECIMAL(8,2),
            ev_to_ebitda DECIMAL(8,2),
        ps_forward DECIMAL(8,2), -- calculated: marketCap / revenue_forward

        price_target DECIMAL(12,4),
        eps DECIMAL(8,4),

        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        UNIQUE(symbol, data_date)
);

-- Opinie analityków - aktualizowane miesięcznie
CREATE TABLE analyst_recommendations (
                                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                         symbol VARCHAR(20) NOT NULL REFERENCES stocks(symbol) ON DELETE CASCADE,
                                         data_date DATE NOT NULL,

                                         strong_buy INTEGER DEFAULT 0,
                                         buy INTEGER DEFAULT 0,
                                         hold INTEGER DEFAULT 0,
                                         sell INTEGER DEFAULT 0,
                                         strong_sell INTEGER DEFAULT 0,

                                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                         UNIQUE(symbol, data_date)
);


CREATE INDEX idx_price_history_symbol_date ON price_history(symbol, date DESC);
CREATE INDEX idx_price_history_date ON price_history(date DESC);

CREATE INDEX idx_fundamental_data_symbol_date ON fundamental_data(symbol, data_date DESC);
CREATE INDEX idx_fundamental_data_date ON fundamental_data(data_date DESC);

CREATE INDEX idx_analyst_recommendations_symbol_date ON analyst_recommendations(symbol, data_date DESC);
