#!/bin/bash

export $(grep -v '^#' postgres.env | xargs)

docker exec -i broadcast-db psql -U "$POSTGRES_USER" -c \
    "INSERT INTO senders (user_id, user_domain) VALUES ('$1', '$2') ON CONFLICT DO NOTHING;"
