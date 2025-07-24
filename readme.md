# 🃏 MemoryCard

> Um jogo da memória desenvolvido em **Java**, com comunicação entre **Servidor** e **Clientes** via **Sockets**.

---

## ✨ Sobre o projeto
✔️ Servidor controla o tabuleiro e a pontuação.  
✔️ Dois clientes se conectam ao servidor para jogar.  
✔️ Comunicação TCP/IP com `ServerSocket` e `Socket`.  
✔️ Implementado com **Threads** para múltiplos jogadores.  
✔️ Projeto desenvolvido para a disciplina **Orientação a Objetos 2**, ministrada pelo professor **Mário Popolin Neto**, pelo Instituto Federal de Educação, Ciência e Tecnologia Público Federal de São Paulo, campus Araraquara.

---

## 🚀 Como executar

🔧 **Pré-requisitos:**  
✔️ Java 8 ou superior instalado.  
✔️ IDE ou terminal.

### ▶️ Passos

1️⃣ **Compile as classes** (na pasta `src`):  
```bash
javac memorycard/*.java

2️⃣ Inicie o servidor:

java memorycard.Servidor

3️⃣ Inicie os clientes (em terminais separados):

java memorycard.Cliente

4️⃣ Na tela do cliente, insira seu nome e o IP do servidor para conectar.
🎮 Como jogar

🃏 Cada cliente escolhe posições para virar as cartas.
⭐ Ao acertar pares, você ganha pontos.
🏁 O jogo termina quando todas as cartas forem encontradas.
🛠 Tecnologias usadas

    ☕ Java SE

    🌐 Sockets (TCP/IP)

    🧵 Threads

💡 Divirta-se jogando! 😄