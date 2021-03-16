#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER docker;
    CREATE DATABASE teacherbot_backend_dev;
    GRANT ALL PRIVILEGES ON DATABASE teacherbot_backend_dev TO docker;
EOSQL