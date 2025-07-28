echo "🚀 Iniciando o Docker se necessário..."

DOCKER_INFO_CMD=""
if ! docker info > /dev/null 2>&1; then
  if [ "$OS_TYPE" = "linux" ]; then
    echo "🔄 Tentando iniciar Docker no Linux via systemctl..."
    sudo systemctl start docker
    DOCKER_INFO_CMD="sudo docker info"
  elif [ "$OS_TYPE" = "windows" ] && [ "$SHELL_TYPE" = "git-bash" ]; then
    echo "🔄 Tentando iniciar Docker Desktop no Windows (via Git Bash)..."
    echo "❗ Por favor, certifique-se que o Docker Desktop está instalado e no PATH, ou inicie-o manualmente."
    start "" "C:\Program Files\Docker\Docker\Docker Desktop.exe"
    DOCKER_INFO_CMD="docker info"
  else
    echo "❗ Docker não detectado e não há forma automática de iniciá-lo para seu SO/Terminal."
    echo "Por favor, inicie o Docker manualmente e execute o script novamente."
    DOCKER_INFO_CMD="docker info"
    exit 1
  fi

  echo "⏳ Aguardando Docker iniciar..."
  for i in {1..20}; do
    sleep 3
    if $DOCKER_INFO_CMD > /dev/null 2>&1; then
      echo "✅ Docker iniciado!"
      break
    fi
    if [ "$i" -eq 20 ]; then
      echo "❌ Docker não iniciou após 60 segundos."
      exit 1
    fi
  done
fi

echo "✅ Docker está rodando e pronto para uso!"