# SÃO TOMÁS DE AQUINO – O MESTRE DA SABEDORIA (Projeto Android Completo)

Este pacote contém **todos os arquivos, pastas, ícones, código-fonte Kotlin, recursos XML, chave criptográfica de assinatura (`saotomas-release-key.jks`) e ativos offline** do aplicativo Android **São Tomás de Aquino – O Mestre da Sabedoria** (`br.org.sabedoria.saotomasdeaquino`).

## Arquivos Inclusos no Projeto

```text
SaoTomasDeAquino_Android_APK_Project/
├── build.gradle.kts                  # Configuração raiz do Gradle (AGP 8.5.2 + Kotlin 1.9.24)
├── settings.gradle.kts               # Repositórios Google e MavenCentral
├── gradle.properties                 # Propriedades JVM e AndroidX
├── gradlew / gradlew.bat             # Scripts de inicialização Gradle
├── saotomas-release-key.jks          # Keystore RSA 2048 bits pré-gerado para assinatura do APK
├── .github/workflows/build_apk.yml   # Workflow para compilação automática em nuvem (GitHub Actions)
└── app/
    ├── build.gradle.kts              # Configuração do módulo Android (minSdk 24, targetSdk 35) + assinatura automática
    ├── proguard-rules.pro            # Regras ProGuard preservando a ponte @JavascriptInterface
    └── src/main/
        ├── AndroidManifest.xml       # Manifesto do App (Permissões, Activity, Queries TTS)
        ├── assets/www/index.html     # Aplicação completa offline (Personagem 2.5D, Lip-Sync, Biblioteca Tomista, Chat IA)
        ├── java/br/org/sabedoria/saotomasdeaquino/
        │   └── MainActivity.kt       # Activity nativa Kotlin + TextToSpeech PT-BR grave + Share Intent
        └── res/
            ├── layout/activity_main.xml
            ├── values/strings.xml
            ├── values/colors.xml
            ├── values/themes.xml
            ├── xml/backup_rules.xml
            ├── xml/data_extraction_rules.xml
            ├── mipmap-anydpi-v26/ic_launcher.xml
            └── mipmap-mdpi ... mipmap-xxxhdpi/ic_launcher.png (Ícones em todas as resoluções)
```

## Como Abrir e Gerar o APK no Android Studio

1. Extraia o arquivo `SaoTomasDeAquino_Android_APK_Project.zip`.
2. No **Android Studio**, clique em **Open** e selecione a pasta `SaoTomasDeAquino_Android_APK_Project`.
3. O projeto já inclui a chave `saotomas-release-key.jks` configurada no `app/build.gradle.kts` (senha da store e da chave: `saotomas1274`, alias: `saotomas_mestre`).
4. Clique em **Build > Build Bundle(s) / APK(s) > Build APK(s)** para gerar o APK imediatamente.
