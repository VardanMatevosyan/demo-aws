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


## EC2

### List List Official Amazon Linux AMIs
```bash

aws ec2 describe-images --owners amazon --filters "Name=name,Values=amzn2-ami-hvm-*-x86_64-gp2" --query 'Images[*].[ImageId,Name]' --output table


```

Example of output

```text
----------------------------------------------------------------------
|                           DescribeImages                           |
+------------------------+-------------------------------------------+
|  ami-00ac244ee0ad9050d |  amzn2-ami-hvm-2.0.20241014.0-x86_64-gp2  |
|  ami-00dc61b35bec09b72 |  amzn2-ami-hvm-2.0.20240306.2-x86_64-gp2  |
|  ami-010195b0508c73d92 |  amzn2-ami-hvm-2.0.20230906.0-x86_64-gp2  |
|  ami-00e89f3f4910f40a1 |  amzn2-ami-hvm-2.0.20240610.1-x86_64-gp2  |
+------------------------+-------------------------------------------+
```

