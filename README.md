# 📱 Guia Pocket - Jardim Universal

## 📋 Descrição do Projeto
O **Guia Pocket - Jardim Universal** é um aplicativo Android desenvolvido em Kotlin que funciona como um guia comercial colaborativo para o bairro Jardim Universal em Araraquara/SP. O app permite aos usuários cadastrar, explorar estabelecimentos locais e acionar funcionalidades nativas como ligações, navegação e compartilhamento.

## ✨ Funcionalidades
### ✅ **Lista de Estabelecimentos**
- RecyclerView otimizado com ViewHolder
- Cards com imagem, nome e categoria
- Filtro em tempo real por nome ou categoria
- Atualização automática após cadastro

### ✅ **Sistema de Cadastro**
- Formulário completo com validação
- Upload de imagens da galeria
- Persistência local com Room Database
- Feedback visual com Toast messages

### ✅ **Detalhes do Estabelecimento**
- Visualização completa de informações
- Intents nativas integradas:
  - 📞 **Ligar** (ACTION_DIAL)
  - 🌐 **Visitar site** (ACTION_VIEW)
  - 🗺️ **Abrir no Maps** (geo: URI)
  - 📤 **Compartilhar** (ACTION_SEND)
- Imagem destacada em alta resolução

### ✅ **Internacionalização**
- Suporte completo a Português e Inglês
- Troca de idioma dinâmica
- Strings centralizadas em resources
- Formatação locale-aware

### ✅ **Temas e Personalização**
- Modo Claro/Escuro automático
- Alternância manual de temas
- Design com Material Design 3
- Interface responsiva e acessível

## 🛠️ Arquitetura Técnica
### **Tecnologias Utilizadas**
- **Linguagem**: Kotlin 100%
- **Persistência**: Room Database + SQLite
- **UI Components**: RecyclerView, ViewBinding
- **Arquitetura**: Clean Architecture com Repository Pattern
- **Concorrência**: Kotlin Coroutines
- **Navegação**: Intents e ActivityResult API

## 🎥 Demonstração em Vídeo
*Demonstração das funcionalidades principais do aplicativo*

<p align="center">
  <img src="screenshots/demo.gif" width="300" alt="Demo do App Guia Pocket">
</p>

## Vídeo Explicativo em Inglês
[![Vídeo Explicativo em Inglês](https://img.shields.io/badge/▶-Assistir_Vídeo_Explicativo-FF0000?style=for-the-badge&logo=youtube)](https://drive.google.com/file/d/1Bgr0nMYeyt3GsdaNQHHqos7SA-Kv-5xp/view?usp=sharing)

### **📹 Conteúdo do Vídeo:**
- Explicação da arquitetura Room Database
- Demonstração do RecyclerView e Adapter
- Funcionamento dos Intents nativos
- Implementação do ActivityResult para imagens
- Sistema de filtro em tempo real

**Para assistir:** Clique no botão acima ou [acesse este link](https://drive.google.com/file/d/1Bgr0nMYeyt3GsdaNQHHqos7SA-Kv-5xp/view?usp=sharing)

## 🚀 Como Executar
1. Clone o repositório
2. Abra no Android Studio
3. Sincronize o projeto
4. Execute em um emulador ou dispositivo físico

## 📄 Licença
Este projeto foi desenvolvido para fins educacionais como parte da disciplina de Dispositivos Móveis 1.

---

**Desenvolvido por** Luiz Gustavo Monico  
**Disciplina**: Dispositivos Móveis 1  
**Instituição**: IFSP - Campus Araraquara
