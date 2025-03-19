

-- create tables permission, role, user_role, user_permission --
CREATE TABLE IF NOT EXISTS "aws-demo"."permission" (
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    CONSTRAINT permissions_name_pk PRIMARY KEY (name)
);

CREATE TABLE IF NOT EXISTS "aws-demo"."role" (
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    CONSTRAINT role_pk PRIMARY KEY (name)
);

CREATE TABLE IF NOT EXISTS "aws-demo"."user_role" (
    user_id BIGINT NOT NULL,
    role_name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_user_role PRIMARY KEY (user_id, role_name),
    CONSTRAINT fk__user_role__user_id
        FOREIGN KEY (user_id)
        REFERENCES "aws-demo"."user"(id)
        ON DELETE CASCADE,
    CONSTRAINT fk__user_role__role_name
        FOREIGN KEY (role_name)
        REFERENCES "aws-demo"."role"(name)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS "aws-demo"."user_permission" (
  user_id BIGINT NOT NULL,
  permission_name VARCHAR(255) NOT NULL,
  CONSTRAINT pk_user_permission PRIMARY KEY (user_id, permission_name),
  CONSTRAINT fk__user_role__user_id
      FOREIGN KEY (user_id)
      REFERENCES "aws-demo"."user"(id)
      ON DELETE CASCADE,
  CONSTRAINT fk__user_role__permission_name
      FOREIGN KEY (permission_name)
      REFERENCES "aws-demo"."permission"(name)
      ON DELETE CASCADE
);


-- insert permission and roles metadata --
INSERT INTO "aws-demo"."role" (name, description)
VALUES ('ADMIN', 'Admin role for grant specific administrator access'),
       ('MANAGER', 'Manger role to access manager specific information'),
       ('USER', 'Basic role with minimum necessary access in the application')
ON CONFLICT DO NOTHING;

INSERT INTO "aws-demo"."permission" (name, description)
VALUES ('ADMIN:READ', 'Admin read permission'),
       ('ADMIN:LEAD', 'Highest permission that allows to submit results or change permissions for the users'),
       ('MANAGER:READ', 'Manager read permission'),
       ('MANAGER:APPROVE', 'Manager approve permission'),
       ('USER:READ', 'User read permission'),
       ('USER:WRITE', 'User able to post own data')
ON CONFLICT DO NOTHING;

