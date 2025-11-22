#!/bin/bash

echo "🧹 Limpando a base de dados com Flyway..."

mvn flyway:clean \
  -Dflyway.url=jdbc:postgresql://localhost:5432/paymentservice \
  -Dflyway.cleanDisabled=false \
  -Dflyway.user=postgres \
  -Dflyway.password=postgres

echo "✅ Base de dados limpa!"
