INSERT INTO "aws-demo"."user" (name, email)
VALUES ('Default', 'default_user@aws.com')
ON CONFLICT DO NOTHING;
