create table results(
    id char(36) PRIMARY KEY,
    school_id char(36) NOT NULL,
    category_id char(36) NOT NULL,
    time_ms BIGINT NULL,

    foreign key (school_id)
        references schools(school_id),
    foreign key (category_id)
        references categories(id),

    unique (school_id,category_id)
);