CREATE TABLE IF NOT EXISTS PLAYERS
(
    id   INTEGER IDENTITY PRIMARY KEY,
    username VARCHAR(100) UNIQUE,
    password VARCHAR(255),
    is_admin BOOLEAN
);

CREATE TABLE IF NOT EXISTS TEAMS
(
    id   INTEGER IDENTITY PRIMARY KEY,
    name VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS MATCHES
(
    id      INTEGER IDENTITY PRIMARY KEY,
    date    DATETIME,
    home    INTEGER,
    visitor INTEGER,
    info    VARCHAR(256),
    home_score INTEGER,
    visitor_score INTEGER,
    CONSTRAINT FK_HOME_TEAM FOREIGN KEY (home) REFERENCES TEAMS,
    CONSTRAINT FK_VISITOR_TEAM FOREIGN KEY (visitor) REFERENCES TEAMS
);

CREATE TABLE IF NOT EXISTS FORECASTS
(
    id      INTEGER IDENTITY PRIMARY KEY,
    match   INTEGER,
    player  INTEGER,
    home_score INTEGER,
    visitor_score INTEGER,
    CONSTRAINT FK_MATCH FOREIGN KEY (match) REFERENCES MATCHES,
    CONSTRAINT FK_PLAYER FOREIGN KEY (player) REFERENCES PLAYERS
);
