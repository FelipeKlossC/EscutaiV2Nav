Escutaí
Aplicativo Android de reprodução de músicas, inspirado no Spotify "Não é cópia", desenvolvido como projeto durante o semestre de Desenvolvimento de Aplicativos Móveis.
Integrantes do grupo: Felipe Kloss, Gabriel Asserman, Mariana Almeida.
O Escutaí é um app de streaming de música feito 100% em Kotlin + Jetpack Compose. Ele permite navegar por uma biblioteca de músicas, criar e gerenciar playlists, favoritar faixas, acompanhar um ranking das mais ouvidas, consultar o histórico de execuções e simular a contratação de planos VIP.
Além das funções clássicas de um player, o projeto traz recursos próprios, como o ranking de reproduções, as métricas calculadas por playlist (duração total e música destaque) e o histórico de execuções.

Sobre o projeto:
O objetivo do trabalho é construir, parte por parte, um aplicativo Android completo usando o Android Studio. Nosso tema foi uma releitura do Spotify, chamada Escutaí, com foco em:
Interface moderna em tema escuro (roxo/magenta), feita com Material 3;
Navegação entre várias telas com Navigation Compose;
Cadastro, listagem e remoção de dados (músicas e playlists);
Dados combinando duas data classes (Musica e Playlist) para gerar métricas.

Funcionalidades:
Player na tela inicial com botões de play/pause, anterior, próxima, aleatório, repetir e barra de progresso.
Biblioteca de músicas: cadastro de novas músicas (título, artista, duração e gênero), listagem e remoção.
Favoritos: marcar/desmarcar músicas com o coração e ver todas as curtidas em uma tela própria.
Playlists: criar, excluir, abrir e adicionar/remover músicas.
Métricas da playlist: total de faixas, duração total (em minutos) e a música mais ouvida (destaque).
Detalhe da música: álbum, gênero, duração e total de execuções; ao tocar a faixa, o contador de execuções aumenta.
Top músicas: ranking ordenado pelo número de reproduções.
Histórico de execuções: lista das músicas ouvidas recentemente (sem repetir a mesma música em sequência).
Perfil do usuário: resumo da atividade no app (músicas, playlists e histórico).
Planos VIP: tela com plano mensal e anual, com seleção e mensagem de confirmação.
Barra de navegação inferior para alternar entre as seções principais.

Telas do aplicativo:
1. Início (Home)
Tela principal do app. No topo ficam o botão de perfil, a logo Escutaí e o botão de compartilhar. No centro há o card do player com a capa do álbum, nome da música/artista, controles de mídia (aleatório, anterior, play/pause, próxima, repetir) e uma barra de progresso. Mais abaixo aparecem as duas primeiras playlists em destaque, o atalho "Ver todas" e o botão Gerenciar Músicas do App. Também há o atalho "abrir essa playlist", que leva direto à primeira playlist.
2. Biblioteca de Músicas
Tela de gerenciamento da biblioteca. Contém um formulário para adicionar nova música (título, artista, duração em segundos e gênero), com validação dos campos, e a lista "Músicas Cadastradas". Cada item mostra título, artista, duração e gênero, além dos botões de favoritar e remover. Tocar em uma música abre o detalhe dela.
3. Detalhe da Música
Mostra a capa, o título, o artista e um card com álbum, gênero, duração e total de execuções. Possui os botões de favoritar, play/pause (cada vez que a música é tocada, o total de execuções aumenta) e compartilhar. Se a música não existir, exibe "Música não encontrada!".
4. Playlists
Lista as playlists existentes e traz um formulário para criar nova playlist (nome e descrição). Cada card mostra nome, descrição e quantidade de músicas, com opção de excluir. Tocar na playlist abre o detalhe.
5. Detalhe da Playlist
Exibe um resumo com as métricas calculadas: total de faixas, duração total em minutos e a música destaque (a mais ouvida da playlist). Abaixo, lista as músicas da playlist, com opção de remover cada uma. O botão + abre uma janela para incluir músicas da biblioteca que ainda não estão na playlist.
6. Favoritos
Mostra todas as músicas marcadas com o coração ("Suas Músicas Curtidas"). É possível desfavoritar direto da lista ou abrir o detalhe da música. Quando não há favoritas, aparece uma mensagem informando.
7. Top Músicas
Ranking "Top Músicas Mais Ouvidas", com as músicas ordenadas da mais para a menos tocada, mostrando posição (#1, #2...), título, artista e número de plays.
8. Perfil
Mostra o avatar e os dados do usuário, e o card "Sua Atividade no App" com quantidade de músicas cadastradas, playlists criadas e músicas no histórico. Tem atalhos para Gerenciar Planos VIP e Ver Histórico de Músicas Ouvidas.
9. Planos (Mensal e Anual)
Apresenta duas opções de assinatura:

Plano Mensal: R$19,90/mês - Benefícios 	Áudio Hi-Fi, sem anúncios, pular quantas faixas quiser.
Plano Anual: R$179,90/ano (equivale a R$14,99/mês, economize 25%).
Ao selecionar um plano, o botão muda para "Plano Selecionado" e aparece uma mensagem de sucesso. (A contratação é apenas simulada.)

10. Histórico de Execuções
Lista as músicas ouvidas recentemente, da mais nova para a mais antiga, com título, artista, duração e gênero. Tocar em um item registra a música novamente no topo do histórico e abre o detalhe dela.

Navegação
A navegação é feita com Navigation Compose, com um NavHost central e uma barra inferior (NavigationBar) com 5 abas:
Início - Home
Músicas - Biblioteca de Músicas
Playlists - Lista de Playlists
Favoritos - Favoritos
Top - Top Músicas

As demais telas são acessadas a partir delas:
Perfil → pelo avatar na tela Início;
Planos e Histórico → pela tela Perfil;
Detalhe da Música e Detalhe da Playlist → ao tocar em um item das listas (recebem o id como argumento da rota).

Tecnologias utilizadas:
Linguagem: Kotlin
Interface: Jetpack Compose + Material 3
Navegação: Navigation Compose (androidx.navigation:navigation-compose)
Ícones: Material Icons Extended
Build: Gradle (Kotlin DSL) + Android Gradle Plugin
IDE: Android Studio
Configurações do módulo: minSdk 24, targetSdk 37, compileSdk 37, Java 11

Estrutura do projeto:
app/src/main/java/com/example/myapplication/
├── MainActivity.kt            # Ponto de entrada do app (tema + navegação)
├── data/
│   └── AppRepository.kt       # "Banco de dados" em memória (músicas, playlists, histórico)
├── model/
│   ├── Musica.kt              # Data class da música
│   └── Playlist.kt            # Data class da playlist
├── navigation/
│   ├── AppNavigation.kt       # NavHost + barra de navegação inferior
│   └── Rotas.kt               # Nomes das rotas do app
└── ui/
    ├── screens/               # Todas as telas (Home, Músicas, Playlists, Favoritos, Top, Perfil, Planos, Histórico...)
    └── theme/                 # Cores, tipografia e tema
Como os dados funcionam: o AppRepository é um object (singleton) que guarda listas observáveis (mutableStateListOf). Quando uma música ou playlist é adicionada, removida ou favoritada, as telas se atualizam automaticamente. O app já inicia com algumas músicas, playlists e histórico de exemplo.

Como rodar o projeto:
Pré-requisitos
Android Studio em versão recente (o projeto usa Android Gradle Plugin 9.x e Gradle 9.5, então use a versão estável mais atual do Android Studio);
Android SDK 37 instalado (o Android Studio oferece a instalação automaticamente ao abrir o projeto);
Um emulador Android (AVD) ou um celular Android com depuração USB ativada (Android 7.0 / API 24 ou superior);
Conexão com a internet na primeira execução, para baixar as dependências do Gradle.

Passo a passo:
Clone o repositório:
   git clone https://github.com/<seu-usuario>/<nome-do-repositorio>.git
Abra o Android Studio e clique em File → Open, selecionando a pasta do projeto clonado.
Aguarde o Gradle Sync terminar (pode demorar alguns minutos na primeira vez).
Crie um emulador em Tools → Device Manager (ou conecte seu celular via USB).
Selecione o dispositivo na barra superior e clique em Run ▶️ (ou use Shift + F10).
O app Escutaí abrirá na tela inicial.

Problemas comuns (Como é no Android Studio sempre vai ter problema)
Erro de versão do SDK: abra Tools → SDK Manager e instale o Android SDK 37.
Erro de JDK: em Settings → Build, Execution, Deployment → Build Tools → Gradle, selecione o JDK embutido do Android Studio (jbr).
Sync falhando: confira a conexão com a internet e use File → Sync Project with Gradle Files.

Limitações atuais:
Por ser um projeto em desenvolvimento, algumas partes ainda são simuladas:
Os botões de play não reproduzem áudio de verdade; eles alternam o estado de tocando/pausado e aumentam o contador de execuções.
Os dados ficam apenas em memória: ao fechar o app, as alterações (músicas, playlists e favoritos criados) são perdidas e voltam aos dados iniciais.
Os planos VIP e o perfil do usuário são demonstrativos (não há login nem pagamento real).
Os botões de compartilhar, anterior, próxima, aleatório e repetir ainda não têm ação.

Equipe
Felipe Kloss Conceição: Telas de Perfil, Planos e Histórico de Execuções; README e documentação
Gabriel Asserman: Tela Início (Home), navegação (barra inferior e rotas), Biblioteca de Músicas, Detalhe da Música e Favoritos
Mariana Almeida: Playlists, Detalhe da Playlist (métricas calculadas) e Top Músicas

Vídeo demonstrando as telas do aplicativo:


https://github.com/user-attachments/assets/e5f861f4-7217-476f-aa55-0c7d1285e75b

