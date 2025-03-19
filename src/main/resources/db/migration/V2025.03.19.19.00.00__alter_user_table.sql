DO $$
BEGIN

    IF EXISTS(SELECT 1 FROM information_schema.tables WHERE table_name='user' AND table_schema='aws-demo')
    THEN
        ALTER TABLE "aws-demo"."user"
            ADD COLUMN IF NOT EXISTS full_name VARCHAR(255) NOT NULL DEFAULT '',
            ADD COLUMN IF NOT EXISTS idp_username VARCHAR(255) NOT NULL DEFAULT '',
            ADD COLUMN IF NOT EXISTS idp_sub VARCHAR(255) NOT NULL DEFAULT '';
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE constraint_name = 'unique_user_idp_username'
          AND table_name = 'user'
          AND table_schema = 'aws-demo'
    ) THEN
        ALTER TABLE "aws-demo"."user" ADD CONSTRAINT unique_user_idp_username UNIQUE (idp_username);
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE constraint_name = 'unique_user_idp_sub'
          AND table_name = 'user'
          AND table_schema = 'aws-demo'
    ) THEN
        ALTER TABLE "aws-demo"."user" ADD CONSTRAINT unique_user_idp_sub UNIQUE (idp_sub);
    END IF;

END $$;
