CREATE TABLE public.tb_category (
    id uuid NOT NULL,
    "name" varchar(40) NOT NULL,
    CONSTRAINT tb_category_pkey PRIMARY KEY (id)
);

CREATE TABLE public.tb_challenge (
    id uuid NOT NULL,
    active bool NOT NULL,
    created_at timestamp(6) NOT NULL,
    description varchar(1500) NOT NULL,
    end_date timestamp(6),
    status varchar(255) NOT NULL,
    title varchar(150) NOT NULL,
    author_id uuid NOT NULL,
    category_id uuid,
    image_id uuid,
    CONSTRAINT tb_challenge_pkey PRIMARY KEY (id),
    CONSTRAINT tb_challenge_status_check CHECK (((status)::text = ANY ((ARRAY['TO_BEGIN'::character varying, 'IN_PROGRESS'::character varying, 'FINISHED'::character varying, 'CANCELED'::character varying])::text[]))),
    CONSTRAINT uk_cycwtdejn9r0s0fnys4qmy0tq UNIQUE (image_id)
);

ALTER TABLE public.tb_challenge ADD CONSTRAINT fkorpoli58ouo8pvr1nellex9fm FOREIGN KEY (author_id) REFERENCES public.tb_user(id);
ALTER TABLE public.tb_challenge ADD CONSTRAINT fkp1ipalns4f34q0textv5aa34v FOREIGN KEY (image_id) REFERENCES public.tb_image(id);
ALTER TABLE public.tb_challenge ADD CONSTRAINT fkqrmqdpynfyt5ev9wkjdprd2mv FOREIGN KEY (category_id) REFERENCES public.tb_category(id);

CREATE TABLE public.tb_challenge_technology (
    id uuid NOT NULL,
    challenge_id uuid NOT NULL,
    technology_id uuid NOT NULL,
    CONSTRAINT tb_challenge_technology_pkey PRIMARY KEY (id, challenge_id, technology_id)
);

ALTER TABLE public.tb_challenge_technology ADD CONSTRAINT fk84rxca3mx89h5b8rtlkget1k5 FOREIGN KEY (technology_id) REFERENCES public.tb_technology(id);
ALTER TABLE public.tb_challenge_technology ADD CONSTRAINT fked1a40iyl3asggy9dnhxce7il FOREIGN KEY (challenge_id) REFERENCES public.tb_challenge(id);

CREATE TABLE public.tb_follow_user (
    id uuid NOT NULL,
    followed_id uuid NOT NULL,
    follower_id uuid NOT NULL,
    CONSTRAINT tb_follow_user_pkey PRIMARY KEY (id, followed_id, follower_id)
);

ALTER TABLE public.tb_follow_user ADD CONSTRAINT fkjkgvnucwgy6xnaldj3jn6fy8d FOREIGN KEY (followed_id) REFERENCES public.tb_user(id);
ALTER TABLE public.tb_follow_user ADD CONSTRAINT fklkpxehtkufhmjsu7g8cpvad3u FOREIGN KEY (follower_id) REFERENCES public.tb_user(id);

CREATE TABLE public.tb_image (
    id uuid NOT NULL,
    file varchar(7000000),
    file_name varchar(255),
    CONSTRAINT tb_image_pkey PRIMARY KEY (id)
);

CREATE TABLE public.tb_label (
    id uuid NOT NULL,
    description varchar(255),
    title varchar(30) NOT NULL,
    CONSTRAINT tb_label_pkey PRIMARY KEY (id)
);

CREATE TABLE public.tb_like (
    solution_id uuid NOT NULL,
    participant_id uuid NOT NULL
);

ALTER TABLE public.tb_like ADD CONSTRAINT fk2p8m0kxtkrtwvoy1817mmjb4p FOREIGN KEY (participant_id) REFERENCES public.tb_user(id);
ALTER TABLE public.tb_like ADD CONSTRAINT fk3l470wa6vg5uf5e53kxvr61ar FOREIGN KEY (solution_id) REFERENCES public.tb_solution(id);

CREATE TABLE public.tb_participant (
    challenge_id uuid NOT NULL,
    participant_id uuid NOT NULL
);

ALTER TABLE public.tb_participant ADD CONSTRAINT fkkinom0hlaua1q0571e8j11vy0 FOREIGN KEY (participant_id) REFERENCES public.tb_user(id);
ALTER TABLE public.tb_participant ADD CONSTRAINT fkqvi2d37hnn18xpkbvcbwnrvqi FOREIGN KEY (challenge_id) REFERENCES public.tb_challenge(id);

CREATE TABLE public.tb_role (
    id uuid NOT NULL,
    "name" varchar(15) NOT NULL,
    CONSTRAINT tb_role_pkey PRIMARY KEY (id)
);

CREATE TABLE public.tb_solution (
    id uuid NOT NULL,
    deploy_url varchar(150),
    repository_url varchar(150) NOT NULL,
    author_id uuid NOT NULL,
    challenge_id uuid NOT NULL,
    CONSTRAINT tb_solution_pkey PRIMARY KEY (id)
);

ALTER TABLE public.tb_solution ADD CONSTRAINT fkgi69jr87ppade4m3hqlg8cpe2 FOREIGN KEY (author_id) REFERENCES public.tb_user(id);
ALTER TABLE public.tb_solution ADD CONSTRAINT fkr0mmy0ns7jgai2rjetakmk54p FOREIGN KEY (challenge_id) REFERENCES public.tb_challenge(id);

CREATE TABLE public.tb_solution_image (
    solution_id uuid NOT NULL,
    image_id uuid NOT NULL
);

ALTER TABLE public.tb_solution_image ADD CONSTRAINT fkcquysux4760mjt2qdnb59fm38 FOREIGN KEY (image_id) REFERENCES public.tb_image(id);
ALTER TABLE public.tb_solution_image ADD CONSTRAINT fkctuum5udt0f1b2s6m6wf36h31 FOREIGN KEY (solution_id) REFERENCES public.tb_solution(id);

CREATE TABLE public.tb_technology (
    id uuid NOT NULL,
    color varchar(6),
    documentation_link varchar(150),
    "name" varchar(50) NOT NULL,
    CONSTRAINT tb_technology_pkey PRIMARY KEY (id)
);

CREATE TABLE public.tb_user (
    id uuid NOT NULL,
    active bool NOT NULL,
    additional_url varchar(150),
    created_at timestamp(6) NOT NULL,
    email varchar(65) NOT NULL,
    github_url varchar(150),
    "name" varchar(50) NOT NULL,
    "password" varchar(45) NOT NULL,
    updated_at timestamp(6),
    image_id uuid,
    CONSTRAINT tb_user_pkey PRIMARY KEY (id),
    CONSTRAINT uk_4vih17mube9j7cqyjlfbcrk4m UNIQUE (email),
    CONSTRAINT uk_5w1tx520bue2xh4802w9feoqj UNIQUE (image_id)
);

ALTER TABLE public.tb_user ADD CONSTRAINT fkebekn1vtug6kd5gqhvjbrxrh5 FOREIGN KEY (image_id) REFERENCES public.tb_image(id);

CREATE TABLE public.tb_user_label (
    user_id uuid NOT NULL,
    label_id uuid NOT NULL,
    CONSTRAINT tb_user_label_pkey PRIMARY KEY (user_id, label_id)
);

ALTER TABLE public.tb_user_label ADD CONSTRAINT fkc4eyd0hcbww3au0u4mh9ghjrx FOREIGN KEY (label_id) REFERENCES public.tb_label(id);
ALTER TABLE public.tb_user_label ADD CONSTRAINT fkmvrc9c5aceveu74mlibuf1umt FOREIGN KEY (user_id) REFERENCES public.tb_user(id);

CREATE TABLE public.tb_user_role (
    user_id uuid NOT NULL,
    role_id uuid NOT NULL
);

ALTER TABLE public.tb_user_role ADD CONSTRAINT fk7vn3h53d0tqdimm8cp45gc0kl FOREIGN KEY (user_id) REFERENCES public.tb_user(id);
ALTER TABLE public.tb_user_role ADD CONSTRAINT fkea2ootw6b6bb0xt3ptl28bymv FOREIGN KEY (role_id) REFERENCES public.tb_role(id);
