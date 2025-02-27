# demo-aws
Demo code using AWS cloud services (S3, DynamoDB, RDS, ...) by using security best practice and to test different services

# AWS commands:

## RDS

### List all RDS available engine versions for specific engine name. For example postgres like in the example below
```bash

aws rds describe-db-engine-versions --engine postgres --region eu-central-1 --query "DBEngineVersions[].EngineVersion"

```


Example of output

```text
[
    "11.22",
    "11.22-rds.20240418",
    "11.22-rds.20240509",
    "11.22-rds.20240808",
    "11.22-rds.20241121",
    "12.15",
]
```


### List all RDS available engine names . For example postgres like in the example below
```bash

aws rds describe-db-engine-versions --engine postgres --region eu-central-1 --query "DBEngineVersions[].EngineVersion"

```

Example of output

```text
[
    "aurora-mysql",
    "docdb",
    "custom-sqlserver-ee",
    "db2-ae",
    "neptune",
    "aurora-postgresql",
    "mariadb",
    "mysql",
    "oracle-ee",
    "oracle-se2",
    "oracle-se2-cdb",
    "postgres",

]
```
